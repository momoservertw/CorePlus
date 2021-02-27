package tw.momocraft.coreplus.utils.language;

import me.clip.placeholderapi.PlaceholderAPI;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import tw.momocraft.coreplus.CorePlus;
import tw.momocraft.coreplus.api.LanguageInterface;
import tw.momocraft.coreplus.handlers.ConfigHandler;
import tw.momocraft.coreplus.handlers.UtilsHandler;

import javax.annotation.Nullable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.*;

public class LanguageUtils implements LanguageInterface {

    private String addPrefix(String prefix, String input) {
        if (prefix == null)
            prefix = "";
        input = prefix + input;
        return ChatColor.translateAlternateColorCodes('&', input);
    }

    @Override
    public void sendChatMsg(String prefix, Player player, String message) {
        message = addPrefix(prefix, message);
        player.chat(message);
    }

    @Override
    public void sendBroadcastMsg(String prefix, String message) {
        message = addPrefix(prefix, message);
        Bukkit.broadcastMessage(message);
    }

    @Override
    public void sendConsoleMsg(String prefix, String message) {
        message = addPrefix(prefix, message);
        CorePlus.getInstance().getServer().getConsoleSender().sendMessage(message);
    }

    @Override
    public void sendPlayerMsg(String prefix, Player player, String message) {
        message = addPrefix(prefix, message);
        player.sendMessage(message);
    }

    @Override
    public void sendMsg(String prefix, CommandSender sender, String message) {
        message = addPrefix(prefix, message);
        sender.sendMessage(message);
    }

    @Override
    public void sendActionBarMsg(Player player, String message) {
        message = ChatColor.translateAlternateColorCodes('&', message);
        player.sendActionBar(TextComponent.fromLegacyText(message));
    }

