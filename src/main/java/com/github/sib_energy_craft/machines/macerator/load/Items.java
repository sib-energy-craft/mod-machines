package com.github.sib_energy_craft.machines.macerator.load;

import com.github.sib_energy_craft.energy_api.utils.Identifiers;
import com.github.sib_energy_craft.machines.item.EnergyMachineBlockItem;
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
    public static final BlockItem MACERATOR;

    static {
        var maceratorSettings = new Item.Settings();

        var macerator = new EnergyMachineBlockItem(Blocks.MACERATOR.entity(), maceratorSettings);
        MACERATOR = ItemUtils.register(ItemGroups.FUNCTIONAL, Identifiers.of("macerator"), macerator);
    }
}
