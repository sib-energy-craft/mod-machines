package com.github.sib_energy_craft.machines.bio_reactor.screen;

import com.github.sib_energy_craft.energy_api.screen.ChargeSlot;
import com.github.sib_energy_craft.energy_api.tags.CoreTags;
import com.github.sib_energy_craft.machines.bio_reactor.BioFuelRegistry;
import com.github.sib_energy_craft.machines.bio_reactor.block.entity.AbstractBioReactorBlockEntity;
import com.github.sib_energy_craft.machines.bio_reactor.block.entity.BioReactorTypedProperties;
import com.github.sib_energy_craft.machines.screen.layout.SlotLayoutManager;
import com.github.sib_energy_craft.screen.TypedPropertyScreenHandler;
import com.github.sib_energy_craft.sec_utils.screen.SlotsScreenHandler;
import com.github.sib_energy_craft.sec_utils.screen.slot.SlotGroupMetaBuilder;
import com.github.sib_energy_craft.sec_utils.screen.slot.SlotGroupsMeta;
import com.github.sib_energy_craft.sec_utils.screen.slot.SlotGroupsMetaBuilder;
import com.github.sib_energy_craft.sec_utils.screen.slot.SlotTypes;
import lombok.Getter;
import lombok.Setter;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.screen.slot.Slot;
import net.minecraft.world.World;
import org.jetbrains.annotations.NotNull;

/**
 * @since 0.0.23
 * @author sibmaks
 */
public abstract class AbstractBioReactorScreenHandler extends SlotsScreenHandler implements TypedPropertyScreenHandler {
    private final Inventory inventory;
    protected final World world;
    protected final SlotGroupsMeta slotGroupsMeta;
    @Getter
    protected int charge;
    @Getter
    protected int fermentationTime;
    @Getter
    protected int fermentationTimeTotal;
    @Getter
    protected int ferments;
    @Getter
    protected final int maxCharge;
    @Getter
    protected final int maxFerments;
    @Getter
    protected final int energyPacketSize;
    @Setter
    protected Runnable syncer;

    protected AbstractBioReactorScreenHandler(@NotNull ScreenHandlerType<?> type,
                                              int syncId,
                                              @NotNull PlayerInventory playerInventory,
                                              int maxCharge,
                                              int maxFerments,
                                              int energyPacketSize,
                                              @NotNull SlotLayoutManager slotLayoutManager) {
        this(type, syncId, playerInventory, new SimpleInventory(2), maxCharge, maxFerments, energyPacketSize,
                slotLayoutManager);
    }

    protected AbstractBioReactorScreenHandler(@NotNull ScreenHandlerType<?> type,
                                              int syncId,
                                              @NotNull PlayerInventory playerInventory,
                                              @NotNull Inventory inventory,
                                              int maxCharge,
                                              int maxFerments,
                                              int energyPacketSize,
                                              @NotNull SlotLayoutManager slotLayoutManager) {
        super(type, syncId);
        AbstractBioReactorScreenHandler.checkSize(inventory, 2);
        this.inventory = inventory;
        this.maxCharge = maxCharge;
        this.maxFerments = maxFerments;
        this.energyPacketSize = energyPacketSize;
        this.world = playerInventory.player.getWorld();
        this.slotGroupsMeta = buildSlots(slotLayoutManager, playerInventory, inventory);
    }

    private @NotNull SlotGroupsMeta buildSlots(@NotNull SlotLayoutManager slotLayoutManager,
                                               @NotNull PlayerInventory playerInventory,
                                               @NotNull Inventory inventory) {
        int globalSlotIndex = 0;
        var slotGroupsBuilder = SlotGroupsMetaBuilder.builder();

        int quickAccessSlots = 9;
        {
            var slotQuickAccessGroupBuilder = SlotGroupMetaBuilder.builder(SlotTypes.QUICK_ACCESS);
            for (int i = 0; i < quickAccessSlots; ++i) {
                slotQuickAccessGroupBuilder.addSlot(globalSlotIndex++, i);
                var pos = slotLayoutManager.getSlotPosition(SlotTypes.QUICK_ACCESS, i, i);
                var slot = new Slot(playerInventory, i, pos.x, pos.y);
                this.addSlot(slot);
            }
            var quickAccessSlotGroup = slotQuickAccessGroupBuilder.build();
            slotGroupsBuilder.add(quickAccessSlotGroup);
        }

        {
            var slotPlayerGroupBuilder = SlotGroupMetaBuilder.builder(SlotTypes.PLAYER_INVENTORY);
            for (int i = 0; i < 3; ++i) {
                for (int j = 0; j < 9; ++j) {
                    int index = j + i * 9 + quickAccessSlots;
                    slotPlayerGroupBuilder.addSlot(globalSlotIndex++, index);
                    var pos = slotLayoutManager.getSlotPosition(SlotTypes.PLAYER_INVENTORY, j + i * 9, index);
                    var slot = new Slot(playerInventory, index, pos.x, pos.y);
                    this.addSlot(slot);
                }
            }
            var playerSlotGroup = slotPlayerGroupBuilder.build();
            slotGroupsBuilder.add(playerSlotGroup);
        }

        {
            var slotGroupBuilder = SlotGroupMetaBuilder.builder(BioReactorSlotTypes.CHARGE);
            slotGroupBuilder.addSlot(globalSlotIndex++, AbstractBioReactorBlockEntity.CHARGE_SLOT_INDEX);
            var pos = slotLayoutManager.getSlotPosition(BioReactorSlotTypes.CHARGE, 0, AbstractBioReactorBlockEntity.CHARGE_SLOT_INDEX);
            var chargeSlot = new ChargeSlot(inventory, AbstractBioReactorBlockEntity.CHARGE_SLOT_INDEX, pos.x, pos.y, true);
            this.addSlot(chargeSlot);
            var slotGroup = slotGroupBuilder.build();
            slotGroupsBuilder.add(slotGroup);
        }

        {
            var slotGroupBuilder = SlotGroupMetaBuilder.builder(BioReactorSlotTypes.FUEL);
            slotGroupBuilder.addSlot(globalSlotIndex, AbstractBioReactorBlockEntity.FUEL_SLOT_INDEX);
            var pos = slotLayoutManager.getSlotPosition(BioReactorSlotTypes.FUEL, 0, AbstractBioReactorBlockEntity.FUEL_SLOT_INDEX);
            var slot = new Slot(inventory, AbstractBioReactorBlockEntity.FUEL_SLOT_INDEX, pos.x, pos.y);
            this.addSlot(slot);
            var slotGroup = slotGroupBuilder.build();
            slotGroupsBuilder.add(slotGroup);
        }

        return slotGroupsBuilder.build();
    }

