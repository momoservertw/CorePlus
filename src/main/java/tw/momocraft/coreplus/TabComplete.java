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
        int length = args.length;
        if (length == 0) {
            if (UtilsHandler.getPlayer().hasPerm(sender, "coreplus.use")) {
                commands.add("help");
            }
            if (UtilsHandler.getPlayer().hasPerm(sender, "coreplus.command.reload")) {
                commands.add("reload");
            }
            if (UtilsHandler.getPlayer().hasPerm(sender, "coreplus.command.version")) {
                commands.add("version");
            }
            if (UtilsHandler.getPlayer().hasPerm(sender, "coreplus.command.test")) {
                commands.add("test");
            }
            if (UtilsHandler.getPlayer().hasPerm(sender, "coreplus.command.configbuilder")) {
                commands.add("configbuilder");
            }
            if (UtilsHandler.getPlayer().hasPerm(sender, "coreplus.command.cmdcustom")) {
                commands.add("cmdcustom");
            }
            if (UtilsHandler.getPlayer().hasPerm(sender, "coreplus.command.cmd")) {
                commands.add("cmd");
            }
            if (UtilsHandler.getPlayer().hasPerm(sender, "coreplus.command.cmdplayer")) {
                commands.add("cmdplayer");
            }
            if (UtilsHandler.getPlayer().hasPerm(sender, "coreplus.command.cmdonline")) {
                commands.add("cmdonline");
            }
        }
        switch (args[0]) {
            case "test":
                if (UtilsHandler.getPlayer().hasPerm(sender, "coreplus.command.test")) {
                    if (length == 1) {
                        commands.add("location");
                        commands.add("blocks");
                    } else if (length == 2) {
                        if (args[1].equalsIgnoreCase("location")) {
                            commands.addAll(ConfigHandler.getConfigPath().getLocProp().keySet());
                        } else if (args[1].equalsIgnoreCase("blocks")) {
                            commands.addAll(ConfigHandler.getConfigPath().getBlocksProp().keySet());
                        }
                    }
                }
                break;
            case "configbuilder":
                if (UtilsHandler.getPlayer().hasPerm(sender, "coreplus.command.configbuilder")) {
                    if (length == 1) {
                        commands.addAll(ConfigHandler.getConfigPath().getConfigBuilderProp().keySet());
                    } else if (length == 2) {
                        if (args[1].equalsIgnoreCase("location")) {
                            commands.addAll(ConfigHandler.getConfigPath().getLocProp().keySet());
                        } else if (args[1].equalsIgnoreCase("blocks")) {
                            commands.addAll(ConfigHandler.getConfigPath().getBlocksProp().keySet());
                        }
                    }
                }
                break;
            case "cmdcustom":
                if (UtilsHandler.getPlayer().hasPerm(sender, "coreplus.command.cmdcustom")) {
                    if (length == 1) {
                        commands.addAll(ConfigHandler.getConfigPath().getCmdProp().keySet());
                    }
                }
                break;
            case "cmd":
                if (UtilsHandler.getPlayer().hasPerm(sender, "coreplus.command.cmd")) {
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
                        UtilsHandler.getLang().sendDebugTrace(ConfigHandler.isDebugging(), ConfigHandler.getPluginPrefix(), e);
                    }
                }
                break;
            case "cmdplayer":
                if (UtilsHandler.getPlayer().hasPerm(sender, "coreplus.command.cmdplayer")) {
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
                        UtilsHandler.getLang().sendDebugTrace(ConfigHandler.isDebugging(), ConfigHandler.getPluginPrefix(), e);
                    }
                }
                break;
            case "cmdonline":
                if (UtilsHandler.getPlayer().hasPerm(sender, "coreplus.command.cmdonline")) {
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
                        UtilsHandler.getLang().sendDebugTrace(ConfigHandler.isDebugging(), ConfigHandler.getPluginPrefix(), e);
                    }
                }
                break;
        }
        StringUtil.copyPartialMatches(args[(args.length - 1)], commands, completions);
        Collections.sort(completions);
        return completions;
    }
}