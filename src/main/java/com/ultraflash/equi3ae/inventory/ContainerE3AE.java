package com.ultraflash.equi3ae.inventory;


import com.ultraflash.equi3ae.utility.ItemStackUtils;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

/**
 * Created by Benni on 23.06.2016.
 */
public class ContainerE3AE extends Container
{
    protected final int PLAYER_INVENTORY_ROWS = 3;
    protected final int PLAYER_INVENTORY_COLUMNS = 9;


    @Override
    public boolean canInteractWith(EntityPlayer playerIn) {
        return true;
    }


    @Override
    protected boolean mergeItemStack(ItemStack itemStack, int slotMin, int slotMax, boolean ascending)
    {
        boolean slotFound = false;
        int currentSlotIndex = ascending ? slotMax - 1 : slotMin;

        Slot slot;
        ItemStack stackInSlot;

        if (itemStack.isStackable())
        {
            while (itemStack.stackSize > 0 && (!ascending && currentSlotIndex < slotMax || ascending && currentSlotIndex >= slotMin))
            {
                slot = (Slot) this.inventorySlots.get(currentSlotIndex);
                stackInSlot = slot.getStack();

                if (slot.isItemValid(itemStack)&& ItemStackUtils.equalsIgnoreStackSize(itemStack, stackInSlot))
                {
                    int combinedStackSize = stackInSlot.stackSize + itemStack.stackSize;
                    int slotStackSizeLimit = Math.min(stackInSlot.getMaxStackSize(), slot.getSlotStackLimit());

                    if (combinedStackSize <= slotStackSizeLimit)
                    {
                        itemStack.stackSize = 0;
                        stackInSlot.stackSize = combinedStackSize;
                        slot.onSlotChanged();
                        slotFound = true;
                    }
                    else if (stackInSlot.stackSize < slotStackSizeLimit)
                    {
                        itemStack.stackSize -= slotStackSizeLimit - stackInSlot.stackSize;
                        stackInSlot.stackSize = slotStackSizeLimit;
                        slot.onSlotChanged();
                        slotFound = true;
                    }
                }

                currentSlotIndex += ascending ? -1 : 1;
            }
        }

        if (itemStack.stackSize > 0)
        {
            currentSlotIndex = ascending ? slotMax - 1 : slotMin;

            while (!ascending && currentSlotIndex < slotMax || ascending && currentSlotIndex >= slotMin)
            {
                slot = (Slot) this.inventorySlots.get(currentSlotIndex);
                stackInSlot = slot.getStack();

                if (slot.isItemValid(itemStack) && stackInSlot == null)
                {
                    slot.putStack(ItemStackUtils.clone(itemStack, Math.min(itemStack.stackSize, slot.getSlotStackLimit())));
                    slot.onSlotChanged();

                    if (slot.getStack() != null)
                    {
                        itemStack.stackSize -= slot.getStack().stackSize;
                        slotFound = true;
                    }

                    break;
                }

                currentSlotIndex += ascending ? -1 : 1;
            }
        }

        return slotFound;
    }



}
