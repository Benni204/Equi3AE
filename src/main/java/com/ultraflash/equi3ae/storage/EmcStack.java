package com.ultraflash.equi3ae.storage;

import appeng.api.config.FuzzyMode;
import appeng.api.features.IItemComparison;
import appeng.api.storage.StorageChannel;
import appeng.api.storage.data.IAEFluidStack;
import appeng.api.storage.data.IAEItemStack;
import appeng.api.storage.data.IAETagCompound;
import appeng.util.item.AEStack;
import com.ultraflash.equi3ae.init.ModFluids;
import com.ultraflash.equi3ae.init.ModItems;
import io.netty.buffer.ByteBuf;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;

import java.io.IOException;

/**
 * Created by Benni on 27.06.2016.
 */
public class EmcStack implements IAEFluidStack
{
    private static final String NBTKEY_EMC_TAG = "EmcSTag", NBTKEY_EMC_AMOUNT = "Amount", NBTKEY_CRAFTABLE = "Craftable";
    protected long stackSize;


    public EmcStack(final long amount) {
        this.stackSize =amount;
    }

    public long adjustStackSize( final long delta )
    {
        this.stackSize += delta;
        return this.stackSize;
    }


    public boolean isEmpty()
    {
        return( this.stackSize <= 0 );
    }

    @Override
    public FluidStack getFluidStack() {
        return  new FluidStack(ModFluids.emcF,(int)stackSize);
    }



    @Override
    public void add(IAEFluidStack option)
    {
        adjustStackSize(option.getStackSize());

    }

    @Override
    public long getStackSize() {
        return this.stackSize;
    }

    @Override
    public IAEFluidStack setStackSize(long stackSize) {
        this.stackSize=stackSize;
        return new EmcStack(stackSize);
    }

    @Override
    public long getCountRequestable() {
        return this.stackSize;
    }

    @Override
    public IAEFluidStack setCountRequestable(long countRequestable) {
        this.stackSize=countRequestable;
        return new EmcStack(countRequestable);
    }

    @Override
    public boolean isCraftable() {
        return false;
    }

    @Override
    public IAEFluidStack setCraftable(boolean isCraftable) {
        return new EmcStack(this.stackSize);
    }

    @Override
    public IAEFluidStack reset() {
        this.stackSize=0;
        return  new EmcStack(this.stackSize);
    }

    @Override
    public boolean isMeaningful() {
        return true;
    }

    @Override
    public void incStackSize(long i) {
        adjustStackSize(i);
    }

    @Override
    public void decStackSize(long i) {
        if(i>=this.stackSize)
        {
            this.stackSize=0;
        }else {
            adjustStackSize(i*(-1));
        }
    }

    @Override
    public void incCountRequestable(long i) {
        incStackSize(i);
    }

    @Override
    public void decCountRequestable(long i) {
        decStackSize(i);
    }

    @Override
    public void writeToNBT(NBTTagCompound data)
    {
        // Write the amount
        if( this.stackSize > 0 )
        {
            data.setString( NBTKEY_EMC_TAG, "EMC-Value" );
            data.setLong( NBTKEY_EMC_AMOUNT, this.stackSize );
        }

    }

    @Override
    public boolean fuzzyComparison(Object st, FuzzyMode mode) {
        if(((EmcStack) st).stackSize==  this.stackSize)return true;

        return false;
    }

    @Override
    public void writeToPacket(ByteBuf data) throws IOException
    {
        data.writeLong(this.stackSize);

    }

    @Override
    public IAEFluidStack copy() {
        return new EmcStack(this.stackSize);
    }

    @Override
    public IAEFluidStack empty() {
        return new EmcStack(0);
    }

    @Override
    public IAETagCompound getTagCompound() {

        return null ;
    }

    @Override
    public boolean isItem() {
        return false;
    }

    @Override
    public boolean isFluid() {
        return true;
    }

    @Override
    public StorageChannel getChannel() {
        return StorageChannel.FLUIDS;
    }

    @Override
    public Fluid getFluid() {
        return new Fluid("emc");
    }
}
