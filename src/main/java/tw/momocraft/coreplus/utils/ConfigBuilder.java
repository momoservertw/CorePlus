package tw.momocraft.coreplus.utils;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.EntityType;
import tw.momocraft.coreplus.api.CorePlusAPI;
import tw.momocraft.coreplus.handlers.ConfigHandler;
import tw.momocraft.coreplus.handlers.UtilsHandler;

import java.util.List;

public class ConfigBuilder {

    public static void start(CommandSender sender, String type) {
        String format;
        switch (type.toLowerCase()) {
            case "entity":
                createEntity();
                UtilsHandler.getLang().sendMsg(ConfigHandler.getPrefix(), sender, "&aSucceed create the configuration for group: &e" + type);
                return;
            case "material":
                createMaterial();
                UtilsHandler.getLang().sendMsg(ConfigHandler.getPrefix(), sender, "&aSucceed create the configuration for group: &e" + type);
                return;
            case "offlineplayer-name":
                createOfflinePlayerName();
                UtilsHandler.getLang().sendMsg(ConfigHandler.getPrefix(), sender, "&aSucceed create the configuration for group: &e" + type);
                return;
            case "offlineplayer-uuid":
                createOfflinePlayerUUID();
                UtilsHandler.getLang().sendMsg(ConfigHandler.getPrefix(), sender, "&aSucceed create the configuration for group: &e" + type);
                return;
        }
        UtilsHandler.getLang().sendMsg(ConfigHandler.getPrefix(), sender, "&cCan not find the group or format: " + type);
    }

    public static void createEntity() {
        String format = ConfigHandler.getConfigPath().getConfigBuilderProp().get("Entity").getKey();
        if (format == null)
            return;
        List<String> ignoreList = ConfigHandler.getConfigPath().getConfigBuilderProp().get("Entity").getValue();
        String input;
        String typeName;
        for (EntityType type : EntityType.values()) {
            typeName = type.name();
            if (ignoreList.contains(typeName))
                continue;
            input = format.replace("%type%", typeName);
            UtilsHandler.getCustomCommands().dispatchLogCustomCmd(ConfigHandler.getPrefix(),
                    "ConfigBuilder, " + input);
        }
    }

    public static void createMaterial() {
        String format = ConfigHandler.getConfigPath().getConfigBuilderProp().get("Material").getKey();
        if (format == null)
            return;
        List<String> ignoreList = ConfigHandler.getConfigPath().getConfigBuilderProp().get("Material").getValue();
        String input;
        String typeName;
        for (Material type : Material.values()) {
            typeName = type.name();
            if (ignoreList.contains(typeName))
                continue;
            input = format.replace("%type%", typeName);
            UtilsHandler.getCustomCommands().dispatchLogCustomCmd(ConfigHandler.getPrefix(),
                    "ConfigBuilder, " + input);
        }
    }

    public static void createOfflinePlayerName() {
        String format = ConfigHandler.getConfigPath().getConfigBuilderProp().get("OfflinePlayer-Name").getKey();
        if (format == null)
            return;
        List<String> ignoreList = ConfigHandler.getConfigPath().getConfigBuilderProp().get("OfflinePlayer-Name").getValue();
        String input;
        String uuid;
        String name;
        for (OfflinePlayer offlinePlayer : Bukkit.getOfflinePlayers()) {
            try {
                uuid = offlinePlayer.getUniqueId().toString();
                name = offlinePlayer.getName();
                if (ignoreList.contains(uuid) || ignoreList.contains(name))
                    continue;
                input = format.replace("%type%", name);
                UtilsHandler.getCustomCommands().dispatchLogCustomCmd(ConfigHandler.getPrefix(),
                        "ConfigBuilder, " + input);
            } catch (Exception ignored) {
            }
        }
    }

    public static void createOfflinePlayerUUID() {
        String format = ConfigHandler.getConfigPath().getConfigBuilderProp().get("OfflinePlayer-UUID").getKey();
        if (format == null)
            return;
        List<String> ignoreList = ConfigHandler.getConfigPath().getConfigBuilderProp().get("OfflinePlayer-UUID").getValue();
        String input;
        String uuid;
        String name;
        for (OfflinePlayer offlinePlayer : Bukkit.getOfflinePlayers()) {
            try {
                uuid = offlinePlayer.getUniqueId().toString();
                name = offlinePlayer.getName();
                if (ignoreList.contains(uuid) || ignoreList.contains(name))
                    continue;
                input = format.replace("%type%", uuid);
                UtilsHandler.getCustomCommands().dispatchLogCustomCmd(ConfigHandler.getPrefix(),
                        "ConfigBuilder, " + input);
            } catch (Exception ignored) {
            }
        }
    }
}
