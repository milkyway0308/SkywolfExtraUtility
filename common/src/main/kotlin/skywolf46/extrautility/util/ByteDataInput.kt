package skywolf46.extrautility.util


import skywolf46.extrautility.util.ByteDataSerializeManager.deserialize
import java.io.ByteArrayInputStream
import java.io.DataInput
import java.io.DataInputStream

class ByteDataInput(arr: ByteArray) : DataInput, AutoCloseable {
    private val stream = ByteArrayInputStream(arr)
    private val dataStream = DataInputStream(stream)

    fun readByteArray(): ByteArray {
        val arr = ByteArray(readInt())
        readFully(arr)
        return arr
    }

    fun <T : Any> readObject(): T {
        val clsName = readUTF()
        try {
            return Class.forName(clsName).deserialize(this) as T
        } catch (e: Exception) {
            throw IllegalStateException("Cannot deserialize $clsName : Class not exists")
        }
    }


    override fun readFully(b: ByteArray) {
        return dataStream.readFully(b)
    }

    override fun readFully(b: ByteArray, off: Int, len: Int) {
        return dataStream.readFully(b, off, len)
    }

    override fun skipBytes(n: Int): Int {
        return dataStream.skipBytes(n)
    }

    override fun readBoolean(): Boolean {
        return dataStream.readBoolean()
    }

    override fun readByte(): Byte {
        return dataStream.readByte()
    }

    override fun readUnsignedByte(): Int {
        return dataStream.readUnsignedByte()
    }

    override fun readShort(): Short {
        return dataStream.readShort()
    }

    override fun readUnsignedShort(): Int {
        return dataStream.readUnsignedShort()
    }

    override fun readChar(): Char {
        return dataStream.readChar()
    }

    override fun readInt(): Int {
        return dataStream.readInt()
    }

    override fun readLong(): Long {
        return dataStream.readLong()
    }

    override fun readFloat(): Float {
        return dataStream.readFloat()
    }

    override fun readDouble(): Double {
        return dataStream.readDouble()
    }

    override fun readLine(): String {
        return dataStream.readLine()
    }

    override fun readUTF(): String {
        return dataStream.readUTF()
    }

    override fun close() {
        stream.close()
    }
}