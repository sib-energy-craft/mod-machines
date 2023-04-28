package com.github.sib_energy_craft.energy_transformer.block.entity;


import com.github.sib_energy_craft.energy_transformer.block.AbstractEnergyTransformerBlock;
import com.github.sib_energy_craft.energy_transformer.load.Entities;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import org.jetbrains.annotations.NotNull;

/**
 * @since 0.0.4
 * @author sibmaks
 */
public class L2EnergyTransformerBlockEntity extends AbstractEnergyTransformerBlockEntity {
    public L2EnergyTransformerBlockEntity(@NotNull BlockPos pos,
                                          @NotNull BlockState state,
                                          @NotNull AbstractEnergyTransformerBlock block) {
        super(Entities.L2_ENERGY_TRANSFORMER, pos, state, block);
    }
}