package com.github.sib_energy_craft.machines.bio_reactor.block.entity;

import com.github.sib_energy_craft.containers.CleanEnergyContainer;
import com.github.sib_energy_craft.energy_api.Energy;
import com.github.sib_energy_craft.energy_api.EnergyOffer;
import com.github.sib_energy_craft.energy_api.items.ChargeableItem;
import com.github.sib_energy_craft.energy_api.supplier.EnergySupplier;
import com.github.sib_energy_craft.machines.bio_reactor.BioFuelRegistry;
import com.github.sib_energy_craft.machines.bio_reactor.block.AbstractBioReactorBlock;
import com.github.sib_energy_craft.machines.bio_reactor.block.BioReactorFilling;
import com.github.sib_energy_craft.pipes.api.ItemConsumer;
import com.github.sib_energy_craft.pipes.utils.PipeUtils;
import com.github.sib_energy_craft.screen.property.ScreenPropertyTypes;
import com.github.sib_energy_craft.screen.property.TypedScreenProperty;
import lombok.Getter;
import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerFactory;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventories;
import net.minecraft.inventory.SidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Set;

/**
 * @since 0.0.23
 * @author sibmaks
 */
public abstract class AbstractBioReactorBlockEntity<T extends AbstractBioReactorBlock> extends BlockEntity
        implements SidedInventory, ExtendedScreenHandlerFactory, EnergySupplier, ItemConsumer {

    private static final Set<Direction> SUPPLYING_DIRECTIONS = Set.of(
            Direction.EAST, Direction.WEST, Direction.NORTH, Direction.SOUTH, Direction.DOWN, Direction.UP
    );

    public static final int CHARGE_SLOT_INDEX = 0;
    public static final int FUEL_SLOT_INDEX = 1;
    public static final int[] ACTIVE_SLOTS = {
            CHARGE_SLOT_INDEX, FUEL_SLOT_INDEX
    };

    protected DefaultedList<ItemStack> inventory;

    @Getter
    private int ferments;
    private int fermentationTime;
    private int fermentationTimeTotal;
    private int fermentationBreakdownTime;
    private CleanEnergyContainer energyContainer;
    protected final T block;

    protected final List<TypedScreenProperty<?>> typedScreenProperties;

    protected AbstractBioReactorBlockEntity(@NotNull BlockEntityType<?> blockEntityType,
                                            @NotNull BlockPos pos,
                                            @NotNull BlockState state,
                                            @NotNull T block) {
        super(blockEntityType, pos, state);
        this.block = block;
        this.energyContainer = new CleanEnergyContainer(Energy.ZERO, block.getMaxCharge());
        this.inventory = DefaultedList.ofSize(2, ItemStack.EMPTY);
        this.typedScreenProperties = List.of(
                new TypedScreenProperty<>(
                        BioReactorTypedProperties.CHARGE.ordinal(),
                        ScreenPropertyTypes.INT,
                        this::getCharge
                ),
                new TypedScreenProperty<>(
                        BioReactorTypedProperties.FERMENTATION_TIME.ordinal(),
                        ScreenPropertyTypes.INT,
                        () -> fermentationTime
                ),
                new TypedScreenProperty<>(
                        BioReactorTypedProperties.FERMENTATION_TIME_TOTAL.ordinal(),
                        ScreenPropertyTypes.INT,
                        () -> fermentationTimeTotal
                ),
                new TypedScreenProperty<>(
                        BioReactorTypedProperties.FERMENTS.ordinal(),
                        ScreenPropertyTypes.INT,
                        () -> ferments
                )
        );
    }

    @Override
    public void readNbt(@NotNull NbtCompound nbt) {
        super.readNbt(nbt);
        this.inventory = DefaultedList.ofSize(this.size(), ItemStack.EMPTY);
        var nbtInventory = nbt.getCompound("Inventory");
        Inventories.readNbt(nbtInventory, this.inventory);
        this.ferments = nbt.getInt("Ferments");
        this.fermentationTime = nbt.getInt("FermentationTime");
        this.fermentationTimeTotal = nbt.getInt("FermentationTimeTotal");
        this.fermentationBreakdownTime = nbt.getInt("FermentationBreakdownTime");
        this.energyContainer = CleanEnergyContainer.readNbt(nbt);
    }

    @Override
    protected void writeNbt(@NotNull NbtCompound nbt) {
        super.writeNbt(nbt);
        var nbtInventory = new NbtCompound();
        Inventories.writeNbt(nbtInventory, this.inventory);
        nbt.put("Inventory", nbtInventory);
        nbt.putInt("Ferments", this.ferments);
        nbt.putInt("FermentationTime", this.fermentationTime);
        nbt.putInt("FermentationTimeTotal", this.fermentationTimeTotal);
        nbt.putInt("FermentationBreakdownTime", this.fermentationBreakdownTime);
        this.energyContainer.writeNbt(nbt);
    }

    private boolean isFermenting() {
        return fermentationTime > 0;
    }

    public static void tick(@NotNull World world,
                            @NotNull BlockPos pos,
                            @NotNull BlockState state,
                            @NotNull AbstractBioReactorBlockEntity<?> blockEntity) {
        if(world.isClient) {
            return;
        }
        var block = blockEntity.block;
        var filling = 100 * blockEntity.ferments / block.getMaxFerments();
        var fermenting = blockEntity.isFermenting();
        var stateChanged = false;
        if (fermenting) {
            --blockEntity.fermentationTime;
            blockEntity.ferments = Math.min(blockEntity.ferments + 1, block.getMaxFerments());
            stateChanged = true;
        }
        if(blockEntity.fermentationBreakdownTime > 0) {
            blockEntity.fermentationBreakdownTime--;
            if(blockEntity.fermentationBreakdownTime == 0) {
                blockEntity.ferments = Math.max(0, blockEntity.ferments - 1);
                blockEntity.fermentationBreakdownTime = block.getTicksToFermentBreakdown();
            }
            stateChanged = true;
        } else if(blockEntity.ferments > 0) {
            blockEntity.fermentationBreakdownTime = block.getTicksToFermentBreakdown();
            stateChanged = true;
        }
        if(blockEntity.energyContainer.hasSpace()) {
            int maxEnergy = block.getMaxEnergy();
            int ferments = blockEntity.ferments;
            int maxFerments = block.getMaxFerments();
            blockEntity.energyContainer.add(ferments * maxEnergy / maxFerments);
        }

        if(blockEntity.energyContainer.hasEnergy()) {
            stateChanged |= blockEntity.chargeSlotStack();
            blockEntity.tick(blockEntity);
        }

        var fuelStack = blockEntity.inventory.get(FUEL_SLOT_INDEX);
        if (!blockEntity.isFermenting() && !fuelStack.isEmpty() && blockEntity.ferments < block.getMaxFerments()) {
            var fuelItem = fuelStack.getItem();
            int fermentationAmount = BioFuelRegistry.getFermentationAmount(fuelItem);
            if(fermentationAmount < block.getMaxFerments() - blockEntity.ferments) {
                blockEntity.fermentationTime = blockEntity.fermentationTimeTotal = fermentationAmount;
                if (blockEntity.isFermenting()) {
                    stateChanged = true;
                    fuelStack.decrement(1);
                    if (fuelStack.isEmpty()) {
                        fuelItem = fuelItem.getRecipeRemainder();
                        blockEntity.inventory.set(FUEL_SLOT_INDEX, fuelItem == null ? ItemStack.EMPTY : new ItemStack(fuelItem));
                    }
                }
            }
        }
        var newFilling = 100 * blockEntity.ferments / block.getMaxFerments();
        if(filling != newFilling) {
            if(newFilling < 10) {
                state = state.with(AbstractBioReactorBlock.FILLING, BioReactorFilling.EMPTY);
            } else if(newFilling < 35) {
                state = state.with(AbstractBioReactorBlock.FILLING, BioReactorFilling.ALMOST_EMPTY);
            } else if(newFilling < 60) {
                state = state.with(AbstractBioReactorBlock.FILLING, BioReactorFilling.HALF);
            } else if(newFilling < 85) {
                state = state.with(AbstractBioReactorBlock.FILLING, BioReactorFilling.ALMOST_FULL);
            } else {
                state = state.with(AbstractBioReactorBlock.FILLING, BioReactorFilling.FULL);
            }
            stateChanged = true;
        }
        if (fermenting != blockEntity.isFermenting()) {
            state = state.with(AbstractBioReactorBlock.LIT, blockEntity.isFermenting());
            stateChanged = true;
        }
        if (stateChanged) {
            world.setBlockState(pos, state, Block.NOTIFY_ALL);
            AbstractBioReactorBlockEntity.markDirty(world, pos, state);
        }
    }

    private boolean chargeSlotStack() {
        var chargingStack = inventory.get(CHARGE_SLOT_INDEX);
        var chargingItem = chargingStack.getItem();
        if(!chargingStack.isEmpty() &&
                chargingItem instanceof ChargeableItem chargeableItem &&
                chargeableItem.hasFreeSpace(chargingStack)) {
            var energyPacketSize = block.getEnergyPacketSize();
            var charge = energyContainer.getCharge();
            var packet = Math.min(energyPacketSize.min(charge).intValue(),
                    chargeableItem.getFreeSpace(chargingStack));
            if(energyContainer.subtract(Energy.of(packet))) {
                var notUsed = chargeableItem.charge(chargingStack, packet);
                energyContainer.add(notUsed);
                return true;
            }
        }
        return false;
    }

    @Override
    public int[] getAvailableSlots(@NotNull Direction side) {
        return ACTIVE_SLOTS;
    }

    @Override
    public boolean canInsert(int slot,
                             @NotNull ItemStack stack,
                             @Nullable Direction dir) {
        return this.isValid(slot, stack);
    }

    @Override
    public boolean canExtract(int slot,
                              @NotNull ItemStack stack,
                              @NotNull Direction dir) {
        if (dir == Direction.DOWN && slot == FUEL_SLOT_INDEX) {
            return stack.isOf(Items.BOWL) || stack.isOf(Items.BUCKET);
        }
        return true;
    }

    @Override
    public int size() {
        return this.inventory.size();
    }

    @Override
    public boolean isEmpty() {
        return this.inventory.stream().allMatch(ItemStack::isEmpty);
    }

    @NotNull
    @Override
    public ItemStack getStack(int slot) {
        return this.inventory.get(slot);
    }

    @NotNull
    @Override
    public ItemStack removeStack(int slot, int amount) {
        return Inventories.splitStack(this.inventory, slot, amount);
    }

    @NotNull
    @Override
    public ItemStack removeStack(int slot) {
        return Inventories.removeStack(this.inventory, slot);
    }

    @Override
    public void setStack(int slot,
                         @NotNull ItemStack stack) {
        var itemStack = this.inventory.get(slot);
        var bl = !stack.isEmpty() && ItemStack.canCombine(stack, itemStack);
        this.inventory.set(slot, stack);
        if (stack.getCount() > this.getMaxCountPerStack()) {
            stack.setCount(this.getMaxCountPerStack());
        }
        if (slot == CHARGE_SLOT_INDEX && !bl) {
            this.markDirty();
        }
    }

    @Override
    public boolean canPlayerUse(@NotNull PlayerEntity player) {
        var world = this.world;
        if (world == null) {
            return false;
        }
        if (world.getBlockEntity(this.pos) != this) {
            return false;
        }
        return player.squaredDistanceTo(pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5) <= 64.0;
    }

    @Override
    public boolean isValid(int slot,
                           @NotNull ItemStack stack) {
        if (slot == FUEL_SLOT_INDEX) {
            var itemStack = this.inventory.get(FUEL_SLOT_INDEX);
            return BioFuelRegistry.isFuel(stack) || stack.isOf(Items.BUCKET) && !itemStack.isOf(Items.BUCKET);
        }
        if(slot == CHARGE_SLOT_INDEX) {
            var item = stack.getItem();
            if(item instanceof ChargeableItem chargeableItem) {
                return chargeableItem.hasFreeSpace(stack);
            }
            return false;
        }
        return true;
    }

    @Override
    public void clear() {
        this.inventory.clear();
    }

    @Override
    public void writeScreenOpeningData(@NotNull ServerPlayerEntity player,
                                       @NotNull PacketByteBuf buf) {
        buf.writeInt(getMaxCharge());
        buf.writeInt(block.getMaxFerments());
        buf.writeInt(block.getEnergyPacketSize().intValue());
    }

    /**
     * Called when item placed in world
     * @param charge item charge
     */
    public void onPlaced(int charge, int ferments) {
        this.energyContainer.add(charge);
        this.ferments = Math.min(block.getMaxFerments(), this.ferments + ferments);
    }

    @Override
    public @NotNull Set<Direction> getSupplyingDirections() {
        return SUPPLYING_DIRECTIONS;
    }

    @Override
    public @NotNull EnergyOffer createOffer() {
        var energyPerTick = block.getEnergyPacketSize();
        return new EnergyOffer(this, energyPerTick);
    }

    @Override
    public synchronized boolean supplyEnergy(@NotNull Energy energy) {
        if(energy.compareTo(this.energyContainer.getCharge()) <= 0) {
            return this.energyContainer.subtract(energy);
        }
        return false;
    }

    @Override
    public boolean canConsume(@NotNull ItemStack itemStack, @NotNull Direction direction) {
        if(BioFuelRegistry.isFuel(itemStack)) {
            var currentFuel = inventory.get(FUEL_SLOT_INDEX);
            return currentFuel.isEmpty() || PipeUtils.canMergeItems(currentFuel, itemStack);
        }
        return false;
    }

    @Override
    public @NotNull ItemStack consume(@NotNull ItemStack itemStack, @NotNull Direction direction) {
        if (!canConsume(itemStack, direction)) {
            return itemStack;
        }
        markDirty();
        var currentFuel = inventory.get(FUEL_SLOT_INDEX);
        if (currentFuel.isEmpty()) {
            inventory.set(FUEL_SLOT_INDEX, itemStack);
            return ItemStack.EMPTY;
        }
        return PipeUtils.mergeItems(currentFuel, itemStack);
    }

    /**
     * Get current generator charge
     *
     * @return generator charge
     */
    public int getCharge() {
        return energyContainer.getCharge().intValue();
    }

    /**
     * Get max generator charge
     *
     * @return max generator charge
     */
    public int getMaxCharge() {
        return energyContainer.getMaxCharge().intValue();
    }
}

