package com.ultraflash.equi3ae.proxy;

import com.ultraflash.equi3ae.handler.GuiHandlerE3AE;
import com.ultraflash.equi3ae.equi3ae;
import com.ultraflash.equi3ae.utility.LogHelper;
import cpw.mods.fml.common.network.NetworkRegistry;

public abstract class CommonProxy implements IProxy
{
    public void registerEventHandlers()
    {
        LogHelper.info("REGISTER GUI");
        NetworkRegistry.INSTANCE.registerGuiHandler(equi3ae.instance, new GuiHandlerE3AE());
    }


}
