package skywolf46.extrautility.structural;

import manifold.ext.rt.api.Structural;
import org.bukkit.Location;
import org.bukkit.util.Vector;

@Structural
public interface Locate {
    double getX();

    double getY();

    double getZ();

    public static Locate of(Location l) {
        return (Locate) l;
    }


    public static Locate of(Vector l) {
        return (Locate) l;
    }
}
