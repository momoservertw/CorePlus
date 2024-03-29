package tw.momocraft.coreplus.utils.condition;

import org.bukkit.Location;
import tw.momocraft.coreplus.handlers.ConfigHandler;
import tw.momocraft.coreplus.handlers.UtilsHandler;

import java.util.List;

public class BlocksUtils {

    public boolean checkBlocks(Location loc, List<String> blocksList, boolean def) {
        if (blocksList == null || blocksList.isEmpty())
            return def;
        for (String group : blocksList)
            if (checkBlocks(loc, group, def))
                return true;
        return false;
    }

    public boolean checkBlocks(Location loc, String group, boolean def) {
        if (group == null)
            return def;
        BlocksMap blocksMap = ConfigHandler.getConfigPath().getBlocksProp().get(group);
        if (blocksMap == null)
            return false;
        List<String> ignoreList = blocksMap.getIgnoreList();
        if (ignoreList != null && !ignoreList.isEmpty()) {
            if (checkBlocks(loc, ignoreList, def))
                return false;
        }
        return getSearchBlocks(loc, blocksMap);
    }

    private boolean getSearchBlocks(Location loc, BlocksMap blocksMap) {
        List<String> blockTypes = blocksMap.getBlockTypes();
        String mode = blocksMap.getMode();
        if (mode.equals("Sphere")) {
            if (blocksMap.getR() == 0) {
                UtilsHandler.getMsg().sendErrorMsg(ConfigHandler.getPluginName(), "You need to set the value of \"R\" - Blocks: " + blocksMap.getGroupName());
                return false;
            }
            return checkSphere(loc, blocksMap.getR(), blockTypes);
        }
        if (blocksMap.getH() == 0) {
            switch (mode) {
                case "Cylinder":
                    if (blocksMap.getR() == 0) {
                        UtilsHandler.getMsg().sendErrorMsg(ConfigHandler.getPluginName(), "You need to set the value of \"R\" - Blocks: " + blocksMap.getGroupName());
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
                    UtilsHandler.getMsg().sendErrorMsg(ConfigHandler.getPluginName(), "You need to set the value of \"R\" - Blocks: " + blocksMap.getGroupName());
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
                    if (blockTypes.contains(blockLoc.getBlock().getType().name()))
                        return true;
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
                        if ((x * x + z * z + y * y) <= range)
                            return true;
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
                        if (blockTypes.contains(blockLoc.getBlock().getType().name()))
                            return true;
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
                if (blockTypes.contains(blockLoc.getBlock().getType().name()))
                    return true;
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
                    if (blockTypes.contains(blockLoc.getBlock().getType().name()))
                        return true;
                }
            }
        }
        return false;
    }
}