package com.github.sib_energy_craft.machines.macerator.screen;

import com.github.sib_energy_craft.machines.cooking.screen.CookingEnergyMachineScreenHandler;
import com.github.sib_energy_craft.machines.cooking.screen.CookingEnergyMachineState;
import com.github.sib_energy_craft.machines.macerator.tag.MaceratorTags;
import com.github.sib_energy_craft.machines.screen.layout.SlotLayoutManager;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.ScreenHandlerType;
import org.jetbrains.annotations.NotNull;

/**
 * @since 0.0.1
 * @author sibmaks
 */
public abstract class AbstractMaceratorScreenHandler
        extends CookingEnergyMachineScreenHandler<CookingEnergyMachineState> {

    protected AbstractMaceratorScreenHandler(@NotNull ScreenHandlerType<?> type,
                                                 int syncId,
                                                 @NotNull PlayerInventory playerInventory,
                                                 int parallelProcess,
                                                 @NotNull SlotLayoutManager slotLayoutManager) {
        super(type, syncId, playerInventory, parallelProcess, parallelProcess, new CookingEnergyMachineState(),
                slotLayoutManager);
    }

    protected AbstractMaceratorScreenHandler(@NotNull ScreenHandlerType<?> type,
                                                 int syncId,
                                                 @NotNull PlayerInventory playerInventory,
                                                 @NotNull Inventory inventory,
                                                 int parallelProcess,
                                                 @NotNull SlotLayoutManager slotLayoutManager) {
        super(type, syncId, playerInventory, inventory, parallelProcess, parallelProcess, new CookingEnergyMachineState(),
                slotLayoutManager);
    }

    @Override
    protected boolean isUsedInMachine(@NotNull ItemStack itemStack) {
        return MaceratorTags.isUsedInMacerator(itemStack);
    }
}

