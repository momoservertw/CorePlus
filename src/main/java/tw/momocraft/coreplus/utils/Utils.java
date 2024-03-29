package tw.momocraft.coreplus.utils;

import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import tw.momocraft.coreplus.api.UtilsInterface;
import tw.momocraft.coreplus.handlers.ConfigHandler;
import tw.momocraft.coreplus.handlers.UtilsHandler;

import java.util.*;

public class Utils implements UtilsInterface {

    @Override
    public Map<String, String> getColorAliasMap() {
        return ConfigHandler.getConfigPath().getColorAliasMap();
    }

    @Override
    public String getObjectType(Object object) {
        if (object instanceof Player) {
            return "player";
        } else if (object instanceof OfflinePlayer) {
            return "offlineplayer";
        } else if (object instanceof Entity) {
            return "entity";
        } else if (object instanceof EntityType) {
            return "entitytype";
        } else if (object instanceof Block) {
            return "block";
        } else if (object instanceof ItemStack) {
            return "itemstack";
        } else if (object instanceof Material) {
            return "material";
        } else if (object instanceof Location) {
            return "location";
        }
        return null;
    }

    @Override
    public boolean containsIgnoreCase(String string1, String string2) {
        return string1 != null && string2 != null && string1.toLowerCase().contains(string2.toLowerCase());
    }

    @Override
    public boolean matchesRegex(List<String> input, String regex) {
        if (regex != null && input != null)
            for (String s : input)
                if (s.matches(regex))
                    return true;
        return false;
    }

    @Override
    public String getColorCodeWithoutSymbol(String input) {
        if (input == null)
            return null;
        input = input.toLowerCase();
        if (input.matches("[12345678abcdef]"))
            return input;
        for (Map.Entry<String, String> entry : getColorAliasMap().entrySet()) {
            if (entry.getValue().equals(input))
                return entry.getKey();
        }
        return null;
    }

    @Override
    public String getColorCode(String input) {
        if (input == null)
            return null;
        input = input.toLowerCase();
        if (input.matches("[12345678abcdef]"))
            return input;
        if (input.matches("[§&][12345678abcdef]"))
            return input.substring(1);

        switch (input.toUpperCase()) {
            case "BLACK":
                return "0";
            case "DARK_BLUE":
                return "1";
            case "DARK_GREEN":
                return "2";
            case "DARK_AQUA":
                return "3";
            case "DARK_RED":
                return "4";
            case "DARK_PURPLE":
                return "5";
            case "GOLD":
                return "6";
            case "GRAY":
                return "7";
            case "DARK_GRAY":
                return "8";
            case "BLUE":
                return "9";
            case "GREEN":
                return "a";
            case "AQUA":
                return "b";
            case "RED":
                return "c";
            case "LIGHT_PURPLE":
                return "d";
            case "YELLOW":
                return "e";
            case "WHITE":
                return "f";
        }
        for (Map.Entry<String, String> entry : getColorAliasMap().entrySet()) {
            if (entry.getValue().equals(input))
                return entry.getKey();
        }
        return null;
    }

    @Override
    public boolean equalColorWithoutSymbol(String input) {
        if (input.matches("[12345678abcdef]"))
            return true;
        return getColorAliasMap().containsValue(input);
    }

    @Override
    public boolean equalColorCodeWithoutSymbol(String input) {
        if (input == null)
            return false;
        return (input.matches("[12345678abcdef]"));
    }

    @Override
    public boolean equalColorCode(String input) {
        if (input == null)
            return false;
        return (input.matches("[§&]?[12345678abcdef]"));
    }

    @Override
    public boolean containsColorCode(String input) {
        if (input == null)
            return false;
        return (input.matches(".*[§&]?[12345678abcdef].*$"));
    }

    @Override
    public String removeColorCode(String input) {
        if (input == null)
            return null;
        return input.replaceAll("[§&]?[12345678abcdef]", "");
    }

