package com.ultraflash.equi3ae.init;

import com.ultraflash.equi3ae.reference.Names;
import com.ultraflash.equi3ae.tileentity.TileEntityEmcFilter;
import com.ultraflash.equi3ae.utility.LogHelper;
import cpw.mods.fml.common.registry.GameRegistry;


public class TileEntities
{

    public static void init()
    {
        GameRegistry.registerTileEntity(TileEntityEmcFilter.class, Names.Blocks.EMCFILTER);
        LogHelper.info("Tile Init Complete");

    }


}
