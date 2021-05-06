package tw.momocraft.coreplus.handlers;

import tw.momocraft.coreplus.utils.Updater;
import tw.momocraft.coreplus.utils.Utils;
import tw.momocraft.coreplus.utils.condition.ConditionManager;
import tw.momocraft.coreplus.utils.customcommand.CommandManager;
import tw.momocraft.coreplus.utils.entity.EntityManager;
import tw.momocraft.coreplus.utils.file.*;
import tw.momocraft.coreplus.utils.message.DiscordUtils;
import tw.momocraft.coreplus.utils.message.MessageManager;
import tw.momocraft.coreplus.utils.message.VanillaUtils;
import tw.momocraft.coreplus.utils.player.PlayerManager;

public class UtilsHandler {

    public static void setUpFirst(boolean reload) {
        if (!reload)
            depend = new DependHandler();
        messageManager = new MessageManager();
    }

    public static void setUpLast(boolean reload) {
        fileManager = new FileManager();
        mySQLUtils = new MySQLUtils();
        yamlUtils = new YamlUtils();
        jsonUtils = new JsonUtils();
        logUtils = new LogUtils();
        propertiesUtils = new PropertiesUtils();
        dataUtils = new DataUtils();
        vanillaUtils = new VanillaUtils();
        discordUtils = new DiscordUtils();
        utils = new Utils();
        conditionManager = new ConditionManager();
        playerManager = new PlayerManager();
        entityManager = new EntityManager();
        commandManager = new CommandManager();
        zipperUtils = new ZipperUtils();
        updater = new Updater();
        syncData();
    }

    public static void syncData() {
        playerManager.importPlayerList();
        playerManager.importPlayerLastLogin();
    }

    private static DependHandler depend;
    private static Updater updater;
    private static Utils utils;
    private static CommandManager commandManager;
    private static MessageManager messageManager;
    private static ConditionManager conditionManager;
    private static PlayerManager playerManager;
    private static EntityManager entityManager;
    private static FileManager fileManager;
    private static LogUtils logUtils;
    private static JsonUtils jsonUtils;
    private static YamlUtils yamlUtils;
    private static PropertiesUtils propertiesUtils;
    private static MySQLUtils mySQLUtils;
    private static ZipperUtils zipperUtils;
    private static VanillaUtils vanillaUtils;
    private static DiscordUtils discordUtils;
    private static DataUtils dataUtils;

    public static DependHandler getDepend() {
        return depend;
    }

    public static CommandManager getCommandManager() {
        return commandManager;
    }

    public static MessageManager getMsg() {
        return messageManager;
    }

    public static Updater getUpdate() {
        return updater;
    }

    public static Utils getUtil() {
        return utils;
    }

    public static ZipperUtils getZip() {
        return zipperUtils;
    }

    public static LogUtils getLog() {
        return logUtils;
    }

    public static ConditionManager getCondition() {
        return conditionManager;
    }

    public static PlayerManager getPlayer() {
        return playerManager;
    }

    public static EntityManager getEntity() {
        return entityManager;
    }

    public static MySQLUtils getMySQL() {
        return mySQLUtils;
    }

    public static VanillaUtils getVanillaUtils() {
        return vanillaUtils;
    }

    public static DiscordUtils getDiscord() {
        return discordUtils;
    }

    public static FileManager getFile() {
        return fileManager;
    }

    public static JsonUtils getJson() {
        return jsonUtils;
    }

    public static YamlUtils getYaml() {
        return yamlUtils;
    }

    public static PropertiesUtils getProperty() {
        return propertiesUtils;
    }

    public static DataUtils getData() {
        return dataUtils;
    }
}
