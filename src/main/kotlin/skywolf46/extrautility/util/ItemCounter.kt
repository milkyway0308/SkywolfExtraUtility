package skywolf46.extrautility.util

import lombok.AllArgsConstructor
import org.bukkit.inventory.ItemStack


@AllArgsConstructor
class ItemCounter {
    var item: ItemStack? = null
        private set
    var amount: Int
        get() = item!!.amount
        set(am) {
            if (am < 0) item = null else item!!.amount = am
        }
}