package skywolf46.extrautility.util

import skywolf46.extrautility.abstraction.AbstractModule

open class ModuleGroup {
    companion object {
        val GLOBAL_GROUP = ModuleGroup()
    }

    protected val groups = mutableListOf<AbstractModule>()

    open fun register(group: AbstractModule) {
        groups += group
    }

    open fun fetch(cls: Class<out AbstractModule>): AbstractModule? {
        return groups.find { x ->
            cls.isAssignableFrom(x.javaClass)
        }
    }

    open fun attach(module: AbstractModule): AbstractModule {
        module.onAttach(this)
        groups += module
        return module
    }

    open fun detach(cls: Class<out AbstractModule>): AbstractModule? {
        return fetch(cls)?.apply {
            groups.remove(this)
            this.onDetach(this@ModuleGroup)
        }
    }

}