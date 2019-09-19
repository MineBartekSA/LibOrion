package com.minebarteksa.orion;

import com.minebarteksa.orion.debugtools.*;
import com.minebarteksa.orion.events.OrionBlockEvents;
import com.minebarteksa.orion.events.OrionMouseEvents;
import com.minebarteksa.orion.multiblock.OrionMultiBlocks;
import com.minebarteksa.orion.network.OrionPacketHandler;
import com.minebarteksa.orion.network.PacketRegister;
import com.minebarteksa.orion.potion.OrionPotion;
import com.minebarteksa.orion.proxy.CommonProxy;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionType;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.event.MouseEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLInterModComms;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import org.apache.logging.log4j.Logger;

@Mod(modid = Orion.ModID, name = Orion.ModName, version = Orion.Version)
public class Orion
{
    public static final String ModID = "liborion";
    public static final String ModName = "LibOrion";
    public static final String Version = "1.0";

    @Mod.Instance(ModID)
    public static Orion instance;

    @SidedProxy(clientSide = "com.minebarteksa.orion.proxy.ClientProxy", serverSide = "com.minebarteksa.orion.proxy.CommonProxy")
    public static CommonProxy proxy;

    static
    {
        FluidRegistry.enableUniversalBucket();
    }

    public static Logger log;
    //public static RenderHandler renderers;
    public static OrionRegistry registry = new OrionRegistry();
    public static ParticleTester pt = new ParticleTester();
    public static RotationTester rt = new RotationTester();
    public static MouseDebug md = new MouseDebug();
    public static DebugBlock db = new DebugBlock();
    public static InfoProviderTester ipt = new InfoProviderTester();
    public static TessellatorTest tt = new TessellatorTest();
    public static TessellatorTest.TESR ttt = new TessellatorTest.TESR();
    public static OrionPotion tp;

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent ev)
    {
        log = ev.getModLog();
        proxy.preInit(ev);
        FMLInterModComms.sendFunctionMessage("theoneprobe", "getTheOneProbe", "com.minebarteksa.orion.integrations.TOPIntegration$GetTheOneProbe");
        OrionRegistry.register(pt, rt, md, ipt);
        OrionRegistry.register(db, tt);
        OrionRegistry.register(ttt);
        OrionRegistry.register(tp = new OrionPotion());
        OrionRegistry.register(new PacketRegister(OrionPacketHandler.PotionPacket.PotionPacketHandler.class, OrionPacketHandler.PotionPacket.class, Side.SERVER));
        OrionMultiBlocks.autoRegister();
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent ev)
    {
        proxy.init(ev);
        registry.registerPackets(OrionPacketHandler.INSTANCE);
        registry.registerFluids();
    }

    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent ev)
    {
        proxy.postInit(ev);
    }

    @Mod.EventBusSubscriber
    public static class EventBusHandler
    {
        @SubscribeEvent
        public static void registerItems(RegistryEvent.Register<Item> ev) { registry.registerItems(ev.getRegistry()); }

        @SubscribeEvent
        public static void registerItems(ModelRegistryEvent ev) { registry.registerItemModels(); }

        @SubscribeEvent
        public static void registerBlocks(RegistryEvent.Register<Block> ev) { registry.registerBlocks(ev.getRegistry()); }

        @SubscribeEvent
        public static void registerSounds(RegistryEvent.Register<SoundEvent> ev) { registry.registerSounds(ev.getRegistry()); }

        @SubscribeEvent
        public static void registerPotions(RegistryEvent.Register<Potion> ev) { registry.registerPotions(ev.getRegistry()); }

        @SubscribeEvent
        public static void registerPotionTypes(RegistryEvent.Register<PotionType> ev) { registry.registerPotionTypes(ev.getRegistry()); }

        @SubscribeEvent
        public static void onBlockBreak(BlockEvent.BreakEvent ev) { OrionBlockEvents.BB.invokeWithValue(ev.getPos()); }

        @SubscribeEvent
        public static void onMouseEvent(MouseEvent ev) //Runs only in game!
        {
            if(ev.getButton() != -1)
            {
                if(ev.getButton() == OrionMouseEvents.MouseButtons.LeftClick.Type())
                    OrionMouseEvents.LC.invokeListeners();
                if(ev.getButton() == OrionMouseEvents.MouseButtons.RightClick.Type())
                    OrionMouseEvents.RC.invokeListeners();
                if(ev.getButton() == OrionMouseEvents.MouseButtons.MiddleClick.Type())
                    OrionMouseEvents.MC.invokeListeners();
            }
            if(ev.getDwheel() != 0)
                OrionMouseEvents.SE.invokeWithValue(ev.getDwheel());
        }
    }
}
