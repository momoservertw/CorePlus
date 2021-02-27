package tw.momocraft.coreplus;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import tw.momocraft.coreplus.handlers.ConfigHandler;
import tw.momocraft.coreplus.handlers.UtilsHandler;
import tw.momocraft.coreplus.utils.files.ConfigBuilder;

import java.util.ArrayList;
import java.util.List;

public class Commands implements CommandExecutor {

    public boolean onCommand(final CommandSender sender, Command cmd, String label, String[] args) {
        int length = args.length;
        if (length == 0) {
            if (UtilsHandler.getPlayer().hasPerm(sender, "coreplus.use")) {
                UtilsHandler.getLang().sendMsg(ConfigHandler.getPrefix(), sender, "");
                UtilsHandler.getLang().sendLangMsg(ConfigHandler.getPluginName(), ConfigHandler.getPrefix(),
                        "Message.Commands.title", sender);
                UtilsHandler.getLang().sendMsg(ConfigHandler.getPrefix(), sender,
                        "&f " + CorePlus.getInstance().getDescription().getName()
                                + " &ev" + CorePlus.getInstance().getDescription().getVersion() + "  &8by Momocraft");
                UtilsHandler.getLang().sendLangMsg(ConfigHandler.getPluginName(), ConfigHandler.getPrefix(),
                        "Message.Commands.help", sender);
                UtilsHandler.getLang().sendMsg(ConfigHandler.getPrefix(), sender, "");
            } else {
                UtilsHandler.getLang().sendLangMsg(ConfigHandler.getPluginName(), ConfigHandler.getPrefix(),
                        "Message.noPermission", sender);
            }
            return true;
        }
        switch (args[0].toLowerCase()) {
            case "help":
                if (UtilsHandler.getPlayer().hasPerm(sender, "coreplus.use")) {
                    UtilsHandler.getLang().sendMsg(ConfigHandler.getPrefix(), sender, "");
                    UtilsHandler.getLang().sendLangMsg(ConfigHandler.getPluginName(), ConfigHandler.getPrefix(),
                            "Message.Commands.title", sender);
                    UtilsHandler.getLang().sendMsg(ConfigHandler.getPrefix(), sender,
                            "&f " + CorePlus.getInstance().getDescription().getName()
                                    + " &ev" + CorePlus.getInstance().getDescription().getVersion() + "  &8by Momocraft");
                    UtilsHandler.getLang().sendLangMsg(ConfigHandler.getPluginName(), ConfigHandler.getPrefix(),
                            "Message.Commands.help", sender);
                    if (UtilsHandler.getPlayer().hasPerm(sender, "coreplus.command.reload")) {
                        UtilsHandler.getLang().sendLangMsg(ConfigHandler.getPluginName(), ConfigHandler.getPrefix(),
                                "Message.Commands.reload", sender);
                    }
                    if (UtilsHandler.getPlayer().hasPerm(sender, "coreplus.command.version")) {
                        UtilsHandler.getLang().sendLangMsg(ConfigHandler.getPluginName(), ConfigHandler.getPrefix(),
                                "Message.Commands.version", sender);
                    }
                    if (UtilsHandler.getPlayer().hasPerm(sender, "coreplus.command.residence.test")) {
                        UtilsHandler.getLang().sendLangMsg(ConfigHandler.getPluginName(), ConfigHandler.getPrefix(),
                                "Message.Commands.test", sender);
                    }
                    if (UtilsHandler.getPlayer().hasPerm(sender, "coreplus.command.residence.configbuilder")) {
                        UtilsHandler.getLang().sendLangMsg(ConfigHandler.getPluginName(), ConfigHandler.getPrefix(),
                                "Message.Commands.configbuilderGroup", sender);
                        UtilsHandler.getLang().sendLangMsg(ConfigHandler.getPluginName(), ConfigHandler.getPrefix(),
                                "Message.Commands.configbuilderCustom", sender);
                    }
                    if (UtilsHandler.getPlayer().hasPerm(sender, "coreplus.command.residence.cmd")) {
                        UtilsHandler.getLang().sendLangMsg(ConfigHandler.getPluginName(), ConfigHandler.getPrefix(),
                                "Message.Commands.cmd", sender);
                    }
                    if (UtilsHandler.getPlayer().hasPerm(sender, "coreplus.command.residence.cmdcustom")) {
                        UtilsHandler.getLang().sendLangMsg(ConfigHandler.getPluginName(), ConfigHandler.getPrefix(),
                                "Message.Commands.cmdcustom", sender);
                    }
                    if (UtilsHandler.getPlayer().hasPerm(sender, "coreplus.command.residence.cmdplayer")) {
                        UtilsHandler.getLang().sendLangMsg(ConfigHandler.getPluginName(), ConfigHandler.getPrefix(),
                                "Message.Commands.cmdplayer", sender);
                    }
                    if (UtilsHandler.getPlayer().hasPerm(sender, "coreplus.command.residence.cmdonline")) {
                        UtilsHandler.getLang().sendLangMsg(ConfigHandler.getPluginName(), ConfigHandler.getPrefix(),
                                "Message.Commands.cmdonline", sender);
                    }
                    UtilsHandler.getLang().sendMsg(ConfigHandler.getPrefix(), sender, "");
                } else {
                    UtilsHandler.getLang().sendLangMsg(ConfigHandler.getPluginName(), ConfigHandler.getPrefix(),
                            "Message.noPermission", sender);
                }
                return true;
            case "reload":
                if (UtilsHandler.getPlayer().hasPerm(sender, "coreplus.command.reload")) {
                    ConfigHandler.generateData(true);
                    UtilsHandler.getLang().sendLangMsg(ConfigHandler.getPluginName(), ConfigHandler.getPrefix(),
                            "Message.configReload", sender);
                } else {
                    UtilsHandler.getLang().sendLangMsg(ConfigHandler.getPluginName(), ConfigHandler.getPrefix(),
                            "Message.noPermission", sender);
                }
                return true;
            case "version":
                if (UtilsHandler.getPlayer().hasPerm(sender, "coreplus.command.version")) {
                    UtilsHandler.getLang().sendMsg(ConfigHandler.getPrefix(), sender,
                            "&f " + CorePlus.getInstance().getDescription().getName()
                                    + " &ev" + CorePlus.getInstance().getDescription().getVersion() + "  &8by Momocraft");
                    UtilsHandler.getUpdate().check(ConfigHandler.getPluginName(), ConfigHandler.getPrefix(), sender,
                            CorePlus.getInstance().getName(), CorePlus.getInstance().getDescription().getVersion(), true);
                } else {
                    UtilsHandler.getLang().sendLangMsg(ConfigHandler.getPluginName(), ConfigHandler.getPrefix(),
                            "Message.noPermission", sender);
                }
                return true;
            case "test":
                if (UtilsHandler.getPlayer().hasPerm(sender, "coreplus.command.test")) {
                    // /crp test <blocks/location> <group>
                    if (length == 3) {
                        if (sender instanceof ConsoleCommandSender) {
                            UtilsHandler.getLang().sendLangMsg(ConfigHandler.getPluginName(), ConfigHandler.getPrefix(),
                                    "Message.onlyPlayer", sender);
                            return true;
                        }
                        Player player = (Player) sender;
                        List<String> group = new ArrayList<>();
                        group.add(args[2]);
                        if (args[1].equalsIgnoreCase("location")) {
                            if (UtilsHandler.getCondition().checkLocation(ConfigHandler.getPluginName(), player.getLocation(), group, false)) {
                                UtilsHandler.getLang().sendMsg(ConfigHandler.getPrefix(), sender, "&aTest Succeed &7(Location: " + args[2] + ")");
                                return true;
                            }
                            UtilsHandler.getLang().sendMsg(ConfigHandler.getPrefix(), sender, "&cTest Failed &7(Location: " + args[2] + ")");
                            return true;
                        } else if (args[1].equalsIgnoreCase("blocks")) {
                            if (UtilsHandler.getCondition().checkBlocks(player.getLocation(), group, false)) {
                                UtilsHandler.getLang().sendMsg(ConfigHandler.getPrefix(), sender, "&aTest Succeed &7(Blocks: " + args[2] + ")");
                                return true;
                            }
                            UtilsHandler.getLang().sendMsg(ConfigHandler.getPrefix(), sender, "&cTest Failed &7(Blocks: " + args[2] + ")");
                            return true;
                        }
                    }
                    UtilsHandler.getLang().sendLangMsg(ConfigHandler.getPluginName(), ConfigHandler.getPrefix(),
                            "Message.Commands.test", sender);
                    return true;
                } else {
                    UtilsHandler.getLang().sendLangMsg(ConfigHandler.getPluginName(), ConfigHandler.getPrefix(),
                            "Message.noPermission", sender);
                }
                return true;
            case "configbuilder":
                if (UtilsHandler.getPlayer().hasPerm(sender, "coreplus.command.configbuilder")) {
                    // crp configbuiler custom group
                    if (length == 3 && args[1].equalsIgnoreCase("custom")) {
                        UtilsHandler.getLang().sendMsg(ConfigHandler.getPrefix(), sender, "&6Creating configuration for group \"" + args[2] + "\"...");
                        ConfigBuilder.startCustom(sender, args[2]);
                        return true;
                    }
                    // crp configbuilder group
                    else if (length == 2 && args[1].equalsIgnoreCase("group")) {
                        UtilsHandler.getLang().sendMsg(ConfigHandler.getPrefix(), sender, "&6Creating configuration for group.yml...");
                        ConfigBuilder.startGroups(sender);
                        return true;
                    }
                    UtilsHandler.getLang().sendLangMsg(ConfigHandler.getPluginName(), ConfigHandler.getPrefix(),
                            "Message.Commands.configbuilderCustom", sender);
                    UtilsHandler.getLang().sendLangMsg(ConfigHandler.getPluginName(), ConfigHandler.getPrefix(),
                            "Message.Commands.configbuilderGroup", sender);
                } else {
                    UtilsHandler.getLang().sendLangMsg(ConfigHandler.getPluginName(), ConfigHandler.getPrefix(),
                            "Message.noPermission", sender);
                }
                return true;
            case "cmdcustom":
                if (UtilsHandler.getPlayer().hasPerm(sender, "coreplus.command.cmdcustom")) {
                    // /crp cmdcustom <command>
                    if (length == 2) {
                        UtilsHandler.getCustomCommands().dispatchCustomCmd(ConfigHandler.getPluginName(), null, args[1], true);
                        return true;
                        // /crp cmdcustom [player] <command>
                    } else if (length == 3) {
                        Player player = UtilsHandler.getPlayer().getPlayerString(args[1]);
                        if (player == null) {
                            String[] placeHolders = UtilsHandler.getLang().newString();
                            placeHolders[1] = args[1]; // %targetplayer%
                            UtilsHandler.getLang().sendLangMsg(ConfigHandler.getPluginName(), ConfigHandler.getPrefix(),
                                    "Message.targetNotFound", sender, placeHolders);
                            return true;
                        }
                        UtilsHandler.getCustomCommands().dispatchCustomCmd(ConfigHandler.getPluginName(), player, args[2], true);
                        return true;
                    }
                    UtilsHandler.getLang().sendLangMsg(ConfigHandler.getPluginName(), ConfigHandler.getPrefix(),
                            "Message.Commands.cmdcustom", sender);
                } else {
                    UtilsHandler.getLang().sendLangMsg(ConfigHandler.getPluginName(), ConfigHandler.getPrefix(),
                            "Message.noPermission", sender);
                }
                return true;
            case "cmd":
                if (UtilsHandler.getPlayer().hasPerm(sender, "coreplus.command.cmd")) {
                    // /crp cmd <command>
                    if (length > 1) {
                        String command = String.join(" ", args).substring(args[0].length() + 1);
                        if (sender instanceof ConsoleCommandSender) {
                            UtilsHandler.getCustomCommands().executeCmd(ConfigHandler.getPluginName(), command, true);
                        } else {
                            UtilsHandler.getCustomCommands().executeCmd(ConfigHandler.getPluginName(), (Player) sender, command, true);
                        }
                        return true;
                    }
                    UtilsHandler.getLang().sendLangMsg(ConfigHandler.getPluginName(), ConfigHandler.getPrefix(),
                            "Message.Commands.cmd", sender);
                } else {
                    UtilsHandler.getLang().sendLangMsg(ConfigHandler.getPluginName(), ConfigHandler.getPrefix(),
                            "Message.noPermission", sender);
                }
                return true;
            case "cmdplayer":
                if (UtilsHandler.getPlayer().hasPerm(sender, "coreplus.command.cmdplayer")) {
                    // /crp cmdplayer <playerName> <command>
                    if (length > 3) {
                        Player player = UtilsHandler.getPlayer().getPlayerString(args[1]);
                        if (player == null) {
                            String[] placeHolders = UtilsHandler.getLang().newString();
                            placeHolders[1] = args[1]; // %targetplayer%
                            UtilsHandler.getLang().sendLangMsg(ConfigHandler.getPluginName(), ConfigHandler.getPrefix(),
                                    "Message.targetNotFound", sender, placeHolders);
                            return true;
                        }
                        String command = String.join(" ", args).substring(args[0].length() + args[1].length() + 2);
                        UtilsHandler.getCustomCommands().executeCmd(ConfigHandler.getPluginName(), player, command, true);
                        return true;
                    }
                    UtilsHandler.getLang().sendLangMsg(ConfigHandler.getPluginName(), ConfigHandler.getPrefix(),
                            "Message.Commands.cmdplayer", sender);
                } else {
                    UtilsHandler.getLang().sendLangMsg(ConfigHandler.getPluginName(), ConfigHandler.getPrefix(),
                            "Message.noPermission", sender);
                }
                return true;
            case "cmdonline":
                if (UtilsHandler.getPlayer().hasPerm(sender, "coreplus.command.cmdonline")) {
                    // /crp cmdonline <player> <expiration> <command>
                    if (length >= 4) {
                        int waitingTime;
                        try {
                            waitingTime = Integer.parseInt(args[2]);
                        } catch (Exception ex) {
                            UtilsHandler.getLang().sendLangMsg(ConfigHandler.getPluginName(), ConfigHandler.getPrefix(),
                                    "Message.Commands.cmdonline", sender);
                            return true;
                        }
                        String command = String.join(" ", args).substring(args[0].length() + args[1].length() + args[2].length() + 3);
                        UtilsHandler.getLang().sendConsoleMsg(ConfigHandler.getPrefix(),
                                "Online command start - Player: " + args[1] + ", Expiration: " + args[2] + ", Command: " + command);
                        Player player = UtilsHandler.getPlayer().getPlayerString(args[1]);
                        if (player != null) {
                            UtilsHandler.getCustomCommands().executeCmd(ConfigHandler.getPluginName(), player,
                                    command, true);
                            return true;
                        }
                        UtilsHandler.getCustomCommands().addWaiting(args[1], waitingTime, command);
                        return true;
                    }
                    UtilsHandler.getLang().sendLangMsg(ConfigHandler.getPluginName(), ConfigHandler.getPrefix(),
                            "Message.Commands.cmdonline", sender);
                } else {
                    UtilsHandler.getLang().sendLangMsg(ConfigHandler.getPluginName(), ConfigHandler.getPrefix(),
                            "Message.noPermission", sender);
                }
                return true;
        }
        UtilsHandler.getLang().sendLangMsg(ConfigHandler.getPluginName(), ConfigHandler.getPrefix(),
                "Message.unknownCommand", sender);
        return true;
    }
}