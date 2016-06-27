package com.ultraflash.equi3ae.client.gui.Inventory;

import com.ultraflash.equi3ae.inventory.ContainerConverter;
import com.ultraflash.equi3ae.inventory.ContainerEmcFilter;
import com.ultraflash.equi3ae.reference.Textures;
import com.ultraflash.equi3ae.tileentity.TileEntityEmcConverter;
import com.ultraflash.equi3ae.tileentity.TileEntityEmcFilter;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.inventory.IInventory;

//import com.ultraflash.equi3ae.reference.Colors;

public class GuiEmcConverter extends GuiContainer
{
    private IInventory playerInv;
    private TileEntityEmcConverter te;

        public GuiEmcConverter(IInventory playerInv, TileEntityEmcConverter te) {
            super(new ContainerConverter(playerInv, te));


            this.playerInv = playerInv;
            this.te = te;

            this.xSize = 176;
            this.ySize = 166;
        }

    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
        //GL.color(1.0f, 1.0f, 1.0f, 1.0f);
        this.mc.getTextureManager().bindTexture(Textures.Container.EMC_CONVERTER);
        this.drawTexturedModalRect(this.guiLeft, this.guiTop, 0, 0, this.xSize, this.ySize);
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY)
    {
       // scaleAdjustment = this.TileEntityEmcConverter;
        //this.drawTexturedModalRect(xStart + 46, yStart + 22 + 23 - scaleAdjustment, 176, 12 - scaleAdjustment, 14, scaleAdjustment + 2);
        String sPr = (String) ((int )(this.te.lastEmc)+"");
        String sInt = (String) ((int )(this.te.internalEMC)+"");
        String sBuf = (String) ((int )(this.te.storedEMC)+"");
        String s = this.te.getInventoryName();

        this.fontRendererObj.drawString(sPr, 15 - this.fontRendererObj.getStringWidth(sPr) / 2, 72, 4210752);
        this.fontRendererObj.drawString(sInt, 35 - this.fontRendererObj.getStringWidth(sInt) / 2, 72, 4210752);
        this.fontRendererObj.drawString(sBuf, 65 - this.fontRendererObj.getStringWidth(sBuf) / 2, 72, 4210752);
        this.fontRendererObj.drawString(s, 88 - this.fontRendererObj.getStringWidth(s) / 2, 6, 4210752);
        //#404040
      //  this.fontRendererObj.drawString(this.playerInv.getInventoryName(), 8, 72, 4210752);      //#404040
    }
}
