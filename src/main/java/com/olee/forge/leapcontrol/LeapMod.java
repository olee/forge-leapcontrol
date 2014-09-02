package com.olee.forge.leapcontrol;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLLoadCompleteEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLServerStartingEvent;
import cpw.mods.fml.common.event.FMLServerStoppedEvent;

@Mod(modid = LeapMod.MODID, version = LeapMod.VERSION)
public class LeapMod {

	// The instance of your mod that Forge uses.
	@Instance(value = LeapMod.MODID)
	public static LeapMod instance;

	public static final String MODID = "leapcontrol";
	public static final String VERSION = "0.1";

	@SidedProxy(clientSide = "com.olee.forge.leapcontrol.client.ClientProxy", serverSide = "com.olee.forge.leapcontrol.CommonProxy")
	public static CommonProxy proxy;

	@EventHandler
	public void initializationEvent(FMLInitializationEvent event) {
		proxy.initializationEvent(event);
	}

	@EventHandler
	public void postInitializationEvent(FMLPostInitializationEvent event) {
		proxy.postInitializationEvent(event);
	}

	@EventHandler
	public void loadCompleteEvent(FMLLoadCompleteEvent event) {
		proxy.loadCompleteEvent(event);
	}

	@EventHandler
	public void serverStartEvent(FMLServerStartingEvent event) {
		proxy.serverStartEvent(event);
	}

	@EventHandler
	public void serverStoppedEvent(FMLServerStoppedEvent event) {
		proxy.serverStoppedEvent(event);
	}

}
