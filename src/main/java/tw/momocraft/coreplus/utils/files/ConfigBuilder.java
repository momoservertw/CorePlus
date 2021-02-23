package tw.momocraft.coreplus.utils.files;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.EntityType;
import tw.momocraft.coreplus.handlers.ConfigHandler;
import tw.momocraft.coreplus.handlers.UtilsHandler;

import java.util.*;

public class ConfigBuilder {

    public static void startGroups(CommandSender sender) {
        Map<String, ConfigBuilderMap> map = ConfigHandler.getConfigPath().getConfigBuilderGroupProp();
        ConfigBuilderMap configBuilderMap;
        for (String type : map.keySet()) {
            configBuilderMap = map.get(type);
            switch (type.toLowerCase()) {
                case "entities":
                    createEntity("groups", configBuilderMap);
                    continue;
                case "materials":
                    createMaterial(configBuilderMap);
                    continue;
                case "offlineplayer-names":
                    createOfflinePlayerName(configBuilderMap);
                    continue;
                case "offlineplayer-uuids":
                    createOfflinePlayerUUID(configBuilderMap);
                    continue;
                default:
                    UtilsHandler.getLang().sendMsg(ConfigHandler.getPrefix(), sender, "&cCan not find the type: " + type);
            }
        }
        UtilsHandler.getLang().sendMsg(ConfigHandler.getPrefix(), sender, "&aSucceed to create the \"&egroup.yml&a\" in CorePlus/Logs");
    }

    public static void startCustom(CommandSender sender, String type) {
        ConfigBuilderMap configBuilderMap = ConfigHandler.getConfigPath().getConfigBuilderCustomProp().get(type);
        switch (type.toLowerCase()) {
            case "entities":
                createEntity("custom", configBuilderMap);
                break;
            case "materials":
                createMaterial(configBuilderMap);
                break;
            case "offlineplayer-names":
                createOfflinePlayerName(configBuilderMap);
                break;
            case "offlineplayer-uuids":
                createOfflinePlayerUUID(configBuilderMap);
                break;
            default:
                UtilsHandler.getLang().sendMsg(ConfigHandler.getPrefix(), sender, "&cCan not find the type: " + type);
                break;
        }
        UtilsHandler.getLang().sendMsg(ConfigHandler.getPrefix(), sender,
                "&aSucceed to create the group \"&e" + configBuilderMap.getGroup() + "&a\" \"&egroup.yml&a\" in CorePlus/Logs");
    }

    private static void createEntity(String type, ConfigBuilderMap configBuilderMap) {
        if (type.equals("groups")) {
            type = "GroupsBuilder";
        } else {
            type = "CustomBuilder";
        }
        boolean rowLine = configBuilderMap.isRowLine();
        String title = configBuilderMap.getTitle();
        String format = configBuilderMap.getFormat();
        List<String> list = configBuilderMap.getList();
        List<String> ignoreList = configBuilderMap.getIgnoreList();

        // Entities:
        // GROUP:
        UtilsHandler.getCustomCommands().dispatchLogCustomCmd(ConfigHandler.getPrefix(),
                type + ", " + title);

        Set<Class<?>> valueClasses;
        Set<Class<?>> listClasses;
        Set<Class<?>> ignoreListClasses;
        List<Class<?>> listClassList = new ArrayList<>();
        List<Class<?>> ignoreListClassList = new ArrayList<>();
        for (String typeName : list) {
            listClasses = getAllExtendedOrImplementedTypes(EntityType.valueOf(typeName).getEntityClass());
            listClassList.addAll(listClasses);
        }
        for (String typeName : ignoreList) {
            ignoreListClasses = getAllExtendedOrImplementedTypes(EntityType.valueOf(typeName).getEntityClass());
            ignoreListClassList.addAll(ignoreListClasses);
        }
        StringBuilder input = new StringBuilder();
        for (EntityType value : EntityType.values()) {
            valueClasses = getAllExtendedOrImplementedTypes(value.getEntityClass());
            for (Class<?> clazz : valueClasses) {
                if (ignoreListClassList.contains(clazz))
                    continue;
                if (listClassList.contains(clazz)) {
                    if (rowLine) {
                        // "  - %value%"
                        input = new StringBuilder(format.replace("%value%", value.name()));
                        UtilsHandler.getCustomCommands().dispatchLogCustomCmd(ConfigHandler.getPrefix(),
                                type + ", " + input.toString());
                    } else {
                        // "%value%"
                        input.append(format.replace("%value%", value.name())).append(", ");
                    }
                }
            }
        }
        if (rowLine) {
            UtilsHandler.getCustomCommands().dispatchLogCustomCmd(ConfigHandler.getPrefix(),
                    type + ", " + input.toString());
        }
    }

    public static Set<Class<?>> getAllExtendedOrImplementedTypes(Class<?> clazz) {
        List<Class<?>> res = new ArrayList<>();
        do {
            res.add(clazz);
            // First, add all the interfaces implemented by this class
            Class<?>[] interfaces = clazz.getInterfaces();
            if (interfaces.length > 0) {
                res.addAll(Arrays.asList(interfaces));
                for (Class<?> interfaze : interfaces) {
                    res.addAll(getAllExtendedOrImplementedTypes(interfaze));
                }
            }
            // Add the super class
            Class<?> superClass = clazz.getSuperclass();
            // Interfaces does not have java,lang.Object as superclass, they have null, so break the cycle and return
            if (superClass == null) {
                break;
            }
            // Now inspect the superclass
            clazz = superClass;
        } while (!"java.lang.Object".equals(clazz.getCanonicalName()));
        return new HashSet<>(res);
    }

    private static void createMaterial() {
        String format = ConfigHandler.getConfigPath().getConfigBuilderGroupProp().get("Material").getKey();
        if (format == null)
            return;
        List<String> ignoreList = ConfigHandler.getConfigPath().getConfigBuilderGroupProp().get("Material").getValue();
        String input;
        String typeName;
        for (Material type : Material.values()) {
            typeName = type.name();
            if (ignoreList.contains(typeName))
                continue;
            input = format.replace("%type%", typeName);
            UtilsHandler.getCustomCommands().dispatchLogCustomCmd(ConfigHandler.getPrefix(),
                    "ConfigBuilder, " + input);
        }
    }

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
                UtilsHandler.getCustomCommands().dispatchLogCustomCmd(ConfigHandler.getPrefix(),
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
                UtilsHandler.getCustomCommands().dispatchLogCustomCmd(ConfigHandler.getPrefix(),
                        "ConfigBuilder, " + input);
            } catch (Exception ignored) {
            }
        }
    }
}
