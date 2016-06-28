package com.ultraflash.equi3ae.init;

import appeng.api.AEApi;
import com.ultraflash.equi3ae.fluid.emcF;
import com.ultraflash.equi3ae.item.*;
import com.ultraflash.equi3ae.reference.Reference;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;


@GameRegistry.ObjectHolder(Reference.MOD_ID)
public class ModItems
{
    public static final ItemE3AE mapleLeaf = new ItemMapleLeaf();
    public static final ItemE3AE distortedSingularity = new ItemDistortedSingularity();
    public static final ItemE3AE emcstoragecell = new ItemEMCStorageCell(1);
    public static final ItemE3AE rawemc = new ItemRawEMC();
   // public static final Fluid emcF = new emcF();

    public static void init()
    {
        GameRegistry.registerItem(mapleLeaf,"mapleLeaf");
        GameRegistry.registerItem(distortedSingularity,"distortedSingularity");
        GameRegistry.registerItem( emcstoragecell,"emcstoragecell");
        GameRegistry.registerItem( rawemc,"rawemc");

    }
}