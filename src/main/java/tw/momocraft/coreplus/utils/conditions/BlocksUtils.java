package tw.momocraft.coreplus.utils.conditions;

import org.bukkit.Location;
import org.bukkit.configuration.ConfigurationSection;
import tw.momocraft.coreplus.api.CorePlusAPI;
import tw.momocraft.coreplus.handlers.ConfigHandler;
import tw.momocraft.coreplus.handlers.UtilsHandler;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BlocksUtils {

    private final Map<String, BlocksMap> blocksProp = new HashMap<>();

    public BlocksUtils() {
        setUp();
    }

    public boolean checkBlocks(Location loc, List<String> blocksList, boolean def) {
        if (blocksList == null || blocksList.isEmpty()) {
            return def;
        }
        List<String> ignoreList;
        BlocksMap blocksMap;
        for (String group : blocksList) {
            blocksMap = blocksProp.get(group);
            if (blocksMap == null) {
                continue;
            }
            ignoreList = blocksMap.getIgnoreList();
            if (ignoreList != null && !ignoreList.isEmpty()) {
                if (checkBlocks(loc, ignoreList, def)) {
                    continue;
                }
            }
            if (getSearchBlocks(loc, blocksMap)) {
                return true;
            }
        }
        return false;
    }

    private void setUp() {
        ConfigurationSection locConfig = ConfigHandler.getConfig("config.yml").getConfigurationSection("General.Blocks");
        if (locConfig == null) {
            return;
        }
        ConfigurationSection groupConfig;
        for (String group : locConfig.getKeys(false)) {
            groupConfig = ConfigHandler.getConfig("config.yml").getConfigurationSection("General.Blocks." + group);
            if (groupConfig == null) {
                UtilsHandler.getLang().sendErrorMsg(ConfigHandler.getPlugin(), "&cCan not set the Blocks: " + group);
                continue;
            }
            blocksProp.put(group, setBlocksMap(group));
            CorePlusAPI.getLangManager().sendFeatureMsg(ConfigHandler.isDebugging(), ConfigHandler.getPlugin(), "Blocks", group, "setup", "continue",
                    new Throwable().getStackTrace()[0]);
        }
    }

    public Map<String, BlocksMap> getBlocksMap() {
        return blocksProp;
    }

    private BlocksMap setBlocksMap(String group) {
        BlocksMap blocksMap = new BlocksMap();
        blocksMap.setGroupName(group);
        blocksMap.setBlockTypes(ConfigHandler.getConfigPath().getTypeList(ConfigHandler.getPrefix(),
                ConfigHandler.getConfig("config.yml").getStringList("General.Blocks." + group + ".Types"), "Materials"));
        blocksMap.setIgnoreList(ConfigHandler.getConfig("config.yml").getStringList("General.Blocks." + group + ".Ignore"));
        int r = ConfigHandler.getConfig("config.yml").getInt("General.Blocks." + group + ".Search.Values.R");
        blocksMap.setR(r);
        blocksMap.setX(ConfigHandler.getConfig("config.yml").getInt("General.Blocks." + group + ".Search.Values.X", r));
        int y = ConfigHandler.getConfig("config.yml").getInt("General.Blocks." + group + ".Search.Values.Y", r);
        blocksMap.setY(y);
        blocksMap.setZ(ConfigHandler.getConfig("config.yml").getInt("General.Blocks." + group + ".Search.Values.Z", r));
        blocksMap.setH(ConfigHandler.getConfig("config.yml").getInt("General.Blocks." + group + ".Search.Values.H"));
        blocksMap.setMode(ConfigHandler.getConfig("config.yml").getString("General.Blocks." + group + ".Search.Mode", "Cuboid"));
        return blocksMap;
    }

    private boolean getSearchBlocks(Location loc, BlocksMap blocksMap) {
        List<String> blockTypes = blocksMap.getBlockTypes();
        String mode = blocksMap.getMode();
        if (mode.equals("Sphere")) {
            if (blocksMap.getR() == 0) {
                UtilsHandler.getLang().sendErrorMsg(ConfigHandler.getPlugin(), "You need to set the value of \"R\" - Blocks: " + blocksMap.getGroupName());
                return false;
            }
            return checkSphere(loc, blocksMap.getR(), blockTypes);
        }
        if (blocksMap.getH() == 0) {
            switch (mode) {
                case "Cylinder":
                    if (blocksMap.getR() == 0) {
                        UtilsHandler.getLang().sendErrorMsg(ConfigHandler.getPlugin(), "You need to set the value of \"R\" - Blocks: " + blocksMap.getGroupName());
                        return false;
                    }
                    return checkCylinder(loc, blocksMap.getR(), blocksMap.getY(), blockTypes);
                case "Cuboid":
                default:
                    return checkCuboid(loc, blocksMap.getX(), blocksMap.getZ(), blocksMap.getY(), blockTypes);
            }
        }
        switch (mode) {
            case "Cylinder":
                if (blocksMap.getR() == 0) {
                    UtilsHandler.getLang().sendErrorMsg(ConfigHandler.getPlugin(), "You need to set the value of \"R\" - Blocks: " + blocksMap.getGroupName());
                    return false;
                }
                return checkRound(loc, blocksMap.getR(), blocksMap.getH(), blockTypes);
            case "Cuboid":
            default:
                return checkRectangle(loc, blocksMap.getX(), blocksMap.getZ(), blocksMap.getH(), blockTypes);
        }
    }

    private boolean checkCuboid(Location loc, int X, int Z, int Y, List<String> blockTypes) {
        Location blockLoc;
        for (int x = -X; x <= X; x++) {
            for (int z = -Z; z <= Z; z++) {
                for (int y = -Y; y <= Y; y++) {
                    blockLoc = loc.clone().add(x, y, z);
                    if (blockTypes.contains(blockLoc.getBlock().getType().name())) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    private boolean checkSphere(Location loc, int R, List<String> blockTypes) {
        int range = R * R;
        Location blockLoc;
        for (int x = -R; x <= R; x++) {
            for (int z = -R; z <= R; z++) {
                for (int y = -R; y <= R; y++) {
                    blockLoc = loc.clone().add(x, y, z);
                    if (blockTypes.contains(blockLoc.getBlock().getType().name())) {
                        if ((x * x + z * z + y * y) <= range) {
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

    private boolean checkCylinder(Location loc, int R, int H, List<String> blockTypes) {
        int range = R * R;
        Location blockLoc;
        for (int x = -R; x <= R; x++) {
            for (int z = -R; z <= R; z++) {
                if (x * x + z * z <= range) {
                    for (int y = -H; y <= H; y++) {
                        blockLoc = loc.clone().add(x, y, z);
                        if (blockTypes.contains(blockLoc.getBlock().getType().name())) {
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

    private boolean checkRectangle(Location loc, int X, int Z, int H, List<String> blockTypes) {
        Location blockLoc;
        for (int x = -X; x <= X; x++) {
            for (int z = -Z; z <= Z; z++) {
                blockLoc = loc.clone().add(x, H, z);
                if (blockTypes.contains(blockLoc.getBlock().getType().name())) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean checkRound(Location loc, int R, int H, List<String> blockTypes) {
        int range = R * R;
        Location blockLoc;
        for (int x = -R; x <= R; x++) {
            for (int z = -R; z <= R; z++) {
                if (x * x + z * z <= range) {
                    blockLoc = loc.clone().add(x, H, z);
                    if (blockTypes.contains(blockLoc.getBlock().getType().name())) {
                        return true;
                    }
                }
            }
        }
        return false;
    }
}