package com.github.sib_energy_craft.machines.press_machine.load;

import com.github.sib_energy_craft.energy_api.utils.Identifiers;
import com.github.sib_energy_craft.sec_utils.load.DefaultModInitializer;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.stat.StatFormatter;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.NotNull;

import static net.minecraft.stat.Stats.CUSTOM;

/**
 * @since 0.0.17
 * @author sibmaks
 */
public final class Stats implements DefaultModInitializer {
    public static final Identifier INTERACT_WITH_PRESS_MACHINE;

    static {
        INTERACT_WITH_PRESS_MACHINE = register(Identifiers.asString("interact_with_press_machine"), StatFormatter.DEFAULT);
    }

    @NotNull
    private static Identifier register(@NotNull String id, @NotNull StatFormatter formatter) {
        var identifier = new Identifier(id);
        Registry.register(Registries.CUSTOM_STAT, id, identifier);
        CUSTOM.getOrCreateStat(identifier, formatter);
        return identifier;
    }
}
