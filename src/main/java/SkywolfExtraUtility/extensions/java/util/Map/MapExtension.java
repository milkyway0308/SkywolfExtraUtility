package SkywolfExtraUtility.extensions.java.util.Map;

import manifold.ext.rt.api.Extension;
import manifold.ext.rt.api.This;
import skywolf46.extrautility.collections.maps.ListMap;

import java.util.Map;

@Extension
public class MapExtension {

    public static <K, V> ListMap<K, V> toListMap(@This Map<K, V> map) {
        return new ListMap<>(map);
    }

    public static <K, V> Map<K, V> append(@This Map<K, V> map, K k, V v) {
        map.put(k, v);
        return map;
    }

    public static <K, V> boolean minus(@This Map<K, V> map, K k) {
        return map.remove(k) != null;
    }
}