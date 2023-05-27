package com.github.sib_energy_craft.machines.ore_purifying_machine.recipe;

import com.github.sib_energy_craft.energy_api.utils.Identifiers;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import net.minecraft.recipe.RecipeType;

/**
 * @since 0.0.31
 * @author sibmaks
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class PurifyingRecipeType implements RecipeType<PurifyingRecipe> {
    public static final String ID = Identifiers.asString("purifying");
    public static final PurifyingRecipeType INSTANCE = new PurifyingRecipeType();
}