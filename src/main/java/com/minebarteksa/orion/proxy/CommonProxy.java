package com.minebarteksa.orion.proxy;

import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraft.util.text.translation.I18n;

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

  public String locaize(String unlocalized, Object ...args)
  {
    return I18n.translateToLocalFormatted(unlocalized, args);
  }
}
