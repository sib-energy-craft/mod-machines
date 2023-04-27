package com.github.sib_energy_craft.machines.iron_furnace.load;

import com.github.sib_energy_craft.machines.iron_furnace.block.entity.IronFurnaceBlockEntity;
import com.github.sib_energy_craft.sec_utils.load.ModRegistrar;
import net.minecraft.block.entity.BlockEntityType;

import static com.github.sib_energy_craft.sec_utils.utils.EntityUtils.register;

/**
 * @since 0.0.1
 * @author sibmaks
 */
public final class Entities implements ModRegistrar {
    public static final BlockEntityType<IronFurnaceBlockEntity> IRON_FURNACE;

    static {
        IRON_FURNACE = register(Blocks.IRON_FURNACE, IronFurnaceBlockEntity::new);
    }
}
