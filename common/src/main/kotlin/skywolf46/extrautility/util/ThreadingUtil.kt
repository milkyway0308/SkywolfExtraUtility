package skywolf46.extrautility.util

import skywolf46.extrautility.abstraction.IThreadSubmitter
import java.util.concurrent.Executors

object ThreadingUtil {
    var MAIN_THREAD = object : IThreadSubmitter {
        private val pool = Executors.newSingleThreadExecutor()
        override fun submit(unit: () -> Unit) {
            pool.submit(unit)
        }

        override fun stop() {
            pool.shutdown()
        }
    }
        set(value) {
            field.stop()
            field = value
        }
    var ASYNC_ORDERED_THREAD = object : IThreadSubmitter {
        private val pool = Executors.newSingleThreadExecutor()
        override fun submit(unit: () -> Unit) {
            pool.submit(unit)
        }

        override fun stop() {
            pool.shutdown()
        }
    }
        set(value) {
            field.stop()
            field = value
        }

    var ASYNC_THREAD = object : IThreadSubmitter {
        private val pool = Executors.newCachedThreadPool()
        override fun submit(unit: () -> Unit) {
            pool.submit(unit)
        }

        override fun stop() {
            pool.shutdown()
        }
    }
        set(value) {
            field.stop()
            field = value
        }
}