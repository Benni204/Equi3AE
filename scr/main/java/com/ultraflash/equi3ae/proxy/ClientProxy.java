package com.ultraflash.equi3ae.proxy;

import com.ultraflash.equi3ae.client.handler.KeyInputEventHandler;
import com.ultraflash.equi3ae.client.settings.Keybindings;
import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.common.FMLCommonHandler;
import net.minecraftforge.common.MinecraftForge;

import static cpw.mods.fml.common.FMLCommonHandler.*;

public class ClientProxy extends CommonProxy{


    @Override
    public ClientProxy getClientProxy() {
        return null;
    }

    @Override
    public void registerEventHandlers()
    {
        super.registerEventHandlers();
    }

    @Override
    public void registerKeyBindings()
    {
        ClientRegistry.registerKeyBinding(Keybindings.charge);
        ClientRegistry.registerKeyBinding(Keybindings.release);
    }
}
