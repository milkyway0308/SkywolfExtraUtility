package skywolf46.extrautility.areas;

import org.bukkit.Location;
import org.bukkit.World;

public class RectangleArea implements IArea {
    public static final RectangleArea INSTANCE = new RectangleArea();
    private ArrayLocation[] locs = new ArrayLocation[2];
    private World baseWorld;

    public RectangleArea(World base, ArrayLocation ar1, ArrayLocation ar2) {
        this.baseWorld = base;
        this.locs[0] = ar1;
        this.locs[1] = ar2;
        if (locs[0].x() > locs[1].x())
            locs[0].x(locs[1]);
        if (locs[0].y() > locs[1].y())
            locs[0].y(locs[1]);
        if (locs[0].z() > locs[1].z())
            locs[0].z(locs[1]);
    }

    public RectangleArea() {

    }

    public ArrayLocation getMin() {
        return locs[0];
    }

    public ArrayLocation getMax() {
        return locs[1];
    }

    @Override
    public int getPoint() {
        return 2;
    }

    @Override
    public boolean isInArea(Location loc) {
        if (!loc.getWorld().equals(baseWorld))
            return false;
        return
                locs[0].x() <= loc.getX() && locs[1].x() >= loc.getX() &&
                        locs[0].y() <= loc.getY() && locs[1].y() >= loc.getY() &&
                        locs[0].z() <= loc.getZ() && locs[1].z() >= loc.getZ()
                ;
    }

    @Override
    public IArea create(Location[] locs) {
        return new RectangleArea(locs[0].getWorld(), new ArrayLocation(locs[0]), new ArrayLocation(locs[1]));
    }

    @Override
    public World getWorld() {
        return baseWorld;
    }

    @Override
    public Location[] getPoints() {
        return new Location[]{
                getMin().toLocation(getWorld()),
                getMax().toLocation(getWorld())
        };
    }
}
