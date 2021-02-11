package skywolf46.extrautility.util;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.bukkit.Material;

public class MaterialUtil {


    public static ItemTypeData parseStringName(String type) {
        short durability = 0;
        if (type.contains(":")) {
            String[] split = type.split(":");
            type = split[0];
            durability = Short.parseShort(split[1]);
        }
        Material data = Material.matchMaterial(type);
        if (data == null)
            return null;
        return new ItemTypeData(data, durability);
    }

    @AllArgsConstructor
    public static final class ItemTypeData {
        @Getter
        private Material type;
        @Getter
        private short durability = 0;

    }
}
