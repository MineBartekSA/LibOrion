package com.minebarteksa.orion.tileentity;

import com.minebarteksa.orion.OrionEnergy;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.energy.CapabilityEnergy;

import javax.annotation.Nullable;

public class TileEntityMachine extends TileEntityInventory
{
    protected OrionEnergy energy;
    protected int progress;

    public int getProgressPercentage(int barWidth) { return (progress * barWidth) / 100; }

    public int getEnergyPercentage(int barWidth) { return (this.energy.getEnergyProcentage() * barWidth) / 100; }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound)
    {
        compound.setTag("energy", energy.serializeNBT());
        compound.setInteger("progress", progress);
        return super.writeToNBT(compound);
    }

    @Override
    public void readFromNBT(NBTTagCompound compound)
    {
        energy.deserializeNBT(compound.getCompoundTag("energy"));
        progress = compound.getInteger("progress");
        super.readFromNBT(compound);
    }

    @Nullable
    @Override
    public <T> T getCapability(Capability<T> capability, @Nullable EnumFacing facing)
    {
        if (capability == CapabilityEnergy.ENERGY)
            return (T)energy;
        return super.getCapability(capability, facing);
    }
}
