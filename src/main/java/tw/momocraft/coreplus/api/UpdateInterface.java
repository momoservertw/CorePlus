package tw.momocraft.coreplus.api;

import org.bukkit.command.CommandSender;

public interface UpdateInterface {

    /**
     * Checking the plugin update.
     *
     * @param prefix the executing plugin prefix.
     * @param sender the executing player or console.
     * @param plugin the checking name of plugin.
     * @param ver the current version of that plugin.
     */
    void check(String prefix, CommandSender sender, String plugin, String ver);
}
