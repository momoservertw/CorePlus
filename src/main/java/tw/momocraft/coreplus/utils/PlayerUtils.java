package tw.momocraft.coreplus.utils;

import com.Zrips.CMI.CMI;
import com.Zrips.CMI.Containers.CMIUser;
import me.NoChance.PvPManager.PvPlayer;
import net.craftersland.data.bridge.PD;
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
    public Player getPlayer(CommandSender sender) {
        if (sender instanceof Player)
            return (Player) sender;
        return null;
    }

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
        OfflinePlayer[] playersOnlineOld;
        try {
            Player player = Bukkit.getPlayer(playerName);
            if (player != null) {
                return Bukkit.getOfflinePlayer(player.getUniqueId());
            }
            playersOnlineOld = ((OfflinePlayer[]) Bukkit.class.getMethod("getOfflinePlayers", new Class<?>[0]).invoke(null, new Object[0]));
            for (OfflinePlayer offlinePlayer : playersOnlineOld) {
                if (offlinePlayer.getName() != null && offlinePlayer.getName().equalsIgnoreCase(playerName)) {
                    return offlinePlayer;
                }
            }
        } catch (Exception ex) {
            UtilsHandler.getLang().sendDebugTrace(ConfigHandler.isDebugging(), ConfigHandler.getPluginName(), ex);
        }
        return null;
    }

    public List<String> getOnlinePlayerNames() {
        Collection<?> playersOnlineNew;
        Player[] playersOnlineOld;
        List<String> playerList = new ArrayList<>();
        try {
            if (Bukkit.class.getMethod("getOnlinePlayers").getReturnType() == Collection.class) {
                if (Bukkit.class.getMethod("getOnlinePlayers").getReturnType() == Collection.class) {
                    playersOnlineNew = ((Collection<?>) Bukkit.class.getMethod("getOnlinePlayers", new Class<?>[0]).invoke(null, new Object[0]));
                    for (Object objPlayer : playersOnlineNew) {
                        playerList.add(((Player) objPlayer).getName());
                    }
                }
            } else {
                playersOnlineOld = ((Player[]) Bukkit.class.getMethod("getOnlinePlayers", new Class<?>[0]).invoke(null, new Object[0]));
                for (Player player : playersOnlineOld) {
                    playerList.add(player.getName());
                }
            }
        } catch (Exception e) {
            UtilsHandler.getLang().sendDebugTrace(tw.momocraft.coreplus.handlers.ConfigHandler.isDebugging(), ConfigHandler.getPluginPrefix(), e);
        }
        return playerList;
    }

    @Override
    public double getLastLogin(String playerName) {
        OfflinePlayer offlinePlayer = getOfflinePlayer(playerName);
        double lastLogin = 0;
        try {
            lastLogin = Double.parseDouble(UtilsHandler.getMySQL().getValueWhere(ConfigHandler.getPluginName(),
                    "playerdataplus", "players", "UUID", offlinePlayer.getUniqueId().toString(), "last_login"));
        } catch (Exception ignored) {
        }
        if (lastLogin != 0)
            return lastLogin;
        return offlinePlayer.getLastLogin() * 1000;
    }

    @Override
    public double getLastLogin(UUID uuid) {
        OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(uuid);
        double lastLogin = 0;
        try {
            lastLogin = Double.parseDouble(UtilsHandler.getMySQL().getValueWhere(ConfigHandler.getPluginName(),
                    "playerdataplus", "players", "UUID", uuid.toString(), "last_login"));
        } catch (Exception ignored) {
        }
        if (lastLogin != 0)
            return lastLogin;
        return offlinePlayer.getLastLogin() * 1000;
    }

    @Override
    public Map<String, Long> getLastLoginMap() {
        Map<String, Long> map = new HashMap<>();
        OfflinePlayer[] offlinePlayers = Bukkit.getOfflinePlayers();
        for (OfflinePlayer offlinePlayer : offlinePlayers) {
            map.put(offlinePlayer.getUniqueId().toString(), offlinePlayer.getLastLogin());
        }
        Map<Object, Object> mySQLMap = UtilsHandler.getMySQL().getValueMap(ConfigHandler.getPluginName(),
                "playerdataplus", "players", "uuid", "last_login",
                "string", "long");
        for (Map.Entry<Object, Object> entry : mySQLMap.entrySet()) {
            map.put(String.valueOf(entry.getKey()), Long.valueOf((String) entry.getValue()));
        }
        return map;
    }

    @Override
    public boolean isPvPEnabled(Player player) {
        if (UtilsHandler.getDepend().PvPManagerEnabled()) {
            return PvPlayer.get(player).hasPvPEnabled();
        }
        if (UtilsHandler.getDepend().MultiverseCoreEnabled()) {
            return UtilsHandler.getDepend().getMultiverseCoreApi().isPvPEnabled(player.getWorld().getName());
        }
        return ConfigHandler.getConfigPath().isPvp();
    }

    @Override
    public boolean isAFK(Player player) {
        if (UtilsHandler.getDepend().CMIEnabled()) {
            return CMI.getInstance().getPlayerManager().getUser(player).isAfk();
        }
        return false;
    }

    @Override
    public double getCurrencyBalance(UUID uuid, String type) {
        switch (type) {
            case "money":
                if (UtilsHandler.getDepend().MpdbEnabled()) {
                    return PD.api.getDatabaseMoney(uuid);
                }
                if (UtilsHandler.getDepend().CMIEnabled()) {
                    CMIUser user = CMI.getInstance().getPlayerManager().getUser(uuid);
                    if (user != null) {
                        return user.getBalance();
                    }
                }
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
        UtilsHandler.getLang().sendErrorMsg(ConfigHandler.getPluginName(), "Can not find price type: " + type);
        return 0;
    }

    @Override
    public double takeCurrency(UUID uuid, String type, double amount) {
        switch (type) {
            case "money":
                if (UtilsHandler.getDepend().CMIEnabled()) {
                    CMIUser user = CMI.getInstance().getPlayerManager().getUser(uuid);
                    if (user != null) {
                        return user.withdraw(amount);
                    }
                }
                if (UtilsHandler.getDepend().MpdbEnabled()) {
                    double money = PD.api.getDatabaseMoney(uuid);
                    money -= amount;
                    PD.api.setDatabaseMoney(uuid, money);
                    return money;
                }
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
                    if (UtilsHandler.getDepend().getGemsEcoApi().getCurrency(type) != null) {
                        return UtilsHandler.getDepend().getGemsEcoApi().withdraw(uuid, amount, type);
                    }
                }
                break;
        }
        UtilsHandler.getLang().sendErrorMsg(ConfigHandler.getPluginName(), "Can not find price type: " + type);
        return 0;
    }

    @Override
    public double giveCurrency(UUID uuid, String type, double amount) {
        switch (type) {
            case "money":
                if (UtilsHandler.getDepend().CMIEnabled()) {
                    CMIUser user = CMI.getInstance().getPlayerManager().getUser(uuid);
                    if (user != null) {
                        user.deposit(amount);
                    }
                }
                if (UtilsHandler.getDepend().MpdbEnabled()) {
                    double money = PD.api.getDatabaseMoney(uuid);
                    money += amount;
                    PD.api.setDatabaseMoney(uuid, money);
                    return money;
                }
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
                    if (UtilsHandler.getDepend().getGemsEcoApi().getCurrency(type) != null) {
                        return UtilsHandler.getDepend().getGemsEcoApi().deposit(uuid, amount, type);
                    }
                }
                break;
        }
        UtilsHandler.getLang().sendErrorMsg(ConfigHandler.getPluginName(), "Can not find price type: " + type);
        return 0;
    }

    public double setCurrency(UUID uuid, String type, double amount) {
        switch (type) {
            case "money":
                if (UtilsHandler.getDepend().CMIEnabled()) {
                    double money = getCurrencyBalance(uuid, type);
                    CMIUser user = CMI.getInstance().getPlayerManager().getUser(uuid);
                    if (user != null) {
                        user.withdraw(money);
                        user.deposit(money);
                        return amount;
                    }
                }
                if (UtilsHandler.getDepend().MpdbEnabled()) {
                    PD.api.setDatabaseMoney(uuid, amount);
                    return amount;
                }
                if (UtilsHandler.getDepend().VaultEnabled() && UtilsHandler.getDepend().getVaultApi().getEconomy() != null) {
                    double money = getCurrencyBalance(uuid, type);
                    UtilsHandler.getDepend().getVaultApi().getEconomy().withdrawPlayer(Bukkit.getOfflinePlayer(uuid), money);
                    UtilsHandler.getDepend().getVaultApi().getEconomy().depositPlayer(Bukkit.getOfflinePlayer(uuid), amount);
                    return amount;
                }
                break;
            case "points":
                if (UtilsHandler.getDepend().PlayerPointsEnabled()) {
                    return UtilsHandler.getDepend().getPlayerPointsApi().givePoints(uuid, amount);
                }
                break;
            default:
                if (UtilsHandler.getDepend().GemsEconomyEnabled()) {
                    if (UtilsHandler.getDepend().getGemsEcoApi().getCurrency(type) != null) {
                        return UtilsHandler.getDepend().getGemsEcoApi().deposit(uuid, amount, type);
                    }
                }
                break;
        }
        UtilsHandler.getLang().sendErrorMsg(ConfigHandler.getPluginName(), "Can not find price type: " + type);
        return 0;
    }

    @Override
    public float getExp(String pluginName, UUID uuid, int amount) {
        if (UtilsHandler.getDepend().MpdbEnabled()) {
            if (getOnlineStatus(uuid).equals("offline")) {
                float exp = Float.parseFloat(UtilsHandler.getMySQL().getValueWhere(pluginName, "MySQLPlayerDataBridge",
                        ConfigHandler.getConfigPath().getMySQLMySQLPlayerDataBridgeExp(),
                        "player_uuid", uuid.toString(), "total_exp"));
                return exp;
            }
            UtilsHandler.getLang().sendErrorMsg(ConfigHandler.getPluginName(), "Target is in other server: " + uuid);
            return 0;
        }
        if (UtilsHandler.getDepend().CMIEnabled()) {
            CMIUser user = CMI.getInstance().getPlayerManager().getUser(uuid);
            if (user != null) {
                return user.getExp();
            }
        }
        Player player = Bukkit.getPlayer(uuid);
        if (player != null) {
            player.getExp();
        }
        return 0;
    }

    @Override
    public void setExp(String pluginName, UUID uuid, int amount) {
        if (UtilsHandler.getDepend().MpdbEnabled()) {
            if (getOnlineStatus(uuid).equals("offline")) {
                UtilsHandler.getMySQL().setValueWhere(pluginName, "MySQLPlayerDataBridge",
                        ConfigHandler.getConfigPath().getMySQLMySQLPlayerDataBridgeExp(),
                        "player_uuid", uuid.toString(), "total_exp", String.valueOf(amount));
                return;
            }
        }
        if (UtilsHandler.getDepend().CMIEnabled()) {
            CMIUser user = CMI.getInstance().getPlayerManager().getUser(uuid);
            user.setExp(amount);
        }
        Player player = Bukkit.getPlayer(uuid);
        if (player != null) {
            player.setExp(amount);
        }
    }

    @Override
    public void giveExp(String pluginName, UUID uuid, int amount) {
        if (getOnlineStatus(uuid).equals("offline")) {
            float exp = Float.parseFloat(UtilsHandler.getMySQL().getValueWhere(pluginName, "MySQLPlayerDataBridge",
                    ConfigHandler.getConfigPath().getMySQLMySQLPlayerDataBridgeExp(),
                    "player_uuid", uuid.toString(), "total_exp"));
            exp += amount;
            UtilsHandler.getMySQL().setValueWhere(pluginName, "MySQLPlayerDataBridge",
                    ConfigHandler.getConfigPath().getMySQLMySQLPlayerDataBridgeExp(),
                    "player_uuid", uuid.toString(), "total_exp", String.valueOf(exp));
            return;
        }
        if (UtilsHandler.getDepend().CMIEnabled()) {
            CMIUser user = CMI.getInstance().getPlayerManager().getUser(uuid);
            if (user != null) {
                user.addExp(amount);
            }
        }
        Player player = Bukkit.getPlayer(uuid);
        if (player != null) {
            player.giveExp(amount);
        }
    }

    public String getOnlineStatus(UUID uuid) {
        if (Bukkit.getPlayer(uuid) == null) {
            if (UtilsHandler.getDepend().MythicMobsEnabled()) {
                if (PD.api.isPlayerOnline(uuid)) {
                    return "bungee";
                }
                return "offline";
            }
        }
        return "server";
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