package com.github.sib_energy_craft.machines.induction_furnace.screen;

import com.github.sib_energy_craft.machines.energy_furnace.screen.AbstractEnergyFurnaceScreenHandler;
import com.github.sib_energy_craft.machines.induction_furnace.block.entity.InductionFurnaceProperties;
import com.github.sib_energy_craft.machines.induction_furnace.load.ScreenHandlers;
import com.github.sib_energy_craft.machines.screen.layout.MultiSlotMachineLayoutManager;
import lombok.Getter;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.network.PacketByteBuf;
import org.jetbrains.annotations.NotNull;
import org.joml.Vector2i;

/**
 * @since 0.0.17
 * @author sibmaks
 */
public class InductionFurnaceScreenHandler extends AbstractEnergyFurnaceScreenHandler {
    private static final MultiSlotMachineLayoutManager LAYOUT_MANAGER = new MultiSlotMachineLayoutManager(
            8, 142,
            8, 84,
            new Vector2i[]{new Vector2i(48, 17), new Vector2i(66, 17)},
            56, 53,
            new Vector2i[]{new Vector2i(116, 35), new Vector2i(142, 35)}
    );

    @Getter
    protected int heatPercent;

    public InductionFurnaceScreenHandler(int syncId,
                                      @NotNull PlayerInventory playerInventory,
                                      @NotNull Inventory inventory) {
        super(ScreenHandlers.INDUCTION_FURNACE, syncId, playerInventory, inventory, 2, LAYOUT_MANAGER);
    }

    public InductionFurnaceScreenHandler(int syncId,
                                         @NotNull PlayerInventory playerInventory,
                                         @NotNull PacketByteBuf packetByteBuf) {
        super(ScreenHandlers.INDUCTION_FURNACE, syncId, playerInventory, 2, LAYOUT_MANAGER);
    }

    @Override
    public <V> void onTypedPropertyChanged(int index, V value) {
        super.onTypedPropertyChanged(index, value);
        if(index == InductionFurnaceProperties.HEAT.getIndex()) {
            heatPercent = (int) value;
        }
    }
}
