package com.github.sib_energy_craft.machines.macerator.screen;

import com.github.sib_energy_craft.energy_api.screen.ChargeSlot;
import com.github.sib_energy_craft.energy_api.tags.CoreTags;
import com.github.sib_energy_craft.machines.block.entity.AbstractEnergyMachineProperties;
import com.github.sib_energy_craft.machines.macerator.block.entity.AbstractMaceratorBlockEntity;
import com.github.sib_energy_craft.machines.macerator.tag.MaceratorTags;
import com.github.sib_energy_craft.machines.screen.slot.OutputSlot;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.ArrayPropertyDelegate;
import net.minecraft.screen.PropertyDelegate;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.screen.slot.Slot;
import net.minecraft.world.World;
import org.jetbrains.annotations.NotNull;

/**
 * @since 0.0.1
 * @author sibmaks
 */
public abstract class AbstractMaceratorScreenHandler extends ScreenHandler {
    private final Inventory inventory;
    private final PropertyDelegate propertyDelegate;
    protected final World world;

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
        super(type, syncId);
        int i;
        AbstractMaceratorScreenHandler.checkSize(inventory, 3);
        AbstractMaceratorScreenHandler.checkDataCount(propertyDelegate, 4);
        this.inventory = inventory;
        this.propertyDelegate = propertyDelegate;
        this.world = playerInventory.player.world;
        this.addSlot(new Slot(inventory, AbstractMaceratorBlockEntity.SOURCE_SLOT, 56, 17));
        var chargeSlot = new ChargeSlot(inventory, AbstractMaceratorBlockEntity.CHARGE_SLOT, 56, 53, false);
        this.addSlot(chargeSlot);
        var outputSlot = new OutputSlot(playerInventory.player, inventory, AbstractMaceratorBlockEntity.OUTPUT_SLOT, 116, 35);
        this.addSlot(outputSlot);
        for (i = 0; i < 3; ++i) {
            for (int j = 0; j < 9; ++j) {
                this.addSlot(new Slot(playerInventory, j + i * 9 + 9, 8 + j * 18, 84 + i * 18));
            }
        }
        for (i = 0; i < 9; ++i) {
            this.addSlot(new Slot(playerInventory, i, 8 + i * 18, 142));
        }
        this.addProperties(propertyDelegate);
    }

    @Override
    public boolean canUse(@NotNull PlayerEntity player) {
        return this.inventory.canPlayerUse(player);
    }

    @NotNull
    @Override
    public ItemStack quickMove(@NotNull PlayerEntity player, int index) {
        var itemStack = ItemStack.EMPTY;
        var slot = this.slots.get(index);
        if (slot.hasStack()) {
            var slotStack = slot.getStack();
            itemStack = slotStack.copy();
            if(index == AbstractMaceratorBlockEntity.OUTPUT_SLOT) {
                if(!insertItem(slotStack, AbstractMaceratorBlockEntity.OUTPUT_SLOT + 1, 39, true)) {
                    return ItemStack.EMPTY;
                }
                slot.onQuickTransfer(slotStack, itemStack);
            } else if(index == AbstractMaceratorBlockEntity.CHARGE_SLOT || index == AbstractMaceratorBlockEntity.SOURCE_SLOT) {
                if(!insertItem(slotStack, AbstractMaceratorBlockEntity.OUTPUT_SLOT + 1, 39, false)) {
                    return ItemStack.EMPTY;
                }
            } else {
                if(MaceratorTags.isUsedInMacerator(slotStack)) {
                    if(!insertItem(slotStack, AbstractMaceratorBlockEntity.SOURCE_SLOT,
                                    AbstractMaceratorBlockEntity.SOURCE_SLOT + 1, false)) {
                        return ItemStack.EMPTY;
                    }
                } else if(CoreTags.isChargeable(slotStack)) {
                    if(!insertItem(slotStack, AbstractMaceratorBlockEntity.CHARGE_SLOT,
                                    AbstractMaceratorBlockEntity.CHARGE_SLOT + 1, false)) {
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

    /**
     * Get charge progress status
     *
     * @return charge progress
     */
    public int getChargeProgress() {
        int i = getCharge();
        int j = getMaxCharge();
        if (j == 0 || i == 0) {
            return 0;
        }
        return i * 13 / j;
    }

    /**
     * Get cook progress status
     *
     * @return cook progress
     */
    public int getCookProgress() {
        int i = getCookingTime();
        int j = getCookingTimeTotal();
        if (j == 0 || i == 0) {
            return 0;
        }
        return i * 22 / j;
    }

    /**
     * Get extractor charge
     *
     * @return charge
     */
    public int getCharge() {
        return propertyDelegate.get(AbstractEnergyMachineProperties.CHARGE.ordinal());
    }

    /**
     * Get extractor max charge
     *
     * @return max charge
     */
    public int getMaxCharge() {
        return propertyDelegate.get(AbstractEnergyMachineProperties.MAX_CHARGE.ordinal());
    }

    /**
     * Get extractor cooking time
     *
     * @return cooking time
     */
    public int getCookingTime() {
        return propertyDelegate.get(AbstractEnergyMachineProperties.COOKING_TIME.ordinal());
    }

    /**
     * Get extractor total cooking time
     *
     * @return total cooking time
     */
    public int getCookingTimeTotal() {
        return propertyDelegate.get(AbstractEnergyMachineProperties.COOKING_TIME_TOTAL.ordinal());
    }
}