    @Override
    public void sendTitleMsg(Player player, String message) {
        message = ChatColor.translateAlternateColorCodes('&', message);
        String[] args = message.split("/n");
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
    public void sendErrorMsg(String pluginName, String message) {
        message = "&4[" + pluginName + "_Error] &e" + message;
        message = ChatColor.translateAlternateColorCodes('&', message);
        CorePlus.getInstance().getServer().getConsoleSender().sendMessage(message);
    }

    //////////////////
    @Override
    public void sendDebugMsg(boolean isDebugging, String pluginName, String message) {
        if (isDebugging) {
            message = "&7[" + pluginName + "_Debug]&r " + message;
            message = ChatColor.translateAlternateColorCodes('&', message);
            CorePlus.getInstance().getServer().getConsoleSender().sendMessage(message);
        }
    }

    @Override
    public void sendDebugTrace(boolean isDebugging, String pluginName, Exception e) {
        if (isDebugging) {
            sendErrorMsg(pluginName, "showing debug trace.");
            e.printStackTrace();
        }
    }

    @Override
    public void sendFeatureMsg(boolean isDebugging, String pluginName, String feature, String target, String check, String action, String detail, StackTraceElement ste) {
        if (!isDebugging) {
            return;
        }
        switch (action) {
            case "cancel":
            case "remove":
            case "kill":
            case "damage":
            case "fail":
            case "warning":
                action = "&c" + action;
                break;
            case "continue":
            case "bypass":
            case "change":
                action = "&e" + action;
                break;
            case "return":
            case "success":
                action = "&a" + action;
            default:
                break;
        }
        sendDebugMsg(true, pluginName,
                "&f" + feature + "&8 - &f" + target + "&8 : &f" + check + "&8, &f" + action + "&8, &7" + detail
                        + " &8(" + ste.getClassName() + " " + ste.getMethodName() + " " + ste.getLineNumber() + ")");
    }

    @Override
    public void sendFeatureMsg(boolean debugging, String pluign, String feature, String target, String check, String action, StackTraceElement ste) {
        if (!debugging) {
            return;
        }
        switch (action) {
            case "cancel":
            case "remove":
            case "kill":
            case "damage":
            case "warning":
                action = "&c" + action;
                break;
            case "continue":
            case "bypass":
            case "change":
                action = "&e" + action;
                break;
            case "return":
            case "success":
                action = "&a" + action;
            default:
                break;
        }
        sendDebugMsg(true, pluign, "&f" + feature + "&8 - &f" + target + "&8 : &f" + check + "&8, &f" + action
                + " &8(" + ste.getClassName() + " " + ste.getMethodName() + " " + ste.getLineNumber() + ")");
    }

    @Override
    public void sendLangMsg(String pluginName, String prefix, String input, CommandSender sender, String... langHolder) {
        if (input == null || input.equals("")) {
            return;
        }
        Player player = null;
        if (sender instanceof Player) {
            player = (Player) sender;
        }
        String langMessage = ConfigHandler.getConfig("config.yml").getString(input);
        if (langMessage != null && !langMessage.isEmpty())
            input = langMessage;
        input = input.replace("%prefix%", prefix != null ? prefix : "");
        input = transByPlayer(pluginName, UtilsHandler.getVanillaUtils().getLocal(player), input, player, "player");
        input = transLangHolders(pluginName, null, input, langHolder);
        String[] langLines = input.split("\\n");
        for (String langLine : langLines) {
            sender.sendMessage(langLine);
        }
    }

    @Override
    public void sendDiscordMsg(String pluginName, String input, boolean placeholder, String... langHolder) {
        input = UtilsHandler.getLang().transLangHolders(pluginName, null, input, langHolder);
        if (placeholder) {
            input = transByGeneral(pluginName, null, input);
        }
        int index = input.indexOf(", ");
        UtilsHandler.getDiscord().sendDiscordMsg(input.substring(0, index - 1), input.substring(index + 1));
    }

    @Override
    public void sendDiscordMsg(String pluginName, Player player, String input, boolean placeholder, String... langHolder) {
        input = transLangHolders(pluginName, UtilsHandler.getVanillaUtils().getLocal(player), input, langHolder);
        if (placeholder) {
            input = transByPlayer(pluginName, UtilsHandler.getVanillaUtils().getLocal(player), input, player, "player");
        }
        input = transByPlayer(pluginName, UtilsHandler.getVanillaUtils().getLocal(player), input, player, "player");
        String message = UtilsHandler.getYaml().getConfig("discord_messages").getString("MinecraftChatToDiscordMessageFormat");
        try {
            message = message.replace("%message%", input);
            message = UtilsHandler.getLang().transLangHolders(pluginName, null, message, langHolder);
            message = UtilsHandler.getLang().transByPlayer(pluginName, UtilsHandler.getVanillaUtils().getLocal(player),
                    message, player, "player");
        } catch (Exception ex) {
            message = input;
        }
        int index = message.indexOf(", ");
        UtilsHandler.getDiscord().sendDiscordMsg(message.substring(0, index - 1), message.substring(index + 1));
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
    public String transLangHolders(String pluginName, @Nullable String local, String input, String... langHolder) {
        langHolder = initializeRows(langHolder);
        if (langHolder.length == 0)
            return input;
        if (input.contains("%material%")) {
            input = input.replace("%material%", getVanillaTrans(pluginName, local, langHolder[7], "material"));
        } else if (input.contains("%entity%")) {
            input = input.replace("%entity%", getVanillaTrans(pluginName, local, langHolder[8], "entity"));
        }
        return input
                .replace("%player%", langHolder[0])
                .replace("%target_player%", langHolder[1])
                .replace("%plugin%", langHolder[2])
                .replace("%prefix%", langHolder[3])
                .replace("%command%", langHolder[4])
                .replace("%group%", langHolder[5])
                .replace("%amount%", langHolder[6])
                .replace("%material%", langHolder[7])
                .replace("%entity%", langHolder[8])
                .replace("%price_type%", getMsgTrans(langHolder[9]))
                .replace("%price%", langHolder[10])
                .replace("%balance%", langHolder[11])
                .replace("%distance%", langHolder[12])
                .replace("%flag%", getMsgTrans(langHolder[13]))
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
    public String transByPlayerName(String pluginName, String local, String input, String playerName) {
        Player player = Bukkit.getPlayer(playerName);
        if (player != null)
            return transByPlayer(pluginName, local, input, player, "player");
        OfflinePlayer offlinePlayer = UtilsHandler.getPlayer().getOfflinePlayer(playerName);
        if (offlinePlayer != null)
            return transByOfflinePlayer(pluginName, local, input, offlinePlayer, "player");
        return transByGeneral(pluginName, local, input);
    }

    @Override
    public String transByPlayerUUID(String pluginName, String local, String input, UUID uuid) {
        Player player = Bukkit.getPlayer(uuid);
        if (player != null)
            return transByPlayer(pluginName, local, input, player, "player");
        OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(uuid);
        return transByOfflinePlayer(pluginName, local, input, offlinePlayer, "player");
    }

    @Override
    public String transByPlayer(String pluginName, String local, String input, Player player, String target) {
        if (input == null)
            return "";
        if (player == null) {
            // %TARGET_has_played%
            input = input.replace("%" + target + "_has_played%", "false");
            return transByGeneral(pluginName, local, input);
        } else if (player instanceof ConsoleCommandSender) {
            return transByGeneral(pluginName, local, input);
        }
        while (true) {
            // %TARGET_display_name%
            try {
                input = input.replace("%" + target + "_display_name%", player.getDisplayName());
            } catch (Exception ex) {
                UtilsHandler.getLang().sendDebugTrace(ConfigHandler.isDebugging(), ConfigHandler.getPluginName(), ex);
            }
            // %TARGET_last_login%
            input = input.replace("%" + target + "_last_login%", String.valueOf(player.getLastLogin()));
            // %money%
            UUID uuid = player.getUniqueId();
            if (UtilsHandler.getDepend().VaultEnabled()) {
                if (input.contains("%money%")) {
                    input = input.replace("%money%", String.valueOf(UtilsHandler.getDepend().getVaultApi().getBalance(uuid)));
                }
            }
            // %points%
            if (UtilsHandler.getDepend().PlayerPointsEnabled()) {
                if (input.contains("%points%")) {
                    input = input.replace("%points%", String.valueOf(UtilsHandler.getDepend().getPlayerPointsApi().getBalance(uuid)));
                }
            }
            // %TARGET_sneaking%
            input = input.replace("%" + target + "_sneaking%", String.valueOf(player.isSneaking()));
            // %TARGET_flying%
            input = input.replace("%" + target + "_flying%", String.valueOf(player.isFlying()));
            // Translate PlaceHolderAPI placeholders.
            if (UtilsHandler.getDepend().PlaceHolderAPIEnabled()) {
                try {
                    input = PlaceholderAPI.setPlaceholders(player, input);
                } catch (NoSuchFieldError e) {
                    UtilsHandler.getLang().sendDebugMsg(ConfigHandler.isDebugging(), ConfigHandler.getPrefix(),
                            "Error has occurred when setting the PlaceHolder " + e.getMessage() +
                                    ", if this issue persist contact the developer of PlaceholderAPI.");
                }
            }
            // Entity
            input = transByEntity(pluginName, local, input, player, target, true);
            // General
            input = transByGeneral(pluginName, local, input);
            // PlaceHolderAPI
            input = transLayoutPAPI(pluginName, input, player);
            // Custom
            String originInput = input;
            input = transByCustom(pluginName, local, input);
            if (input.equals(originInput)) {
                break;
            }
        }
        return input;
    }

    @Override
    public String transByEntity(String pluginName, String local, String input, Entity entity, String target, boolean isTransGeneral) {
        if (input == null)
            return "";
        if (entity == null)
            return transByGeneral(pluginName, local, input);
        while (true) {
            String displayName = entity.getCustomName();
            String type = entity.getType().name();
            // %TARGET%
            input = input.replace("%" + target + "%", displayName != null ? displayName : type);
            // %TARGET_display_name%
            input = input.replace("%" + target + "_display_name%", displayName != null ? displayName : type);
            // %TARGET_has_custom_name%
            input = input.replace("%" + target + "_has_custom_name%", displayName != null ? "true" : "false");
            // %TARGET_type%
            input = input.replace("%" + target + "_type%", type);
            // %TARGET_type_local%
            input = input.replace("%" + target + "_type_local%", getVanillaTrans(pluginName, local, type, "entity"));
            // %TARGET_uuid%
            UUID uuid = entity.getUniqueId();
            input = input.replace("%" + target + "_uuid%", uuid.toString());
            // Location
            input = transByLocation(pluginName, local, input, entity.getLocation(), target, true);
            // General
            if (!isTransGeneral)
                input = transByGeneral(pluginName, local, input);
            // Custom
            String originInput = input;
            input = transByCustom(pluginName, local, input);
            if (input.equals(originInput)) {
                break;
            }
        }
        return input;
    }

    public String transByOfflinePlayer(String pluginName, String local, String input, OfflinePlayer offlinePlayer, String target) {
        if (input == null)
            return "";
        if (offlinePlayer == null) {
            // %TARGET_has_played%
            input = input.replace("%" + target + "_has_played%", "false");
            return transByGeneral(pluginName, local, input);
        }
        while (true) {
            // %TARGET%
            try {
                input = input.replace("%" + target + "%", offlinePlayer.getName());
            } catch (Exception ex) {
                input = input.replace("%" + target + "%", getMsgTrans("noTarget"));
            }
            // %TARGET_uuid%
            try {
                input = input.replace("%" + target + "_uuid%", offlinePlayer.getUniqueId().toString());
            } catch (Exception ex) {
                UtilsHandler.getLang().sendDebugTrace(ConfigHandler.isDebugging(), ConfigHandler.getPluginName(), ex);
            }
            // %TARGET_last_login%
            boolean hasPlayed = offlinePlayer.hasPlayedBefore();
            try {
                input = input.replace("%" + target + "_last_login%",
                        hasPlayed ? String.valueOf(offlinePlayer.getLastLogin()) : getMsgTrans("noData"));
            } catch (Exception ex) {
                UtilsHandler.getLang().sendDebugTrace(ConfigHandler.isDebugging(), ConfigHandler.getPluginName(), ex);
            }
            // %TARGET_has_played%
            try {
                input = input.replace("%" + target + "_has_played%", String.valueOf(hasPlayed));
            } catch (Exception ex) {
                UtilsHandler.getLang().sendDebugTrace(ConfigHandler.isDebugging(), ConfigHandler.getPluginName(), ex);
            }
            // Translate PlaceHolderAPI's placeholders.
            if (UtilsHandler.getDepend().PlaceHolderAPIEnabled()) {
                try {
                    input = PlaceholderAPI.setPlaceholders(offlinePlayer, input);
                } catch (NoSuchFieldError e) {
                    UtilsHandler.getLang().sendDebugMsg(ConfigHandler.isDebugging(), ConfigHandler.getPrefix(),
                            "Error has occurred when setting the PlaceHolder " + e.getMessage() +
                                    ", if this issue persist contact the developer of PlaceholderAPI.");
                }
            }
            // General
            input = transByGeneral(pluginName, local, input);
            // Custom
            String originInput = input;
            input = transByCustom(pluginName, local, input);
            if (input.equals(originInput)) {
                break;
            }
        }
        return input;
    }

    public String transByBlock(String pluginName, String local, String input, Block block, String target) {
        if (input == null)
            return "";
        if (block == null)
            return transByGeneral(pluginName, local, input);
        while (true) {
            // Material
            input = transByMaterial(pluginName, local, input, block.getType(), target, true);
            // Location
            input = transByLocation(pluginName, local, input, block.getLocation(), target, true);
            // General
            input = transByGeneral(pluginName, local, input);
            // Custom
            String originInput = input;
            input = transByCustom(pluginName, local, input);
            if (input.equals(originInput)) {
                break;
            }
        }
        return input;
    }

    public String transByItem(String pluginName, String local, String input, ItemStack itemStack, String target) {
        if (input == null)
            return "";
        if (itemStack == null)
            return transByGeneral(pluginName, local, input);
        while (true) {
            // %TARGET_display_name%
            ItemMeta itemMeta = itemStack.getItemMeta();
            String displayName = null;
            if (itemMeta != null)
                displayName = itemStack.getItemMeta().getDisplayName();
            try {
                input = input.replace("%" + target + "_display_name%", displayName != null ? displayName : itemStack.getType().name());
            } catch (Exception ex) {
                UtilsHandler.getLang().sendDebugTrace(ConfigHandler.isDebugging(), ConfigHandler.getPluginName(), ex);
            }
            // %TARGET_has_custom_name%
            try {
                input = input.replace("%" + target + "_display_name%", displayName != null ? "true" : "false");
            } catch (Exception ex) {
                UtilsHandler.getLang().sendDebugTrace(ConfigHandler.isDebugging(), ConfigHandler.getPluginName(), ex);
            }
            // %TARGET_amount%
            try {
                input = input.replace("%" + target + "_amount%", String.valueOf(itemStack.getAmount()));
            } catch (Exception ex) {
                UtilsHandler.getLang().sendDebugTrace(ConfigHandler.isDebugging(), ConfigHandler.getPluginName(), ex);
            }
            // Material
            input = transByMaterial(pluginName, local, input, itemStack.getType(), target, true);
            // General
            input = transByGeneral(pluginName, local, input);
            // Custom
            String originInput = input;
            input = transByCustom(pluginName, local, input);
            if (input.equals(originInput)) {
                break;
            }
        }
        return input;
    }

    @Override
    public String transByMaterial(String pluginName, String local, String input, Material material, String target, boolean isTransGeneral) {
        if (input == null)
            return "";
        if (material == null)
            return transByGeneral(pluginName, local, input);
        while (true) {
            // %TARGET%
            String type = material.name();
            try {
                input = input.replace("%" + target + "%", type);
            } catch (Exception ex) {
                UtilsHandler.getLang().sendDebugTrace(ConfigHandler.isDebugging(), ConfigHandler.getPluginName(), ex);
            }
            // %TARGET_type%
            try {
                input = input.replace("%" + target + "_type%", type);
            } catch (Exception ex) {
                UtilsHandler.getLang().sendDebugTrace(ConfigHandler.isDebugging(), ConfigHandler.getPluginName(), ex);
            }
            // %TARGET_type_local%
            try {
                input = input.replace("%" + target + "_type_local%", getVanillaTrans(pluginName, type, "material"));
            } catch (Exception ex) {
                UtilsHandler.getLang().sendDebugTrace(ConfigHandler.isDebugging(), ConfigHandler.getPluginName(), ex);
            }
            // General
            if (!isTransGeneral)
                input = transByGeneral(pluginName, local, input);
            // Custom
            String originInput = input;
            input = transByCustom(pluginName, local, input);
            if (input.equals(originInput)) {
                break;
            }
        }
        return input;
    }

    @Override
    public String transByLocation(String pluginName, String local, String input, Location loc, String target, boolean isTransGeneral) {
        World world = loc.getWorld();
        Block block = loc.getBlock();
        while (true) {
            // %TARGET_world%
            if (input.contains("%" + target + "_world%"))
                input = input.replace("%" + target + "_world%", world.getName());
            // %TARGET_loc%
            // %TARGET_loc_x%, %TARGET_loc_y%, %TARGET_loc_z%
            // %TARGET_loc_x_NUMBER%, %TARGET_loc_y_NUMBER%, %TARGET_loc_z_NUMBER%
            if (input.contains("%" + target + "_loc")) {
                try {
                    String loc_x = String.valueOf(loc.getBlockX());
                    String loc_y = String.valueOf(loc.getBlockY());
                    String loc_z = String.valueOf(loc.getBlockZ());
                    String[] arr = input.split("%");
                    for (int i = 0; i < arr.length; i++) {
                        if (arr[i].endsWith("_loc_x")) {
                            if (arr[i + 1].matches("^-?[0-9]\\d*(\\.\\d+)?$")) {
                                input = input.replace("%" + arr[i] + "%" + arr[i + 1] + "%", loc_x + Integer.parseInt(arr[i + 1]));
                            }
                        } else if (arr[i].endsWith("_loc_y")) {
                            if (arr[i + 1].matches("^-?[0-9]\\d*(\\.\\d+)?$")) {
                                input = input.replace("%" + arr[i] + "%" + arr[i + 1] + "%", loc_y + Integer.parseInt(arr[i + 1]));
                            }
                        } else if (arr[i].endsWith("_loc_z")) {
                            if (arr[i + 1].matches("^-?[0-9]\\d*(\\.\\d+)?$")) {
                                input = input.replace("%" + arr[i] + "%" + arr[i + 1] + "%", loc_z + Integer.parseInt(arr[i + 1]));
                            }
                        }
                    }
                    input = input.replace("%" + target + "_loc%", loc_x + ", " + loc_y + ", " + loc_z);
                    input = input.replace("%" + target + "_loc_x%", loc_x);
                    input = input.replace("%" + target + "_loc_y%", loc_y);
                    input = input.replace("%" + target + "_loc_z%", loc_z);
                } catch (Exception ex) {
                    UtilsHandler.getLang().sendDebugTrace(ConfigHandler.isDebugging(), ConfigHandler.getPluginName(), ex);
                }
            }
            // %TARGET_cave%
            if (input.contains("%" + target + "_cave%")) {
                Location blockLoc;
                back:
                for (int x = -3; x <= 3; x++) {
                    for (int z = -3; z <= 3; z++) {
                        for (int y = -3; y <= 3; y++) {
                            blockLoc = loc.clone().add(x, y, z);
                            if (Material.CAVE_AIR.equals(blockLoc.getBlock().getType())) {
                                input = input.replace("%" + target + "_cave%", "true");
                                continue back;
                            }
                        }
                    }
                }
            }
            // %TARGET_loc_material%
            if (input.contains("%" + target + "_loc_material%")) {
                input = input.replace("%" + target + "_material%", block.getType().name());
            }
            // %TARGET_loc_cave%
            if (input.contains("%" + target + "_loc_cave%")) {
                Location blockLoc;
                back:
                for (int x = -3; x <= 3; x++) {
                    for (int z = -3; z <= 3; z++) {
                        for (int y = -3; y <= 3; y++) {
                            blockLoc = loc.clone().add(x, y, z);
                            if (Material.CAVE_AIR.equals(blockLoc.getBlock().getType())) {
                                input = input.replace("%" + target + "_loc_cave%", "true");
                                continue back;
                            }
                        }
                    }
                }
            }
            // %TARGET_world_time%
            if (input.contains("%" + target + "_world_time%"))
                input = input.replace("%" + target + "_loc_time%", String.valueOf(world.getTime()));

            // %TARGET_biomed%
            if (input.contains("%" + target + "_biome%"))
                input = input.replace("%" + target + "_solid%", String.valueOf(block.isSolid()));

            // %TARGET_light%
            if (input.contains("%" + target + "_light_light%"))
                input = input.replace("%" + target + "_solid%", String.valueOf(block.getLightLevel()));

            // %TARGET_liquid%
            if (input.contains("%" + target + "_liquid%"))
                input = input.replace("%" + target + "_liquid%", String.valueOf(block.isLiquid()));

            // %TARGET_solid%
            if (input.contains("%" + target + "_solid%"))
                input = input.replace("%" + target + "_solid%", String.valueOf(block.isSolid()));

            // %TARGET_cave%
            if (input.contains("%" + target + "_cave%"))
                input = input.replace("%" + target + "_solid%", String.valueOf(UtilsHandler.getCondition().isInCave(loc)));

            // %TARGET_residence%
            if (input.contains("%" + target + "_residence%")) {
                String residenceName = UtilsHandler.getCondition().getResidenceName(loc);
                input = input.replace("%" + target + "_residence%", residenceName != null ? residenceName : "");
            }
            // %TARGET_in_residence%
            if (input.contains("%" + target + "_in_residence%"))
                input = input.replace("%" + target + "_residence%",
                        String.valueOf(UtilsHandler.getCondition().isInResidence(loc)));
            // %TARGET_nearby%type%name/type%group%radius%
            // arr[0]=Target_nearby, arr[1]=Type, arr[2]=Name/Type, arr[3]=Group, arr[4]=Radius
            if (input.contains("%" + target + "_nearby%")) {
                String[] arr = input.split("%");
                String nearbyString;
                for (int i = 0; i < arr.length; i++) {
                    if (arr[i].equals("%" + target + "_nearby%")) {
                        nearbyString = UtilsHandler.getUtil().getStringFromNearbyType(pluginName, loc,
                                arr[i + 1], arr[i + 2], arr[i + 3], Integer.parseInt(arr[i + 4]));
                        input = input.replace("%" + arr[i] + "%" + arr[i + 1] + "%" + arr[i + 2] +
                                "%" + arr[i + 3] + "%" + arr[i + 4] + "%", nearbyString);
                        i += 4;
                    }
                }
            }
            // General
            if (!isTransGeneral)
                input = transByGeneral(pluginName, local, input);
            // Custom
            String originInput = input;
            input = transByCustom(pluginName, local, input);
            if (input.equals(originInput)) {
                break;
            }
        }
        return input;
    }

    @Override
    public String transByGeneral(String pluginName, String local, String input) {
        while (true) {
            // %player%, %entity%, %block%, %item%, %target%
            try {
                input = input.replace("%player%", getMsgTrans("console"));
                input = input.replace("%entity%", getMsgTrans("console"));
                input = input.replace("%block%", getMsgTrans("console"));
                input = input.replace("%item%", getMsgTrans("console"));
                input = input.replace("%target%", getMsgTrans("console"));
            } catch (Exception ex) {
                UtilsHandler.getLang().sendDebugTrace(ConfigHandler.isDebugging(), ConfigHandler.getPluginName(), ex);
            }
            // %localtime_time% => 2020/08/08 12:30:00
            input = input.replace("%localtime_time%", new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(new Date()));
            // %random_number%500%
            if (input.contains("%random_number%")) {
                try {
                    String[] arr = input.split("%");
                    for (int i = 0; i < arr.length; i++) {
                        if (arr[i].equals("random_number"))
                            input = input.replace("%random_number%" + arr[i + 1] + "%",
                                    String.valueOf(new Random().nextInt(Integer.parseInt(arr[i + 1]))));
                    }
                } catch (Exception ex) {
                    UtilsHandler.getLang().sendErrorMsg(pluginName, "An error occurred while converting placeholder: \"" + input + "\"");
                    UtilsHandler.getLang().sendErrorMsg(pluginName, "Not correct format: \"\"%random_number%NUMBER%\"");
                    UtilsHandler.getLang().sendErrorMsg(pluginName, "More information: https://github.com/momoservertw/CorePlus/wiki/Placeholders");
                    UtilsHandler.getLang().sendDebugTrace(true, pluginName, ex);
                }
            }
            // %random_list%String1,String2%
            if (input.contains("%random_list%")) {
                List<String> placeholderList = new ArrayList<>();
                String[] arr = input.split("%");
                for (int i = 0; i < arr.length; i++) {
                    if (arr[i].equals("random_list"))
                        placeholderList.add((arr[i + 1]));
                }
                String[] stringArr;
                String randomString;
                try {
                    for (String placeholderValue : placeholderList) {
                        stringArr = placeholderValue.split(",");
                        randomString = stringArr[new Random().nextInt(stringArr.length)];
                        input = input.replace("%random_list%" + placeholderValue + "%", randomString);
                    }
                } catch (Exception ex) {
                    UtilsHandler.getLang().sendErrorMsg(pluginName, "An error occurred while converting placeholder: \"" + input + "\"");
                    UtilsHandler.getLang().sendErrorMsg(pluginName, "Not correct format: \"\"%random_list%STRING1,STRING2%\"");
                    UtilsHandler.getLang().sendErrorMsg(pluginName, "More information: https://github.com/momoservertw/CorePlus/wiki/Placeholders");
                    UtilsHandler.getLang().sendDebugTrace(true, pluginName, ex);
                }
            }
            // %random_player%
            if (input.contains("%random_player%")) {
                try {
                    List<Player> playerList = new ArrayList(Bukkit.getOnlinePlayers());
                    String randomPlayer = playerList.get(new Random().nextInt(playerList.size())).getName();
                    input = input.replace("%random_player%", randomPlayer);
                } catch (Exception ex) {
                    input = input.replace("%random_player%", getMsgTrans("noTargets"));
                }
            }
            // %random_player%
        /*
        if (input.contains("%random_player%")) {
            try {
                List<String> playerList = BungeePlayerUtils.getPlayerMap();
                String randomPlayer = playerList.get(new Random().nextInt(playerList.size())).getName();
                input = input.replace("%random_player%", randomPlayer);
            } catch (Exception ex) {
                UtilsHandler.getLang().sendDebugTrace(ConfigHandler.isDebugging(), ConfigHandler.getPluginName(), ex);
            }
        }
         */
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
                        } catch (Exception ex) {
                            UtilsHandler.getLang().sendErrorMsg(pluginName, "An error occurred while converting placeholder: \"" + input + "\"");
                            UtilsHandler.getLang().sendErrorMsg(pluginName, "Not correct format: \"%random_player_except%PLAYERS%\"");
                            UtilsHandler.getLang().sendErrorMsg(pluginName, "More information: https://github.com/momoservertw/CorePlus/wiki/Placeholders");
                            UtilsHandler.getLang().sendDebugTrace(true, ConfigHandler.getPluginName(), ex);
                        }
                    }
                }
            }
            // Color codes
            input = ChatColor.translateAlternateColorCodes('&', input);
            // Others
            input = input.replace("{s}", " ");
            input = input.replace("{e}", "");
            // PlaceHolderAPI
            input = transLayoutPAPI(pluginName, input, null);
            // Custom
            String originInput = input;
            input = transByCustom(pluginName, local, input);
            if (input.equals(originInput)) {
                break;
            }
        }
        return input;
    }

    @Override
    public String transLayoutPAPI(String pluginName, String input, Player player) {
        if (UtilsHandler.getDepend().PlaceHolderAPIEnabled()) {
            try {
                return PlaceholderAPI.setPlaceholders(player, input);
            } catch (NoSuchFieldError e) {
                UtilsHandler.getLang().sendDebugMsg(true, pluginName,
                        "Error has occurred when setting the PlaceHolder " + e.getMessage() +
                                ", if this issue persist contact the developer of PlaceholderAPI.");
            }
        }
        return input;
    }

    @Override
    public String transByCustom(String pluginName, String local, String input) {
        String placeholder;
        String newPlaceholder;
        // JavaScript placeholder.
        ScriptEngineManager mgr = new ScriptEngineManager();
        ScriptEngine engine = mgr.getEngineByName("JavaScript");
        while (input.contains("<js>")) {
            String originInput = input;
            String[] split = input.split("<js>");
            String script;
            String[] varSplit;
            back:
            for (int i = 0; i < split.length; i++) {
                if (i == 0)
                    continue;
                if (split[i].contains("</js>")) {
                    placeholder = split[i].substring(0, split[i].indexOf("</js>"));
                    newPlaceholder = "";
                    if (placeholder.contains("<var>")) {// <var>
                        script = placeholder.substring(0, placeholder.indexOf("<var>"));
                        varSplit = placeholder.substring(placeholder.indexOf("<var>") + 5).split(", ");
                        for (String var : varSplit) {
                            try {
                                engine.eval(script);
                                newPlaceholder += engine.get(var).toString() + ", ";
                            } catch (Exception ex) {
                                input = input.replace("<js>" + placeholder + "</js>", "%ERROR%");
                                UtilsHandler.getLang().sendErrorMsg(pluginName, "An error occurred while converting message: \"" + originInput + "\"");
                                UtilsHandler.getLang().sendErrorMsg(pluginName, "Not correct format of placeholder: \"<js>" + placeholder + "\"");
                                UtilsHandler.getLang().sendErrorMsg(pluginName, "More information: https://github.com/momoservertw/CorePlus/wiki/Placeholders");
                                continue back;
                            }
                        }
                        newPlaceholder = newPlaceholder.substring(0, newPlaceholder.length() - 2);
                        newPlaceholder = newPlaceholder.replace("<var>", "");
                        input = input.replace("<js>" + placeholder + "</js>", newPlaceholder);
                    } else {
                        script = placeholder;
                        try {
                            newPlaceholder = engine.eval(script).toString();
                            input = input.replace("<js>" + placeholder + "</js>", newPlaceholder);
                        } catch (Exception ex) {
                            input = input.replace("<js>" + placeholder + "</js>", "%ERROR%");
                            UtilsHandler.getLang().sendErrorMsg(pluginName, "An error occurred while converting message: \"" + originInput + "\"");
                            UtilsHandler.getLang().sendErrorMsg(pluginName, "Not correct format of placeholder: \"<js>" + placeholder + "\"");
                            UtilsHandler.getLang().sendErrorMsg(pluginName, "More information: https://github.com/momoservertw/CorePlus/wiki/Placeholders");
                        }
                    }
                    continue;
                }
                input = input.replace("<js>" + split[i], "%ERROR%");
                UtilsHandler.getLang().sendErrorMsg(pluginName, "An error occurred while converting message: \"" + originInput + "\"");
                UtilsHandler.getLang().sendErrorMsg(pluginName, "Not correct format of placeholder: \"<js>" + split[i] + "\"");
                UtilsHandler.getLang().sendErrorMsg(pluginName, "More information: https://github.com/momoservertw/CorePlus/wiki/Placeholders");
            }
        }

        // Condition placeholders.
        String condition;
        String action;
        String[] conditionValues;
        boolean type;
        while (input.contains("%if%")) {
            placeholder = input.substring(input.indexOf("$condition: ") - 1);
            placeholder = placeholder.substring(0, placeholder.indexOf("$") + 1);
            condition = placeholder.substring(0, placeholder.lastIndexOf(", ") - 1);
            action = placeholder.substring(placeholder.lastIndexOf(", ") + 1);
            if (condition.contains(">=")) {
                conditionValues = condition.split(">=");
                try {
                    type = UtilsHandler.getUtil().getCompare(">=",
                            Double.parseDouble(conditionValues[0]), Double.parseDouble(conditionValues[1]));
                } catch (Exception ex) {
                    input = input.replace(placeholder, "ERROR_CONDITION_PLACEHOLDER");
                    UtilsHandler.getLang().sendErrorMsg(pluginName, "An error occurred while converting message: \"" + input + "\"");
                    UtilsHandler.getLang().sendErrorMsg(pluginName, "Not correct format of placeholder: \"" + placeholder + "\"");
                    UtilsHandler.getLang().sendErrorMsg(pluginName, "More information: https://github.com/momoservertw/CorePlus/wiki/Placeholders");
                    UtilsHandler.getLang().sendDebugTrace(true, ConfigHandler.getPluginName(), ex);
                    continue;
                }
            } else if (condition.contains("<=")) {
                conditionValues = condition.split("<=");
                try {
                    type = UtilsHandler.getUtil().getCompare("<=",
                            Double.parseDouble(conditionValues[0]), Double.parseDouble(conditionValues[1]));
                } catch (Exception ex) {
                    input = input.replace(placeholder, "ERROR_CONDITION_PLACEHOLDER");
                    UtilsHandler.getLang().sendErrorMsg(pluginName, "An error occurred while converting message: \"" + input + "\"");
                    UtilsHandler.getLang().sendErrorMsg(pluginName, "Not correct format of placeholder: \"" + placeholder + "\"");
                    UtilsHandler.getLang().sendErrorMsg(pluginName, "More information: https://github.com/momoservertw/CorePlus/wiki/Placeholders");
                    UtilsHandler.getLang().sendDebugTrace(true, ConfigHandler.getPluginName(), ex);
                    continue;
                }
            } else if (condition.contains(">")) {
                conditionValues = condition.split(">");
                try {
                    type = UtilsHandler.getUtil().getCompare(">",
                            Double.parseDouble(conditionValues[0]), Double.parseDouble(conditionValues[1]));
                } catch (Exception ex) {
                    input = input.replace(placeholder, "ERROR_CONDITION_PLACEHOLDER");
                    UtilsHandler.getLang().sendErrorMsg(pluginName, "An error occurred while converting message: \"" + input + "\"");
                    UtilsHandler.getLang().sendErrorMsg(pluginName, "Not correct format of placeholder: \"" + placeholder + "\"");
                    UtilsHandler.getLang().sendErrorMsg(pluginName, "More information: https://github.com/momoservertw/CorePlus/wiki/Placeholders");
                    UtilsHandler.getLang().sendDebugTrace(true, ConfigHandler.getPluginName(), ex);
                    continue;
                }
            } else if (condition.contains("<")) {
                conditionValues = condition.split("<");
                try {
                    type = UtilsHandler.getUtil().getCompare("<",
                            Double.parseDouble(conditionValues[0]), Double.parseDouble(conditionValues[1]));
                } catch (Exception ex) {
                    input = input.replace(placeholder, "ERROR_CONDITION_PLACEHOLDER");
                    UtilsHandler.getLang().sendErrorMsg(pluginName, "An error occurred while converting message: \"" + input + "\"");
                    UtilsHandler.getLang().sendErrorMsg(pluginName, "Not correct format of placeholder: \"" + placeholder + "\"");
                    UtilsHandler.getLang().sendErrorMsg(pluginName, "More information: https://github.com/momoservertw/CorePlus/wiki/Placeholders");
                    UtilsHandler.getLang().sendDebugTrace(true, ConfigHandler.getPluginName(), ex);
                    continue;
                }
            } else if (condition.contains("=")) {
                conditionValues = condition.split("=");
                try {
                    type = UtilsHandler.getUtil().getCompare("=",
                            Double.parseDouble(conditionValues[0]), Double.parseDouble(conditionValues[1]));
                } catch (Exception ex) {
                    type = conditionValues[0].equals(conditionValues[1]);
                }
            } else {
                input = input.replace(placeholder, "ERROR_CONDITION_PLACEHOLDER");
                UtilsHandler.getLang().sendErrorMsg(pluginName, "An error occurred while converting message: \"" + input + "\"");
                UtilsHandler.getLang().sendErrorMsg(pluginName, "Not correct format of placeholder: \"" + placeholder + "\"");
                UtilsHandler.getLang().sendErrorMsg(pluginName, "More information: https://github.com/momoservertw/CorePlus/wiki/Placeholders");
                continue;
            }
            String truePlaceholder = null;
            String falsePlaceholder = null;
            if (action.startsWith("{true}")) {
                if (action.contains("{false}")) {
                    truePlaceholder = action.split("\\{false}")[0];
                    truePlaceholder = truePlaceholder.replace("{true}", "");
                    falsePlaceholder = action.split("\\{false}")[1];
                } else {
                    truePlaceholder = action.replace("{true}", "");
                }
            } else if (action.startsWith("{false}")) {
                if (action.contains("{true}")) {
                    falsePlaceholder = action.split("\\{true}")[0];
                    falsePlaceholder = falsePlaceholder.replace("{false}", "");
                    truePlaceholder = action.split("\\{true}")[1];
                } else {
                    falsePlaceholder = action.replace("{false}", "");
                }
            } else {
                if (action.contains("{false}")) {
                    truePlaceholder = action.split("\\{false}")[0];
                    truePlaceholder = truePlaceholder.replace("{true}", "");
                    falsePlaceholder = action.split("\\{false}")[1];
                } else {
                    truePlaceholder = action.replace("{true}", "");
                }
            }
            if (truePlaceholder == null)
                truePlaceholder = "";
            if (falsePlaceholder == null)
                falsePlaceholder = "";
            if (type) {
                input = input.replace(placeholder, truePlaceholder);
            } else {
                input = input.replace(placeholder, falsePlaceholder);
            }
        }
        return input;
    }

    @Override
    public String getMsgTrans(String input) {
        return ConfigHandler.getConfig("config.yml").getString("Message.Translation." + input, input);
    }

    @Override
    public String getVanillaTrans(String pluginName, Player player, String input, String type) {
        return UtilsHandler.getVanillaUtils().getValinaName(pluginName, player, input, type);
    }

    @Override
    public String getVanillaTrans(String pluginName, String local, String input, String type) {
        return UtilsHandler.getVanillaUtils().getValinaNode(pluginName, local, input, type);
    }

    @Override
    public String getVanillaTrans(String pluginName, String input, String type) {
        return UtilsHandler.getVanillaUtils().getValinaNode(pluginName, null, input, type);
    }

    @Override
    public void addLog(String pluginName, File file, String message, boolean time, boolean newFile, boolean zip) {
        UtilsHandler.getLogger().addLog(pluginName, file, message, time, newFile, zip);
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
            return getMsgTrans("noTarget");
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
            return getMsgTrans("noTarget");
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
            return getMsgTrans("noTarget");
        }
        return stringBuilder.toString();
    }

    @Override
    public void sendHookMsg(String pluginPrefix, String type, List<String> list) {
        if (list == null || list.isEmpty()) {
            return;
        }
        StringBuilder message = new StringBuilder("&fHooked " + type + ": [");
        for (String value : list) {
            if (type.equals("plugins")) {
                switch (value) {
                    case "Vault":
                        if (UtilsHandler.getDepend().VaultEnabled())
                            message.append(value).append(", ");
                        break;
                    case "PlayerPoints":
                        if (UtilsHandler.getDepend().PlayerPointsEnabled())
                            message.append(value).append(", ");
                        break;
                    case "GemsEconomy":
                        if (UtilsHandler.getDepend().GemsEconomyEnabled())
                            message.append(value).append(", ");
                        break;
                    case "LuckPerms":
                        if (UtilsHandler.getDepend().PlaceHolderAPIEnabled())
                            message.append(value).append(", ");
                        break;
                    case "PlaceHolderAPI":
                        if (UtilsHandler.getDepend().DiscordSRVEnabled())
                            message.append(value).append(", ");
                        break;
                    case "DiscordSRV":
                        if (UtilsHandler.getDepend().LuckPermsEnabled())
                            message.append(value).append(", ");
                        break;
                    case "MysqlPlayerDataBridge":
                        if (UtilsHandler.getDepend().MpdbEnabled())
                            message.append(value).append(", ");
                        break;
                    case "CMI":
                        if (UtilsHandler.getDepend().ResidenceEnabled())
                            message.append(value).append(", ");
                        break;
                    case "MythicMobs":
                        if (UtilsHandler.getDepend().CMIEnabled())
                            message.append(value).append(", ");
                        break;
                    case "Residence":
                        if (UtilsHandler.getDepend().MythicMobsEnabled())
                            message.append(value).append(", ");
                        break;
                    case "ItemJoin":
                        if (UtilsHandler.getDepend().ItemJoinEnabled())
                            message.append(value).append(", ");
                        break;
                    case "AuthMe":
                        if (UtilsHandler.getDepend().AuthMeEnabled())
                            message.append(value).append(", ");
                        break;
                    case "PvPManager":
                        if (UtilsHandler.getDepend().PvPManagerEnabled())
                            message.append(value).append(", ");
                        break;
                    case "MultiverseCore":
                        if (UtilsHandler.getDepend().MultiverseCoreEnabled())
                            message.append(value).append(", ");
                        break;
                    case "Vehicles":
                        if (UtilsHandler.getDepend().SurvivalMechanicsEnabled())
                            message.append(value).append(", ");
                        break;
                    case "SurvivalMechanics":
                        if (UtilsHandler.getDepend().VehiclesEnabled())
                            message.append(value).append(", ");
                        break;
                    case "MyPet":
                        if (UtilsHandler.getDepend().MyPetEnabled())
                            message.append(value).append(", ");
                        break;
                    case "MorphTool":
                        if (UtilsHandler.getDepend().MorphToolEnabled())
                            message.append(value).append(", ");
                        break;
                }
            } else if (type.equals("Residence flags")) {
                if (UtilsHandler.getCondition().isRegisteredFlag(value))
                    message.append(value).append(", ");
            } else {
                message.append(value).append(", ");
            }
        }
        if (!message.substring(message.length() - 1).equals("[")) {
            sendConsoleMsg(pluginPrefix, message.substring(0, message.length() - 2) + "]");
        }
    }
}