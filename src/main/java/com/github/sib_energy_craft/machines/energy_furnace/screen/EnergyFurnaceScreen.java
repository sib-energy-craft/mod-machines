package com.github.sib_energy_craft.machines.energy_furnace.screen;

import com.github.sib_energy_craft.energy_api.utils.Identifiers;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.gui.screen.recipebook.AbstractFurnaceRecipeBookScreen;
import net.minecraft.client.gui.screen.recipebook.FurnaceRecipeBookScreen;
import net.minecraft.client.gui.screen.recipebook.RecipeBookProvider;
import net.minecraft.client.gui.screen.recipebook.RecipeBookWidget;
import net.minecraft.client.gui.widget.TexturedButtonWidget;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.screen.slot.Slot;
import net.minecraft.screen.slot.SlotActionType;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.NotNull;

/**
 * @since 0.0.2
 * @author sibmaks
 */
public class EnergyFurnaceScreen extends HandledScreen<EnergyFurnaceScreenHandler> implements RecipeBookProvider {
    private static final Identifier TEXTURE = Identifiers.of("textures/gui/container/energy_furnace.png");
    private static final Identifier RECIPE_BUTTON_TEXTURE = new Identifier("textures/gui/recipe_button.png");

    public final AbstractFurnaceRecipeBookScreen recipeBook;
    private boolean narrow;

    public EnergyFurnaceScreen(@NotNull EnergyFurnaceScreenHandler handler,
                               @NotNull PlayerInventory inventory,
                               @NotNull Text title) {
        super(handler, inventory, title);
        this.recipeBook = new FurnaceRecipeBookScreen();
    }

    @Override
    public void init() {
        super.init();
        this.narrow = this.width < 379;
        this.recipeBook.initialize(this.width, this.height, this.client, this.narrow, this.handler);
        this.x = this.recipeBook.findLeftEdge(this.width, this.backgroundWidth);
        this.addDrawableChild(new TexturedButtonWidget(this.x + 20, this.height / 2 - 49, 20, 18, 0, 0, 19, RECIPE_BUTTON_TEXTURE, button -> {
            this.recipeBook.toggleOpen();
            this.x = this.recipeBook.findLeftEdge(this.width, this.backgroundWidth);
            button.setPosition(this.x + 20, this.height / 2 - 49);
        }));
        this.titleX = (this.backgroundWidth - this.textRenderer.getWidth(this.title)) / 2;
    }

    @Override
    protected void drawBackground(@NotNull MatrixStack matrices, float delta, int mouseX, int mouseY) {
        RenderSystem.setShader(GameRenderer::getPositionTexProgram);
        RenderSystem.setShaderColor(1.0f, 1.0f, 1.0f, 1.0f);
        RenderSystem.setShaderTexture(0, TEXTURE);
        int i = this.x;
        int j = this.y;
        drawTexture(matrices, i, j, 0, 0, this.backgroundWidth, this.backgroundHeight);
        int progress = this.handler.getChargeProgress();
        drawTexture(matrices, i + 60, j + 37, 176, 0, 7, progress);
        progress = this.handler.getCookProgress();
        drawTexture(matrices, i + 80, j + 35, 176, 13, progress, 16);
        if(mouseX >= i + 60 && mouseX <= i + 60 + 7 && mouseY >= j + 37 && mouseY <= j + 37 + 13) {
            int charge = this.handler.getCharge();
            int maxCharge = this.handler.getMaxCharge();
            var charging = Text.translatable("energy.range.text", charge, maxCharge);
            this.renderTooltip(matrices, charging, mouseX, mouseY);
        }
    }

    @Override
    public void render(@NotNull MatrixStack matrices, int mouseX, int mouseY, float delta) {
        this.renderBackground(matrices);
        if (this.recipeBook.isOpen() && this.narrow) {
            this.drawBackground(matrices, delta, mouseX, mouseY);
            this.recipeBook.render(matrices, mouseX, mouseY, delta);
        } else {
            this.recipeBook.render(matrices, mouseX, mouseY, delta);
            super.render(matrices, mouseX, mouseY, delta);
            this.recipeBook.drawGhostSlots(matrices, this.x, this.y, true, delta);
        }
        this.drawMouseoverTooltip(matrices, mouseX, mouseY);
        this.recipeBook.drawTooltip(matrices, this.x, this.y, mouseX, mouseY);
    }

    @Override
    public void handledScreenTick() {
        super.handledScreenTick();
        this.recipeBook.update();
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        if (this.recipeBook.mouseClicked(mouseX, mouseY, button)) {
            return true;
        }
        if (this.narrow && this.recipeBook.isOpen()) {
            return true;
        }
        return super.mouseClicked(mouseX, mouseY, button);
    }

    @Override
    protected void onMouseClick(@NotNull Slot slot,
                                int slotId,
                                int button,
                                @NotNull SlotActionType actionType) {
        super.onMouseClick(slot, slotId, button, actionType);
        this.recipeBook.slotClicked(slot);
    }

    @Override
    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        if (this.recipeBook.keyPressed(keyCode, scanCode, modifiers)) {
            return false;
        }
        return super.keyPressed(keyCode, scanCode, modifiers);
    }

    @Override
    protected boolean isClickOutsideBounds(double mouseX, double mouseY, int left, int top, int button) {
        boolean outside = mouseX < left || mouseY < top || mouseX >= (left + this.backgroundWidth) ||
                mouseY >= (top + this.backgroundHeight);
        return this.recipeBook.isClickOutsideBounds(mouseX, mouseY, this.x, this.y, this.backgroundWidth,
                this.backgroundHeight, button) && outside;
    }

    @Override
    public boolean charTyped(char chr, int modifiers) {
        if (this.recipeBook.charTyped(chr, modifiers)) {
            return true;
        }
        return super.charTyped(chr, modifiers);
    }

    @Override
    public void refreshRecipeBook() {
        this.recipeBook.refresh();
    }

    @NotNull
    @Override
    public RecipeBookWidget getRecipeBookWidget() {
        return this.recipeBook;
    }

    @Override
    public void removed() {
        if(this.recipeBook.isOpen()) {
            this.recipeBook.toggleOpen();
        }
        super.removed();
    }
}