package com.ultraflash.equi3ae.reference;

import com.ultraflash.equi3ae.equi3ae;
import net.minecraft.util.StatCollector;

/**
 * Created by Benni on 28.06.2016.
 */
    public enum StringE3AE {

        // Blocks


        // Parts
        PartEmcTerminal("aeparts.emc.storage.terminal",true),
        PartEmcMonitor("aeparts.emc.storage.monitor",true);

        // Items


        // Tooltips


        // GUI


    private String unlocalized;
    private boolean isDotName;

    private StringE3AE( final String unloc, final boolean isDotName )
    {
        this.unlocalized = Reference.MOD_ID + "." + unloc;
        this.isDotName = isDotName;
    }


    public String getLocalized()
    {
        if( this.isDotName )
        {
            return StatCollector.translateToLocal( this.unlocalized + ".name" );
        }

        return StatCollector.translateToLocal( this.unlocalized );
    }

    /**
     * Gets the unlocalized string.
     *
     * @return
     */
    public String getUnlocalized()
    {
        return this.unlocalized;
    }


}
