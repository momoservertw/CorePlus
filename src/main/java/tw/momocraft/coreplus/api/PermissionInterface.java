package tw.momocraft.coreplus.api;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.UUID;

public interface PermissionInterface {

    boolean hasPermission(CommandSender sender, String permission);

    int getMaxPermission(CommandSender sender, String permission);

    String getPlayerPrimaryGroup(UUID uuid);

    boolean setPlayerPrimaryGroup(UUID uuid, String group);

    boolean isPlayerInGroup(Player player, String group);
}