    @Override
    public boolean canUse(@NotNull PlayerEntity player) {
        return this.inventory.canPlayerUse(player);
    }

    @NotNull
    @Override
    public ItemStack quickMove(@NotNull PlayerEntity player, int index) {
        var itemStack = ItemStack.EMPTY;
        var slot = this.slots.get(index);
        if (slot.hasStack()) {
            var slotStack = slot.getStack();
            itemStack = slotStack.copy();

            var slotMeta = this.slotGroupsMeta.getByGlobalSlotIndex(index);
            if(slotMeta != null) {
                var slotType = slotMeta.getSlotType();
                if (slotType == BioReactorSlotTypes.FUEL || slotType == BioReactorSlotTypes.CHARGE) {
                    if (!insertItem(slotGroupsMeta, slotStack, SlotTypes.PLAYER_INVENTORY, SlotTypes.QUICK_ACCESS)) {
                        return ItemStack.EMPTY;
                    }
                } else {
                    if(isFuel(slotStack)) {
                        if (!insertItem(slotGroupsMeta, slotStack, BioReactorSlotTypes.FUEL)) {
                            return ItemStack.EMPTY;
                        }
                    }
                    if(CoreTags.isChargeable(slotStack)) {
                        if (!insertItem(slotGroupsMeta, slotStack, BioReactorSlotTypes.CHARGE)) {
                            return ItemStack.EMPTY;
                        }
                    }
                }
                if (slotType == SlotTypes.QUICK_ACCESS) {
                    if (!insertItem(slotGroupsMeta, slotStack, SlotTypes.PLAYER_INVENTORY)) {
                        return ItemStack.EMPTY;
                    }
                } else if (slotType == SlotTypes.PLAYER_INVENTORY) {
                    if (!insertItem(slotGroupsMeta, slotStack, SlotTypes.QUICK_ACCESS)) {
                        return ItemStack.EMPTY;
                    }
                }
            }
            slot.onQuickTransfer(slotStack, itemStack);

            if (slotStack.isEmpty()) {
                slot.setStack(ItemStack.EMPTY);
            } else {
                slot.markDirty();
            }
            if (slotStack.getCount() == itemStack.getCount()) {
                return ItemStack.EMPTY;
            }
            slot.onTakeItem(player, slotStack);
        }
        return itemStack;
    }

    /**
     * Check is item can be used as fuel
     *
     * @param itemStack item stack
     * @return true - item is fuel, false - otherwise
     */
    public static boolean isFuel(@NotNull ItemStack itemStack) {
        return BioFuelRegistry.isFuel(itemStack);
    }

    /**
     * Get charge progress status
     *
     * @return charge progress
     */
    public int getChargeProgress(int height) {
        int charge = getCharge();
        int maxCharge = getMaxCharge();
        if (maxCharge == 0 || charge == 0) {
            return 0;
        }
        return charge * height / maxCharge;
    }

    /**
     * Get fermentation progress status
     *
     * @return fermentation progress
     */
    public int getFermentationProgress(int height) {
        int i = fermentationTime;
        int j = fermentationTimeTotal;
        if (j == 0 || i == 0) {
            return 0;
        }
        return i * height / j;
    }

    /**
     * Get fermentation tank filling progress
     *
     * @return fermentation progress
     */
    public int getFermentsFilling(int height) {
        int i = ferments;
        int j = maxFerments;
        if (j == 0 || i == 0) {
            return 0;
        }
        return i * height / j;
    }

    /**
     * Get extractor total fermentation time
     *
     * @return total fermentation time
     */
    public boolean isFermenting() {
        return fermentationTime > 0;
    }

    @Override
    public <V> void onTypedPropertyChanged(int index, V value) {
        if(index == BioReactorTypedProperties.CHARGE.ordinal()) {
            charge = (int) value;
        } else if(index == BioReactorTypedProperties.FERMENTATION_TIME.ordinal()) {
            fermentationTime = (int) value;
        } else if(index == BioReactorTypedProperties.FERMENTATION_TIME_TOTAL.ordinal()) {
            fermentationTimeTotal = (int) value;
        } else if(index == BioReactorTypedProperties.FERMENTS.ordinal()) {
            ferments = (int) value;
        }
    }

    @Override
    public final void sendContentUpdates() {
        super.sendContentUpdates();
        var syncer = this.syncer;
        if(syncer != null) {
            syncer.run();
        }
    }
}

