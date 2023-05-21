package com.github.sib_energy_craft.machines.press_machine.load.client;

import com.github.sib_energy_craft.machines.press_machine.load.ScreenHandlers;
import com.github.sib_energy_craft.machines.press_machine.screen.PressMachineScreen;
import com.github.sib_energy_craft.sec_utils.load.DefaultClientModInitializer;

import static com.github.sib_energy_craft.sec_utils.utils.ScreenUtils.registerScreen;

/**
 * @since 0.0.17
 * @author sibmaks
 */
public final class Screens implements DefaultClientModInitializer {

    static {
        registerScreen(ScreenHandlers.PRESS_MACHINE, PressMachineScreen::new);
    }

}
