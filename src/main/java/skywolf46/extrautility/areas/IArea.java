package skywolf46.extrautility.areas;

import org.bukkit.Location;
import org.bukkit.World;

public interface IArea {
    int getPoint();

    boolean isInArea(Location loc);

    IArea create(Location[] locs);

    World getWorld();

    Location[] getPoints();
}
