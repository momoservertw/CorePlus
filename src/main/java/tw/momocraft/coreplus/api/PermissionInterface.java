package tw.momocraft.coreplus.api;

import org.bukkit.command.CommandSender;

public interface PermissionInterface {

    boolean hasPermission(CommandSender sender, String permission);
}
