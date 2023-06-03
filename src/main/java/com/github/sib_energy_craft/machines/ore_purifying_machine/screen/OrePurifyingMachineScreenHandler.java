package com.github.sib_energy_craft.machines.ore_purifying_machine.screen;

import com.github.sib_energy_craft.machines.cooking.screen.CookingEnergyMachineScreenHandler;
import com.github.sib_energy_craft.machines.ore_purifying_machine.load.ScreenHandlers;
import com.github.sib_energy_craft.machines.ore_purifying_machine.tag.OrePurifyingMachineTags;
import com.github.sib_energy_craft.machines.screen.layout.MultiSlotMachineLayoutManager;
import lombok.Getter;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketByteBuf;
import org.jetbrains.annotations.NotNull;
import org.joml.Vector2i;

/**
 * @since 0.0.26
 * @author sibmaks
 */
public class OrePurifyingMachineScreenHandler extends CookingEnergyMachineScreenHandler<OrePurifyingMachineState> {
    private static final MultiSlotMachineLayoutManager LAYOUT_MANAGER = new MultiSlotMachineLayoutManager(
            8, 166,
            8, 108,
            new Vector2i[]{new Vector2i(56, 26)},
            56, 62,
            new Vector2i[]{new Vector2i(128, 21), new Vector2i(128, 46), new Vector2i(128, 73)}
    );

    @Getter
    protected final int maxDrumSpeed;

    public OrePurifyingMachineScreenHandler(int syncId,
                                            @NotNull PlayerInventory playerInventory,
                                            @NotNull Inventory inventory,
                                            int maxDrumSpeed) {
        super(ScreenHandlers.ORE_PURIFYING_MACHINE, syncId, playerInventory, inventory, 1, 3,
                new OrePurifyingMachineState(), LAYOUT_MANAGER);
        this.maxDrumSpeed = maxDrumSpeed;
    }

    public OrePurifyingMachineScreenHandler(int syncId,
                                            @NotNull PlayerInventory playerInventory,
                                            @NotNull PacketByteBuf packetByteBuf) {
        super(ScreenHandlers.ORE_PURIFYING_MACHINE, syncId, playerInventory, 1, 3,
                new OrePurifyingMachineState(), LAYOUT_MANAGER);
        this.maxDrumSpeed = packetByteBuf.readInt();
    }

    @Override
    protected boolean isUsedInMachine(@NotNull ItemStack itemStack) {
        return OrePurifyingMachineTags.isUsedInOrePurifyingMachine(itemStack);
    }

}
