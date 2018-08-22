package com.minebarteksa.orion.multiblock;

import com.minebarteksa.orion.Orion;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ITickable;
import net.minecraft.util.ResourceLocation;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

public class MultiBlock extends TileEntity implements ITickable // The Extendable class for the MultiBlocks!
{
    private ResourceLocation name; // Name equals to the name of the json file for the multi block
    private MultiBlockInfo mbInfo;

    public MultiBlock(ResourceLocation name)
    {
        this.name = name;
        if(OrionMultiBlocks.REGISTRY.containsKey(this.name))
            this.mbInfo = OrionMultiBlocks.REGISTRY.getObject(this.name);
        else
            register();
    }

    public void register()
    {
        String json;
        try
        {
            String j[] = new String[1];
            Orion.log.info(getClass().getClassLoader().getResource("assets/" + name.getResourceDomain() + "/multiblocks/" + name.getResourcePath() + ".json").toString());
            BufferedReader reader = new BufferedReader(new InputStreamReader(getClass().getClassLoader().getResourceAsStream("assets/" + name.getResourceDomain() + "/multiblocks/" + name.getResourcePath() + ".json"), StandardCharsets.UTF_8));
            reader.lines().forEachOrdered(l -> { if(l != null) j[0] += l.replace(" ", ""); });
            json = j[0];
        }
        catch(Exception e)
        {
            Orion.log.error("Error while trying to read json file for MultiBlock '" + name + "'");
            Orion.log.error(e.toString());
            return;
        }
        this.mbInfo = MultiBlockInfo.createFromJsonFile(json, name);
        if(mbInfo != null)
            OrionMultiBlocks.register(mbInfo);
        else
            Orion.log.error("Error while trying to read form json file for MultiBlock '" + name + "'");
    }

    @Override
    public void update()
    {
        // The main part of this class
    }
}
