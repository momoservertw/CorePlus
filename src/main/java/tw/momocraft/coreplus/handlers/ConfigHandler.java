package tw.momocraft.coreplus.handlers;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import tw.momocraft.coreplus.CorePlus;
import tw.momocraft.coreplus.utils.*;
import tw.momocraft.coreplus.utils.language.VanillaUtils;

import java.io.File;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ConfigHandler {
    private static YamlConfiguration configYAML;
    private static YamlConfiguration spigotYAML;
    private static YamlConfiguration groupsYAML;
    private static ConfigPath configPaths;
    private static VanillaUtils vanillaUtils;

    public static void generateData(boolean reload) {
        genConfigFile("config.yml");
        genConfigFile("groups.yml");
        setConfigPath(new ConfigPath());
        UtilsHandler.setUp();
        setVanillaUtils(new VanillaUtils());
        if (!reload) {
            UtilsHandler.getUpdate().check(getPlugin(), Bukkit.getConsoleSender(),
                    CorePlus.getInstance().getDescription().getName(),
                    CorePlus.getInstance().getDescription().getVersion(), true);
        }
    }

    public static FileConfiguration getConfig(String fileName) {
        File filePath = CorePlus.getInstance().getDataFolder();
        File file;
        switch (fileName) {
            case "config.yml":
                filePath = Bukkit.getWorldContainer();
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
            } catch (Exception e) {
                UtilsHandler.getLang().sendErrorMsg(ConfigHandler.getPrefix(), "&cCannot save " + fileName + " to disk!");
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

    private static void setConfigPath(ConfigPath configPath) {
        configPaths = configPath;
    }

    public static ConfigPath getConfigPath() {
        return configPaths;
    }


    private static void setVanillaUtils(VanillaUtils vanillaUtil) {
        vanillaUtils = vanillaUtil;
    }

    public static VanillaUtils getVanillaUtils() {
        return vanillaUtils;
    }


    public static String getPlugin() {
        return "[" + CorePlus.getInstance().getDescription().getName() + "] ";
    }

    public static String getPrefix() {
        return getConfig("config.yml").getString("Message.prefix");
    }

    public static String getPluginName() {
        return CorePlus.getInstance().getDescription().getName();
    }

    public static boolean isDebugging() {
        return ConfigHandler.getConfig("config.yml").getBoolean("Debugging");
    }
}