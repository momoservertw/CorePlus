package tw.momocraft.coreplus.utils.YamlUtils;

import com.google.common.io.Files;
import org.bukkit.Bukkit;
import tw.momocraft.coreplus.handlers.ConfigHandler;
import tw.momocraft.coreplus.handlers.UtilsHandler;

import java.io.File;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class PropertyUtils {

    private final Map<String, Properties> propertyMap = new HashMap<>();
    private final Map<String, InputStream> streamMap = new HashMap<>();

    public PropertyUtils() {
        loadFile(ConfigHandler.getPluginName(), "server.properties");
    }

    public void loadFile(String pluginName, String fileName) {
        File filePath;
        try {
            switch (fileName) {
                case "server.properties":
                    filePath = Bukkit.getServer().getWorldContainer();
                    break;
                    /*
                case "":
                    filePath = CorePlus.getInstance().getDataFolder();
                    break;
                     */
                default:
                    return;
            }
            InputStream inputStream = streamMap.get(fileName);
            if (inputStream == null) {
                inputStream = Files.asByteSource(new File(filePath, fileName)).openStream();
                Properties properties = new Properties();
                properties.load(inputStream);
                inputStream.close();
                streamMap.put(fileName, inputStream);
                propertyMap.put(fileName, properties);
            }
        } catch (Exception ex) {
            UtilsHandler.getLang().sendErrorMsg(pluginName, "Cannot load the property file: " + fileName);
        }
    }

    public String getValue(String pluginName, String fileName, String input) {
        try {
            if (streamMap.get(fileName) == null) {
                loadFile(pluginName, fileName);
            }
            return propertyMap.get(fileName).getProperty(input);
        } catch (Exception ex) {
            UtilsHandler.getLang().sendErrorMsg(pluginName,
                    "Cannot get the value of \"" + input + "\" in \"" + fileName + "\".");
            UtilsHandler.getLang().sendDebugTrace(true, ConfigHandler.getPluginName(), ex);
            return null;
        }
    }
}
