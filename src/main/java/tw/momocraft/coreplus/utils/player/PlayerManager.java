package tw.momocraft.coreplus.utils.player;

import com.Zrips.CMI.CMI;
import com.Zrips.CMI.Containers.CMIUser;
import fr.xephi.authme.api.v3.AuthMeApi;
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
import tw.momocraft.coreplus.utils.file.MySQLMap;

import java.net.URL;
import java.sql.ResultSet;
import java.util.*;

public class PlayerManager implements PlayerInterface {

    @Override
    public Player getPlayer(CommandSender sender) {
        if (sender instanceof Player)
            return (Player) sender;
        return null;
    }

    @Override
    public Player getPlayer(String playerName) {
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
    public UUID getPlayerUUID(String playerName) {
        UUID uuid = getPlayerUUIDLocal(playerName);
        if (uuid != null)
            return uuid;
        return getPlayerUUIDMojang(playerName);
    }

    @Override
    public UUID getPlayerUUIDLocal(String playerName) {
        Player player = Bukkit.getPlayer(playerName);
        if (player != null)
            return player.getUniqueId();
        UUID uuid;
        if (UtilsHandler.getDepend().LuckPermsEnabled()) {
            uuid = UtilsHandler.getDepend().getLuckPermsApi().getUUID(playerName);
            if (uuid != null)
                return uuid;
        }
        if (UtilsHandler.getDepend().CMIEnabled()) {
            uuid = UtilsHandler.getDepend().getCmiApi().getUUID(playerName);
            if (uuid != null)
                return uuid;
        }
        OfflinePlayer offlinePlayer = getOfflinePlayer(playerName);
        if (offlinePlayer != null)
            return offlinePlayer.getUniqueId();
        return null;
    }

    @Override
    public UUID getPlayerUUIDMojang(String playerName) {
        try {
            Scanner scanner = new Scanner(new URL("https://api.mojang.com/users/profiles/minecraft/" + playerName).openStream());
            String rawData = scanner.nextLine();
            scanner.close();
            return UUID.fromString(rawData.split("\"")[3].replaceFirst("([0-9a-fA-F]{8})([0-9a-fA-F]{4})([0-9a-fA-F]{4})([0-9a-fA-F]{4})([0-9a-fA-F]+)", "$1-$2-$3-$4-$5"));
        } catch (Exception ex) {
            UtilsHandler.getMsg().sendErrorMsg(ConfigHandler.getPlugin(), "Can not get player UUID from the mojang API.");
            return null;
        }
    }

    @Override
    public OfflinePlayer getOfflinePlayer(String playerName) {
        UUID uuid = getPlayerUUID(playerName);
        if (uuid != null)
            return Bukkit.getOfflinePlayer(uuid);
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
            UtilsHandler.getMsg().sendDebugTrace(ConfigHandler.isDebug(), ConfigHandler.getPlugin(), ex);
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
            UtilsHandler.getMsg().sendDebugTrace(tw.momocraft.coreplus.handlers.ConfigHandler.isDebug(), ConfigHandler.getPluginPrefix(), e);
        }
        return playerList;
    }

    @Override
    public String getPlayerLocal(Player player) {
        return UtilsHandler.getVanillaUtils().getLocal(player);
    }

    @Override
    public String getPlayerLocal(CommandSender sender) {
        return UtilsHandler.getVanillaUtils().getLocal(sender);
    }

