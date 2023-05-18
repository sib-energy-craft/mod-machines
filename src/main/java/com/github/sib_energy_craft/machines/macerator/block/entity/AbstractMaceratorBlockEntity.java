package com.github.sib_energy_craft.machines.macerator.block.entity;

import com.github.sib_energy_craft.energy_api.consumer.EnergyConsumer;
import com.github.sib_energy_craft.machines.block.entity.AbstractEnergyMachineBlockEntity;
import com.github.sib_energy_craft.machines.block.entity.EnergyMachineInventoryType;
import com.github.sib_energy_craft.machines.macerator.block.AbstractMaceratorBlock;
import com.github.sib_energy_craft.machines.macerator.tag.MaceratorTags;
import com.github.sib_energy_craft.machines.utils.EnergyMachineUtils;
import com.github.sib_energy_craft.machines.utils.ExperienceUtils;
import com.github.sib_energy_craft.recipes.load.RecipeTypes;
import com.github.sib_energy_craft.recipes.recipe.MaceratingRecipe;
import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerFactory;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.recipe.Recipe;
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
public abstract class AbstractMaceratorBlockEntity<T extends AbstractMaceratorBlock>
        extends AbstractEnergyMachineBlockEntity<T>
        implements ExtendedScreenHandlerFactory, EnergyConsumer {

    protected AbstractMaceratorBlockEntity(@NotNull BlockEntityType<?> blockEntityType,
                                           @NotNull BlockPos pos,
                                           @NotNull BlockState state,
                                           @NotNull T block,
                                           int slots) {
        super(blockEntityType, pos, state, block, slots);
    }

    @Override
    public int getCookTimeTotal(@NotNull World world) {
        return EnergyMachineUtils.getCookTimeTotal(world, RecipeTypes.MACERATING, this);
    }

    @Override
    public boolean isValid(int slot, @NotNull ItemStack stack) {
        var slotType = inventory.getType(slot);
        if(slotType == EnergyMachineInventoryType.SOURCE) {
            return MaceratorTags.isUsedInMacerator(stack);
        }
        return super.isValid(slot, stack);
    }

    @Override
    protected void dropExperience(@NotNull ServerWorld world,
                                  @NotNull Vec3d pos,
                                  int id,
                                  @NotNull Recipe<?> recipe) {
        if (recipe instanceof MaceratingRecipe cookingRecipe) {
            ExperienceUtils.drop(world, pos, id, cookingRecipe.getExperience());
        }
    }

    @Override
    public @Nullable Recipe<Inventory> getRecipe(@NotNull World world, int slot) {
        return getRecipe(RecipeTypes.MACERATING, world, slot);
    }
}

