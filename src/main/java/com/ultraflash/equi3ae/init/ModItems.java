package com.ultraflash.equi3ae.init;

import com.ultraflash.equi3ae.item.ItemE3AE;
import com.ultraflash.equi3ae.item.ItemMapleLeaf;
import com.ultraflash.equi3ae.item.ItemDistortedSingularity;
import com.ultraflash.equi3ae.reference.Reference;
import cpw.mods.fml.common.registry.GameRegistry;


@GameRegistry.ObjectHolder(Reference.MOD_ID)
public class ModItems
{
    public static final ItemE3AE mapleLeaf = new ItemMapleLeaf();
    public static final ItemE3AE distortedSingularity = new ItemDistortedSingularity();

    public static void init()
    {
        GameRegistry.registerItem(mapleLeaf,"mapleLeaf");
        GameRegistry.registerItem(distortedSingularity,"distortedSingularity");
    }
}