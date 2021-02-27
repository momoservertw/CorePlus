package tw.momocraft.coreplus.utils.files;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.data.BlockData;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.EntityType;
import tw.momocraft.coreplus.handlers.ConfigHandler;
import tw.momocraft.coreplus.handlers.UtilsHandler;

import java.util.*;

public class ConfigBuilder {

    public static void startGroups(CommandSender sender) {
        Map<String, List<ConfigBuilderMap>> map = ConfigHandler.getConfigPath().getConfigBuilderGroupProp();
        for (String type : map.keySet()) {
            UtilsHandler.getCustomCommands().dispatchLogCustomCmd(ConfigHandler.getPluginName(),
                    "GroupConfig, " + type + ":");
            for (ConfigBuilderMap configBuilderMap : map.get(type)) {
                switch (type.toLowerCase()) {
                    case "entities":
                        createEntity("groups", configBuilderMap);
                        continue;
                    case "materials":
                        createMaterial("groups", configBuilderMap);
                        continue;
                    /*
                case "mythicmobs":
                    createMythicMobs("groups", configBuilderMap);
                    continue;

                     */
                    default:
                        UtilsHandler.getLang().sendMsg(ConfigHandler.getPrefix(), sender,
                                "&cCan not find the type: " + type);
                }
            }
        }
        UtilsHandler.getLang().sendMsg(ConfigHandler.getPrefix(), sender,
                "&aSucceed to create the \"&egroup.yml&a\" in CorePlus/Logs");
    }

    public static void startCustom(CommandSender sender, String group) {
        ConfigBuilderMap configBuilderMap = ConfigHandler.getConfigPath().getConfigBuilderCustomProp().get(group);
        if (configBuilderMap == null) {
            UtilsHandler.getLang().sendMsg(ConfigHandler.getPrefix(), sender,
                    "&cCan not find the group \"&e" + group + "\" in config.yml");
            return;
        }
        switch (configBuilderMap.getType().toLowerCase()) {
            case "entity":
                createEntity("custom", configBuilderMap);
                break;
            case "material":
                createMaterial("custom", configBuilderMap);
                break;
                    /*
                case "mythicmobs":
                    createMythicMobs("groups", configBuilderMap);
                    break;
                     */
            default:
                UtilsHandler.getLang().sendMsg(ConfigHandler.getPrefix(), sender,
                        "&cCan not find the group: " + group);
                break;
        }
        UtilsHandler.getLang().sendMsg(ConfigHandler.getPrefix(), sender,
                "&aSucceed to create custom group \"" + group + "\" in CorePlus/Logs");
    }

    private static void createEntity(String fileType, ConfigBuilderMap configBuilderMap) {
        if (fileType.equals("groups")) {
            fileType = "GroupConfig";
        } else {
            fileType = "CustomConfig";
        }
        Set<String> set = configBuilderMap.getSet();
        if (set == null) {
            return;
        }
        if (set.contains("all")) {
            set.clear();
            for (EntityType value : EntityType.values()) {
                set.add(value.name());
            }
        }
        Set<String> ignoreSet = configBuilderMap.getIgnoreSet();

        String entityType;
        Set<String> valueSet = new HashSet<>();
        back:
        for (EntityType value : EntityType.values()) {
            if (value.equals(EntityType.UNKNOWN))
                continue;
            entityType = value.name();
            for (String type : ignoreSet) {
                if (type.equalsIgnoreCase(entityType)) {
                    continue back;
                }
                if (UtilsHandler.getUtil().containsIgnoreCase(
                        getClassesStringList(UtilsHandler.getUtil().getAllExtendedOrImplementedClass(value.getEntityClass())),
                        "org.bukkit.entity." + type)) {
                    continue back;
                }
            }
            for (String type : set) {
                if (type.equalsIgnoreCase(entityType)) {
                    valueSet.add(value.name());
                    continue back;
                }
                if (UtilsHandler.getUtil().containsIgnoreCase(
                        getClassesStringList(UtilsHandler.getUtil().getAllExtendedOrImplementedClass(value.getEntityClass())),
                        "org.bukkit.entity." + type)) {
                    valueSet.add(value.name());
                    continue back;
                }
            }
        }
        createLog(fileType, configBuilderMap, valueSet);
    }

