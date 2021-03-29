package tw.momocraft.coreplus.utils.customcommands;

import org.bukkit.Material;
import org.bukkit.Particle;

public class ParticleMap {

    private String groupName;
    private Particle type;
    private int amount = 1;
    private int times = 1;
    private int interval = 20;
    private double offsetX = 0;
    private double offsetY = 0;
    private double offsetZ = 0;
    private double extra = 0;
    private int colorR = 255;
    private int colorG = 255;
    private int colorB = 255;
    private String colorType;
    private Material material;


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

    public int getColorR() {
        return colorR;
    }

    public int getColorG() {
        return colorG;
    }

    public int getColorB() {
        return colorB;
    }

    public String getColorType() {
        return colorType;
    }

    public Material getMaterial() {
        return material;
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

    public void setColorR(int colorR) {
        this.colorR = colorR;
    }

    public void setColorG(int colorG) {
        this.colorG = colorG;
    }

    public void setColorB(int colorB) {
        this.colorB = colorB;
    }

    public void setColorType(String colorType) {
        this.colorType = colorType;
    }

    public void setMaterial(Material material) {
        this.material = material;
    }
}
