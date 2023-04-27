package com.github.sib_energy_craft.machines.macerator.block.entity;

import com.github.sib_energy_craft.machines.macerator.block.AbstractMaceratorBlock;
import com.github.sib_energy_craft.machines.macerator.load.Entities;
import com.github.sib_energy_craft.machines.macerator.screen.MaceratorScreenHandler;
import com.github.sib_energy_craft.recipes.load.RecipeTypes;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.math.BlockPos;

public class MaceratorBlockEntity extends AbstractMaceratorBlockEntity {
    public MaceratorBlockEntity(BlockPos pos, BlockState state, AbstractMaceratorBlock block) {
        super(Entities.MACERATOR, pos, state, RecipeTypes.MACERATING, block);
    }

    @Override
    protected Text getContainerName() {
        return Text.translatable("container.macerator");
    }

    @Override
    protected ScreenHandler createScreenHandler(int syncId, PlayerInventory playerInventory) {
        return new MaceratorScreenHandler(syncId, playerInventory, this, this.propertyMap);
    }

    @Override
    public void writeScreenOpeningData(ServerPlayerEntity player, PacketByteBuf buf) {
        buf.writeBlockPos(pos);
    }

}

