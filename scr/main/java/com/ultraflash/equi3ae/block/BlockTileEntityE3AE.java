package com.ultraflash.equi3ae.block;



import com.ultraflash.equi3ae.creativetab.CreativeTabE3AE;
import com.ultraflash.equi3ae.reference.Reference;
import com.ultraflash.equi3ae.tileentity.TileEntityE3AE;
import com.ultraflash.equi3ae.tileentity.TileEntityEmcFilter;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

import java.util.Random;

    public abstract class BlockTileEntityE3AE extends BlockContainer
    {
        protected BlockTileEntityE3AE(Material material)
        {
            super(material);
            this.setCreativeTab(CreativeTabE3AE.E3AE_TAB);
        }


        @Override
        public String getUnlocalizedName()
        {
            return String.format("tile.%s%s", Reference.MOD_ID.toLowerCase() + ":", getUnwrappedUnlocalizedName(super.getUnlocalizedName()));
        }

        protected String getUnwrappedUnlocalizedName(String unlocalizedName)
        {
            return unlocalizedName.substring(unlocalizedName.indexOf(".") + 1);
        }

        @Override
        @SideOnly(Side.CLIENT)
        public void registerBlockIcons(IIconRegister iconRegister)
        {
            blockIcon = iconRegister.registerIcon(String.format("%s", getUnwrappedUnlocalizedName(this.getUnlocalizedName())));
        }


        @Override
        public void breakBlock(World world, int x, int y, int z, Block block, int meta)
        {
            dropInventory(world, x, y, z);
            super.breakBlock(world, x, y, z, block, meta);
        }

        @Override
        public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase entityLiving, ItemStack itemStack)
        {
            super.onBlockPlacedBy(world, x, y, z, entityLiving, itemStack);
            if (world.getTileEntity(x, y, z) instanceof TileEntityE3AE)
            {
                int direction = 0;
                int facing = MathHelper.floor_double(entityLiving.rotationYaw * 4.0F / 360.0F + 0.5D) & 3;

                if (facing == 0)
                {
                    direction = ForgeDirection.NORTH.ordinal();
                }
                else if (facing == 1)
                {
                    direction = ForgeDirection.EAST.ordinal();
                }
                else if (facing == 2)
                {
                    direction = ForgeDirection.SOUTH.ordinal();
                }
                else if (facing == 3)
                {
                    direction = ForgeDirection.WEST.ordinal();
                }

                if (itemStack.hasDisplayName())
                {
                    ((TileEntityE3AE) world.getTileEntity(x, y, z)).setCustomName(itemStack.getDisplayName());
                }

                ((TileEntityE3AE) world.getTileEntity(x, y, z)).setOrientation(direction);
            }
        }

        @Override
        public AxisAlignedBB getCollisionBoundingBoxFromPool(World world, int x, int y, int z)
        {
            this.setBlockBoundsBasedOnState(world, x, y, z);
            return super.getCollisionBoundingBoxFromPool(world, x, y, z);
        }

        protected void dropInventory(World world, int x, int y, int z)
        {
            TileEntity tileEntity = world.getTileEntity(x, y, z);

            if (!(tileEntity instanceof IInventory))
            {
                return;
            }

            IInventory inventory = (IInventory) tileEntity;

            for (int i = 0; i < inventory.getSizeInventory(); i++)
            {
                ItemStack itemStack = inventory.getStackInSlot(i);

                if (itemStack != null && itemStack.stackSize > 0)
                {
                    Random rand = new Random();

                    float dX = rand.nextFloat() * 0.8F + 0.1F;
                    float dY = rand.nextFloat() * 0.8F + 0.1F;
                    float dZ = rand.nextFloat() * 0.8F + 0.1F;

                    EntityItem entityItem = new EntityItem(world, x + dX, y + dY, z + dZ, itemStack.copy());

                    if (itemStack.hasTagCompound())
                    {
                        entityItem.getEntityItem().setTagCompound((NBTTagCompound) itemStack.getTagCompound().copy());
                    }

                    float factor = 0.05F;
                    entityItem.motionX = rand.nextGaussian() * factor;
                    entityItem.motionY = rand.nextGaussian() * factor + 0.2F;
                    entityItem.motionZ = rand.nextGaussian() * factor;
                    world.spawnEntityInWorld(entityItem);
                    itemStack.stackSize = 0;
                }


            }

       }
    }

