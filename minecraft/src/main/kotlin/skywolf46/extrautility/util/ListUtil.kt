package skywolf46.extrautility.util

import kotlin.random.Random

fun <T : Any> List<T>.sample(amount: Int): List<T> {
    return toMutableList().run {
        (0 until amount.coerceAtMost(size)).map { _ -> removeAt(Random.nextInt(size)) }
    }
}