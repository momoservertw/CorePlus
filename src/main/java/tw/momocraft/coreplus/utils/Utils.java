package tw.momocraft.coreplus.utils;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import me.RockinChaos.itemjoin.api.ItemJoinAPI;
import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;
import tw.momocraft.coreplus.api.UtilsInterface;
import tw.momocraft.coreplus.handlers.ConfigHandler;

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
    public int getRandom(int lower, int upper) {
        Random random = new Random();
        return random.nextInt((upper - lower) + 1) + lower;
    }

    @Override
    public String getRandomString(List<String> list) {
        return list.get(new Random().nextInt(list.size()));
    }

    /**
     * @param value the checking value
     * @return if the chance will succeed or not.
     */
    @Override
    public boolean isRandChance(double value) {
        return value < new Random().nextDouble();
    }

    /**
     * @param value      the spawn reason of this entity.
     * @param list       the spawn Reasons in configuration.
     * @param ignoreList the spawn Ignore-Reasons in configuration.
     * @return if the entity spawn reason match the config setting.
     */
    @Override
    public boolean containIgnoreValue(String value, List<String> list, List<String> ignoreList) {
        if (ignoreList.contains(value)) {
            return false;
        }
        if (list.isEmpty()) {
            return true;
        }
        return list.contains(value);
    }

    /**
     * @param block the checking block.
     * @param value the option "Liquid" in configuration.
     * @return if the entity spawned in water or lava.
     */
    @Override
    public boolean isLiquid(Block block, String value) {
        if (value == null) {
            return true;
        }
        boolean blockLiquid = block.isLiquid();
        return value.equals("true") && blockLiquid || value.equals("false") && !blockLiquid;
    }

    /**
     * @param time  the checking word time..
     * @param value the spawn day/night in configuration.
     * @return if the entity spawn day match the config setting.
     */
    @Override
    public boolean isDay(double time, String value) {
        if (value == null) {
            return true;
        }
        return value.equals("true") && (time < 12300 || time > 23850) || value.equals("false") && (time >= 12300 && time <= 23850);
    }

    /**
     * @param operator the comparison operator to compare two numbers.
     * @param number1  first number.
     * @param number2  second number.
     */
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

    /**
     * @param number the checking number
     * @param r1     the first side of range.
     * @param r2     another side of range.
     * @return if the check number is inside the range.
     * It will return false if the two side of range numbers are equal.
     */
    @Override
    public boolean getRange(double number, double r1, double r2) {
        return r1 <= number && number <= r2 || r2 <= number && number <= r1;
    }

    /**
     * @param number the checking number.
     * @param r1     the first side of range.
     * @param r2     another side of range.
     * @return if the check number is inside the range.
     * It will return false if the two side of range numbers are equal.
     */
    @Override
    public boolean getRange(int number, int r1, int r2) {
        return r1 <= number && number <= r2 || r2 <= number && number <= r1;
    }

    /**
     * @param number the location of event.
     * @param r      the side of range.
     * @return if the check number is inside the range.
     */
    @Override
    public boolean getRange(int number, int r) {
        return -r <= number && number <= r || r <= number && number <= -r;
    }

    /**
     * @param number the location of event.
     * @param r      the side of range.
     * @return if the check number is inside the range.
     */
    @Override
    public boolean getRange(double number, double r) {
        return -r <= number && number <= r || r <= number && number <= -r;
    }

    /**
     * @param loc      location.
     * @param loc2     location2.
     * @param distance The checking value.
     * @return if two locations is in the distance.
     */
    @Override
    public boolean inTheRange(Location loc, Location loc2, int distance) {
        if (loc.getWorld() == loc2.getWorld()) {
            return loc.distanceSquared(loc2) <= distance;
        }
        return false;
    }

    @Override
    public String getNearbyPlayer(Player player, int range) {
        try {
            ArrayList<Entity> entities = (ArrayList<Entity>) player.getNearbyEntities(range, range, range);
            ArrayList<Block> sightBlock = (ArrayList<Block>) player.getLineOfSight(null, range);
            ArrayList<Location> sight = new ArrayList<>();
            for (Block block : sightBlock) sight.add(block.getLocation());
            for (Location location : sight) {
                for (Entity entity : entities) {
                    if (Math.abs(entity.getLocation().getX() - location.getX()) < 1.3) {
                        if (Math.abs(entity.getLocation().getY() - location.getY()) < 1.5) {
                            if (Math.abs(entity.getLocation().getZ() - location.getZ()) < 1.3) {
                                if (entity instanceof Player) {
                                    return entity.getName();
                                }
                            }
                        }
                    }
                }
            }
            return "INVALID";
        } catch (NullPointerException e) {
            return "INVALID";
        }
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
                ConfigHandler.getLang().sendDebugTrace(ConfigHandler.getPrefix(), e);
            }
            // %player_display_name%
            try {
                input = input.replace("%player_display_name%", player.getDisplayName());
            } catch (Exception e) {
                ConfigHandler.getLang().sendDebugTrace(ConfigHandler.getPrefix(), e);
            }
            UUID playerUUID = player.getUniqueId();
            // %player_uuid%
            try {
                input = input.replace("%player_uuid%", playerUUID.toString());
            } catch (Exception e) {
                ConfigHandler.getLang().sendDebugTrace(ConfigHandler.getPrefix(), e);
            }
            // %player_interact%
            try {
                input = input.replace("%player_interact%", getNearbyPlayer(player, 3));
            } catch (Exception e) {
                ConfigHandler.getLang().sendDebugTrace(ConfigHandler.getPrefix(), e);
            }
            // %player_sneaking%
            try {
                input = input.replace("%player_sneaking%", String.valueOf(player.isSneaking()));
            } catch (Exception e) {
                ConfigHandler.getLang().sendDebugTrace(ConfigHandler.getPrefix(), e);
            }
            // %player_flying%
            try {
                input = input.replace("%player_flying%", String.valueOf(player.isFlying()));
            } catch (Exception e) {
                ConfigHandler.getLang().sendDebugTrace(ConfigHandler.getPrefix(), e);
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
                    ConfigHandler.getLang().sendDebugTrace(ConfigHandler.getPrefix(), e);
                }
            }
            if (ConfigHandler.getDepends().VaultEnabled()) {
                if (input.contains("%money%")) {
                    input = input.replace("%money%", String.valueOf(ConfigHandler.getDepends().getVaultApi().getBalance(playerUUID)));
                }
            }
            if (ConfigHandler.getDepends().PlayerPointsEnabled()) {
                if (input.contains("%points%")) {
                    input = input.replace("%points%", String.valueOf(ConfigHandler.getDepends().getPlayerPointsApi().getBalance(playerUUID)));
                }
            }
        }
        // %player% => CONSOLE
        if (player == null) {
            try {
                input = input.replace("%player%", "CONSOLE");
            } catch (Exception e) {
                ConfigHandler.getLang().sendDebugTrace(ConfigHandler.getPrefix(), e);
            }
        }
        // %server_name%
        try {
            input = input.replace("%server_name%", Bukkit.getServer().getName());
        } catch (Exception e) {
            ConfigHandler.getLang().sendDebugTrace(ConfigHandler.getPrefix(), e);
        }
        // %localtime_time% => 2020/08/08 12:30:00
        try {
            input = input.replace("%localtime_time%", new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(new Date()));
        } catch (Exception e) {
            ConfigHandler.getLang().sendDebugTrace(ConfigHandler.getPrefix(), e);
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
                ConfigHandler.getLang().sendDebugTrace(ConfigHandler.getPrefix(), e);
            }
        }
        // %random_player%
        if (input.contains("%random_player%")) {
            try {
                List<Player> playerList = new ArrayList(Bukkit.getOnlinePlayers());
                String randomPlayer = playerList.get(new Random().nextInt(playerList.size())).getName();
                input = input.replace("%random_player%", randomPlayer);
            } catch (Exception e) {
                ConfigHandler.getLang().sendDebugTrace(ConfigHandler.getPrefix(), e);
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
                        ConfigHandler.getLang().sendDebugTrace(ConfigHandler.getPrefix(), e);
                    }
                }
            }
        }
        // Translate color codes.
        input = ChatColor.translateAlternateColorCodes('&', input);
        // Translate PlaceHolderAPI's placeholders.
        if (ConfigHandler.getDepends().PlaceHolderAPIEnabled()) {
            try {
                return PlaceholderAPI.setPlaceholders(player, input);
            } catch (NoSuchFieldError e) {
                ConfigHandler.getLang().sendDebugMsg(ConfigHandler.getPrefix(), "Error has occurred when setting the PlaceHolder " + e.getMessage() + ", if this issue persist contact the developer of PlaceholderAPI.");
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
        SkullMeta headMeta = (SkullMeta) itemStack.getItemMeta();
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
    public boolean isHoldingMenu(ItemStack itemStack, Player player) {
        // Holding ItemJoin menu.
        if (ConfigHandler.getDepends().ItemJoinEnabled()) {
            ItemJoinAPI itemJoinAPI = new ItemJoinAPI();
            String menuIJ = ConfigHandler.getConfigPath().getMenuIJ();
            if (!menuIJ.equals("")) {
                if (itemJoinAPI.getNode(itemStack) != null) {
                    return itemJoinAPI.getNode(itemStack).equals(menuIJ);
                }
                return false;
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
}