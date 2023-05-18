package com.github.sib_energy_craft.machines.advanced_macerator.load;

import com.github.sib_energy_craft.machines.advanced_macerator.block.entity.AdvancedMaceratorBlockEntity;
import com.github.sib_energy_craft.sec_utils.load.DefaultModInitializer;
import com.github.sib_energy_craft.sec_utils.utils.EntityUtils;
import net.minecraft.block.entity.BlockEntityType;

/**
 * @since 0.0.17
 * @author sibmaks
 */
public final class Entities implements DefaultModInitializer {
    public static final BlockEntityType<AdvancedMaceratorBlockEntity> ADVANCED_MACERATOR;

    static {
        ADVANCED_MACERATOR = EntityUtils.register(Blocks.ADVANCED_MACERATOR, AdvancedMaceratorBlockEntity::new);
    }
}
