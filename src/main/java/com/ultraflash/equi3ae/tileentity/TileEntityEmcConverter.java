package com.ultraflash.equi3ae.tileentity;
import appeng.api.config.Actionable;
import appeng.api.networking.GridFlags;
import appeng.api.networking.security.MachineSource;
import appeng.api.storage.ICellContainer;
import appeng.api.storage.ICellProvider;
import appeng.api.storage.IMEInventory;
import appeng.api.storage.IMEMonitor;
import appeng.api.util.AECableType;
import appeng.api.util.DimensionalCoord;
import appeng.tile.inventory.InvOperation;
import appeng.util.ConfigManager;
import com.pahimar.ee3.api.exchange.EnergyValueRegistryProxy;
import com.sun.javafx.scene.layout.region.Margins;
import com.ultraflash.equi3ae.handler.ConfigurationHandler;
import com.ultraflash.equi3ae.init.ModBlocks;
import com.ultraflash.equi3ae.init.ModItems;
import com.ultraflash.equi3ae.inventory.ContainerConverter;
import com.ultraflash.equi3ae.network.packet.BasePacketE3AE;
import com.ultraflash.equi3ae.reference.Names;
import com.ultraflash.equi3ae.reference.Textures;
import com.ultraflash.equi3ae.utility.CoordHelper;
import com.ultraflash.equi3ae.utility.InternalInventory;
import com.ultraflash.equi3ae.utility.ItemStackUtils;
import com.ultraflash.equi3ae.utility.LogHelper;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityHopper;
import net.minecraftforge.common.util.ForgeDirection;

import appeng.api.networking.GridFlags;
import appeng.tile.grid.AENetworkInvTile;
import appeng.api.networking.security.MachineSource;
import appeng.api.networking.storage.IStorageGrid;
import appeng.api.storage.data.IAEItemStack;
import appeng.api.util.AECableType;
import appeng.api.util.DimensionalCoord;
import appeng.me.GridAccessException;
import appeng.me.GridException;
import appeng.tile.TileEvent;
import appeng.tile.events.TileEventType;
import appeng.tile.grid.AENetworkInvTile;
import appeng.tile.inventory.InvOperation;
import appeng.api.AEApi;

import java.io.IOException;


public class TileEntityEmcConverter extends TileEntityNetworkE3AE
{
    private class ConverterInventory extends InternalInventory {
        public ConverterInventory()	{
            super("EMCConverterInventory", TileEntityEmcConverter.NUM_SLOTS, 64);
        }

        @Override
        public boolean isItemValidForSlot(final int slotId, final ItemStack itemStack) {
            return EnergyValueRegistryProxy.hasEnergyValue(itemStack);
        }
    }

    public IInventory iInventory;
    private ItemStack[] inventory;




    public static final int INVENTORY_SIZE = 6;
    public static final int UPGRADE_COUNT=3;
    private static final String INVSLOTS = "items";
    private float EMCupgrade=0.0f;
    private int speedUpgrade=0;
    private boolean isActive;
    private boolean state;
    public static final int NUM_SLOTS = 6; //crash if < 6
    //private ConverterInventory internalI;
   // private  internalI;
    private MachineSource thisSource;
    public float internalEMC;
    public float storedEMC ;
    public float maxstoredEMC = 1024.0f;
    public float maxIntEMC = 0.0f;
    public double efficenty= 0.60;
    public float lastEmc;
    private float procEmc;





