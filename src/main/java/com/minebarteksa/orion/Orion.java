package com.minebarteksa.orion;

import net.minecraftforge.fml.common.Mod;
import org.apache.logging.log4j.Logger;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

@Mod(modid = Orion.ModID, name = Orion.ModName, version = Orion.Version)
public class Orion
{
	public static final String ModID = "liborion";
	public static final String ModName = "LibOrion";
	public static final String Version = "1.0";

	public static Logger log;

	public void preInit(FMLPreInitializationEvent ev)
	{
		log = ev.getModLog();
	}
}
