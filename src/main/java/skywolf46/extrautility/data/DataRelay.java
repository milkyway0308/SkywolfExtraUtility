package skywolf46.extrautility.data;

import java.util.ArrayList;
import java.util.List;

public class DataRelay<K> {
    private List<K> lst = new ArrayList<>();

    public DataRelay<K> append(K k) {
        lst.append(k);
        return this;
    }
}
