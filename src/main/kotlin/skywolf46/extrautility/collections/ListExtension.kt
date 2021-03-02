package skywolf46.extrautility.collections

import java.util.stream.Collectors

fun <T> MutableList<T>.append(x1: T) = this.add(x1)
fun <T> List<T>.streamSort(x1: Comparator<T>) = this.stream().sorted(x1).collect(Collectors.toList())
