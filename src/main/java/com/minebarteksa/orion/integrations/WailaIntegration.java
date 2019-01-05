package com.minebarteksa.orion.integrations;

import com.minebarteksa.orion.Orion;
import com.minebarteksa.orion.integrations.infoprovider.IPData;
import mcp.mobius.waila.api.*;
import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import javax.annotation.Nonnull;
import java.util.List;

@WailaPlugin
public class WailaIntegration implements IWailaPlugin
{
    private WailaProvider provider = new WailaProvider();

    @Override
    public void register(IWailaRegistrar registrar) { Orion.registry.registerWailaProviders(registrar, provider); }

    private class WailaProvider implements IWailaDataProvider
    {
        @Nonnull
        @Override
        public List<String> getWailaBody(ItemStack itemStack, List<String> tooltip, IWailaDataAccessor accessor, IWailaConfigHandler config)
        {
            Block b = accessor.getBlock();
            if(b instanceof IOrionInfoProvider)
                tooltip.addAll(((IOrionInfoProvider)b).addInfo(new IPData(accessor.getPosition(), accessor.getSide(), accessor.getPlayer(), accessor.getWorld())));
            else if(b.hasTileEntity(accessor.getBlockState()))
            {
                TileEntity te = accessor.getWorld().getTileEntity(accessor.getPosition());
                if(te instanceof IOrionInfoProvider)
                    tooltip.addAll(((IOrionInfoProvider)te).addInfo(new IPData(accessor.getPosition(), accessor.getSide(), accessor.getPlayer(), accessor.getWorld())));
            }
            return tooltip; // ToDo - Better information gathering system
        }
    }
}