    @Override
    public boolean containsString(List<String> list, String string) {
        if (string != null && list != null) {
            for (String s : list) {
                if (s.contains(string))
                    return true;
            }
        }
        return false;
    }

    @Override
    public boolean containsIgnoreCase(List<String> list, String string) {
        if (string != null && list != null) {
            for (String s : list) {
                if (s.equalsIgnoreCase(string))
                    return true;
            }
        }
        return false;
    }

    @Override
    public boolean containsIgnoreCase(Set<String> set, String string) {
        if (string != null && set != null) {
            for (String s : set) {
                if (s.equalsIgnoreCase(string))
                    return true;
            }
        }
        return false;
    }

    @Override
    public boolean isInteger(String s) {
        try {
            Integer.parseInt(s);
        } catch (NumberFormatException e) {
            return false;
        }
        return true;
    }

    @Override
    public <K, V> Map<V, K> invertMap(Map<K, V> map) {
        Map<V, K> output = new HashMap<>();
        for (Map.Entry<K, V> entry : map.entrySet())
            output.put(entry.getValue(), entry.getKey());
        return output;
    }

    @Override
    public int getRandom(int value1, int value2) {
        Random random = new Random();
        if (value2 > value1) {
            return random.nextInt((value2 - value1) + 1) + value1;
        }
        return random.nextInt((value1 - value2) + 1) + value2;
    }

    @Override
    public String getRandomString(List<String> list) {
        if (list == null || list.isEmpty())
            return null;
        return list.get(new Random().nextInt(list.size()));
    }

    @Override
    public boolean isRandChance(double value) {
        return value > new Random().nextDouble();
    }

    @Override
    public List<String> removeIgnoreList(List<String> list, List<String> ignoreList) {
        if (list == null || ignoreList == null)
            return list;
        List<String> newList = new ArrayList<>();
        for (String s : list) {
            if (!ignoreList.contains(s))
                newList.add(s);
        }
        return newList;
    }

    @Override
    public boolean containIgnoreValue(String value, List<String> list, List<String> ignoreList, boolean def) {
        if (ignoreList != null && ignoreList.contains(value))
            return false;
        if (list == null || list.isEmpty())
            return def;
        return list.contains(value);
    }

    @Override
    public boolean containIgnoreValue(String value, List<String> list, List<String> ignoreList) {
        if (ignoreList != null && ignoreList.contains(value))
            return false;
        if (list == null || list.isEmpty())
            return true;
        return list.contains(value);
    }

    @Override
    public boolean containIgnoreValue(String value, List<String> list, boolean def) {
        if (list == null || list.isEmpty())
            return def;
        return list.contains(value);
    }

    @Override
    public boolean containIgnoreValue(String value, List<String> list) {
        if (list == null || list.isEmpty())
            return true;
        return list.contains(value);
    }

