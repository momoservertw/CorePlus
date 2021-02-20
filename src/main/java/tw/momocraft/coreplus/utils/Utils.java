package tw.momocraft.coreplus.utils;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;
import tw.momocraft.coreplus.api.UtilsInterface;
import tw.momocraft.coreplus.handlers.ConfigHandler;
import tw.momocraft.coreplus.handlers.UtilsHandler;

import java.lang.reflect.Field;
import java.util.*;

public class Utils implements UtilsInterface {

    @Override
    public boolean containsIgnoreCase(String string1, String string2) {
        return string1 != null && string2 != null && string1.toLowerCase().contains(string2.toLowerCase());
    }

    @Override
    public boolean isInt(String s) {
        try {
            Integer.parseInt(s);
        } catch (NumberFormatException e) {
            return false;
        }
        return true;
    }

    @Override
    public int getRandom(int lower, int higher) {
        Random random = new Random();
        return random.nextInt((higher - lower) + 1) + lower;
    }

    @Override
    public String getRandomString(List<String> list) {
        return list.get(new Random().nextInt(list.size()));
    }

    @Override
    public boolean isRandChance(double value) {
        return value > new Random().nextDouble();
    }

    @Override
    public boolean containIgnoreValue(String value, List<String> list, List<String> ignoreList, boolean def) {
        if (ignoreList != null && ignoreList.contains(value)) {
            return false;
        }
        if (list == null || list.isEmpty()) {
            return def;
        }
        return list.contains(value);
    }

    @Override
    public boolean containIgnoreValue(String value, List<String> list, List<String> ignoreList) {
        if (ignoreList != null && ignoreList.contains(value)) {
            return false;
        }
        if (list == null || list.isEmpty()) {
            return true;
        }
        return list.contains(value);
    }

    @Override
    public boolean containIgnoreValue(String value, List<String> list, boolean def) {
        if (list == null || list.isEmpty()) {
            return def;
        }
        return list.contains(value);
    }

    @Override
    public boolean containIgnoreValue(String value, List<String> list) {
        if (list == null || list.isEmpty()) {
            return true;
        }
        return list.contains(value);
    }

    @Override
    public boolean isLiquid(Block block) {
        return block.isLiquid();
    }

    @Override
    public boolean isLiquid(Block block, String value, boolean def) {
        if (value == null)
            return def;
        return block.isLiquid() == Boolean.parseBoolean(value);
    }

    @Override
    public boolean isDay(double time) {
        return time < 12300 || time > 23850;
    }

    @Override
    public boolean isDay(double time, String value, boolean def) {
        if (value == null)
            return def;
        return (time < 12300 || time > 23850) == Boolean.parseBoolean(value);
    }

