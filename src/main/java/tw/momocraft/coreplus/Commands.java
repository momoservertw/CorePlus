package tw.momocraft.coreplus;

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
        int length = args.length;
        if (length == 0) {
            if (CorePlusAPI.getPlayerManager().hasPerm(sender, "coreplus.use")) {
                CorePlusAPI.getLangManager().sendMsg(ConfigHandler.getPrefix(), sender, "");
                CorePlusAPI.getLangManager().sendLangMsg(ConfigHandler.getPrefix(), "Message.Commands.title", sender);
                CorePlusAPI.getLangManager().sendMsg(ConfigHandler.getPrefix(), sender, "&f " + CorePlus.getInstance().getDescription().getName()
                        + " &ev" + CorePlus.getInstance().getDescription().getVersion() + "  &8by Momocraft");
                CorePlusAPI.getLangManager().sendLangMsg(ConfigHandler.getPrefix(), "Message.Commands.help", sender);
                CorePlusAPI.getLangManager().sendMsg(ConfigHandler.getPrefix(), sender, "");
            } else {
                CorePlusAPI.getLangManager().sendLangMsg(ConfigHandler.getPrefix(), "Message.noPermission", sender);
            }
            return true;
        }
        switch (args[0].toLowerCase()) {
            case "help":
                if (CorePlusAPI.getPlayerManager().hasPerm(sender, "coreplus.use")) {
                    CorePlusAPI.getLangManager().sendMsg(ConfigHandler.getPrefix(), sender, "");
                    CorePlusAPI.getLangManager().sendLangMsg(ConfigHandler.getPrefix(), "Message.Commands.title", sender);
                    CorePlusAPI.getLangManager().sendMsg(ConfigHandler.getPrefix(), sender, "&f " + CorePlus.getInstance().getDescription().getName()
                            + " &ev" + CorePlus.getInstance().getDescription().getVersion() + "  &8by Momocraft");
                    CorePlusAPI.getLangManager().sendLangMsg(ConfigHandler.getPrefix(), "Message.Commands.help", sender);
                    if (CorePlusAPI.getPlayerManager().hasPerm(sender, "coreplus.command.reload")) {
                        CorePlusAPI.getLangManager().sendLangMsg(ConfigHandler.getPrefix(), "Message.Commands.reload", sender);
                    }
                    if (CorePlusAPI.getPlayerManager().hasPerm(sender, "coreplus.command.version")) {
                        CorePlusAPI.getLangManager().sendLangMsg(ConfigHandler.getPrefix(), "Message.Commands.version", sender);
                    }
                    if (CorePlusAPI.getPlayerManager().hasPerm(sender, "coreplus.command.residence.test")) {
                        UtilsHandler.getLang().sendLangMsg(ConfigHandler.getPrefix(), "Message.Commands.test", sender);
                    }
                    if (CorePlusAPI.getPlayerManager().hasPerm(sender, "coreplus.command.residence.configbuilder")) {
                        UtilsHandler.getLang().sendLangMsg(ConfigHandler.getPrefix(), "Message.Commands.configbuilder", sender);
                    }
                    if (CorePlusAPI.getPlayerManager().hasPerm(sender, "coreplus.command.residence.cmd")) {
                        UtilsHandler.getLang().sendLangMsg(ConfigHandler.getPrefix(), "Message.Commands.cmd", sender);
                    }
                    if (CorePlusAPI.getPlayerManager().hasPerm(sender, "coreplus.command.residence.cmdcustom")) {
                        UtilsHandler.getLang().sendLangMsg(ConfigHandler.getPrefix(), "Message.Commands.cmdcustom", sender);
                    }
                    if (CorePlusAPI.getPlayerManager().hasPerm(sender, "coreplus.command.residence.cmdplayer")) {
                        UtilsHandler.getLang().sendLangMsg(ConfigHandler.getPrefix(), "Message.Commands.cmdplayer", sender);
                    }
                    if (CorePlusAPI.getPlayerManager().hasPerm(sender, "coreplus.command.residence.cmdonline")) {
                        UtilsHandler.getLang().sendLangMsg(ConfigHandler.getPrefix(), "Message.Commands.cmdonline", sender);
                    }
                    CorePlusAPI.getLangManager().sendMsg(ConfigHandler.getPrefix(), sender, "");
                } else {
                    CorePlusAPI.getLangManager().sendLangMsg(ConfigHandler.getPrefix(), "Message.noPermission", sender);
                }
                return true;
            case "reload":
                if (CorePlusAPI.getPlayerManager().hasPerm(sender, "coreplus.command.reload")) {
                    ConfigHandler.generateData(true);
                    CorePlusAPI.getLangManager().sendLangMsg(ConfigHandler.getPrefix(), "Message.configReload", sender);
                } else {
                    CorePlusAPI.getLangManager().sendLangMsg(ConfigHandler.getPrefix(), "Message.noPermission", sender);
                }
                return true;
            case "version":
                if (CorePlusAPI.getPlayerManager().hasPerm(sender, "coreplus.command.version")) {
                    CorePlusAPI.getLangManager().sendMsg(ConfigHandler.getPrefix(), sender, "&f " + CorePlus.getInstance().getDescription().getName()
                            + " &ev" + CorePlus.getInstance().getDescription().getVersion() + "  &8by Momocraft");
                    CorePlusAPI.getUpdateManager().check(ConfigHandler.getPrefix(), sender,
                            CorePlus.getInstance().getName(), CorePlus.getInstance().getDescription().getVersion(), true);
                } else {
                    CorePlusAPI.getLangManager().sendLangMsg(ConfigHandler.getPrefix(), "Message.noPermission", sender);
                }
                return true;
            case "test":
                if (CorePlusAPI.getPlayerManager().hasPerm(sender, "coreplus.command.test")) {
                    // /crp test <blocks/location> <group>
                    if (length == 3) {
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
                    UtilsHandler.getLang().sendLangMsg(ConfigHandler.getPrefix(), "Message.Commands.test", sender);
                    return true;
                } else {
                    CorePlusAPI.getLangManager().sendLangMsg(ConfigHandler.getPrefix(), "Message.noPermission", sender);
                }
                return true;
            case "configbuilder":
                if (CorePlusAPI.getPlayerManager().hasPerm(sender, "coreplus.command.configbuilder")) {
                    if (length == 2) {
                        UtilsHandler.getLang().sendMsg(ConfigHandler.getPrefix(), sender, "Creating configuration for " + args[1] + "...");
                        ConfigBuilder.start(sender, args[1]);
                        return true;
                    }
                    UtilsHandler.getLang().sendLangMsg(ConfigHandler.getPrefix(), "Message.Commands.configbuilder", sender);
                } else {
                    CorePlusAPI.getLangManager().sendLangMsg(ConfigHandler.getPrefix(), "Message.noPermission", sender);
                }
                return true;
            case "cmdcustom":
                if (CorePlusAPI.getPlayerManager().hasPerm(sender, "coreplus.command.cmdcustom")) {
                    // /crp cmdcustom <command>
                    if (length == 2) {
                        UtilsHandler.getCustomCommands().dispatchCustomCmd(ConfigHandler.getPluginPrefix(), null, args[1], true);
                        return true;
                        // /crp cmdcustom [player] <command>
                    } else if (length == 3) {
                        Player player = CorePlusAPI.getPlayerManager().getPlayerString(args[1]);
                        if (player == null) {
                            String[] placeHolders = CorePlusAPI.getLangManager().newString();
                            placeHolders[1] = args[1]; // %targetplayer%
                            CorePlusAPI.getLangManager().sendLangMsg(ConfigHandler.getPrefix(), "Message.targetNotFound", sender, placeHolders);
                            return true;
                        }
                        UtilsHandler.getCustomCommands().dispatchCustomCmd(ConfigHandler.getPluginPrefix(), player, args[2], true);
                        return true;
                    }
                    UtilsHandler.getLang().sendLangMsg(ConfigHandler.getPrefix(), "Message.Commands.cmdcustom", sender);
                } else {
                    CorePlusAPI.getLangManager().sendLangMsg(ConfigHandler.getPrefix(), "Message.noPermission", sender);
                }
                return true;
            case "cmd":
                if (CorePlusAPI.getPlayerManager().hasPerm(sender, "coreplus.command.cmd")) {
                    // /crp cmd <command>
                    if (length > 1) {
                        String command = String.join(" ", args).substring(args[0].length() + 1);
                        if (sender instanceof ConsoleCommandSender) {
                            UtilsHandler.getCustomCommands().executeCmd(ConfigHandler.getPluginPrefix(), command, true);
                        } else {
                            UtilsHandler.getCustomCommands().executeCmd(ConfigHandler.getPluginPrefix(), (Player) sender, command, true);
                        }
                        return true;
                    }
                    UtilsHandler.getLang().sendLangMsg(ConfigHandler.getPrefix(), "Message.Commands.cmd", sender);
                } else {
                    CorePlusAPI.getLangManager().sendLangMsg(ConfigHandler.getPrefix(), "Message.noPermission", sender);
                }
                return true;
            case "cmdplayer":
                if (CorePlusAPI.getPlayerManager().hasPerm(sender, "coreplus.command.cmdplayer")) {
                    // /crp cmdplayer <playerName> <command>
                    if (length > 3) {
                        Player player = CorePlusAPI.getPlayerManager().getPlayerString(args[1]);
                        if (player == null) {
                            String[] placeHolders = CorePlusAPI.getLangManager().newString();
                            placeHolders[1] = args[1]; // %targetplayer%
                            CorePlusAPI.getLangManager().sendLangMsg(ConfigHandler.getPrefix(), "Message.targetNotFound", sender, placeHolders);
                            return true;
                        }
                        String command = String.join(" ", args).substring(args[0].length() + args[1].length() + 2);
                        UtilsHandler.getCustomCommands().executeCmd(ConfigHandler.getPluginPrefix(), player, command, true);
                        return true;
                    }
                    UtilsHandler.getLang().sendLangMsg(ConfigHandler.getPrefix(), "Message.Commands.cmdplayer", sender);
                } else {
                    CorePlusAPI.getLangManager().sendLangMsg(ConfigHandler.getPrefix(), "Message.noPermission", sender);
                }
                return true;
            case "cmdonline":
                if (CorePlusAPI.getPlayerManager().hasPerm(sender, "coreplus.command.cmdonline")) {
                    // /crp cmdonline <player> <expiration> <command>
                    if (length >= 4) {
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
                        return true;
                    }
                    UtilsHandler.getLang().sendLangMsg(ConfigHandler.getPrefix(), "Message.Commands.cmdonline", sender);
                } else {
                    CorePlusAPI.getLangManager().sendLangMsg(ConfigHandler.getPrefix(), "Message.noPermission", sender);
                }
                return true;
        }
        UtilsHandler.getLang().sendLangMsg(ConfigHandler.getPrefix(), "Message.unknownCommand", sender);
        return true;
    }
}