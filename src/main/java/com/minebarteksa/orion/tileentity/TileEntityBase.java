package com.minebarteksa.orion.tileentity;

import net.minecraft.block.state.IBlockState;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;

import javax.annotation.Nullable;

public class TileEntityBase extends TileEntity
{
    @Override
    public boolean hasCapability(Capability<?> capability, @Nullable EnumFacing facing)
    {
        if(getCapability(capability, facing) != null)
            return true;
        return super.hasCapability(capability, facing);
    }

    @Override
    public NBTTagCompound getUpdateTag() { return this.writeToNBT(new NBTTagCompound()); }

    @Nullable
    @Override
    public SPacketUpdateTileEntity getUpdatePacket() { return new SPacketUpdateTileEntity(this.pos, 0, this.getUpdateTag()); }

    @Override
    public void onDataPacket(NetworkManager net, SPacketUpdateTileEntity pkt) { readFromNBT(pkt.getNbtCompound()); }

    @Override
    public boolean shouldRefresh(World world, BlockPos pos, IBlockState oldState, IBlockState newSate) { return (oldState.getBlock() != newSate.getBlock()); }
}
