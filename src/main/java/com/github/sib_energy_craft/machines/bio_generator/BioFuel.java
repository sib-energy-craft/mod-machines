package com.github.sib_energy_craft.machines.bio_generator;

import net.minecraft.item.Item;
import org.jetbrains.annotations.NotNull;

/**
 * Biofuel configuration.<br/>
 * * fuel item - item used as biofuel<br/>
 * * fermentation time - amount of fermentation, that can be generated from this item
 *
 * @author sibmaks
 * @since 0.0.23
 */
public record BioFuel(@NotNull Item fuelItem,
                      int fermentationAmount) {
}
