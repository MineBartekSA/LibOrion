package com.minebarteksa.orion.multiblock;

import com.minebarteksa.orion.items.ItemBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public abstract class MultiBlockTool extends ItemBase
{
    public MultiBlockTool(String name, String multiBlockName, String modelDomain)
    {
        super(name, modelDomain);
        //
    }

    @Override
    public EnumActionResult onItemUse(EntityPlayer player, World worldIn, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ)
    {
        // ToDo: All the multiblock detection

        //

        return EnumActionResult.SUCCESS;
    }

    public abstract void onCreated();
}
