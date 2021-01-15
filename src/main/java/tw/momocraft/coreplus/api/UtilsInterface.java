package tw.momocraft.coreplus.api;

import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.*;

public interface UtilsInterface {

    /**
     * Checking if a string contains another string.
     *
     * @param string1 the main string.
     * @param string2 the checking string.
     * @return if a string contains another string.
     */
    boolean containsIgnoreCase(String string1, String string2);

    /**
     * Checking if the string is a number.
     *
     * @param s the checking string.
     * @return if the string is a number.
     */
    boolean isInt(String s);

    /**
     * Getting a random number from the low number to higher number.
     *
     * @param lower  the lower number.
     * @param higher the higher number.
     * @return a random number from the low number to higher number.
     */
    int getRandom(int lower, int higher);

    /**
     * Getting a random string from a list.
     *
     * @param list the list to select.
     * @return a random string from a list.
     */
    String getRandomString(List<String> list);

    /**
     * Checking the change is succeed or not.
     *
     * @param value the checking value
     * @return if the chance is succeed or not.
     */
    boolean isRandChance(double value);


    /**
     * Checking a value is not in the ignore list and contains in the list.
     *
     * @param value      the checking value.
     * @param list       the success list.
     * @param ignoreList the ignore list.
     * @param def        the default value if the list is null or empty.
     * @return if a value is not in the ignore list and contains in the list.
     */
    boolean containIgnoreValue(String value, List<String> list, List<String> ignoreList, boolean def);

    /**
     * Checking a value is not in the ignore list and contains in the list.
     *
     * @param value      the checking value.
     * @param list       the success list.
     * @param ignoreList the ignore list.
     * @return if a value is not in the ignore list and contains in the list.
     */
    boolean containIgnoreValue(String value, List<String> list, List<String> ignoreList);

    /**
     * Checking a value is not in the ignore list and contains in the list.
     *
     * @param value the checking value.
     * @param list  the success list.
     * @param def   the default value if the list is null or empty.
     * @return if a value is not in the ignore list and contains in the list.
     */
    boolean containIgnoreValue(String value, List<String> list, boolean def);

    /**
     * Checking a value is not in the ignore list and contains in the list.
     *
     * @param value the checking value.
     * @param list  the success list.
     * @return if a value is not in the ignore list and contains in the list.
     */
    boolean containIgnoreValue(String value, List<String> list);

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
     * Comparing two numbers.
     *
     * @param operator the comparison operator to compare two numbers.
     * @param number1  first number.
     * @param number2  second number.
     * @return if the comparing is succeed or not.
     */
    boolean getCompare(String operator, double number1, double number2);

    /**
     * Checking a number is inside the range.
     *
     * @param number the checking number
     * @param r1     the first number of range.
     * @param r2     the second number of range.
     * @param equal  the return value if the number is equal the range.
     * @return if the check number is inside the range.
     */
    boolean getRange(double number, double r1, double r2, boolean equal);

    /**
     * Checking a number is inside the range.
     *
     * @param number the checking number
     * @param r1     the first number of range.
     * @param r2     the second number of range.
     * @param equal  the return value if the number is equal the range.
     * @return if the check number is inside the range.
     */
    boolean getRange(int number, int r1, int r2, boolean equal);

    /**
     * Checking a number is inside the range.
     *
     * @param number the checking number
     * @param r      the side of range.
     * @param equal  the return value if the number is equal the range.
     * @return if the check number is inside the range.
     */
    boolean getRange(int number, int r, boolean equal);

    /**
     * Checking a number is inside the range.
     *
     * @param number the checking number
     * @param r      the side of range.
     * @param equal  the return value if the number is equal the range.
     * @return if the check number is inside the range.
     */
    boolean getRange(double number, double r, boolean equal);

    /**
     *
     * @param loc             location.
     * @param loc2            location2.
     * @return get the two location's distance.
     */
    double getDistanceXZ(Location loc, Location loc2);

    /**
     *
     * @param loc             location.
     * @param loc2            location2.
     * @return get the two location's distance.
     */
    double inTheRangeXZY(Location loc, Location loc2);

    /**
     * @param loc             location.
     * @param loc2            location2.
     * @param distanceSquared The checking squared value.
     * @return if two locations's squared distance is in the range.
     */
    boolean inTheRangeXZY(Location loc, Location loc2, int distanceSquared);

    /**
     * @param loc             location.
     * @param loc2            location2.
     * @param distanceSquared The checking squared value.
     * @return if two locations's squared distance is in the range.
     */
    boolean inTheRangeXZ(Location loc, Location loc2, int distanceSquared);

    /**
     *
     * @param loc the checking location.
     * @param rangeSquared the checking range.
     * @return the player list nearby.
     */
    List<Player> getNearbyPlayersXZY(Location loc, int rangeSquared);

    /**
     *
     * @param loc the checking location.
     * @param rangeSquared the checking range.
     * @return the player list nearby.
     */
    List<Player> getNearbyPlayersXZ(Location loc, int rangeSquared);

    /**
     * Sorting a map from high to low by the values.
     *
     * @param map the input map.
     * @param <K> the input key.
     * @param <V> the input value.
     * @return new sorted map.
     */
    <K, V extends Comparable<? super V>> Map<K, V> sortByValue(Map<K, V> map);

    /**
     * Sorting a map from low to high by the values.
     *
     * @param map the input map.
     * @param <K> the input key.
     * @param <V> the input value.
     * @return new sorted map.
     */
    <K, V extends Comparable<? super V>> Map<K, V> sortByValueLow(Map<K, V> map);

    /**
     * Translating the color code "&".
     *
     * @param input the input string.
     * @return the new translated string.
     */
    String translateColorCode(String input);

    /**
     * Getting a skull texture value from a skull item.
     *
     * @param itemStack the item of skull.
     * @return the texture value of skull. Returns null if the item isn't skull.
     */
    String getSkullValue(ItemStack itemStack);

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
}