    @Override
    public boolean checkValues(String pluginName, String value1, String value2) {
        boolean opposite = false;
        if (value2.startsWith("!")) {
            opposite = true;
            value2 = value2.substring(1);
        }
        try {
            if (value2.startsWith(">")) {
                value2 = value2.substring(1);
                if (value2.startsWith("=")) {
                    value2 = value2.substring(1);
                    if (opposite) {
                        if (!checkCompareAndEquals(pluginName, ">=", value1, value2))
                            return true;
                    } else {
                        if (checkCompareAndEquals(pluginName, ">=", value1, value2))
                            return true;
                    }
                }
                if (opposite) {
                    if (!checkCompareAndEquals(pluginName, ">", value1, value2))
                        return true;
                } else {
                    if (checkCompareAndEquals(pluginName, ">", value1, value2))
                        return true;
                }
            } else if (value2.startsWith("<")) {
                value2 = value2.substring(1);
                if (value2.startsWith("=")) {
                    value2 = value2.substring(1);
                    if (opposite) {
                        if (!checkCompareAndEquals(pluginName, "<=", value1, value2))
                            return true;
                    } else {
                        if (checkCompareAndEquals(pluginName, "<=", value1, value2))
                            return true;
                    }
                }
                if (opposite) {
                    if (!checkCompareAndEquals(pluginName, "<", value1, value2))
                        return true;
                } else {
                    if (checkCompareAndEquals(pluginName, "<", value1, value2))
                        return true;
                }
            } else if (value2.startsWith("=")) {
                value2 = value2.substring(1);
                if (!value2.contains("~")) {
                    if (opposite) {
                        if (!checkCompareAndEquals(pluginName, "=", value1, value2))
                            return true;
                    } else {
                        if (checkCompareAndEquals(pluginName, "=", value1, value2))
                            return true;
                    }
                } else {
                    String[] split = value2.split("~");
                    if (opposite) {
                        if (!inRange(Double.parseDouble(value1),
                                Double.parseDouble(split[0]), Double.parseDouble(split[1]), false))
                            return true;
                    } else {
                        if (inRange(Double.parseDouble(value1),
                                Double.parseDouble(split[0]), Double.parseDouble(split[1]), false))
                            return true;
                    }
                }
            } else {
                UtilsHandler.getMsg().sendErrorMsg(pluginName, "An error occurred while checking condition: \"" + value1 + value2 + "\"");
                UtilsHandler.getMsg().sendErrorMsg(pluginName, "More information: https://github.com/momoservertw/CorePlus/wiki/Condtions");
                return false;
            }
        } catch (Exception ex) {
            UtilsHandler.getMsg().sendErrorMsg(pluginName, "An error occurred while checking condition: \"" + value1 + value2 + "\"");
            UtilsHandler.getMsg().sendErrorMsg(pluginName, "More information: https://github.com/momoservertw/CorePlus/wiki/Condtions");
            UtilsHandler.getMsg().sendDebugTrace(pluginName, ex);
        }
        return false;
    }

    @Override
    public boolean checkValues(String pluginName, String input) {
        try {
            String[] arr;
            if (input.contains(">=")) {
                arr = input.split(">=");
                if (arr[0].endsWith("!")) {
                    if (!checkCompareAndEquals(pluginName, ">=", arr[0].substring(0, arr[0].length() - 1), arr[1]))
                        return true;
                } else {
                    if (checkCompareAndEquals(pluginName, ">=", arr[0], arr[1]))
                        return true;
                }
            } else if (input.contains("<=")) {
                arr = input.split("<=");
                if (arr[0].endsWith("!")) {
                    if (!checkCompareAndEquals(pluginName, "<=", arr[0].substring(0, arr[0].length() - 1), arr[1]))
                        return true;
                } else {
                    if (checkCompareAndEquals(pluginName, "<=", arr[0], arr[1]))
                        return true;
                }
            } else if (input.contains(">")) {
                arr = input.split(">");
                if (arr[0].endsWith("!")) {
                    if (!checkCompareAndEquals(pluginName, ">", arr[0].substring(0, arr[0].length() - 1), arr[1]))
                        return true;
                } else {
                    if (checkCompareAndEquals(pluginName, ">", arr[0], arr[1]))
                        return true;
                }
            } else if (input.contains("<")) {
                arr = input.split("<");
                if (arr[0].endsWith("!")) {
                    if (!checkCompareAndEquals(pluginName, "<", arr[0].substring(0, arr[0].length() - 1), arr[1]))
                        return true;
                } else {
                    if (checkCompareAndEquals(pluginName, "<", arr[0], arr[1]))
                        return true;
                }
            } else if (input.contains("=")) {
                arr = input.split("=");
                if (!arr[1].contains("~")) {
                    if (arr[0].endsWith("!")) {
                        if (!checkCompareAndEquals(pluginName, "=", arr[0].substring(0, arr[0].length() - 1), arr[1]))
                            return true;
                    } else {
                        if (checkCompareAndEquals(pluginName, "=", arr[0], arr[1]))
                            return true;
                    }
                } else {
                    String[] split = arr[1].split("~");
                    if (arr[0].endsWith("!")) {
                        if (!inRange(Double.parseDouble(arr[0].substring(0, arr[0].length() - 1)),
                                Double.parseDouble(split[0]), Double.parseDouble(split[1]), false))
                            return true;
                    } else {
                        if (inRange(Double.parseDouble(arr[0]),
                                Double.parseDouble(split[0]), Double.parseDouble(split[1]), false))
                            return true;
                    }
                }
            } else {
                return true;
            }
        } catch (Exception ex) {
            UtilsHandler.getMsg().sendErrorMsg(pluginName, "An error occurred while checking condition: \"" + input + "\"");
            UtilsHandler.getMsg().sendErrorMsg(pluginName, "More information: https://github.com/momoservertw/CorePlus/wiki/Condtions");
            UtilsHandler.getMsg().sendDebugTrace(pluginName, ex);
        }
        return false;
    }

