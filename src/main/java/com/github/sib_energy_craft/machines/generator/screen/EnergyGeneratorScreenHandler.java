package com.github.sib_energy_craft.machines.generator.screen;

import com.github.sib_energy_craft.machines.generator.load.Screens;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.screen.PropertyDelegate;
import org.jetbrains.annotations.NotNull;

/**
 * @author sibmaks
 * Created at 21-05-2022
 */
public class EnergyGeneratorScreenHandler extends AbstractEnergyGeneratorScreenHandler {

    public EnergyGeneratorScreenHandler(int syncId,
                                        @NotNull PlayerInventory playerInventory,
                                        @NotNull Inventory inventory,
                                        @NotNull PropertyDelegate propertyDelegate) {
        super(Screens.ENERGY_GENERATOR, syncId, playerInventory, inventory, propertyDelegate);
    }

    public EnergyGeneratorScreenHandler(int syncId,
                                        @NotNull PlayerInventory playerInventory,
                                        @NotNull PacketByteBuf packetByteBuf) {
        super(Screens.ENERGY_GENERATOR, syncId, playerInventory);

    }
}
