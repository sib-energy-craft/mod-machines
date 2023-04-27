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
import org.jetbrains.annotations.Nullable;

/**
 * @author sibmaks
 * Created at 22-12-2022
 */
public abstract class OpenedAbstractFurnaceBlockEntity extends AbstractFurnaceBlockEntity {
    protected final RecipeManager.MatchGetter<Inventory, ? extends AbstractCookingRecipe> matchGetter;

    protected OpenedAbstractFurnaceBlockEntity(BlockEntityType<?> blockEntityType,
                                               BlockPos pos,
                                               BlockState state,
                                               RecipeType<? extends AbstractCookingRecipe> recipeType) {
        super(blockEntityType, pos, state, recipeType);
        this.matchGetter = RecipeManager.createCachedMatchGetter(recipeType);
    }

    protected static int getCookTime(World world, OpenedAbstractFurnaceBlockEntity furnace) {
        return furnace.matchGetter.getFirstMatch(furnace, world)
                .map(AbstractCookingRecipe::getCookTime)
                .orElse(200);
    }

    protected static boolean craftRecipe(DynamicRegistryManager dynamicRegistryManager,
                                         @Nullable Recipe<?> recipe,
                                         DefaultedList<ItemStack> slots,
                                         int count,
                                         int sourceSlotIndex,
                                         int outputSlotIndex) {
        if (recipe == null || !canAcceptRecipeOutput(dynamicRegistryManager, recipe, slots, count, sourceSlotIndex, outputSlotIndex)) {
            return false;
        }
        ItemStack sourceSlot = slots.get(sourceSlotIndex);
        ItemStack recipeStack = recipe.getOutput(dynamicRegistryManager);
        ItemStack outputStack = slots.get(outputSlotIndex);
        if (outputStack.isEmpty()) {
            slots.set(outputSlotIndex, recipeStack.copy());
        } else if (outputStack.isOf(recipeStack.getItem())) {
            outputStack.increment(recipeStack.getCount());
        }
        sourceSlot.decrement(1);
        return true;
    }

    protected static boolean canAcceptRecipeOutput(DynamicRegistryManager dynamicRegistryManager,
                                                   @Nullable Recipe<?> recipe,
                                                   DefaultedList<ItemStack> slots,
                                                   int count,
                                                   int sourceSlotIndex,
                                                   int outputSlotIndex) {
        if (slots.get(sourceSlotIndex).isEmpty() || recipe == null) {
            return false;
        }
        ItemStack outputStack = recipe.getOutput(dynamicRegistryManager);
        if (outputStack.isEmpty()) {
            return false;
        }
        ItemStack outputSlotStack = slots.get(outputSlotIndex);
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
