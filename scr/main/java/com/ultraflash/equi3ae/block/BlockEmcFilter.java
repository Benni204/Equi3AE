package com.ultraflash.equi3ae.block;

import com.ultraflash.equi3ae.handler.GuiHandlerE3AE;
import com.ultraflash.equi3ae.creativetab.CreativeTabE3AE;
import com.ultraflash.equi3ae.equi3ae;
import com.ultraflash.equi3ae.reference.GUIs;
import com.ultraflash.equi3ae.reference.Textures;
import com.ultraflash.equi3ae.tileentity.TileEntityEmcFilter;
import com.ultraflash.equi3ae.utility.LogHelper;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

/**
 * Created by Benni on 20.06.2016.
 */
public class BlockEmcFilter extends BlockTileEntityE3AE {

    public BlockEmcFilter()
    {
        super(Material.iron);
        this.setHardness(1.5f);
        this.setResistance(6.0f);
        this.setBlockName("emcfilter");
        this.setBlockTextureName("emcfilter");
        this.setCreativeTab(CreativeTabE3AE.E3AE_TAB);
    }


    @Override
    public TileEntity createNewTileEntity(World world, int metaData)
    {

            return new TileEntityEmcFilter();

    }


    @Override
    public int damageDropped(int metaData)
    {
        return metaData;
    }

    @Override
    public boolean renderAsNormalBlock()
    {
        return true;
    }

    @Override
    public boolean isOpaqueCube()
    {
        return false;
    }

    @Override
    public int getRenderType()
    {
        return 0;
    }


    @Override
    public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int par6, float par7, float par8, float par9)
    {
        if (!world.isRemote) {
            LogHelper.info("Open Gui");
            player.openGui(equi3ae.instance, GUIs.EMCFILTER.ordinal(), world, x,y, z);
        }
        return true;
    }


}
