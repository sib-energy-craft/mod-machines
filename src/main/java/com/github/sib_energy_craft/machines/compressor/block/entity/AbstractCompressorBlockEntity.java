package com.github.sib_energy_craft.machines.compressor.block.entity;

import com.github.sib_energy_craft.energy_api.consumer.EnergyConsumer;
import com.github.sib_energy_craft.machines.block.entity.AbstractEnergyMachineBlockEntity;
import com.github.sib_energy_craft.machines.block.entity.EnergyMachineInventoryType;
import com.github.sib_energy_craft.machines.compressor.block.AbstractCompressorBlock;
import com.github.sib_energy_craft.machines.compressor.tag.CompressorTags;
import com.github.sib_energy_craft.machines.utils.ExperienceUtils;
import com.github.sib_energy_craft.recipes.recipe.CompressingRecipe;
import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerFactory;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.inventory.Inventory;
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
 * @since 0.0.1
 * @author sibmaks
 */
public abstract class AbstractCompressorBlockEntity<T extends AbstractCompressorBlock> extends AbstractEnergyMachineBlockEntity<T>
        implements ExtendedScreenHandlerFactory, EnergyConsumer {
    protected final RecipeType<CompressingRecipe> recipeType;


    protected AbstractCompressorBlockEntity(@NotNull BlockEntityType<?> blockEntityType,
                                            @NotNull BlockPos pos,
                                            @NotNull BlockState state,
                                            @NotNull RecipeType<CompressingRecipe> recipeType,
                                            @NotNull T block) {
        super(blockEntityType, pos, state, block);
        this.recipeType = recipeType;
    }

    @Override
    public int getCookTimeTotal(@NotNull World world) {
        return world.getRecipeManager()
                .getFirstMatch(recipeType, this, world)
                .map(CompressingRecipe::getCookTime)
                .orElse(200);
    }

    @Override
    public boolean isValid(int slot, @NotNull ItemStack stack) {
        var slotType = inventory.getType(slot);
        if(slotType == EnergyMachineInventoryType.SOURCE) {
            return CompressorTags.isUsedInCompressor(stack);
        }
        return super.isValid(slot, stack);
    }

    @Override
    protected int calculateDecrement(@NotNull Recipe<?> recipe) {
        if(recipe instanceof CompressingRecipe compressingRecipe) {
            var input = compressingRecipe.getInput();
            return input.getCount();
        }
        return super.calculateDecrement(recipe);
    }

    @Override
    protected void dropExperience(@NotNull ServerWorld world,
                                  @NotNull Vec3d pos,
                                  int id,
                                  @NotNull Recipe<?> recipe) {
        if (recipe instanceof CompressingRecipe cookingRecipe) {
            ExperienceUtils.drop(world, pos, id, cookingRecipe.getExperience());
        }
    }

    @Override
    public @Nullable Recipe<Inventory> getRecipe(@NotNull World world, int slot) {
        return getRecipe(recipeType, world, slot);
    }
}

