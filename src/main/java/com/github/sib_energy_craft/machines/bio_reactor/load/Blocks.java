package com.github.sib_energy_craft.machines.bio_reactor.load;

import com.github.sib_energy_craft.energy_api.Energy;
import com.github.sib_energy_craft.energy_api.utils.Identifiers;
import com.github.sib_energy_craft.machines.bio_reactor.block.BioReactorBlock;
import com.github.sib_energy_craft.sec_utils.common.Identified;
import com.github.sib_energy_craft.sec_utils.load.DefaultModInitializer;
import com.github.sib_energy_craft.sec_utils.utils.BlockUtils;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Material;
import net.minecraft.sound.BlockSoundGroup;

/**
 * @since 0.0.23
 * @author sibmaks
 */
public final class Blocks implements DefaultModInitializer {
    public static final Identified<BioReactorBlock> BIO_REACTOR;

    static {
        var extractorSettings = FabricBlockSettings.of(Material.METAL)
                .sounds(BlockSoundGroup.METAL)
                .strength(5, 6)
                .requiresTool();

        var bioReactorBlock = new BioReactorBlock(extractorSettings, 10000, 8000, 12, 6, Energy.of(16));
        BIO_REACTOR = BlockUtils.register(Identifiers.of("bio_reactor"), bioReactorBlock);
    }
}