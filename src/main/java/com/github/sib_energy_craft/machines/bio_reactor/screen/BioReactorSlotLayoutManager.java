package com.github.sib_energy_craft.machines.bio_reactor.screen;

import com.github.sib_energy_craft.machines.screen.layout.SlotLayoutManager;
import com.github.sib_energy_craft.sec_utils.screen.slot.SlotType;
import com.github.sib_energy_craft.sec_utils.screen.slot.SlotTypes;
import org.jetbrains.annotations.NotNull;
import org.joml.Vector2i;

/**
 * @author sibmaks
 * @since 0.0.23
 */
public class BioReactorSlotLayoutManager implements SlotLayoutManager {
    private final Vector2i quickAccess;
    private final Vector2i playerInventory;
    private final Vector2i chargeSlot;
    private final Vector2i fuelSlot;


    public BioReactorSlotLayoutManager(int quickAccessX, int quickAccessY,
                                       int playerInventoryX, int playerInventoryY,
                                       int chargeSlotX, int chargeSlotY,
                                       int fuelSlotX, int fuelSlotY) {
        this.quickAccess = new Vector2i(quickAccessX, quickAccessY);
        this.playerInventory = new Vector2i(playerInventoryX, playerInventoryY);
        this.chargeSlot = new Vector2i(chargeSlotX, chargeSlotY);
        this.fuelSlot = new Vector2i(fuelSlotX, fuelSlotY);
    }

    @Override
    public @NotNull Vector2i getSlotPosition(@NotNull SlotType slotType, int typeIndex, int inventoryIndex) {
        if(slotType == SlotTypes.QUICK_ACCESS) {
            return new Vector2i(quickAccess.x + typeIndex * 18, quickAccess.y);
        }
        if(slotType == SlotTypes.PLAYER_INVENTORY) {
            int i = typeIndex / 9;
            int j = typeIndex - i * 9;
            return new Vector2i(playerInventory.x + j * 18, playerInventory.y + i * 18);
        }
        if(slotType == BioReactorSlotTypes.CHARGE) {
            return chargeSlot;
        }
        if(slotType == BioReactorSlotTypes.FUEL) {
            return fuelSlot;
        }
        return new Vector2i(0, 0);
    }
}
