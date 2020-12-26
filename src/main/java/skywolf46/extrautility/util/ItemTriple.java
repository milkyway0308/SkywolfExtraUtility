package skywolf46.extrautility.util;

public class ItemTriple<K, V, X> {
    private K k;
    private V v;
    private X x;

    public ItemTriple(K k, V v, X x) {
        this.k = k;
        this.v = v;
        this.x = x;
    }

    public K getK() {
        return k;
    }

    public V getV() {
        return v;
    }

    public X getX() {
        return x;
    }
}
