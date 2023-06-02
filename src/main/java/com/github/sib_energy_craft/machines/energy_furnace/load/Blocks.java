package com.github.sib_energy_craft.machines.energy_furnace.load;

import com.github.sib_energy_craft.energy_api.utils.Identifiers;
import com.github.sib_energy_craft.machines.energy_furnace.block.EnergyFurnaceBlock;
import com.github.sib_energy_craft.sec_utils.common.Identified;
import com.github.sib_energy_craft.sec_utils.load.DefaultModInitializer;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.MapColor;
import net.minecraft.sound.BlockSoundGroup;

import static com.github.sib_energy_craft.sec_utils.utils.BlockUtils.register;

/**
 * @since 0.0.2
 * @author sibmaks
 */
public final class Blocks implements DefaultModInitializer {
    public static final Identified<EnergyFurnaceBlock> ENERGY_FURNACE;

    static {
        var energy_furnaceSettings = AbstractBlock.Settings.create()
                .mapColor(MapColor.IRON_GRAY)
                .sounds(BlockSoundGroup.METAL)
                .strength(5, 6)
                .requiresTool();

        var energy_furnaceBlock = new EnergyFurnaceBlock(energy_furnaceSettings);
        ENERGY_FURNACE = register(Identifiers.of("energy_furnace"), energy_furnaceBlock);
    }
}