    private static void createMaterial(String logGroup, ConfigBuilderMap configBuilderMap) {
        if (logGroup.equals("groups")) {
            logGroup = "GroupConfig";
        } else {
            logGroup = "CustomConfig";
        }
        Set<String> set = configBuilderMap.getSet();
        if (set == null) {
            return;
        }
        if (set.contains("all")) {
            set.clear();
            for (Material value : Material.values()) {
                set.add(value.name());
            }
        }
        Set<String> ignoreSet = configBuilderMap.getIgnoreSet();
        Block block = null;
        World world = Bukkit.getServer().getWorld(ConfigHandler.getConfigPath().getConfigBlockWorld());
        int radius = ConfigHandler.getConfigPath().getConfigBlockRadius();
        try {
            back:
            for (int x = -radius; x <= radius; x++) {
                for (int z = -radius; z <= radius; z++) {
                    for (int y = 255; y > 0; y--) {
                        block = world.getBlockAt(x, y, z);
                        if (block.getType().equals(Material.AIR)) {
                            break back;
                        }
                    }
                }
            }
            if (!block.getType().equals(Material.AIR)) {
                UtilsHandler.getLang().sendErrorMsg(ConfigHandler.getPluginName(), "An error occurred while creating Material configuration.");
                UtilsHandler.getLang().sendErrorMsg(ConfigHandler.getPluginName(), "Please change the world or radius in config.yml Config-Builder settings.");
                return;
            }
        } catch (Exception ex) {
            UtilsHandler.getLang().sendErrorMsg(ConfigHandler.getPluginName(), "An error occurred while creating Material configuration.");
            UtilsHandler.getLang().sendErrorMsg(ConfigHandler.getPluginName(), "Please change the world or radius in config.yml Config-Builder settings.");
            return;
        }
        Set<String> valueSet = new HashSet<>();
        String materialType;
        BlockData blockData;
        Set<Class<?>> classSet;
        back:
        for (Material value : Material.values()) {
            materialType = value.name();
            try {
                block.setType(value);
            } catch (Exception ex) {
                if (UtilsHandler.getUtil().containsIgnoreCase(ignoreSet, materialType)) {
                    valueSet.add(materialType);
                    continue;
                }
                if (UtilsHandler.getUtil().containsIgnoreCase(set, materialType)) {
                    valueSet.add(materialType);
                    continue;
                }
                continue;
            }
            blockData = block.getBlockData();
            classSet = UtilsHandler.getUtil().getAllExtendedOrImplementedClass(blockData.getClass());
            classSet.addAll(UtilsHandler.getUtil().getAllExtendedOrImplementedClass(block.getState().getClass()));
            for (String type : ignoreSet) {
                if (type.equalsIgnoreCase(materialType)) {
                    continue back;
                }
                if (UtilsHandler.getUtil().containsIgnoreCase(getClassesStringList(classSet),
                        "org.bukkit.block." + type)) {
                    continue back;
                }
                if (UtilsHandler.getUtil().containsIgnoreCase(getClassesStringList(classSet),
                        // org.bukkit.block.data.Powerable
                        "org.bukkit.block.data." + type)) {
                    continue back;
                }
                if (UtilsHandler.getUtil().containsIgnoreCase(getClassesStringList(classSet),
                        "org.bukkit.block.data.type." + type)) {
                    continue back;
                }
            }
            for (String type : set) {
                if (type.equalsIgnoreCase(materialType)) {
                    valueSet.add(value.name());
                    continue back;
                }
                if (UtilsHandler.getUtil().containsIgnoreCase(getClassesStringList(classSet),
                        "org.bukkit.block." + type)) {
                    valueSet.add(value.name());
                    continue back;
                }
                if (UtilsHandler.getUtil().containsIgnoreCase(getClassesStringList(classSet),
                        "org.bukkit.block.data." + type)) {
                    valueSet.add(value.name());
                    continue back;
                }
                if (UtilsHandler.getUtil().containsIgnoreCase(getClassesStringList(classSet),
                        "org.bukkit.block.data.type." + type)) {
                    valueSet.add(value.name());
                    continue back;
                }
            }
        }
        createLog(logGroup, configBuilderMap, valueSet);
    }


