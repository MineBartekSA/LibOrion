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
import net.minecraft.world.chunk.Chunk;
import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

public abstract class MultiBlock extends TileEntity implements ITickable, IOrionListener // The Extendable class for the MultiBlocks!
{
    private ResourceLocation name; // Name equals to the name of the json file for the multi block
    protected MultiBlockInfo mbInfo;
    private String mmName;
    private List<Chunk> multiblockChunks = new ArrayList<>();
    protected Boolean isCompleted = false;
    private Boolean disabled = false;
    private Boolean isRegisteredIOL = false;

    public MultiBlock(ResourceLocation name)
    {
        this.name = name;
        if(OrionMultiBlocks.REGISTRY.containsKey(this.name))
            this.mbInfo = OrionMultiBlocks.REGISTRY.getObject(this.name);
        else
        {
            Orion.log.info("No MultiBlock that matches this '" + name + "' name!");
            disabled = true;
        }
    }

    @Override
    public void onOrionEvent(OrionEvent ev)
    {
        if(ev instanceof OrionBlockEvents.BlockBreak)
        {
            if(!isCompleted)
                return;
            if(mbInfo.blocks == null || multiblockChunks.isEmpty())
            {
                isCompleted = false;
                return;
            }
            if(!multiblockChunks.contains(world.getChunkFromBlockCoords(((OrionBlockEvents.BlockBreak) ev).pos)))
                return;
            for(MultiBlockInfo.BlockPoint bp : mbInfo.blocks)
            {
                if(getBlockPosFromPoint(bp).equals(((OrionBlockEvents.BlockBreak) ev).pos))
                {
                    isCompleted = false;
                    multiblockChunks.clear();
                    if(mbInfo.type == MultiBlockType.Multi)
                    {
                        mbInfo.blocks = null;
                        mmName = "";
                    }
                    this.onDestroyedMultiBlock();
                }
            }
        }
    }

    @Override
    public void update()
    {
        if(world.isRemote || disabled)
            return;
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
                    if(!(getWorld().getBlockState(getBlockPosFromPoint(bp)).getBlock().getRegistryName().equals(new ResourceLocation(bp.block))))
                        return;
                isCompleted = true;
                this.onCompleted();
                this.getChunks();
            }
            else if(mbInfo.type == MultiBlockType.Multi)
            {
                for(MultiBlockInfo.MMultiBlocks mm : mbInfo.multiblocks)
                {
                    if(!checkMM(mm))
                        continue;
                    isCompleted = true;
                    mmName = mm.name;
                    mbInfo.blocks = mm.blocks;
                    this.onCompleted();
                    this.getChunks();
                }
            }
        }
    }

    private Boolean checkMM(MultiBlockInfo.MMultiBlocks mm)
    {
        for(MultiBlockInfo.BlockPoint bp : mm.blocks)
            if(!(getWorld().getBlockState(getBlockPosFromPoint(bp)).getBlock().getRegistryName().equals(new ResourceLocation(bp.block))))
                return false;
        return true;
    }

    private void getChunks()
    {
        for(MultiBlockInfo.BlockPoint bp : mbInfo.blocks)
            if(!multiblockChunks.contains(world.getChunkFromBlockCoords(getBlockPosFromPoint(bp))))
                multiblockChunks.add(world.getChunkFromBlockCoords(getBlockPosFromPoint(bp)));
    }

    protected void finalize() { if(!world.isRemote) OrionBlockEvents.BB.removeListener(this); }

    public abstract void onCompleted();

    public void onDestroyedMultiBlock() {}

    private BlockPos getBlockPosFromPoint(MultiBlockInfo.BlockPoint point) { return new BlockPos(pos.getX() + point.x, pos.getY() + point.y, pos.getZ() + point.z); }

    @Nullable
    public String getCurrentMultiBlockName()
    {
        if(!isCompleted)
            return null;
        if(mbInfo.type == MultiBlockType.Single)
            return name.toString();
        else if(mbInfo.type == MultiBlockType.Multi)
            return name + "#" + mmName;
        return null;
    }
}
