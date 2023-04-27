package com.github.sib_energy_craft.machines.extractor.tag;

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
public final class ExtractorTags {
    public static final TagKey<Item> USED_IN_EXTRACTOR;

    static {
        USED_IN_EXTRACTOR = TagKey.of(RegistryKeys.ITEM, Identifiers.of("used_in_extractor"));
    }

    /**
     * Method check can be item stack used in extractor.
     *
     * @param itemStack item stack
     * @return true - can be used, false - otherwise
     */
    public static boolean isUsedInExtractor(@NotNull ItemStack itemStack) {
        return itemStack.streamTags().anyMatch(it -> it.equals(ExtractorTags.USED_IN_EXTRACTOR));
    }

}
