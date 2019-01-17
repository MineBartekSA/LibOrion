package com.minebarteksa.orion;

import com.minebarteksa.orion.blocks.BlockBase;
import com.minebarteksa.orion.blocks.BlockRegister;
import com.minebarteksa.orion.blocks.TERegister;
import com.minebarteksa.orion.fluids.FluidBucket;
import com.minebarteksa.orion.integrations.IOrionInfoProvider;
import com.minebarteksa.orion.items.ItemBase;
import com.minebarteksa.orion.network.PacketRegister;
import com.minebarteksa.orion.potion.MixRegister;
import mcp.mobius.waila.api.IWailaDataProvider;
import mcp.mobius.waila.api.IWailaRegistrar;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionType;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fml.common.Optional;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.registries.IForgeRegistry;
import java.util.*;

public class OrionRegistry
{
    private static List<Block> blocksToRegister = new ArrayList<>();
    private static List<ItemBase> itemsToRegister = new ArrayList<>();
    private static List<SoundEvent> soundsToRegister = new ArrayList<>();
    private static List<Potion> potionsToRegister = new ArrayList<>();
    private static List<PacketRegister> packetsToRegister = new ArrayList<>();
    private static List<Fluid> fluidsToRegister = new ArrayList<>();
    private int nextPacketID;

    /**
     * Method for adding Blocks to be registered.<br>
     * <b>OrionRegistry system uses {@link BlockRegister} and {@link TERegister} interfaces
     * so Blocks should implement one of them!</b><br>
     * I recommend just extending one of the {@link BlockBase } class.<br>
     * If you dose not implement those interfaces the system will attempt to register those.
     * @param blocks should implement BlockRegister or TERegister
     */
    public static void register(Block... blocks) { blocksToRegister.addAll(Arrays.asList(blocks)); }

    public static void register(ItemBase... items) { itemsToRegister.addAll(Arrays.asList(items)); }

    public static void register(SoundEvent... sounds) { soundsToRegister.addAll(Arrays.asList(sounds)); }

    public static void register(Potion... potions) { potionsToRegister.addAll(Arrays.asList(potions)); }

    public static void register(PacketRegister... packets) { packetsToRegister.addAll(Arrays.asList(packets)); }

    public static void register(Fluid... fluids) { fluidsToRegister.addAll(Arrays.asList(fluids)); } // LOL No example on how to add one xD

    public static void registerOreDictionary(String oreDictionaryName, BlockRegister... blocks) { for(BlockRegister b : blocks) OreDictionary.registerOre(oreDictionaryName, b.getBlock()); }

    public static void registerOreDictionary(String oreDictionaryName, ItemBase... items) { for(ItemBase i : items) OreDictionary.registerOre(oreDictionaryName, i); }

    public void registerItems(IForgeRegistry<Item> registry)
    {
        for(ItemBase i : itemsToRegister)
            registry.register(i);
        for(Block b : blocksToRegister)
            if(b instanceof BlockRegister)
                registry.register(((BlockRegister)b).getItemBlock());
            else
                registry.register(new ItemBlock(b).setRegistryName(b.getRegistryName()));
    }

    public void registerBlocks(IForgeRegistry<Block> registry)
    {
        for(Block b : blocksToRegister)
        {
            registry.register(b);
            if(b instanceof TERegister)
                GameRegistry.registerTileEntity(((TERegister)b).getTileEntityClass(), ((TERegister)b).getRegistryName());
        }
    }

    @SideOnly(Side.CLIENT)
    public void registerItemModels()
    {
        for(ItemBase i : itemsToRegister)
            i.registerItemModel();
        for(Block b : blocksToRegister)
            if(b instanceof BlockRegister)
                ((BlockRegister)b).registerItemModel();
            else
                ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(b), 0, new ModelResourceLocation(b.getRegistryName().toString(), "inventory"));
    }

    public void registerSounds(IForgeRegistry<SoundEvent> registry) { for(SoundEvent s : soundsToRegister) registry.register(s); }

    public void registerPotions(IForgeRegistry<Potion> registry) { for(Potion p : potionsToRegister) registry.register(p); }

    public void registerPotionTypes(IForgeRegistry<PotionType> registry)
    {
        for(Potion p : potionsToRegister)
        {
            if(p instanceof MixRegister)
            {
                PotionType pt = ((MixRegister) p).getPotionType();
                PotionType lpt = ((MixRegister) p).getLongPotionType();
                if(pt != null)
                    registry.register(pt);
                if(lpt != null)
                    registry.register(lpt);
                ((MixRegister) p).registerPotionMix();
            }
        }
    }

    public void registerPackets(SimpleNetworkWrapper instance)
    {
        for(PacketRegister pr : packetsToRegister)
            instance.registerMessage(pr.handler, pr.message, nextPacketID++, pr.side);
    }

    public void registerFluids()
    {
        for(Fluid f : fluidsToRegister)
        {
            FluidRegistry.registerFluid(f);
            if(f instanceof FluidBucket)
                FluidRegistry.addBucketForFluid(f);
        }
    }

    @Optional.Method(modid="waila")
    public void registerWailaProviders(IWailaRegistrar registry, IWailaDataProvider wdp)
    {
        for(Block b : blocksToRegister)
        {
            if(b instanceof IOrionInfoProvider)
            {
                registry.registerBodyProvider(wdp, b.getClass());
                Orion.log.info("Registered '" + b.getClass().getName() + "' to Waila");
            }
            else if(b instanceof TERegister && isIOIP(((TERegister)b).getTileEntityClass().getInterfaces()))
            {
                registry.registerBodyProvider(wdp, b.getClass());
                Orion.log.info("Registered '" + ((TERegister)b).getTileEntityClass().getName() + "' to Waila");
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
