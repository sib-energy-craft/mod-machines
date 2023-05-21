package com.github.sib_energy_craft.machines.induction_furnace.load;

import com.github.sib_energy_craft.energy_api.utils.Identifiers;
import com.github.sib_energy_craft.machines.induction_furnace.screen.InductionFurnaceScreenHandler;
import com.github.sib_energy_craft.sec_utils.load.DefaultModInitializer;
import net.minecraft.screen.ScreenHandlerType;

import static com.github.sib_energy_craft.sec_utils.utils.ScreenUtils.registerHandler;

/**
 * @since 0.0.20
 * @author sibmaks
 */
public final class ScreenHandlers implements DefaultModInitializer {
    public static final ScreenHandlerType<InductionFurnaceScreenHandler> INDUCTION_FURNACE;

    static {
        INDUCTION_FURNACE = registerHandler(Identifiers.of("induction_furnace"), InductionFurnaceScreenHandler::new);
    }

}
