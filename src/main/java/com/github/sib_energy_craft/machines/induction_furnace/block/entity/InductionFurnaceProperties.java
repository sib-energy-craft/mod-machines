package com.github.sib_energy_craft.machines.induction_furnace.block.entity;

import com.github.sib_energy_craft.machines.block.entity.EnergyMachineProperties;
import com.github.sib_energy_craft.machines.block.entity.EnergyMachineProperty;

/**
 * @author sibmaks
 * @since 0.0.17
 */
public enum InductionFurnaceProperties implements EnergyMachineProperty {
    HEAT;

    private static final int OFFSET = EnergyMachineProperties.values().length;

    @Override
    public int getIndex() {
        return OFFSET + ordinal();
    }
}
