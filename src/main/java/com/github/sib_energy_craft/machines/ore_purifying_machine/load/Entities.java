package com.github.sib_energy_craft.machines.ore_purifying_machine.load;

import com.github.sib_energy_craft.machines.ore_purifying_machine.block.entity.OrePurifyingMachineBlockEntity;
import com.github.sib_energy_craft.sec_utils.load.DefaultModInitializer;
import com.github.sib_energy_craft.sec_utils.utils.EntityUtils;
import net.minecraft.block.entity.BlockEntityType;

/**
 * @since 0.0.26
 * @author sibmaks
 */
public final class Entities implements DefaultModInitializer {
    public static final BlockEntityType<OrePurifyingMachineBlockEntity> ORE_PURIFYING_MACHINE;

    static {
        ORE_PURIFYING_MACHINE = EntityUtils.register(Blocks.ORE_PURIFYING_MACHINE, OrePurifyingMachineBlockEntity::new);
    }
}
