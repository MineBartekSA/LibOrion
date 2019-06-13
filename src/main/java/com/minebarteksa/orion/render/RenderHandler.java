package com.minebarteksa.orion.render;

import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.WorldClient;

import java.util.ArrayList;
import java.util.List;

public class RenderHandler
{
    private static List<IRender> renders = new ArrayList<>();

    public static void addRender(IRender render) { renders.add(render); }

    public static void removeRender(IRender render) { renders.remove(render); }

    public void render(float pTick, WorldClient world, Minecraft mc)
    {
        for(IRender r : renders)
            r.render(pTick, world, mc);
    }
}
