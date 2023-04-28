package com.github.sib_energy_craft.machines.energy_furnace.screen.slot;

import com.github.sib_energy_craft.machines.energy_furnace.block.entity.AbstractEnergyFurnaceBlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.slot.Slot;
import net.minecraft.server.network.ServerPlayerEntity;
import org.jetbrains.annotations.NotNull;

/**
 * @since 0.0.2
 * @author sibmaks
 */
public class EnergyFurnaceOutputSlot extends Slot {
    private final PlayerEntity player;
    private int amount;

    public EnergyFurnaceOutputSlot(@NotNull PlayerEntity player,
                                   @NotNull Inventory inventory,
                                   int index,
                                   int x,
                                   int y) {
        super(inventory, index, x, y);
        this.player = player;
    }

    @Override
    public boolean canInsert(@NotNull ItemStack stack) {
        return false;
    }

    @NotNull
    @Override
    public ItemStack takeStack(int amount) {
        if (this.hasStack()) {
            this.amount += Math.min(amount, this.getStack().getCount());
        }

        return super.takeStack(amount);
    }

    @Override
    public void onTakeItem(@NotNull PlayerEntity player,
                           @NotNull ItemStack stack) {
        this.onCrafted(stack);
        super.onTakeItem(player, stack);
    }

    @Override
    protected void onCrafted(@NotNull ItemStack stack, int amount) {
        this.amount += amount;
        this.onCrafted(stack);
    }

    @Override
    protected void onCrafted(@NotNull ItemStack stack) {
        stack.onCraft(this.player.world, this.player, this.amount);
        if (this.player instanceof ServerPlayerEntity serverPlayerEntity &&
                this.inventory instanceof AbstractEnergyFurnaceBlockEntity energyFurnaceBlockEntity) {
            energyFurnaceBlockEntity.dropExperienceForRecipesUsed(serverPlayerEntity);
        }

        this.amount = 0;
    }
}
