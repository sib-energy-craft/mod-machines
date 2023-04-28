package com.github.sib_energy_craft.machines.energy_furnace.load;

import com.github.sib_energy_craft.energy_api.utils.Identifiers;
import com.github.sib_energy_craft.machines.energy_furnace.block.EnergyFurnaceBlock;
import com.github.sib_energy_craft.sec_utils.common.Identified;
import com.github.sib_energy_craft.sec_utils.load.ModRegistrar;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Material;
import net.minecraft.sound.BlockSoundGroup;

import static com.github.sib_energy_craft.sec_utils.utils.BlockUtils.register;

/**
 * @since 0.0.2
 * @author sibmaks
 */
public final class Blocks implements ModRegistrar {
    public static final Identified<EnergyFurnaceBlock> ENERGY_FURNACE;

    static {
        var energy_furnaceSettings = FabricBlockSettings.of(Material.METAL)
                .sounds(BlockSoundGroup.METAL)
                .strength(5, 6)
                .requiresTool();

        var energy_furnaceBlock = new EnergyFurnaceBlock(energy_furnaceSettings);
        ENERGY_FURNACE = register(Identifiers.of("energy_furnace"), energy_furnaceBlock);
    }
}