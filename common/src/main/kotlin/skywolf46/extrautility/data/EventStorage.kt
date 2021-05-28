package skywolf46.extrautility.data

import skywolf46.extrautility.util.ClassUtil.iterateParentClasses
import skywolf46.extrautility.util.MethodWrapper
import skywolf46.extrautility.util.PriorityReference
import java.util.ArrayList

class EventStorage {
    private val map = mutableMapOf<Class<*>, MutableList<PriorityReference<MethodWrapper>>>()

    fun insertEventHandler(clazz: Class<*>, priority: Int, wrapper: MethodWrapper) {
        map.computeIfAbsent(clazz) {
            object : ArrayList<PriorityReference<MethodWrapper>>() {
                override fun add(element: PriorityReference<MethodWrapper>): Boolean {
                    val temp = super.add(element)
                    sort()
                    return temp
                }
            }
        }.add(PriorityReference(wrapper, priority))
    }

    fun callEvent(any: Any) {
        any.javaClass.iterateParentClasses {
            map[this]?.forEach {
                it.data.invoke(any)
            }
        }
    }
}