package tw.momocraft.coreplus.utils.files;

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
        loadGroup(ConfigHandler.getPluginName(), "server");

        sendLoadedMsg();
    }

    private void sendLoadedMsg() {
        UtilsHandler.getLang().sendConsoleMsg(ConfigHandler.getPluginPrefix(),
                "&fLoaded Properties files: " + propertiesMap.keySet().toString());
    }

    private boolean loadGroup(String pluginName, String group) {
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
                return false;
        }
        return load(pluginName, group, filePath);
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
            UtilsHandler.getLang().sendErrorMsg(pluginName, "Cannot load the properties file: " + group);
            UtilsHandler.getLang().sendDebugTrace(true, pluginName, ex);
            return false;
        }
    }

    public String getValue(String pluginName, String group, String input) {
        try {
            if (propertiesMap.get(group) == null) {
                loadGroup(pluginName, group);
            }
            return propertiesMap.get(group).getProperty(input);
        } catch (Exception ex) {
            return null;
        }
    }
}
