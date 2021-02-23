package tw.momocraft.coreplus.api;

import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.List;
import java.util.Set;

public interface ConditionInterface {

    /**
     * Checking a location.
     *
     * @param pluginName the sending plugin name.
     * @param loc        the checking location.
     * @param locList    the property of Location settings.
     * @param def        the return value when the locMaps is empty or null.
     * @return if match the Location.
     */
    boolean checkLocation(String pluginName, Location loc, List<String> locList, boolean def);

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
     * Register the residence flag.
     *
     * @param flag the name of flag.
     */
    void registerFlag(String flag);

    /**
     * Getting the registered residence flags.
     *
     * @return the registered residence flags.
     */
    Set<String> getRegisteredFlags();

    /**
     * Checking if the residence flag registered.
     *
     * @param flag the name of flag.
     * @return if the residence flag registered.
     */
    boolean isRegisteredFlag(String flag);

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
     * Checking the location is in residence or not.
     *
     * @param loc the checking location.
     * @return if the location is in residence or not.
     */
    boolean isInResidence(Location loc);

    /**
     * Getting the location is residence name.
     *
     * @param loc the checking location.
     * @return the location is residence name.
     */
    String getResidenceName(Location loc);

    /**
     * To get the ItemStack of menu which specified for a player.
     *
     * @param player the specify item for that player.
     * @return the specify item.
     */
    ItemStack getMenuItemStack(Player player);

    /**
     * To get the ItemStack which specified for a player.
     *
     * @param player the specify item for that player.
     * @param node   the ItemJoin's custom item node.
     * @return the specify item.
     */
    ItemStack getItemJoinItemStack(Player player, String node);

    /**
     * To get the item node form item itemStack.
     * Return null is the itemStack are not ItemJoin's item.
     *
     * @param itemStack the itemStack to get node.
     * @return the item node form item itemStack.
     */
    String getItemNode(ItemStack itemStack);

    /**
     * Getting all nodes of ItemJoin items.
     *
     * @return all nodes of ItemJoin items.
     */
    Set<String> getItemNodes();

    /**
     * Checking a ItemJoin's custom item node is menu or not.
     * Needed to set the menu node in config.yml.
     *
     * @param node the node of custom item.
     * @return if the custom item is menu.
     */
    boolean isMenuNode(String node);

    /**
     * Checking a item is menu or not.
     *
     * @param itemStack the checking item.
     * @return the item is menu or not.
     */
    boolean isMenu(ItemStack itemStack);

    /**
     * Checking a item is a ItemJoin's custom item or not.
     *
     * @param itemStack the checking item.
     * @return the item is a custom item or not.
     */
    boolean isCustomItem(ItemStack itemStack);

    /**
     * Getting a skull texture value from a skull item.
     *
     * @param itemStack the item of skull.
     * @return the texture value of skull. Returns null if the item isn't skull.
     */
    String getSkullValue(ItemStack itemStack);

    /**
     * Checking the skull itemStack is match the textures.
     *
     * @param itemStack the checking itemStack.
     * @param textures  the checking textures.
     * @return the skull itemStack is match the textures.
     */
    boolean isSkullTextures(ItemStack itemStack, String textures);

    /**
     * To check if the block type can be use like door, craft table...
     *
     * @param blockType the checking block type.
     * @return if the block type can be use like door, craft table...
     */
    boolean isCanUse(String blockType);

    /**
     * To check if the block type is a container like chest...
     *
     * @param blockType the checking block type.
     * @return if the block type is a container like chest...
     */
    boolean isContainer(String blockType);


    /**
     * Checking the location of block is liquid.
     *
     * @param block the checking location of block.
     * @return if the location is liquid like water or lava.
     */
    boolean isLiquid(Block block);

    /**
     * Checking the location of block is liquid and match the expect value.
     *
     * @param block the checking location of block.
     * @param value the expect value.
     * @param def   the default return value if the value is null.
     * @return if the location is liquid like water or lava.
     */
    boolean isLiquid(Block block, String value, boolean def);

    /**
     * Checking the world time is day or not.
     *
     * @param time the time of world.
     * @return the world time is day or not
     */
    boolean isDay(double time);

    /**
     * Checking the world time is day or not and match the expect value.
     *
     * @param time  the time of world.
     * @param value the expect value.
     * @param def   the default return value if the value is null.
     * @return the world time is day or not
     */
    boolean isDay(double time, String value, boolean def);

    /**
     * Checking the location is in cave or not.
     * Searching radius: 3 blocks
     *
     * @param loc the checking location.
     * @return the location is in cave or not.
     */
    boolean isInCave(Location loc);
}
