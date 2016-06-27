package com.ultraflash.equi3ae.inventory;

import com.pahimar.ee3.api.exchange.EnergyValueRegistryProxy;
import com.ultraflash.equi3ae.init.ModItems;
import com.ultraflash.equi3ae.tileentity.TileEntityEmcConverter;
import com.ultraflash.equi3ae.tileentity.TileEntityEmcFilter;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

/**
 * Created by Benni on 25.06.2016.
 */
public class ContainerConverter extends ContainerE3AE
{

    private TileEntityEmcConverter te;
    protected Slot[] upgradeSlot={null,null,null};


    private int xUp=160;
    private int yUp=17;


    public ContainerConverter(IInventory playerInv, TileEntityEmcConverter te)
    {
        this.te = te;
        //upgradeSlot


        //Define UpgradeSLots
        upgradeSlot[0] = new Slot(te, 3, xUp,yUp)
        {
            @Override
            public int getSlotStackLimit() {
                return 1;
            }

            @Override
            public final boolean isItemValid( ItemStack itemstack) {

                return itemstack != null && itemstack.getItem() == ModItems.mapleLeaf;
            }

        };
        upgradeSlot[1] = new Slot(te, 4, xUp,yUp*2)
        {
            @Override
            public int getSlotStackLimit() {
                return 1;
            }

            @Override
            public final boolean isItemValid( ItemStack itemstack) {

                return itemstack != null && itemstack.getItem() == ModItems.mapleLeaf;
            }

        };
        upgradeSlot[2] = new Slot(te, 5, xUp,yUp*3)
        {
            @Override
            public int getSlotStackLimit() {
                return 1;
            }

            @Override
            public final boolean isItemValid( ItemStack itemstack) {

                return itemstack != null && itemstack.getItem() == ModItems.mapleLeaf;
            }

        };

        addSlotToContainer(upgradeSlot[0]);
        addSlotToContainer(upgradeSlot[1]);
        addSlotToContainer(upgradeSlot[2]);



        // Tile Entity, Slot 0-8, Slot IDs 0-8
        for (int y = 0; y < 1; ++y) {
            for (int x = 0; x < 3; ++x) {
                this.addSlotToContainer(new Slot(te, x + y * 3, 62 + x * 18, 17 + y * 18)
                {
                    @Override
                    public final boolean isItemValid( ItemStack itemstack) {

                        return itemstack != null &&  EnergyValueRegistryProxy.hasEnergyValue(itemstack);
                    }

                });
            }
        }

        // Player Inventory, Slot 9-35, Slot IDs 9-35
        for (int y = 0; y < PLAYER_INVENTORY_ROWS; ++y) {
            for (int x = 0; x < PLAYER_INVENTORY_COLUMNS; ++x) {
                this.addSlotToContainer(new Slot(playerInv, x + y * 9 + 9, 8 + x * 18, 84 + y * 18));
            }
        }

        // Player Inventory, Slot 0-8, Slot IDs 36-44
        for (int x = 0; x < 9; ++x) {
            this.addSlotToContainer(new Slot(playerInv, x, 8 + x * 18, 142));
        }





    }

    public Slot getUpgradeSlot(int index) {
        return upgradeSlot[index];
    }

    @Override
    public boolean canInteractWith(EntityPlayer playerIn) {
        return this.te.isUseableByPlayer(playerIn);
    }

    @Override
    public ItemStack transferStackInSlot(EntityPlayer playerIn, int fromSlot)
    {
        ItemStack previous = null;
        Slot slot = (Slot) this.inventorySlots.get(fromSlot);

        if (slot != null && slot.getHasStack()) {
            ItemStack current = slot.getStack();
            previous = current.copy();

            //Behavior
            if (fromSlot < 3) {
                // From TE Inventory to Player Inventory
                if (!this.mergeItemStack(current, 9, 35, true))
                    return null;
            } else {
                // From Player Inventory to TE Inventory
                if (!this.mergeItemStack(current, 0, 2, false))
                    return null;
            }


            if (current.stackSize == 0)
                slot.putStack((ItemStack) null);
            else
                slot.onSlotChanged();

            if (current.stackSize == previous.stackSize)
                return null;
            slot.onPickupFromSlot(playerIn, current);
        }
        return previous;

    }




}
