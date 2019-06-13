package com.minebarteksa.orion;

import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;

public interface TESRRegister<T extends TileEntitySpecialRenderer>
{
    Class<?> getTileEntityClass();
    T getTESR();
}
