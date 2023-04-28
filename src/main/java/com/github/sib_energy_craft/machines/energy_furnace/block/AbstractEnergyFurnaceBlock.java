package com.github.sib_energy_craft.machines.energy_furnace.block;

import com.github.sib_energy_craft.energy_api.EnergyLevel;
import com.github.sib_energy_craft.machines.block.AbstractEnergyMachineBlock;
import com.github.sib_energy_craft.machines.energy_furnace.block.entity.AbstractEnergyFurnaceBlockEntity;
import lombok.Getter;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.HorizontalFacingBlock;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.DirectionProperty;
import net.minecraft.util.BlockMirror;
import net.minecraft.util.BlockRotation;
import net.minecraft.util.ItemScatterer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * @since 0.0.2
 * @author sibmaks
 */
public abstract class AbstractEnergyFurnaceBlock extends AbstractEnergyMachineBlock {
    public static final DirectionProperty FACING = HorizontalFacingBlock.FACING;

    @Getter
    private final double cookingTotalTimeMultiplier;
    @Getter
    private final int maxCharge;

    protected AbstractEnergyFurnaceBlock(@NotNull Settings settings,
                                         @NotNull EnergyLevel energyLevel,
                                         double cookingTotalTimeMultiplier,
                                         int maxCharge) {
        super(settings, energyLevel, maxCharge);
        this.cookingTotalTimeMultiplier = cookingTotalTimeMultiplier;
        this.maxCharge = maxCharge;
        this.setDefaultState(getDefaultState().with(FACING, Direction.NORTH));
    }

    @NotNull
    @Override
    public BlockState getPlacementState(@NotNull ItemPlacementContext ctx) {
        var horizontalPlayerFacing = ctx.getHorizontalPlayerFacing();
        return this.getDefaultState().with(FACING, horizontalPlayerFacing.getOpposite());
    }

    @Override
    public void onStateReplaced(@NotNull BlockState state,
                                @NotNull World world,
                                @NotNull BlockPos pos,
                                @NotNull BlockState newState,
                                boolean moved) {
        if (state.isOf(newState.getBlock())) {
            return;
        }
        var blockEntity = world.getBlockEntity(pos);
        if (blockEntity instanceof AbstractEnergyFurnaceBlockEntity energyFurnaceBlockEntity) {
            if (world instanceof ServerWorld serverWorld) {
                ItemScatterer.spawn(world, pos, energyFurnaceBlockEntity);
                energyFurnaceBlockEntity.getRecipesUsedAndDropExperience(serverWorld, Vec3d.ofCenter(pos));
            }
            world.updateComparators(pos, this);
        }
        super.onStateReplaced(state, world, pos, newState, moved);
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
    protected static <T extends BlockEntity, E extends AbstractEnergyFurnaceBlockEntity> BlockEntityTicker<T> checkType(
            @NotNull World world,
            @NotNull BlockEntityType<T> givenType,
            @NotNull BlockEntityType<E> expectedType) {
        return world.isClient ? null : AbstractEnergyFurnaceBlock.checkType(givenType, expectedType,
                AbstractEnergyFurnaceBlockEntity::tick);
    }

}
