package skywolf46.extrautility.collections.maps;

import lombok.Getter;

import java.util.*;
import java.util.stream.Collectors;


public class ListMap<K, V> implements Map<K, V> {
    @Getter
    private Map<K, V> map;
    @Getter
    private List<V> list;
    private boolean requireSort;

    public ListMap(Map<K, V> kv) {
        this(kv, false);
    }


    public ListMap(Map<K, V> kv, boolean requireSort) {
        this.map = kv;
        this.list = new ArrayList<>(kv.values());
        this.requireSort = requireSort;
        this.list.sort(null);
    }

    public ListMap(Map<K, V> kv, List<V> lst) {
        this(kv, lst, false);
    }

    public ListMap(Map<K, V> kv, List<V> lst, boolean reqSort) {
        this.map = kv;
        this.list = lst;
        this.requireSort = reqSort;
    }


    @Override
    public int size() {
        return map.size();
    }

    @Override
    public boolean isEmpty() {
        return map.isEmpty();
    }

    @Override
    public boolean containsKey(Object key) {
        return map.containsKey(key);
    }

    @Override
    public boolean containsValue(Object value) {
        return map.containsValue(value);
    }

    @Override
    public V get(Object key) {
        return map.get(key);
    }

    @Override
    public V put(K key, V value) {
        if (!list.contains(value))
            list.add(value);
        if (requireSort)
            list.sort(null);
        return map.put(key, value);
    }

    @Override
    public V remove(Object key) {
        V o = map.remove(key);
        if (o != null)
            list.remove(o);
        return o;
    }

    @Override
    public void putAll(Map<? extends K, ? extends V> m) {
        map.putAll(m);
        list.addAll(m.values());
        list = list.stream().distinct().collect(Collectors.toList());
        if (requireSort)
            list.sort(null);
    }

    @Override
    public void clear() {
        map.clear();
        list.clear();
    }

    @Override
    public Set<K> keySet() {
        return map.keySet();
    }

    @Override
    public Collection<V> values() {
        return map.values();
    }

    @Override
    public Set<Entry<K, V>> entrySet() {
        return map.entrySet();
    }

    public List<V> orderedValues() {
        return new ArrayList<>(list);
    }
}
