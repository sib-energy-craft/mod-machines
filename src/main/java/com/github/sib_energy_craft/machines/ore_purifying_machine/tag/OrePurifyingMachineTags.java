package com.github.sib_energy_craft.machines.ore_purifying_machine.tag;

import com.github.sib_energy_craft.energy_api.utils.Identifiers;
import com.github.sib_energy_craft.sec_utils.utils.TagUtils;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;
import org.jetbrains.annotations.NotNull;

/**
 * @since 0.0.26
 * @author sibmaks
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class OrePurifyingMachineTags {
    public static final TagKey<Item> USED_IN_ORE_PURIFYING_MACHINE;

    static {
        USED_IN_ORE_PURIFYING_MACHINE = TagKey.of(RegistryKeys.ITEM, Identifiers.of("used_in_ore_purifying_machine"));
    }

    /**
     * Method check can be item stack used in ore purifying machine.
     *
     * @param itemStack item stack
     * @return true - can be used, false - otherwise
     */
    public static boolean isUsedInOrePurifyingMachine(@NotNull ItemStack itemStack) {
        return TagUtils.hasTag(USED_IN_ORE_PURIFYING_MACHINE, itemStack);
    }

}
