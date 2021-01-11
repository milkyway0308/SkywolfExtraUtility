package skywolf46.extrautility.util;

import org.bukkit.Material;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class ItemStackUtil {

    public static String getItemName(ItemStack item) {
        if (item.hasItemMeta() && item.getItemMeta().hasDisplayName()) {
            return item.getItemMeta().getDisplayName();
        }
        return item.getType().name();
    }

    public static int getLeftAmount(Inventory inv, ItemStack item) {
        int slot = 0;
        for (int i = 0; i < inv.getSize(); i++) {
            ItemStack it = inv.getItem(i);
            if (it == null || it.getType() == Material.AIR)
                continue;
            if (it.isSimilar(item)) {
                slot += it.getAmount();
            }
        }
        return slot;
    }

    public static int getLeftSlot(Inventory inv, ItemStack item) {
        int slot = 0;
        for (int i = 0; i < inv.getSize() - (inv.getType() == InventoryType.PLAYER ? 5 : 0); i++) {
            ItemStack it = inv.getItem(i);
            if (it == null || it.getType() == Material.AIR)
                slot += item.getType().getMaxStackSize();
            else if (it.isSimilar(item)) {
                slot += Math.max(0, it.getType().getMaxStackSize() - it.getAmount());
            }
        }
        return slot;
    }

    public static void takeItem(Inventory inv, ItemStack item, int amount) {
        for (int i = 0; i < inv.getSize(); i++) {
            if (amount == 0)
                return;
            ItemStack it = inv.getItem(i);
            if (it == null || it.getType() == Material.AIR)
                continue;
            if (it.isSimilar(item)) {
                if (it.getAmount() > amount) {
                    it.setAmount(it.getAmount() - amount);
                    return;
                }
                if (it.getAmount() == amount) {
                    inv.setItem(i, new ItemStack(Material.AIR));
                    return;
                }
                amount -= it.getAmount();
                inv.setItem(i, new ItemStack(Material.AIR));
            }
        }
    }

    public static void addItemNaturally(Inventory inv, ItemStack item, int amount) {
        while (amount > 0) {
            ItemStack target = item.clone();
            if (amount >= item.getType().getMaxStackSize()) {
                target.setAmount(item.getType().getMaxStackSize());
                inv.addItem(target);
                amount -= item.getType().getMaxStackSize();
            } else {
                target.setAmount(amount);
                inv.addItem(target);
                amount = 0;
            }
        }
    }
}
