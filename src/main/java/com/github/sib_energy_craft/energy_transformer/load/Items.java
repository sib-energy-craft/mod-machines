package com.github.sib_energy_craft.energy_transformer.load;

import com.github.sib_energy_craft.energy_transformer.block.AbstractEnergyTransformerBlock;
import com.github.sib_energy_craft.energy_transformer.item.EnergyTransformerBlockItem;
import com.github.sib_energy_craft.sec_utils.common.Identified;
import com.github.sib_energy_craft.sec_utils.load.ModRegistrar;
import com.github.sib_energy_craft.sec_utils.utils.ItemUtils;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroups;

/**
 * @since 0.0.4
 * @author sibmaks
 */
public final class Items implements ModRegistrar {
    public static final BlockItem L1_ENERGY_TRANSFORMER;
    public static final BlockItem L2_ENERGY_TRANSFORMER;

    static {
        L1_ENERGY_TRANSFORMER = registerTransformer(Blocks.L1_ENERGY_TRANSFORMER);
        L2_ENERGY_TRANSFORMER = registerTransformer(Blocks.L2_ENERGY_TRANSFORMER);
    }

    private static EnergyTransformerBlockItem registerTransformer(Identified<? extends AbstractEnergyTransformerBlock> identified) {
        AbstractEnergyTransformerBlock block = identified.entity();
        Item.Settings settings = new Item.Settings();
        EnergyTransformerBlockItem blockItem = new EnergyTransformerBlockItem(block, settings);
        return ItemUtils.register(ItemGroups.FUNCTIONAL, identified.identifier(), blockItem);
    }
}
