package com.ultraflash.equi3ae.item;

import appeng.api.config.FuzzyMode;
import appeng.api.config.IncludeExclude;
import appeng.api.exceptions.MissingDefinition;
import appeng.api.implementations.items.IItemGroup;
import appeng.api.storage.*;
import appeng.api.storage.data.IAEItemStack;
import appeng.core.AEConfig;
import appeng.core.features.AEFeature;
import appeng.core.localization.GuiText;
import appeng.items.contents.CellConfig;
import appeng.items.contents.CellUpgrades;
import appeng.util.Platform;
import com.google.common.base.Optional;
import com.ultraflash.equi3ae.api.IEmcStorageCell;
import com.ultraflash.equi3ae.api.IHandlerEmcStorage;
import com.ultraflash.equi3ae.creativetab.CreativeTabE3AE;
import com.ultraflash.equi3ae.init.ModItems;
import com.ultraflash.equi3ae.inventory.ECInventory;
import com.ultraflash.equi3ae.reference.Names;
import com.ultraflash.equi3ae.reference.Reference;
import com.ultraflash.equi3ae.tileentity.TileEntityEmcFilter;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.util.MathHelper;

import appeng.api.AEApi;
import appeng.api.config.FuzzyMode;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;


import java.util.EnumSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

/**
 * Created by Benni on 26.06.2016.
 */
public class ItemEMCStorageCell extends ItemE3AE implements IEmcStorageCell, IItemGroup
{

    public static final String[] suffixes = { "1k", "4k", "16k", "64k"};
    public static final int[] spaces = { 1, 4, 16, 64 };
    private IIcon[] icons;
    private final int celltype;
    private final int totalBytes;
    private final int perType;
    private final double idleDrain;


    private float storedEmc;

    public ItemEMCStorageCell(int mode)

    {
     //   super(Optional.of(kilobytes + "k"));
        super();
        this.setUnlocalizedName("emcstoragecell");
        this.setCreativeTab(CreativeTabE3AE.E3AE_TAB);
        setMaxStackSize(1);
        this.celltype=mode;
        this.totalBytes = spaces[mode] * 1024;
        this.storedEmc=0.0f;
      //  this.setFeature(EnumSet.of(AEFeature.StorageCells));

        switch(mode)
        {
            case 1:
                this.idleDrain = 0.5D;
                this.perType = 8;
                break;
            case 2:
                this.idleDrain = 1.0D;
                this.perType = 32;
                break;
            default:
                this.idleDrain = 0.5D;
                this.perType = 8;
        }



    }

   // @SuppressWarnings({ "rawtypes", "unchecked" })
    @Override
    public void addInformation(ItemStack stack, EntityPlayer player, List lines, boolean moreInfo)
    {
        IMEInventoryHandler inventory = AEApi.instance().registries().cell().getCellInventory(stack, (ISaveProvider)null, StorageChannel.ITEMS);

        if(inventory instanceof ICellInventoryHandler)
        {
            ICellInventoryHandler handler = (ICellInventoryHandler) inventory;
            ICellInventory cellInventory = handler.getCellInv();
            if(cellInventory != null)
            {
                lines.add(cellInventory.getUsedBytes() + " " + GuiText.Of.getLocal() + ' ' + cellInventory.getTotalBytes() + ' ' + GuiText.BytesUsed.getLocal());
                lines.add(cellInventory.getStoredItemTypes() + " " + GuiText.Of.getLocal() + ' ' + cellInventory.getTotalItemTypes() + ' ' + GuiText.Types.getLocal());
                if(handler.isPreformatted())
                {
                    String list = (handler.getIncludeExcludeMode() == IncludeExclude.WHITELIST?GuiText.Included:GuiText.Excluded).getLocal();
                    if(handler.isFuzzy()) {
                        lines.add(GuiText.Partitioned.getLocal() + " - " + list + ' ' + GuiText.Fuzzy.getLocal());
                    } else {
                        lines.add(GuiText.Partitioned.getLocal() + " - " + list + ' ' + GuiText.Precise.getLocal());
                    }
                }
            }


        }





        //ICellRegistry cellRegistry = AEApi.instance().registries().cell();

/*
        ICellInventoryHandler inventoryHandler = (ICellInventoryHandler) invHandler;
        ICellInventory cellInv = inventoryHandler.getCellInv();
        long usedBytes = cellInv.getUsedBytes();

        list.add(String.format(StatCollector
                        .translateToLocal("equi3ae.tooltip.storage.emc.bytes"),
                usedBytes, cellInv.getTotalBytes()));
        list.add(String.format(StatCollector
                        .translateToLocal("equi3ae.tooltip.storage.emc.types"),
                cellInv.getStoredItemTypes(), cellInv.getTotalItemTypes()));
        if (usedBytes > 0)
            list.add(String.format(
                    StatCollector
                            .translateToLocal("equi3ae.tooltip.storage.emc.content"),
                    cellInv.getStoredItemCount()));


       /*
            IMEInventoryHandler<IAEItemStack> handler =AEApi.instance().registries().cell().getCellInventory(itemStack, null, StorageChannel.ITEMS);

       IHandlerEmcStorage cellHandler= (IHandlerEmcStorage) handler;
        long usedBytes = cellHandler.usedBytes();

        list.add(String.format(StatCollector.translateToLocal("equi3ae.tooltip.storage.emc.bytes"),cellHandler.usedBytes(),cellHandler.totalBytes()) );
        if (usedBytes != 0) {
            list.add(String.format(StatCollector.translateToLocal("equi3ae.tooltip.storage.emc.content"), usedBytes));
        }

*/

    }

