package com.github.sib_energy_craft.machines.bio_generator.block;

import com.github.sib_energy_craft.energy_api.Energy;
import com.github.sib_energy_craft.machines.bio_generator.block.entity.AbstractBioReactorBlockEntity;
import com.github.sib_energy_craft.machines.bio_generator.item.BioReactorItem;
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
import net.minecraft.state.property.EnumProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.*;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * @since 0.0.23
 * @author sibmaks
 */
public abstract class AbstractBioReactorBlock extends BlockWithEntity {
    public static final DirectionProperty FACING = HorizontalFacingBlock.FACING;
    public static final BooleanProperty LIT = Properties.LIT;
    public static final EnumProperty<BioReactorFilling> FILLING = EnumProperty.of("filling", BioReactorFilling.class);
    @Getter
    private final int maxFerments;
    @Getter
    private final int maxCharge;
    @Getter
    private final int maxEnergy;
    @Getter
    private final int ticksToFermentBreakdown;
    @Getter
    private final Energy energyPacketSize;

    protected AbstractBioReactorBlock(@NotNull Settings settings,
                                      int maxFerments,
                                      int maxCharge,
                                      int maxEnergy,
                                      int ticksToFermentBreakdown,
                                      @NotNull Energy energyPacketSize) {
        super(settings);
        this.maxFerments = maxFerments;
        this.maxCharge = maxCharge;
        this.maxEnergy = maxEnergy;
        this.ticksToFermentBreakdown = ticksToFermentBreakdown;
        this.energyPacketSize = energyPacketSize;
        this.setDefaultState(this.stateManager.getDefaultState()
                .with(FACING, Direction.NORTH)
                .with(LIT, false)
                .with(FILLING, BioReactorFilling.EMPTY)
        );
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
        if (!(world.getBlockEntity(pos) instanceof AbstractBioReactorBlockEntity<?> entity)) {
            return;
        }
        var item = itemStack.getItem();
        if(item instanceof BioReactorItem bioReactorItem) {
            entity.onPlaced(bioReactorItem.getCharge(itemStack), bioReactorItem.getFerments(itemStack));
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
        if (blockEntity instanceof AbstractBioReactorBlockEntity<?> generatorBlock) {
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
        builder.add(FACING, LIT, FILLING);
    }

    @Nullable
    protected static <
            T extends BlockEntity,
            E extends AbstractBioReactorBlockEntity<?>> BlockEntityTicker<T> checkType(@NotNull World world,
                                                                                    @NotNull BlockEntityType<T> givenType,
                                                                                    @NotNull BlockEntityType<E> expectedType) {
        return world.isClient ? null : AbstractBioReactorBlock.checkType(givenType, expectedType,
                AbstractBioReactorBlockEntity::tick);
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
            if (!(blockEntity instanceof AbstractBioReactorBlockEntity<?> abstractBioReactorBlockEntity)) {
                return;
            }
            var item = stackx.getItem();
            var charge = abstractBioReactorBlockEntity.getCharge();
            if (item instanceof BioReactorItem bioReactorItem) {
                bioReactorItem.charge(stackx, charge);
                bioReactorItem.saveFerments(stackx, abstractBioReactorBlockEntity.getFerments());
            }
        });
        state.onStacksDropped(serverWorld, pos, hand, true);
    }
}
