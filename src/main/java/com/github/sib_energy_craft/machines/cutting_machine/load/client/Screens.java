package com.github.sib_energy_craft.machines.cutting_machine.load.client;

import com.github.sib_energy_craft.energy_api.utils.Identifiers;
import com.github.sib_energy_craft.machines.cutting_machine.screen.CuttingMachineScreen;
import com.github.sib_energy_craft.machines.cutting_machine.screen.CuttingMachineScreenHandler;
import com.github.sib_energy_craft.sec_utils.load.DefaultClientModInitializer;
import com.github.sib_energy_craft.sec_utils.utils.ScreenUtils;
import net.minecraft.screen.ScreenHandlerType;

/**
 * @since 0.0.17
 * @author sibmaks
 */
public final class Screens implements DefaultClientModInitializer {
    public static final ScreenHandlerType<CuttingMachineScreenHandler> CUTTING_MACHINE;

    static {
        CUTTING_MACHINE = ScreenUtils.register(Identifiers.of("cutting_machine"), CuttingMachineScreenHandler::new,
                CuttingMachineScreen::new);
    }

}