    public int getBytes(ItemStack cellItem) {
        return this.totalBytes;
    }

    public int BytePerType(ItemStack cellItem) {
        return this.perType;
    }

    public int getBytesPerType(ItemStack cellItem) {
      return  this.perType;
        //return spaces[Math.max(0, cellItem.getItemDamage())];
    }

    public int getTotalTypes(ItemStack cellItem) {
        return 1;
    }


    public boolean isBlackListed(ItemStack cellItem, IAEItemStack requestedAddition) {
         if(requestedAddition.getItem()==ModItems.rawemc)
        {
        return false;
        }
        return true;
    }

    public boolean storableInStorageCell() {
        return false;
    }

    public boolean isStorageCell(ItemStack i) {
        return true;
    }

    public double getIdleDrain() {
        return this.idleDrain;
    }

    public String getUnlocalizedGroupName(Set<ItemStack> others, ItemStack is) {
        return Names.Items.EMCSTORAGECELL;
       // return GuiText.StorageCells.getUnlocalized();
    }


    public boolean isEditable(ItemStack is) {
        return false;
    }

    public IInventory getUpgradesInventory(ItemStack is) {
        return new CellUpgrades(is, 2);
    }

    public IInventory getConfigInventory(ItemStack is) {
        return new CellConfig(is);
    }

    public FuzzyMode getFuzzyMode(ItemStack is) {
        String fz = Platform.openNbtData(is).getString("FuzzyMode");

        try {
            return FuzzyMode.valueOf(fz);
        } catch (Throwable var4) {
            return FuzzyMode.IGNORE_ALL;
        }
    }

    public void setFuzzyMode(ItemStack is, FuzzyMode fzMode) {
        Platform.openNbtData(is).setString("FuzzyMode", fzMode.name());
    }

    public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player) {
        return stack;
    }

    public boolean onItemUseFirst(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ) {
        return false;
    }

    public ItemStack getContainerItem(ItemStack itemStack) {

        return itemStack;
    }


    public boolean hasContainerItem(ItemStack stack) {
        return false;//AEConfig.instance.isFeatureEnabled(AEFeature.EnableDisassemblyCrafting);
    }

    @Override
    public String getUnlocalizedName(ItemStack itemStack) {
        return String.format("item.%s%s", Reference.MOD_ID.toLowerCase() + ":","emcstoragecell."+suffixes[celltype]);
        //return "extracells.item.storage.gas." + suffixes[itemStack.getItemDamage()];
    }

    @Override
    public void registerIcons(IIconRegister iconRegister) {

       /* this.icons = new IIcon[suffixes.length];

        for (int i = 0; i < suffixes.length; ++i) {
            this.icons[i] = iconRegister.registerIcon( Reference.MOD_ID.toLowerCase()+ ":"+"emcstoragecell." + suffixes[i]);
        }*/

        itemIcon = iconRegister.registerIcon(this.getUnlocalizedName().substring(this.getUnlocalizedName().indexOf(".") + 1)+"."+suffixes[celltype]);

    }


}
