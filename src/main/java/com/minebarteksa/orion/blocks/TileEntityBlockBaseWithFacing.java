package com.minebarteksa.orion.blocks;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public abstract class TileEntityBlockBaseWithFacing<TE extends TileEntity> extends BlockBaseWithFacing
{
    public TileEntityBlockBaseWithFacing(Material mat, String name, String modelDomain) { super(mat, name, modelDomain); }

    public abstract Class<TE> getTileEntityClass();

    @SuppressWarnings("unchecked")
    public TE getTileEntity(IBlockAccess world, BlockPos pos)
    {
        return (TE)world.getTileEntity(pos);
    }

    @Override
    public boolean hasTileEntity(IBlockState state)
    {
        return true;
    }

    @Nullable
    @Override
    public abstract TE createTileEntity(World world, IBlockState state);
}
