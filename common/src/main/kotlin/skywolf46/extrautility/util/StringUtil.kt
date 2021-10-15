package skywolf46.extrautility.util

fun String.splitUntilImpossible(vararg splitter: Int): Array<String> {
    val list = mutableListOf<String>()
    splitter.sorted().apply {
        for (x in indices) {
            val starter = if (x == 0) 0 else this[x - 1]
            if (this[x] < length) {
                list += substring(starter, this[x])
            } else {
                list += substring(starter, this[x].coerceAtMost(length))
                break
            }
        }
    }

    if (list.isEmpty())
        list.add("")
    return list.toTypedArray()
}
