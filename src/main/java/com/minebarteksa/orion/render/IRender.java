package com.minebarteksa.orion.render;

import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.WorldClient;

public interface IRender
{
    void render(float pTick, WorldClient world, Minecraft mc);
}
