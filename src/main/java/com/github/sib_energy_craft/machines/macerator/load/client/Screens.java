package com.github.sib_energy_craft.machines.macerator.load.client;

import com.github.sib_energy_craft.energy_api.utils.Identifiers;
import com.github.sib_energy_craft.machines.macerator.screen.MaceratorScreen;
import com.github.sib_energy_craft.machines.macerator.screen.MaceratorScreenHandler;
import com.github.sib_energy_craft.sec_utils.load.DefaultClientModInitializer;
import com.github.sib_energy_craft.sec_utils.utils.ScreenUtils;
import net.minecraft.screen.ScreenHandlerType;

/**
 * @since 0.0.1
 * @author sibmaks
 */
public final class Screens implements DefaultClientModInitializer {
    public static final ScreenHandlerType<MaceratorScreenHandler> MACERATOR;

    static {
        MACERATOR = ScreenUtils.register(Identifiers.of("macerator"), MaceratorScreenHandler::new, MaceratorScreen::new);
    }

}