package com.github.sib_energy_craft.machines.extractor.screen;

import com.github.sib_energy_craft.machines.extractor.load.Screens;
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

    public ExtractorScreenHandler(int syncId,
                                  @NotNull PlayerInventory playerInventory,
                                  @NotNull Inventory inventory,
                                  @NotNull PropertyDelegate propertyDelegate) {
        super(Screens.EXTRACTOR, syncId, playerInventory, inventory, propertyDelegate);
    }

    public ExtractorScreenHandler(int syncId,
                                  @NotNull PlayerInventory playerInventory,
                                  @NotNull PacketByteBuf packetByteBuf) {
        super(Screens.EXTRACTOR, syncId, playerInventory);

    }
}
