package com.github.sib_energy_craft.machines.energy_furnace.screen;

import com.github.sib_energy_craft.machines.energy_furnace.load.client.Screens;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.recipe.RecipeType;
import net.minecraft.recipe.book.RecipeBookCategory;
import net.minecraft.screen.PropertyDelegate;
import org.jetbrains.annotations.NotNull;

/**
 * @since 0.0.2
 * @author sibmaks
 */
public class EnergyFurnaceScreenHandler extends AbstractEnergyFurnaceScreenHandler {

    public EnergyFurnaceScreenHandler(int syncId,
                                      @NotNull PlayerInventory playerInventory,
                                      @NotNull Inventory inventory,
                                      @NotNull PropertyDelegate propertyDelegate) {
        super(Screens.ENERGY_FURNACE, syncId, playerInventory, inventory, propertyDelegate,
                RecipeType.SMELTING, RecipeBookCategory.FURNACE);
    }

    public EnergyFurnaceScreenHandler(int syncId,
                                      @NotNull PlayerInventory playerInventory,
                                      @NotNull PacketByteBuf packetByteBuf) {
        super(Screens.ENERGY_FURNACE, syncId, playerInventory, RecipeType.SMELTING, RecipeBookCategory.FURNACE);

    }
}
