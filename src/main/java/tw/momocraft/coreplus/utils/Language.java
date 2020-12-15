package tw.momocraft.coreplus.utils;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import tw.momocraft.coreplus.CorePlus;
import tw.momocraft.coreplus.api.LanguageInterface;
import tw.momocraft.coreplus.handlers.ConfigHandler;

import java.util.Arrays;

public class Language implements LanguageInterface {

    @Override
    public String[] newString() {
        return new String[20];
    }

    @Override
    public void sendChatMsg(String prefix, Player player, String message) {
        if (prefix == null)
            prefix = "";
        message = prefix + message;
        message = ChatColor.translateAlternateColorCodes('&', message);
        player.chat(message);
    }

    @Override
    public void sendBroadcastMsg(String prefix, String message) {
        if (prefix == null)
            prefix = "";
        message = prefix + message;
        message = ChatColor.translateAlternateColorCodes('&', message);
        Bukkit.broadcastMessage(message);
    }

    @Override
    public void sendConsoleMsg(String prefix, String message) {
        if (prefix == null)
            prefix = "";
        message = prefix + message;
        message = ChatColor.translateAlternateColorCodes('&', message);
        CorePlus.getInstance().getServer().getConsoleSender().sendMessage(message);
    }

    @Override
    public void sendDebugMsg(String prefix, String message) {
        if (prefix == null)
            prefix = "";
        if (ConfigHandler.isDebugging()) {
            message = prefix + "&7[Debug]&r " + message;
            message = ChatColor.translateAlternateColorCodes('&', message);
            CorePlus.getInstance().getServer().getConsoleSender().sendMessage(message);
        }
    }

    @Override
    public void sendErrorMsg(String prefix, String message) {
        if (prefix == null)
            prefix = "";
        message = prefix + "&4[Error]&e " + message;
        message = ChatColor.translateAlternateColorCodes('&', message);
        CorePlus.getInstance().getServer().getConsoleSender().sendMessage(message);
    }

    @Override
    public void sendPlayerMsg(String prefix, Player player, String message) {
        if (prefix == null)
            prefix = "";
        message = prefix + message;
        message = ChatColor.translateAlternateColorCodes('&', message);
        player.sendMessage(message);
    }

    @Override
    public void sendMsg(String prefix, CommandSender sender, String message) {
        if (prefix == null)
            prefix = "";
        message = prefix + message;
        message = ChatColor.translateAlternateColorCodes('&', message);
        sender.sendMessage(message);
    }

    @Override
    public void sendDebugTrace(String prefix, Exception e) {
        if (ConfigHandler.isDebugging()) {
            if (prefix == null)
                prefix = "";
            prefix = ChatColor.translateAlternateColorCodes('&', prefix);
            CorePlus.getInstance().getServer().getConsoleSender().sendMessage(prefix + "showing debug trace.");
            e.printStackTrace();
        }
    }

    @Override
    public void sendFeatureMsg(String prefix, String feature, String target, String check, String action, String detail, StackTraceElement ste) {
        if (!ConfigHandler.isDebugging()) {
            return;
        }
        if (prefix == null)
            prefix = "";
        prefix = ChatColor.translateAlternateColorCodes('&', prefix);
        switch (action) {
            case "cancel":
            case "remove":
            case "kill":
            case "damage":
            case "fail":
            case "warning":
                sendDebugMsg(prefix, "&f" + feature + "&8 - &f" + target + "&8 : &f" + check + "&8, &c" + action + "&8, &7" + detail
                        + " &8(" + ste.getClassName() + " " + ste.getMethodName() + " " + ste.getLineNumber() + ")");
                break;
            case "continue":
            case "bypass":
            case "change":
                sendDebugMsg(prefix, "&f" + feature + "&8 - &f" + target + "&8 : &f" + check + "&8, &e" + action + "&8, &7" + detail
                        + " &8(" + ste.getClassName() + " " + ste.getMethodName() + " " + ste.getLineNumber() + ")");
                break;
            case "return":
            default:
                sendDebugMsg(prefix, "&f" + feature + "&8 - &f" + target + "&8 : &f" + check + "&8, &a" + action + "&8, &7" + detail
                        + " &8(" + ste.getClassName() + " " + ste.getMethodName() + " " + ste.getLineNumber() + ")");
                break;
        }
    }

    @Override
    public void sendFeatureMsg(String prefix, String feature, String target, String check, String action, StackTraceElement ste) {
        if (!ConfigHandler.isDebugging()) {
            return;
        }
        if (prefix == null)
            prefix = "";
        prefix = ChatColor.translateAlternateColorCodes('&', prefix);
        switch (action) {
            case "cancel":
            case "remove":
            case "kill":
            case "damage":
            case "warning":
                sendDebugMsg(prefix, "&f" + feature + "&8 - &f" + target + "&8 : &f" + check + "&8, &c" + action
                        + " &8(" + ste.getClassName() + " " + ste.getMethodName() + " " + ste.getLineNumber() + ")");
                break;
            case "continue":
            case "bypass":
            case "change":
                sendDebugMsg(prefix, "&f" + feature + "&8 - &f" + target + "&8 : &f" + check + "&8, &e" + action
                        + " &8(" + ste.getClassName() + " " + ste.getMethodName() + " " + ste.getLineNumber() + ")");
                break;
            case "return":
            default:
                sendDebugMsg(prefix, "&f" + feature + "&8 - &f" + target + "&8 : &f" + check + "&8, &a" + action
                        + " &8(" + ste.getClassName() + " " + ste.getMethodName() + " " + ste.getLineNumber() + ")");
                break;
        }
    }

    @Override
    public void sendLangMsg(String prefix, String input, CommandSender sender, String... placeHolder) {
        if (input == null) {
            return;
        }
        if (prefix == null)
            prefix = "";
        prefix = ChatColor.translateAlternateColorCodes('&', prefix);
        Player player = null;
        if (sender instanceof Player) {
            player = (Player) sender;
        }
        String langMessage = ConfigHandler.getConfig("config.yml").getString(input);
        if (langMessage != null && !langMessage.isEmpty()) {
            input = langMessage;
        }
        input = translateLangHolders(input, initializeRows(placeHolder));
        input = Utils.translateLayout(input, player);
        String[] langLines = input.split(" /n ");
        for (String langLine : langLines) {
            sender.sendMessage(prefix + langLine);
        }
    }

    private String[] initializeRows(String... placeHolder) {
        if (placeHolder == null || placeHolder.length != newString().length) {
            String[] langHolder = newString();
            Arrays.fill(langHolder, "null");
            return langHolder;
        } else {
            for (int i = 0; i < placeHolder.length; i++) {
                if (placeHolder[i] == null) {
                    placeHolder[i] = "null";
                }
            }
            return placeHolder;
        }
    }

    private String translateLangHolders(String langMessage, String... langHolder) {
        return langMessage
                .replace("%command%", langHolder[0])
                .replace("%player%", langHolder[1])
                .replace("%targetplayer%", langHolder[2])
                .replace("%prefix%", langHolder[3])
                .replace("%group%", langHolder[4])
                .replace("%pricetype%", langHolder[5])
                .replace("%price%", langHolder[6])
                .replace("%balance%", langHolder[7])
                .replace("%amount%", langHolder[8])
                .replace("%item%", langHolder[9])

                .replace("%distance%", langHolder[11])
                .replace("%nick%", langHolder[12])
                .replace("%nick_color%", langHolder[13])
                .replace("%nick_length%", langHolder[14])
                ;
    }
}