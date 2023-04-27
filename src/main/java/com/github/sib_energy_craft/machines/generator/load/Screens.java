package com.github.sib_energy_craft.machines.generator.load;

import com.github.sib_energy_craft.energy_api.utils.Identifiers;
import com.github.sib_energy_craft.machines.generator.screen.EnergyGeneratorScreen;
import com.github.sib_energy_craft.machines.generator.screen.EnergyGeneratorScreenHandler;
import com.github.sib_energy_craft.sec_utils.load.ModRegistrar;
import com.github.sib_energy_craft.sec_utils.utils.ScreenUtils;
import net.minecraft.screen.ScreenHandlerType;

/**
 * @since 0.0.1
 * @author sibmaks
 */
public final class Screens implements ModRegistrar {
    public static final ScreenHandlerType<EnergyGeneratorScreenHandler> ENERGY_GENERATOR;

    static {
        ENERGY_GENERATOR = ScreenUtils.register(Identifiers.of("energy_generator"),
                EnergyGeneratorScreenHandler::new, EnergyGeneratorScreen::new);
    }

}
