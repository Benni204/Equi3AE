package com.ultraflash.equi3ae.init;

import com.ultraflash.equi3ae.block.*;

import com.ultraflash.equi3ae.grid.EmcScreen;
import com.ultraflash.equi3ae.grid.GridEmcCache;
import com.ultraflash.equi3ae.reference.Names;
import com.ultraflash.equi3ae.reference.Reference;
import com.ultraflash.equi3ae.tileentity.TileEntityEmcFilter;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import com.ultraflash.equi3ae.tileentity.TileEntityE3AE;
import cpw.mods.fml.common.registry.GameRegistry;
import scala.tools.nsc.backend.icode.BasicBlocks;

//@GameRegistry.ObjectHolder(Reference.MOD_ID)
public class ModBlocks {

    public static final BlockE3AE flag= new BlockFlag();
    public static final BlockE3AE emcstoragebase= new BlockEmcStorageBase();
    public static final BlockTileEntityE3AE emcfilter= new BlockEmcFilter();
    public static final BlockTileEntityE3AE emcconverter= new BlockEmcConverter();

    public static final EmcScreen emcmonitaor= new EmcScreen();

    public static void init() {
        GameRegistry.registerBlock(flag, "flag");
        GameRegistry.registerBlock(emcfilter, Names.Blocks.EMCFILTER);
        GameRegistry.registerBlock(emcconverter, Names.Blocks.EMCCONVERTER);
        GameRegistry.registerBlock(emcstoragebase, Names.Blocks.EMCSTORAGEBASE);

    }
}
