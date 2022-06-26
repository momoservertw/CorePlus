package tw.momocraft.coreplus.handlers;

import org.bukkit.Bukkit;
import tw.momocraft.coreplus.Commands;
import tw.momocraft.coreplus.CorePlus;
import tw.momocraft.coreplus.TabComplete;
import tw.momocraft.coreplus.api.DependInterface;
import tw.momocraft.coreplus.listeners.CommandOnline;
import tw.momocraft.coreplus.listeners.PlaceHolderTest;
import tw.momocraft.coreplus.utils.condition.ItemJoinUtils;
import tw.momocraft.coreplus.utils.condition.ResidenceUtils;
import tw.momocraft.coreplus.utils.message.DiscordUtils;
import tw.momocraft.coreplus.utils.player.CMIUtils;
import tw.momocraft.coreplus.utils.player.economy.GemsEcoUtils;
import tw.momocraft.coreplus.utils.player.economy.PlayerPointsUtils;
import tw.momocraft.coreplus.utils.player.economy.VaultUtils;
import tw.momocraft.coreplus.utils.player.permission.LuckPermsUtils;

public class DependHandler implements DependInterface {

    private boolean paper;
    private VaultUtils vaultAPI;
    private PlayerPointsUtils playerPointsAPI;
    private GemsEcoUtils gemsEcoAPI;
    private LuckPermsUtils luckPermsAPI;
    private ItemJoinUtils itemJoinAPI;
    private ResidenceUtils residenceAPI;
    private CMIUtils cmiAPI;
    private DiscordUtils discordAPI;

    private boolean Vault = false;
    private boolean PlayerPoints = false;
    private boolean GemsEconomy = false;
    private boolean PlaceHolderAPI = false;
    private boolean DiscordSRV = false;
    private boolean LuckPerms = false;
    private boolean MysqlPlayerDataBridge = false;
    private boolean MythicMobs = false;
    private boolean CMI = false;
    private boolean Residence = false;
    private boolean ItemJoin = false;
    private boolean AuthMe = false;
    private boolean PvPManager = false;

    public void setup(boolean reload) {
        if (!reload) {
            registerEvents();
            //checkUpdate();
        }
        setupHooks();
    }

    public void checkUpdate() {
        if (!ConfigHandler.isCheckUpdates())
            return;
        UtilsHandler.getUpdate().check(ConfigHandler.getPluginName(),
                ConfigHandler.getPluginPrefix(), Bukkit.getConsoleSender(),
                CorePlus.getInstance().getDescription().getName(),
                CorePlus.getInstance().getDescription().getVersion(), true);
    }

    private void registerEvents() {
        CorePlus.getInstance().getCommand("CorePlus").setExecutor(new Commands());
        CorePlus.getInstance().getCommand("CorePlus").setTabCompleter(new TabComplete());

        CorePlus.getInstance().getServer().getPluginManager().registerEvents(new CommandOnline(), CorePlus.getInstance());
        if (MpdbEnabled())
            CorePlus.getInstance().getServer().getPluginManager().registerEvents(new CommandOnline(), CorePlus.getInstance());
        CorePlus.getInstance().getServer().getPluginManager().registerEvents(new PlaceHolderTest(), CorePlus.getInstance());
    }

