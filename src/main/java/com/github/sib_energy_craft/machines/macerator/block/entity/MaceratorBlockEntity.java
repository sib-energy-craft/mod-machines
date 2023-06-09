package com.github.sib_energy_craft.machines.macerator.block.entity;

import com.github.sib_energy_craft.machines.macerator.block.MaceratorBlock;
import com.github.sib_energy_craft.machines.macerator.load.Entities;
import com.github.sib_energy_craft.machines.macerator.screen.MaceratorScreenHandler;
import com.github.sib_energy_craft.machines.screen.AbstractEnergyMachineScreenHandler;
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
public class MaceratorBlockEntity extends AbstractMaceratorBlockEntity<MaceratorBlock> {
    public MaceratorBlockEntity(@NotNull BlockPos pos,
                                @NotNull BlockState state,
                                @NotNull MaceratorBlock block) {
        super(Entities.MACERATOR, pos, state, block, 1);
    }

    @Override
    public @NotNull Text getDisplayName() {
        return Text.translatable("container.macerator");
    }

    @Override
    protected AbstractEnergyMachineScreenHandler createScreenHandler(int syncId,
                                                                     @NotNull PlayerInventory playerInventory,
                                                                     @NotNull PlayerEntity player) {
        return new MaceratorScreenHandler(syncId, playerInventory, this);
    }

    @Override
    public void writeScreenOpeningData(@NotNull ServerPlayerEntity player,
                                       @NotNull PacketByteBuf buf) {
        buf.writeBlockPos(pos);
    }

}