    @Override
    public boolean checkCompare(String operator, double number1, double number2) {
        switch (operator) {
            case ">":
                return number1 > number2;
            case "<":
                return number1 < number2;
            case ">=":
            case "=>":
                return number1 >= number2;
            case "<=":
            case "=<":
                return number1 <= number2;
            case "==":
            case "=":
                return number1 == number2;
        }
        return false;
    }

    @Override
    public boolean checkCompareAndEquals(String pluginName, String operator, String value1, String value2) {
        try {
            if (operator.matches("[><=]+")) {
                return checkCompare(operator, Double.parseDouble(value1), Double.parseDouble(value2));
            } else if (operator.matches("[!][><=]+")) {
                return !checkCompare(operator, Double.parseDouble(value1), Double.parseDouble(value2));
            }
        } catch (Exception ex) {
            if (operator.matches("[=]+")) {
                return value1.equals(value2);
            }
            UtilsHandler.getMsg().sendErrorMsg(pluginName, "An error occurred while checking condition: \"" + value1 + operator + value2 + "\"");
            UtilsHandler.getMsg().sendErrorMsg(pluginName, "More information: https://github.com/momoservertw/CorePlus/wiki/Condtions");
            UtilsHandler.getMsg().sendDebugTrace(pluginName, ex);
        }
        return false;
    }

    @Override
    public boolean inRange(double number, double r1, double r2, boolean equal) {
        if (number == r1 || number == r2)
            return equal;
        return r1 < number && number < r2 || r2 < number && number < r1;
    }

    @Override
    public boolean inRange(int number, int r1, int r2, boolean equal) {
        if (number == r1 || number == r2)
            return equal;
        return r1 < number && number < r2 || r2 < number && number < r1;
    }

    @Override
    public boolean inRange(double number, double r, boolean equal) {
        if (number == r || number == -r)
            return equal;
        return -r < number && number < r || r < number && number < -r;
    }

    @Override
    public boolean inRange(int number, int r, boolean equal) {
        if (number == r || number == -r)
            return equal;
        return -r < number && number < r || r < number && number < -r;
    }

    @Override
    public boolean inRange(double number, double r) {
        if (number == r || number == -r)
            return true;
        return -r < number && number < r || r < number && number < -r;
    }

    @Override
    public boolean inRange(int number, int r) {
        if (number == r || number == -r)
            return true;
        return -r < number && number < r || r < number && number < -r;
    }

    @Override
    public double getDistanceXZ(Location loc, Location loc2) {
        if (loc.getWorld() != loc2.getWorld())
            return 0;
        return Math.abs(loc.getX() * loc.getZ() - loc2.getX() * loc2.getZ());
    }

    @Override
    public double inTheRangeXZY(Location loc, Location loc2) {
        if (loc.getWorld() != loc2.getWorld()) {
            return 0;
        }
        return Math.abs(loc.getX() * loc.getZ() * loc.getY() - loc2.getX() * loc2.getZ() * loc2.getY());
    }

    @Override
    public boolean inTheRangeXZ(Location loc, Location loc2, int distanceSquared) {
        if (loc.getWorld() != loc2.getWorld()) {
            return false;
        }
        return Math.pow(loc.getX() - loc2.getX(), 2) + Math.pow(loc.getZ() - loc2.getZ(), 2)
                <= distanceSquared;
    }

