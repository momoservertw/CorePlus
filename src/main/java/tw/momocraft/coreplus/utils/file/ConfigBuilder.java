package tw.momocraft.coreplus.utils.file;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.data.BlockData;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.EntityType;
import tw.momocraft.coreplus.handlers.ConfigHandler;
import tw.momocraft.coreplus.handlers.UtilsHandler;
import tw.momocraft.coreplus.utils.file.maps.ConfigBuilderMap;

import java.util.*;

public class ConfigBuilder {

    public static void startGroups(CommandSender sender) {
        UtilsHandler.getCommandManager().dispatchLogGroup(ConfigHandler.getPluginName(),
                "GroupConfig, # ░░░░░░░░░░░░░░░   CorePlus, By Momocraft♥   ░░░░░░░░░░░░░░░░░░░");
        UtilsHandler.getCommandManager().dispatchLogGroup(ConfigHandler.getPluginName(),
                "GroupConfig, #  Spigot: https://www.spigotmc.org/resources/coreplus.86532/");
        UtilsHandler.getCommandManager().dispatchLogGroup(ConfigHandler.getPluginName(),
                "GroupConfig, #  Wiki: https://github.com/momoservertw/CorePlus/wiki/groups.yml");
        UtilsHandler.getCommandManager().dispatchLogGroup(ConfigHandler.getPluginName(),
                "GroupConfig, #");
        UtilsHandler.getCommandManager().dispatchLogGroup(ConfigHandler.getPluginName(),
                "GroupConfig, # Grouping of entities and materials.");
        UtilsHandler.getCommandManager().dispatchLogGroup(ConfigHandler.getPluginName(),
                "GroupConfig, # ░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░");
        UtilsHandler.getCommandManager().dispatchLogGroup(ConfigHandler.getPluginName(),
                "GroupConfig, Config-Version: 1");
        UtilsHandler.getCommandManager().dispatchLogGroup(ConfigHandler.getPluginName(),
                "GroupConfig, ");

        Map<String, List<ConfigBuilderMap>> map = ConfigHandler.getConfigPath().getConfigBuilderGroupProp();
        for (String type : map.keySet()) {
            UtilsHandler.getCommandManager().dispatchLogGroup(ConfigHandler.getPluginName(),
                    "GroupConfig, " + type + ":");
            for (ConfigBuilderMap configBuilderMap : map.get(type)) {
                switch (type.toLowerCase()) {
                    case "entities":
                        createEntity("GroupConfig", configBuilderMap);
                        continue;
                    case "materials":
                        createMaterial("GroupConfig", configBuilderMap);
                        continue;
                    default:
                        UtilsHandler.getMsg().sendMsg(ConfigHandler.getPrefix(), sender,
                                "&cCan not find the type: " + type);
                }
            }
        }
        UtilsHandler.getMsg().sendMsg(ConfigHandler.getPrefix(), sender,
                "&aSucceed to create the \"&egroup.yml&a\" in CorePlus/Logs");
    }

    public static void startCustom(CommandSender sender, String group) {
        ConfigBuilderMap configBuilderMap = ConfigHandler.getConfigPath().getConfigBuilderCustomProp().get(group);
        if (configBuilderMap == null) {
            UtilsHandler.getMsg().sendMsg(ConfigHandler.getPrefix(), sender,
                    "&cCan not find the group \"&e" + group + "\" in config.yml");
            return;
        }
        switch (configBuilderMap.getType().toLowerCase()) {
            case "entity":
                createEntity("CustomConfig", configBuilderMap);
                break;
            case "material":
                createMaterial("CustomConfig", configBuilderMap);
                break;
            default:
                UtilsHandler.getMsg().sendMsg(ConfigHandler.getPrefix(), sender,
                        "&cCan not find the group: " + group);
                break;
        }
        UtilsHandler.getMsg().sendMsg(ConfigHandler.getPrefix(), sender,
                "&aSucceed to create custom group \"" + group + "\" in CorePlus/Logs");
    }

