package com.github.sib_energy_craft.energy_transformer.load.client;

import com.github.sib_energy_craft.energy_transformer.load.ScreenHandlers;
import com.github.sib_energy_craft.energy_transformer.screen.EnergyTransformerScreen;
import com.github.sib_energy_craft.sec_utils.load.DefaultClientModInitializer;

import static com.github.sib_energy_craft.sec_utils.utils.ScreenUtils.registerScreen;

/**
 * @since 0.0.4
 * @author sibmaks
 */
public final class Screens implements DefaultClientModInitializer {

    static {
        registerScreen(ScreenHandlers.ENERGY_TRANSFORMER, EnergyTransformerScreen::new);
    }
}
