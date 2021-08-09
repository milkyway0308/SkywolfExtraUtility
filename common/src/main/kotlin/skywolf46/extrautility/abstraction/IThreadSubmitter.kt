package skywolf46.extrautility.abstraction

interface IThreadSubmitter {
    fun submit(unit: () -> Unit)
    fun stop()
}