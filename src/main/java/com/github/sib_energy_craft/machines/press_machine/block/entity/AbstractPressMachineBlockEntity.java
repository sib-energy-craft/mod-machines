package com.github.sib_energy_craft.machines.press_machine.block.entity;

import com.github.sib_energy_craft.energy_api.consumer.EnergyConsumer;
import com.github.sib_energy_craft.machines.block.entity.AbstractEnergyMachineBlockEntity;
import com.github.sib_energy_craft.machines.block.entity.EnergyMachineInventoryType;
import com.github.sib_energy_craft.machines.press_machine.block.AbstractPressMachineBlock;
import com.github.sib_energy_craft.machines.press_machine.utils.PressMachineUtils;
import com.github.sib_energy_craft.metallurgy.iron_craft_table.load.Items;
import com.github.sib_energy_craft.recipes.recipe.IronCraftingTableRecipe;
import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerFactory;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.recipe.Recipe;
import net.minecraft.recipe.RecipeType;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * @since 0.0.17
 * @author sibmaks
 */
public abstract class AbstractPressMachineBlockEntity<T extends AbstractPressMachineBlock> extends AbstractEnergyMachineBlockEntity<T>
        implements ExtendedScreenHandlerFactory, EnergyConsumer {

    protected final RecipeType<IronCraftingTableRecipe> recipeType;
    protected final SimpleInventory craftingInventory;

    protected AbstractPressMachineBlockEntity(@NotNull BlockEntityType<?> blockEntityType,
                                              @NotNull BlockPos pos,
                                              @NotNull BlockState state,
                                              @NotNull RecipeType<IronCraftingTableRecipe> recipeType,
                                              @NotNull T block) {
        super(blockEntityType, pos, state, block);
        this.recipeType = recipeType;
        this.craftingInventory = new SimpleInventory(2);
        this.craftingInventory.setStack(0, new ItemStack(Items.IRON_HAMMER, 1));
    }

    @Override
    public int getCookTimeTotal(@NotNull World world) {
        return ((AbstractPressMachineBlock) block).getCookTimeTotal();
    }

    @Override
    public boolean isValid(int slot, @NotNull ItemStack stack) {
        var slotType = inventory.getType(slot);
        if(world != null && slotType == EnergyMachineInventoryType.SOURCE) {
            return PressMachineUtils.isUsedInPressMachine(world, stack);
        }
        return super.isValid(slot, stack);
    }

    @Override
    protected void dropExperience(@NotNull ServerWorld world,
                                  @NotNull Vec3d pos,
                                  int id,
                                  @NotNull Recipe<?> recipe) {
    }

    @Override
    public @Nullable Recipe<Inventory> getRecipe(@NotNull World world, int slot) {
        var sourceInventory = inventory.getInventory(EnergyMachineInventoryType.SOURCE);
        if(sourceInventory == null) {
            return null;
        }
        var recipeManager = world.getRecipeManager();
        craftingInventory.setStack(1, sourceInventory.getStack(0));
        return recipeManager.getFirstMatch(recipeType, craftingInventory, world)
                .orElse(null);
    }
}

