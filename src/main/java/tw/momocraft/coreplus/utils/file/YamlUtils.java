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

    private final Map<String, YamlConfiguration> configMap = new HashMap<>();
    private final Map<String, File> fileMap = new HashMap<>();
    private final List<String> customList = new ArrayList<>();

    public YamlUtils() {
        loadCustom();
        loadGroup("discord_messages");

        sendLoadedMsg();
    }

    public boolean load(String pluginName, String group, String filePath) {
        try {
            File file = new File(filePath);
            configMap.put(group, YamlConfiguration.loadConfiguration(file));
            fileMap.put(group, file);
            return true;
        } catch (Exception ex) {
            UtilsHandler.getMsg().sendErrorMsg(pluginName,
                    "Cannot load the YAML file: " + filePath);
            return false;
        }
    }

    public void create(String pluginName, String path, String name) {
        File file = new File(path, name);
        if (!file.exists()) {
            try {
                if (!file.mkdir())
                    UtilsHandler.getMsg().sendErrorMsg(pluginName,
                            "Can not create YAML file: " + name);
            } catch (Exception ex) {
                UtilsHandler.getMsg().sendErrorMsg(pluginName,
                        "Can not create YAML file: " + name);
            }
        }
    }

    public void setValue(String pluginName, String groupName, String path, Object value) {
        YamlConfiguration config = configMap.get(groupName);
        config.set(path, value);
        try {
            config.save(fileMap.get(groupName));
        } catch (Exception ex) {
            UtilsHandler.getMsg().sendErrorMsg(pluginName,
                    "Can not create YAML file: " + groupName);
            UtilsHandler.getMsg().sendDebugTrace(true, pluginName, ex);
        }
    }

    public YamlConfiguration getConfig(String group) {
        YamlConfiguration yamlConfiguration = configMap.get(group);
        if (yamlConfiguration == null) {
            loadGroup(group);
            return configMap.get(group);
        }
        return yamlConfiguration;
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

    private void sendLoadedMsg() {
        if (!customList.isEmpty())
            UtilsHandler.getMsg().sendConsoleMsg(ConfigHandler.getPluginPrefix(),
                    "Loaded YAML files: " + customList.toString());
    }
}
