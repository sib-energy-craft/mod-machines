package com.github.sib_energy_craft.machines.induction_furnace.load;

import com.github.sib_energy_craft.machines.induction_furnace.block.entity.InductionFurnaceBlockEntity;
import com.github.sib_energy_craft.sec_utils.load.DefaultModInitializer;
import com.github.sib_energy_craft.sec_utils.utils.EntityUtils;
import net.minecraft.block.entity.BlockEntityType;

/**
 * @since 0.0.17
 * @author sibmaks
 */
public final class Entities implements DefaultModInitializer {
    public static final BlockEntityType<InductionFurnaceBlockEntity> INDUCTION_FURNACE;

    static {
        INDUCTION_FURNACE = EntityUtils.register(Blocks.INDUCTION_FURNACE, InductionFurnaceBlockEntity::new);
    }
}
