package com.github.sib_energy_craft.machines.induction_furnace.load.client;

import com.github.sib_energy_craft.energy_api.utils.Identifiers;
import com.github.sib_energy_craft.machines.induction_furnace.screen.InductionFurnaceScreen;
import com.github.sib_energy_craft.machines.induction_furnace.screen.InductionFurnaceScreenHandler;
import com.github.sib_energy_craft.sec_utils.load.DefaultClientModInitializer;
import net.minecraft.screen.ScreenHandlerType;

import static com.github.sib_energy_craft.sec_utils.utils.ScreenUtils.register;

/**
 * @since 0.0.17
 * @author sibmaks
 */
public final class Screens implements DefaultClientModInitializer {
    public static final ScreenHandlerType<InductionFurnaceScreenHandler> INDUCTION_FURNACE;

    static {
        INDUCTION_FURNACE = register(Identifiers.of("induction_furnace"), InductionFurnaceScreenHandler::new, InductionFurnaceScreen::new);
    }

}
