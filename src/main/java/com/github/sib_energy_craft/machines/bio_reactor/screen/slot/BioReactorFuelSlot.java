package com.github.sib_energy_craft.machines.bio_reactor.screen.slot;

import com.github.sib_energy_craft.machines.bio_reactor.BioFuelRegistry;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.slot.Slot;
import org.jetbrains.annotations.NotNull;

/**
 * @since 0.0.23
 * @author sibmaks
 */
public class BioReactorFuelSlot extends Slot {

    public BioReactorFuelSlot(@NotNull Inventory inventory, int index, int x, int y) {
        super(inventory, index, x, y);
    }

    @Override
    public boolean canInsert(@NotNull ItemStack stack) {
        return BioFuelRegistry.isFuel(stack);
    }
}
