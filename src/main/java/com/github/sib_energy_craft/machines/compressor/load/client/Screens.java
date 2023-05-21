package com.github.sib_energy_craft.machines.compressor.load.client;

import com.github.sib_energy_craft.machines.compressor.load.ScreenHandlers;
import com.github.sib_energy_craft.machines.compressor.screen.CompressorScreen;
import com.github.sib_energy_craft.sec_utils.load.DefaultClientModInitializer;

import static com.github.sib_energy_craft.sec_utils.utils.ScreenUtils.registerScreen;

/**
 * @since 0.0.1
 * @author sibmaks
 */
public final class Screens implements DefaultClientModInitializer {

    static {
        registerScreen(ScreenHandlers.COMPRESSOR, CompressorScreen::new);
    }

}
