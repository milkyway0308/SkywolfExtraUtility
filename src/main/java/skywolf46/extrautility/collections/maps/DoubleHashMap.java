package skywolf46.extrautility.collections.maps;

import java.util.HashMap;
import java.util.function.Function;

public class DoubleHashMap<K, V> extends HashMap<K, V> {
    private HashMap<V, K> reversed = new HashMap<>();

    @Override
    public V put(K key, V value) {
        reversed.put(value, key);
        return super.put(key, value);
    }

    @Override
    public V remove(Object key) {
        V ox = super.remove(key);
        if (ox != null)
            reversed.remove(ox);
        return ox;
    }

    @Override
    public V computeIfAbsent(K key, Function<? super K, ? extends V> mappingFunction) {
        return super.computeIfAbsent(key, (k) -> {
            V x = mappingFunction.apply(k);
            reversed.put(x, k);
            return x;
        });
    }

    public K getReversed(V val) {
        return reversed.get(val);
    }

    public K getReversedOrDefault(Object val, K def) {
        return reversed.getOrDefault(val, def);
    }
}
