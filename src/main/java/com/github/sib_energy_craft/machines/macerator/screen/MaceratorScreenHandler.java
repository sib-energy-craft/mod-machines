package com.github.sib_energy_craft.machines.macerator.screen;

import com.github.sib_energy_craft.machines.macerator.load.client.Screens;
import com.github.sib_energy_craft.machines.screen.layout.OneSlotMachineLayoutManager;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.screen.PropertyDelegate;
import org.jetbrains.annotations.NotNull;

/**
 * @since 0.0.1
 * @author sibmaks
 */
public class MaceratorScreenHandler extends AbstractMaceratorScreenHandler {
    private static final OneSlotMachineLayoutManager LAYOUT_MANAGER = new OneSlotMachineLayoutManager(
            8, 142,
            8, 84,
            56, 17,
            56, 53,
            116, 35
    );


    public MaceratorScreenHandler(int syncId,
                                  @NotNull PlayerInventory playerInventory,
                                  @NotNull Inventory inventory,
                                  @NotNull PropertyDelegate propertyDelegate) {
        super(Screens.MACERATOR, syncId, playerInventory, inventory, propertyDelegate, LAYOUT_MANAGER);
    }

    public MaceratorScreenHandler(int syncId,
                                  @NotNull PlayerInventory playerInventory,
                                  @NotNull PacketByteBuf packetByteBuf) {
        super(Screens.MACERATOR, syncId, playerInventory, LAYOUT_MANAGER);

    }
}
