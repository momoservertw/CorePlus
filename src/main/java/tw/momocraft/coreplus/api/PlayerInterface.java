package tw.momocraft.coreplus.api;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

public interface PlayerInterface {
    /**
     * @param uuid the uuid of this player
     * @return if the player had player before. Only support
     */
    boolean isPlayerPlayerBefore(UUID uuid);

    /**
     * @param playerName the name of this player
     * @return if the player had player before.
     */
    boolean isPlayerPlayerBefore(String playerName);

    /**
     * @param playerName the name of this player
     * @return if the player is online or in other server.
     */
    boolean isPlayerOnline(String playerName);

    /**
     * @param uuid the uuid of this player
     * @return if the player is online or in other server.
     */
    boolean isPlayerOnline(UUID uuid);

    /**
     * @param playerName the name of this player
     * @return if the player is online in the server.
     */
    boolean isPlayerOnlineServer(String playerName);

    /**
     * @param uuid the uuid of this player
     * @return if the player is online in the server.
     */
    boolean isPlayerOnlineServer(UUID uuid);

    /**
     * @param sender the target sender.
     * @return the target player. It will return "null" if sender is not player.
     */
    Player getPlayer(CommandSender sender);

    /**
     * @param playerName the name of this player
     * @return the player from a name or null.
     */
    Player getPlayer(String playerName);

    /**
     * @param uuid the uuid of this player
     * @return the player from a name or null.
     */
    Player getPlayer(UUID uuid);

    /**
     * @param playerName the playerName of this player
     * @return the display name of the player.
     * <p>
     * Will return null if player not online and CMI not enabled.
     */
    String getPlayerDisplayName(String playerName);

    /**
     * @param uuid the uuid of this player
     * @return the display name of the player. Return null if can not found the data of this player.
     * <p>
     * Will return null if player not online and CMI not enabled.
     */
    String getPlayerDisplayName(UUID uuid);

    /**
     * Getting player uuid from a name.
     * plguins(LuckPerms > AuthMe > Local > Mojang)
     *
     * @param playerName the name of this player
     * @return the player's uuid.
     */
    UUID getPlayerUUID(String playerName);

    /**
     * Getting player uuid from a name.
     *
     * @param playerName the name of this player
     * @return the player's uuid.
     */
    UUID getPlayerUUIDLocal(String playerName);

    /**
     * Getting player uuid from a name.
     *
     * @param playerName the name of this player
     * @return the player's uuid.
     */
    UUID getPlayerUUIDMojang(String playerName);

    /**
     * Getting offline player from a name.
     *
     * @param playerName the name of this player
     * @return the player from a name or null.
     */
    OfflinePlayer getOfflinePlayer(String playerName);

    /**
     * Getting offline player from a uuid.
     *
     * @param uuid the name uuid this player
     * @return the player from a uuid or null.
     */
    OfflinePlayer getOfflinePlayer(UUID uuid);

    /**
     * Getting online player names.
     *
     * @return online player names.
     */
    List<String> getOnlinePlayerNames();

    /**
     * Getting the player local language.
     *
     * @param player the target player.
     * @return the player local language.
     */
    String getPlayerLocal(Player player);

    /**
     * Getting the sender local language.
     *
     * @param sender the target sender.
     * @return the sender local language.
     */
    String getPlayerLocal(CommandSender sender);

    /**
     * Getting the last login time(milliseconds) of player.
     *
     * @param playerName the name of player.
     * @return the last login time(milliseconds) of player.
     */
    double getLastLogin(String playerName);

    /**
     * Getting the last login time(milliseconds) of player.
     *
     * @param uuid the uuid of player.
     * @return the last login time(milliseconds) of player.
     */
    double getLastLogin(UUID uuid);


    /**
     * Checking if player is enable pvp.
     *
     * @param player the target player.
     * @return if player is enable pvp.
     */
    boolean isPvPEnabled(Player player);

