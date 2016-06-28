package com.ultraflash.equi3ae.init;

import com.ultraflash.equi3ae.fluid.emcF;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;

/**
 * Created by Benni on 27.06.2016.
 */
public class ModFluids
{

    public static final Fluid emcF = new emcF();

    public static void init()
    {
        FluidRegistry.registerFluid(emcF);

    }


}