    private static List<String> getClassesStringList(Set<Class<?>> classSet) {
        List<String> list = new ArrayList<>();
        for (Class<?> clazz : classSet) {
            list.add(clazz.getName());
        }
        return list;
    }

    private static void createLog(String logGrop, ConfigBuilderMap configBuilderMap, Set<String> valueSet) {
        String title = configBuilderMap.getTitle().replace("%title%", configBuilderMap.getGroup());
        if (valueSet.isEmpty()) {
            UtilsHandler.getCustomCommands().dispatchLogCustomCmd(ConfigHandler.getPluginName(),
                    UtilsHandler.getLang().transByGeneral(ConfigHandler.getPluginName(), null, title));
            return;
        }
        UtilsHandler.getCustomCommands().dispatchLogCustomCmd(ConfigHandler.getPluginName(),
        logGrop + ", " +
                UtilsHandler.getLang().transByGeneral(ConfigHandler.getPluginName(), null, title));
        StringBuilder output = new StringBuilder();
        String line = configBuilderMap.getValue();
        String split = configBuilderMap.getSplit();

        List<String> sortedList = new ArrayList<>(valueSet);
        Collections.sort(sortedList);
        if (configBuilderMap.isRowLine()) {
            for (String value : sortedList) {
                output = new StringBuilder(line.replace("%value%", value));
                UtilsHandler.getCustomCommands().dispatchLogCustomCmd(ConfigHandler.getPluginName(),
                        logGrop + ", " +
                                UtilsHandler.getLang().transByGeneral(ConfigHandler.getPluginName(), null, output.toString()));
            }
        } else {
            String spilt = configBuilderMap.getSplit();
            for (String value : valueSet) {
                output.append(split.replace("%value%", value)).append(spilt);
            }
            output.substring(0, output.length() - spilt.length());
            UtilsHandler.getCustomCommands().dispatchLogCustomCmd(ConfigHandler.getPluginName(),
                    logGrop + ", " +
                            UtilsHandler.getLang().transByGeneral(ConfigHandler.getPluginName(), null, output.toString()));
        }
    }

    /*
    public static void createOfflinePlayerName() {
        String format = ConfigHandler.getConfigPath().getConfigBuilderGroupProp().get("OfflinePlayer-Name").getKey();
        if (format == null)
            return;
        List<String> ignoreList = ConfigHandler.getConfigPath().getConfigBuilderGroupProp().get("OfflinePlayer-Name").getValue();
        String input;
        String uuid;
        String name;
        for (OfflinePlayer offlinePlayer : Bukkit.getOfflinePlayers()) {
            try {
                uuid = offlinePlayer.getUniqueId().toString();
                name = offlinePlayer.getName();
                if (ignoreList.contains(uuid) || ignoreList.contains(name))
                    continue;
                input = format.replace("%type%", name);
                UtilsHandler.getCustomCommands().dispatchLogCustomCmd(ConfigHandler.getPluginName(),
                        "ConfigBuilder, " + input);
            } catch (Exception ignored) {
            }
        }
    }

    public static void createOfflinePlayerUUID() {
        String format = ConfigHandler.getConfigPath().getConfigBuilderGroupProp().get("OfflinePlayer-UUID").getKey();
        if (format == null)
            return;
        List<String> ignoreList = ConfigHandler.getConfigPath().getConfigBuilderGroupProp().get("OfflinePlayer-UUID").getValue();
        String input;
        String uuid;
        String name;
        for (OfflinePlayer offlinePlayer : Bukkit.getOfflinePlayers()) {
            try {
                uuid = offlinePlayer.getUniqueId().toString();
                name = offlinePlayer.getName();
                if (ignoreList.contains(uuid) || ignoreList.contains(name))
                    continue;
                input = format.replace("%type%", uuid);
                UtilsHandler.getCustomCommands().dispatchLogCustomCmd(ConfigHandler.getPluginName(),
                        "ConfigBuilder, " + input);
            } catch (Exception ignored) {
            }
        }
    }

     */
}
