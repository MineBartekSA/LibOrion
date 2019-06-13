package com.minebarteksa.orion.inventory;

import net.minecraft.item.ItemStack;
import net.minecraftforge.items.ItemStackHandler;

public class IOItemStackHandler extends ItemStackHandler
{
    public ItemStackHandler input;
    private int inS, outS, norS;

    public IOItemStackHandler(int inputSlotCount, int outputSlotCount, int normalSlotCount)
    {
        super(inputSlotCount + outputSlotCount + normalSlotCount);
        input = new ItemStackHandler(inputSlotCount + normalSlotCount);
        inS = inputSlotCount;
        outS = outputSlotCount;
        norS = normalSlotCount;
    }

    @Override
    public void setStackInSlot(int slot, ItemStack stack) {
        input.setStackInSlot(slot, stack);
    }

    @Override
    public int getSlots() {
        return input.getSlots();
    }

    @Override
    public ItemStack getStackInSlot(int slot) {
        return input.getStackInSlot(slot);
    }

    @Override
    public ItemStack insertItem(int slot, ItemStack stack, boolean simulate) {
        return input.insertItem(slot, stack, simulate);
    }

    @Override
    public ItemStack extractItem(int slot, int amount, boolean simulate) {
        return super.extractItem(slot, amount, simulate);
    }
}
