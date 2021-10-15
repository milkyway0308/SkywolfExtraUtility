package skywolf46.extrautility.impl

import skywolf46.extrautility.abstraction.IPromise
import java.util.concurrent.locks.ReentrantLock
import kotlin.concurrent.withLock

class PromiseImpl<SUCCESS : Any, FAILED : Any> : IPromise<SUCCESS, FAILED> {
    private val lock = ReentrantLock()
    private var isCompleted = false
    private var data: SUCCESS? = null
    private var error: FAILED? = null

    // 0 = Any
    // 1 = Success
    // 2 = Failed
    private val list = mutableListOf<Pair<Int, () -> Unit>>()


    override fun success(data: (SUCCESS) -> Unit) {
        var execute = false
        lock.withLock {
            if (isCompleted && this.data != null)
                execute = true
            else
                list += 1 to {
                    data(this.data!!)
                }
        }
        if (execute)
            data(this.data!!)
    }

    override fun failed(data: (FAILED) -> Unit) {
        var execute = false
        lock.withLock {
            if (isCompleted && this.error != null)
                execute = true
            else
                list += 2 to {
                    data(error!!)
                }
        }
        if (execute)
            data(error!!)
    }

    override fun after(data: () -> Unit) {
        var execute = false
        lock.withLock {
            if (isCompleted)
                execute = true
            else
                list += 0 to {
                    data()
                }
        }
        if (execute)
            data()
    }

    override fun trigger(data: SUCCESS) {
        val next = mutableListOf<() -> Unit>()
        lock.withLock {
            isCompleted = true
            this.data = data
            next.addAll(list.filter { (x, _) -> x != 2 }.map { x -> x.second })
        }
        next.forEach { it.invoke() }
    }

    override fun triggerFailed(throwable: FAILED) {
        val next = mutableListOf<() -> Unit>()
        lock.withLock {
            isCompleted = true
            this.data = null
            this.error = throwable
            next.addAll(list.filter { (x, _) -> x != 1 }.map { x -> x.second })
        }
        next.forEach { it.invoke() }
    }
}