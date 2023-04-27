package com.github.sib_energy_craft.machines.generator.item;

import com.github.sib_energy_craft.energy_api.items.ChargeableItem;
import com.github.sib_energy_craft.machines.generator.block.AbstractEnergyGeneratorBlock;
import lombok.Getter;
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
 * @since 0.0.1
 * @author sibmaks
 */
public class EnergyGeneratorBlockItem extends BlockItem implements ChargeableItem {
    private final int output;
    @Getter
    private final int maxCharge;

    public EnergyGeneratorBlockItem(@NotNull AbstractEnergyGeneratorBlock block, @NotNull Settings settings) {
        super(block, settings);
        this.output = block.getEnergyPacketSize().intValue();
        this.maxCharge = block.getMaxCharge();
    }

    @Override
    public void appendTooltip(@NotNull ItemStack stack,
                              @Nullable World world,
                              @NotNull List<Text> tooltip,
                              @NotNull TooltipContext context) {
        super.appendTooltip(stack, world, tooltip, context);
        var item = (ChargeableItem) stack.getItem();
        tooltip.add(Text.translatable("attribute.name.sib_energy_craft.charge", item.getCharge(stack))
                .setStyle(Style.EMPTY.withColor(Color.GRAY.getRGB())));
        tooltip.add(Text.translatable("attribute.name.sib_energy_craft.output_eu", output)
                .setStyle(Style.EMPTY.withColor(Color.GRAY.getRGB())));
        tooltip.add(Text.translatable("attribute.name.sib_energy_craft.max_charge", maxCharge)
                .setStyle(Style.EMPTY.withColor(Color.GRAY.getRGB())));
    }
}
