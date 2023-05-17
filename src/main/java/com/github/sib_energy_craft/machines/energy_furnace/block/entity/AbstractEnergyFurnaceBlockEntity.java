package com.github.sib_energy_craft.machines.energy_furnace.block.entity;

import com.github.sib_energy_craft.energy_api.consumer.EnergyConsumer;
import com.github.sib_energy_craft.machines.block.entity.AbstractEnergyMachineBlockEntity;
import com.github.sib_energy_craft.machines.block.entity.EnergyMachineEvent;
import com.github.sib_energy_craft.machines.block.entity.EnergyMachineInventoryType;
import com.github.sib_energy_craft.machines.energy_furnace.block.AbstractEnergyFurnaceBlock;
import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerFactory;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.recipe.Recipe;
import net.minecraft.recipe.RecipeType;
import net.minecraft.recipe.SmeltingRecipe;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;


/**
 * @since 0.0.2
 * @author sibmaks
 */
public abstract class AbstractEnergyFurnaceBlockEntity extends AbstractEnergyMachineBlockEntity
        implements ExtendedScreenHandlerFactory, EnergyConsumer {

    protected final RecipeType<SmeltingRecipe> recipeType;

    protected AbstractEnergyFurnaceBlockEntity(@NotNull BlockEntityType<?> blockEntityType,
                                               @NotNull BlockPos pos,
                                               @NotNull BlockState state,
                                               @NotNull RecipeType<SmeltingRecipe> recipeType,
                                               @NotNull AbstractEnergyFurnaceBlock block,
                                               int slots) {
        super(blockEntityType, pos, state, block, slots);
        this.recipeType = recipeType;
        this.addListener(EnergyMachineEvent.ENERGY_NOT_ENOUGH,
                () -> cookTime = MathHelper.clamp(cookTime - 2, 0, cookTimeTotal));
    }

    @Override
    public boolean isValid(int slot, @NotNull ItemStack stack) {
        var slotType = inventory.getType(slot);
        if(slotType == EnergyMachineInventoryType.SOURCE) {
            return isSmeltable(stack);
        }
        return super.isValid(slot, stack);
    }

    protected boolean isSmeltable(@NotNull ItemStack itemStack) {
        var world = this.world;
        if(world == null) {
            return false;
        }
        var recipeManager = world.getRecipeManager();
        var simpleInventory = new SimpleInventory(itemStack);
        return recipeManager.getFirstMatch(this.recipeType, simpleInventory, world).isPresent();
    }

    @Override
    public int getCookTimeTotal(@NotNull World world) {
        return (int) (getSmeltingCookTime(world, recipeType, this) *
                        ((AbstractEnergyFurnaceBlock)block).getCookingTotalTimeMultiplier());
    }

    protected static<T extends SmeltingRecipe> int getSmeltingCookTime(@NotNull World world,
                                                                       @NotNull RecipeType<T> recipeType,
                                                                       @NotNull Inventory inventory) {
        return world.getRecipeManager()
                .getFirstMatch(recipeType, inventory, world)
                .map(SmeltingRecipe::getCookTime)
                .orElse(200);
    }

    @Override
    public @Nullable Recipe<Inventory> getRecipe(@NotNull World world, int slot) {
        return getRecipe(recipeType, world, slot);
    }
}

