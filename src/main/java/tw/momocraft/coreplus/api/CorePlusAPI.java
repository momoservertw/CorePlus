package tw.momocraft.coreplus.api;

import tw.momocraft.coreplus.CorePlus;

public class CorePlusAPI {

    public static CommandInterface getCommandManager() {
        return CorePlus.getInstance().getCommandManager();
    }

    public static ConditionInterface getConditionManager() {
        return CorePlus.getInstance().getConditionManager();
    }

    public static ConfigInterface getConfigManager() {
        return CorePlus.getInstance().getConfigManager();
    }

    public static DependInterface getDependManager() {
        return CorePlus.getInstance().getDependManager();
    }

    public static EntityInterface getEntityManager() {
        return CorePlus.getInstance().getEntityManager();
    }

    public static FileInterface getFileManager() {
        return CorePlus.getInstance().getFileManager();
    }

    public static LanguageInterface getLangManager() {
        return CorePlus.getInstance().getLangManager();
    }

    public static PlayerInterface getPlayerManager() {
        return CorePlus.getInstance().getPlayerManager();
    }

    public static UpdateInterface getUpdateManager() {
        return CorePlus.getInstance().getUpdateManager();
    }

    public static UtilsInterface getUtilsManager() {
        return CorePlus.getInstance().getUtilsManager();
    }

    public static MySQLInterface getMySQLManager() {
        return CorePlus.getInstance().getMySQLManager();
    }
}
