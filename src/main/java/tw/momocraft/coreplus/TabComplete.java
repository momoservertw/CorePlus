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
                if (UtilsHandler.getPlayer().hasPerm(ConfigHandler.getPluginName(), sender, "coreplus.command.runcmd")) {
                    commands.add("runcmd");
                }
                if (UtilsHandler.getPlayer().hasPerm(ConfigHandler.getPluginName(), sender, "coreplus.command.runcmdcustom")) {
                    commands.add("runcmdcustom");
                }
                break;
            case 2:
                if (args[0].equalsIgnoreCase("test") &&
                        UtilsHandler.getPlayer().hasPerm(ConfigHandler.getPluginName(), sender, "coreplus.command.test")) {
                    commands.add("location");
                    commands.add("blocks");
                } else if (args[0].equalsIgnoreCase("runcmd") &&
                        UtilsHandler.getPlayer().hasPerm(ConfigHandler.getPluginName(), sender, "coreplus.command.runcmd")) {
                    try {
                        if (Bukkit.class.getMethod("getOnlinePlayers").getReturnType() == Collection.class) {
                            if (Bukkit.class.getMethod("getOnlinePlayers").getReturnType() == Collection.class) {
                                playersOnlineNew = ((Collection<?>) Bukkit.class.getMethod("getOnlinePlayers", new Class<?>[0]).invoke(null, new Object[0]));
                                for (Object objPlayer : playersOnlineNew) {
                                    commands.add(((Player) objPlayer).getName());
                                }
                            }
                        } else {
                            playersOnlineOld = ((Player[]) Bukkit.class.getMethod("getOnlinePlayers", new Class<?>[0]).invoke(null, new Object[0]));
                            for (Player player : playersOnlineOld) {
                                commands.add(player.getName());
                            }
                        }
                    } catch (Exception e) {
                        UtilsHandler.getLang().sendDebugTrace(ConfigHandler.isDebugging(), ConfigHandler.getPlugin(), e);
                    }
                } else if (args[0].equalsIgnoreCase("runcmdcustom") &&
                        UtilsHandler.getPlayer().hasPerm(ConfigHandler.getPluginName(), sender, "coreplus.command.runcmdcustom")) {
                    commands.addAll(ConfigHandler.getConfigPath().getCmdProp().keySet());
                    try {
                        if (Bukkit.class.getMethod("getOnlinePlayers").getReturnType() == Collection.class) {
                            if (Bukkit.class.getMethod("getOnlinePlayers").getReturnType() == Collection.class) {
                                playersOnlineNew = ((Collection<?>) Bukkit.class.getMethod("getOnlinePlayers", new Class<?>[0]).invoke(null, new Object[0]));
                                for (Object objPlayer : playersOnlineNew) {
                                    commands.add(((Player) objPlayer).getName());
                                }
                            }
                        } else {
                            playersOnlineOld = ((Player[]) Bukkit.class.getMethod("getOnlinePlayers", new Class<?>[0]).invoke(null, new Object[0]));
                            for (Player player : playersOnlineOld) {
                                commands.add(player.getName());
                            }
                        }
                    } catch (Exception e) {
                        UtilsHandler.getLang().sendDebugTrace(ConfigHandler.isDebugging(), ConfigHandler.getPlugin(), e);
                    }
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
            case 4:
                if (args[0].equalsIgnoreCase("runcmdcustom") &&
                        UtilsHandler.getPlayer().hasPerm(ConfigHandler.getPluginName(), sender, "coreplus.command.runcmdcustom")) {
                    commands.addAll(ConfigHandler.getConfigPath().getCmdProp().keySet());
                }
        }
        StringUtil.copyPartialMatches(args[(args.length - 1)], commands, completions);
        Collections.sort(completions);
        return completions;
    }
}