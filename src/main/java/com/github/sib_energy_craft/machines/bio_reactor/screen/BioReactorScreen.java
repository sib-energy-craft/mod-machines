package com.github.sib_energy_craft.machines.bio_reactor.screen;

import com.github.sib_energy_craft.energy_api.utils.Identifiers;
import com.github.sib_energy_craft.sec_utils.screen.ScreenSquareArea;
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

    private static final Identifier TEXTURE = Identifiers.of("textures/gui/container/bio_reactor.png");

    private static final ScreenSquareArea BATTERY = new ScreenSquareArea(34, 19, 8, 47);
    private static final ScreenSquareArea FERMENTATION = new ScreenSquareArea(54, 36, 19, 14);
    private static final ScreenSquareArea FERMENTS = new ScreenSquareArea(89, 20, 68, 46);

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
        int x = this.x;
        int y = this.y;
        drawTexture(matrices, x, y, 0, 0, backgroundWidth, backgroundHeight);

        int chargeProgress = this.handler.getChargeProgress(BATTERY.height());
        int chargeY = y + BATTERY.y() + BATTERY.height() - chargeProgress;
        int chargeV = BATTERY.height() - chargeProgress;
        drawTexture(matrices, x + BATTERY.x(), chargeY, 176, chargeV, BATTERY.width(), chargeProgress);

        int fermentationProgress = this.handler.getFermentationProgress(FERMENTATION.height());
        int fermentsY = y + FERMENTATION.y() + FERMENTATION.height() - fermentationProgress;
        int fermentsV = 47 + FERMENTATION.height() - fermentationProgress;
        drawTexture(matrices, x + FERMENTATION.x(), fermentsY, 176, fermentsV, FERMENTATION.width(), fermentationProgress);

        int fermentsFilling = this.handler.getFermentsFilling(FERMENTS.height());
        drawTexture(matrices, x + FERMENTS.x(), y + FERMENTS.y() + FERMENTS.height() - fermentsFilling, 184,
                FERMENTS.height() - fermentsFilling, FERMENTS.width(), fermentsFilling);

        var output = Text.translatable("energy.out.text", this.handler.getEnergyPacketSize());
        int outputOffset = 79 + (90 - textRenderer.getWidth(output)) / 2;
        this.textRenderer.draw(matrices, output, x + outputOffset, y + 73, Color.GRAY.getRGB());

        if(BATTERY.in(x, y, mouseX, mouseY)) {
            int charge = this.handler.getCharge();
            int maxCharge = this.handler.getMaxCharge();
            var charging = Text.translatable("energy.range.text", charge, maxCharge);
            this.renderTooltip(matrices, charging, mouseX, mouseY);
        }
        if(FERMENTS.in(x, y, mouseX, mouseY)) {
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