package com.github.sib_energy_craft.machines.generator.block.entity;

import com.github.sib_energy_craft.machines.generator.block.AbstractEnergyGeneratorBlock;
import com.github.sib_energy_craft.machines.generator.load.Entities;
import com.github.sib_energy_craft.machines.generator.screen.EnergyGeneratorScreenHandler;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.math.BlockPos;
import org.jetbrains.annotations.NotNull;

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
    protected Text getContainerName() {
        return Text.translatable("container.energy_generator");
    }

    @Override
    protected ScreenHandler createScreenHandler(int syncId, @NotNull PlayerInventory playerInventory) {
        return new EnergyGeneratorScreenHandler(syncId, playerInventory, this, this.propertyMap);
    }

    @Override
    public void writeScreenOpeningData(@NotNull ServerPlayerEntity player,
                                       @NotNull PacketByteBuf buf) {
        buf.writeBlockPos(pos);
    }
}

