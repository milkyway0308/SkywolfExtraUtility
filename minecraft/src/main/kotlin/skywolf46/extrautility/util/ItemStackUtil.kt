package skywolf46.extrautility.util

import org.bukkit.Material
import org.bukkit.enchantments.Enchantment
import org.bukkit.event.inventory.InventoryType
import org.bukkit.inventory.Inventory
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.meta.ItemMeta
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
    var leftAmount = amount
    for (i in 0 until size) {
        if (leftAmount == 0) break
        val it: ItemStack? = getItem(i)
        if (it == null || it.type === Material.AIR) continue
        if (it.isSimilar(item)) {
            if (it.amount > leftAmount) {
                it.amount = it.amount - leftAmount
                break
            }
            if (it.amount == leftAmount) {
                setItem(i, ItemStack(Material.AIR))
                break
            }
            leftAmount -= it.amount
            setItem(i, ItemStack(Material.AIR))
        }
    }
}

fun ItemStack.itemMeta(block: ItemMeta.() -> Unit): ItemStack {
    val meta = itemMeta
    block(meta)
    itemMeta = meta
    return this
}

fun <T : ItemMeta> ItemStack.castMeta(block: T.() -> Unit): ItemStack {
    val meta = itemMeta as T
    block(meta)
    itemMeta = meta
    return this
}

fun ItemStack.name(str: String): ItemStack {
    itemMeta {
        displayName = str
    }
    return this
}


fun ItemStack.lore(str: List<String>): ItemStack {
    itemMeta {
        lore = str
    }
    return this
}

fun ItemStack.amount(amount: Int): ItemStack {
    setAmount(amount)
    return this
}


fun ItemStack.lore(vararg lore: String): ItemStack = lore(listOf(*lore))

fun ItemStack.enchant(map: Map<Enchantment, Int>): ItemStack {
    itemMeta {
        map.forEach { (ench, lv) ->
            enchant(ench, lv)
        }
    }
    return this
}

fun ItemMeta.enchant(ench: Enchantment, lv: Int): ItemMeta {
    addEnchant(ench, lv, true)
    return this
}