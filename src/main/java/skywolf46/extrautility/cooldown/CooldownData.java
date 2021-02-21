package skywolf46.extrautility.cooldown;

import java.util.HashMap;

public class CooldownData {
    private HashMap<String, Cooldown> cools = new HashMap<>();

    public Cooldown get(String name) {
        return cools.computeIfAbsent(name, x -> new Cooldown());
    }
}
