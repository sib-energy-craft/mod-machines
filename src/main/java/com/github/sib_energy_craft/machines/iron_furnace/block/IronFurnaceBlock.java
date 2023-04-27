package com.github.sib_energy_craft.machines.iron_furnace.block;

import com.github.sib_energy_craft.machines.iron_furnace.block.entity.IronFurnaceBlockEntity;
import com.github.sib_energy_craft.machines.iron_furnace.load.Entities;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.stat.Stats;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * @since 0.0.1
 * @author sibmaks
 */
public class IronFurnaceBlock extends AbstractIronFurnaceBlock {
    public IronFurnaceBlock(@NotNull Settings settings) {
        super(settings, 0.8, 1.25);
    }

    @NotNull
    @Override
    public BlockEntity createBlockEntity(@NotNull BlockPos pos,
                                         @NotNull BlockState state) {
        return new IronFurnaceBlockEntity(pos, state, this);
    }

    @Override
    @Nullable
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(@NotNull World world,
                                                                  @NotNull BlockState state,
                                                                  @NotNull BlockEntityType<T> type) {
        return IronFurnaceBlock.checkType(world, type, Entities.IRON_FURNACE);
    }

    @Override
    protected void openScreen(@NotNull World world,
                              @NotNull BlockPos pos,
                              @NotNull PlayerEntity player) {
        var blockEntity = world.getBlockEntity(pos);
        if (blockEntity instanceof IronFurnaceBlockEntity ironFurnaceBlock) {
            player.openHandledScreen(ironFurnaceBlock);
            player.incrementStat(Stats.INTERACT_WITH_FURNACE);
        }
    }

    @Override
    public void randomDisplayTick(@NotNull BlockState state,
                                  @NotNull World world,
                                  @NotNull BlockPos pos,
                                  @NotNull Random random) {
        if (!state.get(LIT)) {
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
        world.addParticle(ParticleTypes.FLAME, d + i, e + j, f + k, 0.0, 0.0, 0.0);
    }
}
