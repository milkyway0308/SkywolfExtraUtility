package skywolf46.extrautility.areas;

import org.bukkit.Location;

public interface IArea {
    int getPoint();

    boolean isInArea(Location loc);

    IArea create(Location[] locs);
}
