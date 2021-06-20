package skywolf46.extrautility.util

open class PriorityReference<T : Any>(val data: T, val priority: Int = 0) : Comparable<PriorityReference<T>> {
    override fun compareTo(other: PriorityReference<T>): Int {
        return priority.compareTo(other.priority)
    }

}