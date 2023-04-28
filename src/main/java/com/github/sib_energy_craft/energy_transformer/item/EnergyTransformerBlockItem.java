package com.github.sib_energy_craft.energy_transformer.item;

import com.github.sib_energy_craft.energy_transformer.block.AbstractEnergyTransformerBlock;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.world.World;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.awt.*;
import java.util.List;

/**
 * @since 0.0.4
 * @author sibmaks
 */
public class EnergyTransformerBlockItem extends BlockItem {

    public EnergyTransformerBlockItem(@NotNull AbstractEnergyTransformerBlock block,
                                      @NotNull Settings settings) {
        super(block, settings);
    }

    @Override
    public void appendTooltip(@NotNull ItemStack stack,
                              @Nullable World world,
                              @NotNull List<Text> tooltip,
                              @NotNull TooltipContext context) {
        super.appendTooltip(stack, world, tooltip, context);
        var transformerBlock = getBlock();
        int textColor = Color.GRAY.getRGB();
        var textStyle = Style.EMPTY.withColor(textColor);
        tooltip.add(Text.translatable("attribute.name.sib_energy_craft.low_eu", transformerBlock.getLowEnergyLevel().to)
                .setStyle(textStyle));
        tooltip.add(Text.translatable("attribute.name.sib_energy_craft.high_eu", transformerBlock.getHighEnergyLevel().to)
                .setStyle(textStyle));
    }

    @Override
    public @NotNull AbstractEnergyTransformerBlock getBlock() {
        return (AbstractEnergyTransformerBlock) super.getBlock();
    }
}
