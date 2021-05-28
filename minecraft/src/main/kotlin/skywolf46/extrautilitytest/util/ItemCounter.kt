package skywolf46.extrautilitytest.util


import org.bukkit.inventory.ItemStack



class ItemCounter(item: ItemStack) {
    var item: ItemStack? = item
        private set
    var amount: Int
        get() = item!!.amount
        set(am) {
            if (am < 0) item = null else item!!.amount = am
        }
}