package skywolf46.extrautility.cooldown;

public class Cooldown {
    private long expire = -1;

    public long getLeftCooldown() {
        return Math.max(0, expire - System.currentTimeMillis());
    }

    public int getLeftCooldownSeconds() {
        return (int) (getLeftCooldown() / 1000);
    }

    public Cooldown applyCooldown(long ms) {
        expire = System.currentTimeMillis() + ms;
        return this;
    }

    public Cooldown applyCooldown(double seconds) {
        return applyCooldown((long) (seconds * 1000L));
    }

    public Cooldown applyCooldownSecond(double second) {
        return applyCooldown(second);
    }

    public boolean hasCooldown(){
        return expire >= System.currentTimeMillis();
    }
}
