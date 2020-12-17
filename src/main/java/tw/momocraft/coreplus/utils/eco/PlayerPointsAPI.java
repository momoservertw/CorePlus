package tw.momocraft.coreplus.utils.eco;

import org.black_ixx.playerpoints.PlayerPoints;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;

import java.util.UUID;

public class PlayerPointsAPI {

    private PlayerPoints pp;

    public PlayerPointsAPI() {
        Plugin plugin = Bukkit.getServer().getPluginManager().getPlugin("PlayerPoints");
        if (plugin != null) {
            pp = ((PlayerPoints) plugin);
        }
    }

    public double setPoints(UUID uuid, double points) {
        pp.getAPI().set(uuid, (int) points);
        return points;
    }

    public double takePoints(UUID uuid, double points) {
        pp.getAPI().take(uuid, (int) points);
        return getBalance(uuid);
    }

    public double givePoints(UUID uuid, double points) {
        pp.getAPI().give(uuid, (int) points);
        return getBalance(uuid);
    }

    public double getBalance(UUID uuid) {
        return pp.getAPI().look(uuid);
    }
}
