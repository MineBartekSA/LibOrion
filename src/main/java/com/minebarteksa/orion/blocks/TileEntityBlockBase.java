package com.minebarteksa.orion.blocks;

import javax.annotation.Nullable;
import net.minecraft.block.Block;
import net.minecraft.world.World;
import net.minecraft.world.IBlockAccess;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;

public abstract class TileEntityBlockBase<TE extends TileEntity> extends BlockBase implements TERegister
{
  public TileEntityBlockBase(Material mat, String name, String modelDomain)
  {
    super(mat, name, modelDomain);
  }

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

  @Override
  public Block getBlock() { return this; }
}
