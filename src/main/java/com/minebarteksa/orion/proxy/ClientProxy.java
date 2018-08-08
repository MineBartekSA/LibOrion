package com.minebarteksa.orion.proxy;

import com.minebarteksa.orion.Orion;
import net.minecraft.client.particle.ParticleManager;
import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraft.client.resources.I18n;

public class ClientProxy extends CommonProxy
{
  @Override
  public void preInit(FMLPreInitializationEvent ev)
  {
    super.preInit(ev);
  }

  @Override
  public void init(FMLInitializationEvent ev)
  {
    super.init(ev);
  }

  @Override
  public void postInit(FMLPostInitializationEvent ev)
  {
    super.postInit(ev);

    ParticleManager pm = Minecraft.getMinecraft().effectRenderer;
    if(pm != null)
    {
      Orion.log.info(pm.getStatistics());
    }
  }

  public String locaize(String unlocalized, Object... args)
  {
    return I18n.format(unlocalized, args);
  }
}
