package com.ultraflash.equi3ae.handler;


import com.ultraflash.equi3ae.client.gui.Inventory.GuiEmcConverter;
import com.ultraflash.equi3ae.client.gui.Inventory.GuiEmcFilter;
import com.ultraflash.equi3ae.inventory.ContainerConverter;
import com.ultraflash.equi3ae.inventory.ContainerE3AE;
import com.ultraflash.equi3ae.inventory.ContainerEmcFilter;
import com.ultraflash.equi3ae.reference.GUIs;
import com.ultraflash.equi3ae.tileentity.TileEntityEmcConverter;
import com.ultraflash.equi3ae.tileentity.TileEntityEmcFilter;
import cpw.mods.fml.common.network.IGuiHandler;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

public class GuiHandlerE3AE implements IGuiHandler
{
    public static final int MOD_TILE_ENTITY_GUI = 0;



    @Override
    public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        if (ID == GUIs.EMCFILTER.ordinal()) {
            return new ContainerEmcFilter(player.inventory, (TileEntityEmcFilter) world.getTileEntity(x, y, z));
        }else if  (ID== GUIs.EMCCONVERTER.ordinal()) {
            return new ContainerConverter(player.inventory, (TileEntityEmcConverter) world.getTileEntity(x, y, z));
        }
        return null;
    }

    @Override
    public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        if (ID == GUIs.EMCFILTER.ordinal())
        {
            return new GuiEmcFilter(player.inventory ,(TileEntityEmcFilter) world.getTileEntity(x, y, z));
        }else if  (ID== GUIs.EMCCONVERTER.ordinal()) {
            return new GuiEmcConverter(player.inventory ,(TileEntityEmcConverter) world.getTileEntity(x, y, z));
        }

        return null;
    }


}
