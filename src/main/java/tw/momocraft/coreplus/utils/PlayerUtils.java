package tw.momocraft.coreplus.utils;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.bukkit.permissions.PermissionAttachmentInfo;
import tw.momocraft.coreplus.api.PlayerInterface;
import tw.momocraft.coreplus.handlers.ConfigHandler;
import tw.momocraft.coreplus.handlers.UtilsHandler;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

public class PlayerUtils implements PlayerInterface {

    @Override
    public Player getPlayerString(String playerName) {
        Player args = null;
        try {
            args = Bukkit.getPlayer(UUID.fromString(playerName));
        } catch (Exception ignored) {
        }
        if (args == null) {
            return Bukkit.getPlayer(playerName);
        }
        return args;
    }

    @Override
    public double getTypeBalance(UUID uuid, String type) {
        switch (type) {
            case "money":
                if (UtilsHandler.getDepend().VaultEnabled() && UtilsHandler.getDepend().getVaultApi().getEconomy() != null) {
                    return UtilsHandler.getDepend().getVaultApi().getEconomy().getBalance(Bukkit.getOfflinePlayer(uuid));
                }
                break;
            case "points":
                if (UtilsHandler.getDepend().PlayerPointsEnabled()) {
                    return UtilsHandler.getDepend().getPlayerPointsApi().getBalance(uuid);
                }
                break;
            default:
                if (UtilsHandler.getDepend().GemsEconomyEnabled()) {
                    if (UtilsHandler.getDepend().getGemsEcoApi().getCurrency(type) != null) {
                        return UtilsHandler.getDepend().getGemsEcoApi().getBalance(uuid, type);
                    }
                }
                break;
        }
        UtilsHandler.getLang().sendErrorMsg(ConfigHandler.getPrefix(), "Can not find price type: " + type);
        return 0;
    }

    @Override
    public double takeTypeMoney(UUID uuid, String priceType, double amount) {
        switch (priceType) {
            case "money":
                if (UtilsHandler.getDepend().VaultEnabled() && UtilsHandler.getDepend().getVaultApi().getEconomy() != null) {
                    UtilsHandler.getDepend().getVaultApi().getEconomy().withdrawPlayer(Bukkit.getOfflinePlayer(uuid), amount);
                    return UtilsHandler.getDepend().getVaultApi().getBalance(uuid);
                }
                break;
            case "points":
                if (UtilsHandler.getDepend().PlayerPointsEnabled()) {
                    return UtilsHandler.getDepend().getPlayerPointsApi().takePoints(uuid, amount);
                }
                break;
            default:
                if (UtilsHandler.getDepend().GemsEconomyEnabled()) {
                    if (UtilsHandler.getDepend().getGemsEcoApi().getCurrency(priceType) != null) {
                        return UtilsHandler.getDepend().getGemsEcoApi().withdraw(uuid, amount, priceType);
                    }
                }
                break;
        }
        UtilsHandler.getLang().sendErrorMsg(ConfigHandler.getPrefix(), "Can not find price type: " + priceType);
        return 0;
    }

    @Override
    public boolean hasPermission(CommandSender sender, String permission) {
        return sender.hasPermission(permission) || sender.hasPermission("CorePlus.*") || sender.isOp() || (sender instanceof ConsoleCommandSender);
    }

    @Override
    public int getMaxPermission(CommandSender sender, String permission) {
        List<Integer> numbers = new ArrayList<>();
        String perm;
        for (PermissionAttachmentInfo permInfo : sender.getEffectivePermissions()) {
            perm = permInfo.getPermission();
            if (perm.startsWith(permission)) {
                numbers.add(Integer.parseInt(perm.replace(permission, "")));
            }
        }
        if (numbers.isEmpty())
            return 0;
        return Collections.max(numbers);
    }

    @Override
    public String getPlayerPrimaryGroup(UUID uuid) {
        return UtilsHandler.getDepend().getLuckPermsApi().getPlayerPrimaryGroup(uuid);
    }

    @Override
    public boolean setPlayerPrimaryGroup(UUID uuid, String group) {
        return UtilsHandler.getDepend().getLuckPermsApi().setPlayerPrimaryGroup(uuid, group);
    }

    @Override
    public boolean isPlayerInGroup(Player player, String group) {
        return UtilsHandler.getDepend().getLuckPermsApi().isPlayerInGroup(player, group);
    }
}