package com.github.sib_energy_craft.machines.cutting_machine.load;

import com.github.sib_energy_craft.energy_api.utils.Identifiers;
import com.github.sib_energy_craft.machines.item.EnergyMachineBlockItem;
import com.github.sib_energy_craft.sec_utils.load.DefaultModInitializer;
import com.github.sib_energy_craft.sec_utils.utils.ItemUtils;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroups;

/**
 * @since 0.0.17
 * @author sibmaks
 */
public final class Items implements DefaultModInitializer {
    public static final BlockItem CUTTING_MACHINE;

    static {
        var cuttingMachineSettings = new Item.Settings();

        var cuttingMachine = new EnergyMachineBlockItem<>(Blocks.CUTTING_MACHINE.entity(), cuttingMachineSettings);
        CUTTING_MACHINE = ItemUtils.register(ItemGroups.FUNCTIONAL, Identifiers.of("cutting_machine"), cuttingMachine);
    }
}
