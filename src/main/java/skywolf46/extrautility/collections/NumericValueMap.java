package skywolf46.extrautility.collections;

import manifold.ext.rt.api.Self;

import java.util.HashMap;

public class NumericValueMap<V extends NumericItems> extends HashMap<String, V> implements NumericCollections<V> {

    public V addAndGet(String key, V val) {
        put(key, (V) (getOrDefault(key, (V) (Object) 0d) + val));
        return get(key);
    }

    public V subtractAndGet(String key, V val) {
        put(key, (V) (getOrDefault(key, (V) (Object) 0d) - val));
        return get(key);
    }

    public NumericValueMap<V> plus(V x) {
        entrySet().forEach(entry -> {
            entry.setValue((V) (entry.getValue() + x));
        });
        return this;
    }

    public NumericValueMap<V> minus(V x) {
        entrySet().forEach(entry -> {
            entry.setValue((V) (entry.getValue() - x));
        });
        return this;
    }

    public NumericValueMap<V> plus(Number x) {
        entrySet().forEach(entry -> {
            entry.setValue((V) (Object) (((Number) entry.getValue()).doubleValue() + x.doubleValue()));
        });
        return this;
    }

    public NumericValueMap<V> minus(Number x) {
        entrySet().forEach(entry -> {
            entry.setValue((V) (Object) (((Number) entry.getValue()).doubleValue() - x.doubleValue()));
        });
        return this;
    }


    public @Self NumericValueMap<V> put(String name, Number x) {
        return (NumericValueMap<V>) super.put(name, (V) x);
    }


    public static void main(String[] args) {
        NumericValueMap<NumericItems> numeric = new NumericValueMap<>();
        numeric.put("Test", 10);
        numeric = numeric + 4 + 5 - 20;
        System.out.println(numeric["Test"]);
        if (numeric - "Test") {
            System.out.println("Yay");
        }
    }

}
