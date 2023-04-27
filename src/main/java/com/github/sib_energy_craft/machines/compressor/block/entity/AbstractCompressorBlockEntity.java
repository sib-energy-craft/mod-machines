package com.github.sib_energy_craft.machines.compressor.block.entity;

import com.github.sib_energy_craft.energy_api.Energy;
import com.github.sib_energy_craft.energy_api.consumer.EnergyConsumer;
import com.github.sib_energy_craft.machines.block.AbstractEnergyMachineBlock;
import com.github.sib_energy_craft.machines.block.entity.AbstractEnergyMachineBlockEntity;
import com.github.sib_energy_craft.machines.compressor.block.AbstractCompressorBlock;
import com.github.sib_energy_craft.machines.compressor.tag.CompressorTags;
import com.github.sib_energy_craft.recipes.recipe.CompressingRecipe;
import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerFactory;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.recipe.Recipe;
import net.minecraft.recipe.RecipeType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.NotNull;

/**
 * @since 0.0.1
 * @author sibmaks
 */
public abstract class AbstractCompressorBlockEntity extends AbstractEnergyMachineBlockEntity<CompressingRecipe>
        implements ExtendedScreenHandlerFactory, EnergyConsumer {


    protected AbstractCompressorBlockEntity(@NotNull BlockEntityType<?> blockEntityType,
                                            @NotNull BlockPos pos,
                                            @NotNull BlockState state,
                                            @NotNull RecipeType<CompressingRecipe> recipeType,
                                            @NotNull AbstractCompressorBlock block) {
        super(blockEntityType, pos, state, recipeType, block);
    }

    @Override
    public int getCookTime(@NotNull World world) {
        return world.getRecipeManager()
                .getFirstMatch(recipeType, this, world)
                .map(CompressingRecipe::getCookTime)
                .orElse(200);
    }

    @Override
    public boolean isValid(int slot, @NotNull ItemStack stack) {
        if(slot == SOURCE_SLOT) {
            return CompressorTags.isUsedInCompressor(stack);
        }
        return super.isValid(slot, stack);
    }

    public static <T extends Recipe<Inventory>> void tick(
            @NotNull World world,
            @NotNull BlockPos pos,
            @NotNull BlockState state,
            @NotNull AbstractCompressorBlockEntity blockEntity) {
        if (world.isClient) {
            return;
        }
        var hasEnergy = blockEntity.energyContainer.hasEnergy();
        var changed = false;
        var working = blockEntity.working;

        charge(blockEntity);

        if (blockEntity.energyContainer.hasEnergy()) {
            var recipeManager = world.getRecipeManager();
            var recipe = recipeManager.getFirstMatch(blockEntity.recipeType, blockEntity, world)
                    .orElse(null);
            var i = blockEntity.getMaxCountPerStack();
            if (canAcceptRecipeOutput(world, recipe, blockEntity.inventory, i)) {
                blockEntity.energyContainer.subtract(Energy.of(1));
                ++blockEntity.cookTime;
                blockEntity.working = true;
                if (blockEntity.cookTime == blockEntity.cookTimeTotal) {
                    blockEntity.cookTime = 0;
                    blockEntity.cookTimeTotal = blockEntity.getCookTime(world);
                    int decrement = recipe.getInput().getCount();
                    if (craftRecipe(world, recipe, blockEntity.inventory, decrement, i)) {
                        blockEntity.setLastRecipe(recipe);
                    }
                }
                changed = true;
            } else {
                blockEntity.cookTime = 0;
                blockEntity.working = false;
            }
        } else {
            blockEntity.working = false;
        }
        if (working != blockEntity.working) {
            state = state.with(AbstractEnergyMachineBlock.WORKING, blockEntity.working);
            world.setBlockState(pos, state, Block.NOTIFY_ALL);
        }
        if (hasEnergy != blockEntity.energyContainer.hasEnergy() || changed) {
            markDirty(world, pos, state);
        }
    }
}

