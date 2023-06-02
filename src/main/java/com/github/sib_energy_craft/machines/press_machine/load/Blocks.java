package com.github.sib_energy_craft.machines.press_machine.load;

import com.github.sib_energy_craft.energy_api.utils.Identifiers;
import com.github.sib_energy_craft.machines.press_machine.block.PressMachineBlock;
import com.github.sib_energy_craft.sec_utils.common.Identified;
import com.github.sib_energy_craft.sec_utils.load.DefaultModInitializer;
import com.github.sib_energy_craft.sec_utils.utils.BlockUtils;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.MapColor;
import net.minecraft.sound.BlockSoundGroup;

/**
 * @since 0.0.17
 * @author sibmaks
 */
public final class Blocks implements DefaultModInitializer {
    public static final Identified<PressMachineBlock> PRESS_MACHINE;

    static {
        var pressMachineSettings = AbstractBlock.Settings.create()
                .mapColor(MapColor.IRON_GRAY)
                .sounds(BlockSoundGroup.METAL)
                .strength(5, 6)
                .requiresTool();

        var pressMachineBlock = new PressMachineBlock(pressMachineSettings);
        PRESS_MACHINE = BlockUtils.register(Identifiers.of("press_machine"), pressMachineBlock);
    }
}