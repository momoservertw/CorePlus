package tw.momocraft.coreplus.api;

import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.UUID;

public interface PlayerInterface {

    /**
     * Getting player from a name.
     *
     * @param playerName the name of this player
     * @return the player from a name or null.
     */
    Player getPlayerString(String playerName);

    /**
     * Getting offline player from a name.
     *
     * @param playerName the name of this player
     * @return the player from a name or null.
     */
    OfflinePlayer getOfflinePlayer(String playerName);

    boolean isPvPEnabled(Player player, boolean def);

    /**
     * Getting the amount of currency of player.
     *
     * @param uuid the uuid of player.
     * @param type the type of currency. Types: money, points, GemsEconomyAPI
     * @return the amount of currency of player.
     */
    double getTypeBalance(UUID uuid, String type);

    /**
     * Taking the amount of currency from player.
     *
     * @param uuid   the uuid of player.
     * @param type   the type of currency. Types: money, points, GemsEconomyAPI
     * @param amount the amount to take.
     * @return the name amount of currency of player.
     */
    double takeTypeMoney(UUID uuid, String type, double amount);

    /**
     * Giving the amount of currency from player.
     *
     * @param uuid   the uuid of player.
     * @param type   the type of currency. Types: money, points, GemsEconomy Currency
     * @param amount the amount to take.
     * @return the name amount of currency of player.
     */
    double giveTypeMoney(UUID uuid, String type, double amount);

    /**
     * Checking if the sender has permission.
     *
     * @param sender     the checking player or console.
     * @param permission the permission node.
     * @return if the sender has permission.
     */
    boolean hasPerm(CommandSender sender, String permission);

    /**
     * Checking if the player has permission.
     *
     * @param player     the checking player.
     * @param permission the permission node.
     * @return if the player has permission.
     */
    boolean hasPerm(Player player, String permission);

    /**
     * Checking if the uuid of player has permission.
     *
     * @param uuid       the checking uuid.
     * @param permission the permission node.
     * @return if the uuid of player has permission.
     */
    boolean hasPerm(UUID uuid, String permission);

    /**
     * Checking if the offline player has permission.
     *
     * @param offlinePlayer the checking offlinePlayer.
     * @param permission    the permission node.
     * @return if the offline player has permission.
     */
    boolean hasPerm(OfflinePlayer offlinePlayer, String permission);

    /**
     * Checking if one of the sender has permission.
     *
     * @param senders    the checking players or console.
     * @param permission the permission node.
     * @return if one of the sender has permission.
     */
    boolean havePerm(List<CommandSender> senders, String permission);

    /**
     * Checking if one of the player has permission.
     *
     * @param players    the checking player list.
     * @param permission the permission node.
     * @return if one of the player has permission.
     */
    boolean havePermPlayer(List<Player> players, String permission);

    /**
     * Checking if one of the uuid has permission.
     *
     * @param uuids      the checking uuid list.
     * @param permission the permission node.
     * @return if one of the uuid of player has permission.
     */
    boolean havePermUUID(List<UUID> uuids, String permission);

    /**
     * Checking if one of the offlinePlayer has permission.
     *
     * @param offlinePlayers the checking offlinePlayer list.
     * @param permission     the permission node.
     * @return if one of the offlinePlayer has permission.
     */
    boolean havePermOffline(List<OfflinePlayer> offlinePlayers, String permission);

    /**
     * Checking if all of the senders have permission.
     *
     * @param senders    the checking sender list.
     * @param permission the permission node.
     * @return if all of the senders have permission.
     */
    boolean allHavePerm(List<CommandSender> senders, String permission);

    /**
     * Checking if all of the players have permission.
     *
     * @param players    the checking player list.
     * @param permission the permission node.
     * @return if all of the players have permission.
     */
    boolean allHavePermPlayer(List<Player> players, String permission);

    /**
     * Checking if all of the uuids have permission.
     *
     * @param uuids      the checking uuid list.
     * @param permission the permission node.
     * @return if all of the uuids of players have permission.
     */
    boolean allHavePermUUID(List<UUID> uuids, String permission);

    /**
     * Checking if all of the offlinePlayers have permission.
     *
     * @param offlinePlayers the checking offlinePlayer list.
     * @param permission     the permission node.
     * @return if all of the offlinePlayers have permission.
     */
    boolean allHavePermOffline(List<OfflinePlayer> offlinePlayers, String permission);

    /**
     * Getting the highest level of permission of sender.
     *
     * @param sender     the checking player or console.
     * @param permission the start with permission node.
     * @param def        the default value if the sender doesn't have any permission.
     * @return the highest level of permission of sender.
     */
    int getMaxPerm(CommandSender sender, String permission, int def);

    /**
     * Getting the highest level of permission of sender.
     *
     * @param player     the checking player.
     * @param permission the start with permission node.
     * @param def        the default value if the player doesn't have any permission.
     * @return the highest level of permission of sender.
     */
    int getMaxPerm(Player player, String permission, int def);

    /**
     * Getting the highest level of permission of sender.
     *
     * @param uuid       the checking uuid.
     * @param permission the start with permission node.
     * @param def        the default value if the player doesn't have any permission.
     * @return the highest level of permission of sender.
     */
    int getMaxPerm(UUID uuid, String permission, int def);


    /**
     * Getting the lowest level of permission of sender.
     *
     * @param sender     the checking player or console.
     * @param permission the start with permission node.
     * @param def        the default value if the sender doesn't have any permission.
     * @return the lowest level of permission of sender.
     */
    int getMinPerm(CommandSender sender, String permission, int def);

    /**
     * Getting the highest level of permission of sender.
     *
     * @param player     the checking player.
     * @param permission the start with permission node.
     * @param def        the default value if the player doesn't have any permission.
     * @return the hightest level of permission of sender.
     */
    int getMinPerm(Player player, String permission, int def);

    /**
     * Getting the lowest level of permission of sender.
     *
     * @param uuid       the checking uuid.
     * @param permission the start with permission node.
     * @param def        the default value if the player doesn't have any permission.
     * @return the lowest level of permission of sender.
     */
    int getMinPerm(UUID uuid, String permission, int def);

    /**
     * Getting the primary group of player.
     * Need: LuckPerms
     *
     * @param uuid the uuid of player.
     * @return the primary group of player.
     */
    String getPrimaryGroup(UUID uuid);

    /**
     * Setting the primary group for player.
     *
     * @param uuid  the uuid of player.
     * @param group the new primary group of player
     * @return if the process succeed. It may be failed if can not find the player or group.
     */
    boolean setPrimaryGroup(UUID uuid, String group);

    /**
     * Checking if player is inherited a group.
     *
     * @param uuid  the target uuid.
     * @param group the checking group name.
     * @return if player is in a group.
     */
    boolean isInheritedGroup(UUID uuid, String group);

    /**
     * Adding a permission for player.
     *
     * @param uuid       the uuid of player.
     * @param permission the permission node.
     */
    void addPermission(UUID uuid, String permission);

    /**
     * Removing a permission for player.
     *
     * @param uuid       the uuid of player.
     * @param permission the permission node.
     */
    void removePermission(UUID uuid, String permission);

    /**
     * To promote a permission number for a player.
     *
     * @param uuid       the uuid of player.
     * @param permission the permission node.
     * @param number     the value of promote/demote level.
     * @param def        the default value if the player doesn't have any permission.
     */
    void changePermLevel(UUID uuid, String permission, int number, int def);
}
