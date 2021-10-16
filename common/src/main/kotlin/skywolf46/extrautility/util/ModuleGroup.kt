package skywolf46.extrautility.util

import skywolf46.extrautility.abstraction.Module

open class ModuleGroup {
    companion object {
        val GLOBAL_GROUP = ModuleGroup()
    }

    protected val groups = mutableListOf<Module>()

    open fun register(group: Module) {
        groups += group
    }

    open fun fetch(cls: Class<out Module>): Module? {
        return groups.find { x ->
            cls.isAssignableFrom(x.javaClass)
        }
    }

    open fun attach(module: Module): Module {
        module.onAttach(this)
        groups += module
        return module
    }

    open fun detach(cls: Class<out Module>): Module? {
        return fetch(cls)?.apply {
            groups.remove(this)
            this.onDetach(this@ModuleGroup)
        }
    }

}