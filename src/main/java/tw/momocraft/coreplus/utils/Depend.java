package tw.momocraft.coreplus.utils;

import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.permission.Permission;
import org.bukkit.Bukkit;
import tw.momocraft.coreplus.api.CorePlusAPI;
import tw.momocraft.coreplus.api.DependInterface;
import tw.momocraft.coreplus.handlers.ConfigHandler;
import tw.momocraft.coreplus.utils.eco.GemsEcoAPI;
import tw.momocraft.coreplus.utils.eco.PlayerPointsAPI;
import tw.momocraft.coreplus.utils.eco.VaultAPI;

import java.util.UUID;

public class Depend implements DependInterface {

    private VaultAPI vaultApi;
    private PlayerPointsAPI playerPointsApi;
    private GemsEcoAPI gemsEcoApi;

    private boolean Vault = false;
    private boolean PlayerPoints = false;
    private boolean GemsEconomy = false;
    private boolean PlaceHolderAPI = false;
    private boolean LangUtils = false;
    private boolean Residence = false;
    private boolean CMI = false;
    private boolean MyPet = false;
    private boolean ItemJoin = false;
    private boolean MorphTool = false;
    private boolean DiscordSRV = false;
    private boolean MysqlPlayerDataBridge = false;
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
        if (ConfigHandler.getConfig("config.yml").getBoolean("General.Settings.Features.Hook.PlaceHolderAPI")) {
            PlaceHolderAPI = Bukkit.getServer().getPluginManager().getPlugin("PlaceHolderAPI") != null;
        }
        if (ConfigHandler.getConfig("config.yml").getBoolean("General.Settings.Features.Hook.LangUtils")) {
            LangUtils = Bukkit.getServer().getPluginManager().getPlugin("LangUtils") != null;
        }
        if (ConfigHandler.getConfig("config.yml").getBoolean("General.Settings.Features.Hook.Residence")) {
            Residence = Bukkit.getServer().getPluginManager().getPlugin("Residence") != null;
        }
        if (ConfigHandler.getConfig("config.yml").getBoolean("General.Settings.Features.Hook.CMI")) {
            CMI = Bukkit.getServer().getPluginManager().getPlugin("CMI") != null;
        }
        if (ConfigHandler.getConfig("config.yml").getBoolean("General.Settings.Features.Hook.MyPet")) {
            MyPet = Bukkit.getServer().getPluginManager().getPlugin("MyPet") != null;
        }
        if (ConfigHandler.getConfig("config.yml").getBoolean("General.Settings.Features.Hook.ItemJoin")) {
            ItemJoin = Bukkit.getServer().getPluginManager().getPlugin("ItemJoin") != null;
        }
        if (ConfigHandler.getConfig("config.yml").getBoolean("General.Settings.Features.Hook.MorphTool")) {
            MorphTool = Bukkit.getServer().getPluginManager().getPlugin("MorphTool") != null;
        }
        if (ConfigHandler.getConfig("config.yml").getBoolean("General.Settings.Features.Hook.DiscordSRV")) {
            DiscordSRV = Bukkit.getServer().getPluginManager().getPlugin("DiscordSRV") != null;
        }
        if (ConfigHandler.getConfig("config.yml").getBoolean("General.Settings.Features.Hook.MysqlPlayerDataBridge")) {
            MysqlPlayerDataBridge = Bukkit.getServer().getPluginManager().getPlugin("MysqlPlayerDataBridge") != null;
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
                + (PlaceHolderAPIEnabled() ? "PlaceHolderAPI, " : "")
                + (LangUtilsEnabled() ? "LangUtils, " : "")
                + (CMIEnabled() ? "CMI, " : "")
                + (ResidenceEnabled() ? "Residence, " : "")
                + (MyPetEnabled() ? "MyPet, " : "")
                + (ItemJoinEnabled() ? "ItemJoin, " : "")
                + (MorphToolEnabled() ? "MorphTool, " : "")
                + (DiscordSRVEnabled() ? "DiscordSRV, " : "")
                + (MpdbEnabled() ? "MysqlPlayerDataBridge, " : "")
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
    public boolean ResidenceEnabled() {
        return this.Residence;
    }

    @Override
    public boolean CMIEnabled() {
        return this.CMI;
    }

    @Override
    public boolean MyPetEnabled() {
        return this.MyPet;
    }

    @Override
    public boolean ItemJoinEnabled() {
        return this.ItemJoin;
    }

    @Override
    public boolean MorphToolEnabled() {
        return this.MorphTool;
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


    private void setVaultApi() {
        vaultApi = new VaultAPI();
    }

    private void setPlayerPointsApi() {
        playerPointsApi = new PlayerPointsAPI();
    }

    private void setGemsEconomyApi() {
        gemsEcoApi = new GemsEcoAPI();
    }
}
