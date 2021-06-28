package skywolf46.extrautility.data

data class UndoAction(private val undoCall: () -> Unit) {
    fun undo() {
        undoCall()
    }

}