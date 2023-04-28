package com.github.sib_energy_craft.energy_container.block.entity;

import com.github.sib_energy_craft.energy_container.block.EnergyContainerBlock;
import com.github.sib_energy_craft.energy_container.load.Entities;
import com.github.sib_energy_craft.energy_container.screen.EnergyContainerScreenHandler;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.math.BlockPos;
import org.jetbrains.annotations.NotNull;

/**
 * @since 0.0.2
 * @author sibmaks
 */
public class EnergyContainerBlockEntity extends AbstractEnergyContainerBlockEntity {
    public EnergyContainerBlockEntity(@NotNull BlockPos pos,
                                      @NotNull BlockState state,
                                      @NotNull EnergyContainerBlock block) {
        super(Entities.BASIC_ENERGY_CONTAINER, pos, state, block.getContainerNameCode(), block);
    }

    @Override
    protected ScreenHandler createScreenHandler(int syncId,
                                                @NotNull PlayerInventory playerInventory) {
        return new EnergyContainerScreenHandler(syncId, playerInventory, this, this.propertyMap);
    }

    @Override
    public void writeScreenOpeningData(@NotNull ServerPlayerEntity player,
                                       @NotNull PacketByteBuf buf) {
        buf.writeBlockPos(pos);
    }
}

