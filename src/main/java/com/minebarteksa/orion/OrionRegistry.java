package com.minebarteksa.orion;

import com.minebarteksa.orion.blocks.BlockBase;
import com.minebarteksa.orion.blocks.BlockRegister;
import com.minebarteksa.orion.blocks.TERegister;
import com.minebarteksa.orion.fluids.FluidBucket;
import com.minebarteksa.orion.integrations.IOrionInfoProvider;
import com.minebarteksa.orion.items.ItemBase;
import com.minebarteksa.orion.network.PacketRegister;
import com.minebarteksa.orion.particle.OrionParticles;
import com.minebarteksa.orion.particle.ParticleRegister;
import com.minebarteksa.orion.potion.MixRegister;
import com.minebarteksa.orion.render.IEntityRegister;
import mcp.mobius.waila.api.IWailaDataProvider;
import mcp.mobius.waila.api.IWailaRegistrar;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.Entity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionType;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.Optional;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.registries.IForgeRegistry;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class OrionRegistry
{
    private static List<Block> blocks = new ArrayList<>();
    private static List<ItemBase> items = new ArrayList<>();
    private static List<SoundEvent> sounds = new ArrayList<>();
    private static List<Potion> potions = new ArrayList<>();
    private static List<PacketRegister> packets = new ArrayList<>();
    private static List<Fluid> fluids = new ArrayList<>();
    private static List<Entity> entities = new ArrayList<>();
    private static List<TESRRegister> tesrs = new ArrayList<>();
    private static List<Enchantment> enchantments = new ArrayList<>();
    private static List<ParticleRegister> particles = new ArrayList<>();
    private int nextPacketID;

    /**
     * Method for adding Blocks to registration.<br>
     * <b>OrionRegistry system uses {@link BlockRegister} and {@link TERegister} interfaces
     * to register Blocks, so they should implement one of them!</b><br>
     * Recommend is to extend one of the {@link BlockBase } classes.<br>
     * Registration of a Block without these interfaces will still be attempted.
     * @param blocks should implement {@link BlockRegister} or {@link TERegister}
     */
    public static void register(Block... blocks) { OrionRegistry.blocks.addAll(Arrays.asList(blocks)); }

    public static void register(ItemBase... items) { OrionRegistry.items.addAll(Arrays.asList(items)); }

    public static void register(SoundEvent... sounds) { OrionRegistry.sounds.addAll(Arrays.asList(sounds)); }

    public static void register(Potion... potions) { OrionRegistry.potions.addAll(Arrays.asList(potions)); }

    public static void register(PacketRegister... packets) { OrionRegistry.packets.addAll(Arrays.asList(packets)); }

    public static void register(TESRRegister... tesrs) { OrionRegistry.tesrs.addAll(Arrays.asList(tesrs)); }

    public static void register(Entity... entitys) { entities.addAll(Arrays.asList(entitys)); }

    public static void register(Fluid... fluids) { OrionRegistry.fluids.addAll(Arrays.asList(fluids)); } // LOL No example on how to add one xD

    public static void register(Enchantment... enchantments) { OrionRegistry.enchantments.addAll(Arrays.asList(enchantments)); }

    public static void register(ParticleRegister... particles) { OrionRegistry.particles.addAll(Arrays.asList(particles)); }

    public static void registerOreDictionary(String oreDictionaryName, Block... blocks) { for(Block b : blocks) OreDictionary.registerOre(oreDictionaryName, b); }

    public static void registerOreDictionary(String oreDictionaryName, Item... items) { for(Item i : items) OreDictionary.registerOre(oreDictionaryName, i); }

    public void registerItems(IForgeRegistry<Item> registry)
    {
        for(ItemBase i : items)
            registry.register(i);
        for(Block b : blocks)
            if(b instanceof BlockRegister)
                registry.register(((BlockRegister)b).getItemBlock());
            else
                registry.register(new ItemBlock(b).setRegistryName(b.getRegistryName()));
    }

    public void registerBlocks(IForgeRegistry<Block> registry)
    {
        for(Block b : blocks)
        {
            registry.register(b);
            if(b instanceof TERegister)
                GameRegistry.registerTileEntity(((TERegister)b).getTileEntityClass(), ((TERegister)b).getRegistryName());
        }
    }

    @SideOnly(Side.CLIENT)
    public void registerItemModels()
    {
        for(ItemBase i : items)
            i.registerItemModel();
        for(Block b : blocks)
            if(b instanceof BlockRegister)
                ((BlockRegister)b).registerItemModel();
            else
                ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(b), 0, new ModelResourceLocation(b.getRegistryName().toString(), "inventory"));
    }

    public void registerSounds(IForgeRegistry<SoundEvent> registry) { for(SoundEvent s : sounds) registry.register(s); }

    public void registerPotions(IForgeRegistry<Potion> registry) { for(Potion p : potions) registry.register(p); }

    public void registerPotionTypes(IForgeRegistry<PotionType> registry)
    {
        for(Potion p : potions)
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
        for(PacketRegister pr : packets)
            instance.registerMessage(pr.handler, pr.message, nextPacketID++, pr.side);
    }

    public void registerFluids()
    {
        for(Fluid f : fluids)
        {
            FluidRegistry.registerFluid(f);
            if(f instanceof FluidBucket)
                FluidRegistry.addBucketForFluid(f);
        }
    }

    public void registerTESRS()
    {
        for(TESRRegister t : tesrs)
            ClientRegistry.bindTileEntitySpecialRenderer(t.getTileEntityClass(), t.getTESR());
    }

    public void registerEnchantments(IForgeRegistry<Enchantment> registry)
    {
        for(Enchantment e : enchantments)
            registry.register(e);
    }

    @Optional.Method(modid="waila")
    public void registerWailaProviders(IWailaRegistrar registry, IWailaDataProvider wdp)
    {
        for(Block b : blocks)
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

    public void registerEntityRenderers()
    {
        for(Entity e : entities)
            if(e instanceof IEntityRegister)
                RenderingRegistry.registerEntityRenderingHandler(e.getClass(), ((IEntityRegister) e).getRenderer());
    }

    private boolean isIOIP(Class<?>[] interfaces)
    {
        for(Class<?> iface : interfaces)
            if(iface == IOrionInfoProvider.class)
                return true;
        return false;
    }

    public void registerParticles()
    {
        for (ParticleRegister p : particles)
            OrionParticles.registerParticle(p.name, p.factory);
    }
}
