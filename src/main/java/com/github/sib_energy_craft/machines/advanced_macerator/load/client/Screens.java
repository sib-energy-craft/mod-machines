package com.github.sib_energy_craft.machines.advanced_macerator.load.client;

import com.github.sib_energy_craft.energy_api.utils.Identifiers;
import com.github.sib_energy_craft.machines.advanced_macerator.screen.AdvancedMaceratorScreen;
import com.github.sib_energy_craft.machines.advanced_macerator.screen.AdvancedMaceratorScreenHandler;
import com.github.sib_energy_craft.sec_utils.load.DefaultClientModInitializer;
import net.minecraft.screen.ScreenHandlerType;

import static com.github.sib_energy_craft.sec_utils.utils.ScreenUtils.register;

/**
 * @since 0.0.17
 * @author sibmaks
 */
public final class Screens implements DefaultClientModInitializer {
    public static final ScreenHandlerType<AdvancedMaceratorScreenHandler> ADVANCED_MACERATOR;

    static {
        ADVANCED_MACERATOR = register(Identifiers.of("advanced_macerator"), AdvancedMaceratorScreenHandler::new, AdvancedMaceratorScreen::new);
    }

}
