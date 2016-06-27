package com.ultraflash.equi3ae.init;

import appeng.api.AEApi;
import appeng.api.IAppEngApi;
import com.ultraflash.equi3ae.reference.Names;
import com.ultraflash.equi3ae.tileentity.TileEmcStorageCell;
import com.ultraflash.equi3ae.tileentity.TileEntityEmcConverter;
import com.ultraflash.equi3ae.tileentity.TileEntityEmcFilter;
import com.ultraflash.equi3ae.utility.LogHelper;
import cpw.mods.fml.common.registry.GameRegistry;


public class TileEntities
{

    public static void init()
    {
        GameRegistry.registerTileEntity(TileEntityEmcFilter.class, Names.Blocks.EMCFILTER);
        GameRegistry.registerTileEntity(TileEntityEmcConverter.class, Names.Blocks.EMCCONVERTER);
      //  GameRegistry.registerTileEntity(TileEmcStorageCell.class, Names.Items.EMCSTORAGECELL+".4k");
        LogHelper.info("Tile Init Complete");

        IAppEngApi api = AEApi.instance();

        api.registries().movable().whiteListTileEntity(TileEmcStorageCell.class);



    }


}
