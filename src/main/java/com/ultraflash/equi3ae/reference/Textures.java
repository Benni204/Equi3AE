package com.ultraflash.equi3ae.reference;

import com.ultraflash.equi3ae.utility.ResourceLocationHelper;
import net.minecraft.util.ResourceLocation;
public class Textures {

    public static final String RESOURCE_PREFIX = Reference.LOWERCASE_MOD_ID + ":";
    protected static final String GUI_TEXTURE_LOCATION = "textures/gui/";


    public static final class Gui
    {
        protected static final String GUI_TEXTURE_LOCATION = "textures/gui/";
        public static final ResourceLocation EMC_FILTER = ResourceLocationHelper.getResourceLocation(GUI_TEXTURE_LOCATION + "emcfilter.png");

    }


    public static final class Elements
    {
        protected static final String ELEMENT_TEXTURE_LOCATION = GUI_TEXTURE_LOCATION + "elements/";

    }

    public static final class Container
    {
        protected static final String CONTAINER_TEXTURE_LOCATION = GUI_TEXTURE_LOCATION + "container/";
        public static final ResourceLocation EMC_FILTER = ResourceLocationHelper.getResourceLocation(CONTAINER_TEXTURE_LOCATION + "emcfilter.png");
        public static final ResourceLocation EMC_CONVERTER = ResourceLocationHelper.getResourceLocation(CONTAINER_TEXTURE_LOCATION + "emcconverter.png");
    }

}
