package tw.momocraft.coreplus;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import tw.momocraft.coreplus.api.*;
import tw.momocraft.coreplus.handlers.ConfigHandler;
import tw.momocraft.coreplus.handlers.UtilsHandler;
import tw.momocraft.coreplus.utils.Updater;
import tw.momocraft.coreplus.utils.Utils;
import tw.momocraft.coreplus.utils.customcommand.CommandManager;
import tw.momocraft.coreplus.utils.effect.EffectManager;
import tw.momocraft.coreplus.utils.player.PlayerManager;

public class CorePlus extends JavaPlugin {

    private static CorePlus instance;

    @Override
    public void onEnable() {
        instance = this;
        ConfigHandler.generateData(false);
        UtilsHandler.getMsg().sendConsoleMsg(ConfigHandler.getPluginPrefix(), "&fhas been Enabled.");
    }

    @Override
    public void onDisable() {
        UtilsHandler.getMsg().sendConsoleMsg(ConfigHandler.getPluginPrefix(), "&fhas been Disabled.");
    }

    public static CorePlus getInstance() {
        return instance;
    }

    public static void disablePlugin() {
        CorePlusAPI.getMsg().sendConsoleMsg(ConfigHandler.getPluginName(),
                "&fStarting to disable the plugin...");
        Bukkit.getServer().getPluginManager().disablePlugin(instance);
    }

    //  ============================================== //
    //         API                                     //
    //  ============================================== //
    private EffectInterface effectAPI = null;
    public EffectInterface getEffectAPI() {
        if (effectAPI == null)
            effectAPI = new EffectManager();
        return effectAPI;
    }

    private CommandInterface commandAPI = null;
    public CommandInterface getCommandManager() {
        if (commandAPI == null)
            commandAPI = new CommandManager();
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

    private MessageInterface messageAPI = null;
    public MessageInterface getMsgManager() {
        if (messageAPI == null)
            messageAPI = UtilsHandler.getMsg();
        return messageAPI;
    }

    private PlayerInterface playerAPI = null;
    public PlayerInterface getPlayerManager() {
        if (playerAPI == null)
            playerAPI = new PlayerManager();
        return playerAPI;
    }

    private UpdateInterface updaterAPI = null;
    public UpdateInterface getUpdateManager() {
        if (updaterAPI == null)
            updaterAPI = new Updater();
        return updaterAPI;
    }

    private UtilsInterface utilsAPI = null;
    public UtilsInterface getUtilsManager() {
        if (utilsAPI == null)
            utilsAPI = new Utils();
        return utilsAPI;
    }
}