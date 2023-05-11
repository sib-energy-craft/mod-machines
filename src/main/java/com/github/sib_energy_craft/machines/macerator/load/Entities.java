package com.github.sib_energy_craft.machines.macerator.load;

import com.github.sib_energy_craft.machines.macerator.block.entity.MaceratorBlockEntity;
import com.github.sib_energy_craft.sec_utils.load.DefaultModInitializer;
import com.github.sib_energy_craft.sec_utils.utils.EntityUtils;
import net.minecraft.block.entity.BlockEntityType;

/**
 * @since 0.0.1
 * @author sibmaks
 */
public final class Entities implements DefaultModInitializer {
    public static final BlockEntityType<MaceratorBlockEntity> MACERATOR;

    static {
        MACERATOR = EntityUtils.register(Blocks.MACERATOR, MaceratorBlockEntity::new);
    }
}
