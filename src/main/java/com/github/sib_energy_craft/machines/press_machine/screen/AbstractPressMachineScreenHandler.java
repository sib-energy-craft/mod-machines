package com.github.sib_energy_craft.machines.press_machine.screen;

import com.github.sib_energy_craft.machines.press_machine.utils.PressMachineUtils;
import com.github.sib_energy_craft.machines.screen.AbstractEnergyMachineScreenHandler;
import com.github.sib_energy_craft.machines.screen.VisualSettings;
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
public abstract class AbstractPressMachineScreenHandler extends AbstractEnergyMachineScreenHandler {

    protected AbstractPressMachineScreenHandler(@NotNull ScreenHandlerType<?> type,
                                                int syncId,
                                                @NotNull PlayerInventory playerInventory,
                                                @NotNull VisualSettings visualSettings) {
        super(type, syncId, playerInventory, visualSettings);
    }

    protected AbstractPressMachineScreenHandler(@NotNull ScreenHandlerType<?> type,
                                                int syncId,
                                                @NotNull PlayerInventory playerInventory,
                                                @NotNull Inventory inventory,
                                                @NotNull PropertyDelegate propertyDelegate,
                                                @NotNull VisualSettings visualSettings) {
        super(type, syncId, playerInventory, inventory, propertyDelegate, visualSettings);
    }

    @Override
    protected boolean isUsedInMachine(@NotNull ItemStack itemStack) {
        return PressMachineUtils.isUsedInPressMachine(world, itemStack);
    }
}
