package tw.momocraft.coreplus;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.util.StringUtil;
import tw.momocraft.coreplus.handlers.ConfigHandler;
import tw.momocraft.coreplus.handlers.UtilsHandler;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TabComplete implements TabCompleter {

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        final List<String> completions = new ArrayList<>();
        final List<String> commands = new ArrayList<>();
        int length = args.length;
        if (length == 1) {
            if (UtilsHandler.getPlayer().hasPerm(sender, "coreplus.use"))
                commands.add("help");
            if (UtilsHandler.getPlayer().hasPerm(sender, "coreplus.command.reload"))
                commands.add("reload");
            if (UtilsHandler.getPlayer().hasPerm(sender, "coreplus.command.version"))
                commands.add("version");
            if (UtilsHandler.getPlayer().hasPerm(sender, "coreplus.command.test"))
                commands.add("test");
            if (UtilsHandler.getPlayer().hasPerm(sender, "coreplus.command.configbuilder"))
                commands.add("configbuilder");
            if (UtilsHandler.getPlayer().hasPerm(sender, "coreplus.command.cmdgroup"))
                commands.add("cmdgroup");
            if (UtilsHandler.getPlayer().hasPerm(sender, "coreplus.command.cmd"))
                commands.add("cmd");
            if (UtilsHandler.getPlayer().hasPerm(sender, "coreplus.command.cmdplayer"))
                commands.add("cmdplayer");
            if (UtilsHandler.getPlayer().hasPerm(sender, "coreplus.command.cmdonline"))
                commands.add("cmdonline");
        } else {
            switch (args[0].toLowerCase()) {
                case "test":
                    if (UtilsHandler.getPlayer().hasPerm(sender, "coreplus.command.test")) {
                        if (length == 2) {
                            commands.add("placeholder");
                        } else if (length == 3) {
                            commands.add("all");
                        } else if (length == 4) {
                            commands.addAll(UtilsHandler.getPlayer().getOnlinePlayerNames());
                        }
                    }
                    break;
                case "configbuilder":
                    if (UtilsHandler.getPlayer().hasPerm(sender, "coreplus.command.configbuilder")) {
                        if (length == 2) {
                            commands.add("custom");
                            commands.add("group");
                        } else if (length == 3) {
                            if (args[1].equals("custom"))
                                commands.addAll(ConfigHandler.getConfigPath().getConfigBuilderCustomProp().keySet());
                        }
                    }
                    break;
                case "cmdgroup":
                    if (UtilsHandler.getPlayer().hasPerm(sender, "coreplus.command.cmdgroup")) {
                        if (length == 2)
                            commands.addAll(ConfigHandler.getConfigPath().getCmdProp().keySet());
                    }
                    break;
                case "cmd":
                    if (UtilsHandler.getPlayer().hasPerm(sender, "coreplus.command.cmd")) {
                        if (length == 2) {
                            commands.add("custom: ");
                            commands.add("condition: ");
                            commands.add("print:  ");
                            commands.add("log:");
                            commands.add("log-group:  ");
                            commands.add("broadcast:");
                            commands.add("discord: ");
                            commands.add("discord-chat: ");
                            commands.add("bungee: ");
                            commands.add("switch: ");
                            commands.add("console: ");
                            commands.add("bungee: ");
                            commands.add("op: ");
                            commands.add("player: ");
                            commands.add("chat-op: ");
                            commands.add("chat: ");
                            commands.add("message: ");
                            commands.add("action-bar: ");
                            commands.add("title: ");
                            commands.add("sound-group: ");
                            commands.add("particle: ");
                            commands.add("particle-group: ");
                        } else {
                            commands.addAll(UtilsHandler.getPlayer().getOnlinePlayerNames());
                        }
                    }
                    break;
                case "cmdplayer":
                    if (UtilsHandler.getPlayer().hasPerm(sender, "coreplus.command.cmdplayer")) {
                        if (length == 2) {
                            commands.addAll(UtilsHandler.getPlayer().getOnlinePlayerNames());
                        } else {
                            commands.add("custom: ");
                            commands.add("condition: ");
                            commands.add("print:  ");
                            commands.add("log:");
                            commands.add("log-group:  ");
                            commands.add("broadcast:");
                            commands.add("discord: ");
                            commands.add("discord-chat: ");
                            commands.add("bungee: ");
                            commands.add("switch: ");
                            commands.add("console: ");
                            commands.add("bungee: ");
                            commands.add("op: ");
                            commands.add("player: ");
                            commands.add("chat-op: ");
                            commands.add("chat: ");
                            commands.add("message: ");
                            commands.add("action-bar: ");
                            commands.add("title: ");
                            commands.add("sound-group: ");
                            commands.add("particle: ");
                            commands.add("particle-group: ");
                        }
                    }
                    break;
                case "cmdonline":
                    if (UtilsHandler.getPlayer().hasPerm(sender, "coreplus.command.cmdonline")) {
                        if (length == 2) {
                            commands.addAll(UtilsHandler.getPlayer().getOnlinePlayerNames());
                        } else {
                            commands.add("custom: ");
                            commands.add("condition: ");
                            commands.add("print:  ");
                            commands.add("log:");
                            commands.add("log-group:  ");
                            commands.add("broadcast:");
                            commands.add("discord: ");
                            commands.add("discord-chat: ");
                            commands.add("bungee: ");
                            commands.add("switch: ");
                            commands.add("console: ");
                            commands.add("bungee: ");
                            commands.add("op: ");
                            commands.add("player: ");
                            commands.add("chat-op: ");
                            commands.add("chat: ");
                            commands.add("message: ");
                            commands.add("actionbar: ");
                            commands.add("actionbar-group: ");
                            commands.add("title: ");
                            commands.add("sound-group: ");
                            commands.add("particle: ");
                            commands.add("particle-group: ");
                        }
                    }
                    break;
            }
        }
        StringUtil.copyPartialMatches(args[(args.length - 1)], commands, completions);
        Collections.sort(completions);
        return completions;
    }
}