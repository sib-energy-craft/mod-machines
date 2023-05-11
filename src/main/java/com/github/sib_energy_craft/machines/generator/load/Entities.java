package com.github.sib_energy_craft.machines.generator.load;

import com.github.sib_energy_craft.machines.generator.block.entity.EnergyGeneratorBlockEntity;
import com.github.sib_energy_craft.sec_utils.load.DefaultModInitializer;
import com.github.sib_energy_craft.sec_utils.utils.EntityUtils;
import net.minecraft.block.entity.BlockEntityType;

/**
 * @since 0.0.1
 * @author sibmaks
 */
public final class Entities implements DefaultModInitializer {
    public static final BlockEntityType<EnergyGeneratorBlockEntity> ENERGY_GENERATOR;

    static {
        ENERGY_GENERATOR = EntityUtils.register(Blocks.ENERGY_GENERATOR, EnergyGeneratorBlockEntity::new);
    }
}
