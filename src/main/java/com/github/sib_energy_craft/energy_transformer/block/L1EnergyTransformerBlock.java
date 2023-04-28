package com.github.sib_energy_craft.energy_transformer.block;

import com.github.sib_energy_craft.energy_api.EnergyLevel;
import com.github.sib_energy_craft.energy_transformer.block.entity.L1EnergyTransformerBlockEntity;
import com.github.sib_energy_craft.energy_transformer.load.Entities;
import lombok.Builder;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * @since 0.0.4
 * @author sibmaks
 */
public class L1EnergyTransformerBlock extends AbstractEnergyTransformerBlock {

    @Builder
    public L1EnergyTransformerBlock(@NotNull Settings settings,
                                    @NotNull String containerNameCode,
                                    @NotNull EnergyLevel lowEnergyLevel,
                                    @NotNull EnergyLevel highEnergyLevel) {
        super(settings, containerNameCode, lowEnergyLevel, highEnergyLevel);
    }

    @Override
    public @NotNull BlockEntity createBlockEntity(@NotNull BlockPos pos, @NotNull BlockState state) {
        return new L1EnergyTransformerBlockEntity(pos, state, this);
    }

    @Override
    @Nullable
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(@NotNull World world,
                                                                  @NotNull BlockState state,
                                                                  @NotNull BlockEntityType<T> type) {
        return L1EnergyTransformerBlock.checkType(world, type, Entities.L1_ENERGY_TRANSFORMER);
    }
}
