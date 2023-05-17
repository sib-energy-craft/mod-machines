package com.github.sib_energy_craft.machines.energy_furnace.screen;

import com.github.sib_energy_craft.machines.screen.AbstractEnergyMachineScreenHandler;
import com.github.sib_energy_craft.machines.screen.layout.SlotLayoutManager;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.recipe.RecipeType;
import net.minecraft.screen.ArrayPropertyDelegate;
import net.minecraft.screen.PropertyDelegate;
import net.minecraft.screen.ScreenHandlerType;
import org.jetbrains.annotations.NotNull;

/**
 * @since 0.0.2
 * @author sibmaks
 */
public abstract class AbstractEnergyFurnaceScreenHandler extends AbstractEnergyMachineScreenHandler {

    protected AbstractEnergyFurnaceScreenHandler(@NotNull ScreenHandlerType<?> type,
                                             int syncId,
                                             @NotNull PlayerInventory playerInventory,
                                             int slots,
                                             @NotNull SlotLayoutManager slotLayoutManager) {
        super(type, syncId, playerInventory, slots, slotLayoutManager);
    }
    protected AbstractEnergyFurnaceScreenHandler(@NotNull ScreenHandlerType<?> type,
                                                 int syncId,
                                                 @NotNull PlayerInventory playerInventory,
                                                 int properties,
                                                 int slots,
                                                 @NotNull SlotLayoutManager slotLayoutManager) {
        super(type, syncId, playerInventory, new SimpleInventory(1 + 2 * slots),
                new ArrayPropertyDelegate(properties), slots, slotLayoutManager);
    }

    protected AbstractEnergyFurnaceScreenHandler(@NotNull ScreenHandlerType<?> type,
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
        return isSmeltable(itemStack);
    }

    /**
     * Check is item stack smeltable
     *
     * @param itemStack item stack
     * @return true - stack is smeltable, false - otherwise
     */
    protected boolean isSmeltable(@NotNull ItemStack itemStack) {
        var simpleInventory = new SimpleInventory(itemStack);
        var recipeManager = this.world.getRecipeManager();
        return recipeManager.getFirstMatch(RecipeType.SMELTING, simpleInventory, this.world).isPresent();
    }
}

