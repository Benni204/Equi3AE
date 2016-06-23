package com.ultraflash.equi3ae.inventory;

import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;

/**
 * Created by Benni on 23.06.2016.
 */
public class SingleItemSlot extends Slot
{
    public SingleItemSlot(IInventory inventory, int index, int xPosition, int yPosition) {
        super(inventory, index, xPosition, yPosition);
    }

    @Override
    public int getSlotStackLimit() {
        return 1;
    }

}
