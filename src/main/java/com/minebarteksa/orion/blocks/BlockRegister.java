package com.minebarteksa.orion.blocks;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public interface BlockRegister
{
    @SideOnly(Side.CLIENT)
    void registerItemModel();
    Item getItemBlock();
    Block getBlock();
}
