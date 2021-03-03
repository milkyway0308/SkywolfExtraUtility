package skywolf46.extrautility.util

import org.bukkit.Material
import org.bukkit.event.inventory.InventoryType
import org.bukkit.inventory.Inventory
import org.bukkit.inventory.ItemStack
import kotlin.math.max


fun ItemStack.getItemName(): String {
    return (itemMeta?.displayName ?: type.name)
}

fun Inventory.getLeftAmount(item: ItemStack?): Int {
    var slot = 0
    for (i in 0 until size) {
        val it: ItemStack? = getItem(i)
        if (it == null || it.type === Material.AIR) continue
        if (it.isSimilar(item)) {
            slot += it.amount
        }
    }
    return slot
}

fun Inventory.getLeftSlot(item: ItemStack): Int {
    var slot = 0
    for (i in 0 until size - if (type == InventoryType.PLAYER) 5 else 0) {
        val it: ItemStack? = getItem(i)
        if (it == null || it.type === Material.AIR) slot += item.type
            .maxStackSize else if (it.isSimilar(item)) {
            slot += max(0, it.type.maxStackSize - it.amount)
        }
    }
    return slot
}

fun Inventory.takeItem(item: ItemStack?, amount: Int) {
    var amount = amount
    for (i in 0 until size) {
        if (amount == 0) return
        val it: ItemStack? = getItem(i)
        if (it == null || it.type === Material.AIR) continue
        if (it.isSimilar(item)) {
            if (it.amount > amount) {
                it.amount = it.amount - amount
                return
            }
            if (it.amount == amount) {
                setItem(i, ItemStack(Material.AIR))
                return
            }
            amount -= it.amount
            setItem(i, ItemStack(Material.AIR))
        }
    }
}

fun Inventory.addItemNaturally(item: ItemStack, amount: Int) {
    var amount = amount
    while (amount > 0) {
        val target: ItemStack = item.clone()
        if (amount >= item.type.maxStackSize) {
            target.amount = item.type.maxStackSize
            addItem(target)
            amount -= item.type.maxStackSize
        } else {
            target.amount = amount
            addItem(target)
            amount = 0
        }
    }
}