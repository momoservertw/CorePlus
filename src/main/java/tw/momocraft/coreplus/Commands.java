package tw.momocraft.coreplus;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import tw.momocraft.coreplus.handlers.ConfigHandler;


public class Commands implements CommandExecutor {

    public boolean onCommand(final CommandSender sender, Command c, String l, String[] args) {
        if (args.length == 0) {
            if (ConfigHandler.getPerm().hasPermission(sender, "coreplus.use")) {
                ConfigHandler.getLang().sendMsg(ConfigHandler.getPrefix(), sender, "");
                ConfigHandler.getLang().sendLangMsg(ConfigHandler.getPrefix(), ConfigHandler.getConfigPath().getMsgTitle(), sender);
                if (ConfigHandler.getPerm().hasPermission(sender, "CorePlus.command.version")) {
                    ConfigHandler.getLang().sendMsg(ConfigHandler.getPrefix(), sender, "&f " + CorePlus.getInstance().getDescription().getName()
                            + " &ev" + CorePlus.getInstance().getDescription().getVersion() + "  &8by Momocraft");
                }
                ConfigHandler.getLang().sendLangMsg(ConfigHandler.getPrefix(), "Message.Commands.help", sender);
                ConfigHandler.getLang().sendMsg(ConfigHandler.getPrefix(), sender, "");
            } else {
                ConfigHandler.getLang().sendLangMsg(ConfigHandler.getPrefix(), "Message.noPermission", sender);
            }
            return true;
        } else if (args.length == 1 && args[0].equalsIgnoreCase("help")) {
            if (ConfigHandler.getPerm().hasPermission(sender, "coreplus.use")) {
                ConfigHandler.getLang().sendMsg(ConfigHandler.getPrefix(), sender, "");
                ConfigHandler.getLang().sendLangMsg(ConfigHandler.getPrefix(), "Message.Commands.title", sender);
                if (ConfigHandler.getPerm().hasPermission(sender, "CorePlus.command.version")) {
                    ConfigHandler.getLang().sendMsg(ConfigHandler.getPrefix(), sender, "&f " + CorePlus.getInstance().getDescription().getName()
                            + " &ev" + CorePlus.getInstance().getDescription().getVersion() + "  &8by Momocraft");
                }
                ConfigHandler.getLang().sendLangMsg(ConfigHandler.getPrefix(), "Message.Commands.help", sender);
                if (ConfigHandler.getPerm().hasPermission(sender, "CorePlus.command.reload")) {
                    ConfigHandler.getLang().sendLangMsg(ConfigHandler.getPrefix(), "Message.Commands.reload", sender);
                }
                ConfigHandler.getLang().sendMsg(ConfigHandler.getPrefix(), sender, "");
            } else {
                ConfigHandler.getLang().sendLangMsg(ConfigHandler.getPrefix(), "Message.noPermission", sender);
            }
            return true;
        } else if (args.length == 1 && args[0].equalsIgnoreCase("reload")) {
            if (ConfigHandler.getPerm().hasPermission(sender, "coreplus.command.reload")) {
                ConfigHandler.generateData(true);
                ConfigHandler.getLang().sendLangMsg(ConfigHandler.getPrefix(), "Message.configReload", sender);
            } else {
                ConfigHandler.getLang().sendLangMsg(ConfigHandler.getPrefix(), "Message.noPermission", sender);
            }
            return true;
        } else if (args.length == 1 && args[0].equalsIgnoreCase("version")) {
            if (ConfigHandler.getPerm().hasPermission(sender, "coreplus.command.version")) {
                ConfigHandler.getLang().sendMsg(ConfigHandler.getPrefix(), sender, "&f " + CorePlus.getInstance().getDescription().getName()
                        + " &ev" + CorePlus.getInstance().getDescription().getVersion() + "  &8by Momocraft");
                ConfigHandler.getUpdater().check(ConfigHandler.getPrefix(), Bukkit.getConsoleSender(), CorePlus.getInstance().getDescription().getName(), CorePlus.getInstance().getDescription().getVersion());
            } else {
                ConfigHandler.getLang().sendLangMsg(ConfigHandler.getPrefix(), "Message.noPermission", sender);
            }
            return true;
        } else {
            ConfigHandler.getLang().sendLangMsg(ConfigHandler.getPrefix(), "Message.unknownCommand", sender);
            return true;
        }
    }
}