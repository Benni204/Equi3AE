package com.ultraflash.equi3ae.init;

import com.ultraflash.equi3ae.block.BlockE3AE;
import com.ultraflash.equi3ae.block.BlockEmcFilter;
import com.ultraflash.equi3ae.block.BlockFlag;

import com.ultraflash.equi3ae.reference.Names;
import com.ultraflash.equi3ae.reference.Reference;
import com.ultraflash.equi3ae.tileentity.TileEntityEmcFilter;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import com.ultraflash.equi3ae.block.BlockTileEntityE3AE;
import com.ultraflash.equi3ae.tileentity.TileEntityE3AE;
import cpw.mods.fml.common.registry.GameRegistry;
import scala.tools.nsc.backend.icode.BasicBlocks;

@GameRegistry.ObjectHolder(Reference.MOD_ID)
public class ModBlocks {

    public static final BlockE3AE flag= new BlockFlag();
    public static final BlockTileEntityE3AE emcfilter= new BlockEmcFilter();

    public static void init()
        {
            GameRegistry.registerBlock(flag,"flag");
            GameRegistry.registerBlock(emcfilter, Names.Blocks.EMCFILTER);

        }
}
