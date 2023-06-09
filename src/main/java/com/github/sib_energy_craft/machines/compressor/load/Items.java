package com.github.sib_energy_craft.machines.compressor.load;

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
    public static final BlockItem COMPRESSOR;

    static {
        var compressorSettings = new Item.Settings();

        var compressor = new EnergyMachineBlockItem<>(Blocks.COMPRESSOR.entity(), compressorSettings);
        COMPRESSOR = register(ItemGroups.FUNCTIONAL, Identifiers.of("compressor"), compressor);
    }
}
