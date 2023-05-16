package com.github.sib_energy_craft.machines.press_machine.utils;

import com.github.sib_energy_craft.metallurgy.iron_craft_table.load.Items;
import com.github.sib_energy_craft.recipes.recipe.IronCraftingTableRecipeType;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import org.jetbrains.annotations.NotNull;

/**
 * @author sibmaks
 * @since 0.0.16
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class PressMachineUtils {
    private static final SimpleInventory craftingInventory;

    static {
        craftingInventory = new SimpleInventory(2);
        craftingInventory.setStack(0, new ItemStack(Items.IRON_HAMMER, 1));
    }

    /**
     * Check can item be used in press machine
     *
     * @param world game world
     * @param itemStack stack to check
     * @return true - item can be used in machine, false - otherwise
     */
    public static boolean isUsedInPressMachine(@NotNull World world, @NotNull ItemStack itemStack) {
        var recipeManager = world.getRecipeManager();
        craftingInventory.setStack(1, itemStack);
        return recipeManager.getFirstMatch(IronCraftingTableRecipeType.INSTANCE, craftingInventory, world).isPresent();
    }
}
