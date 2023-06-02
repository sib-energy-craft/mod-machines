package com.github.sib_energy_craft.machines.generator.block.entity;

import com.github.sib_energy_craft.machines.generator.block.AbstractEnergyGeneratorBlock;
import com.github.sib_energy_craft.machines.generator.load.Entities;
import com.github.sib_energy_craft.machines.generator.screen.EnergyGeneratorScreenHandler;
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
 * @since 0.0.1
 * @author sibmaks
 */
public class EnergyGeneratorBlockEntity extends AbstractEnergyGeneratorBlockEntity {
    public EnergyGeneratorBlockEntity(@NotNull BlockPos pos,
                                      @NotNull BlockState state,
                                      @NotNull AbstractEnergyGeneratorBlock block) {
        super(Entities.ENERGY_GENERATOR, pos, state, block);
    }

    @Override
    public Text getDisplayName() {
        return Text.translatable("container.energy_generator");
    }

    @Nullable
    @Override
    public ScreenHandler createMenu(int syncId,
                                    @NotNull PlayerInventory playerInventory,
                                    @NotNull PlayerEntity player) {
        var screenHandler = new EnergyGeneratorScreenHandler(syncId, playerInventory, this, this.propertyMap,
                getMaxCharge());
        var world = player.getWorld();
        if(!world.isClient && player instanceof ServerPlayerEntity serverPlayerEntity) {
            var syncer = new PropertyUpdateSyncer(syncId, serverPlayerEntity, typedScreenProperties);
            screenHandler.setSyncer(syncer);
        }
        return screenHandler;
    }
}

