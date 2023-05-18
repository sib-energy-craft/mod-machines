package com.github.sib_energy_craft.machines.advanced_macerator.load;

import com.github.sib_energy_craft.energy_api.utils.Identifiers;
import com.github.sib_energy_craft.machines.item.EnergyMachineBlockItem;
import com.github.sib_energy_craft.sec_utils.load.DefaultModInitializer;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroups;

import static com.github.sib_energy_craft.sec_utils.utils.ItemUtils.register;

/**
 * @since 0.0.17
 * @author sibmaks
 */
public final class Items implements DefaultModInitializer {
    public static final BlockItem ADVANCED_MACERATOR;

    static {
        var advanced_maceratorSettings = new Item.Settings();

        var advanced_macerator = new EnergyMachineBlockItem<>(Blocks.ADVANCED_MACERATOR.entity(), advanced_maceratorSettings);
        ADVANCED_MACERATOR = register(ItemGroups.FUNCTIONAL, Identifiers.of("advanced_macerator"), advanced_macerator);
    }
}
