package com.github.sib_energy_craft.machines.macerator.tag;

import com.github.sib_energy_craft.energy_api.utils.Identifiers;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;
import org.jetbrains.annotations.NotNull;

/**
 * @since 0.0.1
 * @author sibmaks
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class MaceratorTags {
    public static final TagKey<Item> USED_IN_MACERATOR;

    static {
        USED_IN_MACERATOR = TagKey.of(RegistryKeys.ITEM, Identifiers.of("used_in_macerator"));
    }

    /**
     * Method check can be item stack used in macerator.
     *
     * @param itemStack item stack
     * @return true - can be used, false - otherwise
     */
    public static boolean isUsedInMacerator(@NotNull ItemStack itemStack) {
        return itemStack.streamTags().anyMatch(it -> it.equals(MaceratorTags.USED_IN_MACERATOR));
    }

}