    @Override
    public boolean inTheRangeXZY(Location loc, Location loc2, int distanceSquared) {
        if (loc.getWorld() != loc2.getWorld()) {
            return false;
        }
        return Math.pow(loc.getX() - loc2.getX(), 2) + Math.pow(loc.getY() - loc2.getY(), 2) + Math.pow(loc.getZ() - loc2.getZ(), 2)
                <= distanceSquared;
    }

    @Override
    public List<String> getObjectList(List<Object> input, String type) {
        List<String> returnList = new ArrayList<>();
        String customName;
        for (Object value : input) {
            if (value == null)
                continue;
            if (type.equals("type")) {
                if (value instanceof UUID)
                    returnList.add(value.toString());
                if (value instanceof Block)
                    returnList.add(((Block) value).getType().name());
                if (value instanceof ItemStack)
                    returnList.add(((ItemStack) value).getType().name());
                if (value instanceof Material)
                    returnList.add(((Material) value).name());
                if (value instanceof Entity)
                    returnList.add(((Entity) value).getType().name());
                if (value instanceof EntityType)
                    returnList.add(((EntityType) value).name());
            } else if (type.equals("name")) {
                if (value instanceof UUID)
                    returnList.add(value.toString());
                if (value instanceof Block)
                    returnList.add(((Block) value).getType().name());
                if (value instanceof ItemStack) {
                    try {
                        returnList.add(((ItemStack) value).getItemMeta().getDisplayName());
                    } catch (Exception ex) {
                        returnList.add(((ItemStack) value).getType().name());
                    }
                }
                if (value instanceof Material)
                    returnList.add(((Material) value).name());
                if (value instanceof Entity) {
                    if (value instanceof Player) {
                        try {
                            returnList.add(((Player) value).getDisplayName());
                        } catch (Exception ex) {
                            returnList.add(((Player) value).getName());
                        }
                    } else {
                        customName = ((Entity) value).getCustomName();
                        if (customName != null)
                            returnList.add(customName);
                        else
                            returnList.add(((Entity) value).getType().name());
                    }
                }
                if (value instanceof EntityType)
                    returnList.add(((EntityType) value).name());
            }
        }
        return returnList;
    }

    @Override
    public String getObjectListString(List<Object> input, String type) {
        StringBuilder sb = new StringBuilder();
        for (String str : getObjectList(input, type))
            sb.append(str).append(", ");
        try {
            sb.setLength(sb.length() - 2);
        } catch (Exception ignored) {
        }
        return sb.toString();
    }

    @Override
    public String getNearbyListString(String pluginName, Location loc, String targetType, String returnType, String
            group, int range) {
        switch (targetType) {
            case "players":
                break;
            case "entities":
                targetType = "Entities";
                break;
            case "blocks":
                targetType = "Materials";
                break;
            case "mythicmobs":
                targetType = "MythicMobs";
                break;
            default:
                UtilsHandler.getMsg().sendErrorMsg(pluginName,
                        "An error occurred while converting placeholder: \"%TARGET_nearby%TYPE%NAME/TYPE%GROUP%RADIUS%\"");
                UtilsHandler.getMsg().sendErrorMsg(pluginName,
                        "Can not find the type name in groups.yml: \"" + group + "\"");
                return null;
        }
        List<String> list = null;
        if (!targetType.equals("players"))
            list = ConfigHandler.getConfigPath().getGroupProp().get(targetType).get(group);
        if (list == null && !group.equals("all")) {
            UtilsHandler.getMsg().sendErrorMsg(pluginName,
                    "An error occurred while converting placeholder: \"%TARGET_nearby%TYPE%NAME/TYPE%GROUP%RADIUS%\"");
            UtilsHandler.getMsg().sendErrorMsg(pluginName,
                    "Can not find the group name in groups.yml: \"" + group + "\"");
            return null;
        }
        List<Object> objectList = getNearbyList(loc, targetType, list, range, group.equals("all"));
        if (returnType.equals("amount"))
            return String.valueOf(objectList.size());
        return getObjectListString(objectList, returnType);
    }

