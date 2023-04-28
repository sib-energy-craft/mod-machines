package com.github.sib_energy_craft.energy_container.load;

import com.github.sib_energy_craft.energy_api.EnergyLevel;
import com.github.sib_energy_craft.energy_api.utils.Identifiers;
import com.github.sib_energy_craft.energy_container.block.EnergyContainerBlock;
import com.github.sib_energy_craft.sec_utils.common.Identified;
import com.github.sib_energy_craft.sec_utils.load.ModRegistrar;
import com.github.sib_energy_craft.sec_utils.utils.BlockUtils;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Material;
import net.minecraft.sound.BlockSoundGroup;

/**
 * @since 0.0.2
 * @author sibmaks
 */
public final class Blocks implements ModRegistrar {
    public static final Identified<EnergyContainerBlock> BASIC_ENERGY_CONTAINER;

    static {
        var metalBlockSettings = FabricBlockSettings.of(Material.METAL)
                .sounds(BlockSoundGroup.METAL)
                .strength(5, 6)
                .requiresTool();
        var basicEnergyContainer = new EnergyContainerBlock(
                metalBlockSettings,
                "container.basic_energy_container",
                EnergyLevel.L1,
                40000
        );
        BASIC_ENERGY_CONTAINER = BlockUtils.register(Identifiers.of("basic_energy_container"), basicEnergyContainer);
    }
}
