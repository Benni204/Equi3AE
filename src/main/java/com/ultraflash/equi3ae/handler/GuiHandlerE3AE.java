package com.ultraflash.equi3ae.handler;


import appeng.api.parts.IPart;
import appeng.api.parts.IPartHost;
import appeng.client.gui.implementations.GuiPriority;
import appeng.container.implementations.ContainerPriority;
import appeng.helpers.IPriorityHost;
import com.ultraflash.equi3ae.client.gui.Inventory.GuiEmcConverter;
import com.ultraflash.equi3ae.client.gui.Inventory.GuiEmcFilter;
import com.ultraflash.equi3ae.equi3ae;
import com.ultraflash.equi3ae.inventory.ContainerConverter;
import com.ultraflash.equi3ae.inventory.ContainerE3AE;
import com.ultraflash.equi3ae.inventory.ContainerEmcFilter;
import com.ultraflash.equi3ae.parts.PartBaseE3AE;
import com.ultraflash.equi3ae.reference.GUIs;
import com.ultraflash.equi3ae.tileentity.TileEntityEmcConverter;
import com.ultraflash.equi3ae.tileentity.TileEntityEmcFilter;
import cpw.mods.fml.common.network.IGuiHandler;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class GuiHandlerE3AE implements IGuiHandler {
    public static final int MOD_TILE_ENTITY_GUI = 0;

    // ID's between 0 and this number indicate that they are AE parts
    private static final int DIRECTION_OFFSET = ForgeDirection.values().length;

    // ID's must increase in values of 10
    private static final int ID_STEP_VALUE = 10;
    private static Object[] extraData = null;


    public static final int EMC_FILTER =13;

    public static final int EMC_CONVERTER = 23;

    public static final int PRIORITY_ID = ID_STEP_VALUE * 3;

    //AE GUIS
    private static IPart getPart(final ForgeDirection tileSide, final World world, final int x, final int y, final int z) {
        // Get the host at the specified position
        IPartHost partHost = (IPartHost) (world.getTileEntity(x, y, z));

        // Ensure we got a host
        if (partHost == null) {
            return null;
        }

        // Get the part from the host
        return (partHost.getPart(tileSide));
    }


    private static IPart getPartFromSidedID(final int ID, final World world, final int x, final int y, final int z) {
        ForgeDirection side = ForgeDirection.getOrientation(ID % GuiHandlerE3AE.ID_STEP_VALUE);
        return GuiHandlerE3AE.getPart(side, world, x, y, z);
    }

    private static Object getPartGuiElement(final ForgeDirection tileSide, final EntityPlayer player, final World world, final int x, final int y,
                                            final int z, final boolean isServerSide) {
        // Get the part
        PartBaseE3AE part = (PartBaseE3AE) GuiHandlerE3AE.getPart(tileSide, world, x, y, z);

        // Ensure we got the part
        if (part == null) {
            return null;
        }

        // Is this server side?
        if (isServerSide) {
            // Ask the part for its server element
            return part.getServerGuiElement(player);
        }

        // Ask the part for its client element
        return part.getClientGuiElement(player);
    }

    private static boolean isIDInRange(final int ID, final int BaseID) {
        return ((ID >= BaseID) && (ID < (BaseID + GuiHandlerE3AE.ID_STEP_VALUE)));
    }

    public static int generateSidedID(final int ID, final ForgeDirection side) {
        return ID + side.ordinal();
    }


    public static void launchGui(final int ID, final EntityPlayer player, final World world, final int x, final int y, final int z) {
        player.openGui(equi3ae.instance, ID + GuiHandlerE3AE.DIRECTION_OFFSET, world, x, y, z);
    }


    public static void launchGui(final PartBaseE3AE part, final EntityPlayer player, final World world, final int x, final int y, final int z) {
        // Ensure the player is allowed to open the gui
        if (part.isPartUseableByPlayer(player)) {
            player.openGui(equi3ae.instance, part.getSide().ordinal(), world, x, y, z);
        }
    }


    @Override
    public Object getClientGuiElement(int ID, final EntityPlayer player, final World world, final int x, final int y, final int z) {
        // Is the ID a forge direction?
        ForgeDirection side = ForgeDirection.getOrientation(ID);

        // Do we have a world and side?
        if ((world != null) && (side != ForgeDirection.UNKNOWN)) {
            // This is an AE part, get its gui
            return GuiHandlerE3AE.getPartGuiElement(side, player, world, x, y, z, false);
        }

        // This is not an AE part, adjust the ID
        ID -= GuiHandlerE3AE.DIRECTION_OFFSET;

        // Check basic ID's
        switch (ID) {


            case EMC_FILTER:
                return new GuiEmcFilter(player.inventory, (TileEntityEmcFilter) world.getTileEntity(x, y, z));

            case EMC_CONVERTER:
                return new GuiEmcConverter(player.inventory, (TileEntityEmcConverter) world.getTileEntity(x, y, z));


            /*

            // AE2 Autocrafting Amount?
            case GuiHandlerE3AE.AUTO_CRAFTING_AMOUNT:
                ICraftingIssuerHost amountHost = GuiHandlerE3AE.getCraftingIssuerHost( player );
                if( amountHost != null )
                {
                    return new GuiCraftAmountBridge( player, amountHost );
                }
                return null;

            // AE2 Autocrafting Confirm?
            case GuiHandlerE3AE.AUTO_CRAFTING_CONFIRM:
                ICraftingIssuerHost confirmHost = GuiHandlerE3AE.getCraftingIssuerHost( player );
                if( confirmHost != null )
                {
                    return new GuiCraftConfirmBridge( player, confirmHost );
                }
                return null;
*/
        }

        // Is this the priority window?
        if (GuiHandlerE3AE.isIDInRange(ID, GuiHandlerE3AE.PRIORITY_ID)) {
            // Get the part
            IPart part = GuiHandlerE3AE.getPartFromSidedID(ID, world, x, y, z);

            // Ensure we got the part, and that it implements IPriortyHost
            if ((part == null) || !(part instanceof IPriorityHost)) {
                return null;
            }

            // Return the gui
            return new GuiPriority(player.inventory, (IPriorityHost) part);
        }

        // No matching GUI element found
        return null;

    }

    @Override
    public Object getServerGuiElement(int ID, final EntityPlayer player, final World world, final int x, final int y, final int z) {
        // Is the ID a forge Direction?
        ForgeDirection side = ForgeDirection.getOrientation(ID);

        // Do we have a world and side?
        if ((world != null) && (side != ForgeDirection.UNKNOWN)) {
            // This is an AE part, get its gui

            return GuiHandlerE3AE.getPartGuiElement(side, player, world, x, y, z, true);
        }

        // This is not an AE part, adjust the ID
        ID -= GuiHandlerE3AE.DIRECTION_OFFSET;

        switch (ID) {


            case GuiHandlerE3AE.EMC_FILTER:
                return new ContainerEmcFilter(player.inventory, (TileEntityEmcFilter) world.getTileEntity(x, y, z));


            case GuiHandlerE3AE.EMC_CONVERTER:
                return new ContainerConverter(player.inventory, (TileEntityEmcConverter) world.getTileEntity(x, y, z));

            /*

            // AE2 Autocrafting Amount?
            case GuiHandlerE3AE.AUTO_CRAFTING_AMOUNT:
                ICraftingIssuerHost amountHost = GuiHandlerE3AE.getCraftingIssuerHost( player );
                if( amountHost != null )
                {
                    return new ContainerCraftAmount( player.inventory, amountHost );
                }
                return null;

            // AE2 Autocrafting Confirm?
            case GuiHandlerE3AE.AUTO_CRAFTING_CONFIRM:
                ICraftingIssuerHost confirmHost = GuiHandlerE3AE.getCraftingIssuerHost( player );
                if( confirmHost != null )
                {
                    return new ContainerCraftConfirm( player.inventory, confirmHost );
                }
                return null;


*/
        }
            // Is this the priority window?
            if (GuiHandlerE3AE.isIDInRange(ID, GuiHandlerE3AE.PRIORITY_ID)) {
                // Get the part
                IPart part = GuiHandlerE3AE.getPartFromSidedID(ID, world, x, y, z);

                // Ensure we got the part, and that it implements IPriortyHost
                if ((part == null) || !(part instanceof IPriorityHost)) {
                    return null;
                }

                // Return the container
                return new ContainerPriority(player.inventory, (IPriorityHost) part);

            }



// No matching GUI element found
        return null;
    }


}



    /*
    @Override
    public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        if (ID == GUIs.EMCFILTER.ordinal()*ID_STEP_VALUE) {
            return new ContainerEmcFilter(player.inventory, (TileEntityEmcFilter) world.getTileEntity(x, y, z));
        }else if  (ID== GUIs.EMCCONVERTER.ordinal()*ID_STEP_VALUE) {
            return new ContainerConverter(player.inventory, (TileEntityEmcConverter) world.getTileEntity(x, y, z));
        }
        return null;
    }

    @Override
    public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        if (ID == GUIs.EMCFILTER.ordinal()*ID_STEP_VALUE)
        {
            return new GuiEmcFilter(player.inventory ,(TileEntityEmcFilter) world.getTileEntity(x, y, z));
        }else if  (ID== GUIs.EMCCONVERTER.ordinal()*ID_STEP_VALUE) {
            return new GuiEmcConverter(player.inventory ,(TileEntityEmcConverter) world.getTileEntity(x, y, z));
        }

        return null;
    }

*/







