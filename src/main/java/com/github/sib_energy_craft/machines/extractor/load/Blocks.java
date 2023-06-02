package com.github.sib_energy_craft.machines.extractor.load;

import com.github.sib_energy_craft.energy_api.utils.Identifiers;
import com.github.sib_energy_craft.machines.extractor.block.ExtractorBlock;
import com.github.sib_energy_craft.sec_utils.common.Identified;
import com.github.sib_energy_craft.sec_utils.load.DefaultModInitializer;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.MapColor;
import net.minecraft.sound.BlockSoundGroup;

import static com.github.sib_energy_craft.sec_utils.utils.BlockUtils.register;

/**
 * @since 0.0.1
 * @author sibmaks
 */
public final class Blocks implements DefaultModInitializer {
    public static final Identified<ExtractorBlock> EXTRACTOR;

    static {
        var extractorSettings = AbstractBlock.Settings.create()
                .mapColor(MapColor.IRON_GRAY)
                .sounds(BlockSoundGroup.METAL)
                .strength(5, 6)
                .requiresTool();

        var extractorBlock = new ExtractorBlock(extractorSettings);
        EXTRACTOR = register(Identifiers.of("extractor"), extractorBlock);
    }
}