package tw.momocraft.coreplus;

import org.bukkit.plugin.java.JavaPlugin;
import tw.momocraft.coreplus.api.*;
import tw.momocraft.coreplus.handlers.ConfigHandler;
import tw.momocraft.coreplus.handlers.UtilsHandler;
import tw.momocraft.coreplus.handlers.RegisterHandler;
import tw.momocraft.coreplus.utils.*;
import tw.momocraft.coreplus.utils.customcommands.CustomCommands;

public class CorePlus extends JavaPlugin {
    private static CorePlus instance;

    @Override
    public void onEnable() {
        instance = this;
        ConfigHandler.generateData(false);
        RegisterHandler.registerEvents();
        UtilsHandler.getLang().sendConsoleMsg(ConfigHandler.getPlugin(), "&fhas been Enabled.");
    }

    @Override
    public void onDisable() {
        UtilsHandler.getLang().sendConsoleMsg(ConfigHandler.getPlugin(), "&fhas been Disabled.");
    }

    public static CorePlus getInstance() {
        return instance;
    }


    //  ============================================== //
    //         API                                     //
    //  ============================================== //
    private CommandInterface commandAPI = null;

    public CommandInterface getCommandManager() {
        if (commandAPI == null)
            commandAPI = new CustomCommands();
        return commandAPI;
    }

    private ConfigInterface configAPI = null;

    public ConfigInterface getConfigManager() {
        if (configAPI == null)
            configAPI = ConfigHandler.getConfigPath();
        return configAPI;
    }

    private ConditionInterface conditionAPI = null;

    public ConditionInterface getConditionManager() {
        if (conditionAPI == null)
            conditionAPI = UtilsHandler.getCondition();
        return conditionAPI;
    }

    private DependInterface dependAPI = null;

    public DependInterface getDependManager() {
        if (dependAPI == null)
            dependAPI = UtilsHandler.getDepend();
        return dependAPI;
    }

    private EntityInterface entityAPI = null;

    public EntityInterface getEntityManager() {
        if (entityAPI == null)
            entityAPI = UtilsHandler.getEntity();
        return entityAPI;
    }

    private FileInterface fileAPI = null;

    public FileInterface getFileManager() {
        if (fileAPI == null)
            fileAPI = UtilsHandler.getFile();
        return fileAPI;
    }

    private LanguageInterface languageAPI = null;

    public LanguageInterface getLangManager() {
        if (languageAPI == null)
            languageAPI = UtilsHandler.getLang();
        return languageAPI;
    }

    private PlayerInterface playerAPI = null;

    public PlayerInterface getPlayerManager() {
        if (playerAPI == null)
            playerAPI = new PlayerUtils();
        return playerAPI;
    }

    private UpdateInterface updateAPI = null;

    public UpdateInterface getUpdateManager() {
        if (updateAPI == null)
            updateAPI = new Updater();
        return updateAPI;
    }

    private UtilsInterface utilsAPI = null;

    public UtilsInterface getUtilsManager() {
        if (utilsAPI == null)
            utilsAPI = new Utils();
        return utilsAPI;
    }

    private MySQLInterface mySQLAPI = null;

    public MySQLInterface getMySQLManager() {
        if (mySQLAPI == null)
            mySQLAPI = new MySQLUtils();
        return mySQLAPI;
    }
}