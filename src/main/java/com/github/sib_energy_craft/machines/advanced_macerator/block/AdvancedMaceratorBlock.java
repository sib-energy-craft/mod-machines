package com.github.sib_energy_craft.machines.advanced_macerator.block;

import com.github.sib_energy_craft.energy_api.EnergyLevel;
import com.github.sib_energy_craft.machines.advanced_macerator.block.entity.AdvancedMaceratorBlockEntity;
import com.github.sib_energy_craft.machines.advanced_macerator.load.Entities;
import com.github.sib_energy_craft.machines.macerator.block.AbstractMaceratorBlock;
import com.github.sib_energy_craft.machines.macerator.load.Stats;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * @since 0.0.17
 * @author sibmaks
 */
public class AdvancedMaceratorBlock extends AbstractMaceratorBlock {

    public AdvancedMaceratorBlock(@NotNull AbstractBlock.Settings settings) {
        super(settings, EnergyLevel.L3,  1024);
    }

    @Override
    public BlockEntity createBlockEntity(@NotNull BlockPos pos,
                                         @NotNull BlockState state) {
        return new AdvancedMaceratorBlockEntity(pos, state, this);
    }

    @Override
    @Nullable
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(@NotNull World world,
                                                                  @NotNull BlockState state,
                                                                  @NotNull BlockEntityType<T> type) {
        return AdvancedMaceratorBlock.checkType(world, type, Entities.ADVANCED_MACERATOR);
    }

    @Override
    protected void openScreen(@NotNull World world,
                              @NotNull BlockPos blockPos,
                              @NotNull PlayerEntity playerEntity) {
        var blockEntity = world.getBlockEntity(blockPos);
        if (blockEntity instanceof AdvancedMaceratorBlockEntity AdvancedMaceratorBlockEntity) {
            playerEntity.openHandledScreen(AdvancedMaceratorBlockEntity);
            playerEntity.incrementStat(Stats.INTERACT_WITH_MACERATOR);
        }
    }

    @Override
    public void randomDisplayTick(BlockState state, World world, BlockPos pos, Random random) {
        if (!state.get(WORKING)) {
            return;
        }
    }
}
