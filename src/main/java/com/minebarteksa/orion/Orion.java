package com.minebarteksa.orion;

import com.minebarteksa.orion.debugtools.DebugBlock;
import com.minebarteksa.orion.debugtools.MouseDebug;
import com.minebarteksa.orion.events.OrionBlockEvents;
import com.minebarteksa.orion.events.OrionMouseEvents;
import net.minecraft.block.Block;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.client.event.MouseEvent;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.client.event.ModelRegistryEvent;
import com.minebarteksa.orion.debugtools.ParticleTester;
import com.minebarteksa.orion.debugtools.RotationTester;
import net.minecraft.item.Item;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import com.minebarteksa.orion.proxy.CommonProxy;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.Mod;
import org.apache.logging.log4j.Logger;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

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

	public static Logger log;
	public static OrionRegistry registry = new OrionRegistry();
	public static ParticleTester pt = new ParticleTester();
	public static RotationTester rt = new RotationTester();
	public static MouseDebug md = new MouseDebug();
	public static DebugBlock db = new DebugBlock();

	@Mod.EventHandler
	public void preInit(FMLPreInitializationEvent ev)
	{
		log = ev.getModLog();
		proxy.preInit(ev);
		OrionRegistry.register(pt, rt, md);
		OrionRegistry.register(db);
	}

	@Mod.EventHandler
	public void init(FMLInitializationEvent ev)
	{
		proxy.init(ev);
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
