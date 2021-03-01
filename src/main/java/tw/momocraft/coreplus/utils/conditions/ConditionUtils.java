package tw.momocraft.coreplus.utils.conditions;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;
import tw.momocraft.coreplus.api.ConditionInterface;
import tw.momocraft.coreplus.handlers.ConfigHandler;
import tw.momocraft.coreplus.handlers.UtilsHandler;
import tw.momocraft.coreplus.listeners.ConditionTest;

import java.lang.reflect.Field;
import java.util.Collection;
import java.util.List;
import java.util.Set;

public class ConditionUtils implements ConditionInterface {

    private static LocationUtils locationUtils;
    private static BlocksUtils blocksUtils;
    private static ConditionTest conditionTest;

    public ConditionUtils() {
        locationUtils = new LocationUtils();
        blocksUtils = new BlocksUtils();
        conditionTest = new ConditionTest();
    }

    /**
     * Checking the condition is matched.
     * Format:
     * <if>Value1...Value2</if>
     * <if>Value1...Value2<or>Value1...Value2</if>
     * <if>Value1...Value2<and>Value1...Value2</if>
     * <if>Value1...Value2<and>Value1...Value2</if>
     * <if>Value1...Value2<and>Value1...Value2<or>Value1...Value2</if>
     *
     * @param input the input condition string.
     * @return if the condition is matched.
     */
    @Override
    public boolean checkCondition(String input) {
        String[] conditionArray = input.split("<or>");
        String[] arr;
        back:
        for (String conditions : conditionArray) {
            if (conditions.contains("<and>")) {
                for (String condition : conditions.split("<and>")) {
                    if (condition.contains(">")) {
                        arr = condition.split(">");
                        if (arr[0].endsWith("!")) {
                            if (!UtilsHandler.getUtil().getCompareAndEquals(">", arr[0].substring(0, arr.length - 1), arr[1]))
                                continue back;
                        } else {
                            if (UtilsHandler.getUtil().getCompareAndEquals(">", arr[0], arr[1]))
                                continue back;
                        }
                    } else if (condition.contains("<")) {
                        arr = condition.split("<");
                        if (arr[0].endsWith("!")) {
                            if (!UtilsHandler.getUtil().getCompareAndEquals("<", arr[0].substring(0, arr.length - 1), arr[1]))
                                continue back;
                        } else {
                            if (UtilsHandler.getUtil().getCompareAndEquals("<", arr[0], arr[1]))
                                continue back;
                        }
                    } else if (condition.contains(">=")) {
                        arr = condition.split(">=");
                        if (arr[0].endsWith("!")) {
                            if (!UtilsHandler.getUtil().getCompareAndEquals(">=", arr[0].substring(0, arr.length - 1), arr[1]))
                                continue back;
                        } else {
                            if (UtilsHandler.getUtil().getCompareAndEquals(">=", arr[0], arr[1]))
                                continue back;
                        }
                    } else if (condition.contains("<=")) {
                        arr = condition.split("<=");
                        if (arr[0].endsWith("!")) {
                            if (!UtilsHandler.getUtil().getCompareAndEquals("<=", arr[0].substring(0, arr.length - 1), arr[1]))
                                continue back;
                        } else {
                            if (UtilsHandler.getUtil().getCompareAndEquals("<=", arr[0], arr[1]))
                                continue back;
                        }
                    } else if (condition.contains("=")) {
                        arr = condition.split("=");
                        if (arr[0].endsWith("!")) {
                            if (!UtilsHandler.getUtil().getCompareAndEquals("=", arr[0].substring(0, arr.length - 1), arr[1]))
                                continue back;
                        } else {
                            if (UtilsHandler.getUtil().getCompareAndEquals("=", arr[0], arr[1]))
                                continue back;
                        }
                    } else {
                        continue back;
                    }
                }
                return true;
            } else {
                if (conditions.contains(">")) {
                    arr = conditions.split(">");
                    if (arr[0].endsWith("!")) {
                        if (!UtilsHandler.getUtil().getCompareAndEquals(">", arr[0].substring(0, arr.length - 1), arr[1]))
                            return true;
                    } else {
                        if (UtilsHandler.getUtil().getCompareAndEquals(">", arr[0], arr[1]))
                            return true;
                    }
                } else if (conditions.contains("<")) {
                    arr = conditions.split("<");
                    if (arr[0].endsWith("!")) {
                        if (!UtilsHandler.getUtil().getCompareAndEquals("<", arr[0].substring(0, arr.length - 1), arr[1]))
                            return true;
                    } else {
                        if (UtilsHandler.getUtil().getCompareAndEquals("<", arr[0], arr[1]))
                            return true;
                    }
                } else if (conditions.contains(">=")) {
                    arr = conditions.split(">=");
                    if (arr[0].endsWith("!")) {
                        if (!UtilsHandler.getUtil().getCompareAndEquals(">=", arr[0].substring(0, arr.length - 1), arr[1]))
                            return true;
                    } else {
                        if (UtilsHandler.getUtil().getCompareAndEquals(">=", arr[0], arr[1]))
                            return true;
                    }
                } else if (conditions.contains("<=")) {
                    arr = conditions.split("<=");
                    if (arr[0].endsWith("!")) {
                        if (!UtilsHandler.getUtil().getCompareAndEquals("<=", arr[0].substring(0, arr.length - 1), arr[1]))
                            return true;
                    } else {
                        if (UtilsHandler.getUtil().getCompareAndEquals("<=", arr[0], arr[1]))
                            return true;
                    }
                } else if (conditions.contains("=")) {
                    arr = conditions.split("=");
                    if (arr[0].endsWith("!")) {
                        if (!UtilsHandler.getUtil().getCompareAndEquals("=", arr[0].substring(0, arr.length - 1), arr[1]))
                            return true;
                    } else {
                        if (UtilsHandler.getUtil().getCompareAndEquals("=", arr[0], arr[1]))
                            return true;
                    }
                }
            }
        }
        return false;
    }

