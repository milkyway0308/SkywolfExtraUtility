package skywolf46.extrautility.util

import skywolf46.extrautility.abstraction.IThreadSubmitter
import java.util.concurrent.Executors

object ThreadingUtil {
    var MAIN_THREAD = newOrderedThread()
        set(value) {
            field.stop()
            field = value
        }
    var ASYNC_ORDERED_THREAD = newOrderedThread()
        set(value) {
            field.stop()
            field = value
        }

    var ASYNC_THREAD = newAsyncThread()
        set(value) {
            field.stop()
            field = value
        }

    fun newOrderedThread(): IThreadSubmitter {
        return object : IThreadSubmitter {
            private val pool = Executors.newSingleThreadExecutor()
            override fun submit(unit: () -> Unit) {
                pool.submit(unit)
            }

            override fun stop() {
                pool.shutdown()
            }
        }
    }

    fun newAsyncThread(): IThreadSubmitter {
        return object : IThreadSubmitter {
            private val pool = Executors.newCachedThreadPool()
            override fun submit(unit: () -> Unit) {
                pool.submit(unit)
            }

            override fun stop() {
                pool.shutdown()
            }
        }
    }
}