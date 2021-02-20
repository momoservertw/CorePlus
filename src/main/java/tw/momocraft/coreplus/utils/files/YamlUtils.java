package tw.momocraft.coreplus.utils.files;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import tw.momocraft.coreplus.handlers.ConfigHandler;
import tw.momocraft.coreplus.handlers.UtilsHandler;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class YamlUtils {

    private final Map<String, YamlConfiguration> yamlMap = new HashMap<>();

    public YamlUtils() {
        loadFile("discord_message");

        UtilsHandler.getLang().sendConsoleMsg(ConfigHandler.getPluginPrefix(),
                "&fYaml file: " + yamlMap.keySet().toString());
    }

    private void loadFile(String group) {
        String filePath;
        String fileName;
        switch (group) {
            case "discord_messages":
                filePath = Bukkit.getServer().getWorldContainer().getPath() + "//plugins//DiscordSRV";
                fileName = "messages.yml";
                break;
            default:
                return;
        }
        File file = new File(filePath, fileName);
        yamlMap.put(group, YamlConfiguration.loadConfiguration(file));
    }

    private void loadFile(String fileName, String filePath) {
        File file = new File(filePath, fileName);
        yamlMap.put(fileName, YamlConfiguration.loadConfiguration(file));
    }

    public Map<String, YamlConfiguration> getYamlMap() {
        return yamlMap;
    }

    public FileConfiguration getConfig(String fileName) {
        YamlConfiguration yamlConfiguration = yamlMap.get(fileName);
        if (yamlConfiguration == null) {
            loadFile(fileName);
        }
        return yamlConfiguration;
    }
}
