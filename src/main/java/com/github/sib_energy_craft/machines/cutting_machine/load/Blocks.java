package com.github.sib_energy_craft.machines.cutting_machine.load;

import com.github.sib_energy_craft.energy_api.utils.Identifiers;
import com.github.sib_energy_craft.machines.cutting_machine.block.CuttingMachineBlock;
import com.github.sib_energy_craft.sec_utils.common.Identified;
import com.github.sib_energy_craft.sec_utils.load.DefaultModInitializer;
import com.github.sib_energy_craft.sec_utils.utils.BlockUtils;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Material;
import net.minecraft.sound.BlockSoundGroup;

/**
 * @since 0.0.17
 * @author sibmaks
 */
public final class Blocks implements DefaultModInitializer {
    public static final Identified<CuttingMachineBlock> CUTTING_MACHINE;

    static {
        var cuttingMachineSettings = FabricBlockSettings.of(Material.METAL)
                .sounds(BlockSoundGroup.METAL)
                .strength(5, 6)
                .requiresTool();

        var cuttingMachineBlock = new CuttingMachineBlock(cuttingMachineSettings);
        CUTTING_MACHINE = BlockUtils.register(Identifiers.of("cutting_machine"), cuttingMachineBlock);
    }
}