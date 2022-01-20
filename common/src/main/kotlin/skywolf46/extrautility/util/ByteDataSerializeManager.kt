package skywolf46.extrautility.util

import skywolf46.extrautility.util.ClassUtil.iterateParentClassesUntil

object ByteDataSerializeManager {
    private val serializers = mutableMapOf<Class<*>, Any?.(ByteDataOutput) -> Unit>()
    private val deserializers = mutableMapOf<Class<*>, (ByteDataInput) -> Any?>()

    val EMPTY_SERIALIZER: Any?.(ByteDataOutput) -> Unit = { }
    val EMPTY_DESERIALIZER: (ByteDataInput) -> Any? = { null }


    fun <T : Any> Class<T>.serializer(unit: T.(ByteDataOutput) -> Unit) {
        serializers[this] = {
            unit(this!! as T, it)
        }
    }

    fun <T : Any> Class<T>.deserializer(unit: (ByteDataInput) -> T) {
        deserializers[this] = unit
    }

    fun Any.serialize(output: ByteDataOutput) {
        findParentSerializer(this.javaClass)?.invoke(this, output)
            ?: throw IllegalStateException("No default serializer found for class ${this.javaClass.name}")
    }

    fun <T : Any> Class<T>.deserialize(input: ByteDataInput): T =
        findParentDeserializer(this.javaClass)?.invoke(input) as T?
            ?: throw IllegalStateException("No default deserializer found for class ${this.javaClass.name}")


    fun findParentSerializer(cls: Class<*>?): (Any?.(ByteDataOutput) -> Unit)? {
        if (cls == null)
            return EMPTY_SERIALIZER
        return cls.iterateParentClassesUntil {
            serializers[this]
        } as (Any?.(ByteDataOutput) -> Unit)?
    }

    fun findParentSerializerOrThrow(cls: Class<*>?): (Any?.(ByteDataOutput) -> Unit) {
        return findParentSerializer(cls)
            ?: throw IllegalStateException("No default deserializer found for class ${this.javaClass.name}")
    }


    fun findParentDeserializer(cls: Class<*>?): ((ByteDataInput) -> Any?)? {
        if (cls == null)
            return EMPTY_DESERIALIZER
        return cls.iterateParentClassesUntil {
            deserializers[this]
        } as ((ByteDataInput) -> Any)?
    }


    fun findParentDeserializerOrThrow(cls: Class<*>?): (ByteDataInput) -> Any? {
        return findParentDeserializer(cls)
            ?: throw IllegalStateException("No default deserializer found for class ${this.javaClass.name}")
    }

}