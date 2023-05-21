package com.github.sib_energy_craft.machines.extractor.screen;

import com.github.sib_energy_craft.machines.extractor.load.ScreenHandlers;
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
public class ExtractorScreenHandler extends AbstractExtractorScreenHandler {
    private static final MultiSlotMachineLayoutManager LAYOUT_MANAGER = new MultiSlotMachineLayoutManager(
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
        super(ScreenHandlers.EXTRACTOR, syncId, playerInventory, inventory, propertyDelegate, LAYOUT_MANAGER);
    }

    public ExtractorScreenHandler(int syncId,
                                  @NotNull PlayerInventory playerInventory,
                                  @NotNull PacketByteBuf packetByteBuf) {
        super(ScreenHandlers.EXTRACTOR, syncId, playerInventory, LAYOUT_MANAGER);

    }
}
