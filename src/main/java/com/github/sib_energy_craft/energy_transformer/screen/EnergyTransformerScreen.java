package com.github.sib_energy_craft.energy_transformer.screen;

import com.github.sib_energy_craft.energy_api.utils.Identifiers;
import com.github.sib_energy_craft.energy_transformer.block.entity.AbstractEnergyTransformerMode;
import com.github.sib_energy_craft.sec_utils.screen.ScreenSquareArea;
import com.mojang.blaze3d.systems.RenderSystem;
import lombok.Getter;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.ingame.ScreenHandlerProvider;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.NotNull;

import java.awt.*;

/**
 * @since 0.0.4
 * @author sibmaks
 */
public class EnergyTransformerScreen extends Screen
        implements ScreenHandlerProvider<EnergyTransformerScreenHandler> {

    public enum Button {
        MODE_UP, MODE_DOWN
    }

    private static final Identifier TEXTURE = Identifiers.of("textures/gui/container/energy_transformer.png");

    private static final ScreenSquareArea DOWN_BUTTON = new ScreenSquareArea(24, 24, 128, 20);
    private static final ScreenSquareArea UP_BUTTON = new ScreenSquareArea(24, 48, 128, 20);

    @Getter
    private final EnergyTransformerScreenHandler screenHandler;
    private int titleX;
    private final int titleY;
    private final int backgroundWidth;
    private final int backgroundHeight;
    private int x;
    private int y;

    public EnergyTransformerScreen(@NotNull EnergyTransformerScreenHandler screenHandler,
                                   @NotNull PlayerInventory inventory,
                                   @NotNull Text title) {
        super(title);
        this.screenHandler = screenHandler;
        this.titleX = 8;
        this.titleY = 6;
        this.backgroundWidth = 176;
        this.backgroundHeight = 88;
    }

    protected void drawBackground(@NotNull DrawContext drawContext, int mouseX, int mouseY) {
        RenderSystem.setShader(GameRenderer::getPositionTexProgram);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.setShaderTexture(0, TEXTURE);
        int x = this.x;
        int y = this.y;
        drawContext.drawTexture(TEXTURE, x, y, 0, 0, backgroundWidth, backgroundHeight);

        var mode = this.screenHandler.getMode();
        if (mode == AbstractEnergyTransformerMode.DOWN) {
            drawContext.drawTexture(TEXTURE, x + DOWN_BUTTON.x(), y + DOWN_BUTTON.y(), 24, 108, DOWN_BUTTON.width(), DOWN_BUTTON.height());
        }
        if (DOWN_BUTTON.in(x, y, mouseX, mouseY)) {
            drawContext.drawTexture(TEXTURE, x + DOWN_BUTTON.x(), y + DOWN_BUTTON.y(), 24, 128, DOWN_BUTTON.width(), DOWN_BUTTON.height());
        }

        if (mode == AbstractEnergyTransformerMode.UP) {
            drawContext.drawTexture(TEXTURE, x + UP_BUTTON.x(), y + UP_BUTTON.y(), 24, 108, UP_BUTTON.width(), UP_BUTTON.height());
        }
        if (UP_BUTTON.in(x, y, mouseX, mouseY)) {
            drawContext.drawTexture(TEXTURE, x + UP_BUTTON.x(), y + UP_BUTTON.y(), 24, 128, UP_BUTTON.width(), UP_BUTTON.height());
        }

        var modeDown = Text.translatable("attribute.name.sib_energy_craft.transform",
                this.screenHandler.getLowLevel(), this.screenHandler.getMaxLevel());
        int modeUpLeftOffset = (backgroundWidth - textRenderer.getWidth(modeDown)) / 2;
        drawContext.drawTextWithShadow(textRenderer, modeDown, x + modeUpLeftOffset, y + 30, Color.WHITE.getRGB());

        var modeUp = Text.translatable("attribute.name.sib_energy_craft.transform",
                this.screenHandler.getMaxLevel(), this.screenHandler.getLowLevel());
        int modeDownLeftOffset = (backgroundWidth - textRenderer.getWidth(modeUp)) / 2;
        drawContext.drawTextWithShadow(textRenderer, modeUp, x + modeDownLeftOffset, y + 54, Color.WHITE.getRGB());
    }

    @Override
    public void render(@NotNull DrawContext drawContext, int mouseX, int mouseY, float delta) {
        int l;
        int i = this.x;
        int j = this.y;
        this.drawBackground(drawContext, mouseX, mouseY);
        RenderSystem.disableDepthTest();
        super.render(drawContext, mouseX, mouseY, delta);
        MatrixStack matrices = drawContext.getMatrices();
        matrices.push();
        matrices.translate(i, j, 0.0f);
        drawForeground(drawContext);
        matrices.pop();
        RenderSystem.enableDepthTest();
    }

    protected void drawForeground(DrawContext drawContext) {
        drawContext.drawText(textRenderer, this.title, this.titleX, this.titleY, Color.DARK_GRAY.getRGB(), false);
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        if(super.mouseClicked(mouseX, mouseY, button)) {
            return true;
        }
        var mode = this.screenHandler.getMode();
        var client = this.client;
        if(client == null) {
            return false;
        }
        var interactionManager = client.interactionManager;
        if(interactionManager == null) {
            return false;
        }

        if (mode != AbstractEnergyTransformerMode.UP && UP_BUTTON.in(x, y, mouseX, mouseY)) {
            interactionManager.clickButton(this.screenHandler.syncId, Button.MODE_UP.ordinal());
            return true;
        } else if (mode != AbstractEnergyTransformerMode.DOWN && DOWN_BUTTON.in(x, y, mouseX, mouseY)) {
            interactionManager.clickButton(this.screenHandler.syncId, Button.MODE_DOWN.ordinal());
            return true;
        }
        return false;
    }

    @Override
    protected void init() {
        this.x = (this.width - this.backgroundWidth) / 2;
        this.y = (this.height - this.backgroundHeight) / 2;
        titleX = (backgroundWidth - textRenderer.getWidth(title)) / 2;
    }

    @Override
    public boolean shouldPause() {
        return false;
    }

    @Override
    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        if (super.keyPressed(keyCode, scanCode, modifiers)) {
            return true;
        } else if (client != null && client.options.inventoryKey.matchesKey(keyCode, scanCode)) {
            close();
            return true;
        } else {
            return true;
        }
    }
}