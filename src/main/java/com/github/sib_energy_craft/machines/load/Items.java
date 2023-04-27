package com.github.sib_energy_craft.machines.load;

import com.github.sib_energy_craft.sec_utils.load.ModRegistrar;
import com.github.sib_energy_craft.sec_utils.utils.ItemUtils;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroups;

/**
 * @since 0.0.1
 * @author sibmaks
 */
public final class Items implements ModRegistrar {
    public static final Item BASE_MACHINE_BODY;

    static {
        BASE_MACHINE_BODY = ItemUtils.registerBlockItem(ItemGroups.BUILDING_BLOCKS, Blocks.BASE_MACHINE_BODY);
    }
}
