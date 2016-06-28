package com.ultraflash.equi3ae.grid;

import appeng.api.networking.IGrid;
import appeng.api.networking.IGridHost;
import appeng.api.networking.IGridNode;
import appeng.api.networking.IGridStorage;
import appeng.api.networking.energy.IEnergyGrid;
import appeng.api.networking.events.MENetworkEventSubscribe;
import appeng.api.networking.events.MENetworkPostCacheConstruction;
import appeng.api.networking.storage.IStorageGrid;
import com.ultraflash.equi3ae.api.IEmcGrid;

/**
 * Created by Benni on 27.06.2016.
 */
public class GridEmcCache extends EmcScreen implements IEmcGrid
{

    /**
     * Grid the cache is part of.
     */
    final IGrid internalGrid;

   // private final CraftingAspect_ItemWatcher craftingWatcher;


    public GridEmcCache(final IGrid grid)
    {

        this.internalGrid = grid;


    }


    void markForUpdate()
    {
        this.cacheNeedsUpdate = true;
    }


    @MENetworkEventSubscribe
    public void onGridCacheReady( final MENetworkPostCacheConstruction event )
    {
        // Get the storage grid
        IStorageGrid storage = (IStorageGrid)this.internalGrid.getCache( IStorageGrid.class );

        // Wrap
        this.wrap( storage.getFluidInventory(), (IEnergyGrid)this.internalGrid.getCache( IEnergyGrid.class ), this.internalGrid );

        // Set the crafting watcher
     //   storage.getItemInventory().addListener( this.craftingWatcher, this.internalGrid );
    }


    @Override
    public void onJoin( final IGridStorage sourceStorage )
    {
        // Mark that the cache needs to be updated
        this.cacheNeedsUpdate = true;
    }




    @Override
    public long getEmcAmount() {
        return 0;
    }

    @Override
    public void onUpdateTick()
    {

        try
        {
			/*
			 * If the cache is invalid and there are listeners this will update the cache to match the network.
			 * If there are no listeners the update is deferred until there are listeners, or the cache is accessed.
			 */
            if( this.cacheNeedsUpdate  )
            {
                // Update the cache
                this.updateCacheToMatchNetwork();
            }
        }
        catch( Exception e )
        {
            // Ignored
        }


    }

    @Override
    public void removeNode(IGridNode gridNode, IGridHost machine)
    {

    }

    @Override
    public void addNode(IGridNode gridNode, IGridHost machine)
    {
// Does the node wish to watch for changes?
        //if( machine instanceof IEssentiaWatcherHost )
        {
            // Cast
         //   IEssentiaWatcherHost host = (IEssentiaWatcherHost)machine;

            // Create the watcher
           // EssentiaWatcher watcher = new EssentiaWatcher( this.essentiaWatcherManger, host );

            // Add to the watcher manager
            //this.essentiaWatcherManger.addWatcher( gridNode, watcher );

            // Inform the host it has a watcher
            //host.updateWatcher( watcher );
        }

    }

    @Override
    public void onSplit(IGridStorage destinationStorage) {

    }


    @Override
    public void populateGridStorage(IGridStorage destinationStorage) {

    }
}
