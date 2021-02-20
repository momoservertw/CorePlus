package tw.momocraft.coreplus.utils;

import com.onarandombox.MultiverseCore.MultiverseCore;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;

public class MultiverseCoreUtils {

    private MultiverseCore mv;

    public MultiverseCoreUtils() {
        Plugin plugin = Bukkit.getServer().getPluginManager().getPlugin("MultiverseCore");
        if (plugin != null) {
            mv = new MultiverseCore();
        }
    }

    public boolean isPvPEnabled(String worldName) {
        return mv.getMVWorldManager().getMVWorld(worldName).isPVPEnabled();
    }
}
