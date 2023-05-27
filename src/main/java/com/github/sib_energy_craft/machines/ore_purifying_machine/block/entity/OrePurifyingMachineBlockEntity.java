package com.github.sib_energy_craft.machines.ore_purifying_machine.block.entity;

import com.github.sib_energy_craft.machines.ore_purifying_machine.block.OrePurifyingMachineBlock;
import com.github.sib_energy_craft.machines.ore_purifying_machine.load.Entities;
import com.github.sib_energy_craft.machines.ore_purifying_machine.screen.OrePurifyingMachineScreenHandler;
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
 * @since 0.0.26
 * @author sibmaks
 */
public class OrePurifyingMachineBlockEntity extends AbstractOrePurifyingMachineBlockEntity<OrePurifyingMachineBlock> {

    public OrePurifyingMachineBlockEntity(@NotNull BlockPos pos,
                                          @NotNull BlockState state,
                                          @NotNull OrePurifyingMachineBlock block) {
        super(Entities.ORE_PURIFYING_MACHINE, pos, state, block, 1);
    }

    @Override
    public void writeScreenOpeningData(@NotNull ServerPlayerEntity player,
                                       @NotNull PacketByteBuf buf) {
        buf.writeInt(block.getMaxDrumSpeed());
    }

    @Override
    protected AbstractEnergyMachineScreenHandler createScreenHandler(int syncId,
                                                                     @NotNull PlayerInventory playerInventory,
                                                                     @NotNull PlayerEntity player) {
        return new OrePurifyingMachineScreenHandler(syncId, playerInventory, this, block.getMaxDrumSpeed());
    }

    @Override
    public @NotNull Text getDisplayName() {
        return Text.translatable("container.ore_purifying_machine");
    }

}

