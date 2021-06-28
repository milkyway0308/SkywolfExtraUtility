package skywolf46.extrautility.areas.impl

import org.bukkit.Material
import org.bukkit.block.Block
import skywolf46.extrautility.abstraction.IAreaSnapshot
import skywolf46.extrautility.data.RelativeLocationPointer
import skywolf46.extrautility.data.SnapshotBlock
import skywolf46.extrautility.data.UndoAction
import skywolf46.extrautility.data.WrappedChunks
import skywolf46.extrautility.enums.Direction
import kotlin.math.max

class RectangleAreaSnapshot(val area: RectangleArea) : IAreaSnapshot {
    private val chunkMap = WrappedChunks<MutableMap<RelativeLocationPointer, SnapshotBlock>>()


    constructor(area: RectangleArea, doScan: Boolean = true, scanningCheck: (Block) -> Unit = {}) : this(area) {
        // Scan area
        if (doScan) {

            area.parseBlocks {
                getBlockAt(it.blockX, it.blockY, it.blockZ).apply {
                    setType(RelativeLocationPointer(it), type to data)
                    scanningCheck(this)
                }
            }
        }
    }

    override fun clone(): IAreaSnapshot {
        return RectangleAreaSnapshot(area).also {
            it.chunkMap.putAll(chunkMap)
        }
    }

    override fun maxLength(direction: Direction): Int {
        return area.sizeOf(direction).toInt()
    }

    override fun length(direction: Direction, baseLoc: RelativeLocationPointer): Int {
        return maxLength(direction)
    }

    private fun findChunkMap(loc: RelativeLocationPointer): MutableMap<RelativeLocationPointer, SnapshotBlock> {
        return chunkMap.computeIfAbsent(loc) {
            mutableMapOf()
        }
    }

    override fun setType(loc: RelativeLocationPointer, data: SnapshotBlock): SnapshotBlock {
        return getType(loc).apply {
            findChunkMap(loc)[loc] = data
        }
    }

    override fun getType(loc: RelativeLocationPointer): SnapshotBlock {
        return findChunkMap(loc).getOrElse(loc) {
            SnapshotBlock(loc, Material.AIR, 0)
        }
    }

    override fun paste(loc: RelativeLocationPointer, ignoreAir: Boolean, ignoreGravity: Boolean): UndoAction {
        val map = RectangleAreaSnapshot(area, true) {
            val next = getType(RelativeLocationPointer(it.location.toVector()))
            if (next.type == Material.AIR && ignoreAir) {
                return@RectangleAreaSnapshot
            }
            it.setType(next.type, !ignoreGravity)
            it.data = next.data
        }
        return UndoAction {
            map.paste(loc, true)
        }
    }

    override fun determineChunks(): WrappedChunks<RelativeLocationPointer> {
        TODO("Not yet implemented")
    }
}