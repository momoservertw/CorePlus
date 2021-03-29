package tw.momocraft.coreplus.utils.player.economy;

import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.permission.Permission;
import org.bukkit.Bukkit;
import org.bukkit.plugin.RegisteredServiceProvider;
import tw.momocraft.coreplus.CorePlus;
import tw.momocraft.coreplus.handlers.ConfigHandler;
import tw.momocraft.coreplus.handlers.UtilsHandler;

import java.util.UUID;

public class VaultUtils {
    private Economy econ = null;
    private Permission perms = null;

    public VaultUtils() {
        if (!this.setupEconomy()) {
            UtilsHandler.getMsg().sendErrorMsg(ConfigHandler.getPluginName(), "Can not find the Economy plugin.");
        }
        if (!this.setupPermissions()) {
            UtilsHandler.getMsg().sendErrorMsg(ConfigHandler.getPluginName(), "Can not find the Permission plugin.");
        }
    }

    private boolean setupEconomy() {
        RegisteredServiceProvider<Economy> rsp = CorePlus.getInstance().getServer().getServicesManager().getRegistration(Economy.class);
        if (rsp == null) {
            return false;
        }
        this.econ = rsp.getProvider();
        return true;
    }

    private boolean setupPermissions() {
        RegisteredServiceProvider<Permission> rsp = CorePlus.getInstance().getServer().getServicesManager().getRegistration(Permission.class);
        if (rsp == null) {
            return false;
        }
        this.perms = rsp.getProvider();
        return true;
    }

    public boolean isEconEnable() {
        return econ == null;
    }

    public boolean isPermEnable() {
        return perms == null;
    }

    public Economy getEconomy() {
        return this.econ;
    }

    public Permission getPermissions() {
        return this.perms;
    }

    public double getBalance(UUID uuid) {
        return econ.getBalance(Bukkit.getOfflinePlayer(uuid));
    }
}