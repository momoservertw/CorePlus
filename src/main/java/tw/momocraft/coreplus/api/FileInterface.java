package tw.momocraft.coreplus.api;

import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.util.List;

public interface FileInterface {

    /**
     * Compressing a file.
     *
     * @param file the file property.
     * @param path the new file's path.
     * @param name the new file's name.
     * @return if the compressing succeed or not.
     */
    boolean zipFiles(String pluginName, File file, String path, String name);

    /**
     * Loading a YAML file.
     *
     * @param pluginName the sending plugin name.
     * @param group      the new file's name.
     * @param filePath   the target file path.
     * @return if the load process succeed.
     */
    boolean loadYamlFile(String pluginName, String group, String filePath);

    /**
     * Loading a Json file.
     *
     * @param pluginName the sending plugin name.
     * @param group      the new file's name.
     * @param filePath   the target file path.
     * @return if the load process succeed.
     */
    boolean loadJsonFile(String pluginName, String group, String filePath);

    /**
     * Loading a Json file.
     *
     * @param pluginName the sending plugin name.
     * @param group      the new file's name.
     * @param filePath   the target file path.
     * @return if the load process succeed.
     */
    boolean loadPropertiesFile(String pluginName, String group, String filePath);

    /**
     * Getting all settings from the group file.
     *
     * @param group the file's group name.
     * @return all settings from the group file.
     */
    YamlConfiguration getYamlConfig(String group);

    /**
     * Getting the value from the group file.
     *
     * @param group the file's group name.
     * @param input the searching option.
     * @return the value from the group file.
     */
    String getYamlString(String group, String input);

    /**
     * Getting the value list from the group file.
     *
     * @param group the file's group name.
     * @param input the searching option.
     * @return the value list from the group file.
     */
    List<String> getYamlStringList(String group, String input);

    /**
     * Getting the value in a Json file.
     *
     * @param pluginName the sending plugin name.
     * @param group      the file's group name.
     * @param input      the searching option.
     * @return the value in a Json file.
     */
    String getJsonValue(String pluginName, String group, String input);

    /**
     * Getting the value in a Properties file.
     *
     * @param pluginName the sending plugin name.
     * @param group      the file's group name.
     * @param input      the searching option.
     * @return the value in a Properties file.
     */
    String getPropertiesValue(String pluginName, String group, String input);
}
