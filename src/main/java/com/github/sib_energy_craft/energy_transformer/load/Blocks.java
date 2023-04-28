package com.github.sib_energy_craft.energy_transformer.load;

import com.github.sib_energy_craft.energy_api.EnergyLevel;
import com.github.sib_energy_craft.energy_api.utils.Identifiers;
import com.github.sib_energy_craft.energy_transformer.block.L1EnergyTransformerBlock;
import com.github.sib_energy_craft.energy_transformer.block.L2EnergyTransformerBlock;
import com.github.sib_energy_craft.energy_transformer.block.L3EnergyTransformerBlock;
import com.github.sib_energy_craft.sec_utils.common.Identified;
import com.github.sib_energy_craft.sec_utils.load.ModRegistrar;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Material;
import net.minecraft.sound.BlockSoundGroup;

import static com.github.sib_energy_craft.sec_utils.utils.BlockUtils.register;

/**
 * @since 0.0.4
 * @author sibmaks
 */
public final class Blocks implements ModRegistrar {
    public static final Identified<L1EnergyTransformerBlock> L1_ENERGY_TRANSFORMER;
    public static final Identified<L2EnergyTransformerBlock> L2_ENERGY_TRANSFORMER;
    public static final Identified<L3EnergyTransformerBlock> L3_ENERGY_TRANSFORMER;

    static {
        var transformerSettings = FabricBlockSettings.of(Material.METAL)
                .sounds(BlockSoundGroup.METAL)
                .strength(5, 6)
                .requiresTool();

        var l1TransformerBlock = L1EnergyTransformerBlock.builder()
                .settings(transformerSettings)
                .containerNameCode("container.l1_energy_transformer")
                .lowEnergyLevel(EnergyLevel.L1)
                .highEnergyLevel(EnergyLevel.L2)
                .build();
        L1_ENERGY_TRANSFORMER = register(Identifiers.of("l1_energy_transformer"), l1TransformerBlock);

        var l2TransformerBlock = L2EnergyTransformerBlock.builder()
                .settings(transformerSettings)
                .containerNameCode("container.l2_energy_transformer")
                .lowEnergyLevel(EnergyLevel.L2)
                .highEnergyLevel(EnergyLevel.L3)
                .build();
        L2_ENERGY_TRANSFORMER = register(Identifiers.of("l2_energy_transformer"), l2TransformerBlock);

        var l3TransformerBlock = L3EnergyTransformerBlock.builder()
                .settings(transformerSettings)
                .containerNameCode("container.l3_energy_transformer")
                .lowEnergyLevel(EnergyLevel.L3)
                .highEnergyLevel(EnergyLevel.L4)
                .build();
        L3_ENERGY_TRANSFORMER = register(Identifiers.of("l3_energy_transformer"), l3TransformerBlock);
    }
}
