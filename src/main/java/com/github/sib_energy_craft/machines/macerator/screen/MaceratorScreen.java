package com.github.sib_energy_craft.machines.macerator.screen;

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
 * @since 0.0.1
 * @author sibmaks
 */
public class MaceratorScreen extends HandledScreen<MaceratorScreenHandler> {
    private static final Identifier TEXTURE = Identifiers.of("textures/gui/container/macerator.png");

    public MaceratorScreen(@NotNull MaceratorScreenHandler handler,
                           @NotNull PlayerInventory inventory,
                           @NotNull Text title) {
        super(handler, inventory, title);
    }

    @Override
    protected void drawBackground(@NotNull MatrixStack matrices, float delta, int mouseX, int mouseY) {
        RenderSystem.setShader(GameRenderer::getPositionTexProgram);
        RenderSystem.setShaderColor(1.0f, 1.0f, 1.0f, 1.0f);
        RenderSystem.setShaderTexture(0, TEXTURE);
        int i = this.x;
        int j = this.y;
        drawTexture(matrices, i, j, 0, 0, this.backgroundWidth, this.backgroundHeight);
        int chargeProgress = this.handler.getChargeProgress();
        drawTexture(matrices, i + 60, j + 37, 176, 0, 7, chargeProgress);
        int cookProgress = this.handler.getCookProgress(22);
        drawTexture(matrices, i + 80, j + 35, 176, 13, cookProgress, 15);
        if(mouseX >= i + 60 && mouseX <= i + 60 + 7 &&
                mouseY >= j + 37 && mouseY <= j + 37 + 13) {
            var charge = this.handler.getCharge();
            var maxCharge = this.handler.getMaxCharge();
            var charging = Text.translatable("energy.range.text", charge, maxCharge);
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