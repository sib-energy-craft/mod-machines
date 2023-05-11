package com.github.sib_energy_craft.machines.energy_furnace.load;

import com.github.sib_energy_craft.energy_api.utils.Identifiers;
import com.github.sib_energy_craft.machines.energy_furnace.screen.EnergyFurnaceScreen;
import com.github.sib_energy_craft.machines.energy_furnace.screen.EnergyFurnaceScreenHandler;
import com.github.sib_energy_craft.sec_utils.load.DefaultModInitializer;
import net.minecraft.screen.ScreenHandlerType;

import static com.github.sib_energy_craft.sec_utils.utils.ScreenUtils.register;

/**
 * @since 0.0.2
 * @author sibmaks
 */
public final class Screens implements DefaultModInitializer {
    public static final ScreenHandlerType<EnergyFurnaceScreenHandler> ENERGY_FURNACE;

    static {
        ENERGY_FURNACE = register(Identifiers.of("energy_furnace"), EnergyFurnaceScreenHandler::new, EnergyFurnaceScreen::new);
    }

}
