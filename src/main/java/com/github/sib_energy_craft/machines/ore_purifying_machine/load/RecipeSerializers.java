package com.github.sib_energy_craft.machines.ore_purifying_machine.load;

import com.github.sib_energy_craft.machines.ore_purifying_machine.recipe.PurifyingRecipe;
import com.github.sib_energy_craft.machines.ore_purifying_machine.recipe.PurifyingRecipeType;
import com.github.sib_energy_craft.sec_utils.load.DefaultModInitializer;
import net.minecraft.recipe.RecipeSerializer;

import static net.minecraft.recipe.RecipeSerializer.register;

/**
 * @since 0.0.26
 * @author sibmaks
 */
public final class RecipeSerializers implements DefaultModInitializer {
    public static final RecipeSerializer<PurifyingRecipe> PURIFYING_RECIPE_RECIPE;

    static {
        var purifyingRecipeSerializer = new PurifyingRecipe.Serializer();
        PURIFYING_RECIPE_RECIPE = register(PurifyingRecipeType.ID, purifyingRecipeSerializer);
    }

}
