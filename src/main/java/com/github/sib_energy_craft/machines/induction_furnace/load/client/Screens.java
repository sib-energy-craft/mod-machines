package com.github.sib_energy_craft.machines.induction_furnace.load.client;

import com.github.sib_energy_craft.machines.induction_furnace.load.ScreenHandlers;
import com.github.sib_energy_craft.machines.induction_furnace.screen.InductionFurnaceScreen;
import com.github.sib_energy_craft.sec_utils.load.DefaultClientModInitializer;

import static com.github.sib_energy_craft.sec_utils.utils.ScreenUtils.registerScreen;

/**
 * @since 0.0.17
 * @author sibmaks
 */
public final class Screens implements DefaultClientModInitializer {

    static {
        registerScreen(ScreenHandlers.INDUCTION_FURNACE, InductionFurnaceScreen::new);
    }

}