    private static void createEntity(String logGroup, ConfigBuilderMap configBuilderMap) {
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
                // org.bukkit.entity.TYPE
                if (type.equals(entityType)) {
                    continue back;
                }
                // org.bukkit.entity.TYPE
                if (getClassesStringList(UtilsHandler.getUtil().getAllExtendedOrImplementedClass(value.getEntityClass()))
                        .contains("org.bukkit.entity." + type)) {
                    continue back;
                }
                // endsWith_zombie
                if (type.contains("endsWith_")) {
                    if (entityType.endsWith(type.replace("endsWith_", ""))) {
                        continue back;
                    }
                }
                // startsWith_zombie
                if (type.contains("startsWith_")) {
                    if (entityType.startsWith(type.replace("startsWith_", ""))) {
                        continue back;
                    }
                }
            }
            for (String type : set) {
                // org.bukkit.entity.TYPE
                if (type.equals(entityType)) {
                    valueSet.add(value.name());
                    continue back;
                }
                // org.bukkit.entity.TYPE
                if (getClassesStringList(UtilsHandler.getUtil().getAllExtendedOrImplementedClass(value.getEntityClass()))
                        .contains("org.bukkit.entity." + type)) {
                    valueSet.add(value.name());
                    continue back;
                }
                // endsWith_zombie
                if (type.contains("endsWith_")) {
                    if (entityType.endsWith(type.replace("endsWith_", ""))) {
                        valueSet.add(value.name());
                        continue back;
                    }
                }
                // startsWith_zombie
                if (type.contains("startsWith_")) {
                    if (entityType.startsWith(type.replace("startsWith_", ""))) {
                        valueSet.add(value.name());
                        continue back;
                    }
                }
            }
        }
        createLog(logGroup, configBuilderMap, valueSet);
    }

    private static void createMaterial(String logGroup, ConfigBuilderMap configBuilderMap) {
        Set<String> set = configBuilderMap.getSet();
        if (set == null) {
            return;
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
                UtilsHandler.getMsg().sendErrorMsg(ConfigHandler.getPluginName(),
                        "An error occurred while creating Material configuration.");
                UtilsHandler.getMsg().sendErrorMsg(ConfigHandler.getPluginName(),
                        "Please change the world or radius in config.yml Config-Builder settings.");
                return;
            }
        } catch (Exception ex) {
            UtilsHandler.getMsg().sendErrorMsg(ConfigHandler.getPluginName(),
                    "An error occurred while creating Material configuration.");
            UtilsHandler.getMsg().sendErrorMsg(ConfigHandler.getPluginName(),
                    "Please change the world or radius in config.yml Config-Builder settings.");
            return;
        }
        Set<String> valueSet = new HashSet<>();
        String materialType;
        BlockData blockData;
        Set<Class<?>> classSet;
        List<String> classSetStringList;
        back:
        for (Material value : Material.values()) {
            materialType = value.name();
            try {
                block.setType(value);
            } catch (Exception ex) {
                if (ignoreSet.contains(materialType) || ignoreSet.contains("items")) {
                    continue;
                }
                if (set.contains("all") || set.contains("items")) {
                    valueSet.add(materialType);
                    continue;
                }
                if (set.contains(materialType)) {
                    valueSet.add(materialType);
                    continue;
                }
                continue;
            }
            if (ignoreSet.contains("blocks")) {
                continue;
            }
            blockData = block.getBlockData();
            classSet = UtilsHandler.getUtil().getAllExtendedOrImplementedClass(blockData.getClass());
            classSet.addAll(UtilsHandler.getUtil().getAllExtendedOrImplementedClass(block.getState().getClass()));
            classSetStringList = getClassesStringList(classSet);
            for (String type : ignoreSet) {
                // org.bukkit.block.TYPE
                if (type.equals(materialType)) {
                    continue back;
                }
                // org.bukkit.block.TYPE
                if (classSetStringList.contains("org.bukkit.block." + type)) {
                    continue back;
                }
                // org.bukkit.block.data.Powerable
                if (classSetStringList.contains("org.bukkit.block.data." + type)) {
                    continue back;
                }
                // org.bukkit.block.data.type.chest
                if (classSetStringList.contains("org.bukkit.block.data.type." + type)) {
                    continue back;
                }
                // org.bukkit.block.data.*
                if (type.equals("inherited") && // org.bukkit.block.data.BlockData
                        UtilsHandler.getUtil().containsColorCode(classSetStringList, "org.bukkit.block." + "((?!(data.BlockData|BlockState)).)*")) {
                    continue back;
                }
                // org.bukkit.block.* && !org.bukkit.block.data.* = not "org.bukkit.block.BlockData"
                if (type.equals("not_inherited") &&
                        !UtilsHandler.getUtil().containsColorCode(classSetStringList, "org.bukkit.block." + "((?!(data.BlockData|BlockState)).)*")) {
                    continue back;
                }
                // endsWith_logs
                if (type.contains("endsWith_")) {
                    if (materialType.endsWith(type.replace("endsWith_", ""))) {
                        continue back;
                    }
                }
                // startsWith_stone
                if (type.contains("startsWith_")) {
                    if (materialType.startsWith(type.replace("startsWith_", ""))) {
                        valueSet.add(value.name());
                        continue back;
                    }
                }
            }
            if (set.contains("all")) {
                valueSet.add(value.name());
                continue;
            } else if (set.contains("blocks")) {
                valueSet.add(materialType);
                continue;
            }
            for (String type : set) {
                // org.bukkit.block.TYPE
                if (type.equals(materialType)) {
                    valueSet.add(value.name());
                    continue back;
                }
                // org.bukkit.block.data.Type
                if (classSetStringList.contains("org.bukkit.block." + type)) {
                    valueSet.add(value.name());
                    continue back;
                }
                // org.bukkit.block.data.Powerable
                if (classSetStringList.contains("org.bukkit.block.data." + type)) {
                    valueSet.add(value.name());
                    continue back;
                }
                // org.bukkit.block.data.type.chest
                if (classSetStringList.contains("org.bukkit.block.data.type." + type)) {
                    valueSet.add(value.name());
                    continue back;
                }
                // org.bukkit.block.data.*
                if (type.equals("inherited") &&
                        UtilsHandler.getUtil().containsColorCode(classSetStringList, "org.bukkit.block." + "((?!(data.BlockData|BlockState)).)*")) {
                    valueSet.add(value.name());
                    continue back;
                }
                // org.bukkit.block.* && !org.bukkit.block.data.* = not "org.bukkit.block.BlockData"
                if (type.equals("not_inherited") &&
                        !UtilsHandler.getUtil().containsColorCode(classSetStringList, "org.bukkit.block." + "((?!(data.BlockData|BlockState)).)*")) {
                    valueSet.add(value.name());
                    continue back;
                }
                // endsWith_logs
                if (type.contains("endsWith_")) {
                    if (materialType.endsWith(type.replace("endsWith_", ""))) {
                        valueSet.add(value.name());
                        continue back;
                    }
                }
                // startsWith_stone
                if (type.contains("startsWith_")) {
                    if (materialType.startsWith(type.replace("startsWith_", ""))) {
                        valueSet.add(value.name());
                        continue back;
                    }
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

    private static void createLog(String logGroup, ConfigBuilderMap configBuilderMap, Set<String> valueSet) {
        String title = configBuilderMap.getTitle().replace("%title%", configBuilderMap.getGroupName());
        if (valueSet.isEmpty()) {
            UtilsHandler.getCommandManager().dispatchLogGroup(ConfigHandler.getPluginName(),
                    logGroup + ", " +
                            UtilsHandler.getMsg().transByGeneral(ConfigHandler.getPluginName(), null, title) + " []");
            return;
        }
        UtilsHandler.getCommandManager().dispatchLogGroup(ConfigHandler.getPluginName(),
                logGroup + ", " +
                        UtilsHandler.getMsg().transByGeneral(ConfigHandler.getPluginName(), null, title));
        StringBuilder output = new StringBuilder();
        String line = configBuilderMap.getValue();
        String split = configBuilderMap.getSplit();

        List<String> sortedList = new ArrayList<>(valueSet);
        Collections.sort(sortedList);
        if (configBuilderMap.isRowLine()) {
            for (String value : sortedList) {
                output = new StringBuilder(line.replace("%value%", value));
                UtilsHandler.getCommandManager().dispatchLogGroup(ConfigHandler.getPluginName(),
                        logGroup + ", " +
                                UtilsHandler.getMsg().transByGeneral(ConfigHandler.getPluginName(),
                                        null, output.toString()));
            }
        } else {
            for (String value : valueSet) {
                output.append(split.replace("%value%", value)).append(split);
            }
            output.substring(0, output.length() - split.length());
            UtilsHandler.getCommandManager().dispatchLogGroup(ConfigHandler.getPluginName(),
                    logGroup + ", " +
                            UtilsHandler.getMsg().transByGeneral(ConfigHandler.getPluginName(),
                                    null, output.toString()));
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
                UtilsHandler.getCustomCommands().dispatchLogCustomCmd(ConfigHandler.getPlugin(),
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
                UtilsHandler.getCustomCommands().dispatchLogCustomCmd(ConfigHandler.getPlugin(),
                        "ConfigBuilder, " + input);
            } catch (Exception ignored) {
            }
        }
    }

     */
}