    @Override
    public boolean getCompare(String operator, double number1, double number2) {
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
    public boolean getRange(double number, double r1, double r2, boolean equal) {
        if (number == r1 || number == r2)
            return equal;
        return r1 < number && number < r2 || r2 < number && number < r1;
    }

    @Override
    public boolean getRange(int number, int r1, int r2, boolean equal) {
        if (number == r1 || number == r2)
            return equal;
        return r1 < number && number < r2 || r2 < number && number < r1;
    }

    @Override
    public boolean getRange(int number, int r, boolean equal) {
        if (number == r || number == -r)
            return equal;
        return -r < number && number < r || r < number && number < -r;
    }

    @Override
    public boolean getRange(double number, double r, boolean equal) {
        if (number == r || number == -r)
            return equal;
        return -r < number && number < r || r < number && number < -r;
    }

    @Override
    public double getDistanceXZ(Location loc, Location loc2) {
        if (loc.getWorld() != loc2.getWorld()) {
            return 0;
        }
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
    public List<Player> getNearbyPlayersXZY(Location loc, int rangeSquared) {
        List<Player> nearbyPlayers = new ArrayList<>();
        for (Player player : Bukkit.getServer().getOnlinePlayers()) {
            if (inTheRangeXZY(player.getLocation(), loc, rangeSquared)) {
                nearbyPlayers.add(player);
            }
        }
        return nearbyPlayers;
    }

    @Override
    public List<Player> getNearbyPlayersXZ(Location loc, int rangeSquared) {
        List<Player> nearbyPlayers = new ArrayList<>();
        for (Player player : Bukkit.getServer().getOnlinePlayers()) {
            if (inTheRangeXZ(player.getLocation(), loc, rangeSquared)) {
                nearbyPlayers.add(player);
            }
        }
        return nearbyPlayers;
    }

    /**
     * Sort Map keys by values.
     * High -> Low
     *
     * @param map the input map.
     * @param <K>
     * @param <V>
     * @return the sorted map.
     */
    @Override
    public <K, V extends Comparable<? super V>> Map<K, V> sortByValue(Map<K, V> map) {
        List<Map.Entry<K, V>> list = new ArrayList<>(map.entrySet());
        list.sort(Collections.reverseOrder(Map.Entry.comparingByValue()));

        Map<K, V> result = new LinkedHashMap<>();
        for (Map.Entry<K, V> entry : list) {
            result.put(entry.getKey(), entry.getValue());
        }
        return result;
    }

    /**
     * Sort Map keys by values.
     * Low -> High
     *
     * @param map the input map.
     * @param <K>
     * @param <V>
     * @return the sorted map.
     */
    @Override
    public <K, V extends Comparable<? super V>> Map<K, V> sortByValueLow(Map<K, V> map) {
        List<Map.Entry<K, V>> list = new ArrayList<>(map.entrySet());
        list.sort(Map.Entry.comparingByValue());

        Map<K, V> result = new LinkedHashMap<>();
        for (Map.Entry<K, V> entry : list) {
            result.put(entry.getKey(), entry.getValue());
        }
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
    public String getSkullValue(ItemStack itemStack) {
        SkullMeta headMeta;
        try {
            headMeta = (SkullMeta) itemStack.getItemMeta();
        } catch (Exception ex) {
            return null;
        }
        String url = null;
        try {
            Field profileField = headMeta.getClass().getDeclaredField("profile");
            profileField.setAccessible(true);
            GameProfile profile = (GameProfile) profileField.get(headMeta);
            Collection<Property> properties = profile.getProperties().get("textures");
            for (Property property : properties) {
                url = property.getValue();
            }
        } catch (IllegalArgumentException | NoSuchFieldException | SecurityException | IllegalAccessException error) {
            error.printStackTrace();
        }
        return url;
    }

    @Override
    public boolean isMenuNode(String node) {
        if (node == null) {
            return false;
        }
        return ConfigHandler.getConfigPath().getMenuItemJoin().equals(node);
    }

    @Override
    public ItemStack getMenuItemStack(Player player) {
        return UtilsHandler.getDepend().getItemJoinApi().getItemStack(player, ConfigHandler.getConfigPath().getMenuItemJoin());
    }

    @Override
    public ItemStack getItemJoinItemStack(Player player, String node) {
        return UtilsHandler.getDepend().getItemJoinApi().getItemStack(player, node);
    }

    @Override
    public boolean isMenu(ItemStack itemStack) {
        // Holding ItemJoin menu.
        if (UtilsHandler.getDepend().ItemJoinEnabled()) {
            String menuIJ = ConfigHandler.getConfigPath().getMenuItemJoin();
            if (!menuIJ.equals("")) {
                return UtilsHandler.getDepend().getItemJoinApi().isMenu(itemStack);
            }
        }
        // Holding a menu item.
        String itemType = itemStack.getType().name();
        if (itemType.equals(ConfigHandler.getConfigPath().getMenuType())) {
            String itemName;
            try {
                itemName = itemStack.getItemMeta().getDisplayName();
            } catch (Exception ex) {
                itemName = "";
            }
            String menuName = ConfigHandler.getConfigPath().getMenuName();
            if (menuName.equals("") || itemName.equals(translateColorCode(menuName))) {
                if (itemType.equals("PLAYER_HEAD")) {
                    return getSkullValue(itemStack).equals(ConfigHandler.getConfigPath().getMenuSkullTextures());
                }
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean isCustomItem(ItemStack itemStack) {
        // Holding ItemJoin menu.
        if (UtilsHandler.getDepend().ItemJoinEnabled()) {
            return UtilsHandler.getDepend().getItemJoinApi().isCustom(itemStack);
        }
        return false;
    }

    @Override
    public String getItemNode(ItemStack itemStack) {
        return UtilsHandler.getDepend().getItemJoinApi().getItemNode(itemStack);
    }


    @Override
    public boolean isCanUse(String blockType) {
        if (blockType.endsWith("PRESSURE_PLATE")) {
            return true;
        }
        if (blockType.equals("TRIPWIRE")) {
            return true;
        }
        if (blockType.endsWith("DOOR")) {
            return true;
        }
        if (blockType.endsWith("FENCE_GATE")) {
            return true;
        }
        if (blockType.endsWith("BUTTON")) {
            return true;
        }
        switch (blockType) {
            // Crafting
            case "CRAFTING_TABLE":
            case "ENCHANTING_TABLE":
            case "FLETCHING_TABLE":
            case "SMITHING_TABLE":
            case "NOTE_BLOCK":
            case "ANVIL":
            case "BREWING_STAND":
                // Redstone Machine
            case "LEVER":
            case "DIODE":
            case "COMPARATOR":
            case "REPEATER":
            case "REDSTONE_COMPARATOR":
            case "DAYLIGHT_DETECTOR":
                // Other
            case "BEACON":
            case "ITEM_FRAME":
            case "FLOWER_POT":
            case "BED_BLOCK":
            case "CAKE_BLOCK":
            case "COMMAND":
                return true;
            default:
                return false;
        }
    }

    @Override
    public boolean isContainer(String blockType) {
        if (blockType.endsWith("CHEST")) {
            return true;
        }
        if (blockType.endsWith("SHULKER_BOX")) {
            return true;
        }
        switch (blockType) {
            // Crafting
            case "BREWING_STAND":
            case "DISPENSER":
            case "DROPPER":
            case "FURNACE":
            case "HOPPER":
            case "SMOKER":
            case "BARREL":
            case "BLAST_FURNACE":
            case "LOOM":
                // Other
            case "ITEM_FRAME":
            case "JUKEBOX":
            case "ARMOR_STAND":
                return true;
            default:
                return false;
        }
    }
}