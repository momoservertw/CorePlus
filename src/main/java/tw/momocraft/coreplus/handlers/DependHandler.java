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
import tw.momocraft.coreplus.utils.player.CMIUtils;
import tw.momocraft.coreplus.utils.player.economy.GemsEcoUtils;
import tw.momocraft.coreplus.utils.player.economy.PlayerPointsUtils;
import tw.momocraft.coreplus.utils.player.economy.VaultUtils;
import tw.momocraft.coreplus.utils.player.permission.LuckPermsUtils;

public class DependHandler implements DependInterface {

    private boolean paper;
    private VaultUtils vaultApi;
    private PlayerPointsUtils playerPointsApi;
    private GemsEcoUtils gemsEcoApi;
    private LuckPermsUtils luckPermsApi;
    private ItemJoinUtils itemJoinApi;
    private ResidenceUtils residenceApi;
    private CMIUtils cmiApi;

    private boolean Vault = false;
    private boolean PlayerPoints = false;
    private boolean GemsEconomy = false;
    private boolean PlaceHolderAPI = false;
    private boolean LangUtils = false;
    private boolean DiscordSRV = false;
    private boolean LuckPerms = false;
    private boolean MysqlPlayerDataBridge = false;
    private boolean MythicMobs = false;
    private boolean CMI = false;
    private boolean Residence = false;
    private boolean ItemJoin = false;
    private boolean AuthMe = false;
    private boolean PvPManager = false;
    private boolean MultiverseCore = false;
    private boolean SurvivalMechanics = false;
    private boolean Vehicles = false;
    private boolean MyPet = false;
    private boolean MorphTool = false;
    private boolean NameTagEdit = false;

    public DependHandler() {
        setup();
        registerEvents();
    }

    private void registerEvents() {
        CorePlus.getInstance().getCommand("CorePlus").setExecutor(new Commands());
        CorePlus.getInstance().getCommand("CorePlus").setTabCompleter(new TabComplete());

        CorePlus.getInstance().getServer().getPluginManager().registerEvents(new CommandOnline(), CorePlus.getInstance());
        if (MpdbEnabled())
            CorePlus.getInstance().getServer().getPluginManager().registerEvents(new CommandOnline(), CorePlus.getInstance());
        CorePlus.getInstance().getServer().getPluginManager().registerEvents(new PlaceHolderTest(), CorePlus.getInstance());
    }

    private void setup() {
        try {
            paper = Class.forName("com.destroystokyo.paper.VersionHistoryManager$VersionData") != null;
        } catch (ClassNotFoundException ignored) {
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
        if (ConfigHandler.getConfig("config.yml").getBoolean("General.Features.Hook.LangUtils"))
            LangUtils = Bukkit.getServer().getPluginManager().getPlugin("LangUtils") != null;
        if (ConfigHandler.getConfig("config.yml").getBoolean("General.Features.Hook.DiscordSRV"))
            DiscordSRV = Bukkit.getServer().getPluginManager().getPlugin("DiscordSRV") != null;
        if (ConfigHandler.getConfig("config.yml").getBoolean("General.Features.Hook.MultiverseCore"))
            MultiverseCore = Bukkit.getServer().getPluginManager().getPlugin("MultiverseCore") != null;
        if (ConfigHandler.getConfig("config.yml").getBoolean("General.Features.Hook.MysqlPlayerDataBridge"))
            MysqlPlayerDataBridge = Bukkit.getServer().getPluginManager().getPlugin("MysqlPlayerDataBridge") != null;
        if (ConfigHandler.getConfig("config.yml").getBoolean("General.Features.Hook.Residence"))
            Residence = Bukkit.getServer().getPluginManager().getPlugin("Residence") != null;
        if (ConfigHandler.getConfig("config.yml").getBoolean("General.Features.Hook.MythicMobs"))
            MythicMobs = Bukkit.getServer().getPluginManager().getPlugin("MythicMobs") != null;
        if (ConfigHandler.getConfig("config.yml").getBoolean("General.Features.Hook.AuthMe"))
            AuthMe = Bukkit.getServer().getPluginManager().getPlugin("AuthMe") != null;
        if (ConfigHandler.getConfig("config.yml").getBoolean("General.Features.Hook.PvPManager"))
            PvPManager = Bukkit.getServer().getPluginManager().getPlugin("PvPManager") != null;
        if (ConfigHandler.getConfig("config.yml").getBoolean("General.Features.Hook.SurvivalMechanics"))
            SurvivalMechanics = Bukkit.getServer().getPluginManager().getPlugin("SurvivalMechanics") != null;
        if (ConfigHandler.getConfig("config.yml").getBoolean("General.Features.Hook.Vehicles"))
            Vehicles = Bukkit.getServer().getPluginManager().getPlugin("Vehicles") != null;
        if (ConfigHandler.getConfig("config.yml").getBoolean("General.Features.Hook.MyPet"))
            MyPet = Bukkit.getServer().getPluginManager().getPlugin("MorphTool") != null;
        if (ConfigHandler.getConfig("config.yml").getBoolean("General.Features.Hook.MorphTool"))
            MorphTool = Bukkit.getServer().getPluginManager().getPlugin("MorphTool") != null;
        if (ConfigHandler.getConfig("config.yml").getBoolean("General.Features.Hook.NameTagEdit"))
            NameTagEdit = Bukkit.getServer().getPluginManager().getPlugin("NameTagEdit") != null;
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
        return vaultApi.isEconEnable();
    }

    @Override
    public boolean VaultPermEnabled() {
        return vaultApi.isPermEnable();
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
    public boolean LangUtilsEnabled() {
        return this.LangUtils;
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

    @Override
    public boolean MultiverseCoreEnabled() {
        return this.MultiverseCore;
    }

    @Override
    public boolean SurvivalMechanicsEnabled() {
        return this.SurvivalMechanics;
    }

    @Override
    public boolean VehiclesEnabled() {
        return this.Vehicles;
    }

    @Override
    public boolean MyPetEnabled() {
        return this.MyPet;
    }

    @Override
    public boolean MorphToolEnabled() {
        return this.MorphTool;
    }

    @Override
    public boolean NameTagEditEnabled() {
        return this.NameTagEdit;
    }

    public VaultUtils getVaultApi() {
        return this.vaultApi;
    }

    public PlayerPointsUtils getPlayerPointsApi() {
        return this.playerPointsApi;
    }

    public GemsEcoUtils getGemsEcoApi() {
        return this.gemsEcoApi;
    }

    public LuckPermsUtils getLuckPermsApi() {
        return this.luckPermsApi;
    }

    public ItemJoinUtils getItemJoinApi() {
        return this.itemJoinApi;
    }

    public ResidenceUtils getResidenceApi() {
        return this.residenceApi;
    }

    public CMIUtils getCmiApi() {
        return cmiApi;
    }


    private void setVaultApi() {
        vaultApi = new VaultUtils();
    }

    private void setPlayerPointsApi() {
        playerPointsApi = new PlayerPointsUtils();
    }

    private void setGemsEconomyApi() {
        gemsEcoApi = new GemsEcoUtils();
    }

    private void setLuckPermsApi() {
        luckPermsApi = new LuckPermsUtils();
    }

    private void setItemJoinUtils() {
        itemJoinApi = new ItemJoinUtils();
    }

    private void setResidenceUtils() {
        residenceApi = new ResidenceUtils();
    }

    private void setCMIUtils() {
        cmiApi = new CMIUtils();
    }
}
