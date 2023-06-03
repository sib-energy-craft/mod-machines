package com.github.sib_energy_craft.machines.ore_purifying_machine.screen;

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

/**
 * @since 0.0.26
 * @author sibmaks
 */
public class OrePurifyingMachineScreen extends HandledScreen<OrePurifyingMachineScreenHandler> {
    private static final Identifier TEXTURE = Identifiers.of("textures/gui/container/ore_purifying_machine.png");

    private static final ScreenSquareArea CHARGE = new ScreenSquareArea(60, 46, 7, 13);
    private static final ScreenSquareArea DRUM = new ScreenSquareArea(84, 36, 29, 29);

    private double deltaSum = 0;
    private int drumIcon = 0;

    public OrePurifyingMachineScreen(@NotNull OrePurifyingMachineScreenHandler handler,
                                     @NotNull PlayerInventory inventory,
                                     @NotNull Text title) {
        super(handler, inventory, title);
        this.backgroundWidth = 176;
        this.backgroundHeight = 190;
        this.playerInventoryTitleY = this.backgroundHeight - 94;
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
        drawTexture(matrices, x, y, 0, 0, backgroundWidth, backgroundHeight);
        int progress = handler.getChargeProgress();
        drawTexture(matrices, x + CHARGE.x(), y + CHARGE.y(), 176, 0, CHARGE.width(), progress);

        var state = handler.getEnergyMachineState();
        int drumSpeed = state.getDrumSpeed();
        if(drumSpeed > 0) {
            var slower = 1;
            int maxDrumSpeed = handler.getMaxDrumSpeed();
            var drumSpeedProgress = 1d * drumSpeed / maxDrumSpeed;
            deltaSum += delta * drumSpeedProgress;
            if (deltaSum >= 8 * slower) {
                deltaSum = 0;
            }
            drumIcon = (int) (deltaSum / slower);
        }
        int sourceCount = state.getSourceCount();
        if(sourceCount == 0) {
            drawTexture(matrices, x + 84, y + 36, 183, 29 * drumIcon, 29, 29);
        } else {
            drawTexture(matrices, x + 84, y + 36, 212, 29 * drumIcon, 29, 29);
        }

        if(CHARGE.in(x, y, mouseX, mouseY)) {
            var charge = handler.getCharge();
            var maxCharge = handler.getMaxCharge();
            var charging = Text.translatable("energy.range.text", charge, maxCharge);
            renderTooltip(matrices, charging, mouseX, mouseY);
        } else if(DRUM.in(x, y, mouseX, mouseY)) {
            var speedText = Text.translatable("ore_purifying_machine.speed.text", drumSpeed);
            renderTooltip(matrices, speedText, mouseX, mouseY);
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