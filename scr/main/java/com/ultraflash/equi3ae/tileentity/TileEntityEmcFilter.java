package com.ultraflash.equi3ae.tileentity;
import com.ultraflash.equi3ae.block.BlockTileEntityE3AE;
import com.ultraflash.equi3ae.init.ModBlocks;
import com.ultraflash.equi3ae.reference.Names;
import com.ultraflash.equi3ae.utility.CoordHelper;
import com.ultraflash.equi3ae.utility.InternalInventory;
import com.ultraflash.equi3ae.utility.ItemStackUtils;
import com.ultraflash.equi3ae.utility.LogHelper;
import cpw.mods.fml.common.FMLCommonHandler;
import net.minecraft.block.material.Material;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInvBasic;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.server.gui.IUpdatePlayerListBox;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityHopper;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraft.block.Block;
//import com.pahimar.ee3.api.exchange.EnergyValue;




public class TileEntityEmcFilter extends TileEntityE3AE implements ISidedInventory
{
    public short facing;
    private ItemStack[] inventory;
    private int maxUpgrade=1;

    public int numUsingPlayers;
    public static final int INVENTORY_SIZE = 9;
    private static final int DEFAULT_ITEM_SUCK_COOL_DOWN = 20;
    public static final int INPUT[] = {0,1,2,3,4,5};
    public static final int OUTPUT_LEFT_INVENTORY_INDEX = 6;
    public static final int OUTPUT_RIGHT_INVENTORY_INDEX = 7;
    public int itemSuckCoolDown = 0;


    public TileEntityEmcFilter()
    {
        super();
        inventory = new ItemStack[INVENTORY_SIZE];
    }




    public static boolean suckInItems(TileEntityEmcFilter tileEntityEmcFilter)
    {
        EntityItem entityitem = TileEntityHopper.func_145897_a(tileEntityEmcFilter.getWorldObj(), tileEntityEmcFilter.xCoord, tileEntityEmcFilter.yCoord + 1.0D, tileEntityEmcFilter.zCoord);

        return entityitem != null && TileEntityHopper.func_145898_a(tileEntityEmcFilter, entityitem);
    }

    @Override
    public int[] getAccessibleSlotsFromSide(int side)
    {

        if(side==  ForgeDirection.UP.ordinal())
        {
            return INPUT;
        }
        if(side==  ForgeDirection.WEST.ordinal())
        {
            return new int[]{OUTPUT_LEFT_INVENTORY_INDEX };
        }
        if(side==  ForgeDirection.EAST.ordinal())
        {
            return new int[]{OUTPUT_RIGHT_INVENTORY_INDEX};
        }

        return null;
    }


    @Override
    public boolean canInsertItem(int slotIndex, ItemStack itemStack, int side) {

        return slotIndex <= 5 ;
    }

    @Override
    public boolean canExtractItem(int slotIndex, ItemStack itemStack, int side) {
        return slotIndex == OUTPUT_LEFT_INVENTORY_INDEX || slotIndex == OUTPUT_RIGHT_INVENTORY_INDEX;
    }


    @Override
    public int getSizeInventory() {
        return inventory.length;
    }

    @Override
    public ItemStack getStackInSlot(int slotIndex) {
        LogHelper.info("Slot-Index "+ slotIndex);
        return inventory[slotIndex];
    }

    @Override
    public ItemStack decrStackSize(int slotIndex, int decrementAmount)
    {
        ItemStack itemStack = getStackInSlot(slotIndex);
        if (itemStack != null)
        {
            if (itemStack.stackSize <= decrementAmount)
            {
                setInventorySlotContents(slotIndex, null);
            }
            else
            {
                itemStack = itemStack.splitStack(decrementAmount);
                if (itemStack.stackSize == 0)
                {
                    setInventorySlotContents(slotIndex, null);
                }
            }
        }

        return itemStack;
    }

    @Override
    public ItemStack getStackInSlotOnClosing(int slotIndex)
    {
        if (inventory[slotIndex] != null)
        {
            ItemStack itemStack = inventory[slotIndex];
            inventory[slotIndex] = null;
            return itemStack;
        }
        else
        {
            return null;
        }
    }

    @Override
    public void setInventorySlotContents(int slotIndex, ItemStack itemStack)
    {
        inventory[slotIndex] = itemStack;

        if (itemStack != null && itemStack.stackSize > this.getInventoryStackLimit())
        {
            itemStack.stackSize = this.getInventoryStackLimit();
        }


        this.markDirty();
    }

    @Override
    public String getInventoryName() {
        return "emcFilter";
    }

    @Override
    public boolean hasCustomInventoryName() {
        return false;
    }

    @Override
    public int getInventoryStackLimit() {
        return 64;
    }

