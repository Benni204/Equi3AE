package com.ultraflash.equi3ae.block;

import appeng.api.AEApi;
import com.ultraflash.equi3ae.creativetab.CreativeTabE3AE;
import com.ultraflash.equi3ae.equi3ae;
import com.ultraflash.equi3ae.reference.GUIs;
import com.ultraflash.equi3ae.tileentity.TileEntityEmcConverter;
import com.ultraflash.equi3ae.tileentity.TileEntityEmcFilter;
import com.ultraflash.equi3ae.utility.LogHelper;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

/**

 */
public class BlockEmcConverter extends BlockTileEntityE3AE {

    @SideOnly(Side.CLIENT)
    protected IIcon blockIcon, frontIcon;
    public static int renderId;

    public BlockEmcConverter()
    {
        super(Material.iron);
        this.setHardness(1.5f);
        this.setResistance(6.0f);
        this.setBlockName("emcconverter");
        this.setBlockTextureName("emcconverter");
        this.setCreativeTab(CreativeTabE3AE.E3AE_TAB);
    }


    @Override
    public TileEntity createNewTileEntity(World world, int metaData)
    {


            return new TileEntityEmcConverter();

    }


    @Override
    public int damageDropped(int metaData)
    {
        return metaData;
    }

    @Override
    public boolean renderAsNormalBlock()
    {
        return false;
    }

    @Override
    public boolean isOpaqueCube()
    {
        return false;
    }

    @Override
    public int getRenderType()
    {
        return renderId;
    }


    @Override
    public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int par6, float par7, float par8, float par9)
    {
        TileEntityEmcConverter te =(TileEntityEmcConverter) world.getTileEntity(x, y, z);


        if (!world.isRemote) {

          LogHelper.info("I"+te.getinternalEMC()+" S"+te.storedEMC);
            player.openGui(equi3ae.instance, GUIs.EMCCONVERTER.ordinal(), world, x,y, z);
        }
        return true;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(IIconRegister iconRegister)
    {
        blockIcon = iconRegister.registerIcon(String.format("%s", getUnwrappedUnlocalizedName(this.getUnlocalizedName())));
        frontIcon = iconRegister.registerIcon(String.format("%s", getUnwrappedUnlocalizedName(this.getUnlocalizedName()) + "_front"));
    }


    @Override
    @SideOnly(Side.CLIENT)
    public IIcon getIcon(int blockSide, int blockMeta) {
        // This is used to render the block as an item


        ForgeDirection forgeDirection = ForgeDirection.getOrientation(blockSide);
        if (forgeDirection == ForgeDirection.SOUTH )
        {

                return this.frontIcon;

        }


        return this.blockIcon;

    }

    @Override
    @SideOnly(Side.CLIENT)
    public IIcon getIcon(IBlockAccess world, int x, int y, int z, int blockSide)
    {
        // used to render the block in the world
        TileEntity te = world.getTileEntity(x, y, z);
        int facing = 3;
        if(te instanceof TileEntityEmcConverter) {
            TileEntityEmcConverter me = (TileEntityEmcConverter) te;
            facing = me.getOrientation().ordinal();
            if (facing==blockSide )
            {
                return this.frontIcon;

            }

        }

        return this.blockIcon;

        /*
        ForgeDirection forgeDirection = ForgeDirection.getOrientation(side);
        if (forgeDirection == ForgeDirection.SOUTH )
        {

                return this.frontIcon;

        }


        return this.blockIcon;
        */
    }


}
