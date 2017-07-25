package com.gmail.zendarva.scheduler.capabilities;

import net.minecraft.item.ItemStack;
import net.minecraftforge.items.IItemHandler;

import javax.annotation.Nonnull;
import java.util.ArrayList;

/**
 * Created by James on 7/24/2017.
 */
public class ThreadedItemHandler implements IItemHandler, ThreadedCapability {

    private final int slots;
    private ArrayList<ItemStack> inventory;

    public ThreadedItemHandler(int slots){

        this.slots = slots;
        inventory = new ArrayList<>(slots);
    }
    @Override
    public int getSlots() {
        return slots;
    }

    @Nonnull
    @Override
    public synchronized ItemStack getStackInSlot(int slot) {
        return inventory.get(slot);
    }

    @Nonnull
    @Override
    public synchronized ItemStack insertItem(int slot, @Nonnull ItemStack stack, boolean simulate) {
        ItemStack targStack = inventory.get(slot);
        int amountFree = targStack.getMaxStackSize()-targStack.getCount();
        int amountToAdd = Math.min(amountFree, stack.getCount());
        if (!simulate)
            targStack.grow(amountToAdd);
        ItemStack copy = stack.copy();
        copy.shrink(amountToAdd);
        return copy;
    }

    @Nonnull
    @Override
    public synchronized ItemStack extractItem(int slot, int amount, boolean simulate) {
        ItemStack stack = inventory.get(slot);
        if (stack.isEmpty()){
            return stack;
        }
        ItemStack toReturn = stack.copy();
        int returnAmount = Math.min(amount,toReturn.getCount());
        toReturn.setCount(returnAmount);
        stack.shrink(returnAmount);
        return toReturn;
    }

    @Override
    public int getSlotLimit(int slot) {
        return this.getStackInSlot(slot).getMaxStackSize();
    }
}
