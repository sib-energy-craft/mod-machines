package com.github.sib_energy_craft.machines.energy_furnace.screen;

import com.github.sib_energy_craft.machines.energy_furnace.load.client.Screens;
import com.github.sib_energy_craft.machines.screen.layout.MultiSlotMachineLayoutManager;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.screen.PropertyDelegate;
import org.jetbrains.annotations.NotNull;

/**
 * @since 0.0.2
 * @author sibmaks
 */
public class EnergyFurnaceScreenHandler extends AbstractEnergyFurnaceScreenHandler {
    private static final MultiSlotMachineLayoutManager LAYOUT_MANAGER = new MultiSlotMachineLayoutManager(
            8, 142,
            8, 84,
            56, 17,
            56, 53,
            116, 35
    );

    public EnergyFurnaceScreenHandler(int syncId,
                                      @NotNull PlayerInventory playerInventory,
                                      @NotNull Inventory inventory,
                                      @NotNull PropertyDelegate propertyDelegate) {
        super(Screens.ENERGY_FURNACE, syncId, playerInventory, inventory, propertyDelegate, 1, LAYOUT_MANAGER);
    }

    public EnergyFurnaceScreenHandler(int syncId,
                                      @NotNull PlayerInventory playerInventory,
                                      @NotNull PacketByteBuf packetByteBuf) {
        super(Screens.ENERGY_FURNACE, syncId, playerInventory, 1, LAYOUT_MANAGER);

    }
}
