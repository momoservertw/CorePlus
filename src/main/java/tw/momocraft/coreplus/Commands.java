package tw.momocraft.coreplus;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import tw.momocraft.coreplus.api.CorePlusAPI;
import tw.momocraft.coreplus.handlers.ConfigHandler;
import tw.momocraft.coreplus.handlers.UtilsHandler;
import tw.momocraft.coreplus.utils.ConfigBuilder;

import java.util.ArrayList;
import java.util.List;


public class Commands implements CommandExecutor {


    public boolean onCommand(final CommandSender sender, Command cmd, String label, String[] args) {
        Discord
        switch (args.length) {
            case 0:
                if (UtilsHandler.getPlayer().hasPerm(ConfigHandler.getPluginName(), sender, "coreplus.use")) {
                    UtilsHandler.getLang().sendMsg(ConfigHandler.getPrefix(), sender, "");
                    UtilsHandler.getLang().sendLangMsg(ConfigHandler.getPrefix(), "Message.Commands.title", sender);
                    UtilsHandler.getLang().sendMsg(ConfigHandler.getPrefix(), sender, "&f " + CorePlus.getInstance().getDescription().getName()
                            + " &ev" + CorePlus.getInstance().getDescription().getVersion() + "  &8by Momocraft");
                    UtilsHandler.getLang().sendLangMsg(ConfigHandler.getPrefix(), "Message.Commands.help", sender);
                    UtilsHandler.getLang().sendMsg(ConfigHandler.getPrefix(), sender, "");
                } else {
                    UtilsHandler.getLang().sendLangMsg(ConfigHandler.getPrefix(), "Message.noPermission", sender);
                }
                return true;
            case 1:
                if (args[0].equalsIgnoreCase("help")) {
                    if (UtilsHandler.getPlayer().hasPerm(ConfigHandler.getPluginName(), sender, "coreplus.use")) {
                        UtilsHandler.getLang().sendMsg(ConfigHandler.getPrefix(), sender, "");
                        UtilsHandler.getLang().sendLangMsg(ConfigHandler.getPrefix(), "Message.Commands.title", sender);
                        UtilsHandler.getLang().sendMsg(ConfigHandler.getPrefix(), sender, "&f " + CorePlus.getInstance().getDescription().getName()
                                + " &ev" + CorePlus.getInstance().getDescription().getVersion() + "  &8by Momocraft");
                        UtilsHandler.getLang().sendLangMsg(ConfigHandler.getPrefix(), "Message.Commands.help", sender);
                        if (UtilsHandler.getPlayer().hasPerm(ConfigHandler.getPluginName(), sender, "CorePlus.command.reload")) {
                            UtilsHandler.getLang().sendLangMsg(ConfigHandler.getPrefix(), "Message.Commands.reload", sender);
                        }
                        if (UtilsHandler.getPlayer().hasPerm(ConfigHandler.getPluginName(), sender, "CorePlus.command.version")) {
                            UtilsHandler.getLang().sendLangMsg(ConfigHandler.getPrefix(), "Message.Commands.version", sender);
                        }
                        if (UtilsHandler.getPlayer().hasPerm(ConfigHandler.getPluginName(), sender, "CorePlus.command.test")) {
                            UtilsHandler.getLang().sendLangMsg(ConfigHandler.getPrefix(), "Message.Commands.test", sender);
                        }
                        if (UtilsHandler.getPlayer().hasPerm(ConfigHandler.getPluginName(), sender, "CorePlus.command.configbuilder")) {
                            UtilsHandler.getLang().sendLangMsg(ConfigHandler.getPrefix(), "Message.Commands.configbuilder", sender);
                        }
                        if (UtilsHandler.getPlayer().hasPerm(ConfigHandler.getPluginName(), sender, "CorePlus.command.cmd")) {
                            UtilsHandler.getLang().sendLangMsg(ConfigHandler.getPrefix(), "Message.Commands.cmd", sender);
                            UtilsHandler.getLang().sendLangMsg(ConfigHandler.getPrefix(), "Message.Commands.cmdplayer", sender);
                            UtilsHandler.getLang().sendLangMsg(ConfigHandler.getPrefix(), "Message.Commands.cmdonline", sender);
                        }
                    } else {
                        UtilsHandler.getLang().sendLangMsg(ConfigHandler.getPrefix(), "Message.noPermission", sender);
                    }
                    return true;
                } else if (args[0].equalsIgnoreCase("reload")) {
                    if (UtilsHandler.getPlayer().hasPerm(ConfigHandler.getPluginName(), sender, "coreplus.command.reload")) {
                        ConfigHandler.generateData(true);
                        UtilsHandler.getLang().sendLangMsg(ConfigHandler.getPrefix(), "Message.configReload", sender);
                    } else {
                        UtilsHandler.getLang().sendLangMsg(ConfigHandler.getPrefix(), "Message.noPermission", sender);
                    }
                    return true;
                } else if (args[0].equalsIgnoreCase("version")) {
                    if (UtilsHandler.getPlayer().hasPerm(ConfigHandler.getPluginName(), sender, "coreplus.command.version")) {
                        UtilsHandler.getLang().sendMsg(ConfigHandler.getPrefix(), sender, "&f " + CorePlus.getInstance().getDescription().getName()
                                + " &ev" + CorePlus.getInstance().getDescription().getVersion() + "  &8by Momocraft");
                        UtilsHandler.getUpdate().check(ConfigHandler.getPrefix(), Bukkit.getConsoleSender(),
                                CorePlus.getInstance().getDescription().getName(), CorePlus.getInstance().getDescription().getVersion(), false);
                    } else {
                        UtilsHandler.getLang().sendLangMsg(ConfigHandler.getPrefix(), "Message.noPermission", sender);
                    }
                    return true;
                } else if (args[0].equalsIgnoreCase("test")) {
                    if (UtilsHandler.getPlayer().hasPerm(ConfigHandler.getPluginName(), sender, "coreplus.command.test")) {
                        UtilsHandler.getLang().sendLangMsg(ConfigHandler.getPrefix(), "Message.Commands.test", sender);
                    } else {
                        UtilsHandler.getLang().sendLangMsg(ConfigHandler.getPrefix(), "Message.noPermission", sender);
                    }
                    return true;
                } else if (args[0].equalsIgnoreCase("configbuilder")) {
                    if (UtilsHandler.getPlayer().hasPerm(ConfigHandler.getPluginName(), sender, "coreplus.command.configbuilder")) {
                        UtilsHandler.getLang().sendLangMsg(ConfigHandler.getPrefix(), "Message.Commands.configbuilder", sender);
                    } else {
                        UtilsHandler.getLang().sendLangMsg(ConfigHandler.getPrefix(), "Message.noPermission", sender);
                    }
                    return true;
                } else if (args[0].equalsIgnoreCase("cmd")) {
                    if (UtilsHandler.getPlayer().hasPerm(ConfigHandler.getPluginName(), sender, "coreplus.command.cmd")) {
                        UtilsHandler.getLang().sendLangMsg(ConfigHandler.getPrefix(), "Message.Commands.cmd", sender);
                    } else {
                        UtilsHandler.getLang().sendLangMsg(ConfigHandler.getPrefix(), "Message.noPermission", sender);
                    }
                    return true;
                } else if (args[0].equalsIgnoreCase("cmdplayer")) {
                    if (UtilsHandler.getPlayer().hasPerm(ConfigHandler.getPluginName(), sender, "coreplus.command.cmd")) {
                        UtilsHandler.getLang().sendLangMsg(ConfigHandler.getPrefix(), "Message.Commands.cmdplayer", sender);
                    } else {
                        UtilsHandler.getLang().sendLangMsg(ConfigHandler.getPrefix(), "Message.noPermission", sender);
                    }
                    return true;
                } else if (args[0].equalsIgnoreCase("cmdonline")) {
                    if (UtilsHandler.getPlayer().hasPerm(ConfigHandler.getPluginName(), sender, "coreplus.command.cmd")) {
                        UtilsHandler.getLang().sendLangMsg(ConfigHandler.getPrefix(), "Message.Commands.cmdonline", sender);
                    } else {
                        UtilsHandler.getLang().sendLangMsg(ConfigHandler.getPrefix(), "Message.noPermission", sender);
                    }
                    return true;
                }

                break;
            case 2:
                if (args[0].equalsIgnoreCase("configbuilder")) {
                    if (UtilsHandler.getPlayer().hasPerm(ConfigHandler.getPluginName(), sender, "coreplus.command.configbuilder")) {
                        UtilsHandler.getLang().sendMsg(ConfigHandler.getPrefix(), sender, "Creating configuration for " + args[1] + "...");
                        ConfigBuilder.start(sender, args[1]);
                        return true;
                    }
                }
                break;
            case 3:
                // /crp test <blocks/location> <group>
                if (args[0].equalsIgnoreCase("test")) {
                    if (UtilsHandler.getPlayer().hasPerm(ConfigHandler.getPluginName(), sender, "coreplus.command.test")) {
                        if (sender instanceof ConsoleCommandSender) {
                            UtilsHandler.getLang().sendLangMsg(ConfigHandler.getPrefix(), "Message.onlyPlayer", sender);
                            return true;
                        }
                        Player player = (Player) sender;
                        List<String> group = new ArrayList<>();
                        group.add(args[2]);
                        if (args[1].equalsIgnoreCase("location")) {
                            if (UtilsHandler.getCondition().checkLocation(player.getLocation(), group, false)) {
                                UtilsHandler.getLang().sendMsg(ConfigHandler.getPrefix(), sender, "&aTest Succeed &7(Location: " + args[2] + ")");
                                return true;
                            }
                            UtilsHandler.getLang().sendMsg(ConfigHandler.getPrefix(), sender, "&cTest Failed &7(Location: " + args[2] + ")");
                        } else if (args[1].equalsIgnoreCase("blocks")) {
                            if (UtilsHandler.getCondition().checkBlocks(player.getLocation(), group, false)) {
                                UtilsHandler.getLang().sendMsg(ConfigHandler.getPrefix(), sender, "&aTest Succeed &7(Blocks: " + args[2] + ")");
                                return true;
                            }
                            UtilsHandler.getLang().sendMsg(ConfigHandler.getPrefix(), sender, "&cTest Failed &7(Blocks: " + args[2] + ")");
                        } else {
                            break;
                        }
                    } else {
                        UtilsHandler.getLang().sendLangMsg(ConfigHandler.getPrefix(), "Message.noPermission", sender);
                    }
                    return true;
                }
                break;
        }
        // /crp cmd <command>
        if (args.length > 1 && args[0].equalsIgnoreCase("cmd")) {
            if (UtilsHandler.getPlayer().hasPerm(ConfigHandler.getPluginName(), sender, "coreplus.command.cmd")) {
                String command = String.join(" ", args).substring(args[0].length() + 1);
                if (sender instanceof ConsoleCommandSender) {
                    UtilsHandler.getCustomCommands().executeCmd(ConfigHandler.getPlugin(), command, true);
                } else {
                    UtilsHandler.getCustomCommands().executeCmd(ConfigHandler.getPlugin(), (Player) sender, command, true);
                }
            } else {
                UtilsHandler.getLang().sendLangMsg(ConfigHandler.getPrefix(), "Message.noPermission", sender);
            }
            return true;
            // /crp cmdplayer <playerName> <command>
        } else if (args.length > 3 && args[0].equalsIgnoreCase("cmdplayer")) {
            if (UtilsHandler.getPlayer().hasPerm(ConfigHandler.getPluginName(), sender, "coreplus.command.cmd")) {
                Player player = CorePlusAPI.getPlayerManager().getPlayerString(args[1]);
                if (player == null) {
                    String[] placeHolders = CorePlusAPI.getLangManager().newString();
                    placeHolders[1] = args[1]; // %targetplayer%
                    CorePlusAPI.getLangManager().sendLangMsg(ConfigHandler.getPrefix(), "Message.targetNotFound", sender, placeHolders);
                    return true;
                }
                String command = String.join(" ", args).substring(args[0].length() + args[1].length() + 2);
                UtilsHandler.getCustomCommands().executeCmd(ConfigHandler.getPlugin(), player, command, true);
            } else {
                UtilsHandler.getLang().sendLangMsg(ConfigHandler.getPrefix(), "Message.noPermission", sender);
            }
            return true;
            // /crp cmdonline <player> <expiration> <command>
        } else if (args.length > 5 && args[0].equalsIgnoreCase("cmdonline")) {
            if (UtilsHandler.getPlayer().hasPerm(ConfigHandler.getPlugin(), sender, "coreplus.command.cmd")) {
                int waitingTime;
                try {
                    waitingTime = Integer.parseInt(args[2]);
                } catch (Exception ex) {
                    UtilsHandler.getLang().sendLangMsg(ConfigHandler.getPrefix(), "Message.Commands.cmdonline", sender);
                    return true;
                }
                String command = String.join(" ", args).substring(args[0].length() + args[1].length() + args[2].length() + 3);
                UtilsHandler.getLang().sendConsoleMsg(ConfigHandler.getPrefix(),
                        "Online command start - Player: " + args[1] + ", Expiration: " + args[2] + ", Command: " + command);
                Player player = CorePlusAPI.getPlayerManager().getPlayerString(args[1]);
                if (player != null) {
                    CorePlusAPI.getCommandManager().executeCmd(ConfigHandler.getPluginName(), player,
                            command, true);
                    return true;
                }
                UtilsHandler.getCustomCommands().addWaiting(args[1], waitingTime, command);
            } else {
                UtilsHandler.getLang().sendLangMsg(ConfigHandler.getPrefix(), "Message.noPermission", sender);
            }
            return true;
        }
        UtilsHandler.getLang().sendLangMsg(ConfigHandler.getPrefix(), "Message.unknownCommand", sender);
        return true;
    }
}