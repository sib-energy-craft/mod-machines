package com.github.sib_energy_craft.machines.load;

import com.github.sib_energy_craft.energy_api.utils.Identifiers;
import com.github.sib_energy_craft.sec_utils.common.Identified;
import com.github.sib_energy_craft.sec_utils.load.DefaultModInitializer;
import com.github.sib_energy_craft.sec_utils.utils.BlockUtils;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.Material;
import net.minecraft.sound.BlockSoundGroup;

/**
 * @since 0.0.1
 * @author sibmaks
 */
public final class Blocks implements DefaultModInitializer {

    public static final Identified<Block> BASE_MACHINE_BODY;
    public static final Identified<Block> ADVANCED_MACHINE_BODY;

    static {
        var baseMachineBodySettings = FabricBlockSettings.of(Material.METAL)
                .sounds(BlockSoundGroup.METAL)
                .strength(5, 6)
                .requiresTool();
        BASE_MACHINE_BODY = BlockUtils.register(Identifiers.of("base_machine_body"), baseMachineBodySettings);

        var advancedMachineBodySettings = FabricBlockSettings.of(Material.METAL)
                .sounds(BlockSoundGroup.METAL)
                .strength(50, 600)
                .requiresTool();
        ADVANCED_MACHINE_BODY = BlockUtils.register(Identifiers.of("advanced_machine_body"), advancedMachineBodySettings);
    }
}
