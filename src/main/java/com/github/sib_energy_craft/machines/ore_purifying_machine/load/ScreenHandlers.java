package com.github.sib_energy_craft.machines.ore_purifying_machine.load;

import com.github.sib_energy_craft.energy_api.utils.Identifiers;
import com.github.sib_energy_craft.machines.ore_purifying_machine.screen.OrePurifyingMachineScreenHandler;
import com.github.sib_energy_craft.sec_utils.load.DefaultModInitializer;
import net.minecraft.screen.ScreenHandlerType;

import static com.github.sib_energy_craft.sec_utils.utils.ScreenUtils.registerHandler;

/**
 * @since 0.0.31
 * @author sibmaks
 */
public final class ScreenHandlers implements DefaultModInitializer {
    public static final ScreenHandlerType<OrePurifyingMachineScreenHandler> ORE_PURIFYING_MACHINE;

    static {
        ORE_PURIFYING_MACHINE = registerHandler(Identifiers.of("ore_purifying_machine"), OrePurifyingMachineScreenHandler::new);
    }

}
