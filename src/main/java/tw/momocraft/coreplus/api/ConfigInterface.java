package tw.momocraft.coreplus.api;

import org.bukkit.configuration.file.FileConfiguration;
import tw.momocraft.coreplus.utils.conditions.BlocksMap;
import tw.momocraft.coreplus.utils.conditions.LocationMap;

import java.util.List;
import java.util.Map;

public interface ConfigInterface {

    /**
     * @return if mySQL feature enabled.
     */
    boolean isDataMySQL();

    /**
     *
     * @return the conditions settings in condition.yml
     */
    Map<String, List<String>> getConditionProp();

    /**
     * @return to get the property of Location settings in location.yml.
     */
    Map<String, LocationMap> getLocProp();

    /**
     * @return to get the property of Blocks settings in blocks.yml.
     */
    Map<String, BlocksMap> getBlocksProp();

    /**
     *
     * @return the group settings in groups.yml.
     */
    Map<String, Map<String, List<String>>> getGroupProp();

    /**
     * Adding custom group to the list and checking the type is exist.
     *
     * @param pluginName the executing plugin name.
     * @param list       the checking list.
     * @param type       the type of the list like Entity or Material.
     * @return a new list without custom group.
     */
    List<String> getTypeList(String pluginName, List<String> list, String type);

    /**
     * Getting the configuration of the file.
     * Avail: config.yml, spigot.yml, group.yml
     *
     * @param fileName the file name.
     * @return the FileConfiguration of this file.
     */
    FileConfiguration getConfig(String fileName);

    /**
     * Getting the value from property file.
     * Avail: server.properties
     *
     * @param pluginName the sending plugin name.
     * @param fileName   the file name.
     * @param input      the checking name.
     * @return the value from property file.
     */
    String getProp(String pluginName, String fileName, String input);

    /**
     * Getting the value from json file.
     * Avail:
     *
     * @param pluginName the sending plugin name.
     * @param fileName   the file name.
     * @param input      the checking name.
     * @return the value from property file.
     */
    String getJson(String pluginName, String fileName, String input);

    /**
     * Getting the configuration from yaml file.
     * Avail: discord_message
     *
     * @param fileName   the file name.
     */
    FileConfiguration getYaml(String fileName);
}
