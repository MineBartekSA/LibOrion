package com.minebarteksa.orion.multiblock;

import com.minebarteksa.orion.Orion;
import com.minebarteksa.orion.events.IOrionListener;
import com.minebarteksa.orion.events.OrionBlockEvents;
import com.minebarteksa.orion.events.OrionEvent;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ITickable;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import com.minebarteksa.orion.multiblock.MultiBlockInfo.MultiBlockType;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

public abstract class MultiBlock extends TileEntity implements ITickable, IOrionListener // The Extendable class for the MultiBlocks!
{
    private ResourceLocation name; // Name equals to the name of the json file for the multi block
    private MultiBlockInfo mbInfo;
    //private String mmName;
    private Boolean isCompleted = false;
    private Boolean isRegisteredIOL = false;

    public MultiBlock(ResourceLocation name)
    {
        this.name = name;
        if(OrionMultiBlocks.REGISTRY.containsKey(this.name))
            this.mbInfo = OrionMultiBlocks.REGISTRY.getObject(this.name);
        else
            register();
    }

    private void register()
    {
        String json;
        try
        {
            String j[] = new String[] {""};
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
    public void onOrionEvent(OrionEvent ev)
    {
        if(ev instanceof OrionBlockEvents.BlockBreak)
        {
            if(!isCompleted)
                return;
            for(MultiBlockInfo.BlockPoint bp : mbInfo.blocks)
            {
                if(getBlockPosFromPoint(bp).equals(((OrionBlockEvents.BlockBreak) ev).pos))
                {
                    isCompleted = false;
                    if(mbInfo.type == MultiBlockType.Multi)
                    {
                        mbInfo.blocks = null;
                        //mmName = "";
                    }
                }
            }
        }
    }

    @Override
    public void update()
    {
        if(!isRegisteredIOL)
        {
            OrionBlockEvents.BB.addListener(this);
            isRegisteredIOL = true;
        }
        if(!isCompleted)
        {
            if(mbInfo.type == MultiBlockType.Single)
            {
                for(MultiBlockInfo.BlockPoint bp : mbInfo.blocks)
                    if(!(getWorld().getBlockState(getBlockPosFromPoint(bp)).getBlock().getRegistryName().toString().equals(new ResourceLocation(bp.block).toString())))
                        return;
                isCompleted = true;
                this.onCompleted();
            }
            else if(mbInfo.type == MultiBlockType.Multi)
            {
                // ToDo - Multi MultiBlocks
            }
        }
    }

    @Override
    protected void finalize() { OrionBlockEvents.BB.removeListener(this); }

    public abstract void onCompleted();

    private BlockPos getBlockPosFromPoint(MultiBlockInfo.BlockPoint point) { return new BlockPos(pos.getX() + point.x, pos.getY() + point.y, pos.getZ() + point.z); }
}
