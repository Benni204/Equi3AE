package com.ultraflash.equi3ae.reference;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.util.IIcon;

/**
 * Created by Benni on 28.06.2016.
 */
@SideOnly(Side.CLIENT)
public enum TextureManager
        {
            BUS_COLOR (TextureTypes.Part, new String[] { "bus.color.border", "bus.color.light", "bus.color.side" }),

            ESSENTIA_TERMINAL (TextureTypes.Part, new String[] { "emc.terminal.overlay.dark", "emc.terminal.overlay.medium",
                    "emc.terminal.overlay.light", "emc.terminal.side", "emc.terminal.border" });

            private enum TextureTypes
        {
            Block,
            Part;
        }


            public static final TextureManager[] VALUES = TextureManager.values();

            private TextureTypes textureType;

            private String[] textureNames;

            private IIcon[] textures;


            private TextureManager( final TextureTypes textureType, final String[] textureNames )
            {
                this.textureType = textureType;
                this.textureNames = textureNames;
                this.textures = new IIcon[this.textureNames.length];
            }

            public IIcon getTexture()
            {
                return this.textures[0];
            }

            public IIcon[] getTextures()
            {
                return this.textures;
            }


            public void registerTexture( final TextureMap textureMap )
            {
                if( textureMap.getTextureType() == 0 )
                {
                    String header = Reference.MOD_ID + ":";

                    if( this.textureType == TextureTypes.Part )
                    {
                        header += "parts/";
                    }

                    for( int i = 0; i < this.textureNames.length; i++ )
                    {
                        this.textures[i] = textureMap.registerIcon( header + this.textureNames[i] );
                    }
                }
            }




        }
