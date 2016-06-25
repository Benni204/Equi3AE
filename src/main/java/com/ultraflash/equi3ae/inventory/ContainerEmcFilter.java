package com.ultraflash.equi3ae.inventory;

import com.ultraflash.equi3ae.tileentity.TileEntityEmcFilter;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

/**
 * Created by Benni on 23.06.2016.
 */
public class ContainerEmcFilter extends ContainerE3AE
{

    private TileEntityEmcFilter te;


    public ContainerEmcFilter(IInventory playerInv, TileEntityEmcFilter te)
    {
        this.te = te;

        // Tile Entity, Slot 0-8, Slot IDs 0-8
        for (int y = 0; y < 2; ++y) {
            for (int x = 0; x < 3; ++x) {
                this.addSlotToContainer(new Slot(te, x + y * 3, 62 + x * 18, 17 + y * 18));
            }
        }

        // Player Inventory, Slot 9-35, Slot IDs 9-35
        for (int y = 0; y < 3; ++y) {
            for (int x = 0; x < 9; ++x) {
                this.addSlotToContainer(new Slot(playerInv, x + y * 9 + 9, 8 + x * 18, 84 + y * 18));
            }
        }

        // Player Inventory, Slot 0-8, Slot IDs 36-44
        for (int x = 0; x < 9; ++x) {
            this.addSlotToContainer(new Slot(playerInv, x, 8 + x * 18, 142));
        }


/*
 * SLOTS:
 *
 * Tile Entity 0-8 ........ 0  - 8
 * Player Inventory 9-35 .. 9  - 35
 * Player Inventory 0-8 ... 36 - 44
 */

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
            if (fromSlot < 6) {
                // From TE Inventory to Player Inventory
                if (!this.mergeItemStack(current, 9, 45, true))
                    return null;
            } else {
                // From Player Inventory to TE Inventory
                if (!this.mergeItemStack(current, 0, 6, false))
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
