package com.ultraflash.equi3ae.item;

//import com.ultraflash.equi3ae.creativetab.CreativeTabLMRB;
import appeng.core.features.*;
import appeng.items.AEBaseItem;
import com.google.common.base.Optional;
import com.ultraflash.equi3ae.creativetab.CreativeTabE3AE;
import com.ultraflash.equi3ae.reference.Reference;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import java.util.EnumSet;
import java.util.List;

public abstract class ItemE3AEbase extends Item implements IAEFeature
{
   // private final String fullName;
    //private final Optional<String> subName;
    private IFeatureHandler feature;

    public ItemE3AEbase()
    {
        super();
      //  this(Optional.absent());
        this.setNoRepair();
        this.setCreativeTab(CreativeTabE3AE.E3AE_TAB);
    }

    public ItemE3AEbase(Optional<String> subName)
    {
       // this.subName = subName;
        //this.fullName = (new FeatureNameExtractor(this.getClass(), subName)).get();
    }

    public void setFeature(EnumSet<AEFeature> f) {
      //  this.feature = new ItemFeatureHandler(f, this, this, this.subName);
        }

   // public String toString() {
     //   return this.fullName;
    //}

    public IFeatureHandler handler() {
        return this.feature;
    }

    public void postInit() {
    }
    public final void addInformation(ItemStack stack, EntityPlayer player, List lines, boolean displayMoreInfo) {
        this.addCheckedInformation(stack, player, lines, displayMoreInfo);
    }

    public final void getSubItems(Item sameItem, CreativeTabs creativeTab, List itemStacks) {
        this.getCheckedSubItems(sameItem, creativeTab, itemStacks);
    }

    public boolean isBookEnchantable(ItemStack itemstack1, ItemStack itemstack2) {
        return false;
    }

    protected void addCheckedInformation(ItemStack stack, EntityPlayer player, List<String> lines, boolean displayMoreInfo) {
        super.addInformation(stack, player, lines, displayMoreInfo);
    }

    protected void getCheckedSubItems(Item sameItem, CreativeTabs creativeTab, List<ItemStack> itemStacks) {
        super.getSubItems(sameItem, creativeTab, itemStacks);
    }

    @Override
    public String getUnlocalizedName()
    {
        return String.format("item.%s%s", Reference.MOD_ID.toLowerCase() + ":", getUnwrappedUnlocalizedName(super.getUnlocalizedName()));
    }

    @Override
    public String getUnlocalizedName(ItemStack itemStack)
    {
        return String.format("item.%s%s", Reference.MOD_ID.toLowerCase() + ":", getUnwrappedUnlocalizedName(super.getUnlocalizedName()));
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerIcons(IIconRegister iconRegister)
    {
        itemIcon = iconRegister.registerIcon(this.getUnlocalizedName().substring(this.getUnlocalizedName().indexOf(".") + 1));
    }

    protected String getUnwrappedUnlocalizedName(String unlocalizedName)
    {
        return unlocalizedName.substring(unlocalizedName.indexOf(".") + 1);
    }
}
