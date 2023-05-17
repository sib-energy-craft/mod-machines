package com.github.sib_energy_craft.machines.press_machine.block.entity;

import com.github.sib_energy_craft.machines.press_machine.block.PressMachineBlock;
import com.github.sib_energy_craft.machines.press_machine.load.Entities;
import com.github.sib_energy_craft.machines.press_machine.screen.PressMachineScreenHandler;
import com.github.sib_energy_craft.recipes.recipe.IronCraftingTableRecipeType;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.math.BlockPos;
import org.jetbrains.annotations.NotNull;

/**
 * @since 0.0.17
 * @author sibmaks
 */
public class PressMachineBlockEntity extends AbstractPressMachineBlockEntity<PressMachineBlock> {
    public PressMachineBlockEntity(@NotNull BlockPos pos,
                                   @NotNull BlockState state,
                                   @NotNull PressMachineBlock block) {
        super(Entities.PRESS_MACHINE, pos, state, IronCraftingTableRecipeType.INSTANCE, block);
    }

    @NotNull
    @Override
    protected Text getContainerName() {
        return Text.translatable("container.press_machine");
    }

    @NotNull
    @Override
    protected ScreenHandler createScreenHandler(int syncId, @NotNull PlayerInventory playerInventory) {
        return new PressMachineScreenHandler(syncId, playerInventory, this, this.propertyMap);
    }

    @Override
    public void writeScreenOpeningData(@NotNull ServerPlayerEntity player,
                                       @NotNull PacketByteBuf buf) {
        buf.writeBlockPos(pos);
    }

}