    @Override
    public List<Object> getNearbyList(Location loc, String targetType, List<String> input, int range, boolean all) {
        List<Object> output = new ArrayList<>();
        switch (targetType.toLowerCase()) {
            case "players":
                for (Player player : getNearbyPlayersXZY(loc, range))
                    if (all)
                        output.add(player);
                break;
            case "entities":
                for (Entity entity : loc.getNearbyEntities(range, range, range))
                    if (all || input.contains(entity.getType().name()))
                        output.add(entity);
                break;
            case "materials":
                for (Block block : getNearbyBlocks(loc, range, range, range))
                    if (all || input.contains(block.getType().name()))
                        output.add(block);
                break;
            case "mythicmobs":
                if (!UtilsHandler.getDepend().MythicMobsEnabled())
                    return null;
                for (Entity entity : loc.getNearbyEntities(range, range, range))
                    if (all || input.contains(UtilsHandler.getEntity().getMythicMobName(entity)))
                        output.add(entity);
                break;
        }
        return output;
    }

    @Override
    public List<Player> getNearbyPlayersXZY(Location loc, int rangeSquared) {
        List<Player> list = new ArrayList<>();
        for (Player player : Bukkit.getServer().getOnlinePlayers())
            if (inTheRangeXZY(player.getLocation(), loc, rangeSquared))
                list.add(player);
        return list;
    }

    @Override
    public List<Player> getNearbyPlayersXZ(Location loc, int rangeSquared) {
        List<Player> list = new ArrayList<>();
        for (Player player : Bukkit.getServer().getOnlinePlayers())
            if (inTheRangeXZ(player.getLocation(), loc, rangeSquared))
                list.add(player);
        return list;
    }

    @Override
    public Collection<Entity> getNearbyEntities(Location loc, int x, int y, int z) {
        return loc.getNearbyEntities(x, y, z);
    }

    @Override
    public List<Block> getNearbyBlocks(Location loc, int X, int Y, int Z) {
        List<Block> list = new ArrayList<>();
        Location blockLoc;
        for (int x = -X; x <= X; x++) {
            for (int z = -Z; z <= Z; z++) {
                for (int y = -Y; y <= Y; y++) {
                    blockLoc = loc.clone().add(x, y, z);
                    list.add(blockLoc.getBlock());
                }
            }
        }
        return list;
    }

    /**
     * Sort Map keys by values.
     * High -> Low
     *
     * @param map the input map.
     * @param <K> the input key.
     * @param <V> the input value.
     * @return the sorted map.
     */
    @Override
    public <K, V extends Comparable<? super V>> Map<K, V> sortByValue(Map<K, V> map) {
        List<Map.Entry<K, V>> list = new ArrayList<>(map.entrySet());
        list.sort(Collections.reverseOrder(Map.Entry.comparingByValue()));

        Map<K, V> result = new LinkedHashMap<>();
        for (Map.Entry<K, V> entry : list)
            result.put(entry.getKey(), entry.getValue());
        return result;
    }

    /**
     * Sort Map keys by values.
     * Low -> High
     *
     * @param map the input map.
     * @param <K> the input key.
     * @param <V> the input value.
     * @return the sorted map.
     */
    @Override
    public <K, V extends Comparable<? super V>> Map<K, V> sortByValueLow(Map<K, V> map) {
        List<Map.Entry<K, V>> list = new ArrayList<>(map.entrySet());
        list.sort(Map.Entry.comparingByValue());

        Map<K, V> result = new LinkedHashMap<>();
        for (Map.Entry<K, V> entry : list)
            result.put(entry.getKey(), entry.getValue());
        return result;
    }

    @Override
    public String translateColorCode(String input) {
        return ChatColor.translateAlternateColorCodes('&', input);
    }

