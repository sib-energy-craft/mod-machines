package com.github.sib_energy_craft.machines.generator.screen;

import com.github.sib_energy_craft.energy_api.utils.Identifiers;
import com.github.sib_energy_craft.sec_utils.screen.ScreenSquareArea;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.NotNull;

import java.awt.*;

/**
 * @since 0.0.1
 * @author sibmaks
 */
public class EnergyGeneratorScreen extends HandledScreen<EnergyGeneratorScreenHandler> {
    private static final Identifier TEXTURE = Identifiers.of("textures/gui/container/energy_generator.png");

    private static final ScreenSquareArea BATTERY = new ScreenSquareArea(84, 38, 24, 9);

    public EnergyGeneratorScreen(@NotNull EnergyGeneratorScreenHandler handler,
                                 @NotNull PlayerInventory inventory,
                                 @NotNull Text title) {
        super(handler, inventory, title);
    }

    @Override
    protected void drawBackground(@NotNull DrawContext drawContext, float delta, int mouseX, int mouseY) {
        RenderSystem.setShader(GameRenderer::getPositionTexProgram);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.setShaderTexture(0, TEXTURE);
        int x = this.x;
        int y = this.y;
        drawContext.drawTexture(TEXTURE, x, y, 0, 0, backgroundWidth, backgroundHeight);
        if (this.handler.isBurning()) {
            int fuelProgress = this.handler.getFuelProgress();
            drawContext.drawTexture(TEXTURE, x + 56, y + 36 + 12 - fuelProgress, 176, 12 - fuelProgress, 14, fuelProgress + 1);
        }
        int chargeProgress = this.handler.getChargeProgress();
        drawContext.drawTexture(TEXTURE, x + BATTERY.x(), y + BATTERY.y(), 176, 14, chargeProgress, BATTERY.height());
        if(BATTERY.in(x, y, mouseX, mouseY)) {
            int charge = this.handler.getCharge();
            int maxCharge = this.handler.getMaxCharge();
            Text charging = Text.translatable("energy.range.text", charge, maxCharge);
            drawContext.drawTooltip(textRenderer, charging, mouseX, mouseY);
        }
        var output = Text.translatable("energy.out.text", this.handler.getEnergyPacketSize());
        drawContext.drawText(textRenderer, output, x + 81, y + 58, Color.GRAY.getRGB(), false);
    }

    @Override
    public void render(@NotNull DrawContext drawContext, int mouseX, int mouseY, float delta) {
        renderBackground(drawContext);
        super.render(drawContext, mouseX, mouseY, delta);
        drawMouseoverTooltip(drawContext, mouseX, mouseY);
    }

    @Override
    protected void init() {
        super.init();
        titleX = (backgroundWidth - textRenderer.getWidth(title)) / 2;
    }
}