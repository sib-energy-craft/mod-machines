package com.github.sib_energy_craft.machines.bio_reactor;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import lombok.NoArgsConstructor;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

/**
 * @author sibmaks
 * @since 0.0.23
 */
@NoArgsConstructor
public final class BioFuelRegistry {
    private static final BiMap<Item, BioFuel> BIO_FUEL_MAP;

    static {
        BIO_FUEL_MAP = HashBiMap.create();
    }

    /**
     * Check is item stack if fuel for bio reactor
     *
     * @param itemStack item stack
     * @return true - can be used as fuel, false - otherwise
     */
    public static boolean isFuel(ItemStack itemStack) {
        var item = itemStack.getItem();
        return BIO_FUEL_MAP.containsKey(item);
    }

    /**
     * Check is item if fuel for bio reactor
     *
     * @param item item
     * @return true - can be used as fuel, false - otherwise
     */
    public static boolean isFuel(Item item) {
        return BIO_FUEL_MAP.containsKey(item);
    }

    /**
     * Get bio fuel configuration<br/>
     * In case if item is not fuel when 0 will be returned
     *
     * @param item fuel item
     * @return biofuel configuration
     */
    public static int getFermentationAmount(Item item) {
        var bioFuel = BIO_FUEL_MAP.get(item);
        return bioFuel == null ? 0 : bioFuel.fermentationAmount();
    }

    /**
     * Register biofuel in registry.<br/>
     * In case if biofuel already registered {@link IllegalArgumentException} will be thrown.
     *
     * @param item bio fuel item
     * @param fermentationAmount amount of fermentation, that can be generated from item
     */
    public synchronized static void registry(Item item, int fermentationAmount) {
        if(BIO_FUEL_MAP.containsKey(item)) {
            throw new IllegalArgumentException("Fuel %s already registered".formatted(item));
        }
        var bioFuel = new BioFuel(item, fermentationAmount);
        BIO_FUEL_MAP.put(item, bioFuel);
    }
}
