package com.minebarteksa.orion.blocks;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;

public interface TERegister extends BlockRegister
{
    Class<? extends TileEntity> getTileEntityClass();
    ResourceLocation getRegistryName();
}
