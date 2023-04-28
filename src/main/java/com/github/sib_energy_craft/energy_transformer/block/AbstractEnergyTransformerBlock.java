package com.github.sib_energy_craft.energy_transformer.block;

import com.github.sib_energy_craft.energy_api.EnergyLevel;
import com.github.sib_energy_craft.energy_api.items.ChargeableItem;
import com.github.sib_energy_craft.energy_transformer.block.entity.AbstractEnergyTransformerBlockEntity;
import lombok.Getter;
import net.minecraft.block.Block;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.block.BlockWithEntity;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.NamedScreenHandlerFactory;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.DirectionProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.ActionResult;
import net.minecraft.util.BlockMirror;
import net.minecraft.util.BlockRotation;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * @since 0.0.4
 * @author sibmaks
 */
public abstract class AbstractEnergyTransformerBlock extends BlockWithEntity {
    public static final DirectionProperty FACING = Properties.FACING;
    @Getter
    private final String containerNameCode;
    @Getter
    private final EnergyLevel lowEnergyLevel;
    @Getter
    private final EnergyLevel highEnergyLevel;

    protected AbstractEnergyTransformerBlock(@NotNull Settings settings,
                                             @NotNull String containerNameCode,
                                             @NotNull EnergyLevel lowEnergyLevel,
                                             @NotNull EnergyLevel highEnergyLevel) {
        super(settings);
        this.containerNameCode = containerNameCode;
        this.lowEnergyLevel = lowEnergyLevel;
        this.highEnergyLevel = highEnergyLevel;
        this.setDefaultState(this.stateManager.getDefaultState().with(FACING, Direction.UP));
    }

    @Override
    public @NotNull ActionResult onUse(@NotNull BlockState state,
                                       @NotNull World world,
                                       @NotNull BlockPos pos,
                                       @NotNull PlayerEntity player,
                                       @NotNull Hand hand,
                                       @NotNull BlockHitResult hit) {
        if (world.isClient) {
            return ActionResult.SUCCESS;
        }
        var screenHandlerFactory = state.createScreenHandlerFactory(world, pos);
        player.openHandledScreen(screenHandlerFactory);
        return ActionResult.CONSUME;
    }

    @Override
    public @NotNull BlockState getPlacementState(@NotNull ItemPlacementContext ctx) {
        var horizontalPlayerFacing = ctx.getHorizontalPlayerFacing();
        return this.getDefaultState().with(FACING, horizontalPlayerFacing.getOpposite());
    }

    @Override
    public void onPlaced(@NotNull World world,
                         @NotNull BlockPos pos,
                         @NotNull BlockState state,
                         @Nullable LivingEntity placer,
                         @NotNull ItemStack itemStack) {
        var blockEntity = world.getBlockEntity(pos);
        if (!(blockEntity instanceof AbstractEnergyTransformerBlockEntity energyTransformerBlockEntity)) {
            return;
        }
        if (itemStack.hasCustomName()) {
            energyTransformerBlockEntity.setDisplayName(itemStack.getName());
        }
        var item = itemStack.getItem();
        if(item instanceof ChargeableItem chargeableItem) {
            int charge = chargeableItem.getCharge(itemStack);
            energyTransformerBlockEntity.onPlaced(charge);
        }
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
        if (blockEntity instanceof AbstractEnergyTransformerBlockEntity) {
            world.updateComparators(pos, this);
        }
        super.onStateReplaced(state, world, pos, newState, moved);
    }

    @Override
    public boolean hasComparatorOutput(@NotNull BlockState state) {
        return false;
    }

    @Override
    public int getComparatorOutput(@NotNull BlockState state,
                                   @NotNull World world,
                                   @NotNull BlockPos pos) {
        return 0;
    }

    @Override
    public @NotNull BlockRenderType getRenderType(@NotNull BlockState state) {
        return BlockRenderType.MODEL;
    }

    @Override
    public @NotNull BlockState rotate(@NotNull BlockState state, @NotNull BlockRotation rotation) {
        return state.with(FACING, rotation.rotate(state.get(FACING)));
    }

    @Override
    public @NotNull BlockState mirror(@NotNull BlockState state, @NotNull BlockMirror mirror) {
        return state.rotate(mirror.getRotation(state.get(FACING)));
    }

    @Override
    protected void appendProperties(@NotNull StateManager.Builder<Block, BlockState> builder) {
        builder.add(FACING);
    }

    @Nullable
    protected static <T extends BlockEntity, E extends AbstractEnergyTransformerBlockEntity> BlockEntityTicker<T> checkType(
            @NotNull World world,
            @NotNull BlockEntityType<T> givenType,
            @NotNull BlockEntityType<E> expectedType) {
        return world.isClient ? null : AbstractEnergyTransformerBlock.checkType(givenType, expectedType,
                AbstractEnergyTransformerBlockEntity::tick);
    }

    @Override
    @Nullable
    public NamedScreenHandlerFactory createScreenHandlerFactory(@NotNull BlockState state,
                                                                @NotNull World world,
                                                                @NotNull BlockPos pos) {
        var blockEntity = world.getBlockEntity(pos);
        if (blockEntity instanceof AbstractEnergyTransformerBlockEntity transformerBlockEntity) {
            return transformerBlockEntity;
        }
        return null;
    }
}