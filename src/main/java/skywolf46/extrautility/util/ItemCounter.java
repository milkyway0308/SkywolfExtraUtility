package skywolf46.extrautility.util;

import lombok.AllArgsConstructor;
import org.bukkit.inventory.ItemStack;

@AllArgsConstructor
public class ItemCounter {
    private ItemStack item;

    public ItemStack getItem() {
        return item;
    }

    public int getAmount(){
        return item.getAmount();
    }

    public void setAmount(int am) {
        if (am < 0)
            item = null;
        else
            item.setAmount(am);
    }
}
