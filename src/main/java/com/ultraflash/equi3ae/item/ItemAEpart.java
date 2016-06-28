package com.ultraflash.equi3ae.item;

import appeng.api.AEApi;
import appeng.api.config.Upgrades;
import appeng.api.implementations.items.IItemGroup;
import appeng.api.parts.IPart;
import appeng.api.parts.IPartItem;
import com.ultraflash.equi3ae.parts.AEPartEnum;
import com.ultraflash.equi3ae.reference.Reference;
import com.ultraflash.equi3ae.utility.LogHelper;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by Benni on 28.06.2016.
 */
public class ItemAEPart
        extends Item
        implements IPartItem, IItemGroup
{
    /**
     * Constructor
     */
    public ItemAEPart()
    {
        // Undamageable
        this.setMaxDamage( 0 );



        // Has sub types
        this.setHasSubtypes( true );

        // Can be rendered on a cable.
        AEApi.instance().partHelper().setItemBusRenderer( this );

        // Register parts who can take an upgrade card.
        Map<Upgrades, Integer> possibleUpgradesList;
        for( AEPartEnum part : AEPartEnum.VALUES )
        {
            possibleUpgradesList = part.getUpgrades();

            for( Upgrades upgrade : possibleUpgradesList.keySet() )
            {
                upgrade.registerItem( new ItemStack( this, 1, part.ordinal() ), possibleUpgradesList.get( upgrade ).intValue() );
            }
        }

    }

    @Override
    public IPart createPartFromItemStack(final ItemStack itemStack )
    {
        IPart newPart = null;

        // Get the part
        AEPartEnum part = AEPartEnum.getPartFromDamageValue( itemStack );

        // Attempt to create a new instance of the part
        try
        {
            newPart = part.createPartInstance( itemStack );
        }
        catch( Throwable e )
        {
            // Bad stuff, log the error.
           LogHelper.error( e+ "Unable to create cable-part from item: %s" +itemStack.getDisplayName() );
        }

        // Return the part
        return newPart;

    }

    @Override
    public EnumRarity getRarity( final ItemStack itemStack )
    {
        return EnumRarity.rare;
    }

    @SideOnly(Side.CLIENT)
    @Override
    public int getSpriteNumber()
    {
        return 0;
    }

    @Override
    public void getSubItems(final Item item, final CreativeTabs tab, final List itemList )
    {
        // Get the number of parts
        int count = AEPartEnum.VALUES.length;

        // Add each one to the list
        for( int i = 0; i < count; i++ )
        {
            itemList.add( new ItemStack( item, 1, i ) );
        }

    }

    @Override
    public String getUnlocalizedGroupName(final Set<ItemStack> arg0, final ItemStack itemStack )
    {
        return AEPartEnum.getPartFromDamageValue( itemStack ).getGroupName();
    }

    @Override
    public String getUnlocalizedName()
    {
        return Reference.MOD_ID + ".item.aeparts";
    }

    @Override
    public String getUnlocalizedName( final ItemStack itemStack )
    {
        return AEPartEnum.getPartFromDamageValue( itemStack ).getUnlocalizedName();
    }

    @Override
    public boolean onItemUse(final ItemStack itemStack, final EntityPlayer player, final World world, final int x, final int y, final int z,
                             final int side, final float hitX, final float hitY, final float hitZ )
    {
        // Can we place the item on the bus?
        return AEApi.instance().partHelper().placeBus( itemStack, x, y, z, side, player, world );
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void registerIcons( final IIconRegister par1IconRegister )
    {
    }
}