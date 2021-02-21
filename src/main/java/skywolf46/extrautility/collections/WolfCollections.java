package skywolf46.extrautility.collections;

import skywolf46.extrautility.collections.lists.ModifiableChangingArrayList;
import skywolf46.extrautility.collections.maps.DoubleHashMap;
import skywolf46.extrautility.util.ItemPair;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class WolfCollections {
    private static final ModifiableChangingArrayList EMPTY_LIST = new ModifiableChangingArrayList(new Object[0]);

    public static ModifiableChangingArrayList listEmpty() {
        return EMPTY_LIST;
    }

    public static <T> ModifiableChangingArrayList<T> listUnmodifiable(T... obj) {
        HashMap<String, Object> map = new HashMap<>();

        return new ModifiableChangingArrayList<>(obj);
    }

    public static <T> ModifiableChangingArrayList<T> list(T... obj) {
        ModifiableChangingArrayList<T> ar = new ModifiableChangingArrayList<>();
        Collections.addAll(ar, obj);
        return ar;
    }

    public static <K, V> DoubleHashMap<K, V> create(ItemPair<K, V>... kv) {
        DoubleHashMap<K, V> map = new DoubleHashMap<>();
        for (ItemPair<K, V> k : kv) {
            map.put(k.getK(), k.getV());
        }
        return map;
    }
}
