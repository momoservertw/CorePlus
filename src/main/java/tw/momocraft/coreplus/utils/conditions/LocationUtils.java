package tw.momocraft.coreplus.utils.conditions;

import org.bukkit.Location;
import tw.momocraft.coreplus.handlers.ConfigHandler;
import tw.momocraft.coreplus.handlers.UtilsHandler;

import java.util.List;
import java.util.Map;

public class LocationUtils {

    public boolean checkLocation(String pluginName, Location loc, List<String> locMaps, boolean def) {
        if (locMaps == null || locMaps.isEmpty())
            return def;
        try {
            for (String group : locMaps)
                if (checkLocation(pluginName, loc, group, def))
                    return true;
        } catch (Exception ex) {
            UtilsHandler.getLang().sendErrorMsg(pluginName, "An error occurred while checking location.");
            UtilsHandler.getLang().sendErrorMsg(pluginName, "If this error keeps happening, please contact the plugin author.");
            UtilsHandler.getLang().sendDebugTrace(true, ConfigHandler.getPluginName(), ex);
        }
        return false;
    }

    public boolean checkLocation(String pluginName, Location loc, String group, boolean def) {
        if (group == null)
            return def;
        try {
            String worldName = loc.getWorld().getName();
            if (group.equals(worldName))
                return true;
            LocationMap locMap = ConfigHandler.getConfigPath().getLocProp().get(group);
            if (locMap == null)
                return false;
            if (locMap.getWorlds() == null || locMap.getWorlds().isEmpty() || locMap.getWorlds().contains(worldName)) {
                Map<String, String> cord = locMap.getCord();
                if (cord != null) {
                    for (String key : cord.keySet())
                        if (!isCord(loc, key, cord.get(key)))
                            return false;
                }
                return true;
            }
        } catch (Exception ex) {
            UtilsHandler.getLang().sendErrorMsg(pluginName, "An error occurred while checking location.");
            UtilsHandler.getLang().sendErrorMsg(pluginName, "If this error keeps happening, please contact the plugin author.");
            UtilsHandler.getLang().sendDebugTrace(true, ConfigHandler.getPluginName(), ex);
        }
        return false;
    }

    private boolean isCord(Location loc, String type, String value) {
        String[] values = value.split("\\s+");
        int length = values.length;
        try {
            switch (length) {
                case 1:
                    // X: 1000
                    // R: 1000
                    switch (type) {
                        case "X":
                            return UtilsHandler.getUtil().getRange(loc.getBlockX(), Integer.parseInt(values[0]), true);
                        case "Y":
                            return UtilsHandler.getUtil().getRange(loc.getBlockY(), Integer.parseInt(values[0]), true);
                        case "Z":
                            return UtilsHandler.getUtil().getRange(loc.getBlockZ(), Integer.parseInt(values[0]), true);
                        case "!X":
                            return !UtilsHandler.getUtil().getRange(loc.getBlockX(), Integer.parseInt(values[0]), true);
                        case "!Y":
                            return !UtilsHandler.getUtil().getRange(loc.getBlockY(), Integer.parseInt(values[0]), true);
                        case "!Z":
                            return !UtilsHandler.getUtil().getRange(loc.getBlockZ(), Integer.parseInt(values[0]), true);
                        case "R":
                            return getRound(loc, Integer.parseInt(values[0]));
                        case "!R":
                            return !getRound(loc, Integer.parseInt(values[0]));
                        case "S":
                            return getSquared(loc, Integer.parseInt(values[0]));
                        case "!S":
                            return !getSquared(loc, Integer.parseInt(values[0]));
                    }
                    break;
                case 2:
                    // X: ">= 1000"
                    switch (type) {
                        case "X":
                            return UtilsHandler.getUtil().getCompare(values[0], loc.getBlockX(), Integer.parseInt(values[1]));
                        case "Y":
                            return UtilsHandler.getUtil().getCompare(values[0], loc.getBlockY(), Integer.parseInt(values[1]));
                        case "Z":
                            return UtilsHandler.getUtil().getCompare(values[0], loc.getBlockZ(), Integer.parseInt(values[1]));
                        case "!X":
                            return !UtilsHandler.getUtil().getCompare(values[0], loc.getBlockX(), Integer.parseInt(values[1]));
                        case "!Y":
                            return !UtilsHandler.getUtil().getCompare(values[0], loc.getBlockY(), Integer.parseInt(values[1]));
                        case "!Z":
                            return !UtilsHandler.getUtil().getCompare(values[0], loc.getBlockZ(), Integer.parseInt(values[1]));
                    }
                    break;
                case 3:
                    // X: "-1000 ~ 1000"
                    // R: "1000 0 0"
                    switch (type) {
                        case "X":
                            return UtilsHandler.getUtil().getRange(loc.getBlockX(), Integer.parseInt(values[0]), Integer.parseInt(values[2]), true);
                        case "Y":
                            return UtilsHandler.getUtil().getRange(loc.getBlockY(), Integer.parseInt(values[0]), Integer.parseInt(values[2]), true);
                        case "Z":
                            return UtilsHandler.getUtil().getRange(loc.getBlockZ(), Integer.parseInt(values[0]), Integer.parseInt(values[2]), true);
                        case "!X":
                            return !UtilsHandler.getUtil().getRange(loc.getBlockX(), Integer.parseInt(values[0]), Integer.parseInt(values[2]), true);
                        case "!Y":
                            return !UtilsHandler.getUtil().getRange(loc.getBlockY(), Integer.parseInt(values[0]), Integer.parseInt(values[2]), true);
                        case "!Z":
                            return !UtilsHandler.getUtil().getRange(loc.getBlockZ(), Integer.parseInt(values[0]), Integer.parseInt(values[2]), true);
                        case "R":
                            return getRound(loc, Integer.parseInt(values[0]), Integer.parseInt(values[1]), Integer.parseInt(values[2]));
                        case "!R":
                            return !getRound(loc, Integer.parseInt(values[0]), Integer.parseInt(values[1]), Integer.parseInt(values[2]));
                        case "S":
                            return getSquared(loc, Integer.parseInt(values[0]), Integer.parseInt(values[1]), Integer.parseInt(values[2]));
                        case "!S":
                            return !getSquared(loc, Integer.parseInt(values[0]), Integer.parseInt(values[1]), Integer.parseInt(values[2]));
                    }
                case 4:
                    // X: "-1000 ~ 1000"
                    // R: "1000 0 0"
                    switch (type) {
                        case "R":
                            return getRound(loc, Integer.parseInt(values[0]), Integer.parseInt(values[1]), Integer.parseInt(values[2]), Integer.parseInt(values[3]));
                        case "!R":
                            return !getRound(loc, Integer.parseInt(values[0]), Integer.parseInt(values[1]), Integer.parseInt(values[2]), Integer.parseInt(values[3]));
                        case "S":
                            return getSquared(loc, Integer.parseInt(values[0]), Integer.parseInt(values[1]), Integer.parseInt(values[2]), Integer.parseInt(values[3]));
                        case "!S":
                            return !getSquared(loc, Integer.parseInt(values[0]), Integer.parseInt(values[1]), Integer.parseInt(values[2]), Integer.parseInt(values[3]));
                    }
            }
        } catch (Exception ex) {
            UtilsHandler.getLang().sendErrorMsg(ConfigHandler.getPluginName(), "There is an error occurred. Please check the \"Location\" format.");
            UtilsHandler.getLang().sendErrorMsg(ConfigHandler.getPluginName(), type + ": " + value);
        }
        return false;
    }

