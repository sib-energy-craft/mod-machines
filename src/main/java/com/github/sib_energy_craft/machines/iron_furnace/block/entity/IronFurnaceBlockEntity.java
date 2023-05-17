package com.github.sib_energy_craft.machines.iron_furnace.block.entity;

import com.github.sib_energy_craft.machines.iron_furnace.block.AbstractIronFurnaceBlock;
import com.github.sib_energy_craft.machines.iron_furnace.load.Entities;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.screen.FurnaceScreenHandler;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.text.Text;
import net.minecraft.util.math.BlockPos;
import org.jetbrains.annotations.NotNull;

/**
 * @since 0.0.1
 * @author sibmaks
 */
public class IronFurnaceBlockEntity extends AbstractIronFurnaceBlockEntity {
    public IronFurnaceBlockEntity(@NotNull BlockPos pos,
                                  @NotNull BlockState state,
                                  @NotNull AbstractIronFurnaceBlock block) {
        super(Entities.IRON_FURNACE, pos, state, block);
    }

    @Override
    protected Text getContainerName() {
        return Text.translatable("container.iron_furnace");
    }

    @Override
    protected ScreenHandler createScreenHandler(int syncId,
                                                @NotNull PlayerInventory playerInventory) {
        return new FurnaceScreenHandler(syncId, playerInventory, this, this.propertyDelegate);
    }
}

