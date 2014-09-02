package com.olee.forge.leapcontrol.client;

import java.io.File;

import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.KeyBinding;

import org.apache.logging.log4j.LogManager;

import com.olee.forge.leapcontrol.CommonProxy;
import com.olee.forge.leapcontrol.utility.SysUtils;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent.ClientTickEvent;

public class ClientProxy extends CommonProxy {

	public LeapController leapController;

	@Override
	public void initializationEvent(FMLInitializationEvent event) {
		super.initializationEvent(event);
		initializeLeapController();
	}

	private void initializeLeapController() {
		if (System.getProperty("os.arch").equals("amd64")) {
			SysUtils.addLibraryPath(System.getProperty("user.dir") + File.separatorChar + "LeapMotion");
			if (SysUtils.fileExistsInPath("Leap.dll"))
				if (SysUtils.fileExistsInPath("LeapJava.dll"))
					try {
						leapController = new LeapController();
						FMLCommonHandler.instance().bus().register(this);
					} catch (NoClassDefFoundError e) {
						LogManager.getLogger().info("LeapJava.jar not found! (" + e.getMessage() + ")");
					}
				else
					LogManager.getLogger().info("LeapJava.dll not found!");
			else
				LogManager.getLogger().info("Leap.dll not found!");
		} else
			LogManager.getLogger().info("LeapMotion control only supported for Windows x64!");
	}

	@SubscribeEvent
	public void clientTick(ClientTickEvent event) {
		if (leapController != null) {
			if (leapController.isActive()) {
				KeyBinding.setKeyBindState(Minecraft.getMinecraft().gameSettings.keyBindForward.getKeyCode(), leapController.zdir > 0);
				KeyBinding.setKeyBindState(Minecraft.getMinecraft().gameSettings.keyBindBack.getKeyCode(), leapController.zdir < 0);
				KeyBinding.setKeyBindState(Minecraft.getMinecraft().gameSettings.keyBindRight.getKeyCode(), leapController.xdir > 0);
				KeyBinding.setKeyBindState(Minecraft.getMinecraft().gameSettings.keyBindLeft.getKeyCode(), leapController.xdir < 0);
			} else if (leapController.isStoppedActivity()) {
				KeyBinding.setKeyBindState(Minecraft.getMinecraft().gameSettings.keyBindForward.getKeyCode(), false);
				KeyBinding.setKeyBindState(Minecraft.getMinecraft().gameSettings.keyBindBack.getKeyCode(), false);
				KeyBinding.setKeyBindState(Minecraft.getMinecraft().gameSettings.keyBindRight.getKeyCode(), false);
				KeyBinding.setKeyBindState(Minecraft.getMinecraft().gameSettings.keyBindLeft.getKeyCode(), false);
			}
			leapController.resetStoppedActivity();
		}
	}

}