    /**
     * @param loc location.
     * @param r   the checking radius.
     * @param x   the center checking X.
     * @param y   the center checking Y
     * @param z   the center checking Z
     * @return if the entity spawn in three-dimensional radius.
     */
    private boolean getRound(Location loc, int r, int x, int y, int z) {
        x = loc.getBlockX() - x;
        y = loc.getBlockY() - y;
        z = loc.getBlockZ() - z;
        return r > Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2) + Math.pow(z, 2));
    }

    /**
     * @param loc location.
     * @param r   the checking radius.
     * @param x   the center checking X.
     * @param z   the center checking Z
     * @return if the entity spawn in flat radius.
     */
    private boolean getRound(Location loc, int r, int x, int z) {
        x = loc.getBlockX() - x;
        z = loc.getBlockZ() - z;
        return r > Math.sqrt(Math.pow(x, 2) + Math.pow(z, 2));
    }

    /**
     * @param loc location.
     * @param r   the checking radius.
     * @return if the entity spawn in flat radius.
     */
    private boolean getRound(Location loc, int r) {
        int x = loc.getBlockX();
        int z = loc.getBlockZ();
        return r > Math.sqrt(Math.pow(x, 2) + Math.pow(z, 2));
    }

    /**
     * @param loc location.
     * @param r   the checking radius.
     * @param x   the center checking X.
     * @param y   the center checking Y
     * @param z   the center checking Z
     * @return if the entity spawn in three-dimensional radius.
     */
    private boolean getSquared(Location loc, int r, int x, int y, int z) {
        return r > loc.getBlockX() - x && r > loc.getBlockY() - y && r > loc.getBlockZ() - z;
    }

    /**
     * @param loc location.
     * @param r   the checking radius.
     * @param x   the center checking X.
     * @param z   the center checking Z
     * @return if the entity spawn in flat radius.
     */
    private boolean getSquared(Location loc, int r, int x, int z) {
        return r > loc.getBlockX() - x && r > loc.getBlockZ() - z;
    }

    /**
     * @param loc location.
     * @param r   the checking radius.
     * @return if the entity spawn in flat radius.
     */
    private boolean getSquared(Location loc, int r) {
        return r > loc.getBlockX() && r > loc.getBlockZ();
    }
}