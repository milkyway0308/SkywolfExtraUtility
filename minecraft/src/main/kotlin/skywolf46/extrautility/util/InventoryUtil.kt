package skywolf46.extrautility.util

import org.bukkit.inventory.Inventory
import org.bukkit.inventory.ItemStack

fun Inventory.get(data: Int): ItemStack = getItem(data)

fun Inventory.set(data: Int, item: ItemStack) = setItem(data, item)

operator fun <T : Inventory> T.plus(item: ItemStack): T {
    addItem(item)
    return this
}


operator fun <T : Inventory> T.plusAssign(item: ItemStack): Unit {
    addItem(item)
}

fun Inventory.addItemNaturally(item: ItemStack, amount: Int) {
    var leftAmount = amount
    while (leftAmount > 0) {
        val target: ItemStack = item.clone()
        if (leftAmount >= item.type.maxStackSize) {
            target.amount = item.type.maxStackSize
            addItem(target)
            leftAmount -= item.type.maxStackSize
        } else {
            target.amount = leftAmount
            addItem(target)
            leftAmount = 0
        }
    }
}