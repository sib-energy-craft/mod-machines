package com.github.sib_energy_craft.machines.ore_purifying_machine.load;

import com.github.sib_energy_craft.energy_api.utils.Identifiers;
import com.github.sib_energy_craft.machines.item.EnergyMachineBlockItem;
import com.github.sib_energy_craft.sec_utils.load.DefaultModInitializer;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroups;

import static com.github.sib_energy_craft.sec_utils.utils.ItemUtils.register;

/**
 * @since 0.0.26
 * @author sibmaks
 */
public final class Items implements DefaultModInitializer {
    public static final BlockItem ORE_PURIFYING_MACHINE;

    static {
        var commonSettings = new Item.Settings();

        var orePurifyingMachineItem = new EnergyMachineBlockItem<>(Blocks.ORE_PURIFYING_MACHINE.entity(), commonSettings);
        ORE_PURIFYING_MACHINE = register(ItemGroups.FUNCTIONAL, Identifiers.of("ore_purifying_machine"), orePurifyingMachineItem);
    }
}
