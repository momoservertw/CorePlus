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
import tw.momocraft.coreplus.utils.customcommands.CustomCommands;

import java.util.ArrayList;
import java.util.List;


public class Commands implements CommandExecutor {

    public boolean onCommand(final CommandSender sender, Command cmd, String label, String[] args) {
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
                        UtilsHandler.getLang().sendMsg(ConfigHandler.getPrefix(), sender, "");
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
                }
                break;
            case 3:
                if (args[0].equalsIgnoreCase("test")) {
                    if (UtilsHandler.getPlayer().hasPerm(ConfigHandler.getPluginName(), sender, "coreplus.command.test")) {
                        if (sender instanceof ConsoleCommandSender) {
                            CorePlusAPI.getLangManager().sendLangMsg(ConfigHandler.getPrefix(), "Message.onlyPlayer", sender);
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
                } else if (args[0].equalsIgnoreCase("runcmd")) {
                    if (UtilsHandler.getPlayer().hasPerm(ConfigHandler.getPluginName(), sender, "coreplus.command.runcmd")) {
                        if (sender instanceof ConsoleCommandSender) {
                            UtilsHandler.getCustomCommands().executeCmd("",
                                    String.join(",", args).substring(args[0].length() + 1), true);
                            return true;
                        }
                        Player player = (Player) sender;
                        UtilsHandler.getCustomCommands().executeCmd("", player,
                                String.join(",", args).substring(args[0].length() + 1), true);
                    } else {
                        UtilsHandler.getLang().sendLangMsg(ConfigHandler.getPrefix(), "Message.noPermission", sender);
                    }
                    return true;
                } else if (args[0].equalsIgnoreCase("runcmdcustom")) {
                    if (UtilsHandler.getPlayer().hasPerm(ConfigHandler.getPluginName(), sender, "coreplus.command.runcmd")) {
                        if (sender instanceof ConsoleCommandSender) {
                            List<String> cmds = ConfigHandler.getConfigPath().getCmdProp().get(args[1]);
                            if (cmds == null || cmds.isEmpty()) {
                                String[] langHandler = UtilsHandler.getLang().newString();
                                langHandler[5] = args[1];
                                UtilsHandler.getLang().sendLangMsg(ConfigHandler.getPrefix(), "Message.groupNotFound", sender);
                                return true;
                            }
                            UtilsHandler.getCustomCommands().executeCmdList(ConfigHandler.getPrefix(), cmds, true);
                            return true;
                        }
                        Player player = (Player) sender;
                        List<String> cmds = ConfigHandler.getConfigPath().getCmdProp().get(args[1]);
                        if (cmds == null || cmds.isEmpty()) {
                            String[] langHandler = UtilsHandler.getLang().newString();
                            langHandler[5] = args[1];
                            UtilsHandler.getLang().sendLangMsg(ConfigHandler.getPrefix(), "Message.groupNotFound", sender);
                            return true;
                        }
                        UtilsHandler.getCustomCommands().executeCmdList(ConfigHandler.getPrefix(), player, cmds, true);
                    } else {
                        UtilsHandler.getLang().sendLangMsg(ConfigHandler.getPrefix(), "Message.noPermission", sender);
                    }
                    return true;
                }
                break;
            case 4:
                if (args[0].equalsIgnoreCase("runcmd")) {
                    if (UtilsHandler.getPlayer().hasPerm(ConfigHandler.getPluginName(), sender, "coreplus.command.runcmd")) {
                        Player player = CorePlusAPI.getPlayerManager().getPlayerString(args[1]);
                        String runcmd = String.join(",", args).substring(args[0].length() + 1);

                        String[] langHandler = CorePlusAPI.getLangManager().newString();
                        langHandler[1] = args[1]; // %targetplayer%
                        langHandler[4] = runcmd; // %command%
                        if (player == null) {
                            CorePlusAPI.getLangManager().sendLangMsg(ConfigHandler.getPrefix(), "Message.targetNotFound", sender, langHandler);
                            return true;
                        }
                        UtilsHandler.getCustomCommands().executeCmd("", player, runcmd, true);
                        UtilsHandler.getLang().sendLangMsg(ConfigHandler.getPrefix(), "Message.runcmdOther", sender, langHandler);
                    } else {
                        UtilsHandler.getLang().sendLangMsg(ConfigHandler.getPrefix(), "Message.noPermission", sender);
                    }
                    return true;
                } else if (args[0].equalsIgnoreCase("runcmdcustom")) {
                    if (UtilsHandler.getPlayer().hasPerm(ConfigHandler.getPluginName(), sender, "coreplus.command.runcmd")) {
                        Player player = CorePlusAPI.getPlayerManager().getPlayerString(args[1]);
                        String[] langHandler = CorePlusAPI.getLangManager().newString();
                        langHandler[1] = args[1]; // %targetplayer%
                        langHandler[5] = args[2]; // %group%
                        if (player == null) {
                            CorePlusAPI.getLangManager().sendLangMsg(ConfigHandler.getPrefix(), "Message.targetNotFound", sender, langHandler);
                            return true;
                        }
                        List<String> cmds = ConfigHandler.getConfigPath().getCmdProp().get(args[2]);
                        if (cmds == null || cmds.isEmpty()) {
                            UtilsHandler.getLang().sendLangMsg(ConfigHandler.getPrefix(), "Message.groupNotFound", sender);
                            return true;
                        }
                        UtilsHandler.getCustomCommands().executeCmdList(ConfigHandler.getPrefix(), player, cmds, true);
                        UtilsHandler.getLang().sendLangMsg(ConfigHandler.getPrefix(), "Message.runcmdCustomOther", sender, langHandler);
                    } else {
                        UtilsHandler.getLang().sendLangMsg(ConfigHandler.getPrefix(), "Message.noPermission", sender);
                    }
                    return true;
                }
                break;
        }
        UtilsHandler.getLang().sendLangMsg(ConfigHandler.getPrefix(), "Message.unknownCommand", sender);
        return true;
    }
}