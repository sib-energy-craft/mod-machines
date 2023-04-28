package com.github.sib_energy_craft.energy_transformer.screen;

import com.github.sib_energy_craft.energy_transformer.block.entity.AbstractEnergyTransformerBlockEntity;
import com.github.sib_energy_craft.energy_transformer.block.entity.AbstractEnergyTransformerMode;
import com.github.sib_energy_craft.energy_transformer.block.entity.AbstractEnergyTransformerProperties;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.*;
import org.jetbrains.annotations.NotNull;

/**
 * @since 0.0.4
 * @author sibmaks
 */
public abstract class AbstractEnergyTransformerScreenHandler extends ScreenHandler {
    private final PropertyDelegate propertyDelegate;
    private final ScreenHandlerContext context;

    protected AbstractEnergyTransformerScreenHandler(@NotNull ScreenHandlerType<?> type,
                                                     int syncId,
                                                     @NotNull PlayerInventory playerInventory) {
        this(type, syncId, new ArrayPropertyDelegate(3), ScreenHandlerContext.EMPTY);
    }

    protected AbstractEnergyTransformerScreenHandler(@NotNull ScreenHandlerType<?> type,
                                                     int syncId,
                                                     @NotNull PropertyDelegate propertyDelegate,
                                                     @NotNull ScreenHandlerContext context) {
        super(type, syncId);
        AbstractEnergyTransformerScreenHandler.checkDataCount(propertyDelegate, 3);
        this.propertyDelegate = propertyDelegate;
        this.context = context;
        this.addProperties(propertyDelegate);
    }

    @Override
    public @NotNull ItemStack quickMove(@NotNull PlayerEntity player, int index) {
        return ItemStack.EMPTY;
    }

    @Override
    public boolean canUse(@NotNull PlayerEntity player) {
        return true;
    }

    /**
     * Get transformer low level
     *
     * @return low level
     */
    public int getLowLevel() {
        return this.propertyDelegate.get(AbstractEnergyTransformerProperties.LOW_LEVEL.ordinal());
    }

    /**
     * Get transformer max level
     * @return max level
     */
    public int getMaxLevel() {
        return this.propertyDelegate.get(AbstractEnergyTransformerProperties.MAX_LEVEL.ordinal());
    }

    /**
     * Get current transformer mode
     *
     * @return transformer mode
     */
    public  @NotNull AbstractEnergyTransformerMode getMode() {
        int modeOrdinal = this.propertyDelegate.get(AbstractEnergyTransformerProperties.MODE.ordinal());
        return AbstractEnergyTransformerMode.values()[modeOrdinal];
    }

    private void setMode(@NotNull AbstractEnergyTransformerMode mode) {
        super.setProperty(AbstractEnergyTransformerProperties.MODE.ordinal(), mode.ordinal());
        this.context.run((world, pos) -> {
            var blockEntity = world.getBlockEntity(pos);
            if(blockEntity instanceof AbstractEnergyTransformerBlockEntity transformerBlockEntity) {
                transformerBlockEntity.setMode(mode);
            }
        });
    }

    @Override
    public boolean onButtonClick(@NotNull PlayerEntity player, int id) {
        var button = EnergyTransformerScreen.Button.values()[id];
        if(button == EnergyTransformerScreen.Button.MODE_UP) {
            setMode(AbstractEnergyTransformerMode.UP);
            return true;
        } else if(button == EnergyTransformerScreen.Button.MODE_DOWN) {
            setMode(AbstractEnergyTransformerMode.DOWN);
            return true;
        }
        return false;
    }
}

