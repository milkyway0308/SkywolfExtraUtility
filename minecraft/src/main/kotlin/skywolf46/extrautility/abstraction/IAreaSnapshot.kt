package skywolf46.extrautility.abstraction

import org.bukkit.Material
import skywolf46.extrautility.data.*
import skywolf46.extrautility.enums.Direction

interface IAreaSnapshot : Cloneable {
    override fun clone(): IAreaSnapshot

    fun maxLength(direction: Direction): Int

    fun length(direction: Direction, baseLoc: RelativeLocationPointer): Int

    fun setType(loc: RelativeLocationPointer, data: Pair<Material, Byte>): SnapshotBlock {
        return setType(loc, SnapshotBlock(loc, data.first, data.second))
    }

    fun setType(loc: RelativeLocationPointer, data: SnapshotBlock): SnapshotBlock

    fun getType(loc: RelativeLocationPointer): SnapshotBlock

    fun paste(loc: RelativeLocationPointer, ignoreAir: Boolean, ignoreGravity: Boolean = false): UndoAction

    fun paste(
        loc: RelativeLocationPointer,
        ignoreAir: Boolean = false,
        ignoreGravity: Boolean = false,
        undoCallStack: UndoCallStack? = null,
    ): UndoCallStack {
        val stack = undoCallStack ?: UndoCallStack()
        stack.addUndo(paste(loc, ignoreAir, ignoreGravity))
        return stack
    }

    fun determineChunks(): WrappedChunks<RelativeLocationPointer>

    fun getOriginalArea() : IArea?
}