package tw.momocraft.coreplus.utils;

import org.bukkit.Bukkit;
import tw.momocraft.coreplus.api.DependInterface;
import tw.momocraft.coreplus.handlers.ConfigHandler;
import tw.momocraft.coreplus.handlers.UtilsHandler;
import tw.momocraft.coreplus.utils.conditions.ItemJoinUtils;
import tw.momocraft.coreplus.utils.conditions.ResidenceUtils;
import tw.momocraft.coreplus.utils.economy.GemsEcoUtils;
import tw.momocraft.coreplus.utils.economy.PlayerPointsUtils;
import tw.momocraft.coreplus.utils.economy.VaultUtils;
import tw.momocraft.coreplus.utils.permission.LuckPermsAPI;

public class Dependence implements DependInterface {

    private VaultUtils vaultApi;
    private PlayerPointsUtils playerPointsApi;
    private GemsEcoUtils gemsEcoApi;
    private LuckPermsAPI luckPermsApi;
    private ItemJoinUtils itemJoinUtils;
    private ResidenceUtils residenceUtils;

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

    public Dependence() {
        if (ConfigHandler.getConfig("config.yml").getBoolean("General.Features.Hook.Vault")) {
            Vault = Bukkit.getServer().getPluginManager().getPlugin("Vault") != null;
            if (Vault) {
                setVaultApi();
            }
        }
        if (ConfigHandler.getConfig("config.yml").getBoolean("General.Features.Hook.PlayerPoints")) {
            PlayerPoints = Bukkit.getServer().getPluginManager().getPlugin("PlayerPoints") != null;
            if (PlayerPoints) {
                setPlayerPointsApi();
            }
        }
        if (ConfigHandler.getConfig("config.yml").getBoolean("General.Features.Hook.GemsEconomy")) {
            GemsEconomy = Bukkit.getServer().getPluginManager().getPlugin("GemsEconomy") != null;
            if (GemsEconomy) {
                setGemsEconomyApi();
            }
        }
        if (ConfigHandler.getConfig("config.yml").getBoolean("General.Features.Hook.LuckPerms")) {
            LuckPerms = Bukkit.getServer().getPluginManager().getPlugin("LuckPerms") != null;
            if (LuckPerms) {
                setLuckPermsApi();
            }
        }
        if (ConfigHandler.getConfig("config.yml").getBoolean("General.Features.Hook.ItemJoin")) {
            ItemJoin = Bukkit.getServer().getPluginManager().getPlugin("ItemJoin") != null;
            if (ItemJoin) {
                setItemJoinUtils();
            }
        }
        if (ConfigHandler.getConfig("config.yml").getBoolean("General.Features.Hook.Residence")) {
            Residence = Bukkit.getServer().getPluginManager().getPlugin("Residence") != null;
            if (Residence) {
                setResidenceUtils();
            }
        }
        if (ConfigHandler.getConfig("config.yml").getBoolean("General.Features.Hook.PlaceHolderAPI")) {
            PlaceHolderAPI = Bukkit.getServer().getPluginManager().getPlugin("PlaceHolderAPI") != null;
        }
        if (ConfigHandler.getConfig("config.yml").getBoolean("General.Features.Hook.LangUtils")) {
            LangUtils = Bukkit.getServer().getPluginManager().getPlugin("LangUtils") != null;
        }
        if (ConfigHandler.getConfig("config.yml").getBoolean("General.Features.Hook.DiscordSRV")) {
            DiscordSRV = Bukkit.getServer().getPluginManager().getPlugin("DiscordSRV") != null;
        }
        if (ConfigHandler.getConfig("config.yml").getBoolean("General.Features.Hook.MysqlPlayerDataBridge")) {
            MysqlPlayerDataBridge = Bukkit.getServer().getPluginManager().getPlugin("MysqlPlayerDataBridge") != null;
        }
        if (ConfigHandler.getConfig("config.yml").getBoolean("General.Features.Hook.Residence")) {
            Residence = Bukkit.getServer().getPluginManager().getPlugin("Residence") != null;
        }
        if (ConfigHandler.getConfig("config.yml").getBoolean("General.Features.Hook.CMI")) {
            CMI = Bukkit.getServer().getPluginManager().getPlugin("CMI") != null;
        }
        if (ConfigHandler.getConfig("config.yml").getBoolean("General.Features.Hook.MythicMobs")) {
            MythicMobs = Bukkit.getServer().getPluginManager().getPlugin("MythicMobs") != null;
        }
        if (ConfigHandler.getConfig("config.yml").getBoolean("General.Features.Hook.AuthMe")) {
            AuthMe = Bukkit.getServer().getPluginManager().getPlugin("AuthMe") != null;
        }
        if (ConfigHandler.getConfig("config.yml").getBoolean("General.Features.Hook.PvPManager")) {
            PvPManager = Bukkit.getServer().getPluginManager().getPlugin("PvPManager") != null;
        }
        sendUtilityDepends();
    }


    private void sendUtilityDepends() {
        String hookMsg = "&fHooked: ["
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
                + (AuthMeEnabled() ? "AuthMe, " : "")
                + (PvPManagerEnabled() ? "PvPManager, " : "");
        UtilsHandler.getLang().sendConsoleMsg(ConfigHandler.getPluginPrefix(), hookMsg.substring(0, hookMsg.length() - 2) + "]");

        /*
        if (ResidenceEnabled()) {
            hookMsg = "&fResidence Flags: [ "
                    + (FlagPermissions.getPosibleAreaFlags().contains("spawnbypass") ? "spawnbypass, " : "")
                    + (FlagPermissions.getPosibleAreaFlags().contains("spawnerbypass") ? "spawnerbypass, " : "")
                    + (FlagPermissions.getPosibleAreaFlags().contains("damagebypass") ? "damagebypass, " : "")
            ;
            try {
                UtilsHandler.getLang().sendConsoleMsg(ConfigHandler.getPrefix(), hookMsg.substring(0, hookMsg.lastIndexOf(", ")) + " &f]");
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

    public VaultUtils getVaultApi() {
        return this.vaultApi;
    }

    public PlayerPointsUtils getPlayerPointsApi() {
        return this.playerPointsApi;
    }

    public GemsEcoUtils getGemsEcoApi() {
        return this.gemsEcoApi;
    }

    public LuckPermsAPI getLuckPermsApi() {
        return this.luckPermsApi;
    }

    public ItemJoinUtils getItemJoinUtils() {
        return this.itemJoinUtils;
    }

    public ResidenceUtils getResidenceUtils() {
        return this.residenceUtils;
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
        luckPermsApi = new LuckPermsAPI();
    }

    private void setItemJoinUtils() {
        itemJoinUtils = new ItemJoinUtils();
    }

    private void setResidenceUtils() {
        residenceUtils = new ResidenceUtils();
    }

}
