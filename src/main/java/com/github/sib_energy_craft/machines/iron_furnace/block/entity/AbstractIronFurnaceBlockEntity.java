package com.github.sib_energy_craft.machines.iron_furnace.block.entity;

import com.github.sib_energy_craft.machines.iron_furnace.block.AbstractIronFurnaceBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.AbstractFurnaceBlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.block.entity.OpenedAbstractFurnaceBlockEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.recipe.AbstractCookingRecipe;
import net.minecraft.recipe.RecipeType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import org.jetbrains.annotations.NotNull;

import java.util.Map;

/**
 * @since 0.0.1
 * @author sibmaks
 */
public abstract class AbstractIronFurnaceBlockEntity extends OpenedAbstractFurnaceBlockEntity {
    private static final Map<Item, Integer> FUEL_MAP = AbstractFurnaceBlockEntity.createFuelTimeMap();

    public static final int SOURCE_SLOT = 0;
    public static final int FUEL_SLOT = 1;
    public static final int OUTPUT_SLOT = 2;

    private final AbstractIronFurnaceBlock block;

    protected AbstractIronFurnaceBlockEntity(@NotNull BlockEntityType<?> blockEntityType,
                                             @NotNull BlockPos pos,
                                             @NotNull BlockState state,
                                             @NotNull AbstractIronFurnaceBlock block) {
        super(blockEntityType, pos, state, RecipeType.SMELTING);
        this.block = block;
    }

    @Override
    protected int getFuelTime(@NotNull ItemStack fuel) {
        if (fuel.isEmpty()) {
            return 0;
        }
        var item = fuel.getItem();
        return (int) (FUEL_MAP.getOrDefault(item, 0) * block.getBurnTimeMultiplier());
    }

    public static void tick(@NotNull World world,
                            @NotNull BlockPos pos,
                            @NotNull BlockState state,
                            @NotNull AbstractIronFurnaceBlockEntity blockEntity) {
        var burning = blockEntity.isBurning();
        var stateChanged = false;
        if (blockEntity.isBurning()) {
            blockEntity.decBurnTime();
        }
        var fuelStack = blockEntity.inventory.get(FUEL_SLOT);
        var sourceStack = blockEntity.inventory.get(SOURCE_SLOT);
        if (blockEntity.isBurning() || !fuelStack.isEmpty() && !sourceStack.isEmpty()) {
            AbstractCookingRecipe recipe = null;
            if (!sourceStack.isEmpty()) {
                recipe = blockEntity.matchGetter.getFirstMatch(blockEntity, world).orElse(null);
            }

            int maxCountPerStack = blockEntity.getMaxCountPerStack();
            if (!blockEntity.isBurning() && canAcceptRecipeOutput(world.getRegistryManager(), recipe,
                    blockEntity.inventory, maxCountPerStack, SOURCE_SLOT, OUTPUT_SLOT)) {
                blockEntity.setFuelTime(blockEntity.getFuelTime(fuelStack));
                blockEntity.setBurnTime(blockEntity.getFuelTime());
                if (blockEntity.isBurning()) {
                    stateChanged = true;
                    if (!fuelStack.isEmpty()) {
                        var item = fuelStack.getItem();
                        fuelStack.decrement(1);
                        if (fuelStack.isEmpty()) {
                            var recipeItem = item.getRecipeRemainder();
                            blockEntity.inventory.set(FUEL_SLOT, recipeItem == null ? ItemStack.EMPTY : new ItemStack(recipeItem));
                        }
                    }
                }
            }
            if (blockEntity.isBurning() && canAcceptRecipeOutput(world.getRegistryManager(), recipe,
                    blockEntity.inventory, maxCountPerStack, SOURCE_SLOT, OUTPUT_SLOT)) {
                blockEntity.incCookTime();
                if (blockEntity.getCookTime() == blockEntity.getCookTimeTotal()) {
                    blockEntity.setCookTime(0);
                    var cookingTime = getCookTime(world, blockEntity) * blockEntity.block.getCookingTotalTimeMultiplier();
                    blockEntity.setCookTimeTotal((int) (cookingTime));

                    if (AbstractIronFurnaceBlockEntity.craftRecipe(world.getRegistryManager(), recipe,
                            blockEntity.inventory, maxCountPerStack, SOURCE_SLOT, OUTPUT_SLOT)) {
                        blockEntity.setLastRecipe(recipe);
                    }
                    stateChanged = true;
                }
            } else {
                blockEntity.setCookTime(0);
            }
        } else if (blockEntity.getCookTime() > 0) {
            blockEntity.setCookTime(MathHelper.clamp(blockEntity.getCookTime() - 2, 0, blockEntity.getCookTimeTotal()));
        }
        if (burning != blockEntity.isBurning()) {
            stateChanged = true;
            state = state.with(AbstractIronFurnaceBlock.LIT, blockEntity.isBurning());
            world.setBlockState(pos, state, Block.NOTIFY_ALL);
        }
        if (stateChanged) {
            AbstractIronFurnaceBlockEntity.markDirty(world, pos, state);
        }
    }

}

