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

    //Json fields
    public String type;

    public MultiBlockInfo(ResourceLocation name)
    {
        this.name = name;
    }

    public ResourceLocation getName() { return name; }

    @Nullable
    public static MultiBlockInfo createFromJsonFile(String jsonString, ResourceLocation name)
    {
        Orion.log.info("Raw Json: " + jsonString);
        //JsonObject json = new Gson().fromJson(jsonString, JsonObject.class);
        //Orion.log.info("Json type: " + json.get("type"));
        return null;
    }
}
