package com.github.sib_energy_craft.machines.macerator.screen;

import com.github.sib_energy_craft.energy_api.tags.CoreTags;
import com.github.sib_energy_craft.machines.macerator.tag.MaceratorTags;
import com.github.sib_energy_craft.machines.screen.slot.AbstractEnergyMachineScreenHandler;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.ArrayPropertyDelegate;
import net.minecraft.screen.PropertyDelegate;
import net.minecraft.screen.ScreenHandlerType;
import org.jetbrains.annotations.NotNull;

import static com.github.sib_energy_craft.machines.macerator.block.entity.AbstractMaceratorBlockEntity.*;

/**
 * @since 0.0.1
 * @author sibmaks
 */
public abstract class AbstractMaceratorScreenHandler extends AbstractEnergyMachineScreenHandler {

    protected AbstractMaceratorScreenHandler(@NotNull ScreenHandlerType<?> type,
                                             int syncId,
                                             @NotNull PlayerInventory playerInventory) {
        this(type, syncId, playerInventory, new SimpleInventory(3), new ArrayPropertyDelegate(4));
    }

    protected AbstractMaceratorScreenHandler(@NotNull ScreenHandlerType<?> type,
                                             int syncId,
                                             @NotNull PlayerInventory playerInventory,
                                             @NotNull Inventory inventory,
                                             @NotNull PropertyDelegate propertyDelegate) {
        super(type, syncId, playerInventory, inventory, propertyDelegate);
    }

    @NotNull
    @Override
    public ItemStack quickMove(@NotNull PlayerEntity player, int index) {
        var itemStack = ItemStack.EMPTY;
        var slot = this.slots.get(index);
        if (slot.hasStack()) {
            var slotStack = slot.getStack();
            itemStack = slotStack.copy();
            if(index == OUTPUT_SLOT) {
                if(!insertItem(slotStack, OUTPUT_SLOT + 1, 39, true)) {
                    return ItemStack.EMPTY;
                }
                slot.onQuickTransfer(slotStack, itemStack);
            } else if(index == CHARGE_SLOT || index == SOURCE_SLOT) {
                if(!insertItem(slotStack, OUTPUT_SLOT + 1, 39, false)) {
                    return ItemStack.EMPTY;
                }
            } else {
                if(MaceratorTags.isUsedInMacerator(slotStack)) {
                    if(!insertItem(slotStack, SOURCE_SLOT, SOURCE_SLOT + 1, false)) {
                        return ItemStack.EMPTY;
                    }
                } else if(CoreTags.isChargeable(slotStack)) {
                    if(!insertItem(slotStack, CHARGE_SLOT, CHARGE_SLOT + 1, false)) {
                        return ItemStack.EMPTY;
                    }
                } else if(index >= 3 && index < 30 && !insertItem(slotStack, 30, 39, false)) {
                    return ItemStack.EMPTY;
                } else if(index >= 30 && index < 39 && !insertItem(slotStack, 3, 30, false)) {
                    return ItemStack.EMPTY;
                }
            }
            if (slotStack.isEmpty()) {
                slot.setStack(ItemStack.EMPTY);
            } else {
                slot.markDirty();
            }
            if (slotStack.getCount() == itemStack.getCount()) {
                return ItemStack.EMPTY;
            }
            slot.onTakeItem(player, slotStack);
        }
        return itemStack;
    }
}

