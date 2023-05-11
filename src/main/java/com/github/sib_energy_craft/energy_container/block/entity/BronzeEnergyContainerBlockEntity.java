package com.github.sib_energy_craft.energy_container.block.entity;

import com.github.sib_energy_craft.energy_container.block.BronzeEnergyContainerBlock;
import com.github.sib_energy_craft.energy_container.load.Entities;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import org.jetbrains.annotations.NotNull;

/**
 * @since 0.0.12
 * @author sibmaks
 */
public class BronzeEnergyContainerBlockEntity extends AbstractEnergyContainerBlockEntity {
    public BronzeEnergyContainerBlockEntity(@NotNull BlockPos pos,
                                            @NotNull BlockState state,
                                            @NotNull BronzeEnergyContainerBlock block) {
        super(Entities.BRONZE_ENERGY_CONTAINER, pos, state, block.getContainerNameCode(), block);
    }
}

