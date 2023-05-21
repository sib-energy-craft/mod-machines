package com.github.sib_energy_craft.machines.generator.load.client;

import com.github.sib_energy_craft.machines.generator.load.ScreenHandlers;
import com.github.sib_energy_craft.machines.generator.screen.EnergyGeneratorScreen;
import com.github.sib_energy_craft.sec_utils.load.DefaultClientModInitializer;

import static com.github.sib_energy_craft.sec_utils.utils.ScreenUtils.registerScreen;

/**
 * @since 0.0.1
 * @author sibmaks
 */
public final class Screens implements DefaultClientModInitializer {

    static {
        registerScreen(ScreenHandlers.ENERGY_GENERATOR, EnergyGeneratorScreen::new);
    }

}
