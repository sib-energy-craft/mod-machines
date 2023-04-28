package com.github.sib_energy_craft.energy_container.block;

import com.github.sib_energy_craft.energy_api.EnergyLevel;
import com.github.sib_energy_craft.energy_container.block.entity.EnergyContainerBlockEntity;
import com.github.sib_energy_craft.energy_container.load.Entities;
import lombok.Getter;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * @since 0.0.2
 * @author sibmaks
 */
public class EnergyContainerBlock extends AbstractEnergyContainerBlock {
    @Getter
    private final String containerNameCode;

    public EnergyContainerBlock(@NotNull Settings settings,
                                @NotNull String containerNameCode,
                                @NotNull EnergyLevel energyLevel,
                                int maxCharge) {
        super(settings, energyLevel, maxCharge);
        this.containerNameCode = containerNameCode;
    }

    @NotNull
    @Override
    public BlockEntity createBlockEntity(@NotNull BlockPos pos,
                                         @NotNull BlockState state) {
        return new EnergyContainerBlockEntity(pos, state, this);
    }

    @Override
    @Nullable
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(@NotNull World world,
                                                                  @NotNull BlockState state,
                                                                  @NotNull BlockEntityType<T> type) {
        return checkType(world, type, Entities.BASIC_ENERGY_CONTAINER);
    }

    @Override
    protected void openScreen(@NotNull World world,
                              @NotNull BlockPos blockPos,
                              @NotNull PlayerEntity playerEntity) {
        var blockEntity = world.getBlockEntity(blockPos);
        if (blockEntity instanceof EnergyContainerBlockEntity containerBlockEntity) {
            playerEntity.openHandledScreen(containerBlockEntity);
        }
    }
}
