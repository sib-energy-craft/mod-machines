package com.github.sib_energy_craft.machines.ore_purifying_machine.block;

import com.github.sib_energy_craft.energy_api.Energy;
import com.github.sib_energy_craft.energy_api.EnergyLevel;
import com.github.sib_energy_craft.machines.ore_purifying_machine.block.entity.OrePurifyingMachineBlockEntity;
import com.github.sib_energy_craft.machines.ore_purifying_machine.load.Entities;
import com.github.sib_energy_craft.machines.ore_purifying_machine.load.Stats;
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
 * @since 0.0.26
 * @author sibmaks
 */
public class OrePurifyingMachineBlock extends AbstractOrePurifyingMachineBlock {

    public OrePurifyingMachineBlock(@NotNull AbstractBlock.Settings settings) {
        super(settings, EnergyLevel.L2, 1024, Energy.of(10), 3000, 250, 15);
    }

    @Override
    public BlockEntity createBlockEntity(@NotNull BlockPos pos,
                                         @NotNull BlockState state) {
        return new OrePurifyingMachineBlockEntity(pos, state, this);
    }

    @Override
    @Nullable
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(@NotNull World world,
                                                                  @NotNull BlockState state,
                                                                  @NotNull BlockEntityType<T> type) {
        return OrePurifyingMachineBlock.checkType(world, type, Entities.ORE_PURIFYING_MACHINE);
    }

    @Override
    protected void openScreen(@NotNull World world,
                              @NotNull BlockPos blockPos,
                              @NotNull PlayerEntity playerEntity) {
        var blockEntity = world.getBlockEntity(blockPos);
        if (blockEntity instanceof OrePurifyingMachineBlockEntity orePurifyingMachineBlockEntity) {
            playerEntity.openHandledScreen(orePurifyingMachineBlockEntity);
            playerEntity.incrementStat(Stats.INTERACT_WITH_ORE_PURIFYING_MACHINE);
        }
    }

    @Override
    public void randomDisplayTick(BlockState state, World world, BlockPos pos, Random random) {
        if (!state.get(WORKING)) {
            return;
        }
        // TODO: make some sound and partials
    }
}
