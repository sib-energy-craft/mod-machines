package com.github.sib_energy_craft.machines.bio_generator.load;

import com.github.sib_energy_craft.energy_api.utils.Identifiers;
import com.github.sib_energy_craft.machines.bio_generator.item.BioReactorItem;
import com.github.sib_energy_craft.sec_utils.load.DefaultModInitializer;
import com.github.sib_energy_craft.sec_utils.utils.ItemUtils;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroups;

/**
 * @since 0.0.23
 * @author sibmaks
 */
public final class Items implements DefaultModInitializer {
    public static final BlockItem BIO_REACTOR;

    static {
        var commonSettings = new Item.Settings();

        var extractor = new BioReactorItem(Blocks.BIO_REACTOR.entity(), commonSettings);
        BIO_REACTOR = ItemUtils.register(ItemGroups.FUNCTIONAL, Identifiers.of("bio_reactor"), extractor);
    }
}
