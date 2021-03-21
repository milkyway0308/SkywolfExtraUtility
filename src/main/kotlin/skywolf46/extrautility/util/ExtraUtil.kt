package skywolf46.extrautility.util

inline fun Boolean.ifTrue(block: () -> Unit): Boolean {
    if (this) {
        block()
    }
    return this
}

inline fun Boolean.ifFalse(block: () -> Unit): Boolean {
    if (!this) {
        block()
    }
    return this
}


inline fun Boolean.ifTrue(block: () -> Unit, any: Any): Any {
    if (this) {
        block()
    }
    return any
}

inline fun Boolean.ifFalse(block: () -> Unit, any: Any): Any {
    if (!this) {
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

fun <T : Any> runNonNull(any: T?, funct: T.() -> Unit): Boolean {
    if (any == null)
        return false
    funct(any)
    return true
}

fun allNull(vararg any: Any?): Boolean = allNull(any) { }

fun allNull(vararg any: Any?, funct: () -> Unit): Boolean {
    for (x in any)
        if (x != null)
            return false
    funct()
    return true
}


fun anyNull(vararg any: Any?): Boolean = anyNull(any) { }

fun anyNull(vararg any: Any?, funct: () -> Unit): Boolean {
    var passed: Boolean = false
    for (x in any)
        if (x == null)
            passed = true
    if (passed)
        funct()
    return passed
}

fun <T : AutoCloseable> T.close(block: T.() -> Unit) {
    block()
    close()
}

operator fun Boolean.invoke(block: () -> Unit): Boolean {
    if (this)
        block()
    return this
}