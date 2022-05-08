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

    private final Map<String, YamlConfiguration> configMap = new HashMap<>();
    private final Map<String, File> fileMap = new HashMap<>();
    private final List<String> dataList = new ArrayList<>();

    public boolean isEnable() {
        return ConfigHandler.getConfigPath().isDataProp();
    }

    public void setup() {
        loadConfig();
        load(ConfigHandler.getPluginName(),
                "discord_messages",
                Bukkit.getServer().getWorldContainer().getPath() + "//plugins//DiscordSRV//messages.yml");

        UtilsHandler.getMsg().sendFileLoadedMsg("YAML", dataList);
    }

    private void loadConfig() {
        Map<String, String> prop = ConfigHandler.getConfigPath().getPropProp();
        for (String groupName : prop.keySet()) {
            load(ConfigHandler.getPluginName(), groupName, prop.get(groupName));
        }
    }

    public boolean load(String pluginName, String groupName, String filePath) {
        try {
            File file = new File(filePath);
            configMap.put(groupName, YamlConfiguration.loadConfiguration(file));
            fileMap.put(groupName, file);
            dataList.add(groupName);
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
                    "Cannot set the value for YAML file: " + groupName);
            UtilsHandler.getMsg().sendErrorMsg(pluginName,
                    "Value = " + value.toString());
            UtilsHandler.getMsg().sendDebugTrace(ConfigHandler.getPluginName(), ex);
        }
    }

    public YamlConfiguration getConfig(String groupName) {
        return configMap.get(groupName);
    }
}
