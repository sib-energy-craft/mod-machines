package com.github.sib_energy_craft.energy_transformer.load.client;

import com.github.sib_energy_craft.energy_api.utils.Identifiers;
import com.github.sib_energy_craft.energy_transformer.screen.EnergyTransformerScreen;
import com.github.sib_energy_craft.energy_transformer.screen.EnergyTransformerScreenHandler;
import com.github.sib_energy_craft.sec_utils.load.DefaultClientModInitializer;
import net.minecraft.screen.ScreenHandlerType;

import static com.github.sib_energy_craft.sec_utils.utils.ScreenUtils.register;

/**
 * @since 0.0.4
 * @author sibmaks
 */
public final class Screens implements DefaultClientModInitializer {
    public static final ScreenHandlerType<EnergyTransformerScreenHandler> ENERGY_TRANSFORMER;

    static {
        ENERGY_TRANSFORMER = register(Identifiers.of("energy_transformer"), EnergyTransformerScreenHandler::new,
                EnergyTransformerScreen::new);
    }
}
