package com.github.sib_energy_craft.machines.bio_generator.screen;

import com.github.sib_energy_craft.energy_api.utils.Identifiers;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.NotNull;

import java.awt.*;

/**
 * @since 0.0.23
 * @author sibmaks
 */
public class BioReactorScreen extends HandledScreen<BioReactorScreenHandler> {
    private static final int BATTERY_X = 34;
    private static final int BATTERY_Y = 19;
    private static final int BATTERY_W = 8;
    private static final int BATTERY_H = 47;

    private static final int FERMENTATION_X = 54;
    private static final int FERMENTATION_Y = 36;
    private static final int FERMENTATION_W = 19;
    private static final int FERMENTATION_H = 14;

    private static final int FERMENTS_X = 89;
    private static final int FERMENTS_Y = 20;
    private static final int FERMENTS_W = 68;
    private static final int FERMENTS_H = 46;

    private static final Identifier TEXTURE = Identifiers.of("textures/gui/container/bio_reactor.png");

    public BioReactorScreen(@NotNull BioReactorScreenHandler handler,
                            @NotNull PlayerInventory inventory,
                            @NotNull Text title) {
        super(handler, inventory, title);
    }

    @Override
    protected void drawBackground(@NotNull MatrixStack matrices, float delta, int mouseX, int mouseY) {
        RenderSystem.setShader(GameRenderer::getPositionTexProgram);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.setShaderTexture(0, TEXTURE);
        int x = (width - backgroundWidth) / 2;
        int y = (height - backgroundHeight) / 2;
        drawTexture(matrices, x, y, 0, 0, backgroundWidth, backgroundHeight);
        int i = this.x;
        int j = this.y;
        int chargeProgress = this.handler.getChargeProgress(BATTERY_H);
        drawTexture(matrices, i + BATTERY_X, j + BATTERY_Y + BATTERY_H - chargeProgress, 176, BATTERY_H - chargeProgress, BATTERY_W, chargeProgress);

        int fermentationProgress = this.handler.getFermentationProgress(FERMENTATION_H);
        drawTexture(matrices, i + FERMENTATION_X, j + FERMENTATION_Y + FERMENTATION_H - fermentationProgress, 176,
                47 + FERMENTATION_H - fermentationProgress, FERMENTATION_W, fermentationProgress);

        int fermentsFilling = this.handler.getFermentsFilling(FERMENTS_H);
        drawTexture(matrices, i + FERMENTS_X, j + FERMENTS_Y + FERMENTS_H - fermentsFilling, 184,
                FERMENTS_H - fermentsFilling, FERMENTS_W, fermentsFilling);

        var output = Text.translatable("energy.out.text", this.handler.getEnergyPacketSize());
        int outputOffset = 79 + (90 - textRenderer.getWidth(output)) / 2;
        this.textRenderer.draw(matrices, output, i + outputOffset, j + 73, Color.GRAY.getRGB());

        if(mouseX >= i + BATTERY_X && mouseX <= i + BATTERY_X + BATTERY_W &&
                mouseY >= j + BATTERY_Y && mouseY <= j + BATTERY_Y + BATTERY_H) {
            int charge = this.handler.getCharge();
            int maxCharge = this.handler.getMaxCharge();
            var charging = Text.translatable("energy.range.text", charge, maxCharge);
            this.renderTooltip(matrices, charging, mouseX, mouseY);
        }
        if(mouseX >= i + FERMENTS_X && mouseX <= i + FERMENTS_X + FERMENTS_W &&
                mouseY >= j + FERMENTS_Y && mouseY <= j + FERMENTS_Y + FERMENTS_H) {
            int val = this.handler.getFerments();
            int max = this.handler.getMaxFerments();
            var charging = Text.translatable("ferments.range.text", val, max);
            this.renderTooltip(matrices, charging, mouseX, mouseY);
        }
    }

    @Override
    public void render(@NotNull MatrixStack matrices, int mouseX, int mouseY, float delta) {
        renderBackground(matrices);
        super.render(matrices, mouseX, mouseY, delta);
        drawMouseoverTooltip(matrices, mouseX, mouseY);
    }

    @Override
    protected void init() {
        super.init();
        titleX = (backgroundWidth - textRenderer.getWidth(title)) / 2;
    }
}