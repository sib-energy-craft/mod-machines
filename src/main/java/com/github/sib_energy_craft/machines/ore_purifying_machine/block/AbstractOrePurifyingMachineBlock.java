package com.github.sib_energy_craft.machines.ore_purifying_machine.block;

import com.github.sib_energy_craft.energy_api.Energy;
import com.github.sib_energy_craft.energy_api.EnergyLevel;
import com.github.sib_energy_craft.machines.block.AbstractEnergyMachineBlock;
import com.github.sib_energy_craft.machines.ore_purifying_machine.block.entity.AbstractOrePurifyingMachineBlockEntity;
import lombok.Getter;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.HorizontalFacingBlock;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.DirectionProperty;
import net.minecraft.util.BlockMirror;
import net.minecraft.util.BlockRotation;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * @since 0.0.26
 * @author sibmaks
 */
public abstract class AbstractOrePurifyingMachineBlock extends AbstractEnergyMachineBlock {
    public static final DirectionProperty FACING = HorizontalFacingBlock.FACING;

    @Getter
    private final Energy energyPerTick;
    @Getter
    private final int maxDrumSpeed;
    @Getter
    private final int minimalWorkingDrumSpeed;
    @Getter
    private final int fullDrumSpeedCookBoost;

    protected AbstractOrePurifyingMachineBlock(@NotNull Settings settings,
                                               @NotNull EnergyLevel energyLevel,
                                               int maxCharge,
                                               @NotNull Energy energyPerTick,
                                               int maxDrumSpeed,
                                               int minimalWorkingDrumSpeed,
                                               int fullDrumSpeedCookBoost) {
        super(settings, energyLevel, maxCharge);
        this.energyPerTick = energyPerTick;
        this.maxDrumSpeed = maxDrumSpeed;
        this.minimalWorkingDrumSpeed = minimalWorkingDrumSpeed;
        this.fullDrumSpeedCookBoost = fullDrumSpeedCookBoost;
        this.setDefaultState(getDefaultState()
                .with(FACING, Direction.NORTH));
    }

    @NotNull
    @Override
    public BlockState getPlacementState(@NotNull ItemPlacementContext ctx) {
        var horizontalPlayerFacing = ctx.getHorizontalPlayerFacing();
        return this.getDefaultState().with(FACING, horizontalPlayerFacing.getOpposite());
    }

    @NotNull
    @Override
    public BlockState rotate(@NotNull BlockState state, @NotNull BlockRotation rotation) {
        return state.with(FACING, rotation.rotate(state.get(FACING)));
    }

    @NotNull
    @Override
    public BlockState mirror(@NotNull BlockState state, @NotNull BlockMirror mirror) {
        return state.rotate(mirror.getRotation(state.get(FACING)));
    }

    @Override
    protected void appendProperties(@NotNull StateManager.Builder<Block, BlockState> builder) {
        builder.add(FACING, WORKING);
    }

    @Nullable
    protected static <T extends BlockEntity,
            E extends AbstractOrePurifyingMachineBlockEntity<?>> BlockEntityTicker<T> checkType(
            @NotNull World world,
            @NotNull BlockEntityType<T> givenType,
            @NotNull BlockEntityType<E> expectedType) {
        return world.isClient ? null : AbstractOrePurifyingMachineBlock.checkType(givenType, expectedType,
                AbstractOrePurifyingMachineBlockEntity::simpleProcessingTick);
    }
}
