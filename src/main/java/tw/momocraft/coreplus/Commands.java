package tw.momocraft.coreplus;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import tw.momocraft.coreplus.handlers.ConfigHandler;
import tw.momocraft.coreplus.handlers.UtilsHandler;


public class Commands implements CommandExecutor {

    public boolean onCommand(final CommandSender sender, Command c, String l, String[] args) {
        switch (args.length) {
            case 0:
                if (UtilsHandler.getPlayer().hasPermission(sender, "coreplus.use")) {
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
                    if (UtilsHandler.getPlayer().hasPermission(sender, "coreplus.use")) {
                        UtilsHandler.getLang().sendMsg(ConfigHandler.getPrefix(), sender, "");
                        UtilsHandler.getLang().sendLangMsg(ConfigHandler.getPrefix(), "Message.Commands.title", sender);
                        UtilsHandler.getLang().sendMsg(ConfigHandler.getPrefix(), sender, "&f " + CorePlus.getInstance().getDescription().getName()
                                + " &ev" + CorePlus.getInstance().getDescription().getVersion() + "  &8by Momocraft");
                        UtilsHandler.getLang().sendLangMsg(ConfigHandler.getPrefix(), "Message.Commands.help", sender);
                        if (UtilsHandler.getPlayer().hasPermission(sender, "CorePlus.command.reload")) {
                            UtilsHandler.getLang().sendLangMsg(ConfigHandler.getPrefix(), "Message.Commands.reload", sender);
                        }
                        if (UtilsHandler.getPlayer().hasPermission(sender, "CorePlus.command.version")) {
                            UtilsHandler.getLang().sendLangMsg(ConfigHandler.getPrefix(), "Message.Commands.version", sender);
                        }
                        UtilsHandler.getLang().sendMsg(ConfigHandler.getPrefix(), sender, "");
                    } else {
                        UtilsHandler.getLang().sendLangMsg(ConfigHandler.getPrefix(), "Message.noPermission", sender);
                    }
                    return true;
                } else if (args[0].equalsIgnoreCase("reload")) {
                    if (UtilsHandler.getPlayer().hasPermission(sender, "coreplus.command.reload")) {
                        ConfigHandler.generateData(true);
                        UtilsHandler.getLang().sendLangMsg(ConfigHandler.getPrefix(), "Message.configReload", sender);
                    } else {
                        UtilsHandler.getLang().sendLangMsg(ConfigHandler.getPrefix(), "Message.noPermission", sender);
                    }
                    return true;
                } else if (args[0].equalsIgnoreCase("version")) {
                    if (UtilsHandler.getPlayer().hasPermission(sender, "coreplus.command.version")) {
                        UtilsHandler.getLang().sendMsg(ConfigHandler.getPrefix(), sender, "&f " + CorePlus.getInstance().getDescription().getName()
                                + " &ev" + CorePlus.getInstance().getDescription().getVersion() + "  &8by Momocraft");
                        UtilsHandler.getUpdate().check(ConfigHandler.getPrefix(), Bukkit.getConsoleSender(),
                                CorePlus.getInstance().getDescription().getName(), CorePlus.getInstance().getDescription().getVersion(), false);
                    } else {
                        UtilsHandler.getLang().sendLangMsg(ConfigHandler.getPrefix(), "Message.noPermission", sender);
                    }
                    return true;
                }
            default:
                UtilsHandler.getLang().sendLangMsg(ConfigHandler.getPrefix(), "Message.unknownCommand", sender);
                return true;
        }
    }
}