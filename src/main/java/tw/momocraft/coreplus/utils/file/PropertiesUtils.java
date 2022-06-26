package tw.momocraft.coreplus.utils.file;

import com.google.common.io.Files;
import org.bukkit.Bukkit;
import tw.momocraft.coreplus.handlers.ConfigHandler;
import tw.momocraft.coreplus.handlers.UtilsHandler;

import java.io.File;
import java.io.InputStream;
import java.util.*;

public class PropertiesUtils {

    private final Map<String, Properties> fileMap = new HashMap<>();
    private final List<String> dataList = new ArrayList<>();

    public Map<String, Properties> getDataMap() {
        return fileMap;
    }

    public Map<String, String> getPropProp() {
        return ConfigHandler.getConfigPath().getPropProp();
    }

    public boolean isEnable() {
        return ConfigHandler.getConfigPath().isDataProp();
    }

    public void setup() {
        setupConfig();

        load(ConfigHandler.getPluginName(), "server_properties", Bukkit.getServer().getWorldContainer() + "//server.properties");
        UtilsHandler.getMsg().sendFileLoadedMsg("Properties", dataList);
    }

    public void setupConfig() {
        Map<String, String> prop = ConfigHandler.getConfigPath().getPropProp();
        for (String groupName : prop.keySet()) {
            load(ConfigHandler.getPluginName(), groupName, prop.get(groupName));
        }
    }

    public boolean load(String pluginName, String groupName, String filePath) {
        try {
            InputStream inputStream = Files.asByteSource(new File(filePath)).openStream();
            Properties properties = new Properties();
            properties.load(inputStream);
            fileMap.put(groupName, properties);
            dataList.add(groupName);
            inputStream.close();
            return true;
        } catch (Exception ex) {
            UtilsHandler.getMsg().sendErrorMsg(pluginName,
                    "Cannot load the properties file: " + groupName);
            UtilsHandler.getMsg().sendDebugTrace(pluginName, ex);
            return false;
        }
    }

    public String getValue(String pluginName, String groupName, String input) {
        try {
            return fileMap.get(groupName).getProperty(input);
        } catch (Exception ex) {
            UtilsHandler.getMsg().sendErrorMsg(pluginName,
                    "Cannot set the value for Properties file: " + groupName);
            UtilsHandler.getMsg().sendErrorMsg(pluginName,
                    "Value = " + input);
            UtilsHandler.getMsg().sendDebugTrace(ConfigHandler.getPluginName(), ex);
            return null;
        }
    }
}