    @Override
    public List<String> translateColorCode(List<String> input) {
        List<String> list = new ArrayList<>();
        for (String s : input) {
            list.add(ChatColor.translateAlternateColorCodes('&', s));
        }
        return list;
    }


    @Override
    public ChatColor getChatColor(String input) {
        input = getColorCode(input);
        switch (input) {
            case "0":
                return ChatColor.BLACK;
            case "1":
                return ChatColor.DARK_BLUE;
            case "2":
                return ChatColor.DARK_GREEN;
            case "3":
                return ChatColor.DARK_AQUA;
            case "4":
                return ChatColor.DARK_RED;
            case "5":
                return ChatColor.DARK_PURPLE;
            case "6":
                return ChatColor.GOLD;
            case "7":
                return ChatColor.GRAY;
            case "8":
                return ChatColor.DARK_GRAY;
            case "9":
                return ChatColor.BLUE;
            case "a":
                return ChatColor.GREEN;
            case "b":
                return ChatColor.AQUA;
            case "c":
                return ChatColor.RED;
            case "d":
                return ChatColor.LIGHT_PURPLE;
            case "e":
                return ChatColor.YELLOW;
            case "f":
                return ChatColor.WHITE;
            default:
                return null;
        }
    }

    @Override
    public Color getColor(String input) {
        switch (input) {
            case "WHITE":
                return Color.WHITE;
            case "SILVER":
                return Color.SILVER;
            case "GRAY":
                return Color.GRAY;
            case "BLACK":
                return Color.BLACK;
            case "RED":
                return Color.RED;
            case "MAROON":
                return Color.MAROON;
            case "YELLOW":
                return Color.YELLOW;
            case "OLIVE":
                return Color.OLIVE;
            case "LIME":
                return Color.LIME;
            case "GREEN":
                return Color.GREEN;
            case "AQUA":
                return Color.AQUA;
            case "TEAL":
                return Color.TEAL;
            case "BLUE":
                return Color.BLUE;
            case "NAVY":
                return Color.NAVY;
            case "FUCHSIA":
                return Color.FUCHSIA;
            case "PURPLE":
                return Color.PURPLE;
            case "ORANGE":
                return Color.ORANGE;
            default:
                return null;
        }
    }

    @Override
    public Set<Class<?>> getAllExtendedOrImplementedClass(Class<?> clazz) {
        List<Class<?>> res = new ArrayList<>();
        do {
            res.add(clazz);
            // First, add all the interfaces implemented by this class
            Class<?>[] interfaces = clazz.getInterfaces();
            if (interfaces.length > 0) {
                res.addAll(Arrays.asList(interfaces));
                for (Class<?> interfaze : interfaces)
                    res.addAll(getAllExtendedOrImplementedClass(interfaze));
            }
            // Add the super class
            Class<?> superClass = clazz.getSuperclass();
            // Interfaces does not have java,lang.Object as superclass, they have null, so break the cycle and return
            if (superClass == null)
                break;
            // Now inspect the superclass
            clazz = superClass;
        } while (!"java.lang.Object".equals(clazz.getCanonicalName()));
        return new HashSet<>(res);
    }

    /**
     * @param input           the input status. Values: true/on, false/off, null, known
     * @param status          the currently status. Values: true, false, null
     * @param unknown_default setting the unknown input value to null or not.
     * @return the new status. Values: true, false, none, known
     */
    @Override
    public String getToggleStatus(String input, boolean status, boolean unknown_default) {
        if (input == null)
            input = "null";
        switch (input) {
            case "t":
            case "on":
            case "enable":
                input = "true";
                break;
            case "f":
            case "off":
            case "disable":
                input = "false";
                break;
            case "null":
                break;
            default:
                if (unknown_default) {
                    input = "null";
                    break;
                }
                return "unknown";
        }
        if (status) {
            if (input.equals("true"))
                return "alreadyOn";
            else
                return "false";
        } else {
            if (input.equals("false"))
                return "alreadyOff";
            else
                return "true";
        }
    }
}