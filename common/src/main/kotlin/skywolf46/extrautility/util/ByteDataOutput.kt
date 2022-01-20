package skywolf46.extrautility.util

import skywolf46.extrautility.util.ByteDataSerializeManager.findParentSerializerOrThrow
import skywolf46.extrautility.util.ByteDataSerializeManager.serialize
import java.io.ByteArrayOutputStream
import java.io.DataOutput
import java.io.DataOutputStream

class ByteDataOutput : DataOutput, AutoCloseable {
    private val output = ByteArrayOutputStream()
    private val dataOutput = DataOutputStream(output)

    fun toDataInput(): ByteDataInput {
        return ByteDataInput(toByteArray())
    }

    fun toByteArray(): ByteArray {
        return output.toByteArray()
    }

    @JvmOverloads
    fun writeObject(any: Any?, writeHeader: Boolean = true) {
        if (any == null) {
            writeBoolean(false)
        } else {
            writeBoolean(true)
            if (writeHeader)
                writeUTF(any.javaClass.name)
        }
        any?.serialize(this)
    }


    /**
     * Write guaranteed list with minimized bytes.
     * Cause [writeGuaranteedList] only write the header of the first object, it must be guaranteed that all objects are of the same class.
     *
     * WARNING : [writeGuaranteedList] don't check class matching. It can cause error if it used incorrectly.
     */
    fun writeGuaranteedList(lst: List<Any?>) {
        if (lst.isEmpty()) {
            writeInt(0)
            return
        }
        writeInt(lst.size)
        val type = lst.find { it != null }?.javaClass
        writeUTF(getType(type))
        val serializer = findParentSerializerOrThrow(type)
        for (x in lst) {
            serializer.invoke(x, this)
        }
    }

    fun writeGuaranteedMap(map: Map<Any?, Any?>) {
        if (map.isEmpty()) {
            writeInt(0)
            return
        }
        writeInt(map.size)
        val key = map.keys.find { it != null }?.javaClass
        val value = map.values.find { it != null }?.javaClass
        writeUTF(getType(key))
        writeUTF(getType(value))
        val keySerializer = findParentSerializerOrThrow(key)
        val valueSerializer = findParentSerializerOrThrow(value)
        for (x in map) {
            keySerializer.invoke(x, this)
            valueSerializer.invoke(x, this)
        }
    }

    private fun getType(any: Class<Any>?) = if (any == null) "null" else any.name

    override fun write(b: Int) {
        dataOutput.write(b)
    }

    override fun write(b: ByteArray) {
        dataOutput.write(b)
    }

    override fun write(b: ByteArray, off: Int, len: Int) {
        dataOutput.write(b, off, len)
    }

    override fun writeBoolean(v: Boolean) {
        dataOutput.writeBoolean(v)
    }

    override fun writeByte(v: Int) {
        dataOutput.writeByte(v)
    }

    override fun writeShort(v: Int) {
        dataOutput.writeShort(v)
    }

    override fun writeChar(v: Int) {
        dataOutput.writeChar(v)
    }

    override fun writeInt(v: Int) {
        dataOutput.writeInt(v)
    }

    override fun writeLong(v: Long) {
        dataOutput.writeLong(v)
    }

    override fun writeFloat(v: Float) {
        dataOutput.writeFloat(v)
    }

    override fun writeDouble(v: Double) {
        dataOutput.writeDouble(v)
    }

    override fun writeBytes(s: String) {
        dataOutput.writeBytes(s)
    }

    override fun writeChars(s: String) {
        dataOutput.writeChars(s)
    }

    override fun writeUTF(s: String) {
        dataOutput.writeUTF(s)
    }

    override fun close() {
        output.close()
    }

}