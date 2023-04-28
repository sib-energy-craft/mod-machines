package com.github.sib_energy_craft.energy_container.load;

import net.fabricmc.api.ModInitializer;

import static com.github.sib_energy_craft.sec_utils.load.LoadUtils.load;

/**
 * @since 0.0.2
 * @author sibmaks
 */
public class Loader implements ModInitializer {

    @Override
    public void onInitialize() {
        load(Loader.class, "mod-energy-containers");
    }
}
