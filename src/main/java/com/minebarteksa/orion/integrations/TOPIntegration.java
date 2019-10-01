package com.minebarteksa.orion.integrations;

import com.google.common.base.Function;
import com.minebarteksa.orion.Orion;
import com.minebarteksa.orion.integrations.infoprovider.IPData;
import mcjty.theoneprobe.api.*;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.List;

public class TOPIntegration
{
    public static ITheOneProbe itop;

    public static class GetTheOneProbe implements Function<ITheOneProbe, Void>
    {
        @Nullable
        @Override
        public Void apply(ITheOneProbe input) {
            itop = input;
            Orion.log.info("TheOneProbe integration enabled!");

            itop.registerProvider(new IProbeInfoProvider() {
                @Override
                public String getID() { return "liborion:default"; }

                @Override
                public void addProbeInfo(ProbeMode probeMode, IProbeInfo iProbeInfo, EntityPlayer entityPlayer, World world, IBlockState iBlockState, IProbeHitData iProbeHitData)
                {
                    if(iBlockState.getBlock() instanceof IOrionInfoProvider)
                    {
                        List<String> tt = ((IOrionInfoProvider)iBlockState.getBlock()).addInfo(new IPData(iProbeHitData.getPos(), iProbeHitData.getSideHit(), entityPlayer, world));
                        for(String t : tt)
                            iProbeInfo.text(t); // ToDo - Better information gathering system
                    }
                    else if(iBlockState.getBlock().hasTileEntity(iBlockState))
                    {
                        TileEntity te = world.getTileEntity(iProbeHitData.getPos());
                        if(te instanceof IOrionInfoProvider)
                        {
                            List<String> tt = ((IOrionInfoProvider)te).addInfo(new IPData(iProbeHitData.getPos(), iProbeHitData.getSideHit(), entityPlayer, world));
                            for(String t : tt)
                                iProbeInfo.text(t);
                        }
                    }
                }
            });

            return null;
        }
    }
}
