package com.github.sib_energy_craft.machines.extractor.load;

import com.github.sib_energy_craft.energy_api.utils.Identifiers;
import com.github.sib_energy_craft.machines.item.EnergyMachineBlockItem;
import com.github.sib_energy_craft.sec_utils.load.DefaultModInitializer;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroups;

import static com.github.sib_energy_craft.sec_utils.utils.ItemUtils.register;

/**
 * @since 0.0.1
 * @author sibmaks
 */
public final class Items implements DefaultModInitializer {
    public static final BlockItem EXTRACTOR;

    static {
        var extractorSettings = new Item.Settings();

        var extractor = new EnergyMachineBlockItem<>(Blocks.EXTRACTOR.entity(), extractorSettings);
        EXTRACTOR = register(ItemGroups.FUNCTIONAL, Identifiers.of("extractor"), extractor);
    }
}
