package tw.momocraft.coreplus.utils.file;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.YamlConfiguration;
import tw.momocraft.coreplus.handlers.ConfigHandler;
import tw.momocraft.coreplus.handlers.UtilsHandler;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class YamlUtils {

    public Map<String, String> getYMALProp() {
        return ConfigHandler.getConfigPath().getYMALProp();
    }

    private final Map<String, YamlConfiguration> fileMap = new HashMap<>();
    private final List<String> customList = new ArrayList<>();

    public YamlUtils() {
        loadGroup("discord_messages");
        loadCustom();

        sendLoadedMsg();
    }

    private void sendLoadedMsg() {
        if (!customList.isEmpty())
            UtilsHandler.getMsg().sendConsoleMsg(ConfigHandler.getPluginPrefix(),
                    "Loaded YAML files: " + customList.toString());
    }

    private void loadCustom() {
        Map<String, String> prop = ConfigHandler.getConfigPath().getPropProp();
        for (String groupName : prop.keySet()) {
            load(ConfigHandler.getPlugin(), groupName, prop.get(groupName));
            customList.add(groupName);
        }
    }

    private boolean loadGroup(String group) {
        String filePath;
        switch (group) {
            case "discord_messages":
                filePath = Bukkit.getServer().getWorldContainer().getPath() + "//plugins//DiscordSRV//messages.yml";
                break;
            default:
                UtilsHandler.getMsg().sendErrorMsg(ConfigHandler.getPlugin(),
                        "Cannot load the YAML file: " + group);
                return false;
        }
        return load(ConfigHandler.getPlugin(), group, filePath);
    }

    public boolean load(String pluginName, String group, String filePath) {
        try {
            fileMap.put(group, YamlConfiguration.loadConfiguration(new File(filePath)));
            return true;
        } catch (Exception ex) {
            UtilsHandler.getMsg().sendErrorMsg(pluginName,
                    "Cannot load the YAML file: " + filePath);
            return false;
        }
    }


    public String getString(String pluginName, String group, String input) {
        try {
            return fileMap.get(group).getString(input);
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
            return fileMap.get(group).getStringList(input);
        } catch (Exception ex) {
            UtilsHandler.getMsg().sendErrorMsg(pluginName,
                    "An error occurred while getting the value of \"" + input + "\".");
            UtilsHandler.getMsg().sendErrorMsg(pluginName,
                    "Can not find the YAML group of \"" + group + "\".");
            return null;
        }
    }

    public YamlConfiguration getConfig(String group) {
        YamlConfiguration yamlConfiguration = fileMap.get(group);
        if (yamlConfiguration == null) {
            loadGroup(group);
            return fileMap.get(group);
        }
        return yamlConfiguration;
    }
}
