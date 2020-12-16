package tw.momocraft.coreplus.api;

import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

import java.util.*;

public interface UtilsInterface {

    boolean containsIgnoreCase(String string1, String string2);

    boolean isInt(String s);

    int getRandom(int lower, int upper);

    boolean isRandChance(double value);

    boolean containIgnoreValue(String value, List<String> list, List<String> ignoreList);

    boolean isLiquid(Block block, String value);

    boolean isDay(double time, String value);

    boolean getCompare(String operator, double number1, double number2);

    boolean getRange(double number, double r1, double r2);

    boolean getRange(int number, int r1, int r2);

    boolean getRange(int number, int r);

    boolean getRange(double number, double r);

    boolean inTheRange(Location loc, Location loc2, int distance);

    String getNearbyPlayer(Player player, int range);

    String translateLayout(String input, Player player);

    <K, V extends Comparable<? super V>> Map<K, V> sortByValue(Map<K, V> map);

    <K, V extends Comparable<? super V>> Map<K, V> sortByValueLow(Map<K, V> map);

    String translateColorCode(String input);
}
