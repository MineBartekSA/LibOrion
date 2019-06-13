package com.minebarteksa.orion.multiblock;

import com.google.gson.Gson;
import com.minebarteksa.orion.Orion;
import net.minecraft.util.ResourceLocation;

public class MultiBlockInfo
{
    private ResourceLocation name;

    public MultiBlockType type;
    public BlockPoint[] blocks;
    public MMultiBlocks[] multiblocks;
    //public Boolean toolRequired;

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
            //mbi.toolRequired = json.toolRequired;
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
            /*if(mbi.toolRequired && checkToolRequirements(mbi.blocks))
            {
                Orion.log.error("This multiblock ('"+ name + "') is lacking the 0 0 0 block!");
                return null;
            }*/
        }
        else if(json.type.equals("multi"))
        {
            mbi.type = MultiBlockType.Multi;
            mbi.multiblocks = json.multiblocks;
            //mbi.toolRequired = json.toolRequired;
            if(mbi.multiblocks == null)
            {
                Orion.log.error("The field 'multiblocks' in the json file is missing! Check the spelling or change the type");
                return null;
            }
            for(MMultiBlocks mm : mbi.multiblocks)
            {
                if(mm.name.equals("") || mm.name.equals(" ") || mm.name == null)
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
                /*if(mbi.toolRequired && checkToolRequirements(mbi.blocks))
                {
                    Orion.log.error("This multiblock ('" + name + "#" + mm.name + "') is lacking the 0 0 0 block!");
                    return null;
                }*/
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
            if(bp.block.equals("") || bp.block.equals(" ") || bp.block == null)
                return true;
        return false;
    }

    private static boolean checkToolRequirements(BlockPoint[] bps)
    {
        for(BlockPoint bp : bps)
            if(bp.x == 0 && bp.y == 0 && bp.z == 0)
                return true;
        return false;
    }

    public static class JsonClass
    {
        public String type;
        public Boolean changeBorders; // ToDo: MultiBlock have only one border
        public BlockPoint[] blocks;
        public MMultiBlocks[] multiblocks;
        public Boolean toolRequired; // ToDo: Tool for MultiBlock creation!
    }

    public static class BlockPoint
    {
        public String block; // In ResourceLocation format aka domain:path
        public String connectedModel; // ToDo: For the types singleConnected and multiConnected
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
