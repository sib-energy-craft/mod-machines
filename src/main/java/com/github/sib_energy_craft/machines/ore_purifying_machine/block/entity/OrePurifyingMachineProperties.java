package com.github.sib_energy_craft.machines.ore_purifying_machine.block.entity;

import com.github.sib_energy_craft.machines.block.entity.property.EnergyMachineTypedProperties;
import com.github.sib_energy_craft.machines.block.entity.property.EnergyMachineTypedProperty;
import com.github.sib_energy_craft.screen.property.ScreenPropertyType;
import com.github.sib_energy_craft.screen.property.ScreenPropertyTypes;

/**
 * @author sibmaks
 * @since 0.0.26
 */
public enum OrePurifyingMachineProperties implements EnergyMachineTypedProperty<Integer> {
    DRUM_SPEED,
    SOURCE_COUNT;

    private static final int OFFSET = EnergyMachineTypedProperties.values().length;

    @Override
    public int getIndex() {
        return OFFSET + ordinal();
    }

    @Override
    public ScreenPropertyType<Integer> getPropertyType() {
        return ScreenPropertyTypes.INT;
    }
}
