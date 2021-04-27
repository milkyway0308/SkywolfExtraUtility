package skywolf46.extrautility.collections

import java.util.stream.Collectors

fun <X: MutableList<T>, T> X.append(x1: T) : X{
    this.add(x1)
    return this
}


fun <X: MutableList<T>, T> X.append(vararg x1: T) : X{
    x1.forEach {
        this.add(it)
    }
    return this
}
fun <T> List<T>.streamSort(x1: Comparator<T>) = this.stream().sorted(x1).collect(Collectors.toList())
