package com.ultraflash.equi3ae.client.gui.Inventory;
import com.ultraflash.equi3ae.inventory.ContainerEmcFilter;
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
import net.minecraft.util.StatCollector;
import org.lwjgl.opengl.GL11;

public class GuiEmcFilter extends GuiContainer
{

        public GuiEmcFilter(IInventory playerInv, TileEntityEmcFilter te) {
            super(new ContainerEmcFilter(playerInv, te));

            this.xSize = 176;
            this.ySize = 166;
        }

        @Override
        protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
        }
}
