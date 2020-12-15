package tw.momocraft.coreplus.api;

import org.bukkit.command.CommandSender;

public interface UpdateInterface {

    void check(String prefix, CommandSender sender, String plugin, String ver);
}
