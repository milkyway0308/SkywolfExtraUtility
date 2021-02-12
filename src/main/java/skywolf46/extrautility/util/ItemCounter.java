package skywolf46.extrautility.util;

import org.bukkit.inventory.ItemStack;

public class ItemCounter {
    private ItemStack item;

    public ItemStack getItem() {
        return item;
    }

    public void setAmount(int am) {
        if (am < 0)
            item = null;
        else
            item.setAmount(am);
    }
}
