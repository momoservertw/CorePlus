package tw.momocraft.coreplus.api;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

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
     * Getting the amount of currency of player.
     *
     * @param uuid the uuid of player.
     * @param type the type of currency. Types: Money, PlayerPoints, GemsEconomyAPI
     * @return the amount of currency of player.
     */
    double getTypeBalance(UUID uuid, String type);

    /**
     * Taking the amount of currency from player.
     *
     * @param uuid the uuid of player.
     * @param type the type of currency. Types: Money, PlayerPoints, GemsEconomyAPI
     * @param amount the amount to take.
     * @return the name amount of currency of player.
     */
    double takeTypeMoney(UUID uuid, String type, double amount);

    /**
     * Checking if the sender has permission.
     *
     * @param sender the checking player or console.
     * @param permission the permission node.
     * @return if the sender has permission.
     */
    boolean hasPermission(CommandSender sender, String permission);

    /**
     * Getting the highest level of permission of sender.
     *
     * @param sender the checking player or console.
     * @param permission the start with permission node.
     * @return the hightest level of permission of sender.
     */
    int getMaxPermission(CommandSender sender, String permission);

    /**
     * Getting the primary group of player.
     * Need: LuckPerms
     *
     * @param uuid the uuid of player.
     * @return the primary group of player.
     */
    String getPlayerPrimaryGroup(UUID uuid);

    /**
     * Setting the primary group for player.
     *
     * @param uuid the uuid of player.
     * @param group the new primary group of player
     * @return if the process succeed. It may be failed if can not find the player or group.
     */
    boolean setPlayerPrimaryGroup(UUID uuid, String group);

    /**
     * Checking if player is in a group.
     *
     * @param player the target player.
     * @param group the checking group name.
     * @return if player is in a group.
     */
    boolean isPlayerInGroup(Player player, String group);

}
