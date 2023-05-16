package com.github.sib_energy_craft.machines.compressor.screen;

import com.github.sib_energy_craft.machines.compressor.load.client.Screens;
import com.github.sib_energy_craft.machines.screen.VisualSettings;
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
    private static final VisualSettings VISUAL_SETTINGS = new VisualSettings(
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
        super(Screens.COMPRESSOR, syncId, playerInventory, inventory, propertyDelegate, VISUAL_SETTINGS);
    }

    public CompressorScreenHandler(int syncId,
                                   @NotNull PlayerInventory playerInventory,
                                   @NotNull PacketByteBuf packetByteBuf) {
        super(Screens.COMPRESSOR, syncId, playerInventory, VISUAL_SETTINGS);

    }
}
