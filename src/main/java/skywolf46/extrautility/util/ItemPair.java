package skywolf46.extrautility.util;

public class ItemPair<K, V> {
    private K k;
    private V v;

    public ItemPair(K k, V v) {
        this.k = k;
        this.v = v;
    }

    public K getK() {
        return k;
    }

    public V getV() {
        return v;
    }
}
