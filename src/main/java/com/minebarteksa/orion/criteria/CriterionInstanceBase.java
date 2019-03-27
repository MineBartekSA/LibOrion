package com.minebarteksa.orion.criteria;

import com.google.gson.JsonObject;
import net.minecraft.advancements.ICriterionInstance;
import net.minecraft.util.ResourceLocation;

public abstract class CriterionInstanceBase implements ICriterionInstance
{
    private final ResourceLocation id;

    public CriterionInstanceBase(String id) { this(new ResourceLocation(id)); }

    public CriterionInstanceBase(ResourceLocation id) { this.id = id; }

    @Override
    public ResourceLocation getId() { return this.id; }

    @Override
    public String toString() { return "LibOrionCriterionInstance{criterion=" + this.id + "}"; }

    public abstract CriterionInstanceBase deserialize(JsonObject json);
}
