package skywolf46.extrautility.util

import kotlin.random.Random

fun <T : Any> List<T>.sample(amount: Int): List<T> {
    return toMutableList().run {
        (0 until amount.coerceAtMost(size)).map { _ -> removeAt(Random.nextInt(size)) }
    }
}

fun <T : Any> List<T>.samplePercentage(percentage: Double): List<T> {
    return sample((size * (percentage.coerceAtLeast(0.0).coerceAtMost(100.0) * 0.01)).toInt())
}