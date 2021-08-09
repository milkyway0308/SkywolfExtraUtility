package skywolf46.extrautility.util

import skywolf46.extrautility.abstraction.IPromise
import skywolf46.extrautility.impl.PromiseImpl

fun <T : Any, X : Any> promise(): IPromise<T, X> {
    return PromiseImpl()
}


fun emptyPromise(): IPromise<Unit, Throwable> {
    return PromiseImpl()
}

fun <T : Any> promise(executor: () -> T): IPromise<T, Throwable> {
    val promise = PromiseImpl<T, Throwable>()
    try {
        promise.trigger(executor())
    } catch (e: Throwable) {
        promise.triggerFailed(e)
    }
    return promise
}

fun emptyPromise(executor: () -> Unit): IPromise<Unit, Throwable> {
    val promise = PromiseImpl<Unit, Throwable>()
    try {
        executor()
        promise.trigger(Unit)
    } catch (e: Throwable) {
        promise.triggerFailed(e)
    }
    return promise
}


fun <T : Any> promiseSync(executor: () -> T): IPromise<T, Throwable> {
    val promise = PromiseImpl<T, Throwable>()
    ThreadingUtil.MAIN_THREAD.submit {
        try {
            promise.trigger(executor())
        } catch (e: Throwable) {
            promise.triggerFailed(e)
        }
    }
    return promise
}


fun <SUCCESS : Any, FAIL : Any> promiseSync(executor: (IPromise<SUCCESS, FAIL>) -> Unit): IPromise<SUCCESS, FAIL> {
    val promise = PromiseImpl<SUCCESS, FAIL>()
    ThreadingUtil.MAIN_THREAD.submit {
        try {
            executor(promise)
        } catch (e: Throwable) {
            e.printStackTrace()
        }
    }
    return promise
}

fun emptyPromiseSync(executor: () -> Unit): IPromise<Unit, Throwable> {
    val promise = PromiseImpl<Unit, Throwable>()
    ThreadingUtil.MAIN_THREAD.submit {
        try {
            executor()
            promise.trigger(Unit)
        } catch (e: Throwable) {
            promise.triggerFailed(e)
        }
    }
    return promise
}


fun <T : Any> promiseAsync(ordered: Boolean = false, executor: () -> T): IPromise<T, Throwable> {
    val promise = PromiseImpl<T, Throwable>()
    (if (ordered) ThreadingUtil.ASYNC_ORDERED_THREAD else ThreadingUtil.ASYNC_THREAD).submit {
        try {
            promise.trigger(executor())
        } catch (e: Throwable) {
            promise.triggerFailed(e)
        }
    }
    return promise
}

fun <SUCCESS : Any, FAIL : Any> promiseAsync(
    ordered: Boolean = false,
    executor: (IPromise<SUCCESS, FAIL>) -> Unit
): IPromise<SUCCESS, FAIL> {
    val promise = PromiseImpl<SUCCESS, FAIL>()
    (if (ordered) ThreadingUtil.ASYNC_ORDERED_THREAD else ThreadingUtil.ASYNC_THREAD).submit {
        try {
            executor(promise)
        } catch (e: Throwable) {
            e.printStackTrace()
        }
    }
    return promise
}

fun emptyPromiseAsync(ordered: Boolean = false, executor: () -> Unit): IPromise<Unit, Throwable> {
    val promise = PromiseImpl<Unit, Throwable>()
    (if (ordered) ThreadingUtil.ASYNC_ORDERED_THREAD else ThreadingUtil.ASYNC_THREAD).submit {
        try {
            executor()
            promise.trigger(Unit)
        } catch (e: Throwable) {
            promise.triggerFailed(e)
        }
    }
    return promise
}


fun <T : Any> IPromise<Unit, T>.trigger() {
    trigger(Unit)
}
