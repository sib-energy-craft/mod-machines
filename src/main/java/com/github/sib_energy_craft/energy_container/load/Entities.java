package com.github.sib_energy_craft.energy_container.load;

import com.github.sib_energy_craft.energy_container.block.entity.BasicEnergyContainerBlockEntity;
import com.github.sib_energy_craft.energy_container.block.entity.BronzeEnergyContainerBlockEntity;
import com.github.sib_energy_craft.sec_utils.load.ModRegistrar;
import net.minecraft.block.entity.BlockEntityType;

import static com.github.sib_energy_craft.sec_utils.utils.EntityUtils.register;

/**
 * @since 0.0.2
 * @author sibmaks
 */
public final class Entities implements ModRegistrar {
    public static final BlockEntityType<BasicEnergyContainerBlockEntity> BASIC_ENERGY_CONTAINER;
    public static final BlockEntityType<BronzeEnergyContainerBlockEntity> BRONZE_ENERGY_CONTAINER;

    static {
        BASIC_ENERGY_CONTAINER = register(Blocks.BASIC_ENERGY_CONTAINER, BasicEnergyContainerBlockEntity::new);
        BRONZE_ENERGY_CONTAINER = register(Blocks.BRONZE_ENERGY_CONTAINER, BronzeEnergyContainerBlockEntity::new);
    }
}