    @Override
    public double getLastLogin(String playerName) {
        OfflinePlayer offlinePlayer = getOfflinePlayer(playerName);
        double lastLogin = 0;
        try {
            lastLogin = Double.parseDouble(UtilsHandler.getMySQL().getValueWhere(ConfigHandler.getPlugin(),
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
            lastLogin = Double.parseDouble(UtilsHandler.getMySQL().getValueWhere(ConfigHandler.getPlugin(),
                    "playerdataplus", "players", "UUID", uuid.toString(), "last_login"));
        } catch (Exception ignored) {
        }
        if (lastLogin != 0)
            return lastLogin;
        return offlinePlayer.getLastLogin() * 1000;
    }

    public void importPlayerLastLogin() {
        List<String> uuidList = UtilsHandler.getMySQL().getValueList(ConfigHandler.getPlugin(),
                "coreplus", "player", "uuid");
        long dataTime;
        long checkTime;
        OfflinePlayer offlinePlayer;
        for (String uuid : uuidList) {
            // Getting the CorePlus login time.
            dataTime = Long.parseLong(UtilsHandler.getMySQL().getValueWhere(ConfigHandler.getPlugin(),
                    "coreplus", "players", "uuid", uuid, "last_login"));
            // Checking the Server login time.
            offlinePlayer = Bukkit.getOfflinePlayer(UUID.fromString(uuid));
            checkTime = offlinePlayer.getLastLogin();
            if (checkTime == 0 && dataTime > checkTime) {
                UtilsHandler.getMySQL().setValueWhere(ConfigHandler.getPlugin(),
                        "coreplus", "players", "uuid", uuid, "last_login", String.valueOf(checkTime));
                dataTime = checkTime;
            }
            // Checking the AuthMe login time.
            if (UtilsHandler.getDepend().AuthMeEnabled()) {
                checkTime = AuthMeApi.getInstance().getLastLoginTime(offlinePlayer.getName()).toEpochMilli();
                if (checkTime == 0 && dataTime > checkTime) {
                    UtilsHandler.getMySQL().setValueWhere(ConfigHandler.getPlugin(),
                            "coreplus", "players", "uuid", uuid, "last_login", String.valueOf(checkTime));
                }
            }
        }
    }

    public void importPlayerList() {
        // Getting the LuckPerms user list.
        if (UtilsHandler.getDepend().LuckPermsEnabled()) {
            MySQLMap mySQLMap = ConfigHandler.getConfigPath().getMySQLProp().get("luckperms");
            if (mySQLMap != null) {
                ResultSet resultSet = UtilsHandler.getMySQL().getResultSet(ConfigHandler.getPlugin(),
                        mySQLMap.getDatabase(), mySQLMap.getTables().get("Players"));
                try {
                    while (resultSet.next()) {
                        resultSet.getString("uuid");
                        resultSet.getString("username");
                    }
                } catch (Exception ex) {
                    UtilsHandler.getMsg().sendErrorMsg(ConfigHandler.getPlugin(), "An error occurred while importing the player data.");
                    UtilsHandler.getMsg().sendErrorMsg(ConfigHandler.getPlugin(), "Please check the settings \"MySQL.LuckPerms\" in data.yml.");
                }
            }
        }
        // Getting the Server player list.
        OfflinePlayer[] offlinePlayers = Bukkit.getOfflinePlayers();
        for (OfflinePlayer offlinePlayer : offlinePlayers) {
            UtilsHandler.getMySQL().setValueWhere(ConfigHandler.getPlugin(),
                    "playerdataplus", "players", "uuid", offlinePlayer.getUniqueId().toString(),
                    "username", offlinePlayer.getName());
        }
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
        if (UtilsHandler.getDepend().CMIEnabled())
            return UtilsHandler.getDepend().getCmiApi().isAFK(player);
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
        UtilsHandler.getMsg().sendErrorMsg(ConfigHandler.getPlugin(), "Can not find price type: " + type);
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
        UtilsHandler.getMsg().sendErrorMsg(ConfigHandler.getPlugin(), "Can not find price type: " + type);
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
        UtilsHandler.getMsg().sendErrorMsg(ConfigHandler.getPlugin(), "Can not find price type: " + type);
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
        UtilsHandler.getMsg().sendErrorMsg(ConfigHandler.getPlugin(), "Can not find price type: " + type);
        return 0;
    }

    @Override
    public float getExp(String pluginName, UUID uuid, int amount) {
        if (UtilsHandler.getDepend().MpdbEnabled()) {
            if (getOnlineStatus(uuid).equals("offline")) {
                try {
                    return Float.parseFloat(UtilsHandler.getMySQL().getValueWhere(pluginName, "MySQLPlayerDataBridge",
                            ConfigHandler.getConfigPath().getMySQLProp().get("MySQLPlayerDataBridge").getTables().get("Experience"),
                            "player_uuid", uuid.toString(), "total_exp"));
                } catch (Exception ex) {
                    UtilsHandler.getMsg().sendErrorMsg(pluginName,
                            "Can not get the Experience from");
                }
            }
            UtilsHandler.getMsg().sendErrorMsg(ConfigHandler.getPlugin(), "Target is in other server: " + uuid);
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
                if (UtilsHandler.getMySQL().isConnect(pluginName, "MySQLPlayerDataBridge")) {
                    String tableName = ConfigHandler.getConfigPath().getMySQLProp().get("MySQLPlayerDataBridge").getTables().get("Experience");
                    UtilsHandler.getMySQL().setValueWhere(pluginName, "MySQLPlayerDataBridge",
                            tableName, "player_uuid", uuid.toString(), "total_exp", String.valueOf(amount));
                    return;
                }
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
            if (UtilsHandler.getMySQL().isConnect(pluginName, "MySQLPlayerDataBridge")) {
                String tableName = ConfigHandler.getConfigPath().getMySQLProp().get("MySQLPlayerDataBridge").getTables().get("Experience");
                float exp = Float.parseFloat(UtilsHandler.getMySQL().getValueWhere(pluginName, "MySQLPlayerDataBridge",
                        tableName, "player_uuid", uuid.toString(), "total_exp"));
                exp += amount;
                UtilsHandler.getMySQL().setValueWhere(pluginName, "MySQLPlayerDataBridge",
                        tableName, "player_uuid", uuid.toString(), "total_exp", String.valueOf(exp));
                return;
            }
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