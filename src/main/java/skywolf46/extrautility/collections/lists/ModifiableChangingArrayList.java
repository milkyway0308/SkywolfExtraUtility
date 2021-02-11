package skywolf46.extrautility.collections.lists;

import skywolf46.extrautility.collections.exception.UnmodifiableCollectionException;

import java.util.ArrayList;

public class ModifiableChangingArrayList<T> extends ArrayList<T> {
    private boolean accepting = true;

    public ModifiableChangingArrayList() {

    }

    public ModifiableChangingArrayList(Object... objects) {
        for (Object ox : objects)
            add((T) ox);
        accepting = false;
    }

    public void stopAccepting() {
        this.accepting = false;
    }

    @Override
    public boolean add(T t) {
        if (!accepting)
            throw new UnmodifiableCollectionException();
        return super.add(t);
    }
}
