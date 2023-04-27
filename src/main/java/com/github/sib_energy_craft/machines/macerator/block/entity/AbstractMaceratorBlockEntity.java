package com.github.sib_energy_craft.machines.macerator.block.entity;

import com.github.sib_energy_craft.energy_api.consumer.EnergyConsumer;
import com.github.sib_energy_craft.machines.block.entity.AbstractEnergyMachineBlockEntity;
import com.github.sib_energy_craft.machines.macerator.block.AbstractMaceratorBlock;
import com.github.sib_energy_craft.machines.macerator.tag.MaceratorTags;
import com.github.sib_energy_craft.recipes.recipe.MaceratingRecipe;
import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerFactory;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.item.ItemStack;
import net.minecraft.recipe.RecipeType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public abstract class AbstractMaceratorBlockEntity extends AbstractEnergyMachineBlockEntity<MaceratingRecipe>
        implements ExtendedScreenHandlerFactory, EnergyConsumer {


    protected AbstractMaceratorBlockEntity(BlockEntityType<?> blockEntityType,
                                           BlockPos pos,
                                           BlockState state,
                                           RecipeType<MaceratingRecipe> recipeType,
                                           AbstractMaceratorBlock block) {
        super(blockEntityType, pos, state, recipeType, block);
    }

    @Override
    public int getCookTime(World world) {
        return getCookTime(world, recipeType, this);
    }

    @Override
    public boolean isValid(int slot, ItemStack stack) {
        if(slot == SOURCE_SLOT) {
            return MaceratorTags.isUsedInMacerator(stack);
        }
        return super.isValid(slot, stack);
    }
}

