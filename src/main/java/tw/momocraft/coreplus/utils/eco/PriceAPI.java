package tw.momocraft.coreplus.utils.eco;

import org.bukkit.Bukkit;
import tw.momocraft.coreplus.api.PriceInterface;
import tw.momocraft.coreplus.handlers.ConfigHandler;

import java.util.UUID;

public class PriceAPI implements PriceInterface {

    @Override
    public double getTypeBalance(UUID uuid, String priceType) {
        switch (priceType) {
            case "money":
                if (ConfigHandler.getDepends().VaultEnabled() && ConfigHandler.getDepends().getVaultApi().getEconomy() != null) {
                    return ConfigHandler.getDepends().getVaultApi().getEconomy().getBalance(Bukkit.getOfflinePlayer(uuid));
                }
                break;
            case "points":
                if (ConfigHandler.getDepends().PlayerPointsEnabled()) {
                    return ConfigHandler.getDepends().getPlayerPointsApi().getBalance(uuid);
                }
                break;
            default:
                if (ConfigHandler.getDepends().GemsEconomyEnabled()) {
                    if (ConfigHandler.getDepends().getGemsEcoApi().getCurrency(priceType) != null) {
                        return ConfigHandler.getDepends().getGemsEcoApi().getBalance(uuid, priceType);
                    }
                }
                break;
        }
        ConfigHandler.getLang().sendErrorMsg(ConfigHandler.getPrefix(), "Can not find price type: " + priceType);
        return 0;
    }

    @Override
    public double takeTypeMoney(UUID uuid, String priceType, double amount) {
        switch (priceType) {
            case "money":
                if (ConfigHandler.getDepends().VaultEnabled() && ConfigHandler.getDepends().getVaultApi().getEconomy() != null) {
                    ConfigHandler.getDepends().getVaultApi().getEconomy().withdrawPlayer(Bukkit.getOfflinePlayer(uuid), amount);
                    return ConfigHandler.getDepends().getVaultApi().getBalance(uuid);
                }
                break;
            case "points":
                if (ConfigHandler.getDepends().PlayerPointsEnabled()) {
                    return ConfigHandler.getDepends().getPlayerPointsApi().takePoints(uuid, amount);
                }
                break;
            default:
                if (ConfigHandler.getDepends().GemsEconomyEnabled()) {
                    if (ConfigHandler.getDepends().getGemsEcoApi().getCurrency(priceType) != null) {
                        return ConfigHandler.getDepends().getGemsEcoApi().withdraw(uuid, amount, priceType);
                    }
                }
                break;
        }
        ConfigHandler.getLang().sendErrorMsg(ConfigHandler.getPrefix(), "Can not find price type: " + priceType);
        return 0;
    }
}
