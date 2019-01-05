package com.minebarteksa.orion;

import com.minebarteksa.orion.blocks.BlockRegister;
import com.minebarteksa.orion.blocks.TERegister;
import com.minebarteksa.orion.integrations.IOrionInfoProvider;
import com.minebarteksa.orion.items.ItemBase;
import mcp.mobius.waila.api.IWailaDataProvider;
import mcp.mobius.waila.api.IWailaRegistrar;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.fml.common.Optional;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.registries.IForgeRegistry;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class OrionRegistry
{
    private static List<BlockRegister> blocksToRegister = new ArrayList<>();
    private static List<ItemBase> itemsToRegister = new ArrayList<>();
    private static List<TERegister> teBlocksToRegister = new ArrayList<>();
    private static List<SoundEvent> soundsToRegister = new ArrayList<>();

    public static void register(BlockRegister... blocks) { blocksToRegister.addAll(Arrays.asList(blocks)); }

    public static void register(ItemBase... items) { itemsToRegister.addAll(Arrays.asList(items)); }

    public static void register(TERegister... tileEntityBlocks) { teBlocksToRegister.addAll(Arrays.asList(tileEntityBlocks)); }

    public static void register(SoundEvent... sounds) { soundsToRegister.addAll(Arrays.asList(sounds)); }

    public void registerItems(IForgeRegistry<Item> registry)
    {
        for(ItemBase i : itemsToRegister)
            registry.register(i);
        for(BlockRegister b : blocksToRegister)
            registry.register(b.getItemBlock());
        for(TERegister te : teBlocksToRegister)
            registry.register(te.getItemBlock());
    }

    public void registerBlocks(IForgeRegistry<Block> registry)
    {
        for(BlockRegister b : blocksToRegister)
            registry.register(b.getBlock());
        for(TERegister te : teBlocksToRegister)
        {
            registry.register(te.getBlock());
            GameRegistry.registerTileEntity(te.getTileEntityClass(), te.getRegistryName());
        }
    }

    public void registerItemModels()
    {
        for(ItemBase i : itemsToRegister)
            i.registerItemModel();
        for(BlockRegister b : blocksToRegister)
            b.registerItemModel();
        for(TERegister te : teBlocksToRegister)
            te.registerItemModel();
    }

    public void registerSounds(IForgeRegistry<SoundEvent> registry) { for(SoundEvent s : soundsToRegister) registry.register(s); }

    @Optional.Method(modid="waila")
    public void registerWailaProviders(IWailaRegistrar registry, IWailaDataProvider wdp)
    {
        for(BlockRegister b : blocksToRegister)
        {
            if(b instanceof IOrionInfoProvider)
            {
                registry.registerBodyProvider(wdp, b.getClass());
                Orion.log.info("Registered '" + b.getClass().getName() + "' to Waila");
            }
        }
        for(TERegister te : teBlocksToRegister)
        {
            if(te instanceof IOrionInfoProvider)
            {
                registry.registerBodyProvider(wdp, te.getClass());
                Orion.log.info("Registered '" + te.getClass().getName() + "' to Waila");
            }
            else if(isIOIP(te.getTileEntityClass().getInterfaces()))
            {
                registry.registerBodyProvider(wdp, te.getClass());
                Orion.log.info("Registered '" + te.getTileEntityClass().getName() + "' to Waila");
            }
        }
    }

    private boolean isIOIP(Class<?>[] interfaces)
    {
        for(Class<?> iface : interfaces)
            if(iface == IOrionInfoProvider.class)
                return true;
        return false;
    }
}
