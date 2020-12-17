package tw.momocraft.coreplus;

import org.bukkit.plugin.java.JavaPlugin;
import tw.momocraft.coreplus.api.*;
import tw.momocraft.coreplus.handlers.ConfigHandler;
import tw.momocraft.coreplus.utils.Permissions;
import tw.momocraft.coreplus.handlers.RegisterHandler;
import tw.momocraft.coreplus.utils.*;
import tw.momocraft.coreplus.utils.blocksutils.BlocksUtils;
import tw.momocraft.coreplus.utils.customcommands.CustomCommands;
import tw.momocraft.coreplus.utils.eco.PriceAPI;
import tw.momocraft.coreplus.utils.locationutils.LocationUtils;

public class CorePlus extends JavaPlugin {
    private static CorePlus instance;

    @Override
    public void onEnable() {
        instance = this;
        ConfigHandler.generateData(false);
        RegisterHandler.registerEvents();
        ConfigHandler.getLang().sendConsoleMsg(ConfigHandler.getPrefix(), "&fhas been Enabled.");
    }

    @Override
    public void onDisable() {
        ConfigHandler.getLang().sendConsoleMsg(ConfigHandler.getPrefix(), "&fhas been Disabled.");
    }

    public static CorePlus getInstance() {
        return instance;
    }


    //  ============================================== //
    //         API                                     //
    //  ============================================== //
    private DependInterface dependAPI = null;
    public DependInterface getDependManager() {
        if (dependAPI == null)
            dependAPI = new Depend();
        return dependAPI;
    }

    private LanguageInterface languageAPI = null;
    public LanguageInterface getLangManager() {
        if (languageAPI == null)
            languageAPI = new Language();
        return languageAPI;
    }

    private PlayerInterface playerAPI = null;
    public PlayerInterface getPlayerManager() {
        if (playerAPI == null)
            playerAPI = new PlayerUtils();
        return playerAPI;
    }

    private PermissionInterface permissionAPI = null;
    public PermissionInterface getPermManager() {
        if (permissionAPI == null)
            permissionAPI = new Permissions();
        return permissionAPI;
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

    private LoggerInterface loggerAPI = null;
    public LoggerInterface getLogManager() {
        if (loggerAPI == null)
            loggerAPI = new Logger();
        return loggerAPI;
    }

    private ZipInterface zipAPI = null;
    public ZipInterface getZipManager() {
        if (zipAPI == null)
            zipAPI = new Zipper();
        return zipAPI;
    }

    private CommandInterface commandAPI = null;
    public CommandInterface getCommandManager() {
        if (commandAPI == null)
            commandAPI = new CustomCommands();
        return commandAPI;
    }

    private ResidenceInterface residenceAPI = null;
    public ResidenceInterface getResidenceManager() {
        if (residenceAPI == null)
            residenceAPI = new ResidenceUtils();
        return residenceAPI;
    }

    private BlocksInterface blocksAPI = null;
    public BlocksInterface getBlocksManager() {
        if (blocksAPI == null)
            blocksAPI = new BlocksUtils();
        return blocksAPI;
    }

    private LocationInterface locationAPI = null;
    public LocationInterface getLocationManager() {
        if (locationAPI == null)
            locationAPI = new LocationUtils();
        return locationAPI;
    }

    private PriceInterface priceAPI = null;
    public PriceInterface getPriceManager() {
        if (priceAPI == null)
            priceAPI = new PriceAPI();
        return priceAPI;
    }
}