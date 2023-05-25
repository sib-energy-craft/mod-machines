package com.github.sib_energy_craft.machines.bio_generator.screen;

import com.github.sib_energy_craft.machines.bio_generator.load.ScreenHandlers;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.network.PacketByteBuf;
import org.jetbrains.annotations.NotNull;

/**
 * @since 0.0.23
 * @author sibmaks
 */
public class BioReactorScreenHandler extends AbstractBioReactorScreenHandler {
    private static final BioReactorSlotLayoutManager LAYOUT_MANAGER = new BioReactorSlotLayoutManager(
            8, 142,
            8, 84,
            56, 17,
            56, 53
    );

    public BioReactorScreenHandler(int syncId,
                                   @NotNull PlayerInventory playerInventory,
                                   @NotNull Inventory inventory,
                                   int maxCharge,
                                   int maxFerments,
                                   int energyPacketSize) {
        super(ScreenHandlers.BIO_REACTOR, syncId, playerInventory, inventory, maxCharge, maxFerments, energyPacketSize,
                LAYOUT_MANAGER);
    }

    public BioReactorScreenHandler(int syncId,
                                   @NotNull PlayerInventory playerInventory,
                                   @NotNull PacketByteBuf packetByteBuf) {
        super(ScreenHandlers.BIO_REACTOR, syncId, playerInventory, packetByteBuf.readInt(), packetByteBuf.readInt(),
                packetByteBuf.readInt(), LAYOUT_MANAGER);
    }
}
