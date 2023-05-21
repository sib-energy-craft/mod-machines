package com.github.sib_energy_craft.machines.advanced_macerator.screen;

import com.github.sib_energy_craft.machines.advanced_macerator.load.ScreenHandlers;
import com.github.sib_energy_craft.machines.macerator.screen.AbstractMaceratorScreenHandler;
import com.github.sib_energy_craft.machines.screen.layout.MultiSlotMachineLayoutManager;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.screen.PropertyDelegate;
import org.jetbrains.annotations.NotNull;
import org.joml.Vector2i;

/**
 * @since 0.0.17
 * @author sibmaks
 */
public class AdvancedMaceratorScreenHandler extends AbstractMaceratorScreenHandler {
    private static final MultiSlotMachineLayoutManager LAYOUT_MANAGER = new MultiSlotMachineLayoutManager(
            8, 142,
            8, 84,
            new Vector2i[]{new Vector2i(48, 17), new Vector2i(66, 17)},
            56, 53,
            new Vector2i[]{new Vector2i(116, 35), new Vector2i(142, 35)}
    );

    public AdvancedMaceratorScreenHandler(int syncId,
                                      @NotNull PlayerInventory playerInventory,
                                      @NotNull Inventory inventory,
                                      @NotNull PropertyDelegate propertyDelegate) {
        super(ScreenHandlers.ADVANCED_MACERATOR, syncId, playerInventory, inventory, propertyDelegate, 2, LAYOUT_MANAGER);
    }

    public AdvancedMaceratorScreenHandler(int syncId,
                                         @NotNull PlayerInventory playerInventory,
                                         @NotNull PacketByteBuf packetByteBuf) {
        super(ScreenHandlers.ADVANCED_MACERATOR, syncId, playerInventory, 2, LAYOUT_MANAGER);
    }
}
