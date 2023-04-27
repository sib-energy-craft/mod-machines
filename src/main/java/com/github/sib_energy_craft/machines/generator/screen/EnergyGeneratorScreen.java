package com.github.sib_energy_craft.machines.generator.screen;

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
 * @since 0.0.1
 * @author sibmaks
 */
public class EnergyGeneratorScreen extends HandledScreen<EnergyGeneratorScreenHandler> {
    private static final Identifier TEXTURE = Identifiers.of("textures/gui/container/energy_generator.png");

    public EnergyGeneratorScreen(@NotNull EnergyGeneratorScreenHandler handler,
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
        if (this.handler.isBurning()) {
            int fuelProgress = this.handler.getFuelProgress();
            drawTexture(matrices, i + 56, j + 36 + 12 - fuelProgress, 176, 12 - fuelProgress, 14, fuelProgress + 1);
        }
        int chargeProgress = this.handler.getChargeProgress();
        drawTexture(matrices, i + 84, j + 38, 176, 14, chargeProgress, 9);
        if(mouseX >= i + 84 && mouseX <= i + 84 + 24 &&
        mouseY >= j + 38 && mouseY <= j + 38 + 9) {
            int charge = this.handler.getCharge();
            int maxCharge = this.handler.getMaxCharge();
            Text charging = Text.translatable("energy.range.text", charge, maxCharge);
            this.renderTooltip(matrices, charging, mouseX, mouseY);
        }
        var output = Text.translatable("energy.out.text", this.handler.getEnergyPacketSize());
        this.textRenderer.draw(matrices, output, i + 81, j + 58, Color.GRAY.getRGB());
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