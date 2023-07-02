package tw.momocraft.coreplus.handlers;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import tw.momocraft.coreplus.CorePlus;
import tw.momocraft.coreplus.utils.ConfigPath;
import tw.momocraft.coreplus.utils.file.maps.FileMap;

import java.io.File;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

public class ConfigHandler {

    private static final Map<String, YamlConfiguration> configMap = new HashMap<>();
    private static final Map<String, FileMap> configInfoMap = new HashMap<>();

    private static ConfigPath configPath;

    public static void generateData(boolean reload) {
        // Config
        setConfigFile();
        loadConfig("action_bars.yml");
        loadConfig("blocks.yml");
        loadConfig("commands.yml");
        loadConfig("condition.yml");
        loadConfig("config.yml");
        loadConfig("data.yml");
        loadConfig("groups.yml");
        loadConfig("location.yml");
        loadConfig("message.yml");
        loadConfig("particles.yml");
        loadConfig("sounds.yml");
        loadConfig("title_messages.yml");

        checkConfigVer("action_bars.yml");
        checkConfigVer("blocks.yml");
        checkConfigVer("commands.yml");
        checkConfigVer("condition.yml");
        checkConfigVer("config.yml");
        checkConfigVer("data.yml");
        checkConfigVer("groups.yml");
        checkConfigVer("location.yml");
        checkConfigVer("message.yml");
        checkConfigVer("particles.yml");
        checkConfigVer("sounds.yml");
        checkConfigVer("title_messages.yml");

        // Others
        UtilsHandler.setupFirst(reload);
        setConfigPath(new ConfigPath());
        configPath.setupFirst();
        UtilsHandler.setupLast(reload);
        configPath.setupLast();

        logConfigMsg();
    }

    private static void logConfigMsg() {
        UtilsHandler.getMsg().sendConsoleMsg(
                getPluginPrefix() + "Load configurations: " + configMap.keySet());
    }

    /*
    action_bars.yml, blocks.yml, commands.yml, conditions.yml, config.yml,
    data.yml, groups.yml, location.yml, message.yml, particles.yml,
    sounds.yml, title_messages.yml
     */
    private static void setConfigFile() {
        FileMap fileMap;
        String filePath;
        String fileName;
        filePath = CorePlus.getInstance().getDataFolder().getPath();

        // action_bars.yml
        fileMap = new FileMap();
        fileName = "action_bars.yml";
        fileMap.setFile(new File(filePath, fileName));
        fileMap.setFileName(fileName);
        fileMap.setFileType("yaml");
        fileMap.setVersion(1);
        configInfoMap.put(fileName, fileMap);

        // blocks.yml
        fileMap = new FileMap();
        fileName = "blocks.yml";
        fileMap.setFile(new File(filePath, fileName));
        fileMap.setFileName(fileName);
        fileMap.setFileType("yaml");
        fileMap.setVersion(1);
        configInfoMap.put(fileName, fileMap);

        // commands.yml
        fileMap = new FileMap();
        fileName = "commands.yml";
        fileMap.setFile(new File(filePath, fileName));
        fileMap.setFileName(fileName);
        fileMap.setFileType("yaml");
        fileMap.setVersion(1);
        configInfoMap.put(fileName, fileMap);

        // condition.yml
        fileMap = new FileMap();
        fileName = "condition.yml";
        fileMap.setFile(new File(filePath, fileName));
        fileMap.setFileName(fileName);
        fileMap.setFileType("yaml");
        fileMap.setVersion(1);
        configInfoMap.put(fileName, fileMap);

        // config.yml
        fileMap = new FileMap();
        fileName = "config.yml";
        fileMap.setFile(new File(filePath, fileName));
        fileMap.setFileName(fileName);
        fileMap.setFileType("yaml");
        fileMap.setVersion(1);
        configInfoMap.put(fileName, fileMap);

        // data.yml
        fileMap = new FileMap();
        fileName = "data.yml";
        fileMap.setFile(new File(filePath, fileName));
        fileMap.setFileName(fileName);
        fileMap.setFileType("yaml");
        fileMap.setVersion(1);
        configInfoMap.put(fileName, fileMap);

        // groups.yml
        fileMap = new FileMap();
        fileName = "groups.yml";
        fileMap.setFile(new File(filePath, fileName));
        fileMap.setFileName(fileName);
        fileMap.setFileType("yaml");
        fileMap.setVersion(1);
        configInfoMap.put(fileName, fileMap);

        // location.yml
        fileMap = new FileMap();
        fileName = "location.yml";
        fileMap.setFile(new File(filePath, fileName));
        fileMap.setFileName(fileName);
        fileMap.setFileType("yaml");
        fileMap.setVersion(1);
        configInfoMap.put(fileName, fileMap);

        // message.yml
        fileMap = new FileMap();
        fileName = "message.yml";
        fileMap.setFile(new File(filePath, fileName));
        fileMap.setFileName(fileName);
        fileMap.setFileType("yaml");
        fileMap.setVersion(1);
        configInfoMap.put(fileName, fileMap);

        // particles.yml
        fileMap = new FileMap();
        fileName = "particles.yml";
        fileMap.setFile(new File(filePath, fileName));
        fileMap.setFileName(fileName);
        fileMap.setFileType("yaml");
        fileMap.setVersion(1);
        configInfoMap.put(fileName, fileMap);

        // sounds.yml
        fileMap = new FileMap();
        fileName = "sounds.yml";
        fileMap.setFile(new File(filePath, fileName));
        fileMap.setFileName(fileName);
        fileMap.setFileType("yaml");
        fileMap.setVersion(1);
        configInfoMap.put(fileName, fileMap);

        // title_messages.yml
        fileMap = new FileMap();
        fileName = "title_messages.yml";
        fileMap.setFile(new File(filePath, fileName));
        fileMap.setFileName(fileName);
        fileMap.setFileType("yaml");
        fileMap.setVersion(1);
        configInfoMap.put(fileName, fileMap);
    }

    private static void loadConfig(String fileName) {
        File file = configInfoMap.get(fileName).getFile();
        checkResource(file, fileName);
        configMap.put(fileName, YamlConfiguration.loadConfiguration(file));
    }

    private static void checkResource(File file, String resource) {
        if (!(file).exists()) {
            try {
                CorePlus.getInstance().saveResource(resource, false);
            } catch (Exception e) {
                UtilsHandler.getMsg().sendErrorMsg(getPluginName(),
                        "Cannot save " + resource + " to disk!");
            }
        }
    }

    public static FileConfiguration getConfig(String fileName) {
        if (configMap.get(fileName) == null)
            loadConfig(fileName);
        return configMap.get(fileName);
    }

    private static void checkConfigVer(String fileName) {
        String[] fileNameSlit = fileName.split("\\.(?=[^.]+$)");
        FileMap fileMap = configInfoMap.get(fileName);
        String filePath = fileMap.getFilePath();
        int version = fileMap.getVersion();

        loadConfig(fileName);
        File file = new File(filePath, fileName);
        if (file.exists() && getConfig(fileName).getInt("Config-Version") != version) {
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
                    loadConfig(fileName);
                    UtilsHandler.getMsg().sendConsoleMsg(getPrefix(),
                            "&4The file \"" + fileName + "\" is out of date, generating a new one!");
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
        return getConfig("message.yml").getString("Message.prefix");
    }

    public static boolean isDebug() {
        return ConfigHandler.getConfig("config.yml").getBoolean("Debugging");
    }

    public static boolean isCheckUpdates() {
        return ConfigHandler.getConfig("config.yml").getBoolean("Check-Updates");
    }
}