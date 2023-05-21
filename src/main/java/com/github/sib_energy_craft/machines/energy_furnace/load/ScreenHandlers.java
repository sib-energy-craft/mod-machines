package com.github.sib_energy_craft.machines.energy_furnace.load;

import com.github.sib_energy_craft.energy_api.utils.Identifiers;
import com.github.sib_energy_craft.machines.energy_furnace.screen.EnergyFurnaceScreenHandler;
import com.github.sib_energy_craft.sec_utils.load.DefaultModInitializer;
import net.minecraft.screen.ScreenHandlerType;

import static com.github.sib_energy_craft.sec_utils.utils.ScreenUtils.registerHandler;

/**
 * @since 0.0.20
 * @author sibmaks
 */
public final class ScreenHandlers implements DefaultModInitializer {
    public static final ScreenHandlerType<EnergyFurnaceScreenHandler> ENERGY_FURNACE;

    static {
        ENERGY_FURNACE = registerHandler(Identifiers.of("energy_furnace"), EnergyFurnaceScreenHandler::new);
    }

}
