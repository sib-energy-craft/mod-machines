package com.github.sib_energy_craft.machines.bio_generator.load;

import com.github.sib_energy_craft.energy_api.utils.Identifiers;
import com.github.sib_energy_craft.machines.bio_generator.screen.BioReactorScreenHandler;
import com.github.sib_energy_craft.sec_utils.load.DefaultModInitializer;
import net.minecraft.screen.ScreenHandlerType;

import static com.github.sib_energy_craft.sec_utils.utils.ScreenUtils.registerHandler;

/**
 * @since 0.0.23
 * @author sibmaks
 */
public final class ScreenHandlers implements DefaultModInitializer {
    public static final ScreenHandlerType<BioReactorScreenHandler> BIO_REACTOR;

    static {
        BIO_REACTOR = registerHandler(Identifiers.of("bio_reactor"), BioReactorScreenHandler::new);
    }

}
