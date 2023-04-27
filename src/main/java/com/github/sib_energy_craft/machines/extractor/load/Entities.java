package com.github.sib_energy_craft.machines.extractor.load;

import com.github.sib_energy_craft.machines.extractor.block.entity.ExtractorBlockEntity;
import com.github.sib_energy_craft.sec_utils.load.ModRegistrar;
import com.github.sib_energy_craft.sec_utils.utils.EntityUtils;
import net.minecraft.block.entity.BlockEntityType;

/**
 * @since 0.0.1
 * @author sibmaks
 */
public final class Entities implements ModRegistrar {
    public static final BlockEntityType<ExtractorBlockEntity> EXTRACTOR;

    static {
        EXTRACTOR = EntityUtils.register(Blocks.EXTRACTOR, ExtractorBlockEntity::new);
    }
}