    private void setupHooks() {
        try {
            Class.forName("com.destroystokyo.paper.VersionHistoryManager$VersionData");
            paper = true;
        } catch (ClassNotFoundException ignored) {
            paper = false;
        }
        if (ConfigHandler.getConfig("config.yml").getBoolean("General.Features.Hook.Vault")) {
            Vault = Bukkit.getServer().getPluginManager().getPlugin("Vault") != null;
            if (Vault)
                setVaultApi();
        }
        if (ConfigHandler.getConfig("config.yml").getBoolean("General.Features.Hook.PlayerPoints")) {
            PlayerPoints = Bukkit.getServer().getPluginManager().getPlugin("PlayerPoints") != null;
            if (PlayerPoints)
                setPlayerPointsApi();
        }
        if (ConfigHandler.getConfig("config.yml").getBoolean("General.Features.Hook.GemsEconomy")) {
            GemsEconomy = Bukkit.getServer().getPluginManager().getPlugin("GemsEconomy") != null;
            if (GemsEconomy)
                setGemsEconomyApi();
        }
        if (ConfigHandler.getConfig("config.yml").getBoolean("General.Features.Hook.LuckPerms")) {
            LuckPerms = Bukkit.getServer().getPluginManager().getPlugin("LuckPerms") != null;
            if (LuckPerms)
                setLuckPermsApi();
        }
        if (ConfigHandler.getConfig("config.yml").getBoolean("General.Features.Hook.ItemJoin")) {
            ItemJoin = Bukkit.getServer().getPluginManager().getPlugin("ItemJoin") != null;
            if (ItemJoin)
                setItemJoinUtils();
        }
        if (ConfigHandler.getConfig("config.yml").getBoolean("General.Features.Hook.Residence")) {
            Residence = Bukkit.getServer().getPluginManager().getPlugin("Residence") != null;
            if (Residence)
                setResidenceUtils();
        }
        if (ConfigHandler.getConfig("config.yml").getBoolean("General.Features.Hook.CMI")) {
            CMI = Bukkit.getServer().getPluginManager().getPlugin("CMI") != null;
            if (CMI)
                setCMIUtils();
        }
        if (ConfigHandler.getConfig("config.yml").getBoolean("General.Features.Hook.PlaceHolderAPI"))
            PlaceHolderAPI = Bukkit.getServer().getPluginManager().getPlugin("PlaceHolderAPI") != null;
        if (ConfigHandler.getConfig("config.yml").getBoolean("General.Features.Hook.DiscordSRV"))
            DiscordSRV = Bukkit.getServer().getPluginManager().getPlugin("DiscordSRV") != null;
        if (ConfigHandler.getConfig("config.yml").getBoolean("General.Features.Hook.MysqlPlayerDataBridge"))
            MysqlPlayerDataBridge = Bukkit.getServer().getPluginManager().getPlugin("MysqlPlayerDataBridge") != null;
        if (ConfigHandler.getConfig("config.yml").getBoolean("General.Features.Hook.MythicMobs"))
            MythicMobs = Bukkit.getServer().getPluginManager().getPlugin("MythicMobs") != null;
        if (ConfigHandler.getConfig("config.yml").getBoolean("General.Features.Hook.AuthMe"))
            AuthMe = Bukkit.getServer().getPluginManager().getPlugin("AuthMe") != null;
        if (ConfigHandler.getConfig("config.yml").getBoolean("General.Features.Hook.PvPManager"))
            PvPManager = Bukkit.getServer().getPluginManager().getPlugin("PvPManager") != null;
    }

    @Override
    public boolean isPaper() {
        return paper;
    }

    @Override
    public boolean VaultEnabled() {
        return this.Vault;
    }

    @Override
    public boolean VaultEconEnabled() {
        return vaultAPI.isEconEnable();
    }

    @Override
    public boolean VaultPermEnabled() {
        return vaultAPI.isPermEnable();
    }

    @Override
    public boolean PlayerPointsEnabled() {
        return this.PlayerPoints;
    }

    @Override
    public boolean GemsEconomyEnabled() {
        return this.GemsEconomy;
    }

    @Override
    public boolean PlaceHolderAPIEnabled() {
        return this.PlaceHolderAPI;
    }

    @Override
    public boolean DiscordSRVEnabled() {
        return this.DiscordSRV;
    }

    @Override
    public boolean MpdbEnabled() {
        return this.MysqlPlayerDataBridge;
    }

    @Override
    public boolean LuckPermsEnabled() {
        return this.LuckPerms;
    }

    @Override
    public boolean ResidenceEnabled() {
        return this.Residence;
    }

    @Override
    public boolean CMIEnabled() {
        return this.CMI;
    }

    @Override
    public boolean MythicMobsEnabled() {
        return this.MythicMobs;
    }

    @Override
    public boolean ItemJoinEnabled() {
        return this.ItemJoin;
    }

    @Override
    public boolean AuthMeEnabled() {
        return this.AuthMe;
    }

    @Override
    public boolean PvPManagerEnabled() {
        return this.PvPManager;
    }

    public VaultUtils getVaultAPI() {
        return this.vaultAPI;
    }

    public PlayerPointsUtils getPlayerPointsAPI() {
        return this.playerPointsAPI;
    }

    public GemsEcoUtils getGemsEcoAPI() {
        return this.gemsEcoAPI;
    }

    public LuckPermsUtils getLuckPermsAPI() {
        return this.luckPermsAPI;
    }

    public ItemJoinUtils getItemJoinAPI() {
        return this.itemJoinAPI;
    }

    public ResidenceUtils getResidenceAPI() {
        return this.residenceAPI;
    }

    public CMIUtils getCmiAPI() {
        return cmiAPI;
    }

    public DiscordUtils getDiscordAPI() {
        return discordAPI;
    }


    private void setVaultApi() {
        vaultAPI = new VaultUtils();
    }

    private void setPlayerPointsApi() {
        playerPointsAPI = new PlayerPointsUtils();
    }

    private void setGemsEconomyApi() {
        gemsEcoAPI = new GemsEcoUtils();
    }

    private void setLuckPermsApi() {
        luckPermsAPI = new LuckPermsUtils();
    }

    private void setItemJoinUtils() {
        itemJoinAPI = new ItemJoinUtils();
    }

    private void setResidenceUtils() {
        residenceAPI = new ResidenceUtils();
    }

    private void setCMIUtils() {
        cmiAPI = new CMIUtils();
    }

    private void setDiscordAPI() {
        discordAPI = new DiscordUtils();
    }
}
