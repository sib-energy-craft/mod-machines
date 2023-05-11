package com.github.sib_energy_craft.machines.energy_furnace.load;

import com.github.sib_energy_craft.energy_api.utils.Identifiers;
import com.github.sib_energy_craft.machines.item.EnergyMachineBlockItem;
import com.github.sib_energy_craft.sec_utils.load.DefaultModInitializer;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroups;

import static com.github.sib_energy_craft.sec_utils.utils.ItemUtils.register;

/**
 * @since 0.0.2
 * @author sibmaks
 */
public final class Items implements DefaultModInitializer {
    public static final BlockItem ENERGY_FURNACE;

    static {
        var energy_furnaceSettings = new Item.Settings();

        var energy_furnace = new EnergyMachineBlockItem<>(Blocks.ENERGY_FURNACE.entity(), energy_furnaceSettings);
        ENERGY_FURNACE = register(ItemGroups.FUNCTIONAL, Identifiers.of("energy_furnace"), energy_furnace);
    }
}
