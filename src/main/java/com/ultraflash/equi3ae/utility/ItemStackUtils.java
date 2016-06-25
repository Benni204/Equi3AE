package com.ultraflash.equi3ae.utility;

import com.pahimar.ee3.reference.Comparators;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.inventory.InventoryLargeChest;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraftforge.common.util.ForgeDirection;

public class                  ItemStackUtils
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

                        public static int doInsertItem(Object into, ItemStack item, ForgeDirection side,int throughput) {
                            if (into == null || item == null) {
                                return 0;
                            }
                            if (into instanceof ISidedInventory) {
                                return ItemStackUtils.doInsertItemInv((ISidedInventory) into, item, side, true,throughput);
                            } else if (into instanceof IInventory) {
                                return ItemStackUtils.doInsertItemInv(getInventory((IInventory) into), item, side, true,throughput);
                            }



                            return 0;
                        }




                        public static IInventory getInventory(IInventory inv) {
                            if (inv instanceof TileEntityChest) {
                                TileEntityChest chest = (TileEntityChest) inv;
                                TileEntityChest neighbour = null;
                                boolean reverse = false;
                                if (chest.adjacentChestXNeg != null) {
                                    neighbour = chest.adjacentChestXNeg;
                                    reverse = true;
                                } else if (chest.adjacentChestXPos != null) {
                                    neighbour = chest.adjacentChestXPos;
                                } else if (chest.adjacentChestZNeg != null) {
                                    neighbour = chest.adjacentChestZNeg;
                                    reverse = true;
                                } else if (chest.adjacentChestZPos != null) {
                                    neighbour = chest.adjacentChestZPos;
                                }
                                if (neighbour != null) {
                                    if (reverse) {
                                        return new InventoryLargeChest("", neighbour, inv);
                                    } else {
                                        return new InventoryLargeChest("", inv, neighbour);
                                    }
                                }
                            }
                            return inv;
                        }

                        public static int doInsertItem(IInventory inv, int startSlot, int endSlot, ItemStack item,int throughput) {
                            return doInsertItemInv(inv, null, invSlotter.getInstance(startSlot, endSlot), item, ForgeDirection.UNKNOWN, true,throughput);
                        }

                        public static int doInsertItem(IInventory inv, int startSlot, int endSlot, ItemStack item, boolean doInsert,int throughput) {
                            return doInsertItemInv(inv, null, invSlotter.getInstance(startSlot, endSlot), item, ForgeDirection.UNKNOWN, doInsert,throughput);
                        }

  /*
   * Insert items into an IInventory or an ISidedInventory.
   */
                        private static int doInsertItemInv(IInventory inv, ItemStack item, ForgeDirection inventorySide, boolean doInsert,int throughput) {
                            final ISidedInventory sidedInv = inv instanceof ISidedInventory ? (ISidedInventory) inv : null;
                            ISlotIterator slots;

                            if (sidedInv != null) {
                                if (inventorySide == null) {
                                    inventorySide = ForgeDirection.UNKNOWN;
                                }
                    // Note: This is not thread-safe. Change to getInstance() to constructor when needed (1.8++?).
                    slots = sidedSlotter.getInstance(sidedInv.getAccessibleSlotsFromSide(inventorySide.ordinal()));
                } else {
                    slots = invSlotter.getInstance(0, inv.getSizeInventory());
                }

                return doInsertItemInv(inv, sidedInv, slots, item, inventorySide, doInsert, throughput);
            }


    private static int doInsertItemInv(IInventory inv, ISidedInventory sidedInv, ISlotIterator slots, ItemStack item, ForgeDirection inventorySide,
                                       boolean doInsert,int throughput)
    {
        int numInserted = 0;
        int numitems=2;
        int numToInsert= throughput;
        int firstFreeSlot = -1;

/*
         while (numToInsert > 0 && slots.hasNext())
         {


         }

*/
       // if(item.stackSize> )


        //existen Slot
      // int slot= slots.nextSlot();
        while (numToInsert > 0 && slots.hasNext()) {
      //  LogHelper.info(slot);
          // slot = slots.nextSlot();
             final int slot= slots.nextSlot();
            if (sidedInv == null || sidedInv.canInsertItem(slot, item, inventorySide.ordinal())) {
                final ItemStack contents = inv.getStackInSlot(slot);
                if (contents != null) {
                    if (areStackMergable(contents, item)) {
                        final int freeSpace = Math.min(inv.getInventoryStackLimit(), contents.getMaxStackSize()) - contents.stackSize; // some inventories like using itemstacks with invalid stack sizes
                        if (freeSpace > 0) {
                            final int noToInsert = Math.min(numToInsert, freeSpace);
                            final ItemStack toInsert = item.copy();
                            toInsert.stackSize = contents.stackSize + noToInsert;
                            // isItemValidForSlot() may check the stacksize, so give it the number the stack would have in the end.
                            // If it does something funny, like "only even numbers", we are screwed.
                            if (sidedInv != null || inv.isItemValidForSlot(slot, toInsert)) {
                                numInserted += noToInsert;
                                numToInsert -= noToInsert;
                                if (doInsert) {
                                    inv.setInventorySlotContents(slot, toInsert);
                                }
                            }
                        }//else { slot = slots.nextSlot();}
                    } //else{slot = slots.nextSlot();}
                } else if (firstFreeSlot == -1) {
                    firstFreeSlot = slot;
                }
            }
        }

        // emptySLot
        if (numToInsert > 0 && firstFreeSlot != -1) {
            final ItemStack toInsert = item.copy();
            toInsert.stackSize = min(numToInsert, inv.getInventoryStackLimit(), toInsert.getMaxStackSize()); // some inventories like using itemstacks with invalid stack sizes
            if (sidedInv != null || inv.isItemValidForSlot(firstFreeSlot, toInsert)) {
                numInserted += toInsert.stackSize;
                numToInsert -= toInsert.stackSize;
                if (doInsert) {
                    inv.setInventorySlotContents(firstFreeSlot, toInsert);
                }
            }
        }

        if (numInserted > 0 && doInsert) {
            inv.markDirty();
        }

        return numInserted;
    }




    private interface ISlotIterator {
        int nextSlot();

        boolean hasNext();
    }

    private final static class invSlotter implements ISlotIterator {
        private static final invSlotter me = new invSlotter();
        private int end;
        private int current;

        public final static invSlotter getInstance(int start, int end) {
            me.end = end;
            me.current = start;
            return me;
        }

        @Override
        public final int nextSlot() {
            return current++;
        }

        @Override
        public final boolean hasNext() {
            return current < end;
        }
    }

    private final static class sidedSlotter implements ISlotIterator {
        private static final sidedSlotter me = new sidedSlotter();
        private int[] slots;
        private int current;

        public final static sidedSlotter getInstance(int[] slots) {
            me.slots = slots;
            me.current = 0;
            return me;
        }

        @Override
        public final int nextSlot() {
            return slots[current++];
        }

        @Override
        public final boolean hasNext() {
            return slots != null && current < slots.length;
        }
    }

    private final static int min(int i1, int i2, int i3) {
        return i1 < i2 ? (i1 < i3 ? i1 : i3) : (i2 < i3 ? i2 : i3);
    }

    public static boolean areStackMergable(ItemStack s1, ItemStack s2) {
        if (s1 == null || s2 == null || !s1.isStackable() || !s2.isStackable()) {
            return false;
        }
        if (!s1.isItemEqual(s2)) {
            return false;
        }
        return ItemStack.areItemStackTagsEqual(s1, s2);
    }


    public static boolean equals(ItemStack first, ItemStack second) {
        return (Comparators.ID_COMPARATOR.compare(first, second) == 0);
    }

    public static boolean equalsIgnoreStackSize(ItemStack itemStack1, ItemStack itemStack2) {
        return equals(clone(itemStack1, 1), clone(itemStack2, 1));
    }

    public static int compare(ItemStack itemStack1, ItemStack itemStack2) {
        return Comparators.ID_COMPARATOR.compare(itemStack1, itemStack2);
    }


}