    @Override
    public void executeAction(String input) {

    }

    @Override
    public boolean checkLocation(String pluginName, Location loc, List<String> locList, boolean def) {
        return locationUtils.checkLocation(pluginName, loc, locList, def);
    }

    @Override
    public boolean checkBlocks(Location loc, List<String> blocksList, boolean def) {
        return blocksUtils.checkBlocks(loc, blocksList, def);
    }

    public ConditionTest getConditionTest() {
        return conditionTest;
    }

    @Override
    public Set<String> getRegisteredFlags() {
        return UtilsHandler.getDepend().getResidenceApi().getRegisteredFlags();
    }

    @Override
    public boolean isRegisteredFlag(String flag) {
        return UtilsHandler.getDepend().getResidenceApi().isRegisteredFlag(flag);
    }

    @Override
    public void registerFlag(String flag) {
        UtilsHandler.getDepend().getResidenceApi().registerFlag(flag);
    }

    @Override
    public boolean checkFlag(Player player, Location loc, String flag, boolean def) {
        return UtilsHandler.getDepend().getResidenceApi().checkFlag(player, loc, flag, def);
    }

    @Override
    public boolean checkFlag(Player player, Location loc, String flag, boolean def, boolean check) {
        return UtilsHandler.getDepend().getResidenceApi().checkFlag(player, loc, flag, def, check);
    }

    @Override
    public boolean checkFlag(Location loc, String flag, boolean def) {
        return UtilsHandler.getDepend().getResidenceApi().checkFlag(loc, flag, def);
    }

    @Override
    public boolean checkFlag(Location loc, String flag, boolean def, boolean check) {
        return UtilsHandler.getDepend().getResidenceApi().checkFlag(loc, flag, def, check);
    }

    @Override
    public boolean isInResidence(Location loc) {
        if (!UtilsHandler.getDepend().ResidenceEnabled()) {
            return false;
        }
        return UtilsHandler.getDepend().getResidenceApi().isInResidence(loc);
    }

    @Override
    public String getResidenceName(Location loc) {
        return UtilsHandler.getDepend().getResidenceApi().getResidenceName(loc);
    }

    @Override
    public boolean isMenuNode(String node) {
        if (node == null) {
            return false;
        }
        return ConfigHandler.getConfigPath().getMenuItemJoin().equals(node);
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
            if (menuName.equals("") || itemName.equals(UtilsHandler.getUtil().translateColorCode(menuName))) {
                if (itemType.equals("PLAYER_HEAD")) {
                    return isSkullTextures(itemStack, ConfigHandler.getConfigPath().getMenuSkullTextures());
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
    public ItemStack getMenuItemStack(Player player) {
        return UtilsHandler.getDepend().getItemJoinApi().getItemStack(player, ConfigHandler.getConfigPath().getMenuItemJoin());
    }

    @Override
    public ItemStack getItemJoinItemStack(Player player, String node) {
        return UtilsHandler.getDepend().getItemJoinApi().getItemStack(player, node);
    }

    @Override
    public String getItemNode(ItemStack itemStack) {
        return UtilsHandler.getDepend().getItemJoinApi().getItemNode(itemStack);
    }

    @Override
    public Set<String> getItemNodes() {
        return UtilsHandler.getDepend().getItemJoinApi().getItemNodes();
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
    public boolean isSkullTextures(ItemStack itemStack, String textures) {
        return getSkullValue(itemStack).equals(textures);
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
    public boolean isInCave(Location loc) {
        Location blockLoc;
        for (int x = -3; x <= 3; x++) {
            for (int z = -3; z <= 3; z++) {
                for (int y = -3; y <= 3; y++) {
                    blockLoc = loc.clone().add(x, y, z);
                    if (Material.CAVE_AIR.equals(blockLoc.getBlock().getType())) {
                        return true;
                    }
                }
            }
        }
        return false;
    }
}
