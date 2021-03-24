package tw.momocraft.coreplus.api;

import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

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
     * Checking if the string in the list matches some words.
     *
     * @param list   the target list.
     * @param string the checking string.
     * @return if the string in the list matches some words.
     */
    boolean matchString(List<String> list, String string);

    /**
     * Checking if the string in the list contains some words.
     *
     * @param list   the target list.
     * @param string the checking string.
     * @return if the string in the list contains some words.
     */
    boolean containsString(List<String> list, String string);

    /**
     * Checking if a string list contains another string with ignore case.
     *
     * @param list   the main string list.
     * @param string the checking string.
     * @return if a string list contains another string.
     */
    boolean containsIgnoreCase(List<String> list, String string);

    /**
     * Checking if a string set contains another string with ignore case.
     *
     * @param set    the main string set.
     * @param string the checking string.
     * @return if a string set contains another string.
     */
    boolean containsIgnoreCase(Set<String> set, String string);

    /**
     * Checking if the string is a number.
     *
     * @param s the checking string.
     * @return if the string is a number.
     */
    boolean isInteger(String s);

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
     * the input is match the condition.
     *
     * @param pluginName the sending plugin name.
     * @param input      the checking string.
     * @return if the input is match the condition.
     */
    boolean checkValues(String pluginName, String input);

    /**
     * the input is match the condition.
     *
     * @param pluginName the sending plugin name.
     * @param value1      the first checking value.
     * @param value2      the second checking value with operator.
     * @return if the input is match the condition.
     */
    boolean checkValues(String pluginName, String value1, String value2);

    /**
     * Comparing two numbers.
     *
     * @param operator the comparison operator to compare two numbers.
     * @param number1  first number.
     * @param number2  second number.
     * @return if the comparing is succeed or not.
     */
    boolean checkCompare(String operator, double number1, double number2);

    /**
     * Comparing two numbers or strings.
     *
     * @param pluginName the sending plugin name.
     * @param operator the comparison operator to compare two numbers.
     * @param value1   the first value.
     * @param value2   the second value.
     * @return if the comparing is succeed or not.
     */
    boolean checkCompareAndEquals(String pluginName, String operator, String value1, String value2);

    /**
     * Checking a number is inside the range.
     *
     * @param number the checking number
     * @param r1     the first number of range.
     * @param r2     the second number of range.
     * @param equal  the return value if the number is equal the range.
     * @return if the check number is inside the range.
     */
    boolean inRange(double number, double r1, double r2, boolean equal);

    /**
     * Checking a number is inside the range.
     *
     * @param number the checking number
     * @param r1     the first number of range.
     * @param r2     the second number of range.
     * @param equal  the return value if the number is equal the range.
     * @return if the check number is inside the range.
     */
    boolean inRange(int number, int r1, int r2, boolean equal);

    /**
     * Checking a number is inside the range.
     *
     * @param number the checking number
     * @param r      the side of range.
     * @param equal  the return value if the number is equal the range.
     * @return if the check number is inside the range.
     */
    boolean inRange(int number, int r, boolean equal);

    /**
     * Checking a number is inside the range.
     *
     * @param number the checking number
     * @param r      the side of range.
     * @param equal  the return value if the number is equal the range.
     * @return if the check number is inside the range.
     */
    boolean inRange(double number, double r, boolean equal);

    /**
     * Checking a number is inside the range.
     *
     * @param number the checking number
     * @param r      the side of range.
     * @return if the check number is inside the range.
     */
    boolean inRange(double number, double r);

    /**
     * Checking a number is inside the range.
     *
     * @param number the checking number
     * @param r      the side of range.
     * @return if the check number is inside the range.
     */
    boolean inRange(int number, int r);

    /**
     * @param loc  location.
     * @param loc2 location2.
     * @return get the two location's distance.
     */
    double getDistanceXZ(Location loc, Location loc2);

    /**
     * @param loc  location.
     * @param loc2 location2.
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
     * Getting the string list from object list.
     *
     * @param input the input list.
     * @param type  the type of this object list.
     * @return the string list from object list.
     */
    List<String> getStringListFromObjects(List<Object> input, String type);

    /**
     * Getting the string list from object list.
     *
     * @param input the input list.
     * @param type  the type of this object list.
     * @return the string list from object list.
     */
    String getStringFromObjects(List<Object> input, String type);

    /**
     * @param pluginName the sending plugin name.
     * @param loc        the checking location.
     * @param targetType the searching target type.
     * @param returnType the return name or type.
     * @param group      the group from the group.
     * @param range      the searching range.
     * @return the
     */
    String getStringFromNearbyType(String pluginName, Location loc, String targetType, String returnType, String group, int range);

    /**
     * Getting the string list of targets.
     *
     * @param loc        the checking location.
     * @param targetType the searching target type.
     * @param returnType the return name or type.
     * @param input      the searching target type list.
     * @param range      the searching range.
     * @return the string list of targets.
     */
    String getNearbyStringFromTypes(Location loc, String targetType, String returnType, List<String> input, int range);

    /**
     * Getting the string list of targets.
     *
     * @param loc        the checking location.
     * @param targetType the searching target type.
     * @param returnType the return name or type.
     * @param input      the searching target type list.
     * @param range      the searching range.
     * @return the string list of targets.
     */
    List<String> getNearbyStringListFromTypes(Location loc, String targetType, String returnType, List<String> input, int range);

    /**
     * @param loc          the checking location.
     * @param rangeSquared the checking range.
     * @return the player list nearby.
     */
    List<Player> getNearbyPlayersXZY(Location loc, int rangeSquared);

    /**
     * @param loc          the checking location.
     * @param rangeSquared the checking range.
     * @return the player list nearby.
     */
    List<Player> getNearbyPlayersXZ(Location loc, int rangeSquared);

    Collection<Entity> getNearbyEntities(Location loc, int x, int y, int z);

    List<Block> getNearbyBlocks(Location loc, int X, int Y, int Z);


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
     * Translating the color code "&".
     *
     * @param input the input string list.
     * @return the new translated string.
     */
    List<String> translateColorCode(List<String> input);

    /**
     * Getting the color object.
     *
     * @param input the name of color
     * @return the Color object.
     */
    Color getColor(String input);

    /**
     * Getting the extended or implemented class and super class.
     *
     * @param clazz the checking class.
     * @return the set of extended or implemented class and super class.
     */
    Set<Class<?>> getAllExtendedOrImplementedClass(Class<?> clazz);

}
