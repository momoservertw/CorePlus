package tw.momocraft.coreplus.utils.player;

import com.Zrips.CMI.CMI;
import com.Zrips.CMI.Containers.CMIUser;
import com.Zrips.CMI.Modules.Homes.CmiHome;
import com.bekvon.bukkit.residence.Residence;
import me.NoChance.PvPManager.PvPlayer;
import net.craftersland.data.bridge.PD;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.permissions.PermissionAttachmentInfo;
import tw.momocraft.coreplus.api.CorePlusAPI;
import tw.momocraft.coreplus.api.PlayerInterface;
import tw.momocraft.coreplus.handlers.ConfigHandler;
import tw.momocraft.coreplus.handlers.UtilsHandler;

import java.net.URL;
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
    public double getMoney(String pluginName, UUID uuid) {
        if (UtilsHandler.getDepend().MpdbEnabled()) {
            return PD.api.getDatabaseMoney(uuid);
        }
        if (UtilsHandler.getDepend().CMIEnabled()) {
            CMIUser user = CMI.getInstance().getPlayerManager().getUser(uuid);
            if (user != null) {
                return user.getBalance();
            }
        }
        if (UtilsHandler.getDepend().VaultEnabled()) {
            if (UtilsHandler.getDepend().getVaultAPI().getEconomy() != null) {
                return UtilsHandler.getDepend().getVaultAPI().getEconomy().getBalance(Bukkit.getOfflinePlayer(uuid));
            }
        }
        UtilsHandler.getMsg().sendErrorMsg(pluginName, "Can not find Economy plugins.");
        return 0;
    }

    @Override
    public double getPlayerPoints(String pluginName, UUID uuid) {
        if (!UtilsHandler.getDepend().PlayerPointsEnabled()) {
            String[] placeHolders = UtilsHandler.getMsg().newString();
            placeHolders[2] = "PlayerPoints"; // %plugin%
            UtilsHandler.getMsg().sendLangMsg(pluginName,
                    "dependNotFound", Bukkit.getConsoleSender(), placeHolders);
            return 0;
        }
        return UtilsHandler.getDepend().getPlayerPointsAPI().getBalance(uuid);
    }

    @Override
    public void takeMoney(String pluginName, UUID uuid, double amount) {
        if (UtilsHandler.getDepend().CMIEnabled()) {
            CMIUser user = CMI.getInstance().getPlayerManager().getUser(uuid);
            if (user != null) {
                user.withdraw(amount);
                return;
            }
        }
        if (UtilsHandler.getDepend().MpdbEnabled()) {
            double money = PD.api.getDatabaseMoney(uuid);
            money -= amount;
            PD.api.setDatabaseMoney(uuid, money);
            return;
        }
        if (UtilsHandler.getDepend().VaultEnabled()) {
            if (UtilsHandler.getDepend().getVaultAPI().getEconomy() != null) {
                UtilsHandler.getDepend().getVaultAPI().getEconomy().withdrawPlayer(Bukkit.getOfflinePlayer(uuid), amount);
                return;
            }
        }
        UtilsHandler.getMsg().sendErrorMsg(pluginName, "Can not find Economy plugins.");
    }

    @Override
    public void takePlayerPoints(String pluginName, UUID uuid, double amount) {
        if (!UtilsHandler.getDepend().PlayerPointsEnabled()) {
            String[] placeHolders = UtilsHandler.getMsg().newString();
            placeHolders[2] = "PlayerPoints"; // %plugin%
            UtilsHandler.getMsg().sendLangMsg(pluginName,
                    "dependNotFound", Bukkit.getConsoleSender(), placeHolders);
            return;
        }
        UtilsHandler.getDepend().getPlayerPointsAPI().takePoints(uuid, amount);
    }

    @Override
    public void giveMoney(String pluginName, UUID uuid, double amount) {
        if (UtilsHandler.getDepend().CMIEnabled()) {
            try {
                CMIUser user = CMI.getInstance().getPlayerManager().getUser(uuid);
                if (user != null)
                    user.deposit(amount);
                else
                    CorePlusAPI.getMsg().sendErrorMsg(pluginName,
                            "Can not find the CMI user: " + uuid.toString());
                return;
            } catch (Exception ex) {
                CorePlusAPI.getMsg().sendErrorMsg(pluginName,
                        "Can not find the CMI user: " + uuid.toString());
                return;
            }
        }
        if (UtilsHandler.getDepend().MpdbEnabled()) {
            double money = PD.api.getDatabaseMoney(uuid);
            money += amount;
            PD.api.setDatabaseMoney(uuid, money);
            return;
        }
        if (UtilsHandler.getDepend().VaultEnabled()) {
            if (CorePlusAPI.getDepend().VaultEconEnabled()) {
                UtilsHandler.getDepend().getVaultAPI().getEconomy().depositPlayer(
                        Bukkit.getOfflinePlayer(uuid), amount);
                return;
            }
        }
        UtilsHandler.getMsg().sendErrorMsg(pluginName, "Can not find Economy plugins.");
    }

    @Override
    public void givePlayerPoints(String pluginName, UUID uuid, double amount) {
        if (!UtilsHandler.getDepend().PlayerPointsEnabled()) {
            String[] placeHolders = UtilsHandler.getMsg().newString();
            placeHolders[2] = "PlayerPoints"; // %plugin%
            UtilsHandler.getMsg().sendLangMsg(pluginName,
                    "dependNotFound", Bukkit.getConsoleSender(), placeHolders);
            return;
        }
        UtilsHandler.getDepend().getPlayerPointsAPI().givePoints(uuid, amount);
    }

    @Override
    public void setMoney(String pluginName, UUID uuid, double amount) {
        if (UtilsHandler.getDepend().CMIEnabled()) {
            double money = getMoney(pluginName, uuid);
            CMIUser user = CMI.getInstance().getPlayerManager().getUser(uuid);
            if (user != null) {
                user.withdraw(money);
                user.deposit(money);
                return;
            }
        }
        if (UtilsHandler.getDepend().MpdbEnabled()) {
            PD.api.setDatabaseMoney(uuid, amount);
            return;
        }
        if (UtilsHandler.getDepend().VaultEnabled() && UtilsHandler.getDepend().getVaultAPI().getEconomy() != null) {
            double money = getMoney(pluginName, uuid);
            UtilsHandler.getDepend().getVaultAPI().getEconomy().withdrawPlayer(Bukkit.getOfflinePlayer(uuid), money);
            UtilsHandler.getDepend().getVaultAPI().getEconomy().depositPlayer(Bukkit.getOfflinePlayer(uuid), amount);
            return;
        }
        UtilsHandler.getMsg().sendErrorMsg(pluginName, "Can not find Economy plugins.");
    }

    @Override
    public void setPoints(String pluginName, UUID uuid, double amount) {
        if (!UtilsHandler.getDepend().PlayerPointsEnabled()) {
            String[] placeHolders = UtilsHandler.getMsg().newString();
            placeHolders[2] = "PlayerPoints"; // %plugin%
            UtilsHandler.getMsg().sendLangMsg(pluginName,
                    "dependNotFound", Bukkit.getConsoleSender(), placeHolders);
            return;

        }
        UtilsHandler.getDepend().getPlayerPointsAPI().setPoints(uuid, amount);
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
    public boolean hasPerm(CommandSender sender, String permission, boolean allowOp) {
        if (permission == null)
            return true;
        if (sender.isOp() && allowOp)
            return true;
        if (sender.hasPermission(permission + ".*"))
            return true;
        return sender.hasPermission(permission) || sender instanceof ConsoleCommandSender;
    }

    @Override
    public boolean hasPerm(CommandSender sender, String permission) {
        return hasPerm(sender, permission, true);
    }

    @Override
    public boolean hasPerm(Player player, String permission, boolean allowOp) {
        if (permission == null)
            return true;
        if (player.isOp() && allowOp)
            return true;
        if (player.hasPermission(permission + ".*"))
            return true;
        return player.hasPermission(permission) || player instanceof ConsoleCommandSender;
    }

    @Override
    public boolean hasPerm(Player player, String permission) {
        return hasPerm(player, permission, true);
    }

    @Override
    public boolean hasPerm(UUID uuid, String permission, boolean allowOp) {
        return hasPerm(Bukkit.getOfflinePlayer(uuid), permission, allowOp);
    }

    @Override
    public boolean hasPerm(UUID uuid, String permission) {
        return hasPerm(uuid, permission, true);
    }

    @Override
    public boolean hasPerm(OfflinePlayer offlinePlayer, String permission, boolean allowOp) {
        if (permission == null)
            return true;
        if (offlinePlayer.isOp() && allowOp)
            return true;
        if (UtilsHandler.getDepend().LuckPermsEnabled())
            return UtilsHandler.getDepend().getLuckPermsAPI().hasPermission(offlinePlayer.getUniqueId(), permission) ||
                    UtilsHandler.getDepend().getLuckPermsAPI().hasPermission(offlinePlayer.getUniqueId(), permission + ".*");
        return UtilsHandler.getDepend().getVaultAPI().getPermissions().playerHas(
                Bukkit.getWorlds().get(0).getName(), offlinePlayer, permission);
    }

    @Override
    public boolean hasPerm(OfflinePlayer offlinePlayer, String permission) {
        return hasPerm(offlinePlayer, permission, true);
    }

    @Override
    public boolean havePerm(List<CommandSender> senders, String permission, boolean allowOp) {
        if (permission == null)
            return true;
        for (CommandSender sender : senders)
            if (hasPerm(sender, permission, allowOp))
                return true;
        return false;
    }

    @Override
    public boolean havePerm(List<CommandSender> senders, String permission) {
        return havePerm(senders, permission, true);
    }

    @Override
    public boolean havePermPlayer(List<Player> players, String permission, boolean allowOp) {
        if (permission == null)
            return true;
        for (Player player : players)
            if (hasPerm(player, permission, allowOp))
                return true;
        return false;
    }

    @Override
    public boolean havePermPlayer(List<Player> players, String permission) {
        return havePermPlayer(players, permission, true);
    }

    @Override
    public boolean havePermUUID(List<UUID> uuids, String permission, boolean allowOp) {
        if (permission == null)
            return true;
        for (UUID uuid : uuids)
            return hasPerm(uuid, permission, allowOp);
        return false;
    }

    @Override
    public boolean havePermUUID(List<UUID> uuids, String permission) {
        return havePermUUID(uuids, permission, true);
    }

    @Override
    public boolean havePermOffline(List<OfflinePlayer> offlinePlayers, String permission, boolean allowOp) {
        if (permission == null)
            return true;
        for (OfflinePlayer offlinePlayer : offlinePlayers)
            return hasPerm(offlinePlayer, permission, allowOp);
        return false;
    }

    @Override
    public boolean havePermOffline(List<OfflinePlayer> offlinePlayers, String permission) {
        return havePermOffline(offlinePlayers, permission, true);
    }

    @Override
    public boolean allHavePerm(List<CommandSender> senders, String permission, boolean allowOp) {
        if (permission == null)
            return true;
        for (CommandSender sender : senders)
            if (!hasPerm(sender, permission, allowOp))
                return false;
        return true;
    }

    @Override
    public boolean allHavePerm(List<CommandSender> senders, String permission) {
        return allHavePerm(senders, permission);
    }

    @Override
    public boolean allHavePermPlayer(List<Player> players, String permission, boolean allowOp) {
        if (permission == null)
            return true;
        for (Player player : players)
            if (!hasPerm(player, permission, allowOp))
                return false;
        return true;
    }

    @Override
    public boolean allHavePermPlayer(List<Player> players, String permission) {
        return allHavePermPlayer(players, permission, true);
    }

    @Override
    public boolean allHavePermUUID(List<UUID> uuids, String permission, boolean allowOp) {
        if (permission == null)
            return true;
        for (UUID uuid : uuids)
            if (!hasPerm(uuid, permission, allowOp))
                return false;
        return true;
    }

    @Override
    public boolean allHavePermUUID(List<UUID> uuids, String permission) {
        return allHavePermUUID(uuids, permission, true);
    }

    @Override
    public boolean allHavePermOffline(List<OfflinePlayer> offlinePlayers, String permission, boolean allowOp) {
        if (permission == null)
            return true;
        for (OfflinePlayer offlinePlayer : offlinePlayers)
            if (!hasPerm(offlinePlayer, permission, allowOp))
                return false;
        return true;
    }

    @Override
    public boolean allHavePermOffline(List<OfflinePlayer> offlinePlayers, String permission) {
        return allHavePermOffline(offlinePlayers, permission, true);
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
    public Set<String> getLuckPermsInheritedGroups(UUID uuid) {
        return UtilsHandler.getDepend().getLuckPermsAPI().getInheritedGroups(uuid);
    }

    @Override
    public List<String> getLuckPermsAllPerms(UUID uuid) {
        return UtilsHandler.getDepend().getLuckPermsAPI().getAllPerms(uuid);
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

    @Override
    public ItemStack getSlotItem(Player player, String slot) {
        // Inventory
        if (slot.matches("[0-9]+"))
            return player.getInventory().getItem(Integer.parseInt(slot));
        return switch (slot) {
            // Equipment
            case "head" -> player.getInventory().getHelmet();
            case "chest" -> player.getInventory().getChestplate();
            case "legs" -> player.getInventory().getLeggings();
            case "feet" -> player.getInventory().getBoots();
            case "hand" -> player.getInventory().getItemInMainHand();
            case "off_hand" -> player.getInventory().getItemInOffHand();
            // Crafting
            case "crafting[1]", "crafting[2]", "crafting[3]", "crafting[4]" -> player.getOpenInventory().getTopInventory().getItem(Integer.parseInt(slot.replace("CRAFTING[", "").replace("]", "")));
            default -> null;
        };
    }

    @Override
    public void cleanSlot(Player player, String slot) {
        // Inventory
        if (slot.matches("[0-9]+"))
            player.getInventory().setItem(Integer.parseInt(slot), null);
        switch (slot) {
            // ALL
            case "all":
                player.getInventory().clear();
                break;
            // Equipment
            case "head":
                player.getInventory().setHelmet(null);
                break;
            case "chest":
                player.getInventory().setChestplate(null);
                break;
            case "legs":
                player.getInventory().setLeggings(null);
                break;
            case "feet":
                player.getInventory().setBoots(null);
                break;
            case "hand":
                player.getInventory().setItemInMainHand(null);
                break;
            case "off_hand":
                player.getInventory().setItemInOffHand(null);
                break;
            // Crafting
            case "crafting[1]":
            case "crafting[2]":
            case "crafting[3]":
            case "crafting[4]":
                player.getOpenInventory().getTopInventory().setItem(Integer.parseInt(slot.replace("CRAFTING[", "").replace("]", "")), null);
        }
    }

    @Override
    public boolean isInventoryFull(Player player) {
        PlayerInventory playerInventory = player.getInventory();
        for (int i = 0; i <= 35; i++)
            if (playerInventory.getItem(i) == null)
                return true;
        return false;
    }

    @Override
    public boolean isInventoryFull(Player player, Material material, int itemNumber) {
        double itemMaxSize = material.getMaxStackSize();
        int itemStacks = (int) Math.ceil(itemNumber / itemMaxSize);
        double itemRemain = itemNumber % itemMaxSize;

        PlayerInventory playerInventory = player.getInventory();
        int slotEmpty = 0;
        int slotRemain = 0;
        ItemStack slotItem;
        for (int i = 0; i <= 35; i++) {
            slotItem = playerInventory.getItem(i);
            if (slotItem == null) {
                slotEmpty++;
            } else {
                if (slotItem.getType() == material) {
                    slotRemain += itemMaxSize - slotItem.getAmount();
                    if (slotRemain >= itemMaxSize) {
                        slotRemain -= itemMaxSize;
                        slotEmpty++;
                    }
                }
            }
        }
        if (slotEmpty < itemStacks)
            return true;
        return slotRemain < itemRemain;
    }

    @Override
    public int getInventoryEmptySlot(Player player) {
        PlayerInventory playerInventory = player.getInventory();
        for (int i = 0; i <= 35; i++)
            if (playerInventory.getItem(i) == null)
                return i;
        return -1;
    }

    @Override
    public Map<String, Location> getCMIHomes(String playerName) {
        Map<String, Location> map = new HashMap<>();
        for (Map.Entry<String, CmiHome> entry : CMI.getInstance().getPlayerManager().getUser(playerName).getHomes().entrySet())
            map.put(entry.getKey(), entry.getValue().getLoc().getBukkitLoc());
        return map;
    }

    @Override
    public Map<String, Location> getCMIHomes(UUID uuid) {
        Map<String, Location> map = new HashMap<>();
        for (Map.Entry<String, CmiHome> entry : CMI.getInstance().getPlayerManager().getUser(uuid).getHomes().entrySet())
            map.put(entry.getKey(), entry.getValue().getLoc().getBukkitLoc());
        return map;
    }
}