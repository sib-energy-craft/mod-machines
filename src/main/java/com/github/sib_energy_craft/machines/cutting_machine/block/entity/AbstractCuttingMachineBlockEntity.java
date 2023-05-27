package com.github.sib_energy_craft.machines.cutting_machine.block.entity;

import com.github.sib_energy_craft.machines.block.entity.EnergyMachineInventoryType;
import com.github.sib_energy_craft.machines.block.entity.OneToOneEnergyMachineBlockEntity;
import com.github.sib_energy_craft.machines.cutting_machine.block.AbstractCuttingMachineBlock;
import com.github.sib_energy_craft.machines.cutting_machine.utils.CuttingMachineUtils;
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
public abstract class AbstractCuttingMachineBlockEntity<T extends AbstractCuttingMachineBlock>
        extends OneToOneEnergyMachineBlockEntity<T>
        implements ExtendedScreenHandlerFactory {

    protected final RecipeType<IronCraftingTableRecipe> recipeType;
    protected final SimpleInventory craftingInventory;

    protected AbstractCuttingMachineBlockEntity(@NotNull BlockEntityType<?> blockEntityType,
                                                @NotNull BlockPos pos,
                                                @NotNull BlockState state,
                                                @NotNull RecipeType<IronCraftingTableRecipe> recipeType,
                                                @NotNull T block) {
        super(blockEntityType, pos, state, block);
        this.recipeType = recipeType;
        this.craftingInventory = new SimpleInventory(2);
        this.craftingInventory.setStack(0, new ItemStack(Items.METAL_SHEARS, 1));
    }

    @Override
    public int getCookTimeTotal(@NotNull World world) {
        return block.getCookTimeTotal();
    }

    @Override
    public boolean isValid(int slot, @NotNull ItemStack stack) {
        var slotType = inventory.getType(slot);
        if(world != null && slotType == EnergyMachineInventoryType.SOURCE) {
            return CuttingMachineUtils.isUsedInCuttingMachine(world, stack);
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

