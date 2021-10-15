package skywolf46.extrautility.data

import org.bukkit.Chunk
import org.bukkit.World
import org.bukkit.util.Vector

class WrappedChunks<X : Any> : HashMap<Pair<Int, Int>, X>() {

    fun computeIfAbsent(loc: Vector, unit: Vector.() -> X): X {
        return computeIfAbsent(loc.blockX.shr(4) to loc.blockZ.shr(4)) {
            unit(loc)
        }
    }

    fun computeIfAbsent(loc: RelativeLocationPointer, unit: RelativeLocationPointer.() -> X) : X{
        return computeIfAbsent(loc.toVector()) {
            unit(loc)
        }
    }

    fun addChunk(vector: Vector, data: X) {
        put(vector.blockX.shr(4) to vector.blockZ.shr(4), data)
    }

    fun containsChunk(vector: Vector): Boolean {
        return contains(vector.blockX.shr(4) to vector.blockZ.shr(4))
    }

    fun toChunkList(world: World): List<Chunk> {
        return map { x -> world.getChunkAt(x.key.first, x.key.second) }
    }

    fun toChunkMap(world: World): Map<Pair<Int, Int>, Chunk> {
        return map { x -> x.key }.associateWith { x -> world.getChunkAt(x.first, x.second) }
    }

}