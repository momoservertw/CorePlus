package tw.momocraft.coreplus.utils.customcommands;

import org.bukkit.Sound;

public class SoundMap {
    private Sound type;
    private int volume;
    private int pitch;
    private int times;
    private int interval;

    public Sound getType() {
        return type;
    }

    public int getVolume() {
        return volume;
    }

    public int getPitch() {
        return pitch;
    }

    public int getTimes() {
        return times;
    }

    public int getInterval() {
        return interval;
    }


    public void setType(Sound type) {
        this.type = type;
    }

    public void setVolume(int volume) {
        this.volume = volume;
    }

    public void setPitch(int pitch) {
        this.pitch = pitch;
    }

    public void setTimes(int times) {
        this.times = times;
    }

    public void setInterval(int interval) {
        this.interval = interval;
    }
}
