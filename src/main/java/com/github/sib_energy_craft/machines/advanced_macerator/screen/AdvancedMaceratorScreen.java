package com.github.sib_energy_craft.machines.advanced_macerator.screen;

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

/**
 * @since 0.0.17
 * @author sibmaks
 */
public class AdvancedMaceratorScreen extends HandledScreen<AdvancedMaceratorScreenHandler> {
    private static final Identifier TEXTURE = Identifiers.of("textures/gui/container/advanced_macerator.png");

    private static final ScreenSquareArea CHARGE = new ScreenSquareArea(60, 37, 7, 13);

    public AdvancedMaceratorScreen(@NotNull AdvancedMaceratorScreenHandler handler,
                               @NotNull PlayerInventory inventory,
                               @NotNull Text title) {
        super(handler, inventory, title);
    }

    @Override
    protected void drawBackground(@NotNull DrawContext drawContext,
                                  float delta,
                                  int mouseX,
                                  int mouseY) {
        RenderSystem.setShader(GameRenderer::getPositionTexProgram);
        RenderSystem.setShaderColor(1.0f, 1.0f, 1.0f, 1.0f);
        RenderSystem.setShaderTexture(0, TEXTURE);
        int x = this.x;
        int y = this.y;
        drawContext.drawTexture(TEXTURE, x, y, 0, 0, this.backgroundWidth, this.backgroundHeight);
        int progress = this.handler.getChargeProgress();
        drawContext.drawTexture(TEXTURE, x + CHARGE.x(), y + CHARGE.y(), 176, 0, CHARGE.width(), progress);
        progress = this.handler.getCookProgress(21);
        drawContext.drawTexture(TEXTURE, x + 83, y + 36, 176, 13, progress, 11);
        if(CHARGE.in(x, y, mouseX, mouseY)) {
            var charge = this.handler.getCharge();
            var maxCharge = this.handler.getMaxCharge();
            var charging = Text.translatable("energy.range.text", charge, maxCharge);
            drawContext.drawTooltip(textRenderer, charging, mouseX, mouseY);
        }
    }

    @Override
    public void render(@NotNull DrawContext drawContext,
                       int mouseX,
                       int mouseY,
                       float delta) {
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