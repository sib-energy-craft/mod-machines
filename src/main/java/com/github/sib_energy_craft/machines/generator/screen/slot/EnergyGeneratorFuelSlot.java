package com.github.sib_energy_craft.machines.generator.screen.slot;

import com.github.sib_energy_craft.machines.generator.screen.AbstractEnergyGeneratorScreenHandler;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.slot.FurnaceFuelSlot;
import net.minecraft.screen.slot.Slot;
import org.jetbrains.annotations.NotNull;

/**
 * @since 0.0.1
 * @author sibmaks
 */
public class EnergyGeneratorFuelSlot extends Slot {

    public EnergyGeneratorFuelSlot(@NotNull Inventory inventory, int index, int x, int y) {
        super(inventory, index, x, y);
    }

    @Override
    public boolean canInsert(@NotNull ItemStack stack) {
        return AbstractEnergyGeneratorScreenHandler.isFuel(stack) || FurnaceFuelSlot.isBucket(stack);
    }

    @Override
    public int getMaxItemCount(@NotNull ItemStack stack) {
        return FurnaceFuelSlot.isBucket(stack) ? 1 : super.getMaxItemCount(stack);
    }

}
