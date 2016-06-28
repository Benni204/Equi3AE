package com.ultraflash.equi3ae.tileentity;

import appeng.api.config.Actionable;
import appeng.api.config.PowerMultiplier;
import appeng.api.implementations.tiles.IMEChest;
import appeng.api.networking.security.BaseActionSource;
import appeng.api.storage.*;
import appeng.api.storage.data.IAEFluidStack;
import appeng.api.storage.data.IAEItemStack;
import appeng.api.util.IConfigManager;
import appeng.helpers.IPriorityHost;
import appeng.util.IConfigManagerHost;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTankInfo;
import net.minecraftforge.fluids.IFluidHandler;

import java.util.List;

/**
 * Created by Benni on 27.06.2016.
 */
public class TileEmcStorageBase extends TileEntityNetworkE3AE implements IMEChest, IFluidHandler, ITerminalHost, IPriorityHost, IConfigManagerHost
{


    @Override
    public int getCellCount() {
        return 0;
    }

    @Override
    public int getCellStatus(int slot) {
        return 0;
    }

    @Override
    public boolean isPowered() {
        return false;
    }

    @Override
    public boolean isCellBlinking(int slot) {
        return false;
    }

    @Override
    public IStorageMonitorable getMonitorable(ForgeDirection side, BaseActionSource src) {
        return null;
    }

    @Override
    public double extractAEPower(double amt, Actionable mode, PowerMultiplier usePowerMultiplier) {
        return 0;
    }

    @Override
    public void blinkCell(int slot) {

    }

    @Override
    public List<IMEInventoryHandler> getCellArray(StorageChannel channel) {
        return null;
    }

    @Override
    public int getPriority() {
        return 0;
    }

    @Override
    public void setPriority(int i) {

    }

    @Override
    public void saveChanges(IMEInventory cellInventory) {

    }

    @Override
    public IMEMonitor<IAEItemStack> getItemInventory() {
        return null;
    }

    @Override
    public IMEMonitor<IAEFluidStack> getFluidInventory() {
        return null;
    }

    @Override
    public IConfigManager getConfigManager() {
        return null;
    }

    @Override
    public void updateSetting(IConfigManager iConfigManager, Enum anEnum, Enum anEnum1) {

    }

    @Override
    public int fill(ForgeDirection from, FluidStack resource, boolean doFill) {
        return 0;
    }

    @Override
    public FluidStack drain(ForgeDirection from, FluidStack resource, boolean doDrain) {
        return null;
    }

    @Override
    public FluidStack drain(ForgeDirection from, int maxDrain, boolean doDrain) {
        return null;
    }

    @Override
    public boolean canFill(ForgeDirection from, Fluid fluid) {
        return false;
    }

    @Override
    public boolean canDrain(ForgeDirection from, Fluid fluid) {
        return false;
    }

    @Override
    public FluidTankInfo[] getTankInfo(ForgeDirection from) {
        return new FluidTankInfo[0];
    }
}
