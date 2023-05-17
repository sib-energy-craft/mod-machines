package com.github.sib_energy_craft.machines.induction_furnace.load;

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
    public static final BlockItem INDUCTION_FURNACE;

    static {
        var induction_furnaceSettings = new Item.Settings();

        var induction_furnace = new EnergyMachineBlockItem<>(Blocks.INDUCTION_FURNACE.entity(), induction_furnaceSettings);
        INDUCTION_FURNACE = register(ItemGroups.FUNCTIONAL, Identifiers.of("induction_furnace"), induction_furnace);
    }
}