    /**
     * Checking if player is in AFK mode.
     *
     * @param player the target player.
     * @return if player is in AFK mode.
     */
    boolean isAFK(Player player);

    /**
     * Getting the amount of money of player.
     *
     * @param pluginName the sent plugin name.
     * @param uuid       the uuid of player.
     * @return the amount of currency of player.
     */
    double getMoney(String pluginName, UUID uuid);

    /**
     * Getting the amount of PlayerPoints of player.
     *
     * @param pluginName the sent plugin name.
     * @param uuid       the uuid of player.
     * @return the amount of currency of player.
     */
    double getPlayerPoints(String pluginName, UUID uuid);

    /**
     * Taking the amount of money of player.
     *
     * @param pluginName the sent plugin name.
     * @param uuid       the uuid of player.
     */
    void takeMoney(String pluginName, UUID uuid, double amount);

    /**
     * Take the amount of PlayerPoints of player.
     *
     * @param pluginName the sent plugin name.
     * @param uuid       the uuid of player.
     */
    void takePlayerPoints(String pluginName, UUID uuid, double amount);

    /**
     * Give the amount of money of player.
     *
     * @param pluginName the sent plugin name.
     * @param uuid       the uuid of player.
     */
    void giveMoney(String pluginName, UUID uuid, double amount);

    /**
     * Give the amount of PlayerPoints of player.
     *
     * @param pluginName the sent plugin name.
     * @param uuid       the uuid of player.
     */
    void givePlayerPoints(String pluginName, UUID uuid, double amount);

    /**
     * Set the amount of money of player.
     *
     * @param pluginName the sent plugin name.
     * @param uuid       the uuid of player.
     */
    void setMoney(String pluginName, UUID uuid, double amount);

    /**
     * Set the amount of PlayerPoints of player.
     *
     * @param pluginName the sent plugin name.
     * @param uuid       the uuid of player.
     */
    void setPoints(String pluginName, UUID uuid, double amount);

    /**
     * Giving the amount of exp from player.
     *
     * @param pluginName the sending plugin name.
     * @param uuid       the uuid of player.
     * @param amount     the amount to take.
     * @return the name amount of exp of player.
     */
    float getExp(String pluginName, UUID uuid, int amount);

    /**
     * Setting the amount of exp from player.
     *
     * @param pluginName the sending plugin name.
     * @param uuid       the uuid of player.
     * @param amount     the amount to take.
     */
    void setExp(String pluginName, UUID uuid, int amount);

    /**
     * Giving the amount of exp from player.
     *
     * @param pluginName the sending plugin name.
     * @param uuid       the uuid of player.
     * @param amount     the amount to take.
     */
    void giveExp(String pluginName, UUID uuid, int amount);


    /**
     * Checking if the target has permission.
     *
     * @param sender     the target player or console.
     * @param permission the permission node.
     * @param allowOp    bypass if target is server OP.
     * @return if the sender has permission.
     */
    boolean hasPerm(CommandSender sender, String permission, boolean allowOp);

    /**
     * Checking if the target has permission.
     *
     * @param sender     the target player or console.
     * @param permission the permission node.
     * @return if the sender has permission.
     */
    boolean hasPerm(CommandSender sender, String permission);

