package tw.momocraft.coreplus;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.util.StringUtil;
import tw.momocraft.coreplus.handlers.ConfigHandler;
import tw.momocraft.coreplus.handlers.UtilsHandler;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class TabComplete implements TabCompleter {

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        final List<String> completions = new ArrayList<>();
        final List<String> commands = new ArrayList<>();

        Collection<?> playersOnlineNew;
        Player[] playersOnlineOld;

        switch (args.length) {
            case 1:
                if (UtilsHandler.getPlayer().hasPerm(ConfigHandler.getPluginName(), sender, "coreplus.use")) {
                    commands.add("help");
                }
                if (UtilsHandler.getPlayer().hasPerm(ConfigHandler.getPluginName(), sender, "coreplus.command.reload")) {
                    commands.add("reload");
                }
                if (UtilsHandler.getPlayer().hasPerm(ConfigHandler.getPluginName(), sender, "coreplus.command.version")) {
                    commands.add("version");
                }
                if (UtilsHandler.getPlayer().hasPerm(ConfigHandler.getPluginName(), sender, "coreplus.command.test")) {
                    commands.add("test");
                }
                if (UtilsHandler.getPlayer().hasPerm(ConfigHandler.getPluginName(), sender, "coreplus.command.configbuilder")) {
                    commands.add("configbuilder");
                }
                if (UtilsHandler.getPlayer().hasPerm(ConfigHandler.getPluginName(), sender, "coreplus.command.cmd")) {
                    commands.add("cmd");
                    commands.add("cmdplayer");
                    commands.add("cmdonline");
                }
                break;
            case 2:
                if (args[0].equalsIgnoreCase("test") &&
                        UtilsHandler.getPlayer().hasPerm(ConfigHandler.getPluginName(), sender, "coreplus.command.test")) {
                    commands.add("location");
                    commands.add("blocks");
                } else if (args[0].equalsIgnoreCase("configbuilder") && UtilsHandler.getPlayer().hasPerm(ConfigHandler.getPluginName(), sender, "coreplus.command.configbuilder")) {
                    commands.addAll(ConfigHandler.getConfigPath().getConfigBuilderProp().keySet());
                }
                break;
            case 3:
                if (args[0].equalsIgnoreCase("test") && args[1].equalsIgnoreCase("location") &&
                        UtilsHandler.getPlayer().hasPerm(ConfigHandler.getPluginName(), sender, "coreplus.command.test")) {
                    commands.addAll(ConfigHandler.getConfigPath().getLocProp().keySet());
                } else if (args[0].equalsIgnoreCase("test") && args[1].equalsIgnoreCase("blocks") &&
                        UtilsHandler.getPlayer().hasPerm(ConfigHandler.getPluginName(), sender, "coreplus.command.test")) {
                    commands.addAll(ConfigHandler.getConfigPath().getBlocksProp().keySet());
                }
                break;
        }
        StringUtil.copyPartialMatches(args[(args.length - 1)], commands, completions);
        Collections.sort(completions);
        return completions;
    }
}