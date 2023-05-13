package com.github.sib_energy_craft.machines.generator.block;

import com.github.sib_energy_craft.energy_api.Energy;
import com.github.sib_energy_craft.energy_api.items.ChargeableItem;
import com.github.sib_energy_craft.machines.generator.block.entity.AbstractEnergyGeneratorBlockEntity;
import lombok.Getter;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.stat.Stats;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.DirectionProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.*;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * @since 0.0.1
 * @author sibmaks
 */
public abstract class AbstractEnergyGeneratorBlock extends BlockWithEntity {
    public static final DirectionProperty FACING = HorizontalFacingBlock.FACING;
    public static final BooleanProperty LIT = Properties.LIT;
    @Getter
    private final int fuelToEnergyCoefficient;
    @Getter
    private final int maxCharge;
    @Getter
    private final Energy energyPacketSize;

    protected AbstractEnergyGeneratorBlock(@NotNull Settings settings,
                                           int fuelToEnergyCoefficient,
                                           int maxCharge,
                                           int energyPacketSize) {
        super(settings);
        this.fuelToEnergyCoefficient = fuelToEnergyCoefficient;
        this.maxCharge = maxCharge;
        this.energyPacketSize = Energy.of(energyPacketSize);
        this.setDefaultState(this.stateManager.getDefaultState().with(FACING, Direction.NORTH).with(LIT, false));
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
        this.openScreen(world, pos, player);
        return ActionResult.CONSUME;
    }

    protected abstract void openScreen(@NotNull World world,
                                       @NotNull BlockPos blockPos,
                                       @NotNull PlayerEntity playerEntity);

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
        if (!(world.getBlockEntity(pos) instanceof AbstractEnergyGeneratorBlockEntity entity)) {
            return;
        }
        if(itemStack.hasCustomName()) {
            entity.setCustomName(itemStack.getName());
        }
        var item = itemStack.getItem();
        if(item instanceof ChargeableItem chargeableItem) {
            entity.onPlaced(chargeableItem.getCharge(itemStack));
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
        if (blockEntity instanceof AbstractEnergyGeneratorBlockEntity generatorBlock) {
            if (world instanceof ServerWorld) {
                ItemScatterer.spawn(world, pos, generatorBlock);
            }
            world.updateComparators(pos, this);
        }
        super.onStateReplaced(state, world, pos, newState, moved);
    }

    @Override
    public boolean hasComparatorOutput(@NotNull BlockState state) {
        return true;
    }

    @Override
    public int getComparatorOutput(@NotNull BlockState state,
                                   @NotNull World world,
                                   @NotNull BlockPos pos) {
        return ScreenHandler.calculateComparatorOutput(world.getBlockEntity(pos));
    }

    @Override
    public @NotNull BlockRenderType getRenderType(@NotNull BlockState state) {
        return BlockRenderType.MODEL;
    }

    @Override
    public @NotNull BlockState rotate(@NotNull BlockState state,
                                      @NotNull BlockRotation rotation) {
        return state.with(FACING, rotation.rotate(state.get(FACING)));
    }

    @Override
    public @NotNull BlockState mirror(@NotNull BlockState state, @NotNull BlockMirror mirror) {
        return state.rotate(mirror.getRotation(state.get(FACING)));
    }

    @Override
    protected void appendProperties(@NotNull StateManager.Builder<Block, BlockState> builder) {
        builder.add(FACING, LIT);
    }

    @Nullable
    protected static <
            T extends BlockEntity,
            E extends AbstractEnergyGeneratorBlockEntity> BlockEntityTicker<T> checkType(@NotNull World world,
                                                                                         @NotNull BlockEntityType<T> givenType,
                                                                                         @NotNull BlockEntityType<E> expectedType) {
        return world.isClient ? null : AbstractEnergyGeneratorBlock.checkType(givenType, expectedType,
                AbstractEnergyGeneratorBlockEntity::tick);
    }

    @Override
    public void afterBreak(@NotNull World world,
                           @NotNull PlayerEntity player,
                           @NotNull BlockPos pos,
                           @NotNull BlockState state,
                           @Nullable BlockEntity blockEntity,
                           @NotNull ItemStack hand) {
        player.incrementStat(Stats.MINED.getOrCreateStat(this));
        player.addExhaustion(0.005F);
        if (!(world instanceof ServerWorld serverWorld)) {
            return;
        }
        getDroppedStacks(state, serverWorld, pos, blockEntity, player, hand).forEach((stackx) -> {
            dropStack(world, pos, stackx);
            if (!(blockEntity instanceof AbstractEnergyGeneratorBlockEntity energyGeneratorBlockEntity)) {
                return;
            }
            if (energyGeneratorBlockEntity.hasCustomName()) {
                stackx.setCustomName(energyGeneratorBlockEntity.getCustomName());
            }
            var item = stackx.getItem();
            var charge = energyGeneratorBlockEntity.getCharge();
            if (item instanceof ChargeableItem chargeableItem) {
                chargeableItem.charge(stackx, charge);
            }
        });
        state.onStacksDropped(serverWorld, pos, hand, true);
    }
}
