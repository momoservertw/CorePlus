package tw.momocraft.coreplus.utils;

import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import tw.momocraft.coreplus.api.PermissionInterface;

public class Permissions implements PermissionInterface {

	@Override
	public boolean hasPermission(CommandSender sender, String permission) {
		return sender.hasPermission(permission) || sender.hasPermission("CorePlus.*") || sender.isOp() || (sender instanceof ConsoleCommandSender);
	}
}