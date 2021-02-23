package tw.momocraft.coreplus.utils;

import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import tw.momocraft.coreplus.api.UtilsInterface;
import tw.momocraft.coreplus.handlers.ConfigHandler;
import tw.momocraft.coreplus.handlers.UtilsHandler;

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
    public List<String> getStringListFromPlayers(List<Player> input) {
        List<String> list = new ArrayList<>();
        for (Player player : input) {
            if (player == null)
                continue;
            list.add(player.getName());
        }
        return list;
    }

    @Override
    public String getStringFromPlayers(List<Player> input) {
        StringBuilder sb = new StringBuilder();
        for (Player player : input) {
            if (player == null)
                continue;
            sb.append(player.getName()).append(",");
        }
        return sb.toString();
    }

    @Override
    public List<String> getStringListDisplayNameFromPlayers(List<Player> input) {
        List<String> list = new ArrayList<>();
        for (Player player : input) {
            if (player == null)
                continue;
            try {
                list.add(player.getDisplayName());
            } catch (Exception ignored) {
                list.add(player.getName());
            }
        }
        return list;
    }

    @Override
    public String getStringDisplayNameFromPlayers(List<Player> input) {
        StringBuilder sb = new StringBuilder();
        for (Player player : input) {
            if (player == null)
                continue;
            try {
                sb.append(player.getDisplayName()).append(",");
            } catch (Exception ignored) {
                sb.append(player.getName()).append(",");
            }
        }
        return sb.toString();
    }

    @Override
    public List<String> getStringListFromUUIDs(List<UUID> input) {
        List<String> list = new ArrayList<>();
        for (UUID uuid : input) {
            if (uuid == null)
                continue;
            list.add(uuid.toString());
        }
        return list;
    }

    @Override
    public String getStringFromUUIDs(List<UUID> input) {
        StringBuilder sb = new StringBuilder();
        for (UUID uuid : input) {
            if (uuid == null)
                continue;
            sb.append(uuid.toString()).append(",");
        }
        return sb.toString();
    }

    @Override
    public List<String> getStringListTypeFromEntities(List<Entity> input) {
        List<String> list = new ArrayList<>();
        for (Entity entity : input) {
            if (entity == null)
                continue;
            list.add(entity.getType().name());
        }
        return list;
    }

    @Override
    public String getStringTypeFromEntities(List<Entity> input) {
        StringBuilder sb = new StringBuilder();
        for (Entity entity : input) {
            if (entity == null)
                continue;
            sb.append(entity.getType().name()).append(",");
        }
        return sb.toString();
    }

    @Override
    public List<String> getStringListNameFromEntities(List<Entity> input) {
        List<String> list = new ArrayList<>();
        for (Entity entity : input) {
            if (entity == null)
                continue;
            list.add(entity.getName());
        }
        return list;
    }

    @Override
    public String getStringNameFromEntities(List<Entity> input) {
        StringBuilder sb = new StringBuilder();
        for (Entity entity : input) {
            if (entity == null)
                continue;
            sb.append(entity.getName()).append(",");
        }
        return sb.toString();
    }

    @Override
    public List<String> getStringListTypeFromItem(List<ItemStack> input) {
        List<String> list = new ArrayList<>();
        for (ItemStack itemStack : input) {
            if (itemStack == null)
                continue;
            list.add(itemStack.getType().name());
        }
        return list;
    }

    @Override
    public String getStringTypeFromItems(List<ItemStack> input) {
        StringBuilder sb = new StringBuilder();
        for (ItemStack itemStack : input) {
            if (itemStack == null)
                continue;
            sb.append(itemStack.getType()).append(",");
        }
        return sb.toString();
    }

    @Override
    public List<String> getStringListNameFromItems(List<ItemStack> input) {
        List<String> list = new ArrayList<>();
        for (ItemStack itemStack : input) {
            if (itemStack == null)
                continue;
            try {
                list.add(itemStack.getItemMeta().getDisplayName());
            } catch (Exception ignored) {
                list.add(itemStack.getType().name());
            }
        }
        return list;
    }

    @Override
    public String getStringNameFromItems(List<ItemStack> input) {
        StringBuilder sb = new StringBuilder();
        for (ItemStack itemStack : input) {
            if (itemStack == null)
                continue;
            try {
                sb.append(itemStack.getItemMeta().getDisplayName()).append(",");
            } catch (Exception ignored) {
                sb.append(itemStack.getType()).append(",");
            }
        }
        return sb.toString();
    }

    @Override
    public List<String> getStringListFromBlocks(List<Block> input) {
        List<String> list = new ArrayList<>();
        for (Block block : input) {
            if (block == null)
                continue;
            list.add(block.getType().name());
        }
        return list;
    }

    @Override
    public String getStringFromBlocks(List<Block> input) {
        StringBuilder sb = new StringBuilder();
        for (Block block : input) {
            if (block == null)
                continue;
            sb.append(block.getType().name()).append(",");
        }
        return sb.toString();
    }

    @Override
    public List<String> getStringListFromMaterials(List<Material> input) {
        List<String> list = new ArrayList<>();
        for (Material material : input) {
            if (material == null)
                continue;
            list.add(material.name());
        }
        return list;
    }

    @Override
    public String getStringFromMaterials(List<Material> input) {
        StringBuilder sb = new StringBuilder();
        for (Material material : input) {
            if (material == null)
                continue;
            sb.append(material.name()).append(",");
        }
        return sb.toString();
    }

    @Override
    public String getNearbyString(Location loc, String returnType, String type, List<String> checkList, int range) {
        StringBuilder output = new StringBuilder();
        String target;
        switch (type.toLowerCase()) {
            case "entities":
                for (Entity entity : loc.getNearbyEntities(range, range, range)) {
                    if (entity == null)
                        continue;
                    if (returnType.equals("type")) {
                        target = entity.getType().name();
                    } else if (returnType.equals("name")) {
                        target = entity.getCustomName();
                        if (target == null)
                            target = entity.getType().name();
                    } else {
                        UtilsHandler.getLang().sendErrorMsg(ConfigHandler.getPluginName(), "An unexpected error occurred, please report it to the plugin author.");
                        UtilsHandler.getLang().sendErrorMsg(ConfigHandler.getPluginName(), "Can not the the return type of nearby list: \"" + returnType + "\"");
                        return null;
                    }
                    if (group.equals("all")) {
                        output.append(target).append(",");
                        continue;
                    }
                    if (checkList.contains(target))
                        output.append(target).append(",");
                }
                break;
            case "materials":
                for (Material material : getNearbyMaterial(loc, range, range, range)) {
                    target = material.name();
                    if (checkList.contains(target))
                        output.append(target).append(",");
                }
                break;
            case "mythicmobs":
                for (Entity entity : loc.getNearbyEntities(range, range, range)) {
                    if (UtilsHandler.getEntity().isMythicMob(entity)) {
                        target = entity.getCustomName();
                        if (target == null)
                            target = UtilsHandler.getEntity().getMythicMobName(entity);
                        if (checkList.contains(target))
                            output.append(target).append(",");
                    }
                }
            default:
                UtilsHandler.getLang().sendErrorMsg(ConfigHandler.getPluginName(), "An error occurred while getting the nearby things.");
                UtilsHandler.getLang().sendErrorMsg(ConfigHandler.getPluginName(), "Can not find the type of \"" + type + "\" in CorePlus/groups.yml.");
                return null;
        }
        return output.toString();
    }

    @Override
    public List<String> getNearbyStringList(Location loc, String returnType, String type, List<String> input, int range) {
        List<String> output = new ArrayList<>();
        String target;
        switch (type.toLowerCase()) {
            case "entities":
                for (Entity entity : loc.getNearbyEntities(range, range, range)) {
                    if (entity == null)
                        continue;
                    if (returnType.equals("type")) {
                        target = entity.getType().name();
                    } else if (returnType.equals("name")) {
                        target = entity.getCustomName();
                        if (target == null)
                            target = entity.getType().name();
                    } else {
                        UtilsHandler.getLang().sendErrorMsg(ConfigHandler.getPluginName(), "An unexpected error occurred, please report it to the plugin author.");
                        UtilsHandler.getLang().sendErrorMsg(ConfigHandler.getPluginName(), "Can not the the return type of nearby list: \"" + returnType + "\"");
                        return null;
                    }
                    if (checkList.contains(target))
                        output.add(target);
                }
                break;
            case "materials":
                for (Material material : getNearbyMaterial(loc, range, range, range)) {
                    target = material.name();
                    if (checkList.contains(target))
                        output.add(target);
                }
                break;
            case "mythicmobs":
                for (Entity entity : loc.getNearbyEntities(range, range, range)) {
                    if (UtilsHandler.getEntity().isMythicMob(entity)) {
                        target = entity.getCustomName();
                        if (target == null)
                            target = UtilsHandler.getEntity().getMythicMobName(entity);
                        if (checkList.contains(target))
                            output.add(target);
                    }
                }
                break;
            break;
        }
        return output;
    }

    @Override
    public List<Player> getNearbyPlayersXZY(Location loc, int rangeSquared) {
        List<Player> list = new ArrayList<>();
        for (Player player : Bukkit.getServer().getOnlinePlayers()) {
            if (inTheRangeXZY(player.getLocation(), loc, rangeSquared)) {
                list.add(player);
            }
        }
        return list;
    }

    @Override
    public List<Player> getNearbyPlayersXZ(Location loc, int rangeSquared) {
        List<Player> list = new ArrayList<>();
        for (Player player : Bukkit.getServer().getOnlinePlayers()) {
            if (inTheRangeXZ(player.getLocation(), loc, rangeSquared)) {
                list.add(player);
            }
        }
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

    @Override
    public List<Material> getNearbyMaterial(Location loc, int X, int Y, int Z) {
        List<Material> list = new ArrayList<>();
        Location blockLoc;
        for (int x = -X; x <= X; x++) {
            for (int z = -Z; z <= Z; z++) {
                for (int y = -Y; y <= Y; y++) {
                    blockLoc = loc.clone().add(x, y, z);
                    list.add(blockLoc.getBlock().getType());
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
}