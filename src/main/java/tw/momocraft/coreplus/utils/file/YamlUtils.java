package tw.momocraft.coreplus.utils.file;

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
        loadCustom();
        loadGroup("discord_messages");

        sendLoadedMsg();
    }

    private void sendLoadedMsg() {
        UtilsHandler.getMsg().sendConsoleMsg(ConfigHandler.getPluginPrefix(),
                "&fLoaded YAML files: " + yamlMap.keySet().toString());
    }

    private void loadCustom() {
        Map<String, String> prop = ConfigHandler.getConfigPath().getPropProp();
        for (String groupName : prop.keySet())
            load(ConfigHandler.getPluginName(), groupName, prop.get(groupName));
    }

    private boolean loadGroup(String group) {
        String filePath;
        switch (group) {
            case "discord_messages":
                filePath = Bukkit.getServer().getWorldContainer().getPath() + "//plugins//DiscordSRV//messages.yml";
                break;
            default:
                UtilsHandler.getMsg().sendErrorMsg(ConfigHandler.getPluginName(),
                        "Cannot load the YAML file: " + group);
                return false;
        }
        return load(ConfigHandler.getPluginName(), group, filePath);
    }

    public boolean load(String pluginName, String group, String filePath) {
        try {
            yamlMap.put(group, YamlConfiguration.loadConfiguration(new File(filePath)));
            return true;
        } catch (Exception ex) {
            UtilsHandler.getMsg().sendErrorMsg(pluginName,
                    "Cannot load the YAML file: " + filePath);
            return false;
        }
    }


    public String getString(String pluginName, String group, String input) {
        try {
            return yamlMap.get(group).getString(input);
        } catch (Exception ex) {
            UtilsHandler.getMsg().sendErrorMsg(pluginName,
                    "An error occurred while getting the value of \"" + input + "\".");
            UtilsHandler.getMsg().sendErrorMsg(pluginName,
                    "Can not find the YAML group of \"" + group + "\".");
            return null;
        }
    }

    public List<String> getStringList(String pluginName, String group, String input) {
        try {
            return yamlMap.get(group).getStringList(input);
        } catch (Exception ex) {
            UtilsHandler.getMsg().sendErrorMsg(pluginName,
                    "An error occurred while getting the value of \"" + input + "\".");
            UtilsHandler.getMsg().sendErrorMsg(pluginName,
                    "Can not find the YAML group of \"" + group + "\".");
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
