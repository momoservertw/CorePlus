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
        UtilsHandler.getLang().sendConsoleMsg(ConfigHandler.getPrefix(), "&fhas been Enabled.");
    }

    @Override
    public void onDisable() {
        UtilsHandler.getLang().sendConsoleMsg(ConfigHandler.getPrefix(), "&fhas been Disabled.");
    }

    public static CorePlus getInstance() {
        return instance;
    }


    //  ============================================== //
    //         API                                     //
    //  ============================================== //
    private BlocksInterface blocksAPI = null;
    public BlocksInterface getBlocksManager() {
        if (blocksAPI == null)
            blocksAPI = UtilsHandler.getBlock();
        return blocksAPI;
    }
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

    private DependInterface dependAPI = null;
    public DependInterface getDependManager() {
        if (dependAPI == null)
            dependAPI = UtilsHandler.getDepend();
        return dependAPI;
    }

    private LanguageInterface languageAPI = null;
    public LanguageInterface getLangManager() {
        if (languageAPI == null)
            languageAPI = UtilsHandler.getLang();
        return languageAPI;
    }

    private LocationInterface locationAPI = null;
    public LocationInterface getLocationManager() {
        if (locationAPI == null)
            locationAPI = UtilsHandler.getLoc();
        return locationAPI;
    }

    private LoggerInterface loggerAPI = null;
    public LoggerInterface getLogManager() {
        if (loggerAPI == null)
            loggerAPI = new Logger();
        return loggerAPI;
    }

    private PlayerInterface playerAPI = null;
    public PlayerInterface getPlayerManager() {
        if (playerAPI == null)
            playerAPI = new PlayerUtils();
        return playerAPI;
    }

    private UtilsInterface utilsAPI = null;
    public UtilsInterface getUtilsManager() {
        if (utilsAPI == null)
            utilsAPI = new Utils();
        return utilsAPI;
    }

    private UpdateInterface updateAPI = null;
    public UpdateInterface getUpdateManager() {
        if (updateAPI == null)
            updateAPI = new Updater();
        return updateAPI;
    }

    private ZipInterface zipAPI = null;
    public ZipInterface getZipManager() {
        if (zipAPI == null)
            zipAPI = new Zipper();
        return zipAPI;
    }

    private ResidenceInterface residenceAPI = null;
    public ResidenceInterface getResidenceManager() {
        if (residenceAPI == null)
            residenceAPI = new ResidenceUtils();
        return residenceAPI;
    }
}