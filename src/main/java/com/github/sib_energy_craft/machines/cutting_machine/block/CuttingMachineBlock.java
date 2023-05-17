package com.github.sib_energy_craft.machines.cutting_machine.block;

import com.github.sib_energy_craft.energy_api.EnergyLevel;
import com.github.sib_energy_craft.machines.cutting_machine.block.entity.CuttingMachineBlockEntity;
import com.github.sib_energy_craft.machines.cutting_machine.load.Entities;
import com.github.sib_energy_craft.machines.cutting_machine.load.Stats;
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
public class CuttingMachineBlock extends AbstractCuttingMachineBlock {
    public CuttingMachineBlock(@NotNull AbstractBlock.Settings settings) {
        super(settings, EnergyLevel.L1, 800, 100);
    }

    @NotNull
    @Override
    public BlockEntity createBlockEntity(@NotNull BlockPos pos,
                                         @NotNull BlockState state) {
        return new CuttingMachineBlockEntity(pos, state, this);
    }

    @Override
    @Nullable
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(@NotNull World world,
                                                                  @NotNull BlockState state,
                                                                  @NotNull BlockEntityType<T> type) {
        return CuttingMachineBlock.checkType(world, type, Entities.CUTTING_MACHINE);
    }

    @Override
    protected void openScreen(@NotNull World world,
                              @NotNull BlockPos blockPos,
                              @NotNull PlayerEntity playerEntity) {
        var blockEntity = world.getBlockEntity(blockPos);
        if (blockEntity instanceof CuttingMachineBlockEntity cuttingMachineBlockEntity) {
            playerEntity.openHandledScreen(cuttingMachineBlockEntity);
            playerEntity.incrementStat(Stats.INTERACT_WITH_CUTTING_MACHINE);
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
        // TODO: Nice to have here cutting sound
    }
}
