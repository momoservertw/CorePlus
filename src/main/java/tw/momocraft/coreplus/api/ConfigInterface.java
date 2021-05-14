package tw.momocraft.coreplus.api;

import org.bukkit.configuration.file.FileConfiguration;
import tw.momocraft.coreplus.utils.condition.BlocksMap;
import tw.momocraft.coreplus.utils.condition.LocationMap;

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
}
