package com.minebarteksa.orion.particle;

import net.minecraft.client.particle.IParticleFactory;
import net.minecraft.util.ResourceLocation;

public class ParticleRegister
{
    public IParticleFactory factory;
    public ResourceLocation name;

    public ParticleRegister(ResourceLocation name, IParticleFactory factory)
    {
        this.factory = factory;
        this.name = name;
    }
}
