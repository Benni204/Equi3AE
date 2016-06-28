package com.ultraflash.equi3ae.grid;

import appeng.api.networking.energy.IEnergyGrid;
import appeng.api.networking.security.BaseActionSource;
import appeng.api.networking.storage.IBaseMonitor;
import appeng.api.storage.IMEMonitor;
import appeng.api.storage.IMEMonitorHandlerReceiver;
import appeng.api.storage.data.IAEFluidStack;
import appeng.api.storage.data.IItemList;
import com.ultraflash.equi3ae.fluid.emcF;
import com.ultraflash.equi3ae.init.ModItems;
import com.ultraflash.equi3ae.storage.EmcStack;

import java.lang.ref.WeakReference;

/**
 * Created by Benni on 27.06.2016.
 */
public class EmcScreen implements IMEMonitorHandlerReceiver<IAEFluidStack>

{

    protected IMEMonitor<IAEFluidStack> fluidMonitor;

    private final EmcStack cache;

    protected IEnergyGrid energyGrid;
    protected boolean cacheNeedsUpdate = false;
    private WeakReference<Object> token;


    public EmcScreen() {
        this.cache = new EmcStack(0) ;
    }

    public EmcScreen(final IMEMonitor<IAEFluidStack> fluidMonitor, final IEnergyGrid energyGrid, final Object validationToken ) {


        this();
        this.wrap( fluidMonitor, energyGrid, validationToken );


    }

    @SuppressWarnings("null")
    protected void updateCacheToMatchNetwork()
    {

        if( this.energyGrid.isNetworkPowered() )
        {

            IItemList<IAEFluidStack> fluidStackList =  this.fluidMonitor.getStorageList();

            // Loop over all fluids
            for( IAEFluidStack fluidStack : fluidStackList )
            {
                if( !( fluidStack.getFluid() instanceof emcF) )
                {
                    // Not an essentia gas.
                    continue;
                }

                // Calculate the new amount
                        Long newAmount =( fluidStack.getStackSize() )/((int)Math.pow( 2, 7));

                // Update the cache
                EmcStack prevStack =(EmcStack) this.cache.setStackSize( newAmount );


            }
        }
        this.cacheNeedsUpdate = false;

    }


    @Override
    public boolean isValid(Object verificationToken) {

        // Has a token been assigned?
        if( this.token != null )
        {
            // Does the token match?
            if( this.token.equals( verificationToken ) )
            {
                // Does the token still exist?
                Object vToken = this.token.get();
                if( vToken != null )
                {
                    // Return true
                    return true;
                }
            }
        }

        return false;
    }



    @Override
    public void postChange(IBaseMonitor<IAEFluidStack> monitor, Iterable<IAEFluidStack> changeFluid, BaseActionSource actionSource) {
        // Ensure the cache is up to date
        if( this.cacheNeedsUpdate ) {
            // No use updating an out of sync cache
            return;
        }

        if( changeFluid == null )
        {
            return;
        }

        for( IAEFluidStack change : changeFluid )
        {
            if( ( change.getFluid() instanceof emcF ) )
            {

                Long newAmount =( change.getStackSize() )/((int)Math.pow( 2, 7));

                // Update the cache
                EmcStack  previous =(EmcStack) this.cache.setStackSize( newAmount );

                EmcStack changeStack;
                if(previous!= null)
                {
                    changeStack = previous;
                    previous.setStackSize( newAmount );


                }else
                {
                    changeStack = new EmcStack( newAmount );

                }




            }

        }



    }

    @Override
    public void onListUpdate()
    {

        this.cacheNeedsUpdate = true;


    }

    public void wrap( final IMEMonitor<IAEFluidStack> fluidMonitor, final IEnergyGrid energyGrid, final Object validationToken )
    {
        // Ensure the token is not null
        if( validationToken != null )
        {
            // Set the token
            this.token = new WeakReference<Object>( validationToken );
        }
        else
        {
            // Throw exception
            throw new NullPointerException( "Validation Token Can Not Be Null" );
        }

        // Set the fluid monitor
        this.fluidMonitor = fluidMonitor;

        // Set the energy grid
        this.energyGrid = energyGrid;

        // Add listener
        this.fluidMonitor.addListener( this, this.token );

        // Mark that the cache needs to be updated
        this.cacheNeedsUpdate = true;
    }




}
