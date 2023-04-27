package com.github.sib_energy_craft.machines.generator.screen;

import com.github.sib_energy_craft.energy_api.screen.ChargeSlot;
import com.github.sib_energy_craft.energy_api.tags.CoreTags;
import com.github.sib_energy_craft.machines.generator.block.entity.AbstractEnergyGeneratorBlockEntity;
import com.github.sib_energy_craft.machines.generator.block.entity.EnergyGeneratorProperties;
import com.github.sib_energy_craft.machines.generator.screen.slot.EnergyGeneratorFuelSlot;
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
public abstract class AbstractEnergyGeneratorScreenHandler extends ScreenHandler {
    private final Inventory inventory;
    private final PropertyDelegate propertyDelegate;
    protected final World world;

    protected AbstractEnergyGeneratorScreenHandler(@NotNull ScreenHandlerType<?> type,
                                                   int syncId,
                                                   @NotNull PlayerInventory playerInventory) {
        this(type, syncId, playerInventory, new SimpleInventory(2), new ArrayPropertyDelegate(5));
    }

    protected AbstractEnergyGeneratorScreenHandler(@NotNull ScreenHandlerType<?> type,
                                                   int syncId,
                                                   @NotNull PlayerInventory playerInventory,
                                                   @NotNull Inventory inventory,
                                                   @NotNull PropertyDelegate propertyDelegate) {
        super(type, syncId);
        int i;
        AbstractEnergyGeneratorScreenHandler.checkSize(inventory, 2);
        AbstractEnergyGeneratorScreenHandler.checkDataCount(propertyDelegate, 5);
        this.inventory = inventory;
        this.propertyDelegate = propertyDelegate;
        this.world = playerInventory.player.world;
        var chargeSlot = new ChargeSlot(inventory, AbstractEnergyGeneratorBlockEntity.CHARGE_SLOT_INDEX, 56, 17, true);
        this.addSlot(chargeSlot);
        var fuelSlot = new EnergyGeneratorFuelSlot(inventory, AbstractEnergyGeneratorBlockEntity.FUEL_SLOT_INDEX, 56, 53);
        this.addSlot(fuelSlot);
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
            if(index == AbstractEnergyGeneratorBlockEntity.CHARGE_SLOT_INDEX ||
                    index == AbstractEnergyGeneratorBlockEntity.FUEL_SLOT_INDEX) {
                if(!insertItem(slotStack, 2, 38, false)) {
                    return ItemStack.EMPTY;
                }
            } else {
                if(CoreTags.isChargeable(slotStack)) {
                    if(!insertItem(slotStack, 0, 1, false)) {
                        return ItemStack.EMPTY;
                    }
                } else {
                    if(isFuel(slotStack)) {
                        if(!insertItem(slotStack, 1, 2, false)) {
                            return ItemStack.EMPTY;
                        }
                    } else {
                        if(index >= 2 && index < 29) {
                            if(!insertItem(slotStack, 29, 38, false)) {
                                return ItemStack.EMPTY;
                            }
                        } else if(index >= 29 && index < 38) {
                            if(!insertItem(slotStack, 2, 29, false)) {
                                return ItemStack.EMPTY;
                            }
                        }
                    }
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
     * Check is item can be used as fuel
     *
     * @param itemStack item stack
     * @return true - item is fuel, false - otherwise
     */
    public static boolean isFuel(@NotNull ItemStack itemStack) {
        return AbstractEnergyGeneratorBlockEntity.canUseAsFuel(itemStack);
    }

    public int getFuelProgress() {
        int i = propertyDelegate.get(EnergyGeneratorProperties.FUEL_TIME.ordinal());
        if (i == 0) {
            i = 200;
        }
        return propertyDelegate.get(EnergyGeneratorProperties.BURN_TIME.ordinal()) * 13 / i;
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
        return i * 24 / j;
    }

    /**
     * Get extractor charge
     *
     * @return charge
     */
    public int getCharge() {
        return propertyDelegate.get(EnergyGeneratorProperties.CHARGE.ordinal());
    }

    /**
     * Get extractor max charge
     *
     * @return max charge
     */
    public int getMaxCharge() {
        return propertyDelegate.get(EnergyGeneratorProperties.MAX_CHARGE.ordinal());
    }

    /**
     * Get extractor cooking time
     *
     * @return cooking time
     */
    public int getEnergyPacketSize() {
        return propertyDelegate.get(EnergyGeneratorProperties.ENERGY_PACKET_SIZE.ordinal());
    }

    /**
     * Get extractor total cooking time
     *
     * @return total cooking time
     */
    public boolean isBurning() {
        return propertyDelegate.get(EnergyGeneratorProperties.BURN_TIME.ordinal()) > 0;
    }

}

