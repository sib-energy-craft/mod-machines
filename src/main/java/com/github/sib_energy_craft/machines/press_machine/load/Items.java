package com.github.sib_energy_craft.machines.press_machine.load;

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
    public static final BlockItem PRESS_MACHINE;

    static {
        var pressMachineSettings = new Item.Settings();

        var pressMachine = new EnergyMachineBlockItem<>(Blocks.PRESS_MACHINE.entity(), pressMachineSettings);
        PRESS_MACHINE = ItemUtils.register(ItemGroups.FUNCTIONAL, Identifiers.of("press_machine"), pressMachine);
    }
}
