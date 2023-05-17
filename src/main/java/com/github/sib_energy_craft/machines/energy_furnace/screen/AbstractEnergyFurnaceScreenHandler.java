package com.github.sib_energy_craft.machines.energy_furnace.screen;

import com.github.sib_energy_craft.machines.screen.AbstractEnergyMachineScreenHandler;
import com.github.sib_energy_craft.machines.screen.layout.SlotLayoutManager;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.recipe.AbstractCookingRecipe;
import net.minecraft.recipe.RecipeType;
import net.minecraft.screen.PropertyDelegate;
import net.minecraft.screen.ScreenHandlerType;
import org.jetbrains.annotations.NotNull;

/**
 * @since 0.0.2
 * @author sibmaks
 */
public abstract class AbstractEnergyFurnaceScreenHandler extends AbstractEnergyMachineScreenHandler {
    private final RecipeType<? extends AbstractCookingRecipe> recipeType;

    protected AbstractEnergyFurnaceScreenHandler(@NotNull ScreenHandlerType<?> type,
                                             int syncId,
                                             @NotNull PlayerInventory playerInventory,
                                             @NotNull RecipeType<? extends AbstractCookingRecipe> recipeType,
                                             int slots,
                                             @NotNull SlotLayoutManager slotLayoutManager) {
        super(type, syncId, playerInventory, slots, slotLayoutManager);
        this.recipeType = recipeType;
    }

    protected AbstractEnergyFurnaceScreenHandler(@NotNull ScreenHandlerType<?> type,
                                                 int syncId,
                                                 @NotNull PlayerInventory playerInventory,
                                                 @NotNull Inventory inventory,
                                                 @NotNull PropertyDelegate propertyDelegate,
                                                 @NotNull RecipeType<? extends AbstractCookingRecipe> recipeType,
                                                 int slots,
                                                 @NotNull SlotLayoutManager slotLayoutManager) {
        super(type, syncId, playerInventory, inventory, propertyDelegate, slots, slotLayoutManager);
        this.recipeType = recipeType;
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
        return recipeManager.getFirstMatch(this.recipeType, simpleInventory, this.world).isPresent();
    }
}

