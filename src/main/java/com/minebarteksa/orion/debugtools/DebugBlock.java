package com.minebarteksa.orion.debugtools;

import com.minebarteksa.orion.Orion;
import com.minebarteksa.orion.blocks.TileEntityBlockBase;
import com.minebarteksa.orion.multiblock.MultiBlock;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class DebugBlock extends TileEntityBlockBase<DebugBlock.DebugBlockEntity>
{
    public DebugBlock() { super(Material.ROCK, "debug_block", Orion.ModID); }

    @Override
    public Class<DebugBlockEntity> getTileEntityClass() { return DebugBlockEntity.class; }

    @Nullable
    @Override
    public DebugBlockEntity createTileEntity(World world, IBlockState state) { return new DebugBlockEntity(); }

    public static class DebugBlockEntity extends MultiBlock
    {
        public DebugBlockEntity() { super(new ResourceLocation(Orion.ModID, "debug_block")); }
    }
}
