package skywolf46.extrautility.cooldown

import skywolf46.extrautility.cooldown.Cooldown

open class CooldownStorage {
    private val storage: MutableMap<String, Cooldown> = HashMap()

    open fun cooldownOf(str: String) : Cooldown = storage.computeIfAbsent(str) { Cooldown() }



}