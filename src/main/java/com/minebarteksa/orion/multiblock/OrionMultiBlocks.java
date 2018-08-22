package com.minebarteksa.orion.multiblock;

import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.RegistryNamespaced;

public class OrionMultiBlocks
{
    private static int nextId = 0;
    public static final RegistryNamespaced<ResourceLocation, MultiBlockInfo> REGISTRY = new RegistryNamespaced<>();

    public static void register(MultiBlockInfo mBlock)
    {
        REGISTRY.register(nextId, mBlock.getName(), mBlock);
        nextId++;
    }

    public static int getMultiBlockId(ResourceLocation multiblock) { return REGISTRY.getIDForObject(REGISTRY.getObject(multiblock)); }
}
