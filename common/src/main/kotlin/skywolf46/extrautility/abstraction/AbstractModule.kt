package skywolf46.extrautility.abstraction

import skywolf46.extrautility.util.ModuleGroup

abstract class AbstractModule {
    open fun onAttach(group: ModuleGroup) {
        // Empty for implementation
    }

    open fun onDetach(group: ModuleGroup) {
        // Empty for implementation
    }

    fun register() {
        ModuleGroup.GLOBAL_GROUP.register(this)
    }

    fun register(group: ModuleGroup) {
        group.register(this)
    }
}