package tw.momocraft.coreplus.utils;

import org.bukkit.Bukkit;
import tw.momocraft.coreplus.api.DependInterface;
import tw.momocraft.coreplus.handlers.ConfigHandler;
import tw.momocraft.coreplus.utils.eco.GemsEcoAPI;
import tw.momocraft.coreplus.utils.eco.PlayerPointsAPI;
import tw.momocraft.coreplus.utils.eco.VaultAPI;
import tw.momocraft.coreplus.utils.permission.LuckPermsAPI;

public class Depend implements DependInterface {

    private VaultAPI vaultApi;
    private PlayerPointsAPI playerPointsApi;
    private GemsEcoAPI gemsEcoApi;
    private LuckPermsAPI luckPermsApi;

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

    public Depend() {
        if (ConfigHandler.getConfig("config.yml").getBoolean("General.Settings.Features.Hook.Vault")) {
            Vault = Bukkit.getServer().getPluginManager().getPlugin("Vault") != null;
            if (Vault) {
                setVaultApi();
            }
        }
        if (ConfigHandler.getConfig("config.yml").getBoolean("General.Settings.Features.Hook.PlayerPoints")) {
            PlayerPoints = Bukkit.getServer().getPluginManager().getPlugin("PlayerPoints") != null;
            if (PlayerPoints) {
                setPlayerPointsApi();
            }
        }
        if (ConfigHandler.getConfig("config.yml").getBoolean("General.Settings.Features.Hook.GemsEconomy")) {
            GemsEconomy = Bukkit.getServer().getPluginManager().getPlugin("GemsEconomy") != null;
            if (GemsEconomy) {
                setGemsEconomyApi();
            }
        }
        if (ConfigHandler.getConfig("config.yml").getBoolean("General.Settings.Features.Hook.LuckPerms")) {
            LuckPerms = Bukkit.getServer().getPluginManager().getPlugin("LuckPerms") != null;
            if (LuckPerms) {
                setLuckPermsApi();
            }
        }
        if (ConfigHandler.getConfig("config.yml").getBoolean("General.Settings.Features.Hook.PlaceHolderAPI")) {
            PlaceHolderAPI = Bukkit.getServer().getPluginManager().getPlugin("PlaceHolderAPI") != null;
        }
        if (ConfigHandler.getConfig("config.yml").getBoolean("General.Settings.Features.Hook.LangUtils")) {
            LangUtils = Bukkit.getServer().getPluginManager().getPlugin("LangUtils") != null;
        }
        if (ConfigHandler.getConfig("config.yml").getBoolean("General.Settings.Features.Hook.DiscordSRV")) {
            DiscordSRV = Bukkit.getServer().getPluginManager().getPlugin("DiscordSRV") != null;
        }
        if (ConfigHandler.getConfig("config.yml").getBoolean("General.Settings.Features.Hook.MysqlPlayerDataBridge")) {
            MysqlPlayerDataBridge = Bukkit.getServer().getPluginManager().getPlugin("MysqlPlayerDataBridge") != null;
        }
        if (ConfigHandler.getConfig("config.yml").getBoolean("General.Settings.Features.Hook.Residence")) {
            Residence = Bukkit.getServer().getPluginManager().getPlugin("Residence") != null;
        }
        if (ConfigHandler.getConfig("config.yml").getBoolean("General.Settings.Features.Hook.CMI")) {
            CMI = Bukkit.getServer().getPluginManager().getPlugin("CMI") != null;
        }
        if (ConfigHandler.getConfig("config.yml").getBoolean("General.Settings.Features.Hook.MythicMobs")) {
            MythicMobs = Bukkit.getServer().getPluginManager().getPlugin("MythicMobs") != null;
        }
        if (ConfigHandler.getConfig("config.yml").getBoolean("General.Settings.Features.Hook.ItemJoin")) {
            ItemJoin = Bukkit.getServer().getPluginManager().getPlugin("ItemJoin") != null;
        }
        if (ConfigHandler.getConfig("config.yml").getBoolean("General.Settings.Features.Hook.AuthMe")) {
            AuthMe = Bukkit.getServer().getPluginManager().getPlugin("AuthMe") != null;
        }
        sendUtilityDepends();
    }


    private void sendUtilityDepends() {
        String hookMsg = "&fHooked [ &e"
                + (VaultEnabled() ? "Vault, " : "")
                + (PlayerPointsEnabled() ? "PlayerPoints, " : "")
                + (GemsEconomyEnabled() ? "GemsEconomy, " : "")
                + (LuckPermsEnabled() ? "LuckPerms, " : "")
                + (PlaceHolderAPIEnabled() ? "PlaceHolderAPI, " : "")
                + (LangUtilsEnabled() ? "LangUtils, " : "")
                + (DiscordSRVEnabled() ? "DiscordSRV, " : "")
                + (MpdbEnabled() ? "MysqlPlayerDataBridge, " : "")
                + (CMIEnabled() ? "CMI, " : "")
                + (MythicMobsEnabled() ? "MythicMobs, " : "")
                + (ResidenceEnabled() ? "Residence, " : "")
                + (ItemJoinEnabled() ? "ItemJoin, " : "")
                + (AuthMeEnabled() ? "AuthMe, " : "");
        try {
            ConfigHandler.getLang().sendConsoleMsg(ConfigHandler.getPrefix(), hookMsg.substring(0, hookMsg.lastIndexOf(", ")) + " &f]");
        } catch (Exception ignored) {
        }

        /*
        if (ResidenceEnabled()) {
            hookMsg = "&fAdd Residence flags [ &e"
                    + (FlagPermissions.getPosibleAreaFlags().contains("spawnbypass") ? "spawnbypass, " : "")
                    + (FlagPermissions.getPosibleAreaFlags().contains("spawnerbypass") ? "spawnerbypass, " : "")
                    + (FlagPermissions.getPosibleAreaFlags().contains("damagebypass") ? "damagebypass, " : "")
            ;
            try {
                CorePlusAPI.getLangManager().sendConsoleMsg(ConfigHandler.getPrefix(), hookMsg.substring(0, hookMsg.lastIndexOf(", ")) + " &f]");
            } catch (Exception ignored) {
            }
        }
         */
    }

    @Override
    public boolean VaultEnabled() {
        return this.Vault;
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
    public VaultAPI getVaultApi() {
        return this.vaultApi;
    }

    @Override
    public PlayerPointsAPI getPlayerPointsApi() {
        return this.playerPointsApi;
    }

    @Override
    public GemsEcoAPI getGemsEcoApi() {
        return this.gemsEcoApi;
    }

    @Override
    public LuckPermsAPI getLuckPermsApi() {
        return this.luckPermsApi;
    }


    private void setVaultApi() {
        vaultApi = new VaultAPI();
    }

    private void setPlayerPointsApi() {
        playerPointsApi = new PlayerPointsAPI();
    }

    private void setGemsEconomyApi() {
        gemsEcoApi = new GemsEcoAPI();
    }

    private void setLuckPermsApi() {
        luckPermsApi = new LuckPermsAPI();
    }
}
