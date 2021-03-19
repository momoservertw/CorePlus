package tw.momocraft.coreplus.utils.customcommands;

import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import tw.momocraft.coreplus.CorePlus;
import tw.momocraft.coreplus.handlers.ConfigHandler;
import tw.momocraft.coreplus.handlers.UtilsHandler;

public class SoundUtils {

    public static void sendSound(String pluginName, Player player, Location loc, String type) {
        SoundMap soundMap = ConfigHandler.getConfigPath().getSoundProp().get(type);
        if (soundMap == null) {
            try {
                player.playSound(loc, Sound.valueOf(type), 1, 1);
            } catch (Exception ex) {
                UtilsHandler.getLang().sendErrorMsg(pluginName, "Can not find the sound type: \"" + type + "\"");
                UtilsHandler.getLang().sendDebugTrace(ConfigHandler.isDebugging(), pluginName, ex);
            }
            return;
        }
        sendSound(player, loc, soundMap);
    }

    public static void sendSound(Player player, Location loc, SoundMap soundMap) {
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
