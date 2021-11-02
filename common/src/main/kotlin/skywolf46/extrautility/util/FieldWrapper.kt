package skywolf46.extrautility.util

import java.lang.reflect.Field

class FieldWrapper(val field: Field, val instance: Any?) {
    fun get(): Any? {
        return field.get(instance)
    }

    fun set(data: Any) {
        field.set(instance, data)
    }
}