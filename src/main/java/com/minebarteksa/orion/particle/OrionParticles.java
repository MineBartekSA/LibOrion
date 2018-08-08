package com.minebarteksa.orion.particle;

import net.minecraft.client.particle.IParticleFactory;
import net.minecraft.client.particle.ParticleManager;
import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraft.client.particle.Particle;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.RegistryNamespacedDefaultedByKey;
import net.minecraft.util.EnumParticleTypes;

@SideOnly(Side.CLIENT)
public class OrionParticles
{
  public static RegistryNamespacedDefaultedByKey<ResourceLocation, ParticleBase.Factory> REGISTRY;
  private static int nextId = 0;
  public static ParticleManager partManager = Minecraft.getMinecraft().effectRenderer;

  public static void init()
  {
    for(EnumParticleTypes type : EnumParticleTypes.values())
    {
      REGISTRY.register(type.getParticleID(), new ResourceLocation("minecraft", type.getParticleName()), "minecraft");
      nextId++;
    }
  }

  public static void registerParticle(ResourceLocation particleName, ParticleBase.Factory factory)
  {
    REGISTRY.register(nextId, particleName, factory);
    partManager.registerParticle(nextId, factory);
    nextId++;
  }

  public static void spawnParticle(ResourceLocation particleName)
  {
    ParticleBase.Factory factory = REGISTRY.getObject(particleName);

    if(factory != null)
    {
      //Particle particle = factory.createParticle(REGISTRY.getIDForObject(factory), worldIn, xCoordIn, yCoordIn, zCoordIn, xSpeedIn, ySpeedIn, zSpeedIn, p_178902_15_)
      //
    }
  }
}
