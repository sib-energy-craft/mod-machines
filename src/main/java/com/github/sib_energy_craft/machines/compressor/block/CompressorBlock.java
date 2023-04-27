package com.github.sib_energy_craft.machines.compressor.block;

import com.github.sib_energy_craft.energy_api.EnergyLevel;
import com.github.sib_energy_craft.machines.compressor.block.entity.CompressorBlockEntity;
import com.github.sib_energy_craft.machines.compressor.load.Entities;
import com.github.sib_energy_craft.machines.compressor.load.Stats;
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
 * @author sibmaks
 * Created at 21-05-2022
 */
public class CompressorBlock extends AbstractCompressorBlock {
    public CompressorBlock(@NotNull Settings settings) {
        super(settings, EnergyLevel.L1, 800);
    }

    @NotNull
    @Override
    public BlockEntity createBlockEntity(@NotNull BlockPos pos,
                                         @NotNull BlockState state) {
        return new CompressorBlockEntity(pos, state, this);
    }

    @Override
    @Nullable
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(@NotNull World world,
                                                                  @NotNull BlockState state,
                                                                  @NotNull BlockEntityType<T> type) {
        return CompressorBlock.checkType(world, type, Entities.COMPRESSOR);
    }

    @Override
    protected void openScreen(@NotNull World world,
                              @NotNull BlockPos pos,
                              @NotNull PlayerEntity player) {
        var blockEntity = world.getBlockEntity(pos);
        if (blockEntity instanceof CompressorBlockEntity compressorBlockEntity) {
            player.openHandledScreen(compressorBlockEntity);
            player.incrementStat(Stats.INTERACT_WITH_COMPRESSOR);
        }
    }

    @Override
    public void randomDisplayTick(@NotNull BlockState state,
                                  @NotNull World world,
                                  @NotNull BlockPos pos,
                                  @NotNull Random random) {
        if (!state.get(WORKING)) {
            return;
        }
        // TODO: Nice to have here compressor sound
    }
}
