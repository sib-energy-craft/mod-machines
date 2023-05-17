package com.github.sib_energy_craft.machines.induction_furnace.load;

import com.github.sib_energy_craft.energy_api.utils.Identifiers;
import com.github.sib_energy_craft.machines.induction_furnace.block.InductionFurnaceBlock;
import com.github.sib_energy_craft.sec_utils.common.Identified;
import com.github.sib_energy_craft.sec_utils.load.DefaultModInitializer;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Material;
import net.minecraft.sound.BlockSoundGroup;

import static com.github.sib_energy_craft.sec_utils.utils.BlockUtils.register;

/**
 * @since 0.0.17
 * @author sibmaks
 */
public final class Blocks implements DefaultModInitializer {
    public static final Identified<InductionFurnaceBlock> INDUCTION_FURNACE;

    static {
        var induction_furnaceSettings = FabricBlockSettings.of(Material.METAL)
                .sounds(BlockSoundGroup.METAL)
                .strength(5, 6)
                .requiresTool();

        var inductionFurnaceBlock = new InductionFurnaceBlock(induction_furnaceSettings);
        INDUCTION_FURNACE = register(Identifiers.of("induction_furnace"), inductionFurnaceBlock);
    }
}