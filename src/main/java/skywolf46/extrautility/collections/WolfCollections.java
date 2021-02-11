package skywolf46.extrautility.collections;

import skywolf46.extrautility.collections.lists.ModifiableChangingArrayList;

import java.util.Collections;

public class WolfCollections {
    private static final ModifiableChangingArrayList EMPTY_LIST = new ModifiableChangingArrayList(new Object[0]);

    public static ModifiableChangingArrayList listEmpty() {
        return EMPTY_LIST;
    }

    public static <T> ModifiableChangingArrayList<T> listUnmodifiable(T... obj) {
        return new ModifiableChangingArrayList<>(obj);
    }

    public static <T> ModifiableChangingArrayList<T> list(T... obj) {
        ModifiableChangingArrayList<T> ar = new ModifiableChangingArrayList<>();
        Collections.addAll(ar, obj);
        return ar;
    }
}
