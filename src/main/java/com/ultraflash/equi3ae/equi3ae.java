package com.ultraflash.equi3ae;

import com.ultraflash.equi3ae.client.handler.KeyInputEventHandler;
import com.ultraflash.equi3ae.handler.ConfigurationHandler;
import com.ultraflash.equi3ae.init.ModBlocks;
import com.ultraflash.equi3ae.init.Recipes;
import com.ultraflash.equi3ae.init.TileEntities;
import com.ultraflash.equi3ae.proxy.IProxy;
import com.ultraflash.equi3ae.reference.Reference;
import com.ultraflash.equi3ae.init.ModItems;

import com.ultraflash.equi3ae.utility.LogHelper;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.gameevent.InputEvent;
import net.minecraftforge.oredict.OreDictionary;

@Mod(modid = Reference.MOD_ID,name=Reference.MOD_NAME,version=Reference.MOD_NAME,guiFactory=Reference.GUI_FACTORY_CLASS)
public class equi3ae
{
    @Mod.Instance("equi3ae")
    public static equi3ae instance;

    @SidedProxy(clientSide = Reference.CLIENT_PROXY_CLASS, serverSide = Reference.SERVER_PROXY_CLASS)
    public static IProxy proxy;

    @Mod.EventHandler
    public	void PreInit(FMLPreInitializationEvent event)
    {
        ConfigurationHandler.init(event.getSuggestedConfigurationFile());
        FMLCommonHandler.instance().bus().register(new ConfigurationHandler());


        proxy.registerKeyBindings();

        ModItems.init();
        ModBlocks.init();

       LogHelper.info("PreInit Complete");
    }


    @Mod.EventHandler
    public	void Init(FMLInitializationEvent event)
    {
        FMLCommonHandler.instance().bus().register(new KeyInputEventHandler());
        // Initialize mod tile entities
        TileEntities.init();

        Recipes.init();
        proxy.registerEventHandlers();

        LogHelper.info("Init Complete");
    }


    @Mod.EventHandler
    public	void PostInit(FMLPostInitializationEvent event)
    {

        LogHelper.info("PostInit Complete");
        /*
        for (String oreName : OreDictionary.getOreNames())
        {
            LogHelper.info(oreName);
            OreDictionary.getOres("stickWood");
        }
        */
    }

}