package com.minebarteksa.orion.proxy;

import com.minebarteksa.orion.Orion;
import com.minebarteksa.orion.particle.OrionParticles;
import net.minecraft.client.resources.I18n;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

public class ClientProxy extends CommonProxy
{
    @Override
    public void preInit(FMLPreInitializationEvent ev)
    {
        super.preInit(ev);
        //Orion.renderers = new RenderHandler();
    }

    @Override
    public void init(FMLInitializationEvent ev)
    {
        super.init(ev);
        Orion.registry.registerTESRS();
        Orion.registry.registerEntityRenderers();
        OrionParticles.init();
    }

    @Override
    public void postInit(FMLPostInitializationEvent ev) { super.postInit(ev); }

    public String localize(String unlocalized, Object... args) { return I18n.format(unlocalized, args); }
}
