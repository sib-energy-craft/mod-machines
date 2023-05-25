package com.github.sib_energy_craft.machines.bio_generator.block.entity;

import com.github.sib_energy_craft.machines.bio_generator.block.BioReactorBlock;
import com.github.sib_energy_craft.machines.bio_generator.load.Entities;
import com.github.sib_energy_craft.machines.bio_generator.screen.BioReactorScreenHandler;
import com.github.sib_energy_craft.network.PropertyUpdateSyncer;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.math.BlockPos;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * @since 0.0.23
 * @author sibmaks
 */
public class BioReactorBlockEntity extends AbstractBioReactorBlockEntity<BioReactorBlock> {
    public BioReactorBlockEntity(@NotNull BlockPos pos,
                                 @NotNull BlockState state,
                                 @NotNull BioReactorBlock block) {
        super(Entities.BIO_REACTOR, pos, state, block);
    }

    @Override
    public Text getDisplayName() {
        return Text.translatable("container.bio_reactor");
    }

    @Nullable
    @Override
    public ScreenHandler createMenu(int syncId,
                                    @NotNull PlayerInventory playerInventory,
                                    @NotNull PlayerEntity player) {
        var screenHandler = new BioReactorScreenHandler(syncId, playerInventory, this,
                getMaxCharge(), block.getMaxFerments(), block.getEnergyPacketSize().intValue());
        var world = player.world;
        if(!world.isClient && player instanceof ServerPlayerEntity serverPlayerEntity) {
            var syncer = new PropertyUpdateSyncer(syncId, serverPlayerEntity, typedScreenProperties);
            screenHandler.setSyncer(syncer);
        }
        return screenHandler;
    }
}

