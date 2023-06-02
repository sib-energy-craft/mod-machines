package com.github.sib_energy_craft.machines.induction_furnace.load;

import com.github.sib_energy_craft.energy_api.utils.Identifiers;
import com.github.sib_energy_craft.machines.induction_furnace.block.InductionFurnaceBlock;
import com.github.sib_energy_craft.sec_utils.common.Identified;
import com.github.sib_energy_craft.sec_utils.load.DefaultModInitializer;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.MapColor;
import net.minecraft.sound.BlockSoundGroup;

import static com.github.sib_energy_craft.sec_utils.utils.BlockUtils.register;

/**
 * @since 0.0.17
 * @author sibmaks
 */
public final class Blocks implements DefaultModInitializer {
    public static final Identified<InductionFurnaceBlock> INDUCTION_FURNACE;

    static {
        var inductionFurnaceSettings = AbstractBlock.Settings.create()
                .mapColor(MapColor.IRON_GRAY)
                .sounds(BlockSoundGroup.METAL)
                .strength(50, 600)
                .requiresTool();

        var inductionFurnaceBlock = new InductionFurnaceBlock(inductionFurnaceSettings);
        INDUCTION_FURNACE = register(Identifiers.of("induction_furnace"), inductionFurnaceBlock);
    }
}