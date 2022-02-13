package skywolf46.extrautility.collections

import java.util.function.Function

class MultiKeyMap<K : Any, V : Any> : HashMap<K, V>() {
    private val keyMap = mutableMapOf<Any, KeyRelation>()

    override fun put(key: K, value: V): V? {
        return super.put(key, value).apply {
            checkKey(key)
        }
    }

    fun getValue(multiKey: Any): V? {
        return keyMap[multiKey]?.run {
            get(mainKey)
        }
    }

    override fun computeIfAbsent(key: K, mappingFunction: Function<in K, out V>): V {
        return super.computeIfAbsent(key, {
            mappingFunction.apply(it)
        })
    }

    fun <KEY : Any> filterEntries(cls: Class<KEY>): List<Map.Entry<KEY, V>> {
        return (keyMap.entries.filter { x -> cls.isAssignableFrom(x.key.javaClass) } as List<Map.Entry<KEY, V>>)
    }

    private fun checkKey(key: K, vararg multiKey: Any) {
        keyMap[key] ?: kotlin.run {
            // If no relation, create single key relation
            if (multiKey.isEmpty())
                keyMap[key] = KeyRelation(key)
            else {
                val newArr = Array<Any?>(multiKey.size + 1) { null }
                newArr[0] = key
                System.arraycopy(multiKey, 0, newArr, 1, multiKey.size)
                keyMap[key] = KeyRelation(newArr)
            }
        }
    }


    private class KeyRelation(vararg val relatedKeys: Any) {
        val mainKey = relatedKeys[0]
    }
}