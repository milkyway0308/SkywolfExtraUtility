package skywolf46.extrautility.logger


import java.io.BufferedWriter
import java.io.File
import java.io.FileWriter
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.atomic.AtomicBoolean
import java.util.concurrent.locks.ReentrantReadWriteLock


class DateLogger @JvmOverloads constructor(target: File, format: String, writingDelay: Long = 200L) :
    Thread() {
    private val dateFormat: SimpleDateFormat = SimpleDateFormat(format)
    private var writer: BufferedWriter? = null
    private val textToWrite: MutableList<String> = ArrayList()
    private val lock = ReentrantReadWriteLock()
    private val delay: Long = writingDelay
    private val enabled = AtomicBoolean(true)
    fun stopLog() {
        enabled.set(false)
    }

    fun append(text: String) {
        val txt = dateFormat.format(Date())
        lock.writeLock().lock()
        textToWrite.add("[$txt] $text")
        lock.writeLock().unlock()
    }

    @Throws(IOException::class)
    private fun write() {
        lock.writeLock().lock()
        val appender: MutableList<String> = ArrayList(textToWrite)
        textToWrite.clear()
        lock.writeLock().unlock()
        for (x in appender) {
//            println("Appended$x")
            writer!!.append(x)
            writer!!.newLine()
        }
        writer!!.flush()
        appender.clear()
    }

    override fun run() {
        while (enabled.get()) {
            try {
                write()
                sleep(delay)
            } catch (e: InterruptedException) {
                e.printStackTrace()
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
        try {
            write()
            writer!!.flush()
            writer!!.close()
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    init {
        try {
            if (!target.exists()) {
                if (target.parentFile != null) target.parentFile.mkdirs()
                target.createNewFile()
            }
            writer = BufferedWriter(FileWriter(target, false))
        } catch (e: IOException) {
            e.printStackTrace()
        }
        isDaemon = false
        start()
    }
}