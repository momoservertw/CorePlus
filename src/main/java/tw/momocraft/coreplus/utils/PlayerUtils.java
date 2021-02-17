package tw.momocraft.coreplus.utils;

import me.NoChance.PvPManager.PvPlayer;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.bukkit.permissions.PermissionAttachmentInfo;
import tw.momocraft.coreplus.api.PlayerInterface;
import tw.momocraft.coreplus.handlers.ConfigHandler;
import tw.momocraft.coreplus.handlers.UtilsHandler;

import java.util.*;

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
    public OfflinePlayer getOfflinePlayer(String playerName) {
        Collection<?> playersOnlineNew;
        OfflinePlayer[] playersOnlineOld;
        try {
            if (Bukkit.class.getMethod("getOfflinePlayers", new Class<?>[0]).getReturnType() == Collection.class) {
                playersOnlineNew = ((Collection<?>) Bukkit.class.getMethod("getOfflinePlayers", new Class<?>[0]).invoke(null, new Object[0]));
                for (Object objPlayer : playersOnlineNew) {
                    Player player = ((Player) objPlayer);
                    if (player.getName().equalsIgnoreCase(playerName)) {
                        return player;
                    }
                }
            } else {
                playersOnlineOld = ((OfflinePlayer[]) Bukkit.class.getMethod("getOfflinePlayers", new Class<?>[0]).invoke(null, new Object[0]));
                for (OfflinePlayer player : playersOnlineOld) {
                    if (player.getName().equalsIgnoreCase(playerName)) {
                        return player;
                    }
                }
            }
        } catch (Exception e) {
            UtilsHandler.getLang().sendDebugTrace(ConfigHandler.isDebugging(), ConfigHandler.getPluginPrefix(), e);
        }
        return null;
    }

    @Override
    public boolean isPvPEnabled(Player player, boolean def) {
        if (UtilsHandler.getDepend().PvPManagerEnabled()) {
            return PvPlayer.get(player).hasPvPEnabled();
        }
        return def;
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
    public double giveTypeMoney(UUID uuid, String priceType, double amount) {
        switch (priceType) {
            case "money":
                if (UtilsHandler.getDepend().VaultEnabled() && UtilsHandler.getDepend().getVaultApi().getEconomy() != null) {
                    UtilsHandler.getDepend().getVaultApi().getEconomy().depositPlayer(Bukkit.getOfflinePlayer(uuid), amount);
                    return UtilsHandler.getDepend().getVaultApi().getBalance(uuid);
                }
                break;
            case "points":
                if (UtilsHandler.getDepend().PlayerPointsEnabled()) {
                    return UtilsHandler.getDepend().getPlayerPointsApi().givePoints(uuid, amount);
                }
                break;
            default:
                if (UtilsHandler.getDepend().GemsEconomyEnabled()) {
                    if (UtilsHandler.getDepend().getGemsEcoApi().getCurrency(priceType) != null) {
                        return UtilsHandler.getDepend().getGemsEcoApi().deposit(uuid, amount, priceType);
                    }
                }
                break;
        }
        UtilsHandler.getLang().sendErrorMsg(ConfigHandler.getPrefix(), "Can not find price type: " + priceType);
        return 0;
    }

    @Override
    public boolean hasPerm(CommandSender sender, String permission) {
        if (sender.hasPermission(permission + ".*"))
            return true;
        return sender.hasPermission(permission) || sender.isOp() || (sender instanceof ConsoleCommandSender);
    }

    @Override
    public boolean hasPerm(Player player, String permission) {
        if (player.hasPermission(permission + ".*"))
            return true;
        return player.hasPermission(permission) || player.isOp() || (player instanceof ConsoleCommandSender);
    }

    @Override
    public boolean hasPerm(UUID uuid, String permission) {
        OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(uuid);
        if (offlinePlayer.isOp())
            return true;
        if (UtilsHandler.getDepend().LuckPermsEnabled()) {
            return UtilsHandler.getDepend().getLuckPermsApi().hasPermission(uuid, permission) ||
                    UtilsHandler.getDepend().getLuckPermsApi().hasPermission(uuid, permission + ".*");
        }
        return UtilsHandler.getDepend().getVaultApi().getPermissions().playerHas(Bukkit.getWorlds().get(0).getName(), offlinePlayer, permission);
    }

    @Override
    public boolean hasPerm(OfflinePlayer offlinePlayer, String permission) {
        if (offlinePlayer.isOp())
            return true;
        if (UtilsHandler.getDepend().LuckPermsEnabled()) {
            return UtilsHandler.getDepend().getLuckPermsApi().hasPermission(offlinePlayer.getUniqueId(), permission) ||
                    UtilsHandler.getDepend().getLuckPermsApi().hasPermission(offlinePlayer.getUniqueId(), permission + ".*");
        }
        return UtilsHandler.getDepend().getVaultApi().getPermissions().playerHas(Bukkit.getWorlds().get(0).getName(), offlinePlayer, permission);
    }

    @Override
    public boolean havePerm(List<CommandSender> senders, String permission) {
        for (CommandSender sender : senders) {
            if (hasPerm(sender, permission))
                return true;
        }
        return false;
    }

    @Override
    public boolean havePermPlayer(List<Player> players, String permission) {
        for (Player player : players) {
            if (hasPerm(player, permission))
                return true;
        }
        return false;
    }

    @Override
    public boolean havePermUUID(List<UUID> uuids, String permission) {
        for (UUID uuid : uuids) {
            return hasPerm(uuid, permission);
        }
        return false;
    }

    @Override
    public boolean havePermOffline(List<OfflinePlayer> offlinePlayers, String permission) {
        for (OfflinePlayer offlinePlayer : offlinePlayers) {
            return hasPerm(offlinePlayer, permission);
        }
        return false;
    }

    @Override
    public boolean allHavePerm(List<CommandSender> senders, String permission) {
        for (CommandSender sender : senders) {
            if (!hasPerm(sender, permission))
                return false;
        }
        return true;
    }

    @Override
    public boolean allHavePermPlayer(List<Player> players, String permission) {
        for (Player player : players) {
            if (!hasPerm(player, permission))
                return false;
        }
        return true;
    }

    @Override
    public boolean allHavePermUUID(List<UUID> uuids, String permission) {
        for (UUID uuid : uuids) {
            if (!hasPerm(uuid, permission))
                return false;
        }
        return true;
    }

    @Override
    public boolean allHavePermOffline(List<OfflinePlayer> offlinePlayers, String permission) {
        for (OfflinePlayer offlinePlayer : offlinePlayers) {
            if (!hasPerm(offlinePlayer, permission))
                return false;
        }
        return true;
    }

    @Override
    public int getMaxPerm(CommandSender sender, String permission, int def) {
        List<Integer> numbers = new ArrayList<>();
        String perm;
        for (PermissionAttachmentInfo permInfo : sender.getEffectivePermissions()) {
            perm = permInfo.getPermission();
            if (perm.startsWith(permission)) {
                try {
                    numbers.add(Integer.parseInt(perm.replace(permission, "")));
                } catch (Exception ignored) {
                }
            }
        }
        if (numbers.isEmpty())
            return def;
        return Collections.max(numbers);
    }

    @Override
    public int getMaxPerm(Player player, String permission, int def) {
        List<Integer> numbers = new ArrayList<>();
        String perm;
        for (PermissionAttachmentInfo permInfo : player.getEffectivePermissions()) {
            perm = permInfo.getPermission();
            if (!perm.contains(permission))
                continue;
            try {
                numbers.add(Integer.parseInt(perm.replace(permission, "")));
            } catch (Exception ignored) {
            }
        }
        if (numbers.isEmpty())
            return def;
        return Collections.max(numbers);
    }

    @Override
    public int getMaxPerm(UUID uuid, String permission, int def) {
        List<Integer> numbers = new ArrayList<>();
        for (String perm : UtilsHandler.getDepend().getLuckPermsApi().getAllPerms(uuid)) {
            if (!perm.contains(permission))
                continue;
            try {
                numbers.add(Integer.parseInt(perm.replace(permission, "")));
            } catch (Exception ignored) {
            }
        }
        if (numbers.isEmpty())
            return def;
        return Collections.max(numbers);
    }

    @Override
    public int getMinPerm(CommandSender sender, String permission, int def) {
        List<Integer> numbers = new ArrayList<>();
        String perm;
        for (PermissionAttachmentInfo permInfo : sender.getEffectivePermissions()) {
            perm = permInfo.getPermission();
            if (perm.startsWith(permission)) {
                try {
                    numbers.add(Integer.parseInt(perm.replace(permission, "")));
                } catch (Exception ignored) {
                }
            }
        }
        if (numbers.isEmpty())
            return def;
        return Collections.min(numbers);
    }

    @Override
    public int getMinPerm(Player player, String permission, int def) {
        List<Integer> numbers = new ArrayList<>();
        String perm;
        for (PermissionAttachmentInfo permInfo : player.getEffectivePermissions()) {
            perm = permInfo.getPermission();
            if (!perm.contains(permission))
                continue;
            try {
                numbers.add(Integer.parseInt(perm.replace(permission, "")));
            } catch (Exception ignored) {
            }
        }
        if (numbers.isEmpty())
            return def;
        return Collections.min(numbers);
    }

    @Override
    public int getMinPerm(UUID uuid, String permission, int def) {
        List<Integer> numbers = new ArrayList<>();
        for (String perm : UtilsHandler.getDepend().getLuckPermsApi().getAllPerms(uuid)) {
            if (!perm.contains(permission))
                continue;
            try {
                numbers.add(Integer.parseInt(perm.replace(permission, "")));
            } catch (Exception ignored) {
            }
        }
        if (numbers.isEmpty())
            return def;
        return Collections.min(numbers);
    }

    @Override
    public String getPrimaryGroup(UUID uuid) {
        if (!UtilsHandler.getDepend().LuckPermsEnabled()) {
            return null;
        }
        return UtilsHandler.getDepend().getLuckPermsApi().getPlayerPrimaryGroup(uuid);
    }

    @Override
    public boolean setPrimaryGroup(UUID uuid, String group) {
        if (!UtilsHandler.getDepend().LuckPermsEnabled()) {
            return false;
        }
        return UtilsHandler.getDepend().getLuckPermsApi().setPlayerPrimaryGroup(uuid, group);
    }

    @Override
    public boolean isInheritedGroup(UUID uuid, String group) {
        if (!UtilsHandler.getDepend().LuckPermsEnabled()) {
            return false;
        }
        return UtilsHandler.getDepend().getLuckPermsApi().isInheritedGroup(uuid, group);
    }

    @Override
    public void addPermission(UUID uuid, String permission) {
        if (UtilsHandler.getDepend().LuckPermsEnabled()) {
            UtilsHandler.getDepend().getLuckPermsApi().addPermission(uuid, permission);
            return;
        }
        UtilsHandler.getDepend().getVaultApi().getPermissions().playerAdd(Bukkit.getWorlds().get(0).getName(),
                Bukkit.getOfflinePlayer(uuid), permission);
    }

    @Override
    public void removePermission(UUID uuid, String permission) {
        if (UtilsHandler.getDepend().LuckPermsEnabled()) {
            UtilsHandler.getDepend().getLuckPermsApi().removePermission(uuid, permission);
            return;
        }
        UtilsHandler.getDepend().getVaultApi().getPermissions().playerRemove(Bukkit.getWorlds().get(0).getName(),
                Bukkit.getOfflinePlayer(uuid), permission);
    }

    @Override
    public void changePermLevel(UUID uuid, String permission, int number, int def) {
        int level = getMaxPerm(uuid, permission, def);
        int nextLevel = level + number;
        if (nextLevel < 0) {
            nextLevel = 0;
        }
        removePermission(uuid, permission + level);
        addPermission(uuid, permission + nextLevel);
    }
}