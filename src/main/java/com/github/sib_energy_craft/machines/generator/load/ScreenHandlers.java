package com.github.sib_energy_craft.machines.generator.load;

import com.github.sib_energy_craft.energy_api.utils.Identifiers;
import com.github.sib_energy_craft.machines.generator.screen.EnergyGeneratorScreenHandler;
import com.github.sib_energy_craft.sec_utils.load.DefaultModInitializer;
import net.minecraft.screen.ScreenHandlerType;

import static com.github.sib_energy_craft.sec_utils.utils.ScreenUtils.registerHandler;

/**
 * @since 0.0.20
 * @author sibmaks
 */
public final class ScreenHandlers implements DefaultModInitializer {
    public static final ScreenHandlerType<EnergyGeneratorScreenHandler> ENERGY_GENERATOR;

    static {
        ENERGY_GENERATOR = registerHandler(Identifiers.of("energy_generator"), EnergyGeneratorScreenHandler::new);
    }

}
