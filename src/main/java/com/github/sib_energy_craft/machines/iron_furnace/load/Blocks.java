package com.github.sib_energy_craft.machines.iron_furnace.load;

import com.github.sib_energy_craft.energy_api.utils.Identifiers;
import com.github.sib_energy_craft.machines.iron_furnace.block.IronFurnaceBlock;
import com.github.sib_energy_craft.sec_utils.common.Identified;
import com.github.sib_energy_craft.sec_utils.load.DefaultModInitializer;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.MapColor;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.state.property.Properties;
import org.jetbrains.annotations.NotNull;

import java.util.function.ToIntFunction;

import static com.github.sib_energy_craft.sec_utils.utils.BlockUtils.register;

/**
 * @since 0.0.1
 * @author sibmaks
 */
public final class Blocks implements DefaultModInitializer {
    public static final Identified<IronFurnaceBlock> IRON_FURNACE;

    static {
        var ironFurnaceBlockSettings = AbstractBlock.Settings.create()
                .mapColor(MapColor.IRON_GRAY)
                .sounds(BlockSoundGroup.METAL)
                .strength(5, 6)
                .luminance(createLightLevelFromLitBlockState(13))
                .requiresTool();

        var ironFurnaceBlock = new IronFurnaceBlock(ironFurnaceBlockSettings);
        IRON_FURNACE = register(Identifiers.of("iron_furnace"), ironFurnaceBlock);
    }

    @NotNull
    private static ToIntFunction<BlockState> createLightLevelFromLitBlockState(int litLevel) {
        return state -> state.get(Properties.LIT) ? litLevel : 0;
    }
}