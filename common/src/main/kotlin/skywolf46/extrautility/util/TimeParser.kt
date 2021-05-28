package skywolf46.extrautility.util

import java.util.function.Function
import kotlin.math.max


object TimeParser {
    private var parser: ParsingType? = null

    @JvmOverloads
    fun parseToMillisecond(x: String, parser: ParsingType? = TimeParser.parser): Long {
        var total: Long = 0
        var toParse = 0
        var lastParsed = 0
        for (i in x.indices) {
            val cx = x[i]
            if (cx.isDigit()) {
                if (toParse != lastParsed) {
                    val am = parse(x, x.substring(lastParsed, toParse + 1), parser)
                    if (am != Int.MIN_VALUE.toLong()) {
                        total += am
                        toParse = i
                        lastParsed = i
                    }
                }
            } else {
                toParse = i
            }
        }
        if (toParse != lastParsed) {
            val am = parse(x, x.substring(lastParsed), parser)
            if (am != Int.MIN_VALUE.toLong()) total += am
        }
        return total
    }

    private fun parse(original: String, toParse: String, parser: ParsingType?): Long {
        var toParse = toParse
        var target: String? = null
        for (i in toParse.indices) {
            if (!toParse[i].isDigit()) {
                target = toParse.substring(0, i)
                toParse = toParse.substring(i)
                break
            }
        }
        checkNotNull(target) { "Time is not number" }
        check(parser!!.canAccept(toParse)) { "Cannot parse \"$toParse\" at \"$original\" : Not a parsing target" }
        val fi = parser.parser(toParse)
            ?: return Int.MIN_VALUE.toLong()
        return fi.apply(target).toLong()
    }

    class ParsingType {
        private val parsers = HashMap<String, Function<String, Int>>()
        private var maxLength = 0

        fun append(type: String, funct: Function<String, Int>): ParsingType {
            parsers[type] = funct
            maxLength = max(type.length, maxLength)
            return this
        }

        fun parser(x: String): Function<String, Int>? {
            return parsers[x]
        }

        fun canAccept(toParse: String): Boolean {
            return toParse.length <= maxLength
        }
    }

    init {
        parser = ParsingType()
        parser!!.append("ms") { s: String -> s.toInt() }
        parser!!.append("s") { x: String -> x.toInt() * 1000 }
        parser!!.append("m") { x: String -> x.toInt() * 60 * 1000 }
        parser!!.append("h") { x: String -> x.toInt() * 60 * 60 * 1000 }
        parser!!.append("d") { x: String -> x.toInt() * 24 * 60 * 60 * 1000 }
    }
}