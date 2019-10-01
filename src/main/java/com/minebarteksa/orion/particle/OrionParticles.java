package com.minebarteksa.orion.particle;

import com.minebarteksa.orion.Orion;
import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.IParticleFactory;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleManager;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

@SideOnly(Side.CLIENT)
public class OrionParticles
{
    public static ParticleManager partManager = Minecraft.getMinecraft().effectRenderer;
    private static Map<Integer, IParticleFactory> particles;
    private static Map<Integer, ResourceLocation> particleNames = new HashMap<>();
    private static int nextId = 0;

    public static void init()
    {
        try
        {
            Field f = partManager.getClass().getDeclaredField("particleTypes");
            f.setAccessible(true);
            particles = (Map<Integer, IParticleFactory>) f.get(partManager);
        }
        catch (Exception e)
        {
            Orion.log.error("Failed to initialize OrionParticles");
            return;
        }

        for (Map.Entry<Integer, IParticleFactory> p : particles.entrySet())
        {
            EnumParticleTypes particle = EnumParticleTypes.getParticleFromId(p.getKey());
            if (particle == null)
                continue;
            if(nextId < p.getKey() + 1)
                nextId = p.getKey() + 1;
            particleNames.put(p.getKey(), new ResourceLocation("minecraft", particle.getParticleName()));
        }
    }

    public static void registerParticle(ResourceLocation particleName, IParticleFactory factory)
    {
        partManager.registerParticle(nextId, factory);
        particleNames.put(nextId, particleName);
        nextId++;
    }

    public static Particle spawnParticle(ResourceLocation paritcleName, double xCoord, double yCoord, double zCoord, double xSpeed, double ySpeed, double zSpeed, int... parameters)
    {
        for (Map.Entry<Integer, ResourceLocation> p : particleNames.entrySet())
            if (p.getValue() == paritcleName)
                return spawnParticle(p.getKey(), xCoord, yCoord, zCoord, xSpeed, ySpeed, zSpeed, parameters);
        return null;
    }

    public static Particle spawnParticle(int particleId, double xCoord, double yCoord, double zCoord, double xSpeed, double ySpeed, double zSpeed, int... parameters)
    {
        return partManager.spawnEffectParticle(particleId, xCoord, yCoord, zCoord, xSpeed, ySpeed, zSpeed, parameters);
    }

    public static ResourceLocation getParticleNameByID(int id) { return particleNames.get(id); }

    public static int getMaxIDValue() { return particles.size(); }
}
