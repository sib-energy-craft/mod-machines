package com.github.sib_energy_craft.machines.bio_reactor.load;

import com.github.sib_energy_craft.machines.bio_reactor.block.entity.BioReactorBlockEntity;
import com.github.sib_energy_craft.sec_utils.load.DefaultModInitializer;
import com.github.sib_energy_craft.sec_utils.utils.EntityUtils;
import net.minecraft.block.entity.BlockEntityType;

/**
 * @since 0.0.23
 * @author sibmaks
 */
public final class Entities implements DefaultModInitializer {
    public static final BlockEntityType<BioReactorBlockEntity> BIO_REACTOR;

    static {
        BIO_REACTOR = EntityUtils.register(Blocks.BIO_REACTOR, BioReactorBlockEntity::new);
    }
}
