package com.ultraflash.equi3ae.block;

import appeng.api.implementations.tiles.IMEChest;
import appeng.api.storage.ITerminalHost;
import appeng.helpers.IPriorityHost;
import appeng.util.IConfigManagerHost;
import com.ultraflash.equi3ae.creativetab.CreativeTabE3AE;
import com.ultraflash.equi3ae.equi3ae;
import com.ultraflash.equi3ae.reference.GUIs;
import com.ultraflash.equi3ae.reference.Names;
import com.ultraflash.equi3ae.utility.LogHelper;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import net.minecraftforge.fluids.IFluidHandler;

/**
 * Created by Benni on 27.06.2016.
 */
public class BlockEmcStorageBase extends BlockE3AE
{
    public BlockEmcStorageBase() {
        super(Material.iron);
        this.setHardness(1.5f);
        this.setResistance(6.0f);
        this.setBlockName(Names.Blocks.EMCSTORAGEBASE);
        this.setBlockTextureName(Names.Blocks.EMCSTORAGEBASE);
        this.setCreativeTab(CreativeTabE3AE.E3AE_TAB);

    }

    @Override
    public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int par6, float par7, float par8, float par9)
    {
        if (world.isRemote) {
            LogHelper.info("Clicked");
        }
        return true;
    }



}
