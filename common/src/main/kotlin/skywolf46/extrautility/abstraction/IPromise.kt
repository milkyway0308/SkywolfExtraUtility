package skywolf46.extrautility.abstraction

interface IPromise<SUCCESS : Any, FAILED : Any> {
    fun success(data: (SUCCESS) -> Unit)

    fun failed(data: (FAILED) -> Unit)

    fun after(data: () -> Unit)

    fun trigger(data: SUCCESS)

    fun triggerFailed(throwable: FAILED)
}