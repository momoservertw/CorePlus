package tw.momocraft.coreplus.utils.files;

import org.bukkit.configuration.file.YamlConfiguration;
import tw.momocraft.coreplus.api.FileInterface;
import tw.momocraft.coreplus.handlers.UtilsHandler;

import java.io.File;
import java.util.List;

public class FileUtils implements FileInterface {

    @Override
    public boolean zipFiles(String pluginName, File file, String path, String name) {
        return UtilsHandler.getZip().zipFiles(pluginName, file, path, name);
    }

    @Override
    public boolean loadYamlFile(String pluginName, String group, String filePath) {
        return UtilsHandler.getYaml().load(group, filePath);
    }

    @Override
    public boolean loadJsonFile(String pluginName, String group, String filePath) {
        return UtilsHandler.getJson().load(group, filePath);
    }

    @Override
    public boolean loadPropertiesFile(String pluginName, String group, String filePath) {
        return UtilsHandler.getProperty().load(pluginName, group, filePath);
    }

    @Override
    public String getYamlString(String group, String input) {
        return UtilsHandler.getYaml().getString(group, input);
    }

    @Override
    public List<String> getYamlStringList(String group, String input) {
        return UtilsHandler.getYaml().getStringList(group, input);
    }

    @Override
    public YamlConfiguration getYamlConfig(String group) {
        return UtilsHandler.getYaml().getConfig(group);
    }

    @Override
    public String getJsonValue(String pluginName, String group, String input) {
        return UtilsHandler.getJson().getValue(group, input);
    }

    @Override
    public String getPropertiesValue(String pluginName, String group, String input) {
        return UtilsHandler.getProperty().getValue(pluginName, group, input);
    }
}
