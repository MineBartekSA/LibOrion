package com.minebarteksa.orion.multiblock;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.minebarteksa.orion.Orion;
import net.minecraft.util.JsonUtils;
import net.minecraft.util.ResourceLocation;
import javax.annotation.Nullable;

public class MultiBlockInfo
{
    private ResourceLocation name;

    public MultiBlockType type;
    public BlockPoint[] blocks;
    public MMultiBlocks[] multiblocks;

    public MultiBlockInfo(ResourceLocation name)
    {
        this.name = name;
    }

    public ResourceLocation getName() { return name; }

    public static MultiBlockInfo createFromJsonFile(String jsonString, ResourceLocation name)
    {
        JsonClass json = new Gson().fromJson(jsonString, JsonClass.class);

        MultiBlockInfo mbi = new MultiBlockInfo(name);
        if(json.type.equals("single"))
        {
            mbi.type = MultiBlockType.Single;
            mbi.blocks = json.blocks;
        }
        else if(json.type.equals("multi"))
        {
            mbi.type = MultiBlockType.Multi;
            mbi.multiblocks = json.multiblocks;
        }

        if(mbi.type == null)
        {
            Orion.log.error("MultiBlock's 'type' in the json file is missing or invalid!");
            return null;
        }

        return mbi;
    }

    public static class JsonClass
    {
        public String type;
        public Boolean changeBorders; // ToDo - MultiBlock have only one border
        public BlockPoint[] blocks;
        public MMultiBlocks[] multiblocks;
    }

    public static class BlockPoint
    {
        public String block; // In ResourceLocation format aka domain:path
        public String connectedModel; // ToDo - For the types singleConnected and multiConnected
        public int x;
        public int y;
        public int z;
    }

    public static class MMultiBlocks
    {
        public String name;
        public BlockPoint[] blocks;
    }

    public enum MultiBlockType
    {
        Single, Multi
    }
}
