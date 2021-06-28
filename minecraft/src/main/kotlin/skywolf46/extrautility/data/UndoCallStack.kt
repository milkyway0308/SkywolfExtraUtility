package skywolf46.extrautility.data

import java.lang.IllegalStateException

class UndoCallStack {
    private val undoList = mutableListOf<UndoAction>()

    fun undo(): UndoCallStack {
        if (undoList.isEmpty())
            throw IllegalStateException("Nothing to undo")
        undoList.removeAt(undoList.size - 1).undo()
        return this
    }

    fun removeUndo() {
        if (undoList.isEmpty())
            throw IllegalStateException("Nothing to undo")
        undoList.removeAt(undoList.size - 1)
    }

    fun addUndo(undoAction: UndoAction) {
        undoList += undoAction
    }
}