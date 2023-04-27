package com.github.sib_energy_craft.machines.generator.block.entity;

import com.github.sib_energy_craft.containers.CleanEnergyContainer;
import com.github.sib_energy_craft.energy_api.Energy;
import com.github.sib_energy_craft.energy_api.EnergyOffer;
import com.github.sib_energy_craft.energy_api.items.ChargeableItem;
import com.github.sib_energy_craft.energy_api.supplier.EnergySupplier;
import com.github.sib_energy_craft.machines.generator.block.AbstractEnergyGeneratorBlock;
import com.github.sib_energy_craft.sec_utils.screen.PropertyMap;
import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerFactory;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.AbstractFurnaceBlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.block.entity.LockableContainerBlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventories;
import net.minecraft.inventory.SidedInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Map;
import java.util.Set;

/**
 * @since 0.0.1
 * @author sibmaks
 */
public abstract class AbstractEnergyGeneratorBlockEntity extends LockableContainerBlockEntity
        implements SidedInventory, ExtendedScreenHandlerFactory, EnergySupplier {
    private static final Map<Item, Integer> FUEL_MAP = AbstractFurnaceBlockEntity.createFuelTimeMap();

    private static final Set<Direction> SUPPLYING_DIRECTIONS = Set.of(
            Direction.EAST, Direction.WEST, Direction.NORTH, Direction.SOUTH, Direction.DOWN, Direction.UP
    );

    public static final int CHARGE_SLOT_INDEX = 0;
    public static final int FUEL_SLOT_INDEX = 1;
    public static final int[] ACTIVE_SLOTS = {
            CHARGE_SLOT_INDEX, FUEL_SLOT_INDEX
    };

    protected DefaultedList<ItemStack> inventory;

    private int burnTime;
    private int fuelTime;
    private CleanEnergyContainer energyContainer;
    private final AbstractEnergyGeneratorBlock block;

    protected final PropertyMap<EnergyGeneratorProperties> propertyMap;

    protected AbstractEnergyGeneratorBlockEntity(@NotNull BlockEntityType<?> blockEntityType,
                                                 @NotNull BlockPos pos,
                                                 @NotNull BlockState state,
                                                 @NotNull AbstractEnergyGeneratorBlock block) {
        super(blockEntityType, pos, state);
        this.block = block;
        this.propertyMap = new PropertyMap<>(EnergyGeneratorProperties.class);
        this.propertyMap.add(EnergyGeneratorProperties.BURN_TIME, () -> burnTime);
        this.propertyMap.add(EnergyGeneratorProperties.FUEL_TIME, () -> fuelTime);
        this.propertyMap.add(EnergyGeneratorProperties.CHARGE, () -> energyContainer.getCharge().intValue());
        this.propertyMap.add(EnergyGeneratorProperties.MAX_CHARGE, () -> energyContainer.getMaxCharge().intValue());
        this.propertyMap.add(EnergyGeneratorProperties.ENERGY_PACKET_SIZE, () -> block.getEnergyPacketSize().intValue());
        this.energyContainer = new CleanEnergyContainer(Energy.ZERO, block.getMaxCharge());
        inventory = DefaultedList.ofSize(2, ItemStack.EMPTY);
    }

    private boolean isBurning() {
        return this.burnTime > 0;
    }

    @Override
    public void readNbt(@NotNull NbtCompound nbt) {
        super.readNbt(nbt);
        this.inventory = DefaultedList.ofSize(this.size(), ItemStack.EMPTY);
        Inventories.readNbt(nbt, this.inventory);
        this.burnTime = nbt.getShort("BurnTime");
        this.fuelTime = this.getFuelTime(this.inventory.get(FUEL_SLOT_INDEX));
        this.energyContainer = CleanEnergyContainer.readNbt(nbt);
    }

    @Override
    protected void writeNbt(@NotNull NbtCompound nbt) {
        super.writeNbt(nbt);
        nbt.putShort("BurnTime", (short) this.burnTime);
        Inventories.writeNbt(nbt, this.inventory);
        this.energyContainer.writeNbt(nbt);
    }

    public static void tick(@NotNull World world,
                            @NotNull BlockPos pos,
                            @NotNull BlockState state,
                            @NotNull AbstractEnergyGeneratorBlockEntity blockEntity) {
        var burning = blockEntity.isBurning();
        var stateChanged = false;
        if (blockEntity.isBurning()) {
            --blockEntity.burnTime;
            stateChanged = true;
            blockEntity.energyContainer.add(blockEntity.block.getFuelToEnergyCoefficient());
        }
        if(blockEntity.energyContainer.hasEnergy()) {
            stateChanged = true;
            var chargingStack = blockEntity.inventory.get(CHARGE_SLOT_INDEX);
            var chargingItem = chargingStack.getItem();
            if(!chargingStack.isEmpty() &&
                    chargingItem instanceof ChargeableItem chargeableItem &&
                    chargeableItem.hasFreeSpace(chargingStack)) {
                var energyPacketSize = blockEntity.block.getEnergyPacketSize();
                var charge = blockEntity.energyContainer.getCharge();
                var packet = Math.min(energyPacketSize.min(charge).intValue(),
                        chargeableItem.getFreeSpace(chargingStack));
                if(blockEntity.energyContainer.subtract(Energy.of(packet))) {
                    var notUsed = chargeableItem.charge(chargingStack, packet);
                    blockEntity.energyContainer.add(notUsed);
                }
            }
            blockEntity.tick(blockEntity);
        }

        var fuelStack = blockEntity.inventory.get(FUEL_SLOT_INDEX);
        if (!blockEntity.isBurning() && !fuelStack.isEmpty() && blockEntity.energyContainer.hasSpace()) {
            blockEntity.fuelTime = blockEntity.burnTime = blockEntity.getFuelTime(fuelStack);
            if (blockEntity.isBurning()) {
                stateChanged = true;
                var item = fuelStack.getItem();
                fuelStack.decrement(1);
                if (fuelStack.isEmpty()) {
                    item = item.getRecipeRemainder();
                    blockEntity.inventory.set(FUEL_SLOT_INDEX, item == null ? ItemStack.EMPTY : new ItemStack(item));
                }
            }
        }
        if (burning != blockEntity.isBurning()) {
            state = state.with(AbstractEnergyGeneratorBlock.LIT, blockEntity.isBurning());
            world.setBlockState(pos, state, Block.NOTIFY_ALL);
        }
        if (stateChanged) {
            AbstractEnergyGeneratorBlockEntity.markDirty(world, pos, state);
        }
    }

    /**
     * Get fuel time for passed fuel item
     *
     * @param fuel fuel item
     * @return not negative number
     */
    protected int getFuelTime(@NotNull ItemStack fuel) {
        if (fuel.isEmpty()) {
            return 0;
        }
        var item = fuel.getItem();
        return FUEL_MAP.getOrDefault(item, 0);
    }

    /**
     * Check can pass item used as fuel in generator
     *
     * @param stack item stack
     * @return true - can be used, false - otherwise
     */
    public static boolean canUseAsFuel(@NotNull ItemStack stack) {
        return FUEL_MAP.containsKey(stack.getItem());
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
            return stack.isOf(Items.WATER_BUCKET) || stack.isOf(Items.BUCKET);
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
        var bl = !stack.isEmpty() && stack.isItemEqual(itemStack) && ItemStack.areNbtEqual(stack, itemStack);
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
            return AbstractEnergyGeneratorBlockEntity.canUseAsFuel(stack) || stack.isOf(Items.BUCKET) && 
                    !itemStack.isOf(Items.BUCKET);
        }
        return true;
    }

    @Override
    public void clear() {
        this.inventory.clear();
    }

    /**
     * Called when item placed in world
     * @param charge item charge
     */
    public void onPlaced(int charge) {
        this.energyContainer.add(charge);
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
}

