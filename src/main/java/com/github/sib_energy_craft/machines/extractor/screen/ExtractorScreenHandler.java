package com.github.sib_energy_craft.machines.extractor.screen;

import com.github.sib_energy_craft.machines.extractor.load.client.Screens;
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
public class ExtractorScreenHandler extends AbstractExtractorScreenHandler {
    private static final VisualSettings VISUAL_SETTINGS = new VisualSettings(
            8, 142,
            8, 84,
            56, 17,
            56, 53,
            116, 35
    );

    public ExtractorScreenHandler(int syncId,
                                  @NotNull PlayerInventory playerInventory,
                                  @NotNull Inventory inventory,
                                  @NotNull PropertyDelegate propertyDelegate) {
        super(Screens.EXTRACTOR, syncId, playerInventory, inventory, propertyDelegate, VISUAL_SETTINGS);
    }

    public ExtractorScreenHandler(int syncId,
                                  @NotNull PlayerInventory playerInventory,
                                  @NotNull PacketByteBuf packetByteBuf) {
        super(Screens.EXTRACTOR, syncId, playerInventory, VISUAL_SETTINGS);

    }
}
