package com.github.sib_energy_craft.energy_transformer.block.entity;


import com.github.sib_energy_craft.energy_transformer.block.AbstractEnergyTransformerBlock;
import com.github.sib_energy_craft.energy_transformer.load.Entities;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import org.jetbrains.annotations.NotNull;

/**
 * @since 0.0.6
 * @author sibmaks
 */
public class L3EnergyTransformerBlockEntity extends AbstractEnergyTransformerBlockEntity {
    public L3EnergyTransformerBlockEntity(@NotNull BlockPos pos,
                                          @NotNull BlockState state,
                                          @NotNull AbstractEnergyTransformerBlock block) {
        super(Entities.L3_ENERGY_TRANSFORMER, pos, state, block);
    }
}