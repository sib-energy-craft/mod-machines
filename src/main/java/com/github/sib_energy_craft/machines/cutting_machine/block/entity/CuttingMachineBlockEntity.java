package com.github.sib_energy_craft.machines.cutting_machine.block.entity;

import com.github.sib_energy_craft.machines.cutting_machine.block.CuttingMachineBlock;
import com.github.sib_energy_craft.machines.cutting_machine.load.Entities;
import com.github.sib_energy_craft.machines.cutting_machine.screen.CuttingMachineScreenHandler;
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
public class CuttingMachineBlockEntity extends AbstractCuttingMachineBlockEntity<CuttingMachineBlock> {
    public CuttingMachineBlockEntity(@NotNull BlockPos pos,
                                     @NotNull BlockState state,
                                     @NotNull CuttingMachineBlock block) {
        super(Entities.CUTTING_MACHINE, pos, state, IronCraftingTableRecipeType.INSTANCE, block);
    }

    @NotNull
    @Override
    protected Text getContainerName() {
        return Text.translatable("container.cutting_machine");
    }

    @NotNull
    @Override
    protected ScreenHandler createScreenHandler(int syncId, @NotNull PlayerInventory playerInventory) {
        return new CuttingMachineScreenHandler(syncId, playerInventory, this, this.propertyMap);
    }

    @Override
    public void writeScreenOpeningData(@NotNull ServerPlayerEntity player,
                                       @NotNull PacketByteBuf buf) {
        buf.writeBlockPos(pos);
    }

}

