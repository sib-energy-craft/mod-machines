package com.github.sib_energy_craft.machines.extractor.screen;

import com.github.sib_energy_craft.energy_api.screen.ChargeSlot;
import com.github.sib_energy_craft.energy_api.tags.CoreTags;
import com.github.sib_energy_craft.machines.block.entity.AbstractEnergyMachineProperties;
import com.github.sib_energy_craft.machines.extractor.block.entity.AbstractExtractorBlockEntity;
import com.github.sib_energy_craft.machines.extractor.tag.ExtractorTags;
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
public abstract class AbstractExtractorScreenHandler extends ScreenHandler {
    private final Inventory inventory;
    private final PropertyDelegate propertyDelegate;
    protected final World world;

    protected AbstractExtractorScreenHandler(@NotNull ScreenHandlerType<?> type,
                                             int syncId,
                                             @NotNull PlayerInventory playerInventory) {
        this(type, syncId, playerInventory, new SimpleInventory(3), new ArrayPropertyDelegate(4));
    }

    protected AbstractExtractorScreenHandler(@NotNull ScreenHandlerType<?> type,
                                             int syncId,
                                             @NotNull PlayerInventory playerInventory,
                                             @NotNull Inventory inventory,
                                             @NotNull PropertyDelegate propertyDelegate) {
        super(type, syncId);
        AbstractExtractorScreenHandler.checkSize(inventory, 3);
        AbstractExtractorScreenHandler.checkDataCount(propertyDelegate, 4);
        this.inventory = inventory;
        this.propertyDelegate = propertyDelegate;
        this.world = playerInventory.player.world;
        this.addSlot(new Slot(inventory, AbstractExtractorBlockEntity.SOURCE_SLOT, 56, 17));
        var chargeSlot = new ChargeSlot(inventory, AbstractExtractorBlockEntity.CHARGE_SLOT, 56, 53, false);
        this.addSlot(chargeSlot);
        var outputSlot = new OutputSlot(playerInventory.player, inventory, AbstractExtractorBlockEntity.OUTPUT_SLOT, 116, 35);
        this.addSlot(outputSlot);
        for (int i = 0; i < 3; ++i) {
            for (int j = 0; j < 9; ++j) {
                this.addSlot(new Slot(playerInventory, j + i * 9 + 9, 8 + j * 18, 84 + i * 18));
            }
        }
        for (int i = 0; i < 9; ++i) {
            this.addSlot(new Slot(playerInventory, i, 8 + i * 18, 142));
        }
        this.addProperties(propertyDelegate);
    }

    @Override
    public boolean canUse(@NotNull PlayerEntity player) {
        return this.inventory.canPlayerUse(player);
    }

    @Override
    public @NotNull ItemStack quickMove(@NotNull PlayerEntity player, int index) {
        var itemStack = ItemStack.EMPTY;
        var slot = this.slots.get(index);
        if (slot.hasStack()) {
            var slotStack = slot.getStack();
            itemStack = slotStack.copy();
            if(index == AbstractExtractorBlockEntity.OUTPUT_SLOT) {
                if(this.insertItem(slotStack, AbstractExtractorBlockEntity.OUTPUT_SLOT + 1, 39, true)) {
                    return ItemStack.EMPTY;
                }
                slot.onQuickTransfer(slotStack, itemStack);
            } else if(index == AbstractExtractorBlockEntity.CHARGE_SLOT || index == AbstractExtractorBlockEntity.SOURCE_SLOT) {
                if(this.insertItem(slotStack, AbstractExtractorBlockEntity.OUTPUT_SLOT + 1, 39, false)) {
                    return ItemStack.EMPTY;
                }
            } else {
                if(ExtractorTags.isUsedInExtractor(slotStack)) {
                    if(this.insertItem(slotStack, AbstractExtractorBlockEntity.SOURCE_SLOT,
                                    AbstractExtractorBlockEntity.SOURCE_SLOT + 1, false)) {
                        return ItemStack.EMPTY;
                    }
                } else if(CoreTags.isChargeable(slotStack)) {
                    if(this.insertItem(slotStack, AbstractExtractorBlockEntity.CHARGE_SLOT,
                                    AbstractExtractorBlockEntity.CHARGE_SLOT + 1, false)) {
                        return ItemStack.EMPTY;
                    }
                } else if(index >= 3 && index < 30 && this.insertItem(slotStack, 30, 39, false)) {
                    return ItemStack.EMPTY;
                } else if(index >= 30 && index < 39 && this.insertItem(slotStack, 3, 30, false)) {
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
        return this.propertyDelegate.get(AbstractEnergyMachineProperties.CHARGE.ordinal());
    }

    /**
     * Get extractor max charge
     *
     * @return max charge
     */
    public int getMaxCharge() {
        return this.propertyDelegate.get(AbstractEnergyMachineProperties.MAX_CHARGE.ordinal());
    }

    /**
     * Get extractor cooking time
     *
     * @return cooking time
     */
    public int getCookingTime() {
        return this.propertyDelegate.get(AbstractEnergyMachineProperties.COOKING_TIME.ordinal());
    }

    /**
     * Get extractor total cooking time
     *
     * @return total cooking time
     */
    public int getCookingTimeTotal() {
        return this.propertyDelegate.get(AbstractEnergyMachineProperties.COOKING_TIME_TOTAL.ordinal());
    }
}

