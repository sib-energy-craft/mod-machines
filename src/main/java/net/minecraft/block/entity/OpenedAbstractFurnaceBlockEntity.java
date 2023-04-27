package net.minecraft.block.entity;

import net.minecraft.block.BlockState;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.recipe.AbstractCookingRecipe;
import net.minecraft.recipe.Recipe;
import net.minecraft.recipe.RecipeManager;
import net.minecraft.recipe.RecipeType;
import net.minecraft.registry.DynamicRegistryManager;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * @since 0.0.1
 * @author sibmaks
 */
public abstract class OpenedAbstractFurnaceBlockEntity extends AbstractFurnaceBlockEntity {
    protected final RecipeManager.MatchGetter<Inventory, ? extends AbstractCookingRecipe> matchGetter;

    protected OpenedAbstractFurnaceBlockEntity(@NotNull BlockEntityType<?> blockEntityType,
                                               @NotNull BlockPos pos,
                                               @NotNull BlockState state,
                                               @NotNull RecipeType<? extends AbstractCookingRecipe> recipeType) {
        super(blockEntityType, pos, state, recipeType);
        this.matchGetter = RecipeManager.createCachedMatchGetter(recipeType);
    }

    protected static int getCookTime(@NotNull World world,
                                     @NotNull OpenedAbstractFurnaceBlockEntity furnace) {
        return furnace.matchGetter.getFirstMatch(furnace, world)
                .map(AbstractCookingRecipe::getCookTime)
                .orElse(200);
    }

    protected static boolean craftRecipe(@NotNull DynamicRegistryManager dynamicRegistryManager,
                                         @Nullable Recipe<?> recipe,
                                         @NotNull DefaultedList<ItemStack> slots,
                                         int count,
                                         int sourceSlotIndex,
                                         int outputSlotIndex) {
        if (recipe == null || !canAcceptRecipeOutput(dynamicRegistryManager, recipe, slots, count, sourceSlotIndex, outputSlotIndex)) {
            return false;
        }
        var sourceSlot = slots.get(sourceSlotIndex);
        var recipeStack = recipe.getOutput(dynamicRegistryManager);
        var outputStack = slots.get(outputSlotIndex);
        if (outputStack.isEmpty()) {
            slots.set(outputSlotIndex, recipeStack.copy());
        } else if (outputStack.isOf(recipeStack.getItem())) {
            outputStack.increment(recipeStack.getCount());
        }
        sourceSlot.decrement(1);
        return true;
    }

    protected static boolean canAcceptRecipeOutput(@NotNull DynamicRegistryManager dynamicRegistryManager,
                                                   @Nullable Recipe<?> recipe,
                                                   @NotNull DefaultedList<ItemStack> slots,
                                                   int count,
                                                   int sourceSlotIndex,
                                                   int outputSlotIndex) {
        if (slots.get(sourceSlotIndex).isEmpty() || recipe == null) {
            return false;
        }
        var outputStack = recipe.getOutput(dynamicRegistryManager);
        if (outputStack.isEmpty()) {
            return false;
        }
        var outputSlotStack = slots.get(outputSlotIndex);
        if (outputSlotStack.isEmpty()) {
            return true;
        }
        if (!outputSlotStack.isItemEqual(outputStack)) {
            return false;
        }
        if (outputSlotStack.getCount() < count && outputSlotStack.getCount() < outputSlotStack.getMaxCount()) {
            return true;
        }
        return outputSlotStack.getCount() < outputStack.getMaxCount();
    }

    protected boolean isBurning() {
        return this.burnTime > 0;
    }

    protected void decBurnTime() {
        burnTime--;
    }

    public int getBurnTime() {
        return burnTime;
    }

    public void setBurnTime(int burnTime) {
        this.burnTime = burnTime;
    }

    public int getFuelTime() {
        return fuelTime;
    }

    public void setFuelTime(int fuelTime) {
        this.fuelTime = fuelTime;
    }

    public int getCookTime() {
        return cookTime;
    }

    public void setCookTime(int cookTime) {
        this.cookTime = cookTime;
    }

    public int getCookTimeTotal() {
        return cookTimeTotal;
    }

    public void setCookTimeTotal(int cookTimeTotal) {
        this.cookTimeTotal = cookTimeTotal;
    }

    protected void incCookTime() {
        cookTime++;
    }
}
