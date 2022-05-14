package tw.momocraft.coreplus.utils.player;

import com.Zrips.CMI.CMI;
import com.Zrips.CMI.Containers.CMIUser;
import com.bekvon.bukkit.residence.Residence;
import fr.xephi.authme.api.v3.AuthMeApi;
import me.NoChance.PvPManager.PvPlayer;
import net.craftersland.data.bridge.PD;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.bukkit.permissions.PermissionAttachmentInfo;
import tw.momocraft.coreplus.api.PlayerInterface;
import tw.momocraft.coreplus.handlers.ConfigHandler;
import tw.momocraft.coreplus.handlers.UtilsHandler;
import tw.momocraft.coreplus.utils.file.maps.MySQLMap;

import java.net.URL;
import java.sql.ResultSet;
import java.util.*;

public class PlayerManager implements PlayerInterface {

    @Override
    public boolean isPlayerOnline(UUID uuid) {
        return Bukkit.getPlayer(uuid) != null;
    }

    @Override
    public boolean isPlayerOnline(String playerName) {
        return Bukkit.getPlayer(playerName) != null;
    }

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
            uuid = UtilsHandler.getDepend().getLuckPermsAPI().getUUID(playerName);
            if (uuid != null)
                return uuid;
        }
        if (UtilsHandler.getDepend().CMIEnabled()) {
            uuid = UtilsHandler.getDepend().getCmiAPI().getUUID(playerName);
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
            UtilsHandler.getMsg().sendErrorMsg(ConfigHandler.getPluginName(), "Can not get player UUID from the mojang API.");
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
            UtilsHandler.getMsg().sendDebugTrace(ConfigHandler.isDebug(), ConfigHandler.getPluginName(), ex);
        }
        return null;
    }

    @Override
    public OfflinePlayer getOfflinePlayer(UUID uuid) {
        if (uuid == null)
            return null;
        return Bukkit.getOfflinePlayer(uuid);
    }

    @Override
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
            lastLogin = Double.parseDouble(UtilsHandler.getFile().getMySQL().getValueWhere(ConfigHandler.getPluginName(),
                    "playerdataplus", "players", "UUID", offlinePlayer.getUniqueId().toString(), "last_login"));
        } catch (Exception ignored) {
        }
        if (lastLogin != 0)
            return lastLogin;
        return offlinePlayer.getLastLogin() * 1000;
    }

    @Override
    public double getLastLogin(UUID uuid) {
        double lastLogin = 0;
        try {
            lastLogin = Double.parseDouble(UtilsHandler.getFile().getMySQL().getValueWhere(ConfigHandler.getPluginName(),
                    "playerdataplus", "players", "UUID", uuid.toString(), "last_login"));
        } catch (Exception ignored) {
        }
        if (lastLogin != 0)
            return lastLogin;
        return Bukkit.getOfflinePlayer(uuid).getLastLogin() * 1000;
    }

    public void importPlayerLastLogin() {
        if (!ConfigHandler.getConfigPath().isDataMySQL())
            return;
        List<String> uuidList = UtilsHandler.getFile().getMySQL().getValueList(ConfigHandler.getPluginName(),
                "coreplus", "player", "uuid");
        if (uuidList == null)
            return;
        long dataTime;
        long checkTime;
        OfflinePlayer offlinePlayer;
        for (String uuid : uuidList) {
            // Getting the CorePlus login time.
            dataTime = Long.parseLong(UtilsHandler.getFile().getMySQL().getValueWhere(ConfigHandler.getPluginName(),
                    "coreplus", "players", "uuid", uuid, "last_login"));
            // Checking the Server login time.
            offlinePlayer = Bukkit.getOfflinePlayer(UUID.fromString(uuid));
            if (offlinePlayer.getName() == null || offlinePlayer.getName().equals("CMI-Fake-Operator"))
                continue;
            checkTime = offlinePlayer.getLastLogin();
            if (checkTime == 0 && dataTime > checkTime) {
                UtilsHandler.getFile().getMySQL().setValueWhere(ConfigHandler.getPluginName(),
                        "coreplus", "players", "uuid", uuid, "last_login", String.valueOf(checkTime));
                dataTime = checkTime;
            }
            // Checking the AuthMe login time.
            if (UtilsHandler.getDepend().AuthMeEnabled()) {
                checkTime = AuthMeApi.getInstance().getLastLoginTime(offlinePlayer.getName()).toEpochMilli();
                if (checkTime == 0 && dataTime > checkTime) {
                    UtilsHandler.getFile().getMySQL().setValueWhere(ConfigHandler.getPluginName(),
                            "coreplus", "players", "uuid", uuid, "last_login", String.valueOf(checkTime));
                }
            }
        }
    }

    public void importPlayerList() {
        if (!ConfigHandler.getConfigPath().isDataMySQL())
            return;
        if (UtilsHandler.getDepend().LuckPermsEnabled()) {
            MySQLMap mySQLMap = ConfigHandler.getConfigPath().getMySQLProp().get("luckperms");
            if (mySQLMap != null) {
                ResultSet resultSet = UtilsHandler.getFile().getMySQL().getResultSet(ConfigHandler.getPluginName(),
                        mySQLMap.getDatabase(), mySQLMap.getTables().get("Players"));
                try {
                    while (resultSet.next()) {
                        resultSet.getString("uuid");
                        resultSet.getString("username");
                    }
                } catch (Exception ex) {
                    UtilsHandler.getMsg().sendErrorMsg(ConfigHandler.getPluginName(), "An error occurred while importing the player data.");
                    UtilsHandler.getMsg().sendErrorMsg(ConfigHandler.getPluginName(), "Please check the settings \"MySQL.LuckPerms\" in data.yml.");
                }
            }
        }
        // Getting the Server player list.
        OfflinePlayer[] offlinePlayers = Bukkit.getOfflinePlayers();
        for (OfflinePlayer offlinePlayer : offlinePlayers) {
            if (offlinePlayer.getName() == null || offlinePlayer.getName().equals("CMI-Fake-Operator"))
                continue;
            UtilsHandler.getFile().getMySQL().setValueWhere(ConfigHandler.getPluginName(),
                    "playerdataplus", "players", "uuid", offlinePlayer.getUniqueId().toString(),
                    "username", offlinePlayer.getName());
        }
    }

    @Override
    public boolean isPvPEnabled(Player player) {
        if (UtilsHandler.getDepend().PvPManagerEnabled()) {
            return PvPlayer.get(player).hasPvPEnabled();
        }
        return ConfigHandler.getConfigPath().isPvp();
    }

    @Override
    public boolean isAFK(Player player) {
        if (UtilsHandler.getDepend().CMIEnabled())
            return UtilsHandler.getDepend().getCmiAPI().isAFK(player);
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
                if (UtilsHandler.getDepend().VaultEnabled() && UtilsHandler.getDepend().getVaultAPI().getEconomy() != null) {
                    return UtilsHandler.getDepend().getVaultAPI().getEconomy().getBalance(Bukkit.getOfflinePlayer(uuid));
                }
                break;
            case "points":
                if (UtilsHandler.getDepend().PlayerPointsEnabled()) {
                    return UtilsHandler.getDepend().getPlayerPointsAPI().getBalance(uuid);
                }
                break;
            default:
                if (UtilsHandler.getDepend().GemsEconomyEnabled()) {
                    if (UtilsHandler.getDepend().getGemsEcoAPI().getCurrency(type) != null) {
                        return UtilsHandler.getDepend().getGemsEcoAPI().getBalance(uuid, type);
                    }
                }
                break;
        }
        UtilsHandler.getMsg().sendErrorMsg(ConfigHandler.getPluginName(), "Can not find price type: " + type);
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
                if (UtilsHandler.getDepend().VaultEnabled() && UtilsHandler.getDepend().getVaultAPI().getEconomy() != null) {
                    UtilsHandler.getDepend().getVaultAPI().getEconomy().withdrawPlayer(Bukkit.getOfflinePlayer(uuid), amount);
                    return UtilsHandler.getDepend().getVaultAPI().getBalance(uuid);
                }
                break;
            case "points":
                if (UtilsHandler.getDepend().PlayerPointsEnabled()) {
                    return UtilsHandler.getDepend().getPlayerPointsAPI().takePoints(uuid, amount);
                }
                break;
            default:
                if (UtilsHandler.getDepend().GemsEconomyEnabled()) {
                    if (UtilsHandler.getDepend().getGemsEcoAPI().getCurrency(type) != null) {
                        return UtilsHandler.getDepend().getGemsEcoAPI().withdraw(uuid, amount, type);
                    }
                }
                break;
        }
        UtilsHandler.getMsg().sendErrorMsg(ConfigHandler.getPluginName(), "Can not find price type: " + type);
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
                if (UtilsHandler.getDepend().VaultEnabled() && UtilsHandler.getDepend().getVaultAPI().getEconomy() != null) {
                    UtilsHandler.getDepend().getVaultAPI().getEconomy().depositPlayer(Bukkit.getOfflinePlayer(uuid), amount);
                    return UtilsHandler.getDepend().getVaultAPI().getBalance(uuid);
                }
                break;
            case "points":
                if (UtilsHandler.getDepend().PlayerPointsEnabled()) {
                    return UtilsHandler.getDepend().getPlayerPointsAPI().givePoints(uuid, amount);
                }
                break;
            default:
                if (UtilsHandler.getDepend().GemsEconomyEnabled()) {
                    if (UtilsHandler.getDepend().getGemsEcoAPI().getCurrency(type) != null) {
                        return UtilsHandler.getDepend().getGemsEcoAPI().deposit(uuid, amount, type);
                    }
                }
                break;
        }
        UtilsHandler.getMsg().sendErrorMsg(ConfigHandler.getPluginName(), "Can not find price type: " + type);
        return 0;
    }

    @Override
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
                if (UtilsHandler.getDepend().VaultEnabled() && UtilsHandler.getDepend().getVaultAPI().getEconomy() != null) {
                    double money = getCurrencyBalance(uuid, type);
                    UtilsHandler.getDepend().getVaultAPI().getEconomy().withdrawPlayer(Bukkit.getOfflinePlayer(uuid), money);
                    UtilsHandler.getDepend().getVaultAPI().getEconomy().depositPlayer(Bukkit.getOfflinePlayer(uuid), amount);
                    return amount;
                }
                break;
            case "points":
                if (UtilsHandler.getDepend().PlayerPointsEnabled()) {
                    return UtilsHandler.getDepend().getPlayerPointsAPI().givePoints(uuid, amount);
                }
                break;
            default:
                if (UtilsHandler.getDepend().GemsEconomyEnabled()) {
                    if (UtilsHandler.getDepend().getGemsEcoAPI().getCurrency(type) != null) {
                        return UtilsHandler.getDepend().getGemsEcoAPI().deposit(uuid, amount, type);
                    }
                }
                break;
        }
        UtilsHandler.getMsg().sendErrorMsg(ConfigHandler.getPluginName(), "Can not find price type: " + type);
        return 0;
    }

    @Override
    public float getExp(String pluginName, UUID uuid, int amount) {
        if (UtilsHandler.getDepend().MpdbEnabled()) {
            if (getOnlineStatus(uuid).equals("offline")) {
                try {
                    return Float.parseFloat(UtilsHandler.getFile().getMySQL().getValueWhere(pluginName, "MySQLPlayerDataBridge",
                            ConfigHandler.getConfigPath().getMySQLProp().get("MySQLPlayerDataBridge").getTables().get("Experience"),
                            "player_uuid", uuid.toString(), "total_exp"));
                } catch (Exception ex) {
                    UtilsHandler.getMsg().sendErrorMsg(pluginName,
                            "Can not get the Experience from");
                }
            }
            UtilsHandler.getMsg().sendErrorMsg(ConfigHandler.getPluginName(), "Target is in other server: " + uuid);
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
                if (UtilsHandler.getFile().getMySQL().isConnect(pluginName, "MySQLPlayerDataBridge")) {
                    String tableName = ConfigHandler.getConfigPath().getMySQLProp().get("MySQLPlayerDataBridge").getTables().get("Experience");
                    UtilsHandler.getFile().getMySQL().setValueWhere(pluginName, "MySQLPlayerDataBridge",
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
            if (UtilsHandler.getFile().getMySQL().isConnect(pluginName, "MySQLPlayerDataBridge")) {
                String tableName = ConfigHandler.getConfigPath().getMySQLProp().get("MySQLPlayerDataBridge").getTables().get("Experience");
                float exp = Float.parseFloat(UtilsHandler.getFile().getMySQL().getValueWhere(pluginName, "MySQLPlayerDataBridge",
                        tableName, "player_uuid", uuid.toString(), "total_exp"));
                exp += amount;
                UtilsHandler.getFile().getMySQL().setValueWhere(pluginName, "MySQLPlayerDataBridge",
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
            if (UtilsHandler.getDepend().MpdbEnabled()) {
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
        if (permission == null)
            return true;
        if (sender.hasPermission(permission + ".*"))
            return true;
        return sender.hasPermission(permission) || sender.isOp() || (sender instanceof ConsoleCommandSender);
    }

    @Override
    public boolean hasPerm(Player player, String permission) {
        if (permission == null)
            return true;
        if (player.hasPermission(permission + ".*"))
            return true;
        return player.hasPermission(permission) || player.isOp() || (player instanceof ConsoleCommandSender);
    }

    @Override
    public boolean hasPerm(UUID uuid, String permission) {
        if (permission == null)
            return true;
        OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(uuid);
        if (offlinePlayer.isOp())
            return true;
        if (UtilsHandler.getDepend().LuckPermsEnabled()) {
            return UtilsHandler.getDepend().getLuckPermsAPI().hasPermission(uuid, permission) ||
                    UtilsHandler.getDepend().getLuckPermsAPI().hasPermission(uuid, permission + ".*");
        }
        return UtilsHandler.getDepend().getVaultAPI().getPermissions().playerHas(Bukkit.getWorlds().get(0).getName(), offlinePlayer, permission);
    }

    @Override
    public boolean hasPerm(OfflinePlayer offlinePlayer, String permission) {
        if (permission == null)
            return true;
        if (offlinePlayer.isOp())
            return true;
        if (UtilsHandler.getDepend().LuckPermsEnabled()) {
            return UtilsHandler.getDepend().getLuckPermsAPI().hasPermission(offlinePlayer.getUniqueId(), permission) ||
                    UtilsHandler.getDepend().getLuckPermsAPI().hasPermission(offlinePlayer.getUniqueId(), permission + ".*");
        }
        return UtilsHandler.getDepend().getVaultAPI().getPermissions().playerHas(Bukkit.getWorlds().get(0).getName(), offlinePlayer, permission);
    }

    @Override
    public boolean havePerm(List<CommandSender> senders, String permission) {
        if (permission == null)
            return true;
        for (CommandSender sender : senders) {
            if (hasPerm(sender, permission))
                return true;
        }
        return false;
    }

    @Override
    public boolean havePermPlayer(List<Player> players, String permission) {
        if (permission == null)
            return true;
        for (Player player : players) {
            if (hasPerm(player, permission))
                return true;
        }
        return false;
    }

    @Override
    public boolean havePermUUID(List<UUID> uuids, String permission) {
        if (permission == null)
            return true;
        for (UUID uuid : uuids) {
            return hasPerm(uuid, permission);
        }
        return false;
    }

    @Override
    public boolean havePermOffline(List<OfflinePlayer> offlinePlayers, String permission) {
        if (permission == null)
            return true;
        for (OfflinePlayer offlinePlayer : offlinePlayers) {
            return hasPerm(offlinePlayer, permission);
        }
        return false;
    }

    @Override
    public boolean allHavePerm(List<CommandSender> senders, String permission) {
        if (permission == null)
            return true;
        for (CommandSender sender : senders) {
            if (!hasPerm(sender, permission))
                return false;
        }
        return true;
    }

    @Override
    public boolean allHavePermPlayer(List<Player> players, String permission) {
        if (permission == null)
            return true;
        for (Player player : players) {
            if (!hasPerm(player, permission))
                return false;
        }
        return true;
    }

    @Override
    public boolean allHavePermUUID(List<UUID> uuids, String permission) {
        if (permission == null)
            return true;
        for (UUID uuid : uuids) {
            if (!hasPerm(uuid, permission))
                return false;
        }
        return true;
    }

    @Override
    public boolean allHavePermOffline(List<OfflinePlayer> offlinePlayers, String permission) {
        if (permission == null)
            return true;
        for (OfflinePlayer offlinePlayer : offlinePlayers) {
            if (!hasPerm(offlinePlayer, permission))
                return false;
        }
        return true;
    }

    @Override
    public int getMaxPerm(CommandSender sender, String permission, int def) {
        if (permission == null)
            return def;
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
        if (permission == null)
            return def;
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
        if (permission == null)
            return def;
        List<Integer> numbers = new ArrayList<>();
        for (String perm : UtilsHandler.getDepend().getLuckPermsAPI().getAllPerms(uuid)) {
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
        if (permission == null)
            return def;
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
        if (permission == null)
            return def;
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
        if (permission == null)
            return def;
        List<Integer> numbers = new ArrayList<>();
        for (String perm : UtilsHandler.getDepend().getLuckPermsAPI().getAllPerms(uuid)) {
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
        return UtilsHandler.getDepend().getLuckPermsAPI().getPlayerPrimaryGroup(uuid);
    }

    @Override
    public boolean setPrimaryGroup(UUID uuid, String group) {
        if (!UtilsHandler.getDepend().LuckPermsEnabled()) {
            return false;
        }
        return UtilsHandler.getDepend().getLuckPermsAPI().setPlayerPrimaryGroup(uuid, group);
    }

    @Override
    public boolean isInheritedGroup(UUID uuid, String group) {
        if (!UtilsHandler.getDepend().LuckPermsEnabled()) {
            return false;
        }
        return UtilsHandler.getDepend().getLuckPermsAPI().isInheritedGroup(uuid, group);
    }

    @Override
    public void addPermission(UUID uuid, String permission) {
        if (UtilsHandler.getDepend().LuckPermsEnabled()) {
            UtilsHandler.getDepend().getLuckPermsAPI().addPermission(uuid, permission);
            return;
        }
        UtilsHandler.getDepend().getVaultAPI().getPermissions().playerAdd(Bukkit.getWorlds().get(0).getName(),
                Bukkit.getOfflinePlayer(uuid), permission);
    }

    @Override
    public void removePermission(UUID uuid, String permission) {
        if (UtilsHandler.getDepend().LuckPermsEnabled()) {
            UtilsHandler.getDepend().getLuckPermsAPI().removePermission(uuid, permission);
            return;
        }
        UtilsHandler.getDepend().getVaultAPI().getPermissions().playerRemove(Bukkit.getWorlds().get(0).getName(),
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

    @Override
    public void setDiscordNick(UUID uuid, String nickname) {
        UtilsHandler.getDepend().getDiscordAPI().setMemberNick(uuid, nickname);
    }

    @Override
    public String getDiscordNick(UUID uuid) {
        return UtilsHandler.getDepend().getDiscordAPI().getMemberNick(uuid);
    }

    @Override
    public String getDiscordName(UUID uuid) {
        return UtilsHandler.getDepend().getDiscordAPI().getName(uuid);
    }

    @Override
    public Material getResSelectionTool() {
        return Residence.getInstance().getConfigManager().getSelectionTool().getMaterial();
    }

}