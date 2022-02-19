package skywolf46.extrautility.util

fun <T: Any> T.elseDefault(def: T, condition: T.() -> Boolean) : T{
    if(!condition(this))
        return def
    return this
}