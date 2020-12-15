package tw.momocraft.coreplus.handlers;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import tw.momocraft.coreplus.CorePlus;
import tw.momocraft.coreplus.utils.*;

import java.io.File;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ConfigHandler {
    private static YamlConfiguration configYAML;
    private static YamlConfiguration spigotYAML;
    private static Depend depends;
    private static Language lang;
    private static Permissions perm;
    private static ConfigPath configPaths;
    private static Logger log;
    private static Zipper zip;
    private static Updater update;

    public static void generateData(boolean reload) {
        genConfigFile("config.yml");
        setLang(new Language());
        setDepends(new Depend());
        setPerm(new Permissions());
        setConfigPath(new ConfigPath());
        setLogger(new Logger());
        setZipper(new Zipper());
        setUpdater(new Updater());
        if (!reload) {
            getUpdater().check(ConfigHandler.getPrefix(), Bukkit.getConsoleSender(), CorePlus.getInstance().getDescription().getName(), CorePlus.getInstance().getDescription().getVersion());
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
                ConfigHandler.getLang().sendErrorMsg(ConfigHandler.getPrefix(), "&cCannot save " + fileName + " to disk!");
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
        }
        getConfigData(filePath, fileName);
        File file = new File(filePath, fileName);
        if (file.exists() && getConfig(fileName).getInt("Config-Version") != ver) {
            if (CorePlus.getInstance().getResource(fileName) != null) {
                LocalDateTime currentDate = LocalDateTime.now();
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH-mm-ss");
                String currentTime = currentDate.format(formatter);
                String newGen = fileNameSlit[0] + " " + currentTime + "." + fileNameSlit[0];
                File newFile = new File(filePath, newGen);
                if (!newFile.exists()) {
                    file.renameTo(newFile);
                    File configFile = new File(filePath, fileName);
                    configFile.delete();
                    getConfigData(filePath, fileName);
                    ConfigHandler.getLang().sendConsoleMsg(getPrefix(), "&4The file \"" + fileName + "\" is out of date, generating a new one!");
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

    public static Language getLang() {
        return lang;
    }

    private static void setLang(Language language) {
        lang = language;
    }

    public static Depend getDepends() {
        return depends;
    }

    private static void setDepends(Depend depend) {
        depends = depend;
    }

    public static Permissions getPerm() {
        return perm;
    }

    private static void setPerm(Permissions permissions) {
        perm = permissions;
    }

    public static Logger getLogger() {
        return log;
    }

    private static void setLogger(Logger logger) {
        log = logger;
    }

    public static Zipper getZip() {
        return zip;
    }

    private static void setZipper(Zipper ziper) {
        zip = ziper;
    }

    public static Updater getUpdater() {
        return update;
    }

    private static void setUpdater(Updater updater) {
        update = updater;
    }


    public static String getPrefix() {
        return getConfig("config.yml").getString("Message.prefix");
    }

    public static boolean isDebugging() {
        return ConfigHandler.getConfig("config.yml").getBoolean("Debugging");
    }
}