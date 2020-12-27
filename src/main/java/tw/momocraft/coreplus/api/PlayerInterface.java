package tw.momocraft.coreplus.api;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.UUID;

public interface PlayerInterface {

    Player getPlayerString(String playerName);

    double getTypeBalance(UUID uuid, String priceType);

    double takeTypeMoney(UUID uuid, String priceType, double amount);


    boolean hasPermission(CommandSender sender, String permission);

    int getMaxPermission(CommandSender sender, String permission);

    String getPlayerPrimaryGroup(UUID uuid);

    boolean setPlayerPrimaryGroup(UUID uuid, String group);

    boolean isPlayerInGroup(Player player, String group);

}
