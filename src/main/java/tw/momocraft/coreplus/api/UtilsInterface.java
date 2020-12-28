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
     * @return if a value is not in the ignore list and contains in the list.
     */
    boolean containIgnoreValue(String value, List<String> list, List<String> ignoreList);

    /**
     * Checking the location of block is liquid.
     *
     * @param block the checking location of block.
     * @return if the location is liquid like water or lava.
     */
    boolean isLiquid(Block block);

    /**
     * Checking the world time is day or not.
     *
     * @param time the time of world.
     * @return the world time is day or not
     */
    boolean isDay(double time);

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
     * @param loc      location.
     * @param loc2     location2.
     * @param distance The checking value.
     * @return if two locations is in the distance.
     */
    boolean inTheRange(Location loc, Location loc2, int distance);

    String getNearbyPlayer(Player player, int range);

    String translateLayout(String input, Player player);

    <K, V extends Comparable<? super V>> Map<K, V> sortByValue(Map<K, V> map);

    <K, V extends Comparable<? super V>> Map<K, V> sortByValueLow(Map<K, V> map);

    String translateColorCode(String input);

    String getSkullValue(ItemStack itemStack);

    boolean isMenuNode(String node);

    boolean isMenu(ItemStack itemStack);

    boolean isCustomItem(ItemStack itemStack);
}
