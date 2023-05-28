package com.github.sib_energy_craft.machines.ore_purifying_machine.load.client;

import com.github.sib_energy_craft.machines.ore_purifying_machine.load.ScreenHandlers;
import com.github.sib_energy_craft.machines.ore_purifying_machine.screen.OrePurifyingMachineScreen;
import com.github.sib_energy_craft.sec_utils.load.DefaultClientModInitializer;

import static com.github.sib_energy_craft.sec_utils.utils.ScreenUtils.registerScreen;

/**
 * @since 0.0.26
 * @author sibmaks
 */
public final class Screens implements DefaultClientModInitializer {

    static {
        registerScreen(ScreenHandlers.ORE_PURIFYING_MACHINE, OrePurifyingMachineScreen::new);
    }

}
