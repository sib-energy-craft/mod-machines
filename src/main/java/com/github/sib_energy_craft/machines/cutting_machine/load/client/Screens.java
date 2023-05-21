package com.github.sib_energy_craft.machines.cutting_machine.load.client;

import com.github.sib_energy_craft.machines.cutting_machine.load.ScreenHandlers;
import com.github.sib_energy_craft.machines.cutting_machine.screen.CuttingMachineScreen;
import com.github.sib_energy_craft.sec_utils.load.DefaultClientModInitializer;

import static com.github.sib_energy_craft.sec_utils.utils.ScreenUtils.registerScreen;

/**
 * @since 0.0.17
 * @author sibmaks
 */
public final class Screens implements DefaultClientModInitializer {

    static {
        registerScreen(ScreenHandlers.CUTTING_MACHINE, CuttingMachineScreen::new);
    }

}
