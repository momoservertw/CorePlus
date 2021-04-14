package tw.momocraft.coreplus;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import tw.momocraft.coreplus.handlers.ConfigHandler;
import tw.momocraft.coreplus.handlers.UtilsHandler;
import tw.momocraft.coreplus.utils.file.ConfigBuilder;

import java.util.ArrayList;
import java.util.List;

public class Commands implements CommandExecutor {

    public boolean onCommand(final CommandSender sender, Command cmd, String label, String[] args) {
        int length = args.length;
        if (length == 0) {
            if (UtilsHandler.getPlayer().hasPerm(sender, "coreplus.use")) {
                UtilsHandler.getMsg().sendMsg("", sender, "");
                UtilsHandler.getMsg().sendLangMsg("",
                        "Message.Commands.title", sender);
                UtilsHandler.getMsg().sendMsg("", sender,
                        "&f " + CorePlus.getInstance().getDescription().getName()
                                + " &ev" + CorePlus.getInstance().getDescription().getVersion() + "  &8by Momocraft");
                UtilsHandler.getMsg().sendLangMsg("",
                        "Message.Commands.help", sender);
                UtilsHandler.getMsg().sendMsg("", sender, "");
            } else {
                UtilsHandler.getMsg().sendLangMsg(ConfigHandler.getPrefix(),
                        "Message.noPermission", sender);
            }
            return true;
        }
        switch (args[0].toLowerCase()) {
            case "help":
                if (UtilsHandler.getPlayer().hasPerm(sender, "coreplus.use")) {
                    UtilsHandler.getMsg().sendMsg("", sender, "");
                    UtilsHandler.getMsg().sendLangMsg("", "Message.Commands.title", sender);
                    UtilsHandler.getMsg().sendMsg("", sender,
                            "&f " + CorePlus.getInstance().getDescription().getName()
                                    + " &ev" + CorePlus.getInstance().getDescription().getVersion() + "  &8by Momocraft");
                    UtilsHandler.getMsg().sendLangMsg("", "Message.Commands.help", sender);
                    if (UtilsHandler.getPlayer().hasPerm(sender, "coreplus.command.reload"))
                        UtilsHandler.getMsg().sendLangMsg("", "Message.Commands.reload", sender);
                    if (UtilsHandler.getPlayer().hasPerm(sender, "coreplus.command.version"))
                        UtilsHandler.getMsg().sendLangMsg("", "Message.Commands.version", sender);
                    if (UtilsHandler.getPlayer().hasPerm(sender, "coreplus.command.test")) {
                        UtilsHandler.getMsg().sendLangMsg("", "Message.Commands.test", sender);
                        UtilsHandler.getMsg().sendLangMsg("", "Message.Commands.testPlaceholder", sender);
                    }
                    if (UtilsHandler.getPlayer().hasPerm(sender, "coreplus.command.configbuilder")) {
                        UtilsHandler.getMsg().sendLangMsg("", "Message.Commands.configbuilderGroup", sender);
                        UtilsHandler.getMsg().sendLangMsg("", "Message.Commands.configbuilderCustom", sender);
                    }
                    if (UtilsHandler.getPlayer().hasPerm(sender, "coreplus.command.cmd"))
                        UtilsHandler.getMsg().sendLangMsg("", "Message.Commands.cmd", sender);
                    if (UtilsHandler.getPlayer().hasPerm(sender, "coreplus.command.cmdgroup"))
                        UtilsHandler.getMsg().sendLangMsg("", "Message.Commands.cmdgroup", sender);
                    if (UtilsHandler.getPlayer().hasPerm(sender, "coreplus.command.cmdplayer"))
                        UtilsHandler.getMsg().sendLangMsg("", "Message.Commands.cmdplayer", sender);
                    if (UtilsHandler.getPlayer().hasPerm(sender, "coreplus.command.cmdonline"))
                        UtilsHandler.getMsg().sendLangMsg("", "Message.Commands.cmdonline", sender);
                    UtilsHandler.getMsg().sendMsg("", sender, "");
                } else {
                    UtilsHandler.getMsg().sendLangMsg(ConfigHandler.getPrefix(),
                            "Message.noPermission", sender);
                }
                return true;
            case "reload":
                if (UtilsHandler.getPlayer().hasPerm(sender, "coreplus.command.reload")) {
                    ConfigHandler.generateData(true);
                    if (sender instanceof Player)
                        UtilsHandler.getMsg().sendLangMsg(ConfigHandler.getPrefix(),
                                "Message.configReload", null);
                    UtilsHandler.getMsg().sendLangMsg(ConfigHandler.getPrefix(),
                            "Message.configReload", sender);
                } else {
                    UtilsHandler.getMsg().sendLangMsg(ConfigHandler.getPrefix(),
                            "Message.noPermission", sender);
                }
                return true;
            case "version":
                if (UtilsHandler.getPlayer().hasPerm(sender, "coreplus.command.version")) {
                    UtilsHandler.getMsg().sendMsg("", sender,
                            "&f " + CorePlus.getInstance().getDescription().getName()
                                    + " &ev" + CorePlus.getInstance().getDescription().getVersion() + "  &8by Momocraft");
                    UtilsHandler.getUpdate().check(ConfigHandler.getPlugin(), "", sender,
                            CorePlus.getInstance().getName(), CorePlus.getInstance().getDescription().getVersion(), true);
                } else {
                    UtilsHandler.getMsg().sendLangMsg(ConfigHandler.getPrefix(),
                            "Message.noPermission", sender);
                }
                return true;
            case "test":
                if (UtilsHandler.getPlayer().hasPerm(sender, "coreplus.command.test")) {
                    if (args[1].equalsIgnoreCase("location")) {
                        // /crp test <blocks/location> <group>
                        if (length == 3) {
                            if (sender instanceof ConsoleCommandSender) {
                                UtilsHandler.getMsg().sendLangMsg(ConfigHandler.getPrefix(),
                                        "Message.onlyPlayer", sender);
                                return true;
                            }
                            Player player = (Player) sender;
                            List<String> group = new ArrayList<>();
                            group.add(args[2]);
                            if (UtilsHandler.getCondition().checkLocation(ConfigHandler.getPlugin(), player.getLocation(), group, false)) {
                                UtilsHandler.getMsg().sendMsg(ConfigHandler.getPrefix(), sender, "&aTest Succeed &7(Location: " + args[2] + ")");
                                return true;
                            }
                            UtilsHandler.getMsg().sendMsg(ConfigHandler.getPrefix(), sender, "&cTest Failed &7(Location: " + args[2] + ")");
                            return true;
                        }
                    } else if (args[1].equalsIgnoreCase("blocks")) {
                        // /crp test <blocks/location> <group>
                        if (length == 3) {
                            if (sender instanceof ConsoleCommandSender) {
                                UtilsHandler.getMsg().sendLangMsg(ConfigHandler.getPrefix(),
                                        "Message.onlyPlayer", sender);
                                return true;
                            }
                            Player player = (Player) sender;
                            List<String> group = new ArrayList<>();
                            group.add(args[2]);
                            if (UtilsHandler.getCondition().checkBlocks(player.getLocation(), group, false)) {
                                UtilsHandler.getMsg().sendMsg(ConfigHandler.getPrefix(), sender, "&aTest Succeed &7(Blocks: " + args[2] + ")");
                                return true;
                            }
                            UtilsHandler.getMsg().sendMsg(ConfigHandler.getPrefix(), sender, "&cTest Failed &7(Blocks: " + args[2] + ")");
                            return true;
                        }
                    } else if (args[1].equalsIgnoreCase("placeholder")) {
                        if (length == 4) {
                            // crp test placeholder <player/offlineplayer> <player>
                            if (args[2].equalsIgnoreCase("offlineplayer")) {
                                UtilsHandler.getCondition().getConditionTest().cmdOfflinePlayer(sender, args[3]);
                                return true;
                                // crp test placeholder <player/offlineplayer> <player>
                            } else if (args[2].equalsIgnoreCase("player")) {
                                Player player = UtilsHandler.getPlayer().getPlayer(args[1]);
                                if (player == null) {
                                    String[] placeHolders = UtilsHandler.getMsg().newString();
                                    placeHolders[1] = args[3]; // %targetplayer%
                                    UtilsHandler.getMsg().sendLangMsg(ConfigHandler.getPrefix(),
                                            "Message.targetNotFound", sender, placeHolders);
                                    return true;
                                }
                                UtilsHandler.getCondition().getConditionTest().cmdOfflinePlayer(sender, args[2]);
                                return true;
                            }
                        }
                    }
                    UtilsHandler.getMsg().sendLangMsg(ConfigHandler.getPrefix(),
                            "Message.Commands.test", sender);
                    UtilsHandler.getMsg().sendLangMsg(ConfigHandler.getPrefix(),
                            "Message.Commands.testPlaceholder", sender);
                    return true;
                } else {
                    UtilsHandler.getMsg().sendLangMsg(ConfigHandler.getPrefix(),
                            "Message.noPermission", sender);
                }
                return true;
            case "configbuilder":
                if (UtilsHandler.getPlayer().hasPerm(sender, "coreplus.command.configbuilder")) {
                    // crp configbuiler custom group
                    if (length == 3 && args[1].equalsIgnoreCase("custom")) {
                        UtilsHandler.getMsg().sendMsg(ConfigHandler.getPrefix(), sender, "&6Creating configuration for group \"" + args[2] + "\"...");
                        ConfigBuilder.startCustom(sender, args[2]);
                        return true;
                    }
                    // crp configbuilder group
                    else if (length == 2 && args[1].equalsIgnoreCase("group")) {
                        UtilsHandler.getMsg().sendMsg(ConfigHandler.getPrefix(), sender, "&6Creating configuration for group.yml...");
                        ConfigBuilder.startGroups(sender);
                        return true;
                    }
                    UtilsHandler.getMsg().sendLangMsg(ConfigHandler.getPrefix(),
                            "Message.Commands.configbuilderCustom", sender);
                    UtilsHandler.getMsg().sendLangMsg(ConfigHandler.getPrefix(),
                            "Message.Commands.configbuilderGroup", sender);
                } else {
                    UtilsHandler.getMsg().sendLangMsg(ConfigHandler.getPrefix(),
                            "Message.noPermission", sender);
                }
                return true;
            case "cmdgroup":
                if (UtilsHandler.getPlayer().hasPerm(sender, "coreplus.command.cmdgroup")) {
                    // /crp cmdgroup <command>
                    if (length == 2) {
                        UtilsHandler.getCommandManager().sendGroupCmd(ConfigHandler.getPlugin(), null, null, args[1]);
                        return true;
                        // /crp cmdgroup [player] <command>
                    } else if (length == 3) {
                        Player player = UtilsHandler.getPlayer().getPlayer(args[1]);
                        if (player == null) {
                            String[] placeHolders = UtilsHandler.getMsg().newString();
                            placeHolders[1] = args[1]; // %targetplayer%
                            UtilsHandler.getMsg().sendLangMsg(ConfigHandler.getPrefix(),
                                    "Message.targetNotFound", sender, placeHolders);
                            return true;
                        }
                        UtilsHandler.getCommandManager().sendGroupCmd(ConfigHandler.getPlugin(), player, player, args[2]);
                        return true;
                    }
                    UtilsHandler.getMsg().sendLangMsg(ConfigHandler.getPrefix(),
                            "Message.Commands.cmdgroup", sender);
                } else {
                    UtilsHandler.getMsg().sendLangMsg(ConfigHandler.getPrefix(),
                            "Message.noPermission", sender);
                }
                return true;
            case "cmd":
                if (UtilsHandler.getPlayer().hasPerm(sender, "coreplus.command.cmd")) {
                    // /crp cmd <command>
                    if (length > 1) {
                        String command = String.join(" ", args).substring(args[0].length() + 1);
                        UtilsHandler.getCommandManager().sendCmd(ConfigHandler.getPlugin(), null, null, command);
                        return true;
                    }
                    UtilsHandler.getMsg().sendLangMsg(ConfigHandler.getPrefix(),
                            "Message.Commands.cmd", sender);
                } else {
                    UtilsHandler.getMsg().sendLangMsg(ConfigHandler.getPrefix(),
                            "Message.noPermission", sender);
                }
                return true;
            case "cmdplayer":
                if (UtilsHandler.getPlayer().hasPerm(sender, "coreplus.command.cmdplayer")) {
                    // /crp cmdplayer <playerName> <command>
                    if (length > 3) {
                        Player player = UtilsHandler.getPlayer().getPlayer(args[1]);
                        if (player == null) {
                            String[] placeHolders = UtilsHandler.getMsg().newString();
                            placeHolders[1] = args[1]; // %targetplayer%
                            UtilsHandler.getMsg().sendLangMsg(ConfigHandler.getPrefix(),
                                    "Message.targetNotFound", sender, placeHolders);
                            return true;
                        }
                        String command = String.join(" ", args).substring(args[0].length() + args[1].length() + 2);
                        UtilsHandler.getCommandManager().sendCmd(ConfigHandler.getPlugin(), player, player, command);
                        return true;
                    }
                    UtilsHandler.getMsg().sendLangMsg(ConfigHandler.getPrefix(),
                            "Message.Commands.cmdplayer", sender);
                } else {
                    UtilsHandler.getMsg().sendLangMsg(ConfigHandler.getPrefix(),
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
                            UtilsHandler.getMsg().sendLangMsg(ConfigHandler.getPrefix(),
                                    "Message.Commands.cmdonline", sender);
                            return true;
                        }
                        String command = String.join(" ", args).substring(args[0].length() + args[1].length() + args[2].length() + 3);
                        UtilsHandler.getMsg().sendConsoleMsg(ConfigHandler.getPrefix(),
                                "Online command start - Player: " + args[1] + ", Expiration: " + args[2] + ", Command: " + command);
                        Player player = UtilsHandler.getPlayer().getPlayer(args[1]);
                        if (player != null) {
                            UtilsHandler.getCommandManager().sendCmd(ConfigHandler.getPlugin(), player, player, command);
                            return true;
                        }
                        UtilsHandler.getCommandManager().addOnlineCommand(ConfigHandler.getPlugin(), args[1], waitingTime, command);
                        return true;
                    }
                    UtilsHandler.getMsg().sendLangMsg(ConfigHandler.getPrefix(),
                            "Message.Commands.cmdonline", sender);
                } else {
                    UtilsHandler.getMsg().sendLangMsg(ConfigHandler.getPrefix(),
                            "Message.noPermission", sender);
                }
                return true;
        }
        UtilsHandler.getMsg().sendLangMsg(ConfigHandler.getPrefix(),
                "Message.unknownCommand", sender);
        return true;
    }
}