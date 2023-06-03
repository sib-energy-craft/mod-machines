package com.github.sib_energy_craft.machines.induction_furnace.screen;

import com.github.sib_energy_craft.machines.cooking.screen.CookingEnergyMachineState;
import com.github.sib_energy_craft.machines.induction_furnace.block.entity.InductionFurnaceProperties;
import lombok.Getter;

/**
 * @author sibmaks
 * @since 0.0.30
 */
@Getter
public class InductionFurnaceState extends CookingEnergyMachineState {
    protected int heatPercent;

    @Override
    public <V> void changeProperty(int index, V value) {
        super.changeProperty(index, value);
        if(index == InductionFurnaceProperties.HEAT.getIndex()) {
            heatPercent = (int) value;
        }
    }
}