    @Override
    public boolean isUseableByPlayer(EntityPlayer entityplayer) {
        return this.worldObj.getTileEntity(xCoord, yCoord, zCoord) == this && entityplayer.getDistanceSq((double) xCoord + 0.5D, (double) yCoord + 0.5D, (double) zCoord + 0.5D) <= 64D;
    }

    @Override
    public void openInventory() {
        ++numUsingPlayers;
        worldObj.addBlockEvent(xCoord, yCoord, zCoord, ModBlocks.emcfilter, 1, numUsingPlayers);
        LogHelper.info("Ivnertory Opened");
    }

    @Override
    public void closeInventory() {
        --numUsingPlayers;
        worldObj.addBlockEvent(xCoord, yCoord, zCoord, ModBlocks.emcfilter, 1, numUsingPlayers);

    }

    @Override
    public boolean isItemValidForSlot(int p_94041_1_, ItemStack p_94041_2_) {
        return true;
    }

    @Override
    public boolean receiveClientEvent(int eventID, int numUsingPlayers)
    {
        if (eventID == 1)
        {
            this.numUsingPlayers = numUsingPlayers;
            return true;
        }
        else
        {
            return super.receiveClientEvent(eventID, numUsingPlayers);
        }
    }

    @Override
    public void readFromNBT(NBTTagCompound nbtTagCompound)
    {
        super.readFromNBT(nbtTagCompound);

        // Read in the ItemStacks in the inventory from NBT
        NBTTagList tagList = nbtTagCompound.getTagList(Names.NBT.ITEMS, 10);
        inventory = new ItemStack[this.getSizeInventory()];
        for (int i = 0; i < tagList.tagCount(); ++i)
        {
            NBTTagCompound tagCompound = tagList.getCompoundTagAt(i);
            byte slotIndex = tagCompound.getByte("Slot");
            if (slotIndex >= 0 && slotIndex < inventory.length)
            {
                inventory[slotIndex] = ItemStack.loadItemStackFromNBT(tagCompound);
            }
        }
    }

    @Override
    public void writeToNBT(NBTTagCompound nbtTagCompound)
    {
        super.writeToNBT(nbtTagCompound);

        // Write the ItemStacks in the inventory to NBT
        NBTTagList tagList = new NBTTagList();
        for (int currentIndex = 0; currentIndex < inventory.length; ++currentIndex)
        {
            if (inventory[currentIndex] != null)
            {
                NBTTagCompound tagCompound = new NBTTagCompound();
                tagCompound.setByte("Slot", (byte) currentIndex);
                inventory[currentIndex].writeToNBT(tagCompound);
                tagList.appendTag(tagCompound);
            }
        }
        nbtTagCompound.setTag(Names.NBT.ITEMS, tagList);
    }

    @Override
    public void updateEntity()
    {
        // if(ForgeDirection.WEST.ordinal)
        if(doWorkThisTick(5)) {
            doSideCheck();
        }
    }

    private CoordHelper  cachedLocation = null;

    public CoordHelper  getLocation() {
        return cachedLocation == null || !cachedLocation.equals(xCoord, yCoord, zCoord) ? (cachedLocation = new CoordHelper (this)) : cachedLocation;
    }


    protected boolean doSideCheck()
    {
        CoordHelper loc =getLocation().getLocation(ForgeDirection.WEST);
        TileEntity te = worldObj.getTileEntity(loc.x, loc.y, loc.z);
        doPush(te);

        return true;
    }


    protected boolean doPush(TileEntity te)
    {
        int maxThroughput = 2^maxUpgrade;
        int oldVal=0;

        for (int i = 0 ;i <= INPUT.length-1; i++)
        {
            ItemStack item = inventory[i];
            if(item != null)
            {
                oldVal=item.stackSize;
                item.stackSize= Math.min( item.stackSize ,maxThroughput);


                int num = ItemStackUtils.doInsertItem(te, item, ForgeDirection.WEST);


                if(num > 0) {
                    item.stackSize = oldVal-num;
                    if(item.stackSize <= 0) {
                        item = null;
                    }
                    inventory[i] = item;
                    markDirty();
                }
            }

            }


        return true;
    }



    public ForgeDirection getFacingDir() {
        return ForgeDirection.getOrientation(facing);
    }

    private final int checkOffset = (int) (Math.random() * 20);
    protected boolean doWorkThisTick(int interval) {
        return doWorkThisTick(interval, 0);
    }


    protected boolean doWorkThisTick(int interval, int offset) {
        return (worldObj.getTotalWorldTime() + checkOffset + offset) % interval == 0;
    }

    @Override
    public final boolean canUpdate() {
        return shouldUpdate();
    }

    protected boolean shouldUpdate() {
        return true;
    }




}



