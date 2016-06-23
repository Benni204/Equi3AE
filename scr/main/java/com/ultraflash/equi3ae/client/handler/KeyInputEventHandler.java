package com.ultraflash.equi3ae.client.handler;

import com.ultraflash.equi3ae.client.settings.Keybindings;
import com.ultraflash.equi3ae.reference.Key;
import com.ultraflash.equi3ae.utility.LogHelper;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.InputEvent;

/**
 * Created by Benni on 20.06.2016.
 */
public class KeyInputEventHandler
{
    private static Key getPressedKeybinding()
    {
        if(Keybindings.charge.isPressed())
        {
            return Key.CHARGE;
        }
        else if (Keybindings.release.isPressed() )
        {
            return Key.RELEASE;
        }

        return Key.UNKNOWN;

    }

    @SubscribeEvent
    public void handleKeyInputEvent(InputEvent.KeyInputEvent event)
    {
        //LogHelper.info(getPressedKeybinding());
    }
}
