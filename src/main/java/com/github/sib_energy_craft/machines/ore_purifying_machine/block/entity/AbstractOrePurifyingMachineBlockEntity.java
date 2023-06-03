package com.github.sib_energy_craft.machines.ore_purifying_machine.block.entity;

import com.github.sib_energy_craft.energy_api.Energy;
import com.github.sib_energy_craft.machines.block.entity.EnergyMachineEvent;
import com.github.sib_energy_craft.machines.block.entity.EnergyMachineInventoryType;
import com.github.sib_energy_craft.machines.cooking.block.entity.CookingEnergyMachineBlockEntity;
import com.github.sib_energy_craft.machines.ore_purifying_machine.block.AbstractOrePurifyingMachineBlock;
import com.github.sib_energy_craft.machines.ore_purifying_machine.tag.OrePurifyingMachineTags;
import com.github.sib_energy_craft.recipes.recipe.PurifyingRecipe;
import com.github.sib_energy_craft.recipes.recipe.PurifyingRecipeType;
import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerFactory;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.recipe.Recipe;
import net.minecraft.recipe.RecipeType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;


/**
 * @since 0.0.26
 * @author sibmaks
 */
public abstract class AbstractOrePurifyingMachineBlockEntity<T extends AbstractOrePurifyingMachineBlock>
        extends CookingEnergyMachineBlockEntity<T>
        implements ExtendedScreenHandlerFactory {
    protected final RecipeType<PurifyingRecipe> recipeType;

    protected int drumSpeed;

    public AbstractOrePurifyingMachineBlockEntity(@NotNull BlockEntityType<?> blockEntityType,
                                                  @NotNull BlockPos pos,
                                                  @NotNull BlockState state,
                                                  @NotNull T block,
                                                  int parallelProcess) {
        super(blockEntityType, pos, state, block, parallelProcess, 3 * parallelProcess, parallelProcess);
        this.recipeType = PurifyingRecipeType.INSTANCE;
        this.addListener(EnergyMachineEvent.ENERGY_USED, () -> drumSpeed = Math.min(drumSpeed + 1, block.getMaxDrumSpeed()));
        this.addListener(EnergyMachineEvent.CAN_NOT_PROCESS, () -> drumSpeed = Math.max(drumSpeed - 1, 0));
        this.addListener(EnergyMachineEvent.ENERGY_NOT_ENOUGH, () -> drumSpeed = Math.max(drumSpeed - 1, 0));
        this.energyMachinePropertyMap.add(OrePurifyingMachineProperties.DRUM_SPEED, () -> drumSpeed);
        this.energyMachinePropertyMap.add(OrePurifyingMachineProperties.SOURCE_COUNT, this::getSourceAmount);
    }

    @Override
    public int getCookTimeInc(@NotNull World world) {
        if(drumSpeed < block.getMinimalWorkingDrumSpeed()) {
            return 0;
        }
        float fullDrumSpeedCookBoost = block.getFullDrumSpeedCookBoost();
        float max = block.getMaxDrumSpeed();
        return (int) Math.max(1, fullDrumSpeedCookBoost * (drumSpeed / max));
    }

    @Override
    public void readNbt(@NotNull NbtCompound nbt) {
        super.readNbt(nbt);
        this.drumSpeed = nbt.getInt("DrumSpeed");
    }

    @Override
    protected void writeNbt(@NotNull NbtCompound nbt) {
        super.writeNbt(nbt);
        nbt.putInt("DrumSpeed", drumSpeed);
    }

    @Override
    public @Nullable Recipe<Inventory> getRecipe(@NotNull World world, int process) {
        return getRecipe(recipeType, world, process);
    }

    @Override
    public int getCookTimeTotal(@NotNull World world) {return world.getRecipeManager()
            .getFirstMatch(recipeType, inventory, world)
            .map(PurifyingRecipe::getCookingTime)
            .orElse(200);
    }

    @Override
    protected boolean canAcceptRecipeOutput(int process,
                                            @NotNull World world,
                                            @NotNull Recipe<Inventory> recipe,
                                            int count) {
        if(!(recipe instanceof PurifyingRecipe purifyingRecipe)) {
            return false;
        }
        var sourceStack = inventory.getStack(EnergyMachineInventoryType.SOURCE, process);
        if (sourceStack.isEmpty()) {
            return false;
        }
        if(!canAcceptOutputMainStack(process, world, purifyingRecipe, count)) {
            return false;
        }
        if(!canAcceptOutputSideStack(process, purifyingRecipe, count)) {
            return false;
        }
        return canAcceptOutputTrashStack(process, purifyingRecipe, count);
    }

    @Override
    public boolean isValid(int slot, @NotNull ItemStack stack) {
        var slotType = inventory.getType(slot);
        if(slotType == EnergyMachineInventoryType.SOURCE) {
            return OrePurifyingMachineTags.isUsedInOrePurifyingMachine(stack);
        }
        return super.isValid(slot, stack);
    }

    private boolean canAcceptOutputMainStack(int process,
                                             @NotNull World world,
                                             @NotNull PurifyingRecipe recipe,
                                             int count) {
        var outputStack = recipe.getOutput(world.getRegistryManager());
        if (outputStack.isEmpty()) {
            return false;
        }
        var outputSlotStack = inventory.getStack(EnergyMachineInventoryType.OUTPUT, process * 3);
        return isOutputStackFit(count, outputStack, outputSlotStack);
    }

    private boolean canAcceptOutputSideStack(int process,
                                             @NotNull PurifyingRecipe recipe,
                                             int count) {
        var outputSideStack = recipe.getOutputSide();
        if (outputSideStack.isEmpty()) {
            return false;
        }
        var outputSideSlotStack = inventory.getStack(EnergyMachineInventoryType.OUTPUT, process * 3 + 1);
        return isOutputStackFit(count, outputSideStack, outputSideSlotStack);
    }

    private boolean canAcceptOutputTrashStack(int process, 
                                              @NotNull PurifyingRecipe recipe, 
                                              int count) {
        var outputTrashStack = recipe.getOutputTrash();
        if (outputTrashStack.isEmpty()) {
            return true;
        }
        var outputTrashSlotStack = inventory.getStack(EnergyMachineInventoryType.OUTPUT, process * 3 + 2);
        return isOutputStackFit(count, outputTrashStack, outputTrashSlotStack);
    }

    private boolean isOutputStackFit(int count,
                                     ItemStack outputStack,
                                     ItemStack slotStack) {
        if (slotStack.isEmpty()) {
            return true;
        }
        if (!ItemStack.areItemsEqual(slotStack, outputStack)) {
            return false;
        }
        int outputSlotCount = slotStack.getCount();
        if (outputSlotCount < count && outputSlotCount < slotStack.getMaxCount()) {
            return true;
        }
        return outputSlotCount < outputStack.getMaxCount();
    }

    @Override
    public boolean craftRecipe(int process,
                               @NotNull World world,
                               @NotNull Recipe<Inventory> recipe,
                               int decrement,
                               int maxCount) {
        if (!canAcceptRecipeOutput(process, world, recipe, maxCount)) {
            return false;
        }
        if(!(recipe instanceof PurifyingRecipe purifyingRecipe)) {
            return false;
        }
        var sourceStack = inventory.getStack(EnergyMachineInventoryType.SOURCE, process);
        var registryManager = world.getRegistryManager();
        craftOutputStack(purifyingRecipe.getOutput(registryManager), process * 3);
        craftOutputStack(purifyingRecipe.getOutputSide(), process * 3 + 1);
        craftOutputStack(purifyingRecipe.getOutputTrash(), process * 3 + 2);
        sourceStack.decrement(decrement);
        return true;
    }

    private void craftOutputStack(ItemStack recipeStack, int slotIndex) {
        var outputStack = inventory.getStack(EnergyMachineInventoryType.OUTPUT, slotIndex);
        if (outputStack.isEmpty()) {
            inventory.setStack(EnergyMachineInventoryType.OUTPUT, slotIndex, recipeStack.copy());
        } else if (outputStack.isOf(recipeStack.getItem())) {
            outputStack.increment(recipeStack.getCount());
        }
    }

    private int getSourceAmount() {
        var sourceInventory = inventory.getInventory(EnergyMachineInventoryType.SOURCE);
        if(sourceInventory == null) {
            return 0;
        }
        int amount = 0;
        for (int i = 0; i < sourceInventory.size(); i++) {
            var itemStack = sourceInventory.getStack(i);
            if(!itemStack.isEmpty()) {
                amount += itemStack.getCount();
            }
        }
        return amount;
    }

    @Override
    public @NotNull Energy getEnergyUsagePerTick() {
        return block.getEnergyPerTick();
    }
}

