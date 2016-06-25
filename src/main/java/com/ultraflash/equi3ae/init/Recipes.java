package com.ultraflash.equi3ae.init;

import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.ShapedOreRecipe;
import net.minecraftforge.oredict.ShapelessOreRecipe;

/**
 * Created by Benni on 20.06.2016.
 */
public class Recipes
{

    public static void init()
    {
        //shaped
        GameRegistry.addRecipe(new ShapedOreRecipe( new ItemStack(ModItems.mapleLeaf)," s ","sss"," s ",'s',"stickWood"));
        GameRegistry.addRecipe(new ShapelessOreRecipe( new ItemStack(ModBlocks.flag),new ItemStack(ModItems.mapleLeaf),new ItemStack(ModItems.mapleLeaf)));
        //shapeless
       // GameRegistry.addShapelessRecipe(new ItemStack(ModBlocks.flag),new ItemStack(ModItems.mapleLeaf),new ItemStack(ModItems.mapleLeaf));
        //GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.mapleLeaf)," s ","sss"," s ",'s',new ItemStack(Items.stick)));
    }


}
