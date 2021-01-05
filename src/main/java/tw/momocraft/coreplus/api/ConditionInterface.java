package tw.momocraft.coreplus.api;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import tw.momocraft.coreplus.utils.conditions.BlocksMap;
import tw.momocraft.coreplus.utils.conditions.LocationMap;

import java.util.List;
import java.util.Map;

public interface ConditionInterface {

    /**
     * Getting the setting of LocationMap in CorePlus config.yml general.
     *
     * @return the property to LocationMaps.
     */
    Map<String, LocationMap> getLocProp();

    /**
     * Getting the setting of BlocksMap in CorePlus config.yml general.
     *
     * @return the property to BlocksMaps.
     */
    Map<String, BlocksMap> getBlocksProp();

    /**
     * Checking a location.
     *
     * @param loc     the checking location.
     * @param locList the property of Location settings.
     * @param def     the return value when the locMaps is empty or null.
     * @return if match the Location.
     */
    boolean checkLocation(Location loc, List<String> locList, boolean def);

    /**
     * Checking the location is nearby certain blocks.
     *
     * @param loc        the checking location.
     * @param blocksList the property of Blocks settings.
     * @param def        the return value when the blockMaps is empty or null.
     * @return nearby certain blocks.
     */
    boolean checkBlocks(Location loc, List<String> blocksList, boolean def);

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

    /**
     * Checking residence has a flag or not.
     *
     * @param loc  the checking location.
     * @param flag the permission flag of residence.
     * @param def  the return value when the residence doesn't have that flag.
     * @return if player has residence permission.
     */
    boolean checkFlag(Location loc, String flag, boolean def);

    /**
     * Checking residence has a flag or not.
     *
     * @param loc   the checking location.
     * @param flag  the permission flag of residence.
     * @param def   the return value when the residence doesn't have that flag.
     * @param check return true if doesn't need to check.
     * @return if player has residence permission.
     */
    boolean checkFlag(Location loc, String flag, boolean def, boolean check);

    /**
     * Checking a location is in residence or not.
     *
     * @param loc the checking location.
     * @return if the location is in residence or not.
     */
    boolean isInResidence(Location loc);
}
