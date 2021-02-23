package tw.momocraft.coreplus.utils.customcommands;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import tw.momocraft.coreplus.CorePlus;
import tw.momocraft.coreplus.handlers.UtilsHandler;

public class SoundUtils {

    public static void sendSound(String pluginName, Player player, Location loc, SoundMap soundMap) {
        new BukkitRunnable() {
            int i = 1;

            @Override
            public void run() {
                if (i > soundMap.getTimes()) {
                    cancel();
                } else {
                    ++i;
                    player.playSound(loc, soundMap.getType(), soundMap.getPitch(), soundMap.getVolume());
                }
            }
        }.runTaskTimer(CorePlus.getInstance(), 0, soundMap.getInterval());
    }
}
