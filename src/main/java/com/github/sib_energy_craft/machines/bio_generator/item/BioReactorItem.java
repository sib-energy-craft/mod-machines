package com.github.sib_energy_craft.machines.bio_generator.item;

import com.github.sib_energy_craft.energy_api.items.ChargeableItem;
import com.github.sib_energy_craft.machines.bio_generator.block.AbstractBioReactorBlock;
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
import java.util.Optional;

/**
 * @since 0.0.23
 * @author sibmaks
 */
public class BioReactorItem extends BlockItem implements ChargeableItem {
    private static final String FERMENTS = "Ferments";

    private final int output;
    @Getter
    private final int maxCharge;
    private final int maxFerments;

    public BioReactorItem(@NotNull AbstractBioReactorBlock block, @NotNull Settings settings) {
        super(block, settings);
        this.output = block.getEnergyPacketSize().intValue();
        this.maxCharge = block.getMaxCharge();
        this.maxFerments = block.getMaxFerments();
    }

    @Override
    public void appendTooltip(@NotNull ItemStack stack,
                              @Nullable World world,
                              @NotNull List<Text> tooltip,
                              @NotNull TooltipContext context) {
        super.appendTooltip(stack, world, tooltip, context);
        var item = (BioReactorItem) stack.getItem();
        tooltip.add(Text.translatable("attribute.name.sib_energy_craft.charge", item.getCharge(stack))
                .setStyle(Style.EMPTY.withColor(Color.GRAY.getRGB())));
        tooltip.add(Text.translatable("attribute.name.sib_energy_craft.output_eu", output)
                .setStyle(Style.EMPTY.withColor(Color.GRAY.getRGB())));
        tooltip.add(Text.translatable("attribute.name.sib_energy_craft.max_charge", maxCharge)
                .setStyle(Style.EMPTY.withColor(Color.GRAY.getRGB())));
        tooltip.add(Text.translatable("attribute.name.sib_energy_craft.ferments", item.getFerments(stack))
                .setStyle(Style.EMPTY.withColor(Color.GRAY.getRGB())));
        tooltip.add(Text.translatable("attribute.name.sib_energy_craft.max_ferments", maxFerments)
                .setStyle(Style.EMPTY.withColor(Color.GRAY.getRGB())));
    }

    /**
     * Save ferments into item stack
     *
     * @param itemStack item stack to save
     * @param ferments amount of ferments to save
     */
    public void saveFerments(@NotNull ItemStack itemStack, int ferments) {
        var nbt = itemStack.getOrCreateNbt();
        nbt.putInt(FERMENTS, Math.min(maxFerments, ferments));
    }

    /**
     * Get ferments from item stack
     *
     * @param itemStack item stack
     * @return amount of ferments
     */
    public int getFerments(@NotNull ItemStack itemStack) {
        return Optional.ofNullable(itemStack.getNbt())
                .map(it -> it.getInt(FERMENTS))
                .orElse(0);
    }
}
