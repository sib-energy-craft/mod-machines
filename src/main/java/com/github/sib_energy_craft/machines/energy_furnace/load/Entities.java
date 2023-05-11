package com.github.sib_energy_craft.machines.energy_furnace.load;

import com.github.sib_energy_craft.machines.energy_furnace.block.entity.EnergyFurnaceBlockEntity;
import com.github.sib_energy_craft.sec_utils.load.DefaultModInitializer;
import com.github.sib_energy_craft.sec_utils.utils.EntityUtils;
import net.minecraft.block.entity.BlockEntityType;

/**
 * @since 0.0.2
 * @author sibmaks
 */
public final class Entities implements DefaultModInitializer {
    public static final BlockEntityType<EnergyFurnaceBlockEntity> ENERGY_FURNACE;

    static {
        ENERGY_FURNACE = EntityUtils.register(Blocks.ENERGY_FURNACE, EnergyFurnaceBlockEntity::new);
    }
}
