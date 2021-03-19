package tw.momocraft.coreplus.api;

import tw.momocraft.coreplus.CorePlus;

public class CorePlusAPI {

    public static EffectInterface getEffect() {
        return CorePlus.getInstance().getEffectAPI();
    }

    public static CommandInterface getCommand() {
        return CorePlus.getInstance().getCommandManager();
    }

    public static ConditionInterface getCondition() {
        return CorePlus.getInstance().getConditionManager();
    }

    public static ConfigInterface getConfig() {
        return CorePlus.getInstance().getConfigManager();
    }

    public static DependInterface getDepend() {
        return CorePlus.getInstance().getDependManager();
    }

    public static EntityInterface getEntity() {
        return CorePlus.getInstance().getEntityManager();
    }

    public static FileInterface getFile() {
        return CorePlus.getInstance().getFileManager();
    }

    public static LanguageInterface getLang() {
        return CorePlus.getInstance().getLangManager();
    }

    public static PlayerInterface getPlayer() {
        return CorePlus.getInstance().getPlayerManager();
    }

    public static UpdateInterface getUpdate() {
        return CorePlus.getInstance().getUpdateManager();
    }

    public static UtilsInterface getUtils() {
        return CorePlus.getInstance().getUtilsManager();
    }

    public static MySQLInterface getMySQL() {
        return CorePlus.getInstance().getMySQLManager();
    }
}
