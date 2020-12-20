package skywolf46.extrautility.abstraction;

import java.util.HashMap;

public abstract class AbstractStorage<K, V, X> {
    private HashMap<K, V> map = new HashMap<>();

    protected abstract V generate(K key, X x);

    protected abstract V generateDefault(K key);

    public void put(K key, X v) {
        map.put(key, generate(key,v));
    }

    public V get(K key) {
        return map.get(key);
    }

    public V getOrDefault(K key) {
        return map.computeIfAbsent(key, k -> generateDefault(key));
    }


    public abstract AbstractStorage<K, V, X> createNew();
}
