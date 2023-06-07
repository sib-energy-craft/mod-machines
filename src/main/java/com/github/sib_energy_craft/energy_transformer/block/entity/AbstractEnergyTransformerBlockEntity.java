package com.github.sib_energy_craft.energy_transformer.block.entity;

import com.github.sib_energy_craft.containers.CleanEnergyContainer;
import com.github.sib_energy_craft.energy_api.Energy;
import com.github.sib_energy_craft.energy_api.EnergyLevel;
import com.github.sib_energy_craft.energy_api.EnergyOffer;
import com.github.sib_energy_craft.energy_api.consumer.EnergyConsumer;
import com.github.sib_energy_craft.energy_api.supplier.EnergySupplier;
import com.github.sib_energy_craft.energy_transformer.block.AbstractEnergyTransformerBlock;
import com.github.sib_energy_craft.energy_transformer.screen.EnergyTransformerScreenHandler;
import com.github.sib_energy_craft.sec_utils.screen.PropertyMap;
import lombok.Getter;
import lombok.Setter;
import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerFactory;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.state.property.Property;
import net.minecraft.text.Text;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import org.jetbrains.annotations.NotNull;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @since 0.0.4
 * @author sibmaks
 */
public abstract class AbstractEnergyTransformerBlockEntity extends BlockEntity
        implements ExtendedScreenHandlerFactory, EnergyConsumer, EnergySupplier {

    @Getter
    @Setter
    private Text displayName;
    private final AbstractEnergyTransformerBlock block;
    private volatile Direction[] supplyingDirections;
    private volatile List<Direction> consumingDirections;
    private volatile AbstractEnergyTransformerMode mode;
    private volatile boolean dirty;
    private CleanEnergyContainer energyContainer;
    protected final PropertyMap<AbstractEnergyTransformerProperties> propertyMap;

    protected AbstractEnergyTransformerBlockEntity(@NotNull BlockEntityType<?> blockEntityType,
                                                   @NotNull BlockPos pos,
                                                   @NotNull BlockState state,
                                                   @NotNull AbstractEnergyTransformerBlock block) {
        super(blockEntityType, pos, state);
        this.block = block;
        this.displayName = Text.translatable(block.getContainerNameCode());
        this.energyContainer = new CleanEnergyContainer(Energy.ZERO, block.getHighEnergyLevel().to * 2);
        this.mode = AbstractEnergyTransformerMode.UP;
        this.supplyingDirections = getSupplyingDirections(mode, state);
        this.consumingDirections = getConsumingDirections(mode, state);
        this.propertyMap = new PropertyMap<>(AbstractEnergyTransformerProperties.class);
        this.propertyMap.add(AbstractEnergyTransformerProperties.LOW_LEVEL, () -> block.getHighEnergyLevel().to);
        this.propertyMap.add(AbstractEnergyTransformerProperties.MAX_LEVEL, () -> block.getLowEnergyLevel().to);
        this.propertyMap.add(AbstractEnergyTransformerProperties.MODE, () -> mode.ordinal());
    }

    @Override
    public void readNbt(@NotNull NbtCompound nbt) {
        super.readNbt(nbt);
        var modeCode = nbt.getString("Mode");
        this.mode = AbstractEnergyTransformerMode.valueOf(modeCode);
        this.energyContainer = CleanEnergyContainer.readNbt(nbt);
        this.dirty = true;
    }

    @Override
    protected void writeNbt(@NotNull NbtCompound nbt) {
        super.writeNbt(nbt);
        nbt.putString("Mode", this.mode.toString());
        this.energyContainer.writeNbt(nbt);
    }

    public static void tick(@NotNull World world,
                            @NotNull BlockPos pos,
                            @NotNull BlockState state,
                            @NotNull AbstractEnergyTransformerBlockEntity blockEntity) {
        if(world.isClient || !(world instanceof ServerWorld serverWorld)) {
            return;
        }
        if(blockEntity.dirty) {
            blockEntity.supplyingDirections = getSupplyingDirections(blockEntity.mode, state);
            blockEntity.consumingDirections = getConsumingDirections(blockEntity.mode, state);
            blockEntity.dirty = false;
        }
        if (blockEntity.energyContainer.hasEnergy()) {
            blockEntity.tick(serverWorld, blockEntity);
        }
        AbstractEnergyTransformerBlockEntity.markDirty(world, pos, state);
    }

    private static @NotNull Direction[] getSupplyingDirections(
            @NotNull AbstractEnergyTransformerMode mode,
            @NotNull BlockState blockState) {
        var facing = blockState.get(AbstractEnergyTransformerBlock.FACING);
        switch (mode) {
            case UP -> {
                return new Direction[]{facing};
            }
            case DOWN -> {
                return AbstractEnergyTransformerBlock.FACING.stream()
                        .map(Property.Value::value)
                        .filter(it -> !it.equals(facing))
                        .toArray(Direction[]::new);
            }
        }
        return new Direction[0];
    }

    protected static @NotNull List<Direction> getConsumingDirections(
            @NotNull AbstractEnergyTransformerMode mode,
            @NotNull BlockState blockState) {
        var facing = blockState.get(AbstractEnergyTransformerBlock.FACING);
        switch (mode) {
            case UP -> {
                return AbstractEnergyTransformerBlock.FACING.stream()
                        .map(Property.Value::value)
                        .filter(it -> !it.equals(facing))
                        .collect(Collectors.toList());
            }
            case DOWN -> {
                return Collections.singletonList(facing);
            }
        }
        return Collections.emptyList();
    }

    public void setMode(@NotNull AbstractEnergyTransformerMode mode) {
        this.mode = mode;
        this.dirty = true;
    }

    /**
     * Called when item placed in world
     * @param charge item charge
     */
    public void onPlaced(int charge) {
        this.energyContainer.add(charge);
        this.dirty = true;
    }

    @Override
    public @NotNull ScreenHandler createMenu(int syncId,
                                             @NotNull PlayerInventory inv,
                                             @NotNull PlayerEntity player) {
        return new EnergyTransformerScreenHandler(syncId, propertyMap, world, pos);
    }

    @Override
    public void writeScreenOpeningData(@NotNull ServerPlayerEntity player,
                                       @NotNull PacketByteBuf buf) {
        buf.writeBlockPos(pos);
    }

    @Override
    public boolean isConsumeFrom(@NotNull Direction direction) {
        return consumingDirections.contains(direction);
    }

    @Override
    public void receiveOffer(@NotNull EnergyOffer energyOffer) {
        EnergyLevel inputEnergyLevel;
        if(mode == AbstractEnergyTransformerMode.UP) {
            inputEnergyLevel = block.getLowEnergyLevel();
        } else {
            inputEnergyLevel = block.getHighEnergyLevel();
        }

        var energy = energyOffer.getEnergyAmount();
        if(energy.compareTo(inputEnergyLevel.toBig) > 0) {
            if(energyOffer.acceptOffer()) {
                if (world instanceof ServerWorld serverWorld) {
                    serverWorld.playSound(null, pos, SoundEvents.ENTITY_GENERIC_EXPLODE, SoundCategory.BLOCKS, 1f, 1f);
                    serverWorld.breakBlock(pos, false);
                    return;
                }
            }
        }
        energyContainer.receiveOffer(energyOffer);
    }

    @Override
    public @NotNull Set<Direction> getSupplyingDirections() {
        return new HashSet<>(Arrays.asList(supplyingDirections));
    }

    @Override
    public @NotNull EnergyOffer createOffer() {
        switch (mode) {
            case UP -> {
                var energy = block.getHighEnergyLevel().toBig;
                return new EnergyOffer(this, energy);
            }
            case DOWN -> {
                var energy = block.getLowEnergyLevel().toBig;
                return new EnergyOffer(this, energy);
            }
        }
        return new EnergyOffer(this, Energy.ZERO);
    }

    @Override
    public boolean supplyEnergy(@NotNull Energy energy) {
        return this.energyContainer.subtract(energy);
    }
}

