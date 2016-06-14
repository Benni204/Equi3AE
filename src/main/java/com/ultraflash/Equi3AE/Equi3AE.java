package com.ultraflash.Equi3AE;

import com.ultraflash.Equi3AE.handler.ConfigurationHandler;
import com.ultraflash.Equi3AE.reference.Reference;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.InstanceFactory;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;

@Mod(modid = Reference.MOD_ID,name=Reference.MOD_NAME,version=Reference.MOD_NAME,guiFactory=Reference.GUI_FACTORY_CLASS)
public class Equi3AE 
{
	@Mod.Instance("Equi3AE")
	public static Equi3AE instance;
	
	
	@Mod.EventHandler
	public	void PreInit(FMLPreInitializationEvent event)
	{
	 ConfigurationHandler.init(event.getSuggestedConfigurationFile());
	}
	
	
	@Mod.EventHandler
	public	void Init(FMLInitializationEvent event)
	{
	
	}
	
	
	@Mod.EventHandler
	public	void PostInit(FMLPostInitializationEvent event)
	{
	
	}

}
