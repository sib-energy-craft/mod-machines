package com.github.sib_energy_craft.machines.compressor.load.client;

import com.github.sib_energy_craft.energy_api.utils.Identifiers;
import com.github.sib_energy_craft.machines.compressor.screen.CompressorScreen;
import com.github.sib_energy_craft.machines.compressor.screen.CompressorScreenHandler;
import com.github.sib_energy_craft.sec_utils.load.DefaultClientModInitializer;
import net.minecraft.screen.ScreenHandlerType;

import static com.github.sib_energy_craft.sec_utils.utils.ScreenUtils.register;

/**
 * @since 0.0.1
 * @author sibmaks
 */
public final class Screens implements DefaultClientModInitializer {
    public static final ScreenHandlerType<CompressorScreenHandler> COMPRESSOR;

    static {
        COMPRESSOR = register(Identifiers.of("compressor"), CompressorScreenHandler::new, CompressorScreen::new);
    }

}
