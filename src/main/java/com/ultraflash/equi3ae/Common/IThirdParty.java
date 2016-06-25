package com.ultraflash.equi3ae.Common;

/**
 * Created by Benni on 24.06.2016.
 */
public interface IThirdParty
{

    public void preInit();

    public void init();

    public void postInit();

    /**
     * Gets called from the ClientProxy in the preInit.
     */
    public void clientSide();

    /**
     * Gets called from the ClientProxy in the Init.
     */
    public void clientInit();

}
