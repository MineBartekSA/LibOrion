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

        Orion.log.info(new Gson().toJson(json));

        MultiBlockInfo mbi = new MultiBlockInfo(name);
        if(json.type.equals("single"))
        {
            mbi.type = MultiBlockType.Single;
            mbi.blocks = json.blocks;
            if(mbi.blocks == null)
            {
                Orion.log.error("The field 'blocks' in the json file is missing! Check the spelling or change the type");
                return null;
            }
            if(checkBlockPoints(mbi.blocks))
            {
                Orion.log.error("The field 'block' in your 'blocks' in your json file is missing or invalid!");
                return null;
            }
        }
        else if(json.type.equals("multi"))
        {
            mbi.type = MultiBlockType.Multi;
            mbi.multiblocks = json.multiblocks;
            if(mbi.multiblocks == null)
            {
                Orion.log.error("The field 'multiblocks' in the json file is missing! Check the spelling or change the type");
                return null;
            }
            for(MMultiBlocks mm : mbi.multiblocks)
            {
                if(mm.name == "" || mm.name == " " || mm.name == null)
                {
                    Orion.log.error("The field 'name' of one of the MultiBlock variants in the json file is missing or invalid!");
                    return null;
                }
                if(mm.blocks == null)
                {
                    Orion.log.error("The field 'blocks' in the '" + name + "#" + mm.name +"' MultiBlock in json file is missing! Check the spelling of your json");
                    return null;
                }
                if(checkBlockPoints(mm.blocks))
                {
                    Orion.log.error("The field 'block' in your 'blocks' in the '" + name + "#" + mm.name +"' MultiBlock in json file is missing or invalid!");
                    return null;
                }
            }
        }

        if(mbi.type == null)
        {
            Orion.log.error("The field 'type' in the json file is missing or invalid!");
            return null;
        }

        return mbi;
    }

    private static boolean checkBlockPoints(BlockPoint[] bps)
    {
        for(BlockPoint bp : bps)
        {
            if(bp.block.equals("") || bp.block.equals(" ") || bp.block == null)
                return true;
        }
        return false;
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
