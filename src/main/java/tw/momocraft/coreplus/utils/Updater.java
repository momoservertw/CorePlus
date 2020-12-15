package tw.momocraft.coreplus.utils;

import org.bukkit.command.CommandSender;
import tw.momocraft.coreplus.api.UpdateInterface;
import tw.momocraft.coreplus.handlers.ConfigHandler;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

public class Updater implements UpdateInterface {

    @Override
    public void check(String prefix, CommandSender sender, String plugin, String ver) {
        if (!ConfigHandler.getConfig("config.yml").getBoolean("Check-Updates")) {
            return;
        }
        if (prefix == null)
            prefix = "";
        String id = "";
        switch (plugin) {
            case "BarrierPlus":
                id = "70510";
                break;
            case "EntityPlus":
                id = "70592";
                break;
            case "RegionPlus":
                id = "76878";
                break;
            case "PlayerdataPlus":
                id = "75169";
                break;
            case "LotteryPlus":
                id = "85995";
                break;
            case "InventoryPlus":
                id = "86452";
                break;
            case "HotkeyPlus":
                id = "86471";
                break;
            case "SlimeChunkPlus":
                id = "86532";
                break;
        }
        if (ver == null) {
            ConfigHandler.getLang().sendErrorMsg(prefix, "&cCorePlus is out of date.");
            return;
        }
        if (searching(sender, prefix, plugin, ver, id)) {
            ConfigHandler.getLang().sendMsg(prefix, sender, "&eFound new version: &ev" + ver);
            ConfigHandler.getLang().sendMsg(prefix, sender, "&fhttps://www.spigotmc.org/resources/" + plugin + "." + id + "/history");
        } else {
            ConfigHandler.getLang().sendMsg(prefix, sender, "&fYou are up to date!");
        }

    }

    private boolean searching(CommandSender sender, String prefix, String plugin, String ver, String id) {
        ConfigHandler.getLang().sendMsg(prefix, sender, "Checking updates...");
        try {
            URLConnection connection = new URL("https://api.spigotmc.org/legacy/update.php?resource=" + id + "?_=" + System.currentTimeMillis()).openConnection();
            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String onlineVer = reader.readLine();
            reader.close();

            onlineVer = onlineVer.replaceAll("[a-z]", "").replace("-SNAPSHOT", "")
                    .replace("-BETA", "").replace("-ALPHA", "")
                    .replace("-RELEASE", "");
            String[] onlineVerSplit = onlineVer.split("\\.");
            String[] verSplit = ver.split("\\.");
            if ((Integer.parseInt(onlineVerSplit[0]) > Integer.parseInt(verSplit[0]) ||
                    Integer.parseInt(onlineVerSplit[1]) > Integer.parseInt(verSplit[1]) || Integer.parseInt(onlineVerSplit[2]) > Integer.parseInt(verSplit[2]))) {
                return true;
            }
        } catch (Exception e) {
            ConfigHandler.getLang().sendMsg(prefix, sender, "&cFailed to check new updates for " + plugin + ".");
        }
        return false;
    }
}