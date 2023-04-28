package com.github.sib_energy_craft.machines.energy_furnace.screen;

import com.github.sib_energy_craft.energy_api.screen.ChargeSlot;
import com.github.sib_energy_craft.energy_api.tags.CoreTags;
import com.github.sib_energy_craft.machines.block.entity.AbstractEnergyMachineProperties;
import com.github.sib_energy_craft.machines.energy_furnace.screen.slot.EnergyFurnaceOutputSlot;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.recipe.*;
import net.minecraft.recipe.book.RecipeBookCategory;
import net.minecraft.screen.AbstractRecipeScreenHandler;
import net.minecraft.screen.ArrayPropertyDelegate;
import net.minecraft.screen.PropertyDelegate;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.screen.slot.Slot;
import net.minecraft.world.World;
import org.jetbrains.annotations.NotNull;

import static com.github.sib_energy_craft.machines.energy_furnace.block.entity.AbstractEnergyFurnaceBlockEntity.*;

/**
 * @since 0.0.2
 * @author sibmaks
 */
public abstract class AbstractEnergyFurnaceScreenHandler extends AbstractRecipeScreenHandler<Inventory> {
    private final Inventory inventory;
    private final PropertyDelegate propertyDelegate;
    protected final World world;
    private final RecipeType<? extends AbstractCookingRecipe> recipeType;
    private final RecipeBookCategory category;

    protected AbstractEnergyFurnaceScreenHandler(@NotNull ScreenHandlerType<?> type,
                                                 int syncId,
                                                 @NotNull PlayerInventory playerInventory,
                                                 @NotNull RecipeType<? extends AbstractCookingRecipe> recipeType,
                                                 @NotNull RecipeBookCategory category) {
        this(type, syncId, playerInventory, new SimpleInventory(3), new ArrayPropertyDelegate(4), recipeType, category);
    }

    protected AbstractEnergyFurnaceScreenHandler(@NotNull ScreenHandlerType<?> type,
                                                 int syncId,
                                                 @NotNull PlayerInventory playerInventory,
                                                 @NotNull Inventory inventory,
                                                 @NotNull PropertyDelegate propertyDelegate,
                                                 @NotNull RecipeType<? extends AbstractCookingRecipe> recipeType,
                                                 @NotNull RecipeBookCategory category) {
        super(type, syncId);
        this.recipeType = recipeType;
        this.category = category;
        AbstractEnergyFurnaceScreenHandler.checkSize(inventory, 3);
        AbstractEnergyFurnaceScreenHandler.checkDataCount(propertyDelegate, 4);
        this.inventory = inventory;
        this.propertyDelegate = propertyDelegate;
        this.world = playerInventory.player.world;
        this.addSlot(new Slot(inventory, SOURCE_SLOT, 56, 17));
        this.addSlot(new ChargeSlot(inventory, CHARGE_SLOT, 56, 53, false));
        this.addSlot(new EnergyFurnaceOutputSlot(playerInventory.player, inventory, OUTPUT_SLOT, 116, 35));
        for (int i = 0; i < 3; ++i) {
            for (int j = 0; j < 9; ++j) {
                this.addSlot(new Slot(playerInventory, j + i * 9 + 9, 8 + j * 18, 84 + i * 18));
            }
        }
        for (int i = 0; i < 9; ++i) {
            this.addSlot(new Slot(playerInventory, i, 8 + i * 18, 142));
        }
        this.addProperties(propertyDelegate);
    }

    @Override
    public boolean canUse(@NotNull PlayerEntity player) {
        return this.inventory.canPlayerUse(player);
    }

