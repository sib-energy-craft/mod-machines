package com.github.sib_energy_craft.machines.generator.block;

import com.github.sib_energy_craft.machines.generator.block.entity.EnergyGeneratorBlockEntity;
import com.github.sib_energy_craft.machines.generator.load.Entities;
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
 * @since 0.0.1
 * @author sibmaks
 */
public class EnergyGeneratorBlock extends AbstractEnergyGeneratorBlock {

    public EnergyGeneratorBlock(@NotNull Settings settings,
                                int fuelToEnergyCoefficient,
                                int maxCharge,
                                int energyPacketSize) {
        super(settings, fuelToEnergyCoefficient, maxCharge, energyPacketSize);
    }

    @Override
    public @NotNull BlockEntity createBlockEntity(@NotNull BlockPos pos,
                                                  @NotNull BlockState state) {
        return new EnergyGeneratorBlockEntity(pos, state, this);
    }

    @Override
    @Nullable
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(@NotNull World world,
                                                                  @NotNull BlockState state,
                                                                  @NotNull BlockEntityType<T> type) {
        return EnergyGeneratorBlock.checkType(world, type, Entities.ENERGY_GENERATOR);
    }

    @Override
    protected void openScreen(@NotNull World world,
                              @NotNull BlockPos blockPos,
                              @NotNull PlayerEntity playerEntity) {
        var blockEntity = world.getBlockEntity(blockPos);
        if (blockEntity instanceof EnergyGeneratorBlockEntity energyGeneratorBlock) {
            playerEntity.openHandledScreen(energyGeneratorBlock);
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
        double d = (double)pos.getX() + 0.5;
        double e = pos.getY();
        double f = (double)pos.getZ() + 0.5;
        if (random.nextDouble() < 0.1) {
            world.playSound(d, e, f, SoundEvents.BLOCK_FURNACE_FIRE_CRACKLE, SoundCategory.BLOCKS, 1.0f, 1.0f, false);
        }
        var direction = state.get(FACING);
        var axis = direction.getAxis();
        double h = random.nextDouble() * 0.6 - 0.3;
        double i = axis == Direction.Axis.X ? (double)direction.getOffsetX() * 0.52 : h;
        double j = random.nextDouble() * 6.0 / 16.0;
        double k = axis == Direction.Axis.Z ? (double)direction.getOffsetZ() * 0.52 : h;
        world.addParticle(ParticleTypes.SMOKE, d + i, e + j, f + k, 0.0, 0.0, 0.0);
        world.addParticle(ParticleTypes.FLAME, d + i, e + j, f + k, 0.0, 0.0, 0.0);
    }
}
