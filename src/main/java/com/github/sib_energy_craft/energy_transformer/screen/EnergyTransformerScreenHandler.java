package com.github.sib_energy_craft.energy_transformer.screen;

import com.github.sib_energy_craft.energy_transformer.load.client.Screens;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.screen.PropertyDelegate;
import net.minecraft.screen.ScreenHandlerContext;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * @since 0.0.4
 * @author sibmaks
 */
public class EnergyTransformerScreenHandler extends AbstractEnergyTransformerScreenHandler {

    public EnergyTransformerScreenHandler(int syncId,
                                          @NotNull PropertyDelegate propertyDelegate,
                                          @Nullable World world,
                                          @NotNull BlockPos pos) {
        super(Screens.ENERGY_TRANSFORMER, syncId, propertyDelegate, ScreenHandlerContext.create(world, pos));
    }

    public EnergyTransformerScreenHandler(int syncId,
                                          @NotNull PlayerInventory inventory,
                                          @NotNull PacketByteBuf buf) {
        super(Screens.ENERGY_TRANSFORMER, syncId, inventory);
    }
}
