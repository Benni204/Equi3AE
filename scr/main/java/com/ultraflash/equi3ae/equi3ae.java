package com.ultraflash.equi3ae;

import com.ultraflash.equi3ae.handler.ConfigurationHandler;
import com.ultraflash.equi3ae.init.ModBlocks;
import com.ultraflash.equi3ae.reference.Reference;
import com.ultraflash.equi3ae.init.ModItems;

import com.ultraflash.equi3ae.utility.LogHelper;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;

@Mod(modid = Reference.MOD_ID,name=Reference.MOD_NAME,version=Reference.MOD_NAME,guiFactory=Reference.GUI_FACTORY_CLASS)
public class equi3ae
{
    @Mod.Instance("equi3ae")
    public static equi3ae instance;


    @Mod.EventHandler
    public	void PreInit(FMLPreInitializationEvent event)
    {
        ConfigurationHandler.init(event.getSuggestedConfigurationFile());
        FMLCommonHandler.instance().bus().register(new ConfigurationHandler());
        ModItems.init();
        ModBlocks.inti();
       LogHelper.info("PreInit Complete");
    }


    @Mod.EventHandler
    public	void Init(FMLInitializationEvent event)
    {
        LogHelper.info("Init Complete");
    }


    @Mod.EventHandler
    public	void PostInit(FMLPostInitializationEvent event)
    {
        LogHelper.info("PostInit Complete");
    }

}