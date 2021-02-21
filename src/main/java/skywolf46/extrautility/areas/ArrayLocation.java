package skywolf46.extrautility.areas;

import org.bukkit.Location;
import org.bukkit.World;

public class ArrayLocation {
    private double[] xyz = new double[3];

    public ArrayLocation(Location x) {
        xyz = new double[]{
                x.getX(),
                x.getY(),
                x.getZ(),
        };
    }

    public Location toLocation(World wx) {
        return new Location(wx, x(), y(), z());
    }

    public double x() {
        return xyz[0];
    }


    public double y() {
        return xyz[1];
    }

    public double z() {
        return xyz[2];
    }

    public void x(ArrayLocation arx) {
        double cache = x();
        xyz[0] = arx.x();
        arx.xyz[0] = cache;
    }


    public void y(ArrayLocation arx) {
        double cache = y();
        xyz[1] = arx.y();
        arx.xyz[1] = cache;
    }


    public void z(ArrayLocation arx) {
        double cache = z();
        xyz[2] = arx.z();
        arx.xyz[2] = cache;
    }
}
