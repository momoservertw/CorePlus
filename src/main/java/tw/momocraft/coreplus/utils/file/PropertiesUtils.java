package tw.momocraft.coreplus.utils.file;

import com.google.common.io.Files;
import org.bukkit.Bukkit;
import tw.momocraft.coreplus.handlers.ConfigHandler;
import tw.momocraft.coreplus.handlers.UtilsHandler;

import java.io.File;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class PropertiesUtils {

    private final Map<String, Properties> propertiesMap = new HashMap<>();

    public PropertiesUtils() {
        loadCustom();
        loadGroup("server");

        sendLoadedMsg();
    }

    private void sendLoadedMsg() {
        UtilsHandler.getMsg().sendConsoleMsg(ConfigHandler.getPluginPrefix(),
                "&fLoaded Properties files: " + propertiesMap.keySet().toString());
    }

    private boolean loadGroup(String group) {
        String filePath;
        switch (group) {
            case "server":
                filePath = Bukkit.getServer().getWorldContainer().toString() + "//server.properties";
                break;
                    /*
                case "":
                    filePath = CorePlus.getInstance().getDataFolder();
                    break;
                     */
            default:
                UtilsHandler.getMsg().sendErrorMsg(ConfigHandler.getPluginName(),
                        "Cannot load the properties file: " + group);
                return false;
        }
        return load(ConfigHandler.getPluginName(), group, filePath);
    }

    private void loadCustom() {
        Map<String, String> prop = ConfigHandler.getConfigPath().getPropProp();
        for (String groupName : prop.keySet())
            load(ConfigHandler.getPluginName(), groupName, prop.get(groupName));
    }

    public boolean load(String pluginName, String group, String filePath) {
        try {
            InputStream inputStream = Files.asByteSource(new File(filePath)).openStream();
            Properties properties = new Properties();
            properties.load(inputStream);
            propertiesMap.put(group, properties);
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
            return propertiesMap.get(group).getProperty(input);
        } catch (Exception ex) {
            UtilsHandler.getMsg().sendErrorMsg(pluginName,
                    "An error occurred while getting the value of \"" + input + "\".");
            UtilsHandler.getMsg().sendErrorMsg(pluginName,
                    "Can not find the Properties group of \"" + group + "\".");
            return null;
        }
    }
}
