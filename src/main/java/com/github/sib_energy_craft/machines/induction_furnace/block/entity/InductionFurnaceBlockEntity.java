package com.github.sib_energy_craft.machines.induction_furnace.block.entity;

import com.github.sib_energy_craft.machines.block.entity.EnergyMachineEvent;
import com.github.sib_energy_craft.machines.energy_furnace.block.entity.AbstractEnergyFurnaceBlockEntity;
import com.github.sib_energy_craft.machines.induction_furnace.block.InductionFurnaceBlock;
import com.github.sib_energy_craft.machines.induction_furnace.load.Entities;
import com.github.sib_energy_craft.machines.induction_furnace.screen.InductionFurnaceScreenHandler;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.nbt.NbtCompound;
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
public class InductionFurnaceBlockEntity extends AbstractEnergyFurnaceBlockEntity<InductionFurnaceBlock> {

    protected int heatTicks;

    public InductionFurnaceBlockEntity(@NotNull BlockPos pos,
                                       @NotNull BlockState state,
                                       @NotNull InductionFurnaceBlock block) {
        super(Entities.INDUCTION_FURNACE, pos, state, block, 2);
        this.addListener(EnergyMachineEvent.ENERGY_USED, () -> heatTicks = Math.min(heatTicks + 1, block.getMaxHeatTicks()));
        this.addListener(EnergyMachineEvent.CAN_NOT_COOK, () -> heatTicks = Math.max(heatTicks - 1, 0));
        this.addListener(EnergyMachineEvent.ENERGY_NOT_ENOUGH, () -> heatTicks = Math.max(heatTicks - 1, 0));
        this.propertyMap.add(InductionFurnaceProperties.HEAT, () -> (int)(100f * heatTicks / block.getMaxHeatTicks()));
    }

    @Override
    public void readNbt(@NotNull NbtCompound nbt) {
        super.readNbt(nbt);
        this.heatTicks = nbt.getInt("HeatTicks");
    }

    @Override
    protected void writeNbt(@NotNull NbtCompound nbt) {
        super.writeNbt(nbt);
        nbt.putInt("HeatTicks", heatTicks);
    }

    @Override
    protected @NotNull Text getContainerName() {
        return Text.translatable("container.induction_furnace");
    }

    @Override
    protected @NotNull ScreenHandler createScreenHandler(int syncId,
                                                         @NotNull PlayerInventory playerInventory) {
        return new InductionFurnaceScreenHandler(syncId, playerInventory, this, this.propertyMap);
    }

    @Override
    public void writeScreenOpeningData(@NotNull ServerPlayerEntity player,
                                       @NotNull PacketByteBuf buf) {
        buf.writeBlockPos(pos);
    }

    @Override
    public int getCookTimeInc(@NotNull World world) {
        var furnace = (InductionFurnaceBlock) block;
        float heatCookSpeedMultiplier = furnace.getHeatCookSpeedMultiplier();
        float maxHeatTicks = furnace.getMaxHeatTicks();
        return (int) Math.max(1, heatCookSpeedMultiplier * (heatTicks / maxHeatTicks));
    }
}

