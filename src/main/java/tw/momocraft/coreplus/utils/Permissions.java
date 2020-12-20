package tw.momocraft.coreplus.utils;

import net.luckperms.api.model.user.User;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.bukkit.permissions.PermissionAttachmentInfo;
import tw.momocraft.coreplus.api.PermissionInterface;
import tw.momocraft.coreplus.handlers.ConfigHandler;
import tw.momocraft.coreplus.utils.permission.LuckPermsAPI;

import java.util.*;

public class Permissions implements PermissionInterface {

    @Override
    public boolean hasPermission(CommandSender sender, String permission) {
        return sender.hasPermission(permission) || sender.hasPermission("CorePlus.*") || sender.isOp() || (sender instanceof ConsoleCommandSender);
    }

    @Override
    public int getMaxPermission(CommandSender sender, String permission) {
        List<Integer> numbers = new ArrayList<>();
        String perm;
        for (PermissionAttachmentInfo permInfo : sender.getEffectivePermissions()) {
            perm = permInfo.getPermission();
            if (perm.startsWith(permission)) {
                numbers.add(Integer.parseInt(perm.replace(permission, "")));
            }
        }
        if (numbers.isEmpty())
            return 0;
        return Collections.max(numbers);
    }

    @Override
    public String getPlayerPrimaryGroup(UUID uuid) {
        return ConfigHandler.getDepends().getLuckPermsApi().getPlayerPrimaryGroup(uuid);
    }

    @Override
    public boolean setPlayerPrimaryGroup(UUID uuid, String group) {
        return ConfigHandler.getDepends().getLuckPermsApi().setPlayerPrimaryGroup(uuid, group);
    }

    @Override
    public boolean isPlayerInGroup(Player player, String group) {
        return ConfigHandler.getDepends().getLuckPermsApi().isPlayerInGroup(player, group);
    }
}