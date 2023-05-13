package com.github.sib_energy_craft.machines.compressor.screen;

import com.github.sib_energy_craft.machines.compressor.load.client.Screens;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.screen.PropertyDelegate;
import org.jetbrains.annotations.NotNull;

/**
 * @since 0.0.1
 * @author sibmaks
 */
public class CompressorScreenHandler extends AbstractCompressorScreenHandler {

    public CompressorScreenHandler(int syncId,
                                   @NotNull PlayerInventory playerInventory,
                                   @NotNull Inventory inventory,
                                   @NotNull PropertyDelegate propertyDelegate) {
        super(Screens.COMPRESSOR, syncId, playerInventory, inventory, propertyDelegate);
    }

    public CompressorScreenHandler(int syncId,
                                   @NotNull PlayerInventory playerInventory,
                                   @NotNull PacketByteBuf packetByteBuf) {
        super(Screens.COMPRESSOR, syncId, playerInventory);

    }
}
