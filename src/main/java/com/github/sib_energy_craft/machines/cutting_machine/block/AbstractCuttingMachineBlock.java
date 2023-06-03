package com.github.sib_energy_craft.machines.cutting_machine.block;

import com.github.sib_energy_craft.energy_api.EnergyLevel;
import com.github.sib_energy_craft.machines.block.AbstractEnergyMachineBlock;
import com.github.sib_energy_craft.machines.block.entity.AbstractEnergyMachineBlockEntity;
import com.github.sib_energy_craft.machines.cutting_machine.block.entity.AbstractCuttingMachineBlockEntity;
import lombok.Getter;
import net.minecraft.block.AbstractBlock;
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
 * @since 0.0.17
 * @author sibmaks
 */
public abstract class AbstractCuttingMachineBlock extends AbstractEnergyMachineBlock {
    public static final DirectionProperty FACING = HorizontalFacingBlock.FACING;

    @Getter
    protected final int cookTimeTotal;

    protected AbstractCuttingMachineBlock(@NotNull AbstractBlock.Settings settings,
                                          @NotNull EnergyLevel energyLevel,
                                          int maxCharge,
                                          int cookTimeTotal) {
        super(settings, energyLevel, maxCharge);
        this.setDefaultState(getDefaultState().with(FACING, Direction.NORTH));
        this.cookTimeTotal = cookTimeTotal;
    }

    @NotNull
    @Override
    public BlockState getPlacementState(@NotNull ItemPlacementContext ctx) {
        var horizontalPlayerFacing = ctx.getHorizontalPlayerFacing();
        return this.getDefaultState().with(FACING, horizontalPlayerFacing.getOpposite());
    }

    @NotNull
    @Override
    public BlockState rotate(@NotNull BlockState state,
                             @NotNull BlockRotation rotation) {
        return state.with(FACING, rotation.rotate(state.get(FACING)));
    }

    @NotNull
    @Override
    public BlockState mirror(@NotNull BlockState state,
                             @NotNull BlockMirror mirror) {
        return state.rotate(mirror.getRotation(state.get(FACING)));
    }

    @Override
    protected void appendProperties(@NotNull StateManager.Builder<Block, BlockState> builder) {
        super.appendProperties(builder);
        builder.add(FACING);
    }

    @Nullable
    protected static <T extends BlockEntity, E extends AbstractCuttingMachineBlockEntity<?>> BlockEntityTicker<T> checkType(
            @NotNull World world,
            @NotNull BlockEntityType<T> givenType,
            @NotNull BlockEntityType<E> expectedType) {
        return world.isClient ? null : AbstractCuttingMachineBlock.checkType(givenType, expectedType,
                AbstractEnergyMachineBlockEntity::simpleProcessingTick);
    }
}
