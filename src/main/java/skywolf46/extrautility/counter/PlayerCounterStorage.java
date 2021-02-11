package skywolf46.extrautility.counter;

import skywolf46.extrautility.abstraction.AbstractStorage;

import java.util.HashMap;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

public class PlayerCounterStorage extends AbstractStorage<UUID, AtomicInteger, Integer> {
    private HashMap<UUID, AtomicInteger> item = new HashMap<>();

    @Override
    protected AtomicInteger generate(UUID key, Integer integer) {
        return item.computeIfAbsent(key, a -> new AtomicInteger(integer));
    }

    @Override
    protected AtomicInteger generateDefault(UUID key) {
        return item.computeIfAbsent(key, a -> new AtomicInteger());
    }

    @Override
    public AbstractStorage<UUID, AtomicInteger, Integer> createNew() {
        return new PlayerCounterStorage();
    }
}
