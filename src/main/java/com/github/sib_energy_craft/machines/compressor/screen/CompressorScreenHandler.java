package com.github.sib_energy_craft.machines.compressor.screen;

import com.github.sib_energy_craft.machines.compressor.load.client.Screens;
import com.github.sib_energy_craft.machines.screen.layout.MultiSlotMachineLayoutManager;
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
    private static final MultiSlotMachineLayoutManager LAYOUT_MANAGER = new MultiSlotMachineLayoutManager(
            8, 142,
            8, 84,
            56, 17,
            56, 53,
            116, 35
    );

    public CompressorScreenHandler(int syncId,
                                   @NotNull PlayerInventory playerInventory,
                                   @NotNull Inventory inventory,
                                   @NotNull PropertyDelegate propertyDelegate) {
        super(Screens.COMPRESSOR, syncId, playerInventory, inventory, propertyDelegate, LAYOUT_MANAGER);
    }

    public CompressorScreenHandler(int syncId,
                                   @NotNull PlayerInventory playerInventory,
                                   @NotNull PacketByteBuf packetByteBuf) {
        super(Screens.COMPRESSOR, syncId, playerInventory, LAYOUT_MANAGER);

    }
}
