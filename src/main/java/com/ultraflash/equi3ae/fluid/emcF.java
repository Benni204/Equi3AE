package com.ultraflash.equi3ae.fluid;

import com.ultraflash.equi3ae.reference.Reference;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraftforge.fluids.Fluid;

/**
 * Created by Benni on 27.06.2016.
 */
public class emcF extends Fluid {

    public emcF() {
        super("emc");
        // Gas slightly glows
        this.setLuminosity( 7 );

        // Negative density, it floats away!
        this.setDensity( -4 );

        // Flow speed, 3x slower than water
        this.setViscosity( 3000 );

        // This is a gas, adjusts the render pass.
        this.setGaseous( true );
    }


    @Override
    public String getUnlocalizedName()
    {
        return String.format("item.%s%s", Reference.MOD_ID.toLowerCase() + ":", getUnwrappedUnlocalizedName(super.getUnlocalizedName()));
    }


    protected String getUnwrappedUnlocalizedName(String unlocalizedName)
    {
        return unlocalizedName.substring(unlocalizedName.indexOf(".") + 1);
    }
}
