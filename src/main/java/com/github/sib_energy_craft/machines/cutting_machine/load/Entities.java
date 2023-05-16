package com.github.sib_energy_craft.machines.cutting_machine.load;

import com.github.sib_energy_craft.machines.cutting_machine.block.entity.CuttingMachineBlockEntity;
import com.github.sib_energy_craft.sec_utils.load.DefaultModInitializer;
import com.github.sib_energy_craft.sec_utils.utils.EntityUtils;
import net.minecraft.block.entity.BlockEntityType;

/**
 * @since 0.0.17
 * @author sibmaks
 */
public final class Entities implements DefaultModInitializer {
    public static final BlockEntityType<CuttingMachineBlockEntity> CUTTING_MACHINE;

    static {
        CUTTING_MACHINE = EntityUtils.register(Blocks.CUTTING_MACHINE, CuttingMachineBlockEntity::new);
    }
}
