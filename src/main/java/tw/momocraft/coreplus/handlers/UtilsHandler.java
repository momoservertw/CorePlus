package tw.momocraft.coreplus.handlers;

import tw.momocraft.coreplus.utils.Updater;
import tw.momocraft.coreplus.utils.Utils;
import tw.momocraft.coreplus.utils.condition.ConditionManager;
import tw.momocraft.coreplus.utils.customcommand.CommandManager;
import tw.momocraft.coreplus.utils.entity.EntityManager;
import tw.momocraft.coreplus.utils.file.FileManager;
import tw.momocraft.coreplus.utils.message.MessageManager;
import tw.momocraft.coreplus.utils.message.VanillaUtils;
import tw.momocraft.coreplus.utils.player.PlayerManager;

public class UtilsHandler {

    public static void setupFirst(boolean reload) {
        depend = new DependHandler();
        messageManager = new MessageManager();
        depend.setup(reload);
    }

    public static void setupLast(boolean reload) {
        fileManager = new FileManager();
        fileManager.setup();
        vanillaUtils = new VanillaUtils();
        utils = new Utils();
        conditionManager = new ConditionManager();
        playerManager = new PlayerManager();
        entityManager = new EntityManager();
        commandManager = new CommandManager();
        messageManager.setupColorMap();
        updater = new Updater();
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
    private static VanillaUtils vanillaUtils;

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

    public static ConditionManager getCondition() {
        return conditionManager;
    }

    public static PlayerManager getPlayer() {
        return playerManager;
    }

    public static EntityManager getEntity() {
        return entityManager;
    }

    public static VanillaUtils getVanillaUtils() {
        return vanillaUtils;
    }

    public static FileManager getFile() {
        return fileManager;
    }
}
