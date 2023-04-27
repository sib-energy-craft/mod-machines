package com.github.sib_energy_craft.machines.extractor.load;

import com.github.sib_energy_craft.energy_api.utils.Identifiers;
import com.github.sib_energy_craft.machines.extractor.screen.ExtractorScreen;
import com.github.sib_energy_craft.machines.extractor.screen.ExtractorScreenHandler;
import com.github.sib_energy_craft.sec_utils.load.ModRegistrar;
import net.minecraft.screen.ScreenHandlerType;

import static com.github.sib_energy_craft.sec_utils.utils.ScreenUtils.register;

/**
 * @since 0.0.1
 * @author sibmaks
 */
public final class Screens implements ModRegistrar {
    public static final ScreenHandlerType<ExtractorScreenHandler> EXTRACTOR;

    static {
        EXTRACTOR = register(Identifiers.of("extractor"), ExtractorScreenHandler::new, ExtractorScreen::new);
    }

}
