package com.github.sib_energy_craft.machines.compressor.load;

import com.github.sib_energy_craft.energy_api.utils.Identifiers;
import com.github.sib_energy_craft.machines.compressor.block.CompressorBlock;
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
    public static final Identified<CompressorBlock> COMPRESSOR;

    static {
        var compressorSettings = AbstractBlock.Settings.create()
                .mapColor(MapColor.IRON_GRAY)
                .sounds(BlockSoundGroup.METAL)
                .strength(5, 6)
                .requiresTool();

        var compressorBlock = new CompressorBlock(compressorSettings);
        COMPRESSOR = register(Identifiers.of("compressor"), compressorBlock);
    }
}