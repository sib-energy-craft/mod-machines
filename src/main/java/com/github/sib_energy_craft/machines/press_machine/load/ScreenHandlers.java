package com.github.sib_energy_craft.machines.press_machine.load;

import com.github.sib_energy_craft.energy_api.utils.Identifiers;
import com.github.sib_energy_craft.machines.press_machine.screen.PressMachineScreenHandler;
import com.github.sib_energy_craft.sec_utils.load.DefaultModInitializer;
import net.minecraft.screen.ScreenHandlerType;

import static com.github.sib_energy_craft.sec_utils.utils.ScreenUtils.registerHandler;

/**
 * @since 0.0.20
 * @author sibmaks
 */
public final class ScreenHandlers implements DefaultModInitializer {
    public static final ScreenHandlerType<PressMachineScreenHandler> PRESS_MACHINE;

    static {
        PRESS_MACHINE = registerHandler(Identifiers.of("press_machine"), PressMachineScreenHandler::new);
    }

}
