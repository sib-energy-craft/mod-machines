package com.github.sib_energy_craft.machines.energy_furnace.block.entity;

import com.github.sib_energy_craft.machines.energy_furnace.block.AbstractEnergyFurnaceBlock;
import com.github.sib_energy_craft.machines.energy_furnace.load.Entities;
import com.github.sib_energy_craft.machines.energy_furnace.screen.EnergyFurnaceScreenHandler;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.recipe.RecipeType;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.math.BlockPos;


/**
 * @since 0.0.2
 * @author sibmaks
 */
public class EnergyFurnaceBlockEntity extends AbstractEnergyFurnaceBlockEntity {
    public EnergyFurnaceBlockEntity(BlockPos pos, BlockState state, AbstractEnergyFurnaceBlock block) {
        super(Entities.ENERGY_FURNACE, pos, state, RecipeType.SMELTING, block);
    }

    @Override
    protected Text getContainerName() {
        return Text.translatable("container.energy_furnace");
    }

    @Override
    protected ScreenHandler createScreenHandler(int syncId, PlayerInventory playerInventory) {
        return new EnergyFurnaceScreenHandler(syncId, playerInventory, this, this.propertyMap);
    }

    @Override
    public void writeScreenOpeningData(ServerPlayerEntity player, PacketByteBuf buf) {
        buf.writeBlockPos(pos);
    }
}

