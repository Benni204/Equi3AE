package com.ultraflash.equi3ae;

import appeng.api.AEApi;
import com.ultraflash.equi3ae.api.IEmcGrid;
import com.ultraflash.equi3ae.client.handler.KeyInputEventHandler;
import com.ultraflash.equi3ae.fluid.emcF;
import com.ultraflash.equi3ae.grid.GridEmcCache;
import com.ultraflash.equi3ae.handler.ConfigurationHandler;
import com.ultraflash.equi3ae.init.*;
import com.ultraflash.equi3ae.proxy.IProxy;
import com.ultraflash.equi3ae.reference.Reference;

import com.ultraflash.equi3ae.utility.LogHelper;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.gameevent.InputEvent;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
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
        ModFluids.init();


       LogHelper.info("PreInit Complete");
    }


    @Mod.EventHandler
    public	void Init(FMLInitializationEvent event)
    {
        FMLCommonHandler.instance().bus().register(new KeyInputEventHandler());
        // Initialize mod tile entities
        TileEntities.init();
        AEApi.instance().registries().gridCache().registerGridCache(IEmcGrid.class, GridEmcCache.class );

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