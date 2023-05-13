package com.github.sib_energy_craft.machines.macerator.screen;

import com.github.sib_energy_craft.machines.macerator.load.client.Screens;
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

    public MaceratorScreenHandler(int syncId,
                                  @NotNull PlayerInventory playerInventory,
                                  @NotNull Inventory inventory,
                                  @NotNull PropertyDelegate propertyDelegate) {
        super(Screens.MACERATOR, syncId, playerInventory, inventory, propertyDelegate);
    }

    public MaceratorScreenHandler(int syncId,
                                  @NotNull PlayerInventory playerInventory,
                                  @NotNull PacketByteBuf packetByteBuf) {
        super(Screens.MACERATOR, syncId, playerInventory);

    }
}
