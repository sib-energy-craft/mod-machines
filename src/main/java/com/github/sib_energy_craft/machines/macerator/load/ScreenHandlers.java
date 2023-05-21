package com.github.sib_energy_craft.machines.macerator.load;

import com.github.sib_energy_craft.energy_api.utils.Identifiers;
import com.github.sib_energy_craft.machines.macerator.screen.MaceratorScreenHandler;
import com.github.sib_energy_craft.sec_utils.load.DefaultModInitializer;
import net.minecraft.screen.ScreenHandlerType;

import static com.github.sib_energy_craft.sec_utils.utils.ScreenUtils.registerHandler;

/**
 * @since 0.0.20
 * @author sibmaks
 */
public final class ScreenHandlers implements DefaultModInitializer {
    public static final ScreenHandlerType<MaceratorScreenHandler> MACERATOR;

    static {
        MACERATOR = registerHandler(Identifiers.of("macerator"), MaceratorScreenHandler::new);
    }

}
