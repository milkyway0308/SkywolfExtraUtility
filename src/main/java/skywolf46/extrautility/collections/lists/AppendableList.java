package skywolf46.extrautility.collections.lists;

import java.util.ArrayList;

public class AppendableList<K> extends ArrayList<K> {

    public AppendableList<K> append(K k) {
        add(k);
        return this;
    }
}
