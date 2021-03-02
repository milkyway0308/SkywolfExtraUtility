package skywolf46.extrautility.util

inline fun Boolean?.ifTrue(block: Boolean.() -> Unit): Boolean? {
    if (this == true) {
        block()
    }
    return this
}

inline fun Boolean?.ifFalse(block: Boolean?.() -> Unit): Boolean? {
    if (null == this || !this) {
        block()
    }
    return this
}


inline fun Boolean?.ifTrue(block: Boolean.() -> Unit, any: Any): Any {
    if (this == true) {
        block()
    }
    return any
}

inline fun Boolean?.ifFalse(block: Boolean?.() -> Unit, any: Any): Any {
    if (null == this || !this) {
        block()
    }
    return any
}

inline fun ifTrue(condition: () -> Boolean, data: () -> Any): Any? {
    if (condition()) {
        return data()
    }
    return null
}


inline fun ifFalse(condition: () -> Boolean, data: () -> Any): Any? {
    if (!condition()) {
        return data()
    }
    return null
}

fun nonNull(vararg any: Any?, funct: () -> Unit): Boolean {
    for (x in any)
        if (x == null)
            return false
    funct()
    return true
}


fun allNull(vararg any: Any?, funct: () -> Unit): Boolean {
    for (x in any)
        if (x != null)
            return false
    funct()
    return true
}

fun anyNull(vararg any: Any?, funct: () -> Unit): Boolean {
    var passed: Boolean = false
    for (x in any)
        if (x == null)
            passed = true
    if (passed)
        funct()
    return passed
}
