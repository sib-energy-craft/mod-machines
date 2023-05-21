package com.github.sib_energy_craft.machines.energy_furnace.load.client;

import com.github.sib_energy_craft.machines.energy_furnace.load.ScreenHandlers;
import com.github.sib_energy_craft.machines.energy_furnace.screen.EnergyFurnaceScreen;
import com.github.sib_energy_craft.sec_utils.load.DefaultClientModInitializer;

import static com.github.sib_energy_craft.sec_utils.utils.ScreenUtils.registerScreen;

/**
 * @since 0.0.2
 * @author sibmaks
 */
public final class Screens implements DefaultClientModInitializer {

    static {
        registerScreen(ScreenHandlers.ENERGY_FURNACE, EnergyFurnaceScreen::new);
    }

}
