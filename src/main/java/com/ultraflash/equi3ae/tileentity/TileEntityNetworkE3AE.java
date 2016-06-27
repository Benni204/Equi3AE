package com.ultraflash.equi3ae.tileentity;


import appeng.api.util.AECableType;
import appeng.api.util.DimensionalCoord;
import appeng.tile.grid.AENetworkInvTile;
import appeng.tile.inventory.InvOperation;
import com.ultraflash.equi3ae.reference.Names;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.util.ForgeDirection;

public class TileEntityNetworkE3AE extends AENetworkInvTile
{
    protected ForgeDirection orientation;
    protected byte state;
    protected String customName;

    protected   ForgeDirection left ;
    protected  ForgeDirection right;
    //protected UUID ownerUUID;

    public TileEntityNetworkE3AE()
    {
        orientation = ForgeDirection.SOUTH;
        state = 0;
        customName = "";
       // ownerUUID = null;
    }

    @Override
    public IInventory getInternalInventory() {
        return null;
    }

    @Override
    public void onChangeInventory(IInventory iInventory, int i, InvOperation invOperation, ItemStack itemStack, ItemStack itemStack1) {

    }

    @Override
    public int[] getAccessibleSlotsBySide(ForgeDirection forgeDirection) {
        return new int[0];
    }

    public ForgeDirection getOrientation()
    {
        return orientation;
    }

    public void setOrientation(ForgeDirection orientation)
    {
        this.orientation = orientation;
    }

    public void setOrientation(int orientation)
    {
        this.orientation = ForgeDirection.getOrientation(orientation);
        setRightLeft();
    }

    public void setRightLeft()
    {
        this.left =  this.orientation.getRotation(ForgeDirection.UP);;
        this.right = left.getOpposite();
    }

    public short getState()
    {
        return state;
    }

    public void setState(byte state)
    {
        this.state = state;
    }

    public String getCustomName()
    {
        return customName;
    }

    public void setCustomName(String customName)
    {
        this.customName = customName;
    }





    public boolean hasCustomName()
    {
        return customName != null && customName.length() > 0;
    }

    @Override
    public DimensionalCoord getLocation() {
        return null;
    }

    @Override
    public AECableType getCableConnectionType(ForgeDirection dir) {
        return null;
    }
/*
    public boolean hasOwner()
    {
        return ownerUUID != null;
    }


    @Override
    public Packet getDescriptionPacket()
    {
        return PacketHandler.INSTANCE.getPacketFrom(new MessageTileEntityEE(this));
    }
    */
}


