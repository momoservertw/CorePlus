package tw.momocraft.coreplus.utils.file;

import com.google.common.io.Files;
import org.bukkit.Bukkit;
import tw.momocraft.coreplus.handlers.ConfigHandler;
import tw.momocraft.coreplus.handlers.UtilsHandler;

import java.io.File;
import java.io.InputStream;
import java.util.*;

public class PropertiesUtils {

    public Map<String, String> getPropProp() {
        return ConfigHandler.getConfigPath().getPropProp();
    }

    private final Map<String, Properties> fileMap = new HashMap<>();
    private final List<String> customList = new ArrayList<>();

    public PropertiesUtils() {
        loadCustom();
        loadGroup("server.properties");

        sendLoadedMsg();
    }

    public boolean load(String pluginName, String group, String filePath) {
        try {
            InputStream inputStream = Files.asByteSource(new File(filePath)).openStream();
            Properties properties = new Properties();
            properties.load(inputStream);
            fileMap.put(group, properties);
            inputStream.close();
            return true;
        } catch (Exception ex) {
            UtilsHandler.getMsg().sendErrorMsg(pluginName,
                    "Cannot load the properties file: " + group);
            UtilsHandler.getMsg().sendDebugTrace(true, pluginName, ex);
            return false;
        }
    }

    public String getValue(String pluginName, String group, String input) {
        try {
            return fileMap.get(group).getProperty(input);
        } catch (Exception ex) {
            UtilsHandler.getMsg().sendErrorMsg(pluginName,
                    "An error occurred while getting the value of \"" + input + "\".");
            UtilsHandler.getMsg().sendErrorMsg(pluginName,
                    "Can not find the Properties group of \"" + group + "\".");
            return null;
        }
    }

    private boolean loadGroup(String group) {
        String filePath;
        switch (group) {
            case "server.properties":
                filePath = Bukkit.getServer().getWorldContainer() + "//server.properties";
                break;
            default:
                UtilsHandler.getMsg().sendErrorMsg(ConfigHandler.getPluginName(),
                        "Cannot load the properties file: " + group);
                return false;
        }
        return load(ConfigHandler.getPluginName(), group, filePath);
    }

    private void loadCustom() {
        Map<String, String> prop = ConfigHandler.getConfigPath().getPropProp();
        for (String groupName : prop.keySet()) {
            load(ConfigHandler.getPluginName(), groupName, prop.get(groupName));
            customList.add(groupName);
        }
    }

    private void sendLoadedMsg() {
        if (!customList.isEmpty())
            UtilsHandler.getMsg().sendConsoleMsg(ConfigHandler.getPluginPrefix(),
                    "Loaded Properties files: " + customList);
    }
}
