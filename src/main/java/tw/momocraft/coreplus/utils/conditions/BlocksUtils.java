package tw.momocraft.coreplus.utils.conditions;

import org.bukkit.Location;
import org.bukkit.configuration.ConfigurationSection;
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
            ignoreList = blocksMap.getIgnoreList();
            if (ignoreList == null || ignoreList.isEmpty()) {
                continue;
            }
            if (checkBlocks(loc, ignoreList, def)) {
                return false;
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
        }
    }

    public Map<String, BlocksMap> getBlocksMap() {
        return blocksProp;
    }

    private BlocksMap setBlocksMap(String group) {
        BlocksMap blocksMap = new BlocksMap();
        blocksMap.setBlockTypes(ConfigHandler.getConfigPath().getTypeList(ConfigHandler.getPrefix(),
                ConfigHandler.getConfig("config.yml").getStringList("General.Blocks." + group + ".Types"), "Materials"));
        blocksMap.setIgnoreList(ConfigHandler.getConfig("config.yml").getStringList("General.Blocks." + group + ".Ignore"));
        blocksMap.setS(ConfigHandler.getConfig("config.yml").getInt("General.Blocks." + group + ".Search.S"));
        blocksMap.setR(ConfigHandler.getConfig("config.yml").getInt("General.Blocks." + group + ".Search.R"));
        blocksMap.setY(ConfigHandler.getConfig("config.yml").getInt("General.Blocks." + group + ".Search.Y"));
        blocksMap.setV(ConfigHandler.getConfig("config.yml").getInt("General.Blocks." + group + ".Search.V"));
        return blocksMap;
    }

    private boolean getSearchBlocks(Location loc, BlocksMap blocksMap) {
        List<String> blockTypes = blocksMap.getBlockTypes();
        int range;
        if (blocksMap.getR() != 0) {
            range = blocksMap.getR();
        } else {
            range = blocksMap.getS();
        }
        int high;
        if (blocksMap.getV() != 0) {
            high = blocksMap.getV();
        } else {
            high = blocksMap.getY();
        }
        Location blockLoc;
        if (blocksMap.getR() != 0) {
            for (int x = -range; x <= range; x++) {
                for (int z = -range; z <= range; z++) {
                    if (blocksMap.getV() != 0) {
                        for (int y = -high; y <= high; y++) {
                            blockLoc = loc.clone().add(x, y, z);
                            if (blockTypes.contains(blockLoc.getBlock().getType().name())) {
                                return true;
                            }
                        }
                    } else {
                        blockLoc = loc.clone().add(x, high, z);
                        if (blockTypes.contains(blockLoc.getBlock().getType().name())) {
                            return true;
                        }
                    }
                }
            }
        } else {
            for (int x = -range; x <= range; x++) {
                for (int z = -range; z <= range; z++) {
                    if (blocksMap.getV() != 0) {
                        for (int y = -high; y <= high; y++) {
                            blockLoc = loc.clone().add(x, y, z);
                            if (blockTypes.contains(blockLoc.getBlock().getType().name())) {
                                return true;
                            }
                        }
                    } else {
                        blockLoc = loc.clone().add(x, high, z);
                        if (blockTypes.contains(blockLoc.getBlock().getType().name())) {
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }
}