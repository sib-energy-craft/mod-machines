package com.github.sib_energy_craft.machines.press_machine.load.client;

import com.github.sib_energy_craft.energy_api.utils.Identifiers;
import com.github.sib_energy_craft.machines.press_machine.screen.PressMachineScreen;
import com.github.sib_energy_craft.machines.press_machine.screen.PressMachineScreenHandler;
import com.github.sib_energy_craft.sec_utils.load.DefaultClientModInitializer;
import com.github.sib_energy_craft.sec_utils.utils.ScreenUtils;
import net.minecraft.screen.ScreenHandlerType;

/**
 * @since 0.0.17
 * @author sibmaks
 */
public final class Screens implements DefaultClientModInitializer {
    public static final ScreenHandlerType<PressMachineScreenHandler> PRESS_MACHINE;

    static {
        PRESS_MACHINE = ScreenUtils.register(Identifiers.of("press_machine"), PressMachineScreenHandler::new,
                PressMachineScreen::new);
    }

}
