package tw.momocraft.coreplus.api;

import org.bukkit.command.CommandSender;

public interface UpdaterInterface {

    /**
     * Checking the plugin update.
     *
     * @param pluginName the executing plugin name.
     * @param prefix     the executing plugin prefix.
     * @param sender     the executing player or console.
     * @param plugin     the checking name of plugin.
     * @param ver        the current version of that plugin..
     * @param auto       is running when plugin starting.
     */
    void check(String pluginName, String prefix, CommandSender sender, String plugin, String ver, boolean auto);
}
