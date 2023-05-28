package com.github.sib_energy_craft.machines.compressor.block.entity;

import com.github.sib_energy_craft.machines.compressor.block.CompressorBlock;
import com.github.sib_energy_craft.machines.compressor.load.Entities;
import com.github.sib_energy_craft.machines.compressor.screen.CompressorScreenHandler;
import com.github.sib_energy_craft.machines.screen.AbstractEnergyMachineScreenHandler;
import com.github.sib_energy_craft.recipes.load.RecipeTypes;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.math.BlockPos;
import org.jetbrains.annotations.NotNull;

/**
 * @since 0.0.1
 * @author sibmaks
 */
public class CompressorBlockEntity extends AbstractCompressorBlockEntity<CompressorBlock> {
    public CompressorBlockEntity(@NotNull BlockPos pos,
                                 @NotNull BlockState state,
                                 @NotNull CompressorBlock block) {
        super(Entities.COMPRESSOR, pos, state, RecipeTypes.COMPRESSING, block);
    }

    @Override
    public @NotNull Text getDisplayName() {
        return Text.translatable("container.compressor");
    }

    @Override
    protected AbstractEnergyMachineScreenHandler createScreenHandler(int syncId,
                                                                     @NotNull PlayerInventory playerInventory,
                                                                     @NotNull PlayerEntity player) {
        return new CompressorScreenHandler(syncId, playerInventory, this);
    }

    @Override
    public void writeScreenOpeningData(@NotNull ServerPlayerEntity player,
                                       @NotNull PacketByteBuf buf) {
        buf.writeBlockPos(pos);
    }
}

