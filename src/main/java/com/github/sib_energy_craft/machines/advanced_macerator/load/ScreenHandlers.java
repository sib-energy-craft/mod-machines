package com.github.sib_energy_craft.machines.advanced_macerator.load;

import com.github.sib_energy_craft.energy_api.utils.Identifiers;
import com.github.sib_energy_craft.machines.advanced_macerator.screen.AdvancedMaceratorScreenHandler;
import com.github.sib_energy_craft.sec_utils.load.DefaultModInitializer;
import net.minecraft.screen.ScreenHandlerType;

import static com.github.sib_energy_craft.sec_utils.utils.ScreenUtils.registerHandler;

/**
 * @since 0.0.20
 * @author sibmaks
 */
public final class ScreenHandlers implements DefaultModInitializer {
    public static final ScreenHandlerType<AdvancedMaceratorScreenHandler> ADVANCED_MACERATOR;

    static {
        ADVANCED_MACERATOR = registerHandler(Identifiers.of("advanced_macerator"), AdvancedMaceratorScreenHandler::new);
    }

}
