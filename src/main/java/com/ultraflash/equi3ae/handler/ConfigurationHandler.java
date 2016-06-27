package com.ultraflash.equi3ae.handler;

import java.io.File;

import com.ultraflash.equi3ae.reference.Reference;

import cpw.mods.fml.client.event.ConfigChangedEvent;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.common.config.Property;

public class ConfigurationHandler 
{
	public static Configuration configuration;
	public static boolean testValue =false;
	public static double powerConverterIdle;
	public static double powerConverterActive;
	public static double emcPerTick;
	
 public static void init(File configFile)
 {
	
	 if (configuration == null)
	 {
		  configuration =new Configuration(configFile);
		  loadConfiguration();
		  
	 }
	
 } 
	 
 private static void loadConfiguration()
 {
	 testValue = configuration.get(Configuration.CATEGORY_GENERAL, "testValue",false,"Example Value").getBoolean(true);
	 powerConverterIdle =  configuration.get("PowerUsage", "PowerDrain_IDLE", 0.0).getDouble(0.0);
	 powerConverterActive =  configuration.get("PowerUsage", "PowerDrain_perEMC", 0.025).getDouble(0.025);
	 emcPerTick =  configuration.get("EMC", "emcPerTick", 2).getDouble(2);
	 
	 if(configuration.hasChanged())
	 {
		configuration.save(); 
	 }
 }
	
 
 @SubscribeEvent
 public void onConfigruationChangedEvent(ConfigChangedEvent.OnConfigChangedEvent event)
 {
	 if(event.modID.equalsIgnoreCase(Reference.MOD_ID))
	 {
		 //resync configs
		 loadConfiguration();
	 }
 }
 
 
 
 
}
