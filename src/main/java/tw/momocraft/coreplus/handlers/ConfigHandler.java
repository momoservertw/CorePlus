package tw.momocraft.coreplus.handlers;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import tw.momocraft.coreplus.CorePlus;
import tw.momocraft.coreplus.utils.ConfigPath;

import java.io.File;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ConfigHandler {
    private static YamlConfiguration configYAML;
    private static YamlConfiguration spigotYAML;
    private static YamlConfiguration groupsYAML;

    private static YamlConfiguration commandsYAML;
    private static YamlConfiguration logsYAML;
    private static YamlConfiguration locationYAML;
    private static YamlConfiguration blocksYAML;
    private static YamlConfiguration particlesYAML;
    private static YamlConfiguration soundsYAML;
    private static YamlConfiguration actionBarsYAML;
    private static YamlConfiguration titleMessagesYAML;

    private static ConfigPath configPath;

    public static void generateData(boolean reload) {
        UtilsHandler.setUpFirst();
        genConfigFile("config.yml");
        genConfigFile("groups.yml");
        genConfigFile("commands.yml");
        genConfigFile("logs.yml");
        genConfigFile("location.yml");
        genConfigFile("blocks.yml");
        genConfigFile("particles.yml");
        genConfigFile("sounds.yml");
        genConfigFile("action_bars.yml");
        genConfigFile("title_messages.yml");
        setConfigPath(new ConfigPath());
        UtilsHandler.setUpLast();
        if (!reload) {
            UtilsHandler.getUpdate().check(getPluginName(), getPluginPrefix(), Bukkit.getConsoleSender(),
                    CorePlus.getInstance().getDescription().getName(),
                    CorePlus.getInstance().getDescription().getVersion(), true);
        }
    }

    public static FileConfiguration getConfig(String fileName) {
        File filePath = CorePlus.getInstance().getDataFolder();
        File file;
        switch (fileName) {
            case "config.yml":
                if (configYAML == null) {
                    getConfigData(filePath, fileName);
                }
                break;
            case "spigot.yml":
                filePath = Bukkit.getServer().getWorldContainer();
                if (spigotYAML == null) {
                    getConfigData(filePath, fileName);
                }
                break;
            case "groups.yml":
                if (groupsYAML == null) {
                    getConfigData(filePath, fileName);
                }
                break;
            case "commands.yml":
                if (commandsYAML == null) {
                    getConfigData(filePath, fileName);
                }
                break;
            case "logs.yml":
                if (logsYAML == null) {
                    getConfigData(filePath, fileName);
                }
                break;
            case "location.yml":
                if (locationYAML == null) {
                    getConfigData(filePath, fileName);
                }
                break;
            case "blocks.yml":
                if (blocksYAML == null) {
                    getConfigData(filePath, fileName);
                }
                break;
            case "particles.yml":
                if (particlesYAML == null) {
                    getConfigData(filePath, fileName);
                }
                break;
            case "sounds.yml":
                if (soundsYAML == null) {
                    getConfigData(filePath, fileName);
                }
                break;
            case "action_bars.yml":
                if (actionBarsYAML == null) {
                    getConfigData(filePath, fileName);
                }
                break;
            case "title_messages.yml":
                if (titleMessagesYAML == null) {
                    getConfigData(filePath, fileName);
                }
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
                String pluginName = "";
                if (!fileName.equals("config.yml")) {
                    pluginName = ConfigHandler.getPrefix();
                }
                UtilsHandler.getLang().sendErrorMsg(pluginName, "&cCannot save " + fileName + " to disk!");
                return;
            }
        }
        getPath(fileName, file, true);
    }

    private static YamlConfiguration getPath(String fileName, File file, boolean saveData) {
        switch (fileName) {
            case "config.yml":
                if (saveData) {
                    configYAML = YamlConfiguration.loadConfiguration(file);
                }
                return configYAML;
            case "spigot.yml":
                if (saveData) {
                    spigotYAML = YamlConfiguration.loadConfiguration(file);
                }
                return spigotYAML;
            case "groups.yml":
                if (saveData) {
                    groupsYAML = YamlConfiguration.loadConfiguration(file);
                }
                return groupsYAML;
            case "commands.yml":
                if (saveData) {
                    commandsYAML = YamlConfiguration.loadConfiguration(file);
                }
                return commandsYAML;
            case "logs.yml":
                if (saveData) {
                    logsYAML = YamlConfiguration.loadConfiguration(file);
                }
                return logsYAML;
            case "location.yml":
                if (saveData) {
                    locationYAML = YamlConfiguration.loadConfiguration(file);
                }
                return locationYAML;
            case "blocks.yml":
                if (saveData) {
                    blocksYAML = YamlConfiguration.loadConfiguration(file);
                }
                return blocksYAML;
            case "particles.yml":
                if (saveData) {
                    particlesYAML = YamlConfiguration.loadConfiguration(file);
                }
                return particlesYAML;
            case "sounds.yml":
                if (saveData) {
                    soundsYAML = YamlConfiguration.loadConfiguration(file);
                }
                return soundsYAML;
            case "action_bars.yml":
                if (saveData) {
                    actionBarsYAML = YamlConfiguration.loadConfiguration(file);
                }
                return actionBarsYAML;
            case "title_messages.yml":
                if (saveData) {
                    titleMessagesYAML = YamlConfiguration.loadConfiguration(file);
                }
                return titleMessagesYAML;
        }
        return null;
    }

    private static void genConfigFile(String fileName) {
        String[] fileNameSlit = fileName.split("\\.(?=[^\\.]+$)");
        int ver = 0;
        File filePath = CorePlus.getInstance().getDataFolder();
        switch (fileName) {
            case "config.yml":
                ver = 1;
                break;
            case "groups.yml":
            case "commands.yml":
            case "logs.yml":
            case "location.yml":
            case "blocks.yml":
            case "sounds.yml":
            case "particles.yml":
            case "action_bars.yml":
            case "title_messages.yml":
                ver = 1;
                break;
        }
        getConfigData(filePath, fileName);
        File file = new File(filePath, fileName);
        if (file.exists() && getConfig(fileName).getInt("Config-Version") != ver) {
            if (CorePlus.getInstance().getResource(fileName) != null) {
                // Creating a new file name "2020-11-20 00-00-00.yml"
                File newFile = new File(filePath, fileNameSlit[0] + " " + LocalDateTime.now().format(
                        DateTimeFormatter.ofPattern("yyyy-MM-dd HH-mm-ss"))
                        + "." + fileNameSlit[0]);
                if (!newFile.exists()) {
                    file.renameTo(newFile);
                    File configFile = new File(filePath, fileName);
                    configFile.delete();
                    getConfigData(filePath, fileName);
                    UtilsHandler.getLang().sendConsoleMsg(getPrefix(), "&4The file \"" + fileName + "\" is out of date, generating a new one!");
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

    public static boolean isDebugging() {
        return ConfigHandler.getConfig("config.yml").getBoolean("Debugging");
    }
}