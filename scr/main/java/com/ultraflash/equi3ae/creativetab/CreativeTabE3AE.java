package com.ultraflash.equi3ae.creativetab;

import com.ultraflash.equi3ae.init.ModItems;
import com.ultraflash.equi3ae.reference.Reference;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;

/**
 * Created by Benni on 20.06.2016.
 */
public class CreativeTabE3AE
{
    public static final CreativeTabs E3AE_TAB=new CreativeTabs(Reference.MOD_ID) {
        @Override
        public Item getTabIconItem() {
            return ModItems.distortedSingularity;
        }

        @Override
        public String getTranslatedTabLabel()
        {
            return "Equi3 AE3 Bride";
        }
    };
}
