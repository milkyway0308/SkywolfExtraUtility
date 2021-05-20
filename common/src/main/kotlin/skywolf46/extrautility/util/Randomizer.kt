package skywolf46.extrautility.util

import java.util.*

class Randomizer<V : Any> {
    private val rand: Random = Random()
    private val map: TreeMap<Double, V?> = TreeMap()
    private var maxCount = .0

    fun addItem(amount: Double, item: V?) {
        map[maxCount] = item
        maxCount += amount
    }

    fun maxWeight() = maxCount
    fun size() = map.size

    fun list() = ArrayList(map.values)

    fun toPercentage(what: V): Double {
        map.entries.forEach {
            if(it.value == what)
                return it.key / maxCount * 100.0
        }
        return .0
    }

    fun random(): V? {
        return if (map.size <= 0) null else map.floorEntry(rand.nextDouble() * maxCount).value
    }
}