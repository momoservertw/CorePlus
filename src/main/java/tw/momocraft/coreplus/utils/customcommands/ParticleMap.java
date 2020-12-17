package tw.momocraft.coreplus.utils.customcommands;

import org.bukkit.Particle;

public class ParticleMap {

    private Particle type;
    private int amount;
    private int times;
    private int interval;

    public Particle getType() {
        return type;
    }

    public int getAmount() {
        return amount;
    }

    public int getTimes() {
        return times;
    }

    public int getInterval() {
        return interval;
    }

    public void setType(Particle type) {
        this.type = type;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public void setTimes(int times) {
        this.times = times;
    }

    public void setInterval(int interval) {
        this.interval = interval;
    }
}
