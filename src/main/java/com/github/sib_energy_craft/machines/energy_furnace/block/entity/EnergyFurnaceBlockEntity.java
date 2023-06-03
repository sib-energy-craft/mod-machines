package com.github.sib_energy_craft.machines.energy_furnace.block.entity;

import com.github.sib_energy_craft.machines.energy_furnace.block.EnergyFurnaceBlock;
import com.github.sib_energy_craft.machines.energy_furnace.load.Entities;
import com.github.sib_energy_craft.machines.energy_furnace.screen.EnergyFurnaceScreenHandler;
import com.github.sib_energy_craft.machines.screen.AbstractEnergyMachineScreenHandler;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.text.Text;
import net.minecraft.util.math.BlockPos;
import org.jetbrains.annotations.NotNull;


/**
 * @since 0.0.2
 * @author sibmaks
 */
public class EnergyFurnaceBlockEntity extends AbstractEnergyFurnaceBlockEntity<EnergyFurnaceBlock> {
    public EnergyFurnaceBlockEntity(BlockPos pos, BlockState state, EnergyFurnaceBlock block) {
        super(Entities.ENERGY_FURNACE, pos, state, block, 1);
    }

    @Override
    public @NotNull Text getDisplayName() {
        return Text.translatable("container.energy_furnace");
    }

    @Override
    protected AbstractEnergyMachineScreenHandler<?> createScreenHandler(int syncId,
                                                                     @NotNull PlayerInventory playerInventory,
                                                                     @NotNull PlayerEntity player) {
        return new EnergyFurnaceScreenHandler(syncId, playerInventory, this);
    }

}

