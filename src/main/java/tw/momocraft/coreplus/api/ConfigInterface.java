package tw.momocraft.coreplus.api;

import org.bukkit.configuration.file.FileConfiguration;
import tw.momocraft.coreplus.utils.conditions.BlocksMap;
import tw.momocraft.coreplus.utils.conditions.LocationMap;

import java.util.List;
import java.util.Map;

public interface ConfigInterface {

    boolean isMySQL();

    /**
     * Adding custom group to the list and checking the type is exist.
     *
     * @param prefix the executing plugin prefix.
     * @param list   the checking list.
     * @param type   the type of the list like Entity or Material.
     * @return a new list without custom group.
     */
    List<String> getTypeList(String prefix, List<String> list, String type);

    /**
     * Getting the configuration of the file.
     * Avail: config.yml, spigot.yml, group.yml
     *
     * @param fileName the file name.
     * @return the FileConfiguration of this file.
     */
    FileConfiguration getConfig(String fileName);
}
