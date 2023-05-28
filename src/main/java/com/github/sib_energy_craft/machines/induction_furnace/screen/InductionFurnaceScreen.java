package com.github.sib_energy_craft.machines.induction_furnace.screen;

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
 * @since 0.0.17
 * @author sibmaks
 */
public class InductionFurnaceScreen extends HandledScreen<InductionFurnaceScreenHandler> {
    private static final Identifier TEXTURE = Identifiers.of("textures/gui/container/induction_furnace.png");

    private static final ScreenSquareArea CHARGE = new ScreenSquareArea(60, 37, 7, 13);

    public InductionFurnaceScreen(@NotNull InductionFurnaceScreenHandler handler,
                               @NotNull PlayerInventory inventory,
                               @NotNull Text title) {
        super(handler, inventory, title);
    }

    @Override
    protected void drawBackground(@NotNull MatrixStack matrices,
                                  float delta,
                                  int mouseX,
                                  int mouseY) {
        RenderSystem.setShader(GameRenderer::getPositionTexProgram);
        RenderSystem.setShaderColor(1.0f, 1.0f, 1.0f, 1.0f);
        RenderSystem.setShaderTexture(0, TEXTURE);
        int x = this.x;
        int y = this.y;
        drawTexture(matrices, x, y, 0, 0, this.backgroundWidth, this.backgroundHeight);
        int progress = this.handler.getChargeProgress();
        drawTexture(matrices, x + CHARGE.x(), y + CHARGE.y(), 176, 0, CHARGE.width(), progress);
        progress = this.handler.getCookProgress(22);
        drawTexture(matrices, x + 80, y + 35, 176, 13, progress, 16);
        int heat = this.handler.getHeatPercent();
        var heatText = Text.translatable("induction_furnace.heat.text", heat);
        var heatTextX = 5 + (52 - textRenderer.getWidth(heatText)) / 2;
        this.textRenderer.drawWithShadow(matrices, heatText, x + heatTextX, y + 38, Color.WHITE.getRGB());
        if(CHARGE.in(x, y, mouseX, mouseY)) {
            var charge = this.handler.getCharge();
            var maxCharge = this.handler.getMaxCharge();
            var charging = Text.translatable("energy.range.text", charge, maxCharge);
            this.renderTooltip(matrices, charging, mouseX, mouseY);
        }
    }

    @Override
    public void render(@NotNull MatrixStack matrices,
                       int mouseX,
                       int mouseY,
                       float delta) {
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