package tw.momocraft.coreplus.utils.language;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import tw.momocraft.coreplus.CorePlus;
import tw.momocraft.coreplus.api.LanguageInterface;
import tw.momocraft.coreplus.handlers.ConfigHandler;
import tw.momocraft.coreplus.handlers.UtilsHandler;

import java.io.File;
import java.util.Arrays;

public class LanguageUtils implements LanguageInterface {

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
        message = prefix + message;
        message = ChatColor.translateAlternateColorCodes('&', message);
        CorePlus.getInstance().getServer().getConsoleSender().sendMessage(message);
    }

    @Override
    public void sendDebugMsg(String prefix, String message) {
        if (ConfigHandler.isDebugging()) {
            message = prefix + "&7[Debug]&r " + message;
            message = ChatColor.translateAlternateColorCodes('&', message);
            CorePlus.getInstance().getServer().getConsoleSender().sendMessage(message);
        }
    }

    @Override
    public void sendErrorMsg(String prefix, String message) {
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
            prefix = ChatColor.translateAlternateColorCodes('&', prefix);
            sendErrorMsg(prefix, "showing debug trace.");
            e.printStackTrace();
        }
    }

    @Override
    public void sendFeatureMsg(String prefix, String feature, String target, String check, String action, String detail, StackTraceElement ste) {
        if (!ConfigHandler.isDebugging()) {
            return;
        }
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
        input = translateLangHolders(player, input, initializeRows(placeHolder));
        input = UtilsHandler.getUtil().translateLayout(input, player);
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

    @Override
    public String[] newString() {
        return new String[25];
    }

    private String translateLangHolders(Player player, String langMessage, String... langHolder) {
        return langMessage
                .replace("%player%", langHolder[0])
                .replace("%targetplayer%", langHolder[1])
                .replace("%plugin%", langHolder[2])
                .replace("%prefix%", langHolder[3])
                .replace("%command%", langHolder[4])
                .replace("%group%", langHolder[5])
                .replace("%amount%", langHolder[6])
                .replace("%material%", getVanillaTrans(player, langHolder[7], "material"))
                .replace("%entity%", getVanillaTrans(player, langHolder[8], "entity"))
                .replace("%pricetype%", langHolder[9])
                .replace("%price%", langHolder[10])
                .replace("%balance%", langHolder[11])
                .replace("%distance%", langHolder[12])
                .replace("%flag%", langHolder[13])
                .replace("%length%", langHolder[14])
                .replace("%color%", langHolder[15])
                .replace("%material_display_name%", langHolder[17])
                .replace("%entity_display_name%", langHolder[18])
                .replace("%nick%", langHolder[21])
                ;
    }

    @Override
    public String getTranslation(String input) {
        return ConfigHandler.getConfig("config.yml").getString("Message.Translation." + input);
    }

    @Override
    public String getVanillaTrans(Player player, String input, String type) {
        UtilsHandler.getLang().sendConsoleMsg("", input);
        UtilsHandler.getLang().sendConsoleMsg("", type);
        String test = ConfigHandler.getVanillaUtils().getValinaName(player, input, type);
        System.out.println(test);
        return test;
    }

    @Override
    public String getVanillaTrans(String input, String type) {
        UtilsHandler.getLang().sendConsoleMsg("", input);
        UtilsHandler.getLang().sendConsoleMsg("", type);
        String test = ConfigHandler.getVanillaUtils().getValinaNode(null, input, type);
        System.out.println(test);
        return test;
    }

    @Override
    public void addLog(File file, String message, boolean time, boolean newFile, boolean zip) {
        UtilsHandler.getLogger().addLog(file, message, true, newFile, zip);
    }
}