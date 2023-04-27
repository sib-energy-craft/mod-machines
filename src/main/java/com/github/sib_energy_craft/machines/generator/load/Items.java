package com.github.sib_energy_craft.machines.generator.load;

import com.github.sib_energy_craft.energy_api.utils.Identifiers;
import com.github.sib_energy_craft.machines.generator.item.EnergyGeneratorBlockItem;
import com.github.sib_energy_craft.sec_utils.load.ModRegistrar;
import com.github.sib_energy_craft.sec_utils.utils.ItemUtils;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroups;

/**
 * @since 0.0.1
 * @author sibmaks
 */
public final class Items implements ModRegistrar {
    public static final BlockItem ENERGY_GENERATOR;

    static {
        var extractorSettings = new Item.Settings();

        var extractor = new EnergyGeneratorBlockItem(Blocks.ENERGY_GENERATOR.entity(), extractorSettings);
        ENERGY_GENERATOR = ItemUtils.register(ItemGroups.FUNCTIONAL, Identifiers.of("energy_generator"), extractor);
    }
}
