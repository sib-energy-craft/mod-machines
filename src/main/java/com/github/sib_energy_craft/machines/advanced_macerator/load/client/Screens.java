package com.github.sib_energy_craft.machines.advanced_macerator.load.client;

import com.github.sib_energy_craft.machines.advanced_macerator.load.ScreenHandlers;
import com.github.sib_energy_craft.machines.advanced_macerator.screen.AdvancedMaceratorScreen;
import com.github.sib_energy_craft.sec_utils.load.DefaultClientModInitializer;

import static com.github.sib_energy_craft.sec_utils.utils.ScreenUtils.registerScreen;

/**
 * @since 0.0.17
 * @author sibmaks
 */
public final class Screens implements DefaultClientModInitializer {

    static {
        registerScreen(ScreenHandlers.ADVANCED_MACERATOR, AdvancedMaceratorScreen::new);
    }

}
