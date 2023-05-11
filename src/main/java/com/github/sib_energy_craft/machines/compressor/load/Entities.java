package com.github.sib_energy_craft.machines.compressor.load;

import com.github.sib_energy_craft.machines.compressor.block.entity.CompressorBlockEntity;
import com.github.sib_energy_craft.sec_utils.load.DefaultModInitializer;
import com.github.sib_energy_craft.sec_utils.utils.EntityUtils;
import net.minecraft.block.entity.BlockEntityType;

/**
 * @since 0.0.1
 * @author sibmaks
 */
public final class Entities implements DefaultModInitializer {
    public static final BlockEntityType<CompressorBlockEntity> COMPRESSOR;

    static {
        COMPRESSOR = EntityUtils.register(Blocks.COMPRESSOR, CompressorBlockEntity::new);
    }
}
