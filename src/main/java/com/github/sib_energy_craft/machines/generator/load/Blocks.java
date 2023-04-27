package com.github.sib_energy_craft.machines.generator.load;

import com.github.sib_energy_craft.energy_api.utils.Identifiers;
import com.github.sib_energy_craft.machines.generator.block.EnergyGeneratorBlock;
import com.github.sib_energy_craft.sec_utils.common.Identified;
import com.github.sib_energy_craft.sec_utils.load.ModRegistrar;
import com.github.sib_energy_craft.sec_utils.utils.BlockUtils;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Material;
import net.minecraft.sound.BlockSoundGroup;

/**
 * @since 0.0.1
 * @author sibmaks
 */
public final class Blocks implements ModRegistrar {
    public static final Identified<EnergyGeneratorBlock> ENERGY_GENERATOR;

    static {
        var extractorSettings = FabricBlockSettings.of(Material.METAL)
                .sounds(BlockSoundGroup.METAL)
                .strength(5, 6)
                .requiresTool();

        var energyGeneratorBlock = new EnergyGeneratorBlock(extractorSettings, 8, 8000, 10);
        ENERGY_GENERATOR = BlockUtils.register(Identifiers.of("energy_generator"), energyGeneratorBlock);
    }
}