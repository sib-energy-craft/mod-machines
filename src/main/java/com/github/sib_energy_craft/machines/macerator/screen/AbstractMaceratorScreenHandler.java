package com.github.sib_energy_craft.machines.macerator.screen;

import com.github.sib_energy_craft.machines.macerator.tag.MaceratorTags;
import com.github.sib_energy_craft.machines.screen.AbstractEnergyMachineScreenHandler;
import com.github.sib_energy_craft.machines.screen.layout.SlotLayoutManager;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.ArrayPropertyDelegate;
import net.minecraft.screen.PropertyDelegate;
import net.minecraft.screen.ScreenHandlerType;
import org.jetbrains.annotations.NotNull;

/**
 * @since 0.0.1
 * @author sibmaks
 */
public abstract class AbstractMaceratorScreenHandler extends AbstractEnergyMachineScreenHandler {

    protected AbstractMaceratorScreenHandler(@NotNull ScreenHandlerType<?> type,
                                                 int syncId,
                                                 @NotNull PlayerInventory playerInventory,
                                                 int slots,
                                                 @NotNull SlotLayoutManager slotLayoutManager) {
        super(type, syncId, playerInventory, slots, slotLayoutManager);
    }
    protected AbstractMaceratorScreenHandler(@NotNull ScreenHandlerType<?> type,
                                                 int syncId,
                                                 @NotNull PlayerInventory playerInventory,
                                                 int properties,
                                                 int slots,
                                                 @NotNull SlotLayoutManager slotLayoutManager) {
        super(type, syncId, playerInventory, new SimpleInventory(1 + 2 * slots),
                new ArrayPropertyDelegate(properties), slots, slotLayoutManager);
    }

    protected AbstractMaceratorScreenHandler(@NotNull ScreenHandlerType<?> type,
                                                 int syncId,
                                                 @NotNull PlayerInventory playerInventory,
                                                 @NotNull Inventory inventory,
                                                 @NotNull PropertyDelegate propertyDelegate,
                                                 int slots,
                                                 @NotNull SlotLayoutManager slotLayoutManager) {
        super(type, syncId, playerInventory, inventory, propertyDelegate, slots, slotLayoutManager);
    }

    @Override
    protected boolean isUsedInMachine(@NotNull ItemStack itemStack) {
        return MaceratorTags.isUsedInMacerator(itemStack);
    }
}

