package tw.momocraft.coreplus.utils;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;
import tw.momocraft.coreplus.api.CorePlusAPI;
import tw.momocraft.coreplus.api.UtilsInterface;
import tw.momocraft.coreplus.handlers.ConfigHandler;
import tw.momocraft.coreplus.handlers.UtilsHandler;

import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
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
        return Math.abs(loc.getX() * loc.getZ() - loc2.getX() * loc2.getZ()) <= distanceSquared;
    }

    @Override
    public boolean inTheRangeXZY(Location loc, Location loc2, int distanceSquared) {
        if (loc.getWorld() != loc2.getWorld()) {
            return false;
        }
        return Math.abs(loc.getX() * loc.getZ() * loc.getY() - loc2.getX() * loc2.getZ() * loc2.getY()) <= distanceSquared;
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

    @Override
    public String translateLayout(String input, Player player) {
        if (input == null) {
            return "";
        }
        if (player != null && !(player instanceof ConsoleCommandSender)) {
            String playerName = player.getName();
            // %player%
            try {
                input = input.replace("%player%", playerName);
            } catch (Exception e) {
                UtilsHandler.getLang().sendDebugTrace(ConfigHandler.getPlugin(), e);
            }
            // %player_display_name%
            try {
                input = input.replace("%player_display_name%", player.getDisplayName());
            } catch (Exception e) {
                UtilsHandler.getLang().sendDebugTrace(ConfigHandler.getPlugin(), e);
            }
            UUID playerUUID = player.getUniqueId();
            // %player_uuid%
            try {
                input = input.replace("%player_uuid%", playerUUID.toString());
            } catch (Exception e) {
                UtilsHandler.getLang().sendDebugTrace(ConfigHandler.getPlugin(), e);
            }
            // %player_sneaking%
            try {
                input = input.replace("%player_sneaking%", String.valueOf(player.isSneaking()));
            } catch (Exception e) {
                UtilsHandler.getLang().sendDebugTrace(ConfigHandler.getPlugin(), e);
            }
            // %player_flying%
            try {
                input = input.replace("%player_flying%", String.valueOf(player.isFlying()));
            } catch (Exception e) {
                UtilsHandler.getLang().sendDebugTrace(ConfigHandler.getPlugin(), e);
            }
            Location loc = player.getLocation();
            // %player_world%
            if (input.contains("%player_world%")) {
                input = input.replace("%player_world%", loc.getWorld().getName());
            }
            // %player_loc%
            // %player_loc_x%, %player_loc_y%, %player_loc_z%
            // %player_loc_x_NUMBER%, %player_loc_y_NUMBER%, %player_loc_z_NUMBER%
            if (input.contains("%player_loc")) {
                try {
                    String loc_x = String.valueOf(loc.getBlockX());
                    String loc_y = String.valueOf(loc.getBlockY());
                    String loc_z = String.valueOf(loc.getBlockZ());
                    String[] arr = input.split("%");
                    for (int i = 0; i < arr.length; i++) {
                        switch (arr[i]) {
                            case "player_loc_x":
                                if (arr[i + 1].matches("^-?[0-9]\\d*(\\.\\d+)?$")) {
                                    input = input.replace("%player_loc_x%" + arr[i + 1] + "%", loc_x + Integer.parseInt(arr[i + 1]));
                                }
                                break;
                            case "player_loc_y":
                                if (arr[i + 1].matches("^-?[0-9]\\d*(\\.\\d+)?$")) {
                                    input = input.replace("%player_loc_y%" + arr[i + 1] + "%", loc_y + Integer.parseInt(arr[i + 1]));
                                }
                                break;
                            case "player_loc_z":
                                if (arr[i + 1].matches("^-?[0-9]\\d*(\\.\\d+)?$")) {
                                    input = input.replace("%player_loc_z%" + arr[i + 1] + "%", loc_z + Integer.parseInt(arr[i + 1]));
                                }
                                break;
                        }
                    }
                    input = input.replace("%player_loc%", loc_x + ", " + loc_y + ", " + loc_z);
                    input = input.replace("%player_loc_x%", loc_x);
                    input = input.replace("%player_loc_y%", loc_y);
                    input = input.replace("%player_loc_z%", loc_z);
                } catch (Exception e) {
                    UtilsHandler.getLang().sendDebugTrace(ConfigHandler.getPlugin(), e);
                }
            }
            if (UtilsHandler.getDepend().VaultEnabled()) {
                if (input.contains("%money%")) {
                    input = input.replace("%money%", String.valueOf(UtilsHandler.getDepend().getVaultApi().getBalance(playerUUID)));
                }
            }
            if (UtilsHandler.getDepend().PlayerPointsEnabled()) {
                if (input.contains("%points%")) {
                    input = input.replace("%points%", String.valueOf(UtilsHandler.getDepend().getPlayerPointsApi().getBalance(playerUUID)));
                }
            }
        }
        // %player% => CONSOLE
        if (player == null) {
            try {
                input = input.replace("%player%", "CONSOLE");
            } catch (Exception e) {
                UtilsHandler.getLang().sendDebugTrace(ConfigHandler.getPlugin(), e);
            }
        }
        // %server_name%
        try {
            input = input.replace("%server_name%", Bukkit.getServer().getName());
        } catch (Exception e) {
            UtilsHandler.getLang().sendDebugTrace(ConfigHandler.getPlugin(), e);
        }
        // %localtime_time% => 2020/08/08 12:30:00
        try {
            input = input.replace("%localtime_time%", new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(new Date()));
        } catch (Exception e) {
            UtilsHandler.getLang().sendDebugTrace(ConfigHandler.getPlugin(), e);
        }
        // %random_number%500%
        if (input.contains("%random_number%")) {
            try {
                String[] arr = input.split("%");
                for (int i = 0; i < arr.length; i++) {
                    if (arr[i].equals("random_number") && arr[i + 1].matches("^[0-9]*$")) {
                        input = input.replace("%random_number%" + arr[i + 1] + "%", String.valueOf(new Random().nextInt(Integer.parseInt(arr[i + 1]))));
                    }
                }
            } catch (Exception e) {
                UtilsHandler.getLang().sendDebugTrace(ConfigHandler.getPlugin(), e);
            }
        }
        // %random_player%
        if (input.contains("%random_player%")) {
            try {
                List<Player> playerList = new ArrayList(Bukkit.getOnlinePlayers());
                String randomPlayer = playerList.get(new Random().nextInt(playerList.size())).getName();
                input = input.replace("%random_player%", randomPlayer);
            } catch (Exception e) {
                UtilsHandler.getLang().sendDebugTrace(ConfigHandler.getPlugin(), e);
            }
        }
        // %random_player_except%AllBye,huangge0513%
        if (input.contains("%random_player_except%")) {
            List<String> placeholderList = new ArrayList<>();
            List<Player> playerList = new ArrayList(Bukkit.getOnlinePlayers());
            String[] arr = input.split("%");
            for (int i = 0; i < arr.length; i++) {
                if (arr[i].equals("random_player_except")) {
                    placeholderList.add((arr[i + 1]));
                }
            }
            String[] playerArr;
            Player randomPlayer;
            String randomPlayerName;
            for (String exceptPlayer : placeholderList) {
                playerArr = exceptPlayer.split(",");
                while (true) {
                    if (playerList.isEmpty()) {
                        input = input.replace("%random_player_except%" + exceptPlayer + "%", "");
                        break;
                    }
                    randomPlayer = playerList.get(new Random().nextInt(playerList.size()));
                    randomPlayerName = randomPlayer.getName();
                    playerList.remove(randomPlayer);
                    try {
                        if (!Arrays.asList(playerArr).contains(randomPlayerName)) {
                            String newList = placeholderList.toString().replaceAll("[\\[\\]\\s]", "");
                            input = input.replace("%random_player_except%" + newList + "%", randomPlayerName);
                            break;
                        }
                    } catch (Exception e) {
                        UtilsHandler.getLang().sendDebugTrace(ConfigHandler.getPlugin(), e);
                    }
                }
            }
        }
        // Translate color codes.
        input = ChatColor.translateAlternateColorCodes('&', input);
        // Translate PlaceHolderAPI's placeholders.
        if (UtilsHandler.getDepend().PlaceHolderAPIEnabled()) {
            try {
                return PlaceholderAPI.setPlaceholders(player, input);
            } catch (NoSuchFieldError e) {
                UtilsHandler.getLang().sendDebugMsg(ConfigHandler.getPrefix(), "Error has occurred when setting the PlaceHolder " + e.getMessage() + ", if this issue persist contact the developer of PlaceholderAPI.");
                return input;
            }
        }
        return input;
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
    public String getSkullValue(ItemStack itemStack) {
        SkullMeta headMeta;
        try {
            headMeta = (SkullMeta) itemStack.getItemMeta();
        } catch (Exception e) {
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
        return ConfigHandler.getConfigPath().getMenuIJ().equals(node);
    }

    @Override
    public boolean isMenu(ItemStack itemStack) {
        // Holding ItemJoin menu.
        if (UtilsHandler.getDepend().ItemJoinEnabled()) {
            String menuIJ = ConfigHandler.getConfigPath().getMenuIJ();
            if (!menuIJ.equals("")) {
                return UtilsHandler.getDepend().getItemJoinUtils().isMenu(itemStack);
            }
        }
        // Holding a menu item.
        if (itemStack.getType().name().equals(ConfigHandler.getConfigPath().getMenuType())) {
            String itemName;
            try {
                itemName = itemStack.getItemMeta().getDisplayName();
            } catch (Exception ex) {
                itemName = "";
            }
            String menuName = ConfigHandler.getConfigPath().getMenuName();
            return menuName.equals("") || itemName.equals(translateColorCode(menuName));
        }
        return false;
    }

    @Override
    public boolean isCustomItem(ItemStack itemStack) {
        // Holding ItemJoin menu.
        if (UtilsHandler.getDepend().ItemJoinEnabled()) {
            return UtilsHandler.getDepend().getItemJoinUtils().isCustom(itemStack);
        }
        return false;
    }
}