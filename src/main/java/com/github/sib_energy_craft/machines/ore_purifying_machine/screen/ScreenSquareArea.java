package com.github.sib_energy_craft.machines.ore_purifying_machine.screen;

/**
 * @author sibmaks
 * @since 0.0.31
 */
public record ScreenSquareArea(int x, int y, int width, int height) {

    /**
     * Check is mouse in square
     *
     * @param xOffset x coordinate offset
     * @param yOffset y coordinate offset
     * @param mouseX mouse x coordinate
     * @param mouseY mouse y coordinate
     * @return true - mouse in area, false - otherwise
     */
    public boolean in(int xOffset, int yOffset, int mouseX, int mouseY) {
        return mouseX >= xOffset + x && mouseX <= xOffset + x + width &&
                mouseY >= yOffset + y && mouseY <= yOffset + y + height;
    }

}
