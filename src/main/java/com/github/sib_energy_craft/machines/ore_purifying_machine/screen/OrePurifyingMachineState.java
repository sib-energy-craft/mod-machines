package com.github.sib_energy_craft.machines.ore_purifying_machine.screen;

import com.github.sib_energy_craft.machines.cooking.screen.CookingEnergyMachineState;
import com.github.sib_energy_craft.machines.ore_purifying_machine.block.entity.OrePurifyingMachineProperties;
import lombok.Getter;

/**
 * @author sibmaks
 * @since 0.0.30
 */
@Getter
public class OrePurifyingMachineState extends CookingEnergyMachineState {
    @Getter
    protected int drumSpeed;
    @Getter
    protected int sourceCount;

    @Override
    public <V> void changeProperty(int index, V value) {
        super.changeProperty(index, value);
        if(index == OrePurifyingMachineProperties.DRUM_SPEED.getIndex()) {
            drumSpeed = (int) value;
        } else if(index == OrePurifyingMachineProperties.SOURCE_COUNT.getIndex()) {
            sourceCount = (int) value;
        }
    }
}
