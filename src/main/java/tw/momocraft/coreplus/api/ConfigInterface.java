package tw.momocraft.coreplus.api;

import org.bukkit.configuration.file.FileConfiguration;
import tw.momocraft.coreplus.utils.condition.BlocksMap;
import tw.momocraft.coreplus.utils.condition.LocationMap;
import tw.momocraft.coreplus.utils.file.maps.MySQLMap;
import tw.momocraft.coreplus.utils.message.LogMap;

import java.util.List;
import java.util.Map;

public interface ConfigInterface {
    /**
     * @return if custom MySQL feature enabled.
     */
    boolean isDataMySQL();

    /**
     * @return if custom YAML files feature enabled.
     */
    boolean isDataYMAL();

    /**
     * @return if custom Json files feature enabled.
     */
    boolean isDataJson();

    /**
     * @return if custom Properties files feature enabled.
     */
    boolean isDataProp();

    /**
     * @return if custom Log files feature enabled.
     */
    boolean isDataLog();

    /**
     * @return the settings of custom MySQL.
     */
    Map<String, MySQLMap> getMySQLProp();

    /**
     * @return the settings of custom YAML files.
     */
    Map<String, String> getYAMLProp();

    /**
     * @return the settings of custom Json files.
     */
    Map<String, String> getJsonProp();

    /**
     * @return the settings of custom Properties files.
     */
    Map<String, String> getPropProp();

    /**
     * @return the settings of custom Log files.
     */
    Map<String, LogMap> getLogProp();


    /**
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
     * @param fileName the file name of config like "config.yml" and "message.yml".
     * @return the yaml configuration.
     */
    FileConfiguration getConfig(String fileName);
}
