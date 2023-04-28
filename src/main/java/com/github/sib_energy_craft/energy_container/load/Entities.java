package com.github.sib_energy_craft.energy_container.load;

import com.github.sib_energy_craft.energy_container.block.entity.EnergyContainerBlockEntity;
import com.github.sib_energy_craft.sec_utils.load.ModRegistrar;
import net.minecraft.block.entity.BlockEntityType;

import static com.github.sib_energy_craft.sec_utils.utils.EntityUtils.register;

/**
 * @since 0.0.2
 * @author sibmaks
 */
public final class Entities implements ModRegistrar {
    public static final BlockEntityType<EnergyContainerBlockEntity> BASIC_ENERGY_CONTAINER;

    static {
        BASIC_ENERGY_CONTAINER = register(Blocks.BASIC_ENERGY_CONTAINER, EnergyContainerBlockEntity::new);
    }
}
