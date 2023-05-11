package com.github.sib_energy_craft.energy_container.load;

import com.github.sib_energy_craft.energy_api.EnergyLevel;
import com.github.sib_energy_craft.energy_api.utils.Identifiers;
import com.github.sib_energy_craft.energy_container.block.BasicEnergyContainerBlock;
import com.github.sib_energy_craft.energy_container.block.BronzeEnergyContainerBlock;
import com.github.sib_energy_craft.energy_container.block.CrystalEnergyContainerBlock;
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
    public static final Identified<BasicEnergyContainerBlock> BASIC_ENERGY_CONTAINER;
    public static final Identified<BronzeEnergyContainerBlock> BRONZE_ENERGY_CONTAINER;
    public static final Identified<CrystalEnergyContainerBlock> CRYSTAL_ENERGY_CONTAINER;

    static {
        var metalBlockSettings = FabricBlockSettings.of(Material.METAL)
                .sounds(BlockSoundGroup.METAL)
                .strength(5, 6)
                .requiresTool();

        var basicEnergyContainer = new BasicEnergyContainerBlock(
                metalBlockSettings,
                "container.basic_energy_container",
                EnergyLevel.L1,
                40_000
        );
        BASIC_ENERGY_CONTAINER = BlockUtils.register(Identifiers.of("basic_energy_container"), basicEnergyContainer);

        var bronzeEnergyContainer = new BronzeEnergyContainerBlock(
                metalBlockSettings,
                "container.bronze_energy_container",
                EnergyLevel.L2,
                300_000
        );
        BRONZE_ENERGY_CONTAINER = BlockUtils.register(Identifiers.of("bronze_energy_container"), bronzeEnergyContainer);

        var crystalEnergyContainer = new CrystalEnergyContainerBlock(
                metalBlockSettings,
                "container.crystal_energy_container",
                EnergyLevel.L3,
                4_000_000
        );
        CRYSTAL_ENERGY_CONTAINER = BlockUtils.register(Identifiers.of("crystal_energy_container"), crystalEnergyContainer);
    }
}
