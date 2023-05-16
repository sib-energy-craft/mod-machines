package com.github.sib_energy_craft.machines.cutting_machine.screen;

import com.github.sib_energy_craft.machines.cutting_machine.load.client.Screens;
import com.github.sib_energy_craft.machines.screen.VisualSettings;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.screen.PropertyDelegate;
import org.jetbrains.annotations.NotNull;

/**
 * @since 0.0.17
 * @author sibmaks
 */
public class CuttingMachineScreenHandler extends AbstractCuttingMachineScreenHandler {
    private static final VisualSettings VISUAL_SETTINGS = new VisualSettings(
            8, 142,
            8, 84,
            56, 17,
            56, 53,
            116, 35
    );


    public CuttingMachineScreenHandler(int syncId,
                                       @NotNull PlayerInventory playerInventory,
                                       @NotNull Inventory inventory,
                                       @NotNull PropertyDelegate propertyDelegate) {
        super(Screens.CUTTING_MACHINE, syncId, playerInventory, inventory, propertyDelegate, VISUAL_SETTINGS);
    }

    public CuttingMachineScreenHandler(int syncId,
                                       @NotNull PlayerInventory playerInventory,
                                       @NotNull PacketByteBuf packetByteBuf) {
        super(Screens.CUTTING_MACHINE, syncId, playerInventory, VISUAL_SETTINGS);
    }
}
