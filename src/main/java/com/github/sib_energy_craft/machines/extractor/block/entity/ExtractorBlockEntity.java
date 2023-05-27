package com.github.sib_energy_craft.machines.extractor.block.entity;

import com.github.sib_energy_craft.machines.extractor.block.ExtractorBlock;
import com.github.sib_energy_craft.machines.extractor.load.Entities;
import com.github.sib_energy_craft.machines.extractor.screen.ExtractorScreenHandler;
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
public class ExtractorBlockEntity extends AbstractExtractorBlockEntity<ExtractorBlock> {
    public ExtractorBlockEntity(@NotNull BlockPos pos,
                                @NotNull BlockState state,
                                @NotNull ExtractorBlock block) {
        super(Entities.EXTRACTOR, pos, state, RecipeTypes.EXTRACTING, block);
    }

    @Override
    public @NotNull Text getDisplayName() {
        return Text.translatable("container.extractor");
    }

    @Override
    protected AbstractEnergyMachineScreenHandler createScreenHandler(int syncId,
                                                                     @NotNull PlayerInventory playerInventory,
                                                                     @NotNull PlayerEntity player) {
        return new ExtractorScreenHandler(syncId, playerInventory, this);
    }

    @Override
    public void writeScreenOpeningData(@NotNull ServerPlayerEntity player,
                                       @NotNull PacketByteBuf buf) {
        buf.writeBlockPos(pos);
    }

}

