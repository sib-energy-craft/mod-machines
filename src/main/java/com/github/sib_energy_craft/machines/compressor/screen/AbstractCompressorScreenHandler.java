package com.github.sib_energy_craft.machines.compressor.screen;

import com.github.sib_energy_craft.machines.compressor.tag.CompressorTags;
import com.github.sib_energy_craft.machines.screen.AbstractEnergyMachineScreenHandler;
import com.github.sib_energy_craft.machines.screen.layout.SlotLayoutManager;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.ScreenHandlerType;
import org.jetbrains.annotations.NotNull;

/**
 * @since 0.0.1
 * @author sibmaks
 */
public abstract class AbstractCompressorScreenHandler extends AbstractEnergyMachineScreenHandler {

    protected AbstractCompressorScreenHandler(@NotNull ScreenHandlerType<?> type,
                                              int syncId,
                                              @NotNull PlayerInventory playerInventory,
                                              @NotNull SlotLayoutManager slotLayoutManager) {
        super(type, syncId, playerInventory, slotLayoutManager);
    }

    protected AbstractCompressorScreenHandler(@NotNull ScreenHandlerType<?> type,
                                              int syncId,
                                              @NotNull PlayerInventory playerInventory,
                                              @NotNull Inventory inventory,
                                              @NotNull SlotLayoutManager slotLayoutManager) {
        super(type, syncId, playerInventory, inventory, slotLayoutManager);
    }

    @Override
    protected boolean isUsedInMachine(@NotNull ItemStack itemStack) {
        return CompressorTags.isUsedInCompressor(itemStack);
    }
}