    @Override
    public ItemStack quickMove(PlayerEntity player, int index) {
        var itemStack = ItemStack.EMPTY;
        var slot = this.slots.get(index);
        if (slot.hasStack()) {
            var slotStack = slot.getStack();
            itemStack = slotStack.copy();
            if(index == OUTPUT_SLOT) {
                if(!insertItem(slotStack, OUTPUT_SLOT + 1, 39, true)) {
                    return ItemStack.EMPTY;
                }
                slot.onQuickTransfer(slotStack, itemStack);
            } else if(index == CHARGE_SLOT || index == SOURCE_SLOT) {
                if(!insertItem(slotStack, OUTPUT_SLOT + 1, 39, false)) {
                    return ItemStack.EMPTY;
                }
            } else {
                if(isSmeltable(slotStack) && !insertItem(slotStack, SOURCE_SLOT, SOURCE_SLOT + 1, false)) {
                    return ItemStack.EMPTY;
                } else if(CoreTags.isChargeable(slotStack) &&
                        !insertItem(slotStack, CHARGE_SLOT, CHARGE_SLOT + 1, false)) {
                    return ItemStack.EMPTY;
                } else if(index >= 3 && index < 30 && !insertItem(slotStack, 30, 39, false)) {
                    return ItemStack.EMPTY;
                } else if(index >= 30 && index < 39 && !insertItem(slotStack, 3, 30, false)) {
                    return ItemStack.EMPTY;
                }
            }
            if (slotStack.isEmpty()) {
                slot.setStack(ItemStack.EMPTY);
            } else {
                slot.markDirty();
            }
            if (slotStack.getCount() == itemStack.getCount()) {
                return ItemStack.EMPTY;
            }
            slot.onTakeItem(player, slotStack);
        }
        return itemStack;
    }

    @Override
    public void clearCraftingSlots() {
        this.getSlot(SOURCE_SLOT).setStack(ItemStack.EMPTY);
        this.getSlot(OUTPUT_SLOT).setStack(ItemStack.EMPTY);
    }

    @Override
    public void populateRecipeFinder(@NotNull RecipeMatcher finder) {
        if (this.inventory instanceof RecipeInputProvider recipeInputProvider) {
            recipeInputProvider.provideRecipeInputs(finder);
        }
    }

    @Override
    public boolean matches(@NotNull Recipe<? super Inventory> recipe) {
        return recipe.matches(this.inventory, this.world);
    }

    @Override
    public int getCraftingResultSlotIndex() {
        return 2;
    }

    @Override
    public int getCraftingWidth() {
        return 1;
    }

    @Override
    public int getCraftingHeight() {
        return 1;
    }

    @Override
    public int getCraftingSlotCount() {
        return 3;
    }

    @NotNull
    @Override
    public RecipeBookCategory getCategory() {
        return this.category;
    }

    @Override
    public boolean canInsertIntoSlot(int index) {
        return index != 1;
    }

    /**
     * Check is item stack smeltable
     *
     * @param itemStack item stack
     * @return true - stack is smeltable, false - otherwise
     */
    protected boolean isSmeltable(@NotNull ItemStack itemStack) {
        var simpleInventory = new SimpleInventory(itemStack);
        var recipeManager = this.world.getRecipeManager();
        return recipeManager.getFirstMatch(this.recipeType, simpleInventory, this.world).isPresent();
    }
    
    /**
     * Get charge progress status
     *
     * @return charge progress
     */
    public int getChargeProgress() {
        int i = getCharge();
        int j = getMaxCharge();
        if (j == 0 || i == 0) {
            return 0;
        }
        return i * 13 / j;
    }

    /**
     * Get cook progress status
     *
     * @return cook progress
     */
    public int getCookProgress() {
        int i = getCookingTime();
        int j = getCookingTimeTotal();
        if (j == 0 || i == 0) {
            return 0;
        }
        return i * 22 / j;
    }

    /**
     * Get extractor charge
     *
     * @return charge
     */
    public int getCharge() {
        return propertyDelegate.get(AbstractEnergyMachineProperties.CHARGE.ordinal());
    }

    /**
     * Get extractor max charge
     *
     * @return max charge
     */
    public int getMaxCharge() {
        return propertyDelegate.get(AbstractEnergyMachineProperties.MAX_CHARGE.ordinal());
    }

    /**
     * Get extractor cooking time
     *
     * @return cooking time
     */
    public int getCookingTime() {
        return propertyDelegate.get(AbstractEnergyMachineProperties.COOKING_TIME.ordinal());
    }

    /**
     * Get extractor total cooking time
     *
     * @return total cooking time
     */
    public int getCookingTimeTotal() {
        return propertyDelegate.get(AbstractEnergyMachineProperties.COOKING_TIME_TOTAL.ordinal());
    }
}

