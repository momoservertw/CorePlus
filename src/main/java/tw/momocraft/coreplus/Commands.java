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
        if (!UtilsHandler.getPlayer().hasPerm(sender, "coreplus.admin")) {
            return true;
        }
        switch (args.length) {
            case 0:
                UtilsHandler.getLang().sendMsg(ConfigHandler.getPrefix(), sender, "");
                UtilsHandler.getLang().sendLangMsg(ConfigHandler.getPrefix(), "Message.Commands.title", sender);
                UtilsHandler.getLang().sendMsg(ConfigHandler.getPrefix(), sender, "&f " + CorePlus.getInstance().getDescription().getName()
                        + " &ev" + CorePlus.getInstance().getDescription().getVersion() + "  &8by Momocraft");
                UtilsHandler.getLang().sendLangMsg(ConfigHandler.getPrefix(), "Message.Commands.help", sender);
                UtilsHandler.getLang().sendMsg(ConfigHandler.getPrefix(), sender, "");
                return true;
            case 1:
                if (args[0].equalsIgnoreCase("help")) {
                    UtilsHandler.getLang().sendMsg(ConfigHandler.getPrefix(), sender, "");
                    UtilsHandler.getLang().sendLangMsg(ConfigHandler.getPrefix(), "Message.Commands.title", sender);
                    UtilsHandler.getLang().sendMsg(ConfigHandler.getPrefix(), sender, "&f " + CorePlus.getInstance().getDescription().getName()
                            + " &ev" + CorePlus.getInstance().getDescription().getVersion() + "  &8by Momocraft");
                    UtilsHandler.getLang().sendLangMsg(ConfigHandler.getPrefix(), "Message.Commands.help", sender);
                    UtilsHandler.getLang().sendLangMsg(ConfigHandler.getPrefix(), "Message.Commands.reload", sender);
                    UtilsHandler.getLang().sendLangMsg(ConfigHandler.getPrefix(), "Message.Commands.version", sender);
                    UtilsHandler.getLang().sendLangMsg(ConfigHandler.getPrefix(), "Message.Commands.test", sender);
                    UtilsHandler.getLang().sendLangMsg(ConfigHandler.getPrefix(), "Message.Commands.configbuilder", sender);
                    UtilsHandler.getLang().sendLangMsg(ConfigHandler.getPrefix(), "Message.Commands.cmd", sender);
                    UtilsHandler.getLang().sendLangMsg(ConfigHandler.getPrefix(), "Message.Commands.cmdcustom", sender);
                    UtilsHandler.getLang().sendLangMsg(ConfigHandler.getPrefix(), "Message.Commands.cmdplayer", sender);
                    UtilsHandler.getLang().sendLangMsg(ConfigHandler.getPrefix(), "Message.Commands.cmdonline", sender);
                    UtilsHandler.getLang().sendMsg(ConfigHandler.getPrefix(), sender, "");
                    return true;
                } else if (args[0].equalsIgnoreCase("reload")) {
                    ConfigHandler.generateData(true);
                    UtilsHandler.getLang().sendLangMsg(ConfigHandler.getPrefix(), "Message.configReload", sender);
                    return true;
                } else if (args[0].equalsIgnoreCase("version")) {
                    UtilsHandler.getLang().sendMsg(ConfigHandler.getPrefix(), sender, "&f " + CorePlus.getInstance().getDescription().getName()
                            + " &ev" + CorePlus.getInstance().getDescription().getVersion() + "  &8by Momocraft");
                    UtilsHandler.getUpdate().check(ConfigHandler.getPrefix(), Bukkit.getConsoleSender(),
                            CorePlus.getInstance().getDescription().getName(), CorePlus.getInstance().getDescription().getVersion(), false);
                    return true;
                } else if (args[0].equalsIgnoreCase("test")) {
                    UtilsHandler.getLang().sendLangMsg(ConfigHandler.getPrefix(), "Message.Commands.test", sender);
                    return true;
                } else if (args[0].equalsIgnoreCase("configbuilder")) {
                    UtilsHandler.getLang().sendLangMsg(ConfigHandler.getPrefix(), "Message.Commands.configbuilder", sender);
                    return true;
                } else if (args[0].equalsIgnoreCase("cmd")) {
                    UtilsHandler.getLang().sendLangMsg(ConfigHandler.getPrefix(), "Message.Commands.cmd", sender);
                    return true;
                } else if (args[0].equalsIgnoreCase("cmdplayer")) {
                    UtilsHandler.getLang().sendLangMsg(ConfigHandler.getPrefix(), "Message.Commands.cmdplayer", sender);
                    return true;
                } else if (args[0].equalsIgnoreCase("cmdonline")) {
                    UtilsHandler.getLang().sendLangMsg(ConfigHandler.getPrefix(), "Message.Commands.cmdonline", sender);
                    return true;
                }
                break;
            case 2:
                if (args[0].equalsIgnoreCase("configbuilder")) {
                    UtilsHandler.getLang().sendMsg(ConfigHandler.getPrefix(), sender, "Creating configuration for " + args[1] + "...");
                    ConfigBuilder.start(sender, args[1]);
                    return true;
                }
                break;
            case 3:
                // /crp test <blocks/location> <group>
                if (args[0].equalsIgnoreCase("test")) {
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
                    return true;
                } else if (args[0].equalsIgnoreCase("promoteperm")) {

                }
                break;
        }
        // /crp cmd <command>
        if (args.length > 1 && args[0].equalsIgnoreCase("cmd")) {
            String command = String.join(" ", args).substring(args[0].length() + 1);
            if (sender instanceof ConsoleCommandSender) {
                UtilsHandler.getCustomCommands().executeCmd(ConfigHandler.getPluginPrefix(), command, true);
            } else {
                UtilsHandler.getCustomCommands().executeCmd(ConfigHandler.getPluginPrefix(), (Player) sender, command, true);
            }
            return true;
            // /crp cmdplayer <playerName> <command>
        } else if (args.length > 3 && args[0].equalsIgnoreCase("cmdplayer")) {
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
            // /crp cmdonline <player> <expiration> <command>
        } else if (args.length > 5 && args[0].equalsIgnoreCase("cmdonline")) {
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
        UtilsHandler.getLang().sendLangMsg(ConfigHandler.getPrefix(), "Message.unknownCommand", sender);
        return true;
    }
}