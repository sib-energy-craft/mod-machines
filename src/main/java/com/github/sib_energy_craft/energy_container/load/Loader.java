package com.github.sib_energy_craft.energy_container.load;

import lombok.extern.slf4j.Slf4j;
import net.fabricmc.api.ModInitializer;

import static com.github.sib_energy_craft.sec_utils.load.LoadUtils.load;

/**
 * @since 0.0.2
 * @author sibmaks
 */
@Slf4j
public class Loader implements ModInitializer {

    @Override
    public void onInitialize() {
        var classLoader = getClass().getClassLoader();
        load(classLoader, "mod-energy-containers", Loader.class.getPackageName());
    }
}
