package com.minebarteksa.orion.proxy;

import net.minecraft.util.text.translation.I18n;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

@SuppressWarnings("deprecation")
public class CommonProxy
{
    public void preInit(FMLPreInitializationEvent ev)
    {
        //
    }

    public void init(FMLInitializationEvent ev)
    {
        //
    }

    public void postInit(FMLPostInitializationEvent ev)
    {
        //
    }

    public String localize(String unlocalized, Object ...args) { return I18n.translateToLocalFormatted(unlocalized, args); }
}
