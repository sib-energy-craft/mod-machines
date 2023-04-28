package com.github.sib_energy_craft.energy_container.load;

import com.github.sib_energy_craft.energy_container.item.EnergyContainerBlockItem;
import com.github.sib_energy_craft.sec_utils.load.ModRegistrar;
import com.github.sib_energy_craft.sec_utils.utils.ItemUtils;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroups;

/**
 * @since 0.0.2
 * @author sibmaks
 */
public final class Items implements ModRegistrar {
    public static final EnergyContainerBlockItem BASIC_ENERGY_CONTAINER;

    static {
        var commonSettings = new Item.Settings();

        BASIC_ENERGY_CONTAINER = ItemUtils.register(ItemGroups.FUNCTIONAL,
                Blocks.BASIC_ENERGY_CONTAINER,
                it -> new EnergyContainerBlockItem(it, commonSettings));
    }
}
