package tw.momocraft.coreplus.utils.language;

import me.clip.placeholderapi.PlaceholderAPI;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import tw.momocraft.coreplus.CorePlus;
import tw.momocraft.coreplus.api.LanguageInterface;
import tw.momocraft.coreplus.handlers.ConfigHandler;
import tw.momocraft.coreplus.handlers.UtilsHandler;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.List;

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
    public void sendErrorMsg(String prefix, String message) {
        message = "&c[" + prefix + "_Error] &r" + message;
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
    public void sendActionBarMsg(String prefix, Player player, String message) {
        if (prefix == null)
            prefix = "";
        message = prefix + message;
        message = ChatColor.translateAlternateColorCodes('&', message);
        player.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(message));
    }

    // title: "title/nsubtitle"
    @Override
    public void sendTitleMsg(Player player, String input) {
        input = ChatColor.translateAlternateColorCodes('&', input);
        String[] args = input.split("/n");
        player.sendTitle(args[0], args[1], 10, 70, 20);
    }

    @Override
    public void sendTitleMsg(Player player, String title, String subtitle) {
        title = ChatColor.translateAlternateColorCodes('&', title);
        subtitle = ChatColor.translateAlternateColorCodes('&', subtitle);
        player.sendTitle(title, subtitle, 10, 70, 20);
    }

    @Override
    public void sendTitleMsg(Player player, String title, String subtitle, int fadeIn, int stay, int fadeOut) {
        title = ChatColor.translateAlternateColorCodes('&', title);
        subtitle = ChatColor.translateAlternateColorCodes('&', subtitle);
        player.sendTitle(title, subtitle, fadeIn, stay, fadeOut);
    }

    @Override
    public void sendDebugMsg(boolean debugging, String prefix, String message) {
        if (debugging) {
            message = prefix + "&7[Debug]&r " + message;
            message = ChatColor.translateAlternateColorCodes('&', message);
            CorePlus.getInstance().getServer().getConsoleSender().sendMessage(message);
        }
    }

    @Override
    public void sendDebugTrace(boolean debugging, String prefix, Exception e) {
        if (debugging) {
            prefix = ChatColor.translateAlternateColorCodes('&', prefix);
            sendErrorMsg(prefix, "showing debug trace.");
            e.printStackTrace();
        }
    }

    @Override
    public void sendFeatureMsg(boolean debugging, String prefix, String feature, String target, String check, String action, String detail, StackTraceElement ste) {
        if (!debugging) {
            return;
        }
        switch (action) {
            case "cancel":
            case "remove":
            case "kill":
            case "damage":
            case "fail":
            case "warning":
                sendDebugMsg(true, prefix, "&f" + feature + "&8 - &f" + target + "&8 : &f" + check + "&8, &c" + action + "&8, &7" + detail
                        + " &8(" + ste.getClassName() + " " + ste.getMethodName() + " " + ste.getLineNumber() + ")");
                break;
            case "continue":
            case "bypass":
            case "change":
                sendDebugMsg(true, prefix, "&f" + feature + "&8 - &f" + target + "&8 : &f" + check + "&8, &e" + action + "&8, &7" + detail
                        + " &8(" + ste.getClassName() + " " + ste.getMethodName() + " " + ste.getLineNumber() + ")");
                break;
            case "return":
            default:
                sendDebugMsg(true, prefix, "&f" + feature + "&8 - &f" + target + "&8 : &f" + check + "&8, &a" + action + "&8, &7" + detail
                        + " &8(" + ste.getClassName() + " " + ste.getMethodName() + " " + ste.getLineNumber() + ")");
                break;
        }
    }

    @Override
    public void sendFeatureMsg(boolean debugging, String prefix, String feature, String target, String check, String action, StackTraceElement ste) {
        if (!debugging) {
            return;
        }
        prefix = ChatColor.translateAlternateColorCodes('&', prefix);
        switch (action) {
            case "cancel":
            case "remove":
            case "kill":
            case "damage":
            case "warning":
                sendDebugMsg(true, prefix, "&f" + feature + "&8 - &f" + target + "&8 : &f" + check + "&8, &c" + action
                        + " &8(" + ste.getClassName() + " " + ste.getMethodName() + " " + ste.getLineNumber() + ")");
                break;
            case "continue":
            case "bypass":
            case "change":
                sendDebugMsg(true, prefix, "&f" + feature + "&8 - &f" + target + "&8 : &f" + check + "&8, &e" + action
                        + " &8(" + ste.getClassName() + " " + ste.getMethodName() + " " + ste.getLineNumber() + ")");
                break;
            case "return":
            default:
                sendDebugMsg(true, prefix, "&f" + feature + "&8 - &f" + target + "&8 : &f" + check + "&8, &a" + action
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
        input = translateLayout(input, player);
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
        return new String[30];
    }

    @Override
    public String translateLangHolders(Player player, String langMessage, String... langHolder) {
        if (langHolder.length == 0) {
            return langMessage;
        }
        if (langMessage.contains("%material%")) {
            langMessage = langMessage.replace("%material%", getVanillaTrans(player, langHolder[7], "material"));
        } else if (langMessage.contains("%entity%")) {
            langMessage = langMessage.replace("%entity%", getVanillaTrans(player, langHolder[8], "entity"));
        }
        return langMessage
                .replace("%player%", langHolder[0])
                .replace("%targetplayer%", langHolder[1])
                .replace("%plugin%", langHolder[2])
                .replace("%prefix%", langHolder[3])
                .replace("%command%", langHolder[4])
                .replace("%group%", langHolder[5])
                .replace("%amount%", langHolder[6])
                .replace("%material%", langHolder[7])
                .replace("%entity%", langHolder[8])
                .replace("%pricetype%", getMessageTranslation(langHolder[9]))
                .replace("%price%", langHolder[10])
                .replace("%balance%", langHolder[11])
                .replace("%distance%", langHolder[12])
                .replace("%flag%", getMessageTranslation(langHolder[13]))
                .replace("%length%", langHolder[14])
                .replace("%size%", langHolder[15])
                .replace("%color%", langHolder[16])
                .replace("%material_display_name%", langHolder[17])
                .replace("%entity_display_name%", langHolder[18])
                .replace("%targets%", langHolder[19])
                .replace("%nick%", langHolder[21])
                .replace("%need%", langHolder[22])
                .replace("%limit%", langHolder[23])
                .replace("%next_limit%", langHolder[24])
                .replace("%new_entity%", langHolder[25])
                .replace("%new_entity_display_name%", langHolder[26])
                ;
    }

    @Override
    public String translateLayout(String input, Player player) {
        if (input == null) {
            return "";
        }
        if (player != null && !(player instanceof ConsoleCommandSender)) {
            String playerName = player.getName();
            // %player%
            try {
                input = input.replace("%player%", playerName);
            } catch (Exception e) {
                UtilsHandler.getLang().sendDebugTrace(ConfigHandler.isDebugging(), ConfigHandler.getPlugin(), e);
            }
            // %player_display_name%
            try {
                input = input.replace("%player_display_name%", player.getDisplayName());
            } catch (Exception e) {
                UtilsHandler.getLang().sendDebugTrace(ConfigHandler.isDebugging(), ConfigHandler.getPlugin(), e);
            }
            UUID playerUUID = player.getUniqueId();
            // %player_uuid%
            try {
                input = input.replace("%player_uuid%", playerUUID.toString());
            } catch (Exception e) {
                UtilsHandler.getLang().sendDebugTrace(ConfigHandler.isDebugging(), ConfigHandler.getPlugin(), e);
            }
            // %player_sneaking%
            try {
                input = input.replace("%player_sneaking%", String.valueOf(player.isSneaking()));
            } catch (Exception e) {
                UtilsHandler.getLang().sendDebugTrace(ConfigHandler.isDebugging(), ConfigHandler.getPlugin(), e);
            }
            // %player_flying%
            try {
                input = input.replace("%player_flying%", String.valueOf(player.isFlying()));
            } catch (Exception e) {
                UtilsHandler.getLang().sendDebugTrace(ConfigHandler.isDebugging(), ConfigHandler.getPlugin(), e);
            }
            Location loc = player.getLocation();
            // %player_world%
            if (input.contains("%player_world%")) {
                input = input.replace("%player_world%", loc.getWorld().getName());
            }
            // %player_loc%
            // %player_loc_x%, %player_loc_y%, %player_loc_z%
            // %player_loc_x_NUMBER%, %player_loc_y_NUMBER%, %player_loc_z_NUMBER%
            if (input.contains("%player_loc")) {
                try {
                    String loc_x = String.valueOf(loc.getBlockX());
                    String loc_y = String.valueOf(loc.getBlockY());
                    String loc_z = String.valueOf(loc.getBlockZ());
                    String[] arr = input.split("%");
                    for (int i = 0; i < arr.length; i++) {
                        switch (arr[i]) {
                            case "player_loc_x":
                                if (arr[i + 1].matches("^-?[0-9]\\d*(\\.\\d+)?$")) {
                                    input = input.replace("%player_loc_x%" + arr[i + 1] + "%", loc_x + Integer.parseInt(arr[i + 1]));
                                }
                                break;
                            case "player_loc_y":
                                if (arr[i + 1].matches("^-?[0-9]\\d*(\\.\\d+)?$")) {
                                    input = input.replace("%player_loc_y%" + arr[i + 1] + "%", loc_y + Integer.parseInt(arr[i + 1]));
                                }
                                break;
                            case "player_loc_z":
                                if (arr[i + 1].matches("^-?[0-9]\\d*(\\.\\d+)?$")) {
                                    input = input.replace("%player_loc_z%" + arr[i + 1] + "%", loc_z + Integer.parseInt(arr[i + 1]));
                                }
                                break;
                        }
                    }
                    input = input.replace("%player_loc%", loc_x + ", " + loc_y + ", " + loc_z);
                    input = input.replace("%player_loc_x%", loc_x);
                    input = input.replace("%player_loc_y%", loc_y);
                    input = input.replace("%player_loc_z%", loc_z);
                } catch (Exception e) {
                    UtilsHandler.getLang().sendDebugTrace(ConfigHandler.isDebugging(), ConfigHandler.getPlugin(), e);
                }
            }
            if (UtilsHandler.getDepend().VaultEnabled()) {
                if (input.contains("%money%")) {
                    input = input.replace("%money%", String.valueOf(UtilsHandler.getDepend().getVaultApi().getBalance(playerUUID)));
                }
            }
            if (UtilsHandler.getDepend().PlayerPointsEnabled()) {
                if (input.contains("%points%")) {
                    input = input.replace("%points%", String.valueOf(UtilsHandler.getDepend().getPlayerPointsApi().getBalance(playerUUID)));
                }
            }
        }
        // %player% => CONSOLE
        if (player == null) {
            try {
                input = input.replace("%player%", "CONSOLE");
            } catch (Exception e) {
                UtilsHandler.getLang().sendDebugTrace(ConfigHandler.isDebugging(), ConfigHandler.getPlugin(), e);
            }
        }
        // %server_name%
        try {
            input = input.replace("%server_name%", Bukkit.getServer().getName());
        } catch (Exception e) {
            UtilsHandler.getLang().sendDebugTrace(ConfigHandler.isDebugging(), ConfigHandler.getPlugin(), e);
        }
        // %localtime_time% => 2020/08/08 12:30:00
        try {
            input = input.replace("%localtime_time%", new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(new Date()));
        } catch (Exception e) {
            UtilsHandler.getLang().sendDebugTrace(ConfigHandler.isDebugging(), ConfigHandler.getPlugin(), e);
        }
        // %random_number%500%
        if (input.contains("%random_number%")) {
            try {
                String[] arr = input.split("%");
                for (int i = 0; i < arr.length; i++) {
                    if (arr[i].equals("random_number") && arr[i + 1].matches("^[0-9]*$")) {
                        input = input.replace("%random_number%" + arr[i + 1] + "%", String.valueOf(new Random().nextInt(Integer.parseInt(arr[i + 1]))));
                    }
                }
            } catch (Exception e) {
                UtilsHandler.getLang().sendDebugTrace(ConfigHandler.isDebugging(), ConfigHandler.getPlugin(), e);
            }
        }
        // %random_player%
        if (input.contains("%random_player%")) {
            try {
                List<Player> playerList = new ArrayList(Bukkit.getOnlinePlayers());
                String randomPlayer = playerList.get(new Random().nextInt(playerList.size())).getName();
                input = input.replace("%random_player%", randomPlayer);
            } catch (Exception e) {
                UtilsHandler.getLang().sendDebugTrace(ConfigHandler.isDebugging(), ConfigHandler.getPlugin(), e);
            }
        }
        // %random_player_except%AllBye,huangge0513%
        if (input.contains("%random_player_except%")) {
            List<String> placeholderList = new ArrayList<>();
            List<Player> playerList = new ArrayList(Bukkit.getOnlinePlayers());
            String[] arr = input.split("%");
            for (int i = 0; i < arr.length; i++) {
                if (arr[i].equals("random_player_except")) {
                    placeholderList.add((arr[i + 1]));
                }
            }
            String[] playerArr;
            Player randomPlayer;
            String randomPlayerName;
            for (String exceptPlayer : placeholderList) {
                playerArr = exceptPlayer.split(",");
                while (true) {
                    if (playerList.isEmpty()) {
                        input = input.replace("%random_player_except%" + exceptPlayer + "%", "");
                        break;
                    }
                    randomPlayer = playerList.get(new Random().nextInt(playerList.size()));
                    randomPlayerName = randomPlayer.getName();
                    playerList.remove(randomPlayer);
                    try {
                        if (!Arrays.asList(playerArr).contains(randomPlayerName)) {
                            String newList = placeholderList.toString().replaceAll("[\\[\\]\\s]", "");
                            input = input.replace("%random_player_except%" + newList + "%", randomPlayerName);
                            break;
                        }
                    } catch (Exception e) {
                        UtilsHandler.getLang().sendDebugTrace(ConfigHandler.isDebugging(), ConfigHandler.getPlugin(), e);
                    }
                }
            }
        }
        // Translate color codes.
        input = ChatColor.translateAlternateColorCodes('&', input);
        // Translate PlaceHolderAPI's placeholders.
        if (UtilsHandler.getDepend().PlaceHolderAPIEnabled()) {
            try {
                return PlaceholderAPI.setPlaceholders(player, input);
            } catch (NoSuchFieldError e) {
                UtilsHandler.getLang().sendDebugMsg(ConfigHandler.isDebugging(), ConfigHandler.getPrefix(), "Error has occurred when setting the PlaceHolder " + e.getMessage() + ", if this issue persist contact the developer of PlaceholderAPI.");
                return input;
            }
        }
        return input;
    }

    @Override
    public String getMessageTranslation(String input) {
        return ConfigHandler.getConfig("config.yml").getString("Message.Translation." + input, input);
    }

    @Override
    public String getVanillaTrans(Player player, String input, String type) {
        return UtilsHandler.getVanillaUtils().getValinaName(player, input, type);
    }

    @Override
    public String getVanillaTrans(String input, String type) {
        return UtilsHandler.getVanillaUtils().getValinaNode(null, input, type);
    }

    @Override
    public void addLog(File file, String message, boolean time, boolean newFile, boolean zip) {
        UtilsHandler.getLogger().addLog(file, message, time, newFile, zip);
    }

    @Override
    public String getPlayersString(List<Player> players) {
        StringBuilder stringBuilder = new StringBuilder();
        Player player;
        int size = players.size();
        for (int i = 0; i < size; i++) {
            player = players.get(i);
            stringBuilder.append(player.getName());
            if (i != size - 1) {
                stringBuilder.append(player.getName()).append(", ");
            }
        }
        if (stringBuilder.toString().equals("")) {
            return getMessageTranslation("noTarget");
        }
        return stringBuilder.toString();
    }

    @Override
    public String getBlocksString(List<Block> blocks) {
        StringBuilder stringBuilder = new StringBuilder();
        Block block;
        int size = blocks.size();
        for (int i = 0; i < size; i++) {
            block = blocks.get(i);
            stringBuilder.append(block.getType().name());
            if (i != size - 1) {
                stringBuilder.append(block.getType().name()).append(", ");
            }
        }
        if (stringBuilder.toString().equals("")) {
            return getMessageTranslation("noTarget");
        }
        return stringBuilder.toString();
    }

    @Override
    public String getEntitiesString(List<Entity> entities) {
        StringBuilder stringBuilder = new StringBuilder();
        Entity entity;
        int size = entities.size();
        for (int i = 0; i < size; i++) {
            entity = entities.get(i);
            stringBuilder.append(entity.getType().name());
            if (i != size - 1) {
                stringBuilder.append(entity.getType().name()).append(", ");
            }
        }
        if (stringBuilder.toString().equals("")) {
            return getMessageTranslation("noTarget");
        }
        return stringBuilder.toString();
    }
}