package com.github.sib_energy_craft.machines.advanced_macerator.screen;

import com.github.sib_energy_craft.energy_api.utils.Identifiers;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.NotNull;

/**
 * @since 0.0.17
 * @author sibmaks
 */
public class AdvancedMaceratorScreen extends HandledScreen<AdvancedMaceratorScreenHandler> {
    private static final Identifier TEXTURE = Identifiers.of("textures/gui/container/advanced_macerator.png");

    public AdvancedMaceratorScreen(@NotNull AdvancedMaceratorScreenHandler handler,
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
        drawTexture(matrices, x + 60, y + 37, 176, 0, 7, progress);
        progress = this.handler.getCookProgress(21);
        drawTexture(matrices, x + 83, y + 36, 176, 13, progress, 11);
        if(mouseX >= x + 60 && mouseX <= x + 60 + 7 &&
                mouseY >= y + 37 && mouseY <= y + 37 + 13) {
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