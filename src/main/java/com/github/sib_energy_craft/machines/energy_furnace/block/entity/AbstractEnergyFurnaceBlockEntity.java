package com.github.sib_energy_craft.machines.energy_furnace.block.entity;

import com.github.sib_energy_craft.energy_api.Energy;
import com.github.sib_energy_craft.energy_api.consumer.EnergyConsumer;
import com.github.sib_energy_craft.machines.block.entity.AbstractEnergyMachineBlockEntity;
import com.github.sib_energy_craft.machines.block.entity.EnergyMachineInventoryTypes;
import com.github.sib_energy_craft.machines.energy_furnace.block.AbstractEnergyFurnaceBlock;
import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerFactory;
import net.minecraft.block.Block;
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


/**
 * @since 0.0.2
 * @author sibmaks
 */
public abstract class AbstractEnergyFurnaceBlockEntity extends AbstractEnergyMachineBlockEntity
        implements ExtendedScreenHandlerFactory, EnergyConsumer {
    protected static final Energy ENERGY_ONE = Energy.of(1);

    protected final RecipeType<SmeltingRecipe> recipeType;

    protected AbstractEnergyFurnaceBlockEntity(@NotNull BlockEntityType<?> blockEntityType,
                                               @NotNull BlockPos pos,
                                               @NotNull BlockState state,
                                               @NotNull RecipeType<SmeltingRecipe> recipeType,
                                               @NotNull AbstractEnergyFurnaceBlock block) {
        super(blockEntityType, pos, state, block);
        this.recipeType = recipeType;
    }

    public static void tick(@NotNull World world,
                            @NotNull BlockPos pos,
                            @NotNull BlockState state,
                            @NotNull AbstractEnergyFurnaceBlockEntity blockEntity) {
        if(world.isClient) {
            return;
        }
        boolean hasEnergy = blockEntity.energyContainer.hasAtLeast(ENERGY_ONE);
        boolean changed = false;
        boolean working = blockEntity.working;
        blockEntity.working = false;

        charge(blockEntity);

        if(blockEntity.energyContainer.hasAtLeast(ENERGY_ONE)) {
            var recipeManager = world.getRecipeManager();
            var recipe = recipeManager.getFirstMatch(blockEntity.recipeType, blockEntity, world)
                    .orElse(null);
            if(recipe != null) {
                int i = blockEntity.getMaxCountPerStack();
                if (canAcceptRecipeOutput(0, blockEntity.inventory, world, recipe, i)) {
                    if(blockEntity.energyContainer.subtract(ENERGY_ONE)) {
                        ++blockEntity.cookTime;
                        blockEntity.working = true;
                        if (blockEntity.cookTime >= blockEntity.cookTimeTotal) {
                            blockEntity.cookTime = 0;
                            var block = (AbstractEnergyFurnaceBlock) blockEntity.block;
                            blockEntity.cookTimeTotal = (int) (getSmeltingCookTime(world, blockEntity.recipeType, blockEntity) *
                                    block.getCookingTotalTimeMultiplier());
                            if (craftRecipe(0, blockEntity.inventory, world, recipe, 1, i)) {
                                blockEntity.setLastRecipe(recipe);
                            }
                        }
                        changed = true;
                    }
                } else {
                    blockEntity.cookTime = 0;
                }
            }
        } else {
            if(blockEntity.cookTime > 0) {
                blockEntity.cookTime = MathHelper.clamp(blockEntity.cookTime - 2, 0, blockEntity.cookTimeTotal);
            }
        }
        if(working != blockEntity.working) {
            state = state.with(AbstractEnergyFurnaceBlock.WORKING, blockEntity.working);
            world.setBlockState(pos, state, Block.NOTIFY_ALL);
        }
        if (hasEnergy != blockEntity.energyContainer.hasEnergy() || changed) {
            markDirty(world, pos, state);
        }
    }

    @Override
    public boolean isValid(int slot, @NotNull ItemStack stack) {
        var slotType = inventory.getType(slot);
        if(slotType == EnergyMachineInventoryTypes.SOURCE) {
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
    public int getCookTime(@NotNull World world) {
        return getSmeltingCookTime(world, recipeType, this);
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
    public @NotNull <C extends Inventory, T extends Recipe<C>> RecipeType<T> getRecipeType() {
        return (RecipeType<T>) recipeType;
    }
}

