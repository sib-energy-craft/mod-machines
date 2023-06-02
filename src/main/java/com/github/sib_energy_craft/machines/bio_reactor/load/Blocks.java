package com.github.sib_energy_craft.machines.bio_reactor.load;

import com.github.sib_energy_craft.energy_api.Energy;
import com.github.sib_energy_craft.energy_api.utils.Identifiers;
import com.github.sib_energy_craft.machines.bio_reactor.block.BioReactorBlock;
import com.github.sib_energy_craft.sec_utils.common.Identified;
import com.github.sib_energy_craft.sec_utils.load.DefaultModInitializer;
import com.github.sib_energy_craft.sec_utils.utils.BlockUtils;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.MapColor;
import net.minecraft.sound.BlockSoundGroup;

/**
 * @since 0.0.23
 * @author sibmaks
 */
public final class Blocks implements DefaultModInitializer {
    public static final Identified<BioReactorBlock> BIO_REACTOR;

    static {
        var extractorSettings = AbstractBlock.Settings.create()
                .mapColor(MapColor.IRON_GRAY)
                .sounds(BlockSoundGroup.METAL)
                .strength(5, 6)
                .requiresTool();

        var bioReactorBlock = new BioReactorBlock(extractorSettings, 10000, 8000, 12, 6, Energy.of(16));
        BIO_REACTOR = BlockUtils.register(Identifiers.of("bio_reactor"), bioReactorBlock);
    }
}