package tw.momocraft.coreplus.utils;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.EntityType;
import tw.momocraft.coreplus.api.CorePlusAPI;
import tw.momocraft.coreplus.handlers.ConfigHandler;
import tw.momocraft.coreplus.handlers.UtilsHandler;

public class ConfigBuilder {

    public static void start(CommandSender sender, String type) {
        String format;
        switch (type.toLowerCase()) {
            case "entity":
                format = ConfigHandler.getConfigPath().getConfigBuilderProp().get("Entity");
                if (format == null)
                    break;
                createEntity(format);
                UtilsHandler.getLang().sendMsg(ConfigHandler.getPrefix(), sender, "&aSucceed create the configuration for group: &e" + type);
                return;
            case "material":
                format = ConfigHandler.getConfigPath().getConfigBuilderProp().get("Material");
                if (format == null)
                    break;
                createMaterial(format);
                UtilsHandler.getLang().sendMsg(ConfigHandler.getPrefix(), sender, "&aSucceed create the configuration for group: &e" + type);
                return;
            case "offlineplayer-name":
                format = ConfigHandler.getConfigPath().getConfigBuilderProp().get("OfflinePlayer-Name");
                if (format == null)
                    break;
                createOfflinePlayerName(format);
                UtilsHandler.getLang().sendMsg(ConfigHandler.getPrefix(), sender, "&aSucceed create the configuration for group: &e" + type);
                return;
            case "offlineplayer-uuid":
                format = ConfigHandler.getConfigPath().getConfigBuilderProp().get("OfflinePlayer-UUID");
                if (format == null)
                    break;
                createOfflinePlayerUUID(format);
                UtilsHandler.getLang().sendMsg(ConfigHandler.getPrefix(), sender, "&aSucceed create the configuration for group: &e" + type);
                return;
        }
        UtilsHandler.getLang().sendMsg(ConfigHandler.getPrefix(), sender, "&cCan not find the group or format: " + type);
    }

    public static void createEntity(String format) {
        String input;
        for (EntityType type : EntityType.values()) {
            input = format.replace("%type%", type.name());
            CorePlusAPI.getCommandManager().dispatchLogCustomCmd(ConfigHandler.getPrefix(),
                    "ConfigBuilder, " + input);
        }
    }

    public static void createMaterial(String format) {
        String input;
        for (Material type : Material.values()) {
            input = format.replace("%type%", type.name());
            CorePlusAPI.getCommandManager().dispatchLogCustomCmd(ConfigHandler.getPrefix(),
                    "ConfigBuilder, " + input);
        }
    }

    public static void createOfflinePlayerName(String format) {
        String input;
        for (OfflinePlayer offlinePlayer : Bukkit.getOfflinePlayers()) {
            try {
                input = format.replace("%type%", offlinePlayer.getName());
                CorePlusAPI.getCommandManager().dispatchLogCustomCmd(ConfigHandler.getPrefix(),
                        "ConfigBuilder, " + input);
            } catch (Exception ignored) {
            }
        }
    }

    public static void createOfflinePlayerUUID(String format) {
        String input;
        for (OfflinePlayer offlinePlayer : Bukkit.getOfflinePlayers()) {
            try {
                input = format.replace("%type%", offlinePlayer.getUniqueId().toString());
                CorePlusAPI.getCommandManager().dispatchLogCustomCmd(ConfigHandler.getPrefix(),
                        "ConfigBuilder, " + input);
            } catch (Exception ignored) {
            }
        }
    }
}
