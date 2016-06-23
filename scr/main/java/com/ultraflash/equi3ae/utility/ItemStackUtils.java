package com.ultraflash.equi3ae.utility;

import net.minecraft.item.ItemStack;

public class ItemStackUtils
{
    public static ItemStack clone(ItemStack itemStack, int stackSize) {

        if (itemStack != null) {
            ItemStack clonedItemStack = itemStack.copy();
            clonedItemStack.stackSize = stackSize;
            return clonedItemStack;
        }
        else {
            return null;
        }
    }
/*
    public static boolean equals(ItemStack first, ItemStack second) {
        return (Comparators.ID_COMPARATOR.compare(first, second) == 0);
    }

    public static boolean equalsIgnoreStackSize(ItemStack itemStack1, ItemStack itemStack2) {
        return equals(clone(itemStack1, 1), clone(itemStack2, 1));
    }

    public static int compare(ItemStack itemStack1, ItemStack itemStack2) {
        return Comparators.ID_COMPARATOR.compare(itemStack1, itemStack2);
    }
*/

}


