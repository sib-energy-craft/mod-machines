package com.github.sib_energy_craft.machines.bio_reactor.load.client;

import com.github.sib_energy_craft.machines.bio_reactor.load.ScreenHandlers;
import com.github.sib_energy_craft.machines.bio_reactor.screen.BioReactorScreen;
import com.github.sib_energy_craft.sec_utils.load.DefaultClientModInitializer;

import static com.github.sib_energy_craft.sec_utils.utils.ScreenUtils.registerScreen;

/**
 * @since 0.0.23
 * @author sibmaks
 */
public final class Screens implements DefaultClientModInitializer {

    static {
        registerScreen(ScreenHandlers.BIO_REACTOR, BioReactorScreen::new);
    }

}
