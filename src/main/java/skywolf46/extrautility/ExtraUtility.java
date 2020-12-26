package skywolf46.extrautility;

import skywolf46.extrautility.abstraction.AbstractStorage;
import skywolf46.extrautility.cooldown.Cooldown;
import skywolf46.extrautility.cooldown.CooldownStorage;

import java.util.HashMap;

public class ExtraUtility {
    private static HashMap<Class, AbstractStorage> factory = new HashMap<>();

    static {
        registerStorage(Cooldown.class, new CooldownStorage());
    }


    public static <K, V, X> void registerStorage(Class<V> from, AbstractStorage<K, V, X> ks) {
        factory.put(from, ks);
    }

    public static <T extends AbstractStorage> T generateStorage(Class target) {
        return factory.containsKey(target) ? (T) factory.get(target).createNew() : null;
    }
}
