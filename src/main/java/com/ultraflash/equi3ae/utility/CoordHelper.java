package com.ultraflash.equi3ae.utility;

import net.minecraft.entity.Entity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.MathHelper;
import net.minecraftforge.common.util.ForgeDirection;

/**
 * Created by Benni on 24.06.2016.
 */
public class CoordHelper
{
    public final int x, y, z;

    public CoordHelper() {
        this(0, 0, 0);
    }

    public CoordHelper(int x, int y, int z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }


    public  CoordHelper getLocation(ForgeDirection dir) {
        return new  CoordHelper(x + dir.offsetX, y + dir.offsetY, z + dir.offsetZ);
    }


    public CoordHelper(double x, double y, double z) {
        this(MathHelper.floor_double(x), MathHelper.floor_double(y), MathHelper.floor_double(z));
    }

    public CoordHelper(TileEntity tile) {
        this(tile.xCoord, tile.yCoord, tile.zCoord);
    }

    public CoordHelper(Entity e) {
        this(e.posX, e.posY, e.posZ);
    }

    public CoordHelper(CoordHelper bc) {
        this(bc.x, bc.y, bc.z);
    }

    public boolean equals(int x, int y, int z) {
        return equals(new CoordHelper(x, y, z));
    }

    @Override
    public boolean equals(final Object o) {
        if (o == this) return true;
        if (!(o instanceof CoordHelper)) return false;
        final CoordHelper other = (CoordHelper)o;
        if (!other.canEqual((Object)this)) return false;
        if (this.x != other.x) return false;
        if (this.y != other.y) return false;
        if (this.z != other.z) return false;
        return true;
    }

    protected boolean canEqual(final Object other) {
        return other instanceof CoordHelper;
    }
}
