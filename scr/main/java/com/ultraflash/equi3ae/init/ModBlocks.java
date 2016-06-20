package com.ultraflash.equi3ae.init;

import com.ultraflash.equi3ae.block.BlockE3AE;
import com.ultraflash.equi3ae.block.BlockFlag;
import cpw.mods.fml.common.registry.GameRegistry;

/**
 * Created by Benni on 20.06.2016.
 */
public class ModBlocks {

    public static final BlockE3AE flag= new BlockFlag();

        public static void inti()
        {
            GameRegistry.registerBlock(flag,"flag");
        }
}
