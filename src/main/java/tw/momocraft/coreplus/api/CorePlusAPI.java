package tw.momocraft.coreplus.api;


import tw.momocraft.coreplus.CorePlus;

public class CorePlusAPI {

    public static DependInterface getDependManager() {
        return CorePlus.getInstance().getDependManager();
    }

    public static LanguageInterface getLangManager() {
        return CorePlus.getInstance().getLangManager();
    }

    public static PlayerInterface getPlayerManager() {
        return CorePlus.getInstance().getPlayerManager();
    }

    public static PermissionInterface getPermManager() {
        return CorePlus.getInstance().getPermManager();
    }

    public static UpdateInterface getUpdateManager() {
        return CorePlus.getInstance().getUpdateManager();
    }

    public static LoggerInterface getLoggerManager() {
        return CorePlus.getInstance().getLogManager();
    }

    public static ZipInterface getZipManager() {
        return CorePlus.getInstance().getZipManager();
    }

    public static CommandInterface getCommandManager() {
        return CorePlus.getInstance().getCommandManager();
    }

    public static ResidenceInterface getResidenceManager() {
        return CorePlus.getInstance().getResidenceManager();
    }
}
