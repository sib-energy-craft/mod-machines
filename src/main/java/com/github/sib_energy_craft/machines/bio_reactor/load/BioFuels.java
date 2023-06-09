package com.github.sib_energy_craft.machines.bio_reactor.load;

import com.github.sib_energy_craft.machines.bio_reactor.BioFuelRegistry;
import com.github.sib_energy_craft.sec_utils.load.DefaultModInitializer;
import net.minecraft.item.Items;

/**
 * @since 0.0.23
 * @author sibmaks
 */
public final class BioFuels implements DefaultModInitializer {

    static {
        // current rate is 20 ticks per second
        int hungryToEnergy = 10;

        // # edible
        // 0.5
        BioFuelRegistry.registry(Items.DRIED_KELP, hungryToEnergy);
        // 1
        BioFuelRegistry.registry(Items.COOKIE, 2 * hungryToEnergy);
        // 2
        BioFuelRegistry.registry(Items.ROTTEN_FLESH, 4 * hungryToEnergy);
        BioFuelRegistry.registry(Items.CHORUS_FRUIT, 6 * hungryToEnergy);
        // 2.5
        BioFuelRegistry.registry(Items.BREAD, 8 * hungryToEnergy);
        // 3
        BioFuelRegistry.registry(Items.HONEY_BLOCK, 10 * hungryToEnergy);
        // 7
        BioFuelRegistry.registry(Items.CAKE, 25 * hungryToEnergy);


        // # edible fish
        // 0.5
        BioFuelRegistry.registry(Items.PUFFERFISH, hungryToEnergy);
        BioFuelRegistry.registry(Items.TROPICAL_FISH, hungryToEnergy);
        // 1
        BioFuelRegistry.registry(Items.SALMON,  2 * hungryToEnergy);
        BioFuelRegistry.registry(Items.COD,  2 * hungryToEnergy);
        // 2.5
        BioFuelRegistry.registry(Items.COOKED_COD,  8 * hungryToEnergy);
        // 3
        BioFuelRegistry.registry(Items.COOKED_SALMON,  10 * hungryToEnergy);
        // 4
        BioFuelRegistry.registry(Items.PUMPKIN_PIE,  14 * hungryToEnergy);

        // # edible meat
        // 1
        BioFuelRegistry.registry(Items.SPIDER_EYE, 3 * hungryToEnergy);
        BioFuelRegistry.registry(Items.MUTTON, 3 * hungryToEnergy);
        BioFuelRegistry.registry(Items.CHICKEN,  3 * hungryToEnergy);
        // 1.5
        BioFuelRegistry.registry(Items.PORKCHOP, 5 * hungryToEnergy);
        BioFuelRegistry.registry(Items.RABBIT, 5 * hungryToEnergy);
        BioFuelRegistry.registry(Items.BEEF, 5 * hungryToEnergy);
        // 2.5
        BioFuelRegistry.registry(Items.COOKED_RABBIT, 8 * hungryToEnergy);
        // 3
        BioFuelRegistry.registry(Items.COOKED_CHICKEN, 10 * hungryToEnergy);
        BioFuelRegistry.registry(Items.COOKED_MUTTON, 10 * hungryToEnergy);
        // 4
        BioFuelRegistry.registry(Items.COOKED_PORKCHOP, 12 * hungryToEnergy);
        BioFuelRegistry.registry(Items.COOKED_BEEF, 12 * hungryToEnergy);

        // # edible vegetable
        // 0.5
        BioFuelRegistry.registry(Items.POTATO, hungryToEnergy);
        BioFuelRegistry.registry(Items.BEETROOT, hungryToEnergy);
        // 1
        BioFuelRegistry.registry(Items.POISONOUS_POTATO, 2 * hungryToEnergy);
        BioFuelRegistry.registry(Items.MELON_SLICE, 2 * hungryToEnergy);
        BioFuelRegistry.registry(Items.GLOW_BERRIES, 4 * hungryToEnergy);
        BioFuelRegistry.registry(Items.SWEET_BERRIES, 2 * hungryToEnergy);
        // 1.5
        BioFuelRegistry.registry(Items.CARROT, 2 * hungryToEnergy);
        // 2
        BioFuelRegistry.registry(Items.APPLE, 3 * hungryToEnergy);
        BioFuelRegistry.registry(Items.GOLDEN_APPLE, 5 * hungryToEnergy);
        // 2.5
        BioFuelRegistry.registry(Items.BAKED_POTATO, 8 * hungryToEnergy);
        // 3
        BioFuelRegistry.registry(Items.GOLDEN_CARROT, 10 * hungryToEnergy);

        // # other
        BioFuelRegistry.registry(Items.BAMBOO, hungryToEnergy / 2);
        BioFuelRegistry.registry(Items.SUGAR_CANE, hungryToEnergy / 2);

        // # flowers
        BioFuelRegistry.registry(Items.DANDELION, hungryToEnergy / 2);
        BioFuelRegistry.registry(Items.POPPY, hungryToEnergy / 2);
        BioFuelRegistry.registry(Items.BLUE_ORCHID, hungryToEnergy / 2);
        BioFuelRegistry.registry(Items.ALLIUM, hungryToEnergy / 2);
        BioFuelRegistry.registry(Items.AZURE_BLUET, hungryToEnergy / 2);
        BioFuelRegistry.registry(Items.ORANGE_TULIP, hungryToEnergy / 2);
        BioFuelRegistry.registry(Items.PINK_TULIP, hungryToEnergy / 2);
        BioFuelRegistry.registry(Items.RED_TULIP, hungryToEnergy / 2);
        BioFuelRegistry.registry(Items.WHITE_TULIP, hungryToEnergy / 2);
        BioFuelRegistry.registry(Items.OXEYE_DAISY, hungryToEnergy / 2);
        BioFuelRegistry.registry(Items.CORNFLOWER, hungryToEnergy / 2);
        BioFuelRegistry.registry(Items.LILY_OF_THE_VALLEY, hungryToEnergy / 2);
        BioFuelRegistry.registry(Items.TORCHFLOWER, hungryToEnergy / 2);

        // # seeds
        BioFuelRegistry.registry(Items.WHEAT_SEEDS, hungryToEnergy / 2);
        BioFuelRegistry.registry(Items.PUMPKIN_SEEDS, hungryToEnergy / 2);
        BioFuelRegistry.registry(Items.MELON_SEEDS, hungryToEnergy / 2);
        BioFuelRegistry.registry(Items.BEETROOT_SEEDS, hungryToEnergy / 2);
        BioFuelRegistry.registry(Items.COCOA_BEANS, hungryToEnergy / 2);
        BioFuelRegistry.registry(Items.TORCHFLOWER_SEEDS, 2 * hungryToEnergy);

        BioFuelRegistry.registry(Items.WHEAT, hungryToEnergy);

        BioFuelRegistry.registry(Items.PUMPKIN, 2 * hungryToEnergy);
        BioFuelRegistry.registry(Items.MELON, 18 * hungryToEnergy);

        // # ingredients
        BioFuelRegistry.registry(Items.SUGAR, hungryToEnergy);
        BioFuelRegistry.registry(Items.EGG, hungryToEnergy / 2);
        BioFuelRegistry.registry(Items.RED_MUSHROOM, hungryToEnergy / 2);
        BioFuelRegistry.registry(Items.BROWN_MUSHROOM, hungryToEnergy / 2);
    }
}
