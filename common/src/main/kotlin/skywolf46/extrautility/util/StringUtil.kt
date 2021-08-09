package skywolf46.extrautility.util

fun String.splitUntilImpossible(vararg splitter: Int): Array<String> {
    val list = mutableListOf<String>()
    splitter.sorted().apply {
        for (x in indices) {
            if (this[x] <= length) {
                val starter = if (x == 0) 0 else this[x - 1]
                list += substring(starter, this[x])
            }
        }
    }
    return list.toTypedArray()
}