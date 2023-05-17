package com.github.sib_energy_craft.machines.induction_furnace.block;

import com.github.sib_energy_craft.energy_api.EnergyLevel;
import com.github.sib_energy_craft.machines.energy_furnace.block.AbstractEnergyFurnaceBlock;
import com.github.sib_energy_craft.machines.induction_furnace.block.entity.InductionFurnaceBlockEntity;
import com.github.sib_energy_craft.machines.induction_furnace.load.Entities;
import com.github.sib_energy_craft.machines.induction_furnace.load.Stats;
import lombok.Getter;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * @since 0.0.17
 * @author sibmaks
 */
public class InductionFurnaceBlock extends AbstractEnergyFurnaceBlock {

    @Getter
    protected final int maxHeatTicks;
    @Getter
    protected final int heatCookSpeedMultiplier;

    public InductionFurnaceBlock(@NotNull AbstractBlock.Settings settings) {
        super(settings, EnergyLevel.L2, 1.5, 1024);
        this.maxHeatTicks = 1000;
        this.heatCookSpeedMultiplier = 4;
    }

    @Override
    public BlockEntity createBlockEntity(@NotNull BlockPos pos,
                                         @NotNull BlockState state) {
        return new InductionFurnaceBlockEntity(pos, state, this);
    }

    @Override
    @Nullable
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(@NotNull World world,
                                                                  @NotNull BlockState state,
                                                                  @NotNull BlockEntityType<T> type) {
        return InductionFurnaceBlock.checkType(world, type, Entities.INDUCTION_FURNACE);
    }

    @Override
    protected void openScreen(@NotNull World world,
                              @NotNull BlockPos blockPos,
                              @NotNull PlayerEntity playerEntity) {
        var blockEntity = world.getBlockEntity(blockPos);
        if (blockEntity instanceof InductionFurnaceBlockEntity inductionFurnaceBlockEntity) {
            playerEntity.openHandledScreen(inductionFurnaceBlockEntity);
            playerEntity.incrementStat(Stats.INTERACT_WITH_INDUCTION_FURNACE);
        }
    }

    @Override
    public void randomDisplayTick(BlockState state, World world, BlockPos pos, Random random) {
        if (!state.get(WORKING)) {
            return;
        }
        double d = pos.getX() + 0.5;
        double e = pos.getY();
        double f = pos.getZ() + 0.5;
        if (random.nextDouble() < 0.1) {
            world.playSound(d, e, f, SoundEvents.BLOCK_FURNACE_FIRE_CRACKLE, SoundCategory.BLOCKS, 1.0f, 1.0f, false);
        }
        var direction = state.get(FACING);
        var axis = direction.getAxis();
        double h = random.nextDouble() * 0.6 - 0.3;
        double i = axis == Direction.Axis.X ? direction.getOffsetX() * 0.52 : h;
        double j = random.nextDouble() * 6.0 / 16.0;
        double k = axis == Direction.Axis.Z ? direction.getOffsetZ() * 0.52 : h;
        world.addParticle(ParticleTypes.SMOKE, d + i, e + j, f + k, 0.0, 0.0, 0.0);
    }
}
