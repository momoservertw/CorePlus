package tw.momocraft.coreplus.utils.customcommands;

import org.bukkit.Particle;

public class ParticleMap {

    private String groupName;
    private Particle type;
    private int amount;
    private int times;
    private int interval;
    private double offsetX;
    private double offsetY;
    private double offsetZ;
    private double extra;

    public String getGroupName() {
        return groupName;
    }

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

    public double getOffsetX() {
        return offsetX;
    }

    public double getOffsetY() {
        return offsetY;
    }

    public double getOffsetZ() {
        return offsetZ;
    }

    public double getExtra() {
        return extra;
    }


    public void setGroupName(String groupName) {
        this.groupName = groupName;
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

    public void setOffsetX(double offsetX) {
        this.offsetX = offsetX;
    }

    public void setOffsetY(double offsetY) {
        this.offsetY = offsetY;
    }

    public void setOffsetZ(double offsetZ) {
        this.offsetZ = offsetZ;
    }

    public void setExtra(double extra) {
        this.extra = extra;
    }
}
