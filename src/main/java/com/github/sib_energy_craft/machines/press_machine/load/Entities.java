package com.github.sib_energy_craft.machines.press_machine.load;

import com.github.sib_energy_craft.machines.press_machine.block.entity.PressMachineBlockEntity;
import com.github.sib_energy_craft.sec_utils.load.DefaultModInitializer;
import com.github.sib_energy_craft.sec_utils.utils.EntityUtils;
import net.minecraft.block.entity.BlockEntityType;

/**
 * @since 0.0.17
 * @author sibmaks
 */
public final class Entities implements DefaultModInitializer {
    public static final BlockEntityType<PressMachineBlockEntity> PRESS_MACHINE;

    static {
        PRESS_MACHINE = EntityUtils.register(Blocks.PRESS_MACHINE, PressMachineBlockEntity::new);
    }
}
