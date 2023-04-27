package com.github.sib_energy_craft.machines.extractor.block.entity;

import com.github.sib_energy_craft.machines.extractor.block.AbstractExtractorBlock;
import com.github.sib_energy_craft.machines.extractor.load.Entities;
import com.github.sib_energy_craft.machines.extractor.screen.ExtractorScreenHandler;
import com.github.sib_energy_craft.recipes.load.RecipeTypes;
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
public class ExtractorBlockEntity extends AbstractExtractorBlockEntity {
    public ExtractorBlockEntity(@NotNull BlockPos pos,
                                @NotNull BlockState state,
                                @NotNull AbstractExtractorBlock block) {
        super(Entities.EXTRACTOR, pos, state, RecipeTypes.EXTRACTING, block);
    }

    @NotNull
    @Override
    protected Text getContainerName() {
        return Text.translatable("container.extractor");
    }

    @NotNull
    @Override
    protected ScreenHandler createScreenHandler(int syncId,
                                                @NotNull PlayerInventory playerInventory) {
        return new ExtractorScreenHandler(syncId, playerInventory, this, this.propertyMap);
    }

    @Override
    public void writeScreenOpeningData(@NotNull ServerPlayerEntity player,
                                       @NotNull PacketByteBuf buf) {
        buf.writeBlockPos(pos);
    }

}