    public TileEntityEmcConverter()
    {
        super();
        thisSource=new MachineSource(this);
        inventory = new ItemStack[NUM_SLOTS];
       //internalI=new ConverterInventory();
        this.state=false;
        this.procEmc=0.0f;
        this.internalEMC = 0.0f;
        this.storedEMC = 0.0f;


        if(FMLCommonHandler.instance().getEffectiveSide().isServer()) {
            this.getProxy().setIdlePowerUsage(ConfigurationHandler.powerConverterIdle);
            this.getProxy().setFlags(GridFlags.REQUIRE_CHANNEL);
        }

       iInventory = new IInventory() {
           @Override
           public int getSizeInventory()
           {
               return inventory.length;
           }


           @Override
           public ItemStack getStackInSlot(int slotIndex)
           {
               // sendDustPileData();
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
               ItemStack itemStack = getStackInSlot(slotIndex);
               if (itemStack != null)
               {
                   setInventorySlotContents(slotIndex, null);
               }
               return itemStack;
           }

           @Override
           public void setInventorySlotContents(int slotIndex, ItemStack itemStack)
           {
               inventory[slotIndex] = itemStack;
               if (itemStack != null && itemStack.stackSize > getInventoryStackLimit())
               {
                   itemStack.stackSize = getInventoryStackLimit();
               }
           }

           @Override
           public String getInventoryName() {
               return "ConverterInventory";
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
           public void markDirty() {

           }

           @Override
           public boolean isUseableByPlayer(EntityPlayer p_70300_1_) {
               return true;
           }

           @Override
           public void openInventory() {

           }

           @Override
           public void closeInventory() {

           }

           @Override
           public boolean isItemValidForSlot(int slotIndex, ItemStack itemStack) {

               if (itemStack != null) {
                   if (slotIndex <= 2) {
                       return EnergyValueRegistryProxy.hasEnergyValue(itemStack);
                   }
                   else if (slotIndex < 6) {
                       return itemStack.getItem() == ModItems.mapleLeaf;
                   }
               }

               return false;
           }

       };
    }

    @Override
    public boolean canInsertItem(int slotIndex, ItemStack itemStack, int side) {

        return slotIndex <= 2& EnergyValueRegistryProxy.hasEnergyValue(itemStack) ;
    }

    @Override
    public IInventory getInternalInventory() {
        return  iInventory;
    }


    @TileEvent(TileEventType.TICK)
    public void onTick()
    {
        if(!isActive())
        {
            return;
        }


        if(getEmcState())
        {
            processItem();
        }else{
            feedEmcItem();
        }

     //   if(!worldObj.isRemote)
       // {
           // worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
            // return;
        //}


        //


    }

    private void processItem()
    {
        float emc= getEmcToProc();
        float addEmc= Math.min((float) ConfigurationHandler.emcPerTick+speedUpgrade, emc) ;

        internalEMC+= addEmc;
        if(emc-addEmc<=0)
        {

            setEmcToProc(0.0f);
            if((storedEMC+internalEMC)<=maxstoredEMC)
            {
                storedEMC+=internalEMC;
                internalEMC=0.0f;
                lastEmc=0.0f;
                setEmcState(false);

                return;
            }else{
                //Buffer is full
                internalEMC= internalEMC -(maxstoredEMC-storedEMC);
                storedEMC=maxstoredEMC;

                try	{
                IAEItemStack crystal = AEApi.instance().storage().createItemStack(new ItemStack(ModItems.rawemc,(int) storedEMC));
                IStorageGrid storageGrid = this.getProxy().getStorage();

                  //  IMEInventory

                    IMEMonitor<IAEItemStack> ItemInventory =storageGrid.getItemInventory();
                    IAEItemStack rejected =ItemInventory.injectItems(crystal, Actionable.SIMULATE, this.thisSource);

                    if(rejected == null || rejected.getStackSize() == 0)
                    {
                        ItemInventory.injectItems(crystal, Actionable.MODULATE, this.thisSource);
                        storedEMC=0;

                    }


                } catch(GridAccessException e) {}

            }
            worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
        }else{
           // internalEMC+= addEmc;
            setEmcToProc(emc-addEmc);
        }

    }



    private void feedEmcItem()
    {

        try {
            for (int i = 0; i < NUM_SLOTS - UPGRADE_COUNT; i++)
            {

                if(iInventory.getStackInSlot(i)!=null)
                {
                  //  ItemStack cur_item=getStackInSlot(i);
                    //cur_item.stackSize=1;
                    float emcVal=EnergyValueRegistryProxy.getEnergyValue(iInventory.getStackInSlot(i)).getValue();
                    if(emcVal>0)
                    {
                        float val=(float) (emcVal*efficenty);

                        iInventory. decrStackSize(i, 1);
                        setEmcToProc(val);
                        lastEmc=val;
                        setEmcState(true);
                      //  worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
                       //this.markDirty();
                        return;
                    }
                }

            }
        }catch(Exception e) {}

       // worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
    }

    private boolean  sendEmcPaket()
    {

        return false;
    }



    public float getEmcToProc(){return this.procEmc;}
    public void setEmcToProc(float emc){this.procEmc=emc;}
    public boolean getEmcState(){return this.state;}
    public float getinternalEMC(){return this.internalEMC;}
    public void setEmcState(boolean state){this.state=state;};


    @Override
    public void onChangeInventory(IInventory iInventory, int i, InvOperation invOperation, ItemStack itemStack, ItemStack itemStack1)
    {
    }

    @Override
    public int[] getAccessibleSlotsBySide(ForgeDirection forgeDirection) {
        return new int[] {0,1,2,3};
    }


    @Override
    public AECableType getCableConnectionType(ForgeDirection dir) {
        return AECableType.SMART;
    }

    @Override
    public DimensionalCoord getLocation() {
        return  new DimensionalCoord(this);
    }

    @Override
    public boolean canExtractItem(int slotIndex, ItemStack extractedItem, int side) {
        return false;
    }

    public float getMaxEMC()
    {
        return 32.0f+ EMCupgrade;
    }

    @TileEvent(TileEventType.WORLD_NBT_READ)
    public void onLoadNBT(final NBTTagCompound data) {

        NBTTagList tagList = data.getTagList("Items", 10);
        inventory = new ItemStack[this.getSizeInventory()];

        for (int i = 0; i < tagList.tagCount(); ++i)
        {
            NBTTagCompound tagCompound = tagList.getCompoundTagAt(i);
            byte slotIndex = tagCompound.getByte("Slot");
            if (slotIndex >= 0 && slotIndex < inventory.length)
            {
                this.inventory[slotIndex] = ItemStack.loadItemStackFromNBT(tagCompound);
            }
        }

        this.storedEMC=data.getFloat("storedEMC");
        this. internalEMC=data.getFloat("internalEMC");
        this. procEmc =data.getFloat("procEmc");
        this.lastEmc=data.getFloat("lastEmc");

        this.markForUpdate();

    }

    @TileEvent(TileEventType.WORLD_NBT_WRITE)
    public void onSaveNBT(final NBTTagCompound data) {


        NBTTagList tagList = new NBTTagList();

        for(int i= 0; i < inventory.length ; i++) {
            if(getStackInSlot(i) != null) {
                NBTTagCompound nbtCompound = new NBTTagCompound();

                nbtCompound.setByte("Slot",(byte)i);
                this. inventory[i].writeToNBT(nbtCompound);
                tagList.appendTag(nbtCompound);

            }
        }

       // if(tagList.tagCount() > 0) {
            data.setTag( Names.NBT.ITEMS,tagList);
        //}
        data.setFloat("storedEMC", this.storedEMC);
        data.setFloat("internalEMC", internalEMC);
        data.setFloat("procEmc", this.procEmc);
        data.setFloat("lastEmc", this.lastEmc);

    }

    public boolean isActive() {
        if(!worldObj.isRemote) {
            if((getProxy() != null) && (getProxy().getNode() != null)) {
                isActive =getProxy().getNode().isActive();
            }
        }

        return isActive;
    }

    public void onBreak() {
        getProxy().invalidate();
    }


    @TileEvent(TileEventType.NETWORK_READ)
    @SideOnly(Side.CLIENT)
    public boolean onReceiveNetworkData(final ByteBuf stream)	{
        isActive = stream.readBoolean();
        storedEMC = stream.readFloat();
        return true;
    }

    @TileEvent(TileEventType.NETWORK_WRITE)
    public void onSendNetworkData(final ByteBuf stream) throws IOException {
        stream.writeBoolean(isActive());
        stream.writeFloat(storedEMC);
       // this.network
    }


    
    protected void networkWrite( final ByteBuf stream )
    {
        
            BasePacketE3AE.writeEMC ((String.valueOf(this.storedEMC)), stream );

    }


}



