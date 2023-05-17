package com.github.sib_energy_craft.machines.cutting_machine.screen;

import com.github.sib_energy_craft.machines.cutting_machine.utils.CuttingMachineUtils;
import com.github.sib_energy_craft.machines.screen.AbstractEnergyMachineScreenHandler;
import com.github.sib_energy_craft.machines.screen.layout.SlotLayoutManager;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.PropertyDelegate;
import net.minecraft.screen.ScreenHandlerType;
import org.jetbrains.annotations.NotNull;

/**
 * @since 0.0.17
 * @author sibmaks
 */
public abstract class AbstractCuttingMachineScreenHandler extends AbstractEnergyMachineScreenHandler {

    protected AbstractCuttingMachineScreenHandler(@NotNull ScreenHandlerType<?> type,
                                                int syncId,
                                                @NotNull PlayerInventory playerInventory,
                                                @NotNull SlotLayoutManager slotLayoutManager) {
        super(type, syncId, playerInventory, slotLayoutManager);
    }

    protected AbstractCuttingMachineScreenHandler(@NotNull ScreenHandlerType<?> type,
                                                int syncId,
                                                @NotNull PlayerInventory playerInventory,
                                                @NotNull Inventory inventory,
                                                @NotNull PropertyDelegate propertyDelegate,
                                                @NotNull SlotLayoutManager slotLayoutManager) {
        super(type, syncId, playerInventory, inventory, propertyDelegate, slotLayoutManager);
    }

    @Override
    protected boolean isUsedInMachine(@NotNull ItemStack itemStack) {
        return CuttingMachineUtils.isUsedInCuttingMachine(world, itemStack);
    }
}

