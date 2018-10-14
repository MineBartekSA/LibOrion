package com.minebarteksa.orion.blocks;

import net.minecraft.block.BlockHorizontal;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BlockBaseWithFacing extends BlockBase
{
    public static final PropertyDirection FACING = BlockHorizontal.FACING;

    public BlockBaseWithFacing(Material mat, String name, String modelDomain)
    {
        super(mat, name, modelDomain);
        this.setDefaultState(this.blockState.getBaseState().withProperty(FACING, EnumFacing.NORTH));
    }

    @Override
    public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack) { worldIn.setBlockState(pos, state.withProperty(FACING, placer.getHorizontalFacing()), 2); }

    public IBlockState getStateFromMeta(int meta)
    {
        EnumFacing enumfacing = EnumFacing.getFront(meta);

        if (enumfacing.getAxis() == EnumFacing.Axis.Y)
            enumfacing = EnumFacing.NORTH;

        return this.getDefaultState().withProperty(FACING, enumfacing);
    }

    public int getMetaFromState(IBlockState state) { return state.getValue(FACING).getIndex(); }

    protected BlockStateContainer createBlockState() { return new BlockStateContainer(this, FACING); }
}
