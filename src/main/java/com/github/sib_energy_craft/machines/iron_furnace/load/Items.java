package com.github.sib_energy_craft.machines.iron_furnace.load;

import com.github.sib_energy_craft.energy_api.utils.Identifiers;
import com.github.sib_energy_craft.sec_utils.load.ModRegistrar;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroups;

import static com.github.sib_energy_craft.sec_utils.utils.ItemUtils.register;

/**
 * @since 0.0.1
 * @author sibmaks
 */
public final class Items implements ModRegistrar {
    public static final BlockItem IRON_FURNACE;

    static {
        var ironFurnaceSettings = new Item.Settings();

        var ironFurnaceBlock = new BlockItem(Blocks.IRON_FURNACE.entity(), ironFurnaceSettings);
        IRON_FURNACE = register(ItemGroups.FUNCTIONAL, Identifiers.of("iron_furnace"), ironFurnaceBlock);
    }
}
