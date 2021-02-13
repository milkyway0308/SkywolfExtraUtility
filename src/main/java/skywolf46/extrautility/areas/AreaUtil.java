package skywolf46.extrautility.areas;

import org.bukkit.Location;

public class AreaUtil {

    public static RectangleArea rectagle(Location x1, Location x2) {
        return (RectangleArea) RectangleArea.INSTANCE.create(new Location[]{x1, x2});
    }
}
