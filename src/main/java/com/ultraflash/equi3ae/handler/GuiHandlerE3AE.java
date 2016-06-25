package com.ultraflash.equi3ae.handler;


import com.ultraflash.equi3ae.client.gui.Inventory.GuiEmcFilter;
import com.ultraflash.equi3ae.inventory.ContainerE3AE;
import com.ultraflash.equi3ae.inventory.ContainerEmcFilter;
import com.ultraflash.equi3ae.tileentity.TileEntityEmcFilter;
import cpw.mods.fml.common.network.IGuiHandler;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

public class GuiHandlerE3AE implements IGuiHandler
{
    public static final int MOD_TILE_ENTITY_GUI = 0;



    @Override
    public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        if (ID == MOD_TILE_ENTITY_GUI)
            return new ContainerEmcFilter(player.inventory, (TileEntityEmcFilter) world.getTileEntity(x, y, z));

        return null;
    }

    @Override
    public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        if (ID == MOD_TILE_ENTITY_GUI)
        {
            return new GuiEmcFilter(player.inventory ,(TileEntityEmcFilter) world.getTileEntity(x, y, z));
           // TileEntityEmcFilter tileEntityEmcFilter = (TileEntityEmcFilter) world.getTileEntity(x, y, z);
           // return new GuiEmcFilter(player.inventory, tileEntityEmcFilter);

        }
        return null;
    }


}
