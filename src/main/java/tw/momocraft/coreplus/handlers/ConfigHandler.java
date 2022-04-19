package tw.momocraft.coreplus.handlers;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import tw.momocraft.coreplus.CorePlus;
import tw.momocraft.coreplus.api.CorePlusAPI;
import tw.momocraft.coreplus.utils.ConfigPath;

import java.io.File;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

public class ConfigHandler {

    private static final Map<String, YamlConfiguration> configMap = new HashMap<>();

    private static ConfigPath configPath;

    public static void generateData(boolean reload) {
        setConfigPath(new ConfigPath());
        UtilsHandler.setUpFirst(reload);
        genConfigFile("config.yml");
        genConfigFile("data.yml");
        genConfigFile("groups.yml");
        genConfigFile("commands.yml");
        genConfigFile("location.yml");
        genConfigFile("blocks.yml");
        genConfigFile("condition.yml");
        genConfigFile("particles.yml");
        genConfigFile("sounds.yml");
        genConfigFile("action_bars.yml");
        genConfigFile("title_messages.yml");
        logConfigMsg();
        configPath.setupFirst();
        UtilsHandler.setUpLast(reload);
        configPath.setupLast();
        /*
       if (!reload) {
            UtilsHandler.getUpdate().check(getPlugin(), getPluginPrefix(), Bukkit.getConsoleSender(),
                    CorePlus.getInstance().getDescription().getName(),
                    CorePlus.getInstance().getDescription().getVersion(), true);
        }
        */
    }

    private static void logConfigMsg() {
        CorePlusAPI.getMsg().sendConsoleMsg(getPrefix(), "Load yaml files:" + configMap.keySet());
    }

    private static void genConfigFolder(String folderName) {
        File filePath = new File(CorePlus.getInstance().getDataFolder().getPath(), folderName);
        String[] fileList = filePath.list();
        for (String fileName : fileList) {
            if (fileName.endsWith(".yml")) {
                genConfigFile(fileName);
            }
        }
    }

    public static FileConfiguration getConfig(String fileName) {
        File filePath = CorePlus.getInstance().getDataFolder();
        File file;
        switch (fileName) {
            case "config.yml":
            case "spigot.yml":
            case "groups.yml":
            case "commands.yml":
            case "condition.yml":
            case "location.yml":
            case "blocks.yml":
            case "particles.yml":
            case "sounds.yml":
            case "action_bars.yml":
            case "title_messages.yml":
            case "data.yml":
                if (configMap.get(fileName) == null)
                    getConfigData(filePath, fileName);
                break;
            default:
                break;
        }
        file = new File(filePath, fileName);
        return getPath(fileName, file, false);
    }

    private static void getConfigData(File filePath, String fileName) {
        File file = new File(filePath, fileName);
        if (!(file).exists()) {
            try {
                CorePlus.getInstance().saveResource(fileName, false);
            } catch (Exception ex) {
                UtilsHandler.getMsg().sendErrorMsg(getPluginName(), "Cannot save " + fileName + " to disk!");
                return;
            }
        }
        getPath(fileName, file, true);
    }

    private static YamlConfiguration getPath(String fileName, File file, boolean saveData) {
        switch (fileName) {
            case "config.yml":
            case "spigot.yml":
            case "groups.yml":
            case "commands.yml":
            case "condition.yml":
            case "location.yml":
            case "blocks.yml":
            case "particles.yml":
            case "sounds.yml":
            case "action_bars.yml":
            case "title_messages.yml":
            case "data.yml":
                if (saveData)
                    configMap.put(fileName, YamlConfiguration.loadConfiguration(file));
                return configMap.get(fileName);
        }
        return null;
    }

    private static void genConfigFile(String fileName) {
        String[] fileNameSlit = fileName.split("\\.(?=[^.]+$)");
        int ver = 0;
        File filePath = CorePlus.getInstance().getDataFolder();
        switch (fileName) {
            case "config.yml":
            case "groups.yml":
            case "commands.yml":
            case "logs.yml":
            case "condition.yml":
            case "location.yml":
            case "blocks.yml":
            case "sounds.yml":
            case "particles.yml":
            case "action_bars.yml":
            case "title_messages.yml":
            case "data.yml":
                ver = 1;
                break;
        }
        getConfigData(filePath, fileName);
        File file = new File(filePath, fileName);
        if (file.exists() && getConfig(fileName).getInt("Config-Version") != ver) {
            if (CorePlus.getInstance().getResource(fileName) != null) {
                File newFile = new File(filePath, fileNameSlit[0] + " " + LocalDateTime.now().format(
                        DateTimeFormatter.ofPattern("yyyy-MM-dd HH-mm-ss"))
                        + "." + fileNameSlit[0]);
                if (!newFile.exists()) {
                    file.renameTo(newFile);
                    File configFile = new File(filePath, fileName);
                    configFile.delete();
                    getConfigData(filePath, fileName);
                    UtilsHandler.getMsg().sendConsoleMsg(getPrefix(), "&4The file \"" + fileName + "\" is out of date, generating a new one!");
                }
            }
        }
        getConfig(fileName).options().copyDefaults(false);
    }

    private static void setConfigPath(ConfigPath configPaths) {
        configPath = configPaths;
    }

    public static ConfigPath getConfigPath() {
        return configPath;
    }


    public static String getPluginName() {
        return CorePlus.getInstance().getDescription().getName();
    }

    public static String getPluginPrefix() {
        return "[" + CorePlus.getInstance().getDescription().getName() + "] ";
    }

    public static String getPrefix() {
        return getConfig("config.yml").getString("Message.prefix");
    }

    public static boolean isDebug() {
        return ConfigHandler.getConfig("config.yml").getBoolean("Debugging");
    }
}