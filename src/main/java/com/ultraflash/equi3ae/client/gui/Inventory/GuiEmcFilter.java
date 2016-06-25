package com.ultraflash.equi3ae.client.gui.Inventory;
import com.ultraflash.equi3ae.inventory.ContainerEmcFilter;
import com.ultraflash.equi3ae.tileentity.TileEntityE3AE;
import net.minecraft.client.gui.inventory.GuiContainer;

//import com.ultraflash.equi3ae.reference.Colors;
import com.ultraflash.equi3ae.reference.Colors;
import com.ultraflash.equi3ae.reference.Names;
import com.ultraflash.equi3ae.reference.Textures;
import com.ultraflash.equi3ae.tileentity.TileEntityEmcFilter;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;
import org.lwjgl.opengl.GL11;

public class GuiEmcFilter extends GuiContainer
{
    private IInventory playerInv;
    private TileEntityEmcFilter te;

        public GuiEmcFilter(IInventory playerInv, TileEntityEmcFilter te) {
            super(new ContainerEmcFilter(playerInv, te));


            this.playerInv = playerInv;
            this.te = te;

            this.xSize = 176;
            this.ySize = 166;
        }

    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
        //GL.color(1.0f, 1.0f, 1.0f, 1.0f);
        this.mc.getTextureManager().bindTexture(Textures.Container.EMC_FILTER);
        this.drawTexturedModalRect(this.guiLeft, this.guiTop, 0, 0, this.xSize, this.ySize);
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY)
    {

        String s = this.te.getInventoryName();
        this.fontRendererObj.drawString(s, 88 - this.fontRendererObj.getStringWidth(s) / 2, 6, 4210752);            //#404040
        this.fontRendererObj.drawString(this.playerInv.getInventoryName(), 8, 72, 4210752);      //#404040
    }
}
