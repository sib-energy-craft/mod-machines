package com.github.sib_energy_craft.machines.induction_furnace.block.entity;

import com.github.sib_energy_craft.machines.block.entity.property.EnergyMachineTypedProperty;
import com.github.sib_energy_craft.machines.cooking.block.entity.property.CookingEnergyMachineTypedProperties;
import com.github.sib_energy_craft.screen.property.ScreenPropertyType;
import com.github.sib_energy_craft.screen.property.ScreenPropertyTypes;

/**
 * @author sibmaks
 * @since 0.0.17
 */
public enum InductionFurnaceProperties implements EnergyMachineTypedProperty<Integer> {
    HEAT;

    private static final int OFFSET = CookingEnergyMachineTypedProperties.PROPERTIES_SIZE;
    public static final int PROPERTIES_SIZE = OFFSET + values().length;

    @Override
    public int getIndex() {
        return OFFSET + ordinal();
    }

    @Override
    public ScreenPropertyType<Integer> getPropertyType() {
        return ScreenPropertyTypes.INT;
    }
}
