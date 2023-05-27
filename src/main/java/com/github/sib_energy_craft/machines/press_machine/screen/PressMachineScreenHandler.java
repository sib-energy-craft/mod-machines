package com.github.sib_energy_craft.machines.press_machine.screen;

import com.github.sib_energy_craft.machines.press_machine.load.ScreenHandlers;
import com.github.sib_energy_craft.machines.screen.layout.MultiSlotMachineLayoutManager;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.network.PacketByteBuf;
import org.jetbrains.annotations.NotNull;

/**
 * @since 0.0.17
 * @author sibmaks
 */
public class PressMachineScreenHandler extends AbstractPressMachineScreenHandler {
    private static final MultiSlotMachineLayoutManager LAYOUT_MANAGER = new MultiSlotMachineLayoutManager(
            8, 142,
            8, 84,
            56, 17,
            56, 53,
            116, 35
    );


    public PressMachineScreenHandler(int syncId,
                                     @NotNull PlayerInventory playerInventory,
                                     @NotNull Inventory inventory) {
        super(ScreenHandlers.PRESS_MACHINE, syncId, playerInventory, inventory, LAYOUT_MANAGER);
    }

    public PressMachineScreenHandler(int syncId,
                                     @NotNull PlayerInventory playerInventory,
                                     @NotNull PacketByteBuf packetByteBuf) {
        super(ScreenHandlers.PRESS_MACHINE, syncId, playerInventory, LAYOUT_MANAGER);
    }
}
