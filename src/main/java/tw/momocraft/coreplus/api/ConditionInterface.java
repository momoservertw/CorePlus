package tw.momocraft.coreplus.api;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import tw.momocraft.coreplus.utils.conditions.BlocksMap;
import tw.momocraft.coreplus.utils.conditions.LocationMap;

import java.util.List;

public interface ConditionInterface {

    /**
     * Creating a list of LocationMap.
     *
     * @param list the block group list.
     * @return LocationMap list.
     */
    List<LocationMap> getSpeLocMaps(List<String> list);

    /**
     * Checking a location.
     *
     * @param loc     the checking location.
     * @param locMaps the property of Location settings.
     * @param def     the return value when the locMaps is empty or null.
     * @return if match the Location.
     */
    boolean checkLocation(Location loc, List<LocationMap> locMaps, boolean def);

    /**
     * Creatiing a list of BlocksMap.
     *
     * @param list the block group list.
     * @return BlocksMap list.
     */
    List<BlocksMap> getSpeBlocksMaps(List<String> list);

    /**
     * Checking the location is nearby certain blocks.
     *
     * @param loc        the checking location.
     * @param blocksMaps the property of Blocks settings.
     * @param def        the return value when the blockMaps is empty or null.
     * @return nearby certain blocks.
     */
    boolean checkBlocks(Location loc, List<BlocksMap> blocksMaps, boolean def);

    /**
     * Checking player has the permission of residence or not.
     *
     * @param player the target player.
     * @param loc    the checking location.
     * @param flag   the permission flag of residence.
     * @param def    the return value when the residence doesn't have that flag.
     * @return if player has residence permission.
     */
    boolean checkFlag(Player player, Location loc, String flag, boolean def);

    /**
     * Checking player has the permission of residence or not.
     *
     * @param player the target player.
     * @param loc    the checking location.
     * @param flag   the permission flag of residence.
     * @param def    the return value when the residence doesn't have that flag.
     * @param check  return true if doesn't need to check.
     * @return if player has residence permission.
     */
    boolean checkFlag(Player player, Location loc, String flag, boolean def, boolean check);
}
