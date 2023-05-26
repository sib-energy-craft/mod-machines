package com.github.sib_energy_craft.machines.bio_reactor.block;

import net.minecraft.util.StringIdentifiable;

import java.util.Locale;

/**
 * @author sibmaks
 * @since 0.0.23
 */
public enum BioReactorFilling implements StringIdentifiable {
    EMPTY,
    ALMOST_EMPTY,
    HALF,
    ALMOST_FULL,
    FULL;

    @Override
    public String asString() {
        return name().toLowerCase(Locale.ENGLISH);
    }
}
