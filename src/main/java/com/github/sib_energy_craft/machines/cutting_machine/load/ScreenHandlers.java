package com.github.sib_energy_craft.machines.cutting_machine.load;

import com.github.sib_energy_craft.energy_api.utils.Identifiers;
import com.github.sib_energy_craft.machines.cutting_machine.screen.CuttingMachineScreenHandler;
import com.github.sib_energy_craft.sec_utils.load.DefaultModInitializer;
import net.minecraft.screen.ScreenHandlerType;

import static com.github.sib_energy_craft.sec_utils.utils.ScreenUtils.registerHandler;

/**
 * @since 0.0.20
 * @author sibmaks
 */
public final class ScreenHandlers implements DefaultModInitializer {
    public static final ScreenHandlerType<CuttingMachineScreenHandler> CUTTING_MACHINE;

    static {
        CUTTING_MACHINE = registerHandler(Identifiers.of("cutting_machine"), CuttingMachineScreenHandler::new);
    }

}
