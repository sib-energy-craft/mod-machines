package com.github.sib_energy_craft.machines.ore_purifying_machine.load;

import com.github.sib_energy_craft.energy_api.utils.Identifiers;
import com.github.sib_energy_craft.machines.ore_purifying_machine.block.OrePurifyingMachineBlock;
import com.github.sib_energy_craft.sec_utils.common.Identified;
import com.github.sib_energy_craft.sec_utils.load.DefaultModInitializer;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.MapColor;
import net.minecraft.sound.BlockSoundGroup;

import static com.github.sib_energy_craft.sec_utils.utils.BlockUtils.register;

/**
 * @since 0.0.26
 * @author sibmaks
 */
public final class Blocks implements DefaultModInitializer {
    public static final Identified<OrePurifyingMachineBlock> ORE_PURIFYING_MACHINE;

    static {
        var orePurifyingMachineSettings = AbstractBlock.Settings.create()
                .mapColor(MapColor.IRON_GRAY)
                .sounds(BlockSoundGroup.METAL)
                .strength(50, 600)
                .requiresTool();

        var orePurifyingMachineBlock = new OrePurifyingMachineBlock(orePurifyingMachineSettings);
        ORE_PURIFYING_MACHINE = register(Identifiers.of("ore_purifying_machine"), orePurifyingMachineBlock);
    }
}