package com.github.sib_energy_craft.machines.energy_furnace.block.entity;

import com.github.sib_energy_craft.machines.block.entity.EnergyMachineEvent;
import com.github.sib_energy_craft.machines.block.entity.EnergyMachineInventoryType;
import com.github.sib_energy_craft.machines.cooking.block.entity.OneToOneEnergyMachineBlockEntity;
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
public abstract class AbstractEnergyFurnaceBlockEntity<T extends AbstractEnergyFurnaceBlock>
        extends OneToOneEnergyMachineBlockEntity<T>
        implements ExtendedScreenHandlerFactory {

    protected AbstractEnergyFurnaceBlockEntity(@NotNull BlockEntityType<?> blockEntityType,
                                               @NotNull BlockPos pos,
                                               @NotNull BlockState state,
                                               @NotNull T block,
                                               int slots) {
        super(blockEntityType, pos, state, block, slots);
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
        return recipeManager.getFirstMatch(RecipeType.SMELTING, simpleInventory, world).isPresent();
    }

    @Override
    public int getCookTimeTotal(@NotNull World world) {
        return (int) (getSmeltingCookTime(world, this) * block.getCookingTotalTimeMultiplier());
    }

    protected static<T extends SmeltingRecipe> int getSmeltingCookTime(@NotNull World world,
                                                                       @NotNull Inventory inventory) {
        return world.getRecipeManager()
                .getFirstMatch(RecipeType.SMELTING, inventory, world)
                .map(SmeltingRecipe::getCookTime)
                .orElse(200);
    }

    @Override
    public @Nullable Recipe<Inventory> getRecipe(@NotNull World world, int slot) {
        return getRecipe(RecipeType.SMELTING, world, slot);
    }
}

