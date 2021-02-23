package tw.momocraft.coreplus.utils.files;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.YamlConfiguration;
import tw.momocraft.coreplus.handlers.ConfigHandler;
import tw.momocraft.coreplus.handlers.UtilsHandler;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class YamlUtils {

    private final Map<String, YamlConfiguration> yamlMap = new HashMap<>();

    public YamlUtils() {
        loadGroup("discord_messages");

        sendLoadedMsg();
    }

    private void sendLoadedMsg() {
        UtilsHandler.getLang().sendConsoleMsg(ConfigHandler.getPluginPrefix(),
                "&fLoaded YAML files: " + yamlMap.keySet().toString());
    }

    public boolean load(String group, String filePath) {
        try {
            yamlMap.put(group, YamlConfiguration.loadConfiguration(new File(filePath)));
            return true;
        } catch (Exception ex) {
            return false;
        }
    }

    private boolean loadGroup(String group) {
        String filePath;
        switch (group) {
            case "discord_messages":
                filePath = Bukkit.getServer().getWorldContainer().getPath() + "//plugins//DiscordSRV//messages.yml";
                break;
            default:
                return false;
        }
        return load(group, filePath);
    }


    public String getString(String group, String input) {
        YamlConfiguration config = yamlMap.get(group);
        if (config == null) {
            loadGroup(group);
        }
        try {
            return config.getString(input);
        } catch (Exception ex) {
            return null;
        }
    }

    public List<String> getStringList(String group, String input) {
        YamlConfiguration config = yamlMap.get(group);
        if (config == null) {
            loadGroup(group);
        }
        try {
            return config.getStringList(input);
        } catch (Exception ex) {
            return null;
        }
    }

    public YamlConfiguration getConfig(String group) {
        YamlConfiguration yamlConfiguration = yamlMap.get(group);
        if (yamlConfiguration == null) {
            loadGroup(group);
            return yamlMap.get(group);
        }
        return yamlConfiguration;
    }
}
