package com.github.sib_energy_craft.machines.load;

import com.github.sib_energy_craft.energy_api.utils.Identifiers;
import com.github.sib_energy_craft.sec_utils.common.Identified;
import com.github.sib_energy_craft.sec_utils.load.DefaultModInitializer;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.MapColor;
import net.minecraft.sound.BlockSoundGroup;

import static com.github.sib_energy_craft.sec_utils.utils.BlockUtils.register;

/**
 * @since 0.0.1
 * @author sibmaks
 */
public final class Blocks implements DefaultModInitializer {

    public static final Identified<Block> BASE_MACHINE_BODY;
    public static final Identified<Block> ADVANCED_MACHINE_BODY;

    static {
        var baseMachineBodySettings = AbstractBlock.Settings.create()
                .mapColor(MapColor.IRON_GRAY)
                .sounds(BlockSoundGroup.METAL)
                .strength(5, 6)
                .requiresTool();
        BASE_MACHINE_BODY = register(Identifiers.of("base_machine_body"), baseMachineBodySettings);

        var advancedMachineBodySettings = AbstractBlock.Settings.create()
                .mapColor(MapColor.IRON_GRAY)
                .sounds(BlockSoundGroup.METAL)
                .strength(50, 600)
                .requiresTool();
        ADVANCED_MACHINE_BODY = register(Identifiers.of("advanced_machine_body"), advancedMachineBodySettings);
    }
}