    /**
     * Checking if the player has permission.
     *
     * @param player     the checking player.
     * @param permission the permission node.
     * @param allowOp    bypass if target is server OP.
     * @return if the player has permission.
     */
    boolean hasPerm(Player player, String permission, boolean allowOp);

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
     * @param allowOp    bypass if target is server OP.
     * @return if the uuid of player has permission.
     */
    boolean hasPerm(UUID uuid, String permission, boolean allowOp);

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
     * @param allowOp       bypass if target is server OP.
     * @return if the offline player has permission.
     */
    boolean hasPerm(OfflinePlayer offlinePlayer, String permission, boolean allowOp);

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
     * @param allowOp    bypass if target is server OP.
     * @return if one of the sender has permission.
     */
    boolean havePerm(List<CommandSender> senders, String permission, boolean allowOp);

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
     * @param allowOp    bypass if target is server OP.
     * @return if one of the player has permission.
     */
    boolean havePermPlayer(List<Player> players, String permission, boolean allowOp);

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
     * @param allowOp    bypass if target is server OP.
     * @return if one of the uuid of player has permission.
     */
    boolean havePermUUID(List<UUID> uuids, String permission, boolean allowOp);

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
     * @param allowOp        bypass if target is server OP.
     * @return if one of the offlinePlayer has permission.
     */
    boolean havePermOffline(List<OfflinePlayer> offlinePlayers, String permission, boolean allowOp);

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
     * @param allowOp    bypass if target is server OP.
     * @return if all of the senders have permission.
     */
    boolean allHavePerm(List<CommandSender> senders, String permission, boolean allowOp);

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
     * @param allowOp    bypass if target is server OP.
     * @return if all of the players have permission.
     */
    boolean allHavePermPlayer(List<Player> players, String permission, boolean allowOp);

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
     * @param allowOp    bypass if target is server OP.
     * @return if all of the uuids of players have permission.
     */
    boolean allHavePermUUID(List<UUID> uuids, String permission, boolean allowOp);

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
     * @param allowOp        bypass if target is server OP.
     * @return if all of the offlinePlayers have permission.
     */
    boolean allHavePermOffline(List<OfflinePlayer> offlinePlayers, String permission, boolean allowOp);

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
     * @param uuid the uuid of player.
     * @return all inherited groups of LuckPerms.
     */
    Set<String> getLuckPermsInheritedGroups(UUID uuid);

    /**
     * @param uuid the uuid of player.
     * @return all permissions of LuckPerms.
     */
    List<String> getLuckPermsAllPerms(UUID uuid);

    /**
     * To promote a permission number for a player.
     *
     * @param uuid       the uuid of player.
     * @param permission the permission node.
     * @param number     the value of promote/demote level.
     * @param def        the default value if the player doesn't have any permission.
     */
    void changePermLevel(UUID uuid, String permission, int number, int def);

    /**
     * @param uuid     the id of player in the game.
     * @param nickname the new name of Discord name.
     */
    void setDiscordNick(UUID uuid, String nickname);

    /**
     * Get the nick name of Discord.
     *
     * @param uuid the id of player in the game.
     */
    String getDiscordNick(UUID uuid);

    /**
     * Get the name of Discord.
     *
     * @param uuid the id of player in the game.
     */
    String getDiscordName(UUID uuid);

    /**
     * @return get the Material name of Residence selection tool.
     */
    Material getResSelectionTool();

    /**
     * @param player the target player.
     * @param slot   the slot to get item.
     * @return the item of the slot.
     */
    ItemStack getSlotItem(Player player, String slot);

    /**
     * @param player the target player.
     * @param slot   the slot to clean all items.
     */
    void cleanSlot(Player player, String slot);

    /**
     * @param player the target player.
     * @return if inventory has empty slot.
     */
    boolean isInventoryFull(Player player);

    /**
     * Check if a player's inventory can put a itemStack normally.
     *
     * @param player     the target player.
     * @param material   the target item's material.
     * @param itemNumber the number of items.
     * @return if a player's inventory can put a itemStack normally.
     */
    boolean isInventoryFull(Player player, Material material, int itemNumber);

    /**
     * @param player the target player.
     * @return the first empty slot of this player.
     */
    int getInventoryEmptySlot(Player player);

    /**
     * @param playerName the target player name.
     * @return the map of player homes' name and location.
     */
    Map<String, Location> getCMIHomes(String playerName);

    /**
     * @param uuid the target player uuid.
     * @return the map of player homes' name and location.
     */
    Map<String, Location> getCMIHomes(UUID uuid);
}