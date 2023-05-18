package com.github.sib_energy_craft.machines.advanced_macerator.block.entity;

import com.github.sib_energy_craft.machines.advanced_macerator.block.AdvancedMaceratorBlock;
import com.github.sib_energy_craft.machines.advanced_macerator.load.Entities;
import com.github.sib_energy_craft.machines.advanced_macerator.screen.AdvancedMaceratorScreenHandler;
import com.github.sib_energy_craft.machines.macerator.block.entity.AbstractMaceratorBlockEntity;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.NotNull;


/**
 * @since 0.0.17
 * @author sibmaks
 */
public class AdvancedMaceratorBlockEntity extends AbstractMaceratorBlockEntity<AdvancedMaceratorBlock> {

    public AdvancedMaceratorBlockEntity(@NotNull BlockPos pos,
                                       @NotNull BlockState state,
                                       @NotNull AdvancedMaceratorBlock block) {
        super(Entities.ADVANCED_MACERATOR, pos, state, block, 2);
    }

    @Override
    protected @NotNull Text getContainerName() {
        return Text.translatable("container.advanced_macerator");
    }

    @Override
    protected @NotNull ScreenHandler createScreenHandler(int syncId,
                                                         @NotNull PlayerInventory playerInventory) {
        return new AdvancedMaceratorScreenHandler(syncId, playerInventory, this, this.propertyMap);
    }

    @Override
    public void writeScreenOpeningData(@NotNull ServerPlayerEntity player,
                                       @NotNull PacketByteBuf buf) {
        buf.writeBlockPos(pos);
    }

    @Override
    public int getCookTimeTotal(@NotNull World world) {
        return super.getCookTimeTotal(world) * 60 / 100;
    }
}

