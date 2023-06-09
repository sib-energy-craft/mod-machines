package com.github.sib_energy_craft.energy_transformer.load;

import com.github.sib_energy_craft.energy_transformer.block.entity.L1EnergyTransformerBlockEntity;
import com.github.sib_energy_craft.energy_transformer.block.entity.L2EnergyTransformerBlockEntity;
import com.github.sib_energy_craft.energy_transformer.block.entity.L3EnergyTransformerBlockEntity;
import com.github.sib_energy_craft.sec_utils.load.DefaultModInitializer;
import net.minecraft.block.entity.BlockEntityType;

import static com.github.sib_energy_craft.sec_utils.utils.EntityUtils.register;

/**
 * @since 0.0.4
 * @author sibmaks
 */
public final class Entities implements DefaultModInitializer {
    public static final BlockEntityType<L1EnergyTransformerBlockEntity> L1_ENERGY_TRANSFORMER;
    public static final BlockEntityType<L2EnergyTransformerBlockEntity> L2_ENERGY_TRANSFORMER;
    public static final BlockEntityType<L3EnergyTransformerBlockEntity> L3_ENERGY_TRANSFORMER;

    static {
        L1_ENERGY_TRANSFORMER = register(Blocks.L1_ENERGY_TRANSFORMER, L1EnergyTransformerBlockEntity::new);
        L2_ENERGY_TRANSFORMER = register(Blocks.L2_ENERGY_TRANSFORMER, L2EnergyTransformerBlockEntity::new);
        L3_ENERGY_TRANSFORMER = register(Blocks.L3_ENERGY_TRANSFORMER, L3EnergyTransformerBlockEntity::new);
    }
}
