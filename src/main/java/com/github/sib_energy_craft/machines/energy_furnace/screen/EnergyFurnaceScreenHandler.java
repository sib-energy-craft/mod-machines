package com.github.sib_energy_craft.machines.energy_furnace.screen;

import com.github.sib_energy_craft.machines.cooking.screen.CookingEnergyMachineState;
import com.github.sib_energy_craft.machines.energy_furnace.load.ScreenHandlers;
import com.github.sib_energy_craft.machines.screen.layout.MultiSlotMachineLayoutManager;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.network.PacketByteBuf;
import org.jetbrains.annotations.NotNull;

/**
 * @since 0.0.2
 * @author sibmaks
 */
public class EnergyFurnaceScreenHandler extends AbstractEnergyFurnaceScreenHandler<CookingEnergyMachineState> {
    private static final MultiSlotMachineLayoutManager LAYOUT_MANAGER = new MultiSlotMachineLayoutManager(
            8, 142,
            8, 84,
            56, 17,
            56, 53,
            116, 35
    );

    public EnergyFurnaceScreenHandler(int syncId,
                                      @NotNull PlayerInventory playerInventory,
                                      @NotNull Inventory inventory) {
        super(ScreenHandlers.ENERGY_FURNACE, syncId, playerInventory, inventory, 1,
                new CookingEnergyMachineState(), LAYOUT_MANAGER);
    }

    public EnergyFurnaceScreenHandler(int syncId,
                                      @NotNull PlayerInventory playerInventory,
                                      @NotNull PacketByteBuf packetByteBuf) {
        super(ScreenHandlers.ENERGY_FURNACE, syncId, playerInventory, 1, new CookingEnergyMachineState(),
                LAYOUT_MANAGER);

    }
}
