package skywolf46.extrautility.util

import org.bukkit.Material
import org.bukkit.inventory.Inventory

import org.bukkit.event.inventory.InventoryType
import org.bukkit.inventory.ItemStack


class ItemStackUtil {
    fun getItemName(item: ItemStack): String? {
        return if (item.hasItemMeta() && item.itemMeta.hasDisplayName()) {
            item.itemMeta.displayName
        } else item.type.name
    }

    fun getLeftAmount(inv: Inventory, item: ItemStack?): Int {
        var slot = 0
        for (i in 0 until inv.size) {
            val it: ItemStack? = inv.getItem(i)
            if (it == null || it.type === Material.AIR) continue
            if (it.isSimilar(item)) {
                slot += it.amount
            }
        }
        return slot
    }

    fun getLeftSlot(inv: Inventory, item: ItemStack): Int {
        var slot = 0
        for (i in 0 until inv.size - if (inv.type == InventoryType.PLAYER) 5 else 0) {
            val it: ItemStack? = inv.getItem(i)
            if (it == null || it.type === Material.AIR) slot += item.type
                .maxStackSize else if (it.isSimilar(item)) {
                slot += Math.max(0, it.type.maxStackSize - it.amount)
            }
        }
        return slot
    }

    fun takeItem(inv: Inventory, item: ItemStack?, amount: Int) {
        var amount = amount
        for (i in 0 until inv.size) {
            if (amount == 0) return
            val it: ItemStack? = inv.getItem(i)
            if (it == null || it.type === Material.AIR) continue
            if (it.isSimilar(item)) {
                if (it.amount > amount) {
                    it.amount = it.amount - amount
                    return
                }
                if (it.amount == amount) {
                    inv.setItem(i, ItemStack(Material.AIR))
                    return
                }
                amount -= it.amount
                inv.setItem(i, ItemStack(Material.AIR))
            }
        }
    }

    fun addItemNaturally(inv: Inventory, item: ItemStack, amount: Int) {
        var amount = amount
        while (amount > 0) {
            val target: ItemStack = item.clone()
            if (amount >= item.type.maxStackSize) {
                target.amount = item.type.maxStackSize
                inv.addItem(target)
                amount -= item.type.maxStackSize
            } else {
                target.amount = amount
                inv.addItem(target)
                amount = 0
            }
        }
    }
}