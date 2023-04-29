package com.github.sib_energy_craft.machines.extractor.block.entity;

import com.github.sib_energy_craft.energy_api.consumer.EnergyConsumer;
import com.github.sib_energy_craft.machines.block.entity.AbstractEnergyMachineBlockEntity;
import com.github.sib_energy_craft.machines.extractor.block.AbstractExtractorBlock;
import com.github.sib_energy_craft.machines.extractor.tag.ExtractorTags;
import com.github.sib_energy_craft.machines.utils.ExperienceUtils;
import com.github.sib_energy_craft.recipes.recipe.ExtractingRecipe;
import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerFactory;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.item.ItemStack;
import net.minecraft.recipe.Recipe;
import net.minecraft.recipe.RecipeType;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.jetbrains.annotations.NotNull;

/**
 * @since 0.0.1
 * @author sibmaks
 */
public abstract class AbstractExtractorBlockEntity extends AbstractEnergyMachineBlockEntity<ExtractingRecipe>
        implements ExtendedScreenHandlerFactory, EnergyConsumer {


    protected AbstractExtractorBlockEntity(@NotNull BlockEntityType<?> blockEntityType,
                                           @NotNull BlockPos pos,
                                           @NotNull BlockState state,
                                           @NotNull RecipeType<ExtractingRecipe> recipeType,
                                           @NotNull AbstractExtractorBlock block) {
        super(blockEntityType, pos, state, recipeType, block);
    }

    @Override
    public int getCookTime(@NotNull World world) {
        return getCookTime(world, recipeType, this);
    }

    @Override
    public boolean isValid(int slot, @NotNull ItemStack stack) {
        if(slot == SOURCE_SLOT) {
            return ExtractorTags.isUsedInExtractor(stack);
        }
        return super.isValid(slot, stack);
    }

    @Override
    protected void dropExperience(@NotNull ServerWorld world,
                                  @NotNull Vec3d pos,
                                  int id,
                                  @NotNull Recipe<?> recipe) {
        if (recipe instanceof ExtractingRecipe cookingRecipe) {
            ExperienceUtils.drop(world, pos, id, cookingRecipe.getExperience());
        }
    }
}

