package skywolf46.extrautility.cooldown;

import skywolf46.extrautility.abstraction.AbstractStorage;

import java.util.UUID;

public class CooldownStorage extends AbstractStorage<UUID, Cooldown, Long> {
    @Override
    protected Cooldown generate(UUID uid, Long aLong) {
        return new Cooldown().applyCooldown(aLong);
    }

    @Override
    protected Cooldown generateDefault(UUID key) {
        return new Cooldown();
    }

    @Override
    public AbstractStorage<UUID, Cooldown, Long> createNew() {
        return new CooldownStorage();
    }
}
