package com.github.sib_energy_craft.energy_transformer.screen;

import com.github.sib_energy_craft.energy_api.utils.Identifiers;
import com.github.sib_energy_craft.energy_transformer.block.entity.AbstractEnergyTransformerMode;
import com.mojang.blaze3d.systems.RenderSystem;
import lombok.Getter;
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

    protected void drawBackground(@NotNull MatrixStack matrices, int mouseX, int mouseY) {
        RenderSystem.setShader(GameRenderer::getPositionTexProgram);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.setShaderTexture(0, TEXTURE);
        drawTexture(matrices, 0, 0, 0, 0, backgroundWidth, backgroundHeight);

        var mode = this.screenHandler.getMode();
        if (mode == AbstractEnergyTransformerMode.DOWN) {
            drawTexture(matrices, 24, 24, 24, 108, 128, 20);
        }
        if (isOnModeDownButton(mouseX, mouseY)) {
            drawTexture(matrices, 24, 24, 24, 128, 128, 20);
        }

        if (mode == AbstractEnergyTransformerMode.UP) {
            drawTexture(matrices, 24, 48, 24, 108, 128, 20);
        }
        if (isOnModeUpButton(mouseX, mouseY)) {
            drawTexture(matrices, 24, 48, 24, 128, 128, 20);
        }

        var modeDown = Text.translatable("attribute.name.sib_energy_craft.transform",
                this.screenHandler.getLowLevel(), this.screenHandler.getMaxLevel());
        int modeUpLeftOffset = (backgroundWidth - textRenderer.getWidth(modeDown)) / 2;
        this.textRenderer.drawWithShadow(matrices, modeDown, modeUpLeftOffset, 30, Color.WHITE.getRGB());

        var modeUp = Text.translatable("attribute.name.sib_energy_craft.transform",
                this.screenHandler.getMaxLevel(), this.screenHandler.getLowLevel());
        int modeDownLeftOffset = (backgroundWidth - textRenderer.getWidth(modeUp)) / 2;
        this.textRenderer.drawWithShadow(matrices, modeUp, modeDownLeftOffset, 54, Color.WHITE.getRGB());
    }

    @Override
    public void render(@NotNull MatrixStack matrices, int mouseX, int mouseY, float delta) {
        super.renderBackground(matrices);
        var matrixStack = RenderSystem.getModelViewStack();
        matrixStack.push();
        matrixStack.translate(x, y, 0.0);
        RenderSystem.applyModelViewMatrix();
        drawBackground(matrices, mouseX, mouseY);
        this.textRenderer.draw(matrices, this.title, this.titleX, this.titleY, 0x404040);
        matrixStack.pop();
        RenderSystem.applyModelViewMatrix();
        RenderSystem.enableDepthTest();
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

        if (mode != AbstractEnergyTransformerMode.UP && isOnModeUpButton(mouseX, mouseY)) {
            interactionManager.clickButton(this.screenHandler.syncId, Button.MODE_UP.ordinal());
            return true;
        } else if (mode != AbstractEnergyTransformerMode.DOWN && isOnModeDownButton(mouseX, mouseY)) {
            interactionManager.clickButton(this.screenHandler.syncId, Button.MODE_DOWN.ordinal());
            return true;
        }
        return false;
    }

    private boolean isOnModeDownButton(double mouseX, double mouseY) {
        return mouseX >= x + 24 && mouseX <= x + 24 + 128 && mouseY >= y + 24 && mouseY <= y + 24 + 20;
    }

    private boolean isOnModeUpButton(double mouseX, double mouseY) {
        return mouseX >= x + 24 && mouseX <= x + 24 + 128 && mouseY >= y + 48 && mouseY <= y + 48 + 20;
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