package tw.momocraft.coreplus.utils.message;

import me.clip.placeholderapi.PlaceholderAPI;
import net.md_5.bungee.api.chat.TextComponent;
import org.apache.commons.lang.StringUtils;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import tw.momocraft.coreplus.CorePlus;
import tw.momocraft.coreplus.api.MessageInterface;
import tw.momocraft.coreplus.handlers.ConfigHandler;
import tw.momocraft.coreplus.handlers.UtilsHandler;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.*;

public class MessageManager implements MessageInterface {

    private String setPrefixAndColor(String prefix, String input) {
        if (prefix == null)
            prefix = "";
        input = prefix + input;
        input = input.replace("%prefix%", prefix);
        return ChatColor.translateAlternateColorCodes('&', input);
    }

    @Override
    public void sendBroadcastMsg(String prefix, String input, String... langHolder) {
        input = setPrefixAndColor(prefix, input);
        Bukkit.broadcastMessage(input);
    }

    @Override
    public void sendDiscordMsg(String prefix, String channel, String input, String... langHolder) {
        input = setPrefixAndColor(prefix, input);
        UtilsHandler.getDiscord().sendDiscordMsg(channel, input);
    }

    @Override
    public void sendDiscordMsg(String prefix, String channel, String input, Player player, String... langHolder) {
        input = setPrefixAndColor(prefix, input);
        String message = UtilsHandler.getYaml().getConfig("discord_messages").getString("MinecraftChatToDiscordMessageFormat");
        if (message != null)
            message = message.replace("%message%", input);
        else
            message = input;
        if (player != null) {
            String name = player.getName();
            String displayName = player.getCustomName();
            message = message.replace("%username%", name);
            message = message.replace("%displayname%", displayName != null ? displayName : name);
        }
        message = message.replace("%channelname%", channel);
        UtilsHandler.getDiscord().sendDiscordMsg(input, message);
    }

    @Override
    public void sendMsg(String prefix, CommandSender sender, String input, String... langHolder) {
        input = setPrefixAndColor(prefix, input);
        sender.sendMessage(input);
    }

    @Override
    public void sendConsoleMsg(String prefix, String input, String... langHolder) {
        input = setPrefixAndColor(prefix, input);
        CorePlus.getInstance().getServer().getConsoleSender().sendMessage(input);
    }

    @Override
    public void sendPlayerMsg(String prefix, Player player, String input, String... langHolder) {
        input = setPrefixAndColor(prefix, input);
        player.sendMessage(input);
    }

    @Override
    public void sendChatMsg(String prefix, Player player, String input, String... langHolder) {
        input = setPrefixAndColor(prefix, input);
        player.chat(input);
    }

    @Override
    public void sendActionBarMsg(Player player, String input, String... langHolder) {
        input = ChatColor.translateAlternateColorCodes('&', input);
        player.sendActionBar(TextComponent.fromLegacyText(input));
    }

    @Override
    public void sendTitleMsg(Player player, String input, String... langHolder) {
        String[] args = input.split("\\n");
        sendTitleMsg(player, args[0], args[1], 10, 70, 20, langHolder);
    }

    @Override
    public void sendTitleMsg(Player player, String inputTitle, String inputSubtitle, String... langHolder) {
        sendTitleMsg(player, inputTitle, inputSubtitle, 10, 70, 20, langHolder);
    }

    @Override
    public void sendTitleMsg(Player player, String input, TitleMsgMap titleMsgMap, String... langHolder) {
        String[] args = input.split("\\n");
        sendTitleMsg(player, args[0], args[1], titleMsgMap.getFadeIn(), titleMsgMap.getStay(), titleMsgMap.getFadeOut(), langHolder);
    }

    @Override
    public void sendTitleMsg(Player player, String inputTitle, String inputSubtitle, int fadeIn, int stay, int fadeOut, String... langHolder) {
        inputTitle = ChatColor.translateAlternateColorCodes('&', inputTitle);
        inputSubtitle = ChatColor.translateAlternateColorCodes('&', inputSubtitle);
        player.sendTitle(inputTitle, inputSubtitle, fadeIn, stay, fadeOut);
    }

    @Override
    public void sendErrorMsg(String pluginName, String input) {
        input = "&4[" + pluginName + "_Error] &e" + input;
        input = ChatColor.translateAlternateColorCodes('&', input);
        CorePlus.getInstance().getServer().getConsoleSender().sendMessage(input);
    }

    @Override
    public void sendDebugMsg(boolean isDebug, String pluginName, String input) {
        if (isDebug) {
            input = "&7[" + pluginName + "_Debug]&r " + input;
            input = ChatColor.translateAlternateColorCodes('&', input);
            CorePlus.getInstance().getServer().getConsoleSender().sendMessage(input);
        }
    }

    @Override
    public void sendDebugTrace(boolean isDebug, String pluginName, Exception ex) {
        if (isDebug) {
            sendDebugMsg(true, pluginName, "showing debug trace.");
            ex.printStackTrace();
        }
    }

    @Override
    public void sendDetailMsg(boolean isDebug, String pluginName, String feature, String target, String check, String action, String detail, StackTraceElement ste) {
        if (!isDebug)
            return;
        switch (action) {
            case "cancel":
            case "remove":
            case "kill":
            case "damage":
            case "fail":
            case "failed":
            case "warning":
            case "deny":
            case "prevent":
                action = "&c" + action;
                break;
            case "continue":
            case "bypass":
            case "change":
                action = "&e" + action;
                break;
            case "none":
                action = "&7" + action;
                break;
            case "return":
            case "success":
            case "succeed":
            case "accept":
            case "allow":
            default:
                action = "&a" + action;
                break;
        }
        sendDebugMsg(true, pluginName,
                "&f" + feature + "&8 - &f" + target + "&8 : &f" + check + "&8, &f" + action + "&8, &7" + detail);
        sendDebugMsg(true, pluginName,
                "&8(" + ste.getClassName() + " " + ste.getMethodName() + " " + ste.getLineNumber() + ")");
    }

    @Override
    public void sendDetailMsg(boolean debugging, String pluginName, String feature, String target, String check, String action, StackTraceElement ste) {
        if (!debugging)
            return;
        switch (action) {
            case "cancel":
            case "remove":
            case "kill":
            case "damage":
            case "fail":
            case "failed":
            case "warning":
            case "deny":
            case "prevent":
                action = "&c" + action;
                break;
            case "continue":
            case "bypass":
            case "change":
                action = "&e" + action;
                break;
            case "none":
                action = "&7" + action;
                break;
            case "return":
            case "success":
            case "succeed":
            case "accept":
            case "allow":
            default:
                action = "&a" + action;
                break;
        }
        sendDebugMsg(true, pluginName,
                "&f" + feature + "&8 - &f" + target + " &8: &f" + check + "&8, &f" + action);
        sendDebugMsg(true, pluginName,
                "&8(" + ste.getClassName() + " " + ste.getMethodName() + " " + ste.getLineNumber() + ")");
    }

    @Override
    public void sendLangMsg(String prefix, String input, CommandSender sender, String... langHolder) {
        if (input == null || input.equals(""))
            return;
        if (sender == null)
            sender = Bukkit.getConsoleSender();
        String langMessage = ConfigHandler.getConfig("config.yml").getString(input);
        if (langMessage != null)
            input = langMessage;
        input = transLang(UtilsHandler.getVanillaUtils().getLocal(sender), input, langHolder);
        input = setPrefixAndColor(prefix, input);
        String[] langLines = input.split("\\n");
        for (String langLine : langLines)
            sender.sendMessage(langLine);
    }


    @Override
    public String[] newString() {
        return new String[30];
    }

    private String[] initializeRows(String... placeHolder) {
        if (placeHolder == null || placeHolder.length != newString().length) {
            String[] langHolder = newString();
            Arrays.fill(langHolder, "null");
            return langHolder;
        } else {
            for (int i = 0; i < placeHolder.length; i++)
                if (placeHolder[i] == null)
                    placeHolder[i] = "null";
            return placeHolder;
        }
    }

    @Override
    public List<String> transLang(CommandSender sender, List<String> input, String... langHolder) {
        List<String> list = new ArrayList<>();
        String local = UtilsHandler.getVanillaUtils().getLocal(sender);
        for (String value : input)
            list.add(transLang(local, value, langHolder));
        return list;
    }

    @Override
    public List<String> transLang(Player player, List<String> input, String... langHolder) {
        List<String> list = new ArrayList<>();
        String local = UtilsHandler.getVanillaUtils().getLocal(player);
        for (String value : input)
            list.add(transLang(local, value, langHolder));
        return list;
    }

    @Override
    public List<String> transLang(String local, List<String> input, String... langHolder) {
        List<String> list = new ArrayList<>();
        for (String value : input)
            list.add(transLang(local, value, langHolder));
        return list;
    }

    @Override
    public String transLang(CommandSender sender, String input, String... langHolder) {
        return transLang(UtilsHandler.getVanillaUtils().getLocal(sender), input, langHolder);
    }

    @Override
    public String transLang(Player player, String input, String... langHolder) {
        return transLang(UtilsHandler.getVanillaUtils().getLocal(player), input, langHolder);
    }

    @Override
    public String transLang(String local, String input, String... langHolder) {
        if (input == null)
            return "";
        if (langHolder.length == 0)
            return input;
        String langMessage = ConfigHandler.getConfig("config.yml").getString(input);
        if (langMessage != null)
            input = langMessage;
        langHolder = initializeRows(langHolder);
        if (input.contains("%material%"))
            input = input.replace("%material%", getVanillaTrans(local, langHolder[7], "material"));
        else if (input.contains("%entity%"))
            input = input.replace("%entity%", getVanillaTrans(local, langHolder[8], "entity"));
        return input
                .replace("%player%", langHolder[0])
                .replace("%target_player%", langHolder[1])
                .replace("%plugin%", langHolder[2])
                .replace("%prefix%", langHolder[3])
                .replace("%value%", langHolder[4])
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
    public String transHolder(Player sender, Object target, String input) {
        if (input == null || input.isEmpty())
            return null;
        // Sender: %player_Placeholder%
        TranslateMap translateMap = getTranslateMap(null, sender, "player");
        // Target: %target_Placeholder%, %object_Placeholder%
        if (target != null)
            translateMap = getTranslateMap(translateMap, target, "target");
        return transTranslateMap(ConfigHandler.getPlugin(), (Player) sender, translateMap, input);
    }

    @Override
    public List<String> transHolder(Player sender, Object target, List<String> input) {
        if (input == null || input.isEmpty())
            return null;
        // Sender: %player_Placeholder%
        TranslateMap translateMap = getTranslateMap(null, sender, "player");
        // Target: %target_Placeholder%, %object_Placeholder%
        if (target != null)
            translateMap = getTranslateMap(translateMap, target, "target");
        return transTranslateMap(ConfigHandler.getPlugin(), sender, translateMap, input);
    }

    @Override
    public List<String> transHolder(Player sender, Object target, Object trigger, List<String> input) {
        if (input == null || input.isEmpty())
            return null;
        // Sender: %player_Placeholder%
        TranslateMap translateMap = getTranslateMap(null, sender, "player");
        // Target: %target_Placeholder%, %object_Placeholder%
        if (target != null)
            translateMap = getTranslateMap(translateMap, target, "target");
        // Trigger: %trigger_Placeholder%, %object_Placeholder%
        if (target != null)
            translateMap = getTranslateMap(translateMap, trigger, "trigger");
        return transTranslateMap(ConfigHandler.getPlugin(), sender, translateMap, input);
    }

    @Override
    public List<String> transHolder(Player sender, List<Object> targets, List<String> input) {
        if (input == null || input.isEmpty())
            return null;
        // Sender
        TranslateMap translateMap = getTranslateMap(null, sender, "player");
        // Targets
        Object target;
        for (int i = 1; i < targets.size(); i++) {
            target = targets.get(i);
            if (target != null)
                translateMap = getTranslateMap(translateMap, target, "target_" + i);
        }
        return transTranslateMap(ConfigHandler.getPlugin(), (Player) sender, translateMap, input);
    }

    @Override
    public List<String> transHolder(Player sender, List<Object> targets, List<Object> triggers, List<String> input) {
        if (input == null || input.isEmpty())
            return null;
        // Sender
        TranslateMap translateMap = getTranslateMap(null, sender, "player");
        // Targets
        Object target;
        for (int i = 1; i < targets.size(); i++) {
            target = targets.get(i);
            if (target != null)
                translateMap = getTranslateMap(translateMap, target, "target_" + i);
        }
        // Triggers
        for (int i = 1; i < targets.size(); i++) {
            target = triggers.get(i);
            if (target != null)
                translateMap = getTranslateMap(translateMap, target, "trigger_" + i);
        }
        return transTranslateMap(ConfigHandler.getPlugin(), sender, translateMap, input);
    }

    @Override
    public List<String> transTranslateMap(String pluginName, Player player, TranslateMap translateMap, List<String> input) {
        String local = UtilsHandler.getPlayer().getPlayerLocal(player);
        if (translateMap == null)
            return transByGeneral(pluginName, local, input);
        List<String> output;
        // Player
        if (translateMap.getPlayerMap() != null)
            for (Map.Entry<Player, String> entry : translateMap.getPlayerMap().entrySet())
                do {
                    output = input;
                    input = transByPlayer(pluginName, local, input, entry.getKey(), entry.getValue());
                    input = transByGeneral(pluginName, local, input);
                } while (!input.equals(output));
        // PlayerName
        if (translateMap.getPlayerNameMap() != null)
            for (Map.Entry<String, String> entry : translateMap.getPlayerNameMap().entrySet())
                do {
                    output = input;
                    input = transByPlayerName(pluginName, local, input, entry.getKey(), entry.getValue());
                    input = transByGeneral(pluginName, local, input);
                } while (!input.equals(output));
        // PlayerUUID
        if (translateMap.getPlayerUUIDMap() != null)
            for (Map.Entry<UUID, String> entry : translateMap.getPlayerUUIDMap().entrySet())
                do {
                    output = input;
                    input = transByPlayerUUID(pluginName, local, input, entry.getKey(), entry.getValue());
                    input = transByGeneral(pluginName, local, input);
                } while (!input.equals(output));
        // OfflinePlayer
        if (translateMap.getPlayerMap() != null)
            for (Map.Entry<OfflinePlayer, String> entry : translateMap.getOfflinePlayerMap().entrySet())
                do {
                    output = input;
                    input = transByOfflinePlayer(pluginName, local, input, entry.getKey(), entry.getValue());
                    input = transByGeneral(pluginName, local, input);
                } while (!input.equals(output));
        // Entity
        if (translateMap.getEntityMap() != null)
            for (Map.Entry<Entity, String> entry : translateMap.getEntityMap().entrySet())
                do {
                    output = input;
                    input = transByEntity(pluginName, local, input, entry.getKey(), entry.getValue());
                    input = transByGeneral(pluginName, local, input);
                } while (!input.equals(output));
        // EntityType
        if (translateMap.getEntityTypeMap() != null)
            for (Map.Entry<EntityType, String> entry : translateMap.getEntityTypeMap().entrySet())
                do {
                    output = input;
                    input = transByEntityType(pluginName, local, input, entry.getKey(), entry.getValue());
                    input = transByGeneral(pluginName, local, input);
                } while (!input.equals(output));
        // Block
        if (translateMap.getBlockMap() != null)
            for (Map.Entry<Block, String> entry : translateMap.getBlockMap().entrySet())
                do {
                    output = input;
                    input = transByBlock(pluginName, local, input, entry.getKey(), entry.getValue());
                    input = transByGeneral(pluginName, local, input);
                } while (!input.equals(output));
        // ItemStack
        if (translateMap.getItemStackMap() != null)
            for (Map.Entry<ItemStack, String> entry : translateMap.getItemStackMap().entrySet())
                do {
                    output = input;
                    input = transByItemStack(pluginName, local, input, entry.getKey(), entry.getValue());
                    input = transByGeneral(pluginName, local, input);
                } while (!input.equals(output));
        // Material
        if (translateMap.getMaterialMap() != null)
            for (Map.Entry<Material, String> entry : translateMap.getMaterialMap().entrySet())
                do {
                    output = input;
                    input = transByMaterial(pluginName, local, input, entry.getKey(), entry.getValue());
                    input = transByGeneral(pluginName, local, input);
                } while (!input.equals(output));
        // Location
        if (translateMap.getLocationMap() != null)
            for (Map.Entry<Location, String> entry : translateMap.getLocationMap().entrySet())
                do {
                    output = input;
                    input = transByLocation(pluginName, local, input, entry.getKey(), entry.getValue());
                    input = transByGeneral(pluginName, local, input);
                } while (!input.equals(output));
        return input;
    }

    @Override
    public String transTranslateMap(String pluginName, Player player, TranslateMap translateMap, String input) {
        String local = UtilsHandler.getPlayer().getPlayerLocal(player);
        String output;
        // Player
        if (translateMap.getPlayerMap() != null)
            for (Map.Entry<Player, String> entry : translateMap.getPlayerMap().entrySet())
                do {
                    output = input;
                    input = transByPlayer(pluginName, local, input, entry.getKey(), entry.getValue());
                    input = transByGeneral(pluginName, local, input);
                } while (!input.equals(output));
        // PlayerName
        if (translateMap.getPlayerNameMap() != null)
            for (Map.Entry<String, String> entry : translateMap.getPlayerNameMap().entrySet())
                do {
                    output = input;
                    input = transByPlayerName(pluginName, local, input, entry.getKey(), entry.getValue());
                    input = transByGeneral(pluginName, local, input);
                } while (!input.equals(output));
        // PlayerUUID
        if (translateMap.getPlayerUUIDMap() != null)
            for (Map.Entry<UUID, String> entry : translateMap.getPlayerUUIDMap().entrySet())
                do {
                    output = input;
                    input = transByPlayerUUID(pluginName, local, input, entry.getKey(), entry.getValue());
                    input = transByGeneral(pluginName, local, input);
                } while (!input.equals(output));
        // OfflinePlayer
        if (translateMap.getPlayerMap() != null)
            for (Map.Entry<OfflinePlayer, String> entry : translateMap.getOfflinePlayerMap().entrySet())
                do {
                    output = input;
                    input = transByOfflinePlayer(pluginName, local, input, entry.getKey(), entry.getValue());
                    input = transByGeneral(pluginName, local, input);
                } while (!input.equals(output));
        // Entity
        if (translateMap.getEntityMap() != null)
            for (Map.Entry<Entity, String> entry : translateMap.getEntityMap().entrySet())
                do {
                    output = input;
                    input = transByEntity(pluginName, local, input, entry.getKey(), entry.getValue());
                    input = transByGeneral(pluginName, local, input);
                } while (!input.equals(output));
        // EntityType
        if (translateMap.getEntityTypeMap() != null)
            for (Map.Entry<EntityType, String> entry : translateMap.getEntityTypeMap().entrySet())
                do {
                    output = input;
                    input = transByEntityType(pluginName, local, input, entry.getKey(), entry.getValue());
                    input = transByGeneral(pluginName, local, input);
                } while (!input.equals(output));
        // Block
        if (translateMap.getBlockMap() != null)
            for (Map.Entry<Block, String> entry : translateMap.getBlockMap().entrySet())
                do {
                    output = input;
                    input = transByBlock(pluginName, local, input, entry.getKey(), entry.getValue());
                    input = transByGeneral(pluginName, local, input);
                } while (!input.equals(output));
        // ItemStack
        if (translateMap.getItemStackMap() != null)
            for (Map.Entry<ItemStack, String> entry : translateMap.getItemStackMap().entrySet())
                do {
                    output = input;
                    input = transByItemStack(pluginName, local, input, entry.getKey(), entry.getValue());
                    input = transByGeneral(pluginName, local, input);
                } while (!input.equals(output));
        // Material
        if (translateMap.getMaterialMap() != null)
            for (Map.Entry<Material, String> entry : translateMap.getMaterialMap().entrySet())
                do {
                    output = input;
                    input = transByMaterial(pluginName, local, input, entry.getKey(), entry.getValue());
                    input = transByGeneral(pluginName, local, input);
                } while (!input.equals(output));
        // Location
        if (translateMap.getLocationMap() != null)
            for (Map.Entry<Location, String> entry : translateMap.getLocationMap().entrySet())
                do {
                    output = input;
                    input = transByLocation(pluginName, local, input, entry.getKey(), entry.getValue());
                    input = transByGeneral(pluginName, local, input);
                } while (!input.equals(output));
        return input;
    }

    @Override
    public TranslateMap getTranslateMap(TranslateMap translateMap, Object target, String targetType, String prefixName) {
        if (target == null)
            return translateMap;
        translateMap = getTranslateMap(translateMap, target, targetType);
        if (translateMap == null)
            translateMap = new TranslateMap();
        if (target instanceof Player)
            translateMap.putPlayer((Player) target, prefixName);
        else if (target instanceof Entity)
            translateMap.putEntity((Entity) target, prefixName);
        else if (target instanceof EntityType)
            translateMap.putEntityType((EntityType) target, prefixName);
        else if (target instanceof Block)
            translateMap.putBlock((Block) target, prefixName);
        else if (target instanceof ItemStack)
            translateMap.putItemStack((ItemStack) target, prefixName);
        else if (target instanceof Material)
            translateMap.putMaterial((Material) target, prefixName);
        else if (target instanceof Location)
            translateMap.putLocation((Location) target, prefixName);
        else {
            UtilsHandler.getMsg().sendErrorMsg(ConfigHandler.getPlugin(),
                    "An error occurred while translating placeholders.");
            UtilsHandler.getMsg().sendErrorMsg(ConfigHandler.getPlugin(),
                    "Unknown target type: \"" + target.toString() + "\"");
        }
        return translateMap;
    }

    @Override
    public TranslateMap getTranslateMap(TranslateMap translateMap, Object target, String targetType) {
        if (target == null)
            return translateMap;
        if (translateMap == null)
            translateMap = new TranslateMap();
        translateMap = getTranslateMap(translateMap, target, targetType);
        if (target instanceof Player)
            translateMap.putPlayer((Player) target, "player");
        else if (target instanceof Entity)
            translateMap.putEntity((Entity) target, "entity");
        else if (target instanceof EntityType)
            translateMap.putEntityType((EntityType) target, "entitytype");
        else if (target instanceof Block)
            translateMap.putBlock((Block) target, "block");
        else if (target instanceof ItemStack)
            translateMap.putItemStack((ItemStack) target, "item");
        else if (target instanceof Material)
            translateMap.putMaterial((Material) target, "material");
        else if (target instanceof Location)
            translateMap.putLocation((Location) target, "location");
        else {
            UtilsHandler.getMsg().sendErrorMsg(ConfigHandler.getPlugin(),
                    "An error occurred while translating placeholders.");
            UtilsHandler.getMsg().sendErrorMsg(ConfigHandler.getPlugin(),
                    "Unknown target type: \"" + target.toString() + "\"");
        }
        return translateMap;
    }

    private List<String> transByPlayerName(String pluginName, String local, List<String> input, String
            target, String prefixName) {
        if (input == null || input.isEmpty())
            return null;
        List<String> list = new ArrayList<>();
        for (String value : input)
            list.add(transByPlayerName(pluginName, local, value, target, prefixName));
        return list;
    }

    private String transByPlayerName(String pluginName, String local, String input, String target, String
            prefixName) {
        Player player = Bukkit.getPlayer(target);
        if (player != null)
            return transByPlayer(pluginName, local, input, player, prefixName);
        OfflinePlayer offlinePlayer = UtilsHandler.getPlayer().getOfflinePlayer(target);
        if (offlinePlayer != null)
            return transByOfflinePlayer(pluginName, local, input, offlinePlayer, prefixName);
        return input;
    }

    private List<String> transByPlayerUUID(String pluginName, String local, List<String> input, UUID
            target, String prefixName) {
        if (input == null || input.isEmpty())
            return null;
        List<String> list = new ArrayList<>();
        for (String value : input)
            list.add(transByPlayerUUID(pluginName, local, value, target, prefixName));
        return list;
    }

    private String transByPlayerUUID(String pluginName, String local, String input, UUID target, String prefixName) {
        Player player = Bukkit.getPlayer(target);
        if (player != null)
            return transByPlayer(pluginName, local, input, player, prefixName);
        OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(target);
        return transByOfflinePlayer(pluginName, local, input, offlinePlayer, prefixName);
    }

    private List<String> transByPlayer(String pluginName, String local, List<String> input, Player
            target, String prefixName) {
        if (input == null || input.isEmpty())
            return null;
        List<String> list = new ArrayList<>();
        for (String value : input)
            list.add(transByPlayer(pluginName, local, value, target, prefixName));
        return list;
    }

    private String transByPlayer(String pluginName, String local, String input, Player target, String prefixName) {
        if (input == null)
            return "";
        if (target == null) {
            // %TARGET_has_played%
            input = input.replace("%" + prefixName + "_has_played%", "false");
            return input;
        } else if (target instanceof ConsoleCommandSender) {
            return input;
        }
        // %TARGET_last_login%
        input = input.replace("%" + prefixName + "_last_login%", String.valueOf(target.getLastLogin()));
        // %TARGET_display_name%
        input = input.replace("%" + prefixName + "_display_name%", target.getDisplayName());
        // %money%
        UUID uuid = target.getUniqueId();
        if (UtilsHandler.getDepend().VaultEnabled())
            if (input.contains("%" + prefixName + "_money%"))
                input = input.replace("%" + prefixName + "_money%", String.valueOf(UtilsHandler.getDepend().getVaultApi().getBalance(uuid)));
        // %points%
        if (UtilsHandler.getDepend().PlayerPointsEnabled())
            if (input.contains("%" + prefixName + "_points%"))
                input = input.replace("%" + prefixName + "_points%", String.valueOf(UtilsHandler.getDepend().getPlayerPointsApi().getBalance(uuid)));
        // %TARGET_gamemode%
        input = input.replace("%" + prefixName + "_gamemode%", target.getGameMode().name());
        // %TARGET_sneaking%
        input = input.replace("%" + prefixName + "_sneaking%", String.valueOf(target.isSneaking()));
        // %TARGET_flying%
        input = input.replace("%" + prefixName + "_flying%", String.valueOf(target.isFlying()));
        // Translate PlaceHolderAPI placeholders.
        if (UtilsHandler.getDepend().PlaceHolderAPIEnabled()) {
            try {
                if (StringUtils.countMatches(input, "%") % 2 == 1) {
                    input = input + "$%";
                    input = PlaceholderAPI.setPlaceholders(target, input);
                    input = input.substring(0, input.length() - 2);
                } else {
                    input = PlaceholderAPI.setPlaceholders(target, input);
                }
            } catch (NoSuchFieldError e) {
                UtilsHandler.getMsg().sendDebugMsg(ConfigHandler.isDebug(), ConfigHandler.getPrefix(),
                        "Error has occurred when setting the PlaceHolder " + e.getMessage() +
                                ", if this issue persist contact the developer of PlaceholderAPI.");
            }
        }
        // Entity
        input = transByEntity(pluginName, local, input, target, prefixName);
        // PlaceHolderAPI
        if (UtilsHandler.getDepend().PlaceHolderAPIEnabled()) {
            try {
                if (StringUtils.countMatches(input, "%") % 2 == 1) {
                    input = input + "$%";
                    input = PlaceholderAPI.setPlaceholders(target, input);
                    return input.substring(0, input.length() - 2);
                } else {
                    return PlaceholderAPI.setPlaceholders(target, input);
                }
            } catch (NoSuchFieldError e) {
                UtilsHandler.getMsg().sendDebugMsg(true, pluginName,
                        "Error has occurred when setting the PlaceHolder " + e.getMessage() +
                                ", if this issue persist contact the developer of PlaceholderAPI.");
            }
        }
        return input;
    }

    private List<String> transByEntity(String pluginName, String local, List<String> input, Entity
            target, String prefixName) {
        if (input == null || input.isEmpty())
            return null;
        List<String> list = new ArrayList<>();
        for (String value : input)
            list.add(transByEntity(pluginName, local, value, target, prefixName));
        return list;
    }

    private String transByEntity(String pluginName, String local, String input, Entity target, String prefixName) {
        if (input == null)
            return "";
        if (target == null)
            return input;
        String displayName = target.getCustomName();
        String name = target.getName();
        // %TARGET%
        input = input.replace("%" + prefixName + "%", displayName != null ? displayName : name);
        // %TARGET_display_name%
        input = input.replace("%" + prefixName + "_display_name%", displayName != null ? displayName : name);
        // %TARGET_has_custom_name%
        input = input.replace("%" + prefixName + "_has_custom_name%", displayName != null ? "true" : "false");
        // %TARGET_uuid%
        UUID uuid = target.getUniqueId();
        input = input.replace("%" + prefixName + "_uuid%", uuid.toString());
        // EntityType
        input = transByEntityType(pluginName, local, input, target.getType(), prefixName);
        // Location
        input = transByLocation(pluginName, local, input, target.getLocation(), prefixName);
        return input;
    }

    private List<String> transByEntityType(String pluginName, String local, List<String> input, EntityType
            target, String prefixName) {
        if (input == null || input.isEmpty())
            return null;
        List<String> list = new ArrayList<>();
        for (String value : input)
            list.add(transByEntityType(pluginName, local, value, target, prefixName));
        return list;
    }

    private String transByEntityType(String pluginName, String local, String input, EntityType target, String
            prefixName) {
        if (input == null)
            return "";
        if (target == null)
            return input;
        // %TARGET%
        String type = target.name();
        try {
            input = input.replace("%" + prefixName + "%", type);
        } catch (Exception ex) {
            UtilsHandler.getMsg().sendDebugTrace(ConfigHandler.isDebug(), ConfigHandler.getPlugin(), ex);
        }
        // %TARGET_type%
        try {
            input = input.replace("%" + prefixName + "_type%", type);
        } catch (Exception ex) {
            UtilsHandler.getMsg().sendDebugTrace(ConfigHandler.isDebug(), ConfigHandler.getPlugin(), ex);
        }
        // %TARGET_type_local%
        try {
            input = input.replace("%" + prefixName + "_type_local%", getVanillaTrans(pluginName, type, "material"));
        } catch (Exception ex) {
            UtilsHandler.getMsg().sendDebugTrace(ConfigHandler.isDebug(), ConfigHandler.getPlugin(), ex);
        }
        return input;
    }

    private List<String> transByOfflinePlayer(String pluginName, String local, List<String> input, OfflinePlayer
            target, String prefixName) {
        if (input == null || input.isEmpty())
            return null;
        List<String> list = new ArrayList<>();
        for (String value : input)
            list.add(transByOfflinePlayer(pluginName, local, value, target, prefixName));
        return list;
    }

    private String transByOfflinePlayer(String pluginName, String local, String input, OfflinePlayer target, String
            prefixName) {
        if (input == null)
            return "";
        if (target == null)
            return input;
        // %TARGET_has_played%
        input = input.replace("%" + prefixName + "_has_played%", "false");
        // %TARGET%
        try {
            input = input.replace("%" + prefixName + "%", target.getName());
        } catch (Exception ex) {
            input = input.replace("%" + prefixName + "%", getMsgTrans("noTarget"));
        }
        // %TARGET_uuid%
        try {
            input = input.replace("%" + prefixName + "_uuid%", target.getUniqueId().toString());
        } catch (Exception ex) {
            UtilsHandler.getMsg().sendDebugTrace(ConfigHandler.isDebug(), ConfigHandler.getPlugin(), ex);
        }
        // %TARGET_last_login%
        boolean hasPlayed = target.hasPlayedBefore();
        try {
            input = input.replace("%" + prefixName + "_last_login%",
                    hasPlayed ? String.valueOf(target.getLastLogin()) : getMsgTrans("noData"));
        } catch (Exception ex) {
            UtilsHandler.getMsg().sendDebugTrace(ConfigHandler.isDebug(), ConfigHandler.getPlugin(), ex);
        }
        // %TARGET_has_played%
        try {
            input = input.replace("%" + prefixName + "_has_played%", String.valueOf(hasPlayed));
        } catch (Exception ex) {
            UtilsHandler.getMsg().sendDebugTrace(ConfigHandler.isDebug(), ConfigHandler.getPlugin(), ex);
        }
        // Translate PlaceHolderAPI's placeholders.
        if (UtilsHandler.getDepend().PlaceHolderAPIEnabled()) {
            try {
                if (StringUtils.countMatches(input, "%") % 2 == 1) {
                    input = input + "$%";
                    input = PlaceholderAPI.setPlaceholders(target, input);
                    input = input.substring(0, input.length() - 2);
                } else {
                    input = PlaceholderAPI.setPlaceholders(target, input);
                }
            } catch (NoSuchFieldError e) {
                UtilsHandler.getMsg().sendDebugMsg(ConfigHandler.isDebug(), ConfigHandler.getPrefix(),
                        "Error has occurred when setting the PlaceHolder " + e.getMessage() +
                                ", if this issue persist contact the developer of PlaceholderAPI.");
            }
        }
        return input;
    }

    private List<String> transByBlock(String pluginName, String local, List<String> input, Block target, String
            prefixName) {
        if (input == null || input.isEmpty())
            return null;
        List<String> list = new ArrayList<>();
        for (String value : input)
            list.add(transByBlock(pluginName, local, value, target, prefixName));
        return list;
    }

    private String transByBlock(String pluginName, String local, String input, Block target, String prefixName) {
        if (input == null)
            return "";
        if (target == null)
            return input;
        // Material
        input = transByMaterial(pluginName, local, input, target.getType(), prefixName);
        // Location
        input = transByLocation(pluginName, local, input, target.getLocation(), prefixName);
        return input;
    }

    private List<String> transByItemStack(String pluginName, String local, List<String> input, ItemStack
            target, String prefixName) {
        if (input == null || input.isEmpty())
            return null;
        List<String> list = new ArrayList<>();
        for (String value : input)
            list.add(transByItemStack(pluginName, local, value, target, prefixName));
        return list;
    }

    private String transByItemStack(String pluginName, String local, String input, ItemStack target, String
            prefixName) {
        if (input == null)
            return "";
        if (target == null)
            return input;
        // %TARGET_display_name%
        ItemMeta itemMeta = target.getItemMeta();
        String displayName = null;
        if (itemMeta != null)
            displayName = target.getItemMeta().getDisplayName();
        try {
            input = input.replace("%" + prefixName + "_display_name%", displayName != null ? displayName : target.getType().name());
        } catch (Exception ex) {
            UtilsHandler.getMsg().sendDebugTrace(ConfigHandler.isDebug(), ConfigHandler.getPlugin(), ex);
        }
        // %TARGET_has_custom_name%
        try {
            input = input.replace("%" + prefixName + "_display_name%", displayName != null ? "true" : "false");
        } catch (Exception ex) {
            UtilsHandler.getMsg().sendDebugTrace(ConfigHandler.isDebug(), ConfigHandler.getPlugin(), ex);
        }
        // %TARGET_amount%
        try {
            input = input.replace("%" + prefixName + "_amount%", String.valueOf(target.getAmount()));
        } catch (Exception ex) {
            UtilsHandler.getMsg().sendDebugTrace(ConfigHandler.isDebug(), ConfigHandler.getPlugin(), ex);
        }
        // Material
        input = transByMaterial(pluginName, local, input, target.getType(), prefixName);
        return input;
    }

    private List<String> transByMaterial(String pluginName, String local, List<String> input, Material
            target, String prefixName) {
        if (input == null || input.isEmpty())
            return null;
        List<String> list = new ArrayList<>();
        for (String value : input)
            list.add(transByMaterial(pluginName, local, value, target, prefixName));
        return list;
    }

    private String transByMaterial(String pluginName, String local, String input, Material target, String
            prefixName) {
        if (input == null)
            return "";
        if (target == null)
            return input;
        // %TARGET%
        String type = target.name();
        try {
            input = input.replace("%" + prefixName + "%", type);
        } catch (Exception ex) {
            UtilsHandler.getMsg().sendDebugTrace(ConfigHandler.isDebug(), ConfigHandler.getPlugin(), ex);
        }
        // %TARGET_type%
        try {
            input = input.replace("%" + prefixName + "_type%", type);
        } catch (Exception ex) {
            UtilsHandler.getMsg().sendDebugTrace(ConfigHandler.isDebug(), ConfigHandler.getPlugin(), ex);
        }
        // %TARGET_type_local%
        try {
            input = input.replace("%" + prefixName + "_type_local%", getVanillaTrans(pluginName, type, "material"));
        } catch (Exception ex) {
            UtilsHandler.getMsg().sendDebugTrace(ConfigHandler.isDebug(), ConfigHandler.getPlugin(), ex);
        }
        return input;
    }

    private List<String> transByLocation(String pluginName, String local, List<String> input, Location
            target, String prefixName) {
        if (input == null || input.isEmpty())
            return null;
        List<String> list = new ArrayList<>();
        for (String value : input)
            list.add(transByLocation(pluginName, local, value, target, prefixName));
        return list;
    }

    private String transByLocation(String pluginName, String local, String input, Location target, String
            prefixName) {
        if (input == null)
            return "";
        if (target == null)
            return input;
        World world = target.getWorld();
        Block block = target.getBlock();
        // %TARGET_world%
        if (input.contains("%" + prefixName + "_world%"))
            input = input.replace("%" + prefixName + "_world%", world.getName());
        // %TARGET_loc%
        // %TARGET_loc_x%, %TARGET_loc_y%, %TARGET_loc_z%
        // %TARGET_loc_x_NUMBER%, %TARGET_loc_y_NUMBER%, %TARGET_loc_z_NUMBER%
        if (input.contains("%" + prefixName + "_loc")) {
            String loc_x = String.valueOf(target.getBlockX());
            String loc_y = String.valueOf(target.getBlockY());
            String loc_z = String.valueOf(target.getBlockZ());
            String[] arr = input.split("%");
            int offset;
            for (int i = 0; i < arr.length; i++) {
                try {
                    if (arr[i].endsWith("_loc_x")) {
                        if (arr[i + 1].matches("-?[0-9]*(\\.[0-9]+)?")) {
                            offset = Integer.parseInt(loc_x) + Integer.parseInt(arr[i + 1]);
                            input = input.replace("%" + arr[i] + "%" + arr[i + 1] + "%", String.valueOf(offset));
                        }
                    } else if (arr[i].endsWith("_loc_y")) {
                        if (arr[i + 1].matches("-?[0-9]*(\\.[0-9]+)?")) {
                            offset = Integer.parseInt(loc_y) + Integer.parseInt(arr[i + 1]);
                            input = input.replace("%" + arr[i] + "%" + arr[i + 1] + "%", String.valueOf(offset));
                        }
                    } else if (arr[i].endsWith("_loc_z")) {
                        if (arr[i + 1].matches("-?[0-9]*(\\.[0-9]+)?")) {
                            offset = Integer.parseInt(loc_z) + Integer.parseInt(arr[i + 1]);
                            input = input.replace("%" + arr[i] + "%" + arr[i + 1] + "%", String.valueOf(offset));
                        }
                    }
                } catch (Exception ignored) {
                }
            }
            input = input.replace("%" + prefixName + "_loc%", loc_x + ", " + loc_y + ", " + loc_z);
            input = input.replace("%" + prefixName + "_loc_x%", loc_x);
            input = input.replace("%" + prefixName + "_loc_y%", loc_y);
            input = input.replace("%" + prefixName + "_loc_z%", loc_z);
        }
        // %TARGET_cave%
        if (input.contains("%" + prefixName + "_cave%")) {
            Location blockLoc;
            back:
            for (int x = -3; x <= 3; x++) {
                for (int z = -3; z <= 3; z++) {
                    for (int y = -3; y <= 3; y++) {
                        blockLoc = target.clone().add(x, y, z);
                        if (Material.CAVE_AIR.equals(blockLoc.getBlock().getType())) {
                            input = input.replace("%" + prefixName + "_cave%", "true");
                            continue back;
                        }
                    }
                }
            }
        }
        // %TARGET_loc_material%
        if (input.contains("%" + prefixName + "_loc_material%"))
            input = input.replace("%" + prefixName + "_material%", block.getType().name());
        // %TARGET_loc_cave%
        if (input.contains("%" + prefixName + "_loc_cave%")) {
            Location blockLoc;
            back:
            for (int x = -3; x <= 3; x++) {
                for (int z = -3; z <= 3; z++) {
                    for (int y = -3; y <= 3; y++) {
                        blockLoc = target.clone().add(x, y, z);
                        if (Material.CAVE_AIR.equals(blockLoc.getBlock().getType())) {
                            input = input.replace("%" + prefixName + "_loc_cave%", "true");
                            break back;
                        }
                    }
                }
            }
        }
        // %TARGET_world_time%
        if (input.contains("%" + prefixName + "_world_time%"))
            input = input.replace("%" + prefixName + "_world_time%", String.valueOf(world.getTime()));
        // %TARGET_biome%
        if (input.contains("%" + prefixName + "_biome%"))
            input = input.replace("%" + prefixName + "_biome%", block.getBiome().name());
        // %TARGET_light%
        if (input.contains("%" + prefixName + "_light%"))
            input = input.replace("%" + prefixName + "_light%", String.valueOf(block.getLightLevel()));
        // %TARGET_liquid%
        if (input.contains("%" + prefixName + "_liquid%"))
            input = input.replace("%" + prefixName + "_liquid%", String.valueOf(block.isLiquid()));
        // %TARGET_solid%
        if (input.contains("%" + prefixName + "_solid%"))
            input = input.replace("%" + prefixName + "_solid%", String.valueOf(block.getType().isSolid()));
        // %TARGET_cave%
        if (input.contains("%" + prefixName + "_cave%"))
            input = input.replace("%" + prefixName + "_cave%", String.valueOf(UtilsHandler.getCondition().isInCave(target)));
        // %TARGET_outside%
        if (input.contains("%" + prefixName + "_outside%"))
            input = input.replace("%" + prefixName + "_outside%", String.valueOf(UtilsHandler.getCondition().isInOutside(target)));
        // %TARGET_ground%
        if (input.contains("%" + prefixName + "_ground%"))
            input = input.replace("%" + prefixName + "_ground%", String.valueOf(UtilsHandler.getCondition().isOnGround(target)));
        // %TARGET_residence%
        if (input.contains("%" + prefixName + "_residence%")) {
            String residenceName = UtilsHandler.getCondition().getResidenceName(target);
            input = input.replace("%" + prefixName + "_residence%", residenceName != null ? residenceName : "");
        }
        // %TARGET_in_residence%
        if (input.contains("%" + prefixName + "_in_residence%"))
            input = input.replace("%" + prefixName + "_in_residence%",
                    String.valueOf(UtilsHandler.getCondition().isInResidence(target)));
        // %TARGET_location%LOCATION%
        if (input.contains("%" + prefixName + "_location%")) {
            try {
                String[] arr = input.split("%");
                for (int i = 0; i < arr.length; i++) {
                    if (arr[i].equals("%" + prefixName + "_location%"))
                        input = input.replace("%" + prefixName + "_location%" + arr[i + 1] + "%",
                                String.valueOf(UtilsHandler.getCondition().checkLocation(ConfigHandler.getPlugin(), target, arr[i + 1], false)));
                }
            } catch (Exception ex) {
                UtilsHandler.getMsg().sendErrorMsg(pluginName, "An error occurred while converting placeholder: \"" + input + "\"");
                UtilsHandler.getMsg().sendErrorMsg(pluginName, "Not correct format: \"\"%TARGET_location%LOCATION%\"");
                UtilsHandler.getMsg().sendErrorMsg(pluginName, "More information: https://github.com/momoservertw/CorePlus/wiki/Placeholders");
                UtilsHandler.getMsg().sendDebugTrace(true, pluginName, ex);
            }
        }
        // %TARGET_blocks%BLOCKS%
        if (input.contains("%" + prefixName + "_blocks%")) {
            try {
                String[] arr = input.split("%");
                for (int i = 0; i < arr.length; i++) {
                    if (arr[i].equals("%" + prefixName + "_blocks%"))
                        input = input.replace("%" + prefixName + "_blocks%" + arr[i + 1] + "%",
                                String.valueOf(UtilsHandler.getCondition().checkBlocks(target, arr[i + 1], false)));
                }
            } catch (Exception ex) {
                UtilsHandler.getMsg().sendErrorMsg(pluginName, "An error occurred while converting placeholder: \"" + input + "\"");
                UtilsHandler.getMsg().sendErrorMsg(pluginName, "Not correct format: \"\"%TARGET_blocks%BLOCKS%\"");
                UtilsHandler.getMsg().sendErrorMsg(pluginName, "More information: https://github.com/momoservertw/CorePlus/wiki/Placeholders");
                UtilsHandler.getMsg().sendDebugTrace(true, pluginName, ex);
            }
        }
        // %TARGET_nearby%type%name/type%group%radius%
        // arr[0]=Target_nearby, arr[1]=Type, arr[2]=Name/Type, arr[3]=Group, arr[4]=Radius
        if (input.contains("%" + prefixName + "_nearby%")) {
            String[] arr = input.split("%");
            String nearbyString;
            for (int i = 0; i < arr.length; i++) {
                if (arr[i].equals("%" + prefixName + "_nearby%")) {
                    nearbyString = UtilsHandler.getUtil().getStringFromNearbyType(pluginName, target,
                            arr[i + 1], arr[i + 2], arr[i + 3], Integer.parseInt(arr[i + 4]));
                    input = input.replace("%" + arr[i] + "%" + arr[i + 1] + "%" + arr[i + 2] +
                            "%" + arr[i + 3] + "%" + arr[i + 4] + "%", nearbyString);
                    i += 4;
                }
            }
        }
        return input;
    }


    @Override
    public List<String> transByGeneral(String pluginName, String local, List<String> input) {
        if (input == null || input.isEmpty())
            return null;
        List<String> list = new ArrayList<>();
        for (String value : input)
            list.add(transByGeneral(pluginName, local, value));
        return list;
    }


    @Override
    public String transByGeneral(String pluginName, String local, String input) {
        if (input == null)
            return "";
        // Color codes
        input = ChatColor.translateAlternateColorCodes('&', input);
        // Symbol
        input = input.replace("{s}", " ");
        input = input.replace("{e}", "");
        // %player%, %entity%, %block%, %item%, %target%
        try {
            input = input.replace("%player%", getMsgTrans("console"));
            input = input.replace("%entity%", getMsgTrans("console"));
            input = input.replace("%block%", getMsgTrans("console"));
            input = input.replace("%item%", getMsgTrans("console"));
            input = input.replace("%target%", getMsgTrans("console"));
        } catch (Exception ex) {
            UtilsHandler.getMsg().sendDebugTrace(ConfigHandler.isDebug(), ConfigHandler.getPlugin(), ex);
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
                UtilsHandler.getMsg().sendErrorMsg(pluginName, "An error occurred while converting placeholder: \"" + input + "\"");
                UtilsHandler.getMsg().sendErrorMsg(pluginName, "Not correct format: \"\"%random_number%NUMBER%\"");
                UtilsHandler.getMsg().sendErrorMsg(pluginName, "More information: https://github.com/momoservertw/CorePlus/wiki/Placeholders");
                UtilsHandler.getMsg().sendDebugTrace(true, pluginName, ex);
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
                UtilsHandler.getMsg().sendErrorMsg(pluginName, "An error occurred while converting placeholder: \"" + input + "\"");
                UtilsHandler.getMsg().sendErrorMsg(pluginName, "Not correct format: \"\"%random_list%STRING1,STRING2%\"");
                UtilsHandler.getMsg().sendErrorMsg(pluginName, "More information: https://github.com/momoservertw/CorePlus/wiki/Placeholders");
                UtilsHandler.getMsg().sendDebugTrace(true, pluginName, ex);
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
                    UtilsHandler.getLang().sendDebugTrace(ConfigHandler.isDebug(), ConfigHandler.getPlugin(), ex);
                }
                }
             */
        // %random_player_except%AllBye,huangge0513%
        if (input.contains("%random_player_except%")) {
            List<String> placeholderList = new ArrayList<>();
            List<Player> playerList = new ArrayList(Bukkit.getOnlinePlayers());
            String[] arr = input.split("%");
            for (int i = 0; i < arr.length; i++) {
                if (arr[i].equals("random_player_except"))
                    placeholderList.add((arr[i + 1]));
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
                        UtilsHandler.getMsg().sendErrorMsg(pluginName, "An error occurred while converting placeholder: \"" + input + "\"");
                        UtilsHandler.getMsg().sendErrorMsg(pluginName, "Not correct format: \"%random_player_except%PLAYERS%\"");
                        UtilsHandler.getMsg().sendErrorMsg(pluginName, "More information: https://github.com/momoservertw/CorePlus/wiki/Placeholders");
                        UtilsHandler.getMsg().sendDebugTrace(true, ConfigHandler.getPlugin(), ex);
                    }
                }
            }
        }
        // The target placeholder.
        String placeholder;
        // The replaced placeholder.
        String newPlaceholder;
        // The placeholder without prefix and suffix.
        String placeholderValue;
        // The string for sending error messages.
        String originInput = input;

        String[] split;
        String[] splitValues;
        if (input.contains("%str_")) {
            String[] arr = input.split("%", -1);
            StringBuilder stringBuilder;
            for (int i = 0; i < arr.length; i++) {
                try {
                    switch (arr[i].toLowerCase()) {
                        case "str_replace":
                            // %str_replace%Momocraft%craft%{e}%
                            placeholder = "%" + arr[i] + "%" + arr[i + 1] + "%" + arr[i + 2] + "%" + arr[i + 3] + "%";
                            newPlaceholder = arr[i + 1].replace(arr[i + 2], arr[i + 3]);
                            input = input.replace(placeholder, newPlaceholder);
                            break;
                        case "str_endswith":
                            // %str_endswith%Momocraft%aft%
                            placeholder = "%" + arr[i] + "%" + arr[i + 1] + "%" + arr[i + 2] + "%";
                            newPlaceholder = String.valueOf(arr[i + 1].endsWith(arr[i + 2]));
                            input = input.replace(placeholder, newPlaceholder);
                            break;
                        case "str_startswith":
                            // %str_startswith%Momocraft%Momo%4%
                            if (arr.length >= i + 3 && arr[i + 3].matches("[0-9]+")) {
                                placeholder = "%" + arr[i] + "%" + arr[i + 1] + "%" + arr[i + 2] + "%" + arr[i + 3] + "%";
                                newPlaceholder = String.valueOf(arr[i + 1].startsWith(arr[i + 2], Integer.parseInt(arr[i + 3])));
                            } else {
                                // %str_startswith%Momocraft%Momo%
                                placeholder = "%" + arr[i] + "%" + arr[i + 1] + "%" + arr[i + 2] + "%";
                                newPlaceholder = String.valueOf(arr[i + 1].startsWith(arr[i + 2]));
                            }
                            input = input.replace(placeholder, newPlaceholder);
                            break;
                        case "str_matches":
                            // %str_matches%Momocraft%[a-zA-Z]+%
                            placeholder = "%" + arr[i] + "%" + arr[i + 1] + "%" + arr[i + 2] + "%";
                            newPlaceholder = String.valueOf(arr[i + 1].matches(arr[i + 2]));
                            input = input.replace(placeholder, newPlaceholder);
                            break;
                        case "str_tolowercase":
                            placeholder = "%" + arr[i] + "%" + arr[i + 1] + "%";
                            newPlaceholder = arr[i + 1].toLowerCase();
                            input = input.replace(placeholder, newPlaceholder);
                            break;
                        case "str_touppercase":
                            placeholder = "%" + arr[i] + "%" + arr[i + 1] + "%";
                            newPlaceholder = arr[i + 1].toUpperCase();
                            input = input.replace(placeholder, newPlaceholder);
                            break;
                        case "str_length":
                            placeholder = "%" + arr[i] + "%" + arr[i + 1] + "%";
                            newPlaceholder = String.valueOf(arr[i + 1].length());
                            input = input.replace(placeholder, newPlaceholder);
                            break;
                        case "str_indexof":
                            placeholder = "%" + arr[i] + "%" + arr[i + 1] + "%" + arr[i + 2] + "%";
                            newPlaceholder = String.valueOf(arr[i + 1].indexOf(arr[i + 2]));
                            input = input.replace(placeholder, newPlaceholder);
                            break;
                        case "str_lastindexof":
                            placeholder = "%" + arr[i] + "%" + arr[i + 1] + "%" + arr[i + 2] + "%";
                            newPlaceholder = String.valueOf(arr[i + 1].lastIndexOf(arr[i + 2]));
                            input = input.replace(placeholder, newPlaceholder);
                            break;
                        case "str_equalsignorecase":
                            placeholder = "%" + arr[i] + "%" + arr[i + 1] + "%" + arr[i + 2] + "%";
                            newPlaceholder = String.valueOf(arr[i + 1].equalsIgnoreCase(arr[i + 2]));
                            input = input.replace(placeholder, newPlaceholder);
                            break;
                        case "str_contains":
                            placeholder = "%" + arr[i] + "%" + arr[i + 1] + "%" + arr[i + 2] + "%";
                            newPlaceholder = String.valueOf(arr[i + 1].contains(arr[i + 2]));
                            input = input.replace(placeholder, newPlaceholder);
                            break;
                        case "str_charat":
                            // %str_charAt%Momocraft%3%
                            placeholder = "%" + arr[i] + "%" + arr[i + 1] + "%" + arr[i + 2] + "%";
                            newPlaceholder = String.valueOf(arr[i + 1].charAt(Integer.parseInt(arr[i + 2])));
                            input = input.replace(placeholder, newPlaceholder);
                            break;
                        case "str_substring":
                            // %str_substring%Momocraft%4%10%
                            if (arr.length >= i + 3 && arr[i + 3].matches("[0-9]+")) {
                                placeholder = "%" + arr[i] + "%" + arr[i + 1] + "%" + arr[i + 2] + "%" + arr[i + 3] + "%";
                                newPlaceholder = arr[i + 1].substring(Integer.parseInt(arr[i + 2]), Integer.parseInt(arr[i + 3]));
                            } else {
                                // %str_substring%Momocraft%4%
                                placeholder = "%" + arr[i] + "%" + arr[i + 1] + "%" + arr[i + 2] + "%";
                                newPlaceholder = arr[i + 1].substring(Integer.parseInt(arr[i + 2]));
                            }
                            input = input.replace(placeholder, newPlaceholder);
                            break;
                        case "str_split":
                            stringBuilder = new StringBuilder();
                            // %str_split%1+2+3%+%
                            placeholder = "%" + arr[i] + "%" + arr[i + 1] + "%" + arr[i + 2] + "%";
                            for (String s : arr[i + 1].split(arr[i + 2], -1)) {
                                stringBuilder.append(s);
                            }
                            newPlaceholder = stringBuilder.toString();
                            input = input.replace(placeholder, newPlaceholder);
                            break;
                    }
                } catch (Exception ex) {
                    UtilsHandler.getMsg().sendDebugTrace(ConfigHandler.isDebug(), ConfigHandler.getPlugin(), ex);
                }
            }
        }
        // JavaScript placeholder.
        ScriptEngineManager mgr = new ScriptEngineManager();
        ScriptEngine engine = mgr.getEngineByName("JavaScript");
        while (input.contains("<js>")) {
            split = input.split("<js>");
            back:
            for (int i = 0; i < split.length; i++) {
                if (i == 0)
                    continue;
                if (split[i].contains("</js>")) {
                    placeholder = split[i].substring(0, split[i].indexOf("</js>"));
                    newPlaceholder = "";
                    if (placeholder.contains("<var>")) {// <var>
                        placeholderValue = placeholder.substring(0, placeholder.indexOf("<var>"));
                        splitValues = placeholder.substring(placeholder.indexOf("<var>") + 5).split(",");
                        for (String var : splitValues) {
                            try {
                                engine.eval(placeholderValue);
                                newPlaceholder += engine.get(var).toString() + ", ";
                            } catch (Exception ex) {
                                input = input.replace("<js>" + placeholder + "</js>", "%ERROR%");
                                UtilsHandler.getMsg().sendErrorMsg(pluginName, "An error occurred while converting message: \"" + originInput + "\"");
                                UtilsHandler.getMsg().sendErrorMsg(pluginName, "Not correct format of placeholder: \"<js>" + placeholder + "\"");
                                UtilsHandler.getMsg().sendErrorMsg(pluginName, "More information: https://github.com/momoservertw/CorePlus/wiki/Placeholders");
                                continue back;
                            }
                        }
                        newPlaceholder = newPlaceholder.substring(0, newPlaceholder.length() - 1);
                        newPlaceholder = newPlaceholder.replace("<var>", "");
                        input = input.replace("<js>" + placeholder + "</js>", newPlaceholder);
                    } else {
                        placeholderValue = placeholder;
                        try {
                            newPlaceholder = engine.eval(placeholderValue).toString();
                            input = input.replace("<js>" + placeholder + "</js>", newPlaceholder);
                        } catch (Exception ex) {
                            input = input.replace("<js>" + placeholder + "</js>", "%ERROR%");
                            UtilsHandler.getMsg().sendErrorMsg(pluginName, "An error occurred while converting message: \"" + originInput + "\"");
                            UtilsHandler.getMsg().sendErrorMsg(pluginName, "Not correct format of placeholder: \"<js>" + placeholder + "\"");
                            UtilsHandler.getMsg().sendErrorMsg(pluginName, "More information: https://github.com/momoservertw/CorePlus/wiki/Placeholders");
                        }
                    }
                    continue;
                }
                input = input.replace("<if>" + split[i], "%ERROR%");
                UtilsHandler.getMsg().sendErrorMsg(pluginName, "An error occurred while converting message: \"" + originInput + "\"");
                UtilsHandler.getMsg().sendErrorMsg(pluginName, "Not correct format of placeholder: \"<js>" + split[i] + "\"");
                UtilsHandler.getMsg().sendErrorMsg(pluginName, "More information: https://github.com/momoservertw/CorePlus/wiki/Placeholders");
            }
        }
        // Condition placeholders.
        // <if>value1...value2<and>value3...value4<or>value5...value6</if>
        while (input.contains("<if>")) {
            split = input.split("<if>");
            for (int i = 0; i < split.length; i++) {
                if (i == 0)
                    continue;
                if (split[i].contains("</if>")) {
                    placeholder = split[i].substring(0, split[i].indexOf("</if>"));
                    try {
                        newPlaceholder = String.valueOf(UtilsHandler.getCondition().checkCondition(pluginName, placeholder));
                        input = input.replace("<if>" + placeholder + "</if>", newPlaceholder);
                    } catch (Exception ex) {
                        ex.printStackTrace();
                        input = input.replace("<if>" + placeholder, "%ERROR%");
                        UtilsHandler.getMsg().sendErrorMsg(pluginName, "An error occurred while converting message: \"" + originInput + "\"");
                        UtilsHandler.getMsg().sendErrorMsg(pluginName, "Not correct format of placeholder: \"<if>" + split[i] + "\"");
                        UtilsHandler.getMsg().sendErrorMsg(pluginName, "More information: https://github.com/momoservertw/CorePlus/wiki/Placeholders");
                    }
                    continue;
                }
                input = input.replace("<if>" + split[i], "%ERROR%");
                UtilsHandler.getMsg().sendErrorMsg(pluginName, "An error occurred while converting message: \"" + originInput + "\"");
                UtilsHandler.getMsg().sendErrorMsg(pluginName, "Not correct format of placeholder: \"<if>" + split[i] + "\"");
                UtilsHandler.getMsg().sendErrorMsg(pluginName, "More information: https://github.com/momoservertw/CorePlus/wiki/Placeholders");
            }
        }
        // %TARGET_condition%CONDITION%
        if (input.contains("%condition%")) {
            try {
                String[] arr = input.split("%");
                for (int i = 0; i < arr.length; i++) {
                    if (arr[i].equals("%condition%"))
                        input = input.replace("%condition%" + arr[i + 1] + "%",
                                String.valueOf(UtilsHandler.getCondition().checkCondition(pluginName,
                                        ConfigHandler.getConfigPath().getConditionProp().get(arr[i + 1]))));
                }
            } catch (Exception ex) {
                UtilsHandler.getMsg().sendErrorMsg(pluginName, "An error occurred while converting placeholder: \"" + input + "\"");
                UtilsHandler.getMsg().sendErrorMsg(pluginName, "Not correct format: \"\"%condition%CONDITION%\"");
                UtilsHandler.getMsg().sendErrorMsg(pluginName, "More information: https://github.com/momoservertw/CorePlus/wiki/Placeholders");
                UtilsHandler.getMsg().sendDebugTrace(true, pluginName, ex);
            }
        }
        // PlaceHolderAPI
        if (UtilsHandler.getDepend().PlaceHolderAPIEnabled()) {
            try {
                if (StringUtils.countMatches(input, "%") % 2 == 1) {
                    input = input + "$%";
                    input = PlaceholderAPI.setPlaceholders(null, input);
                    return input.substring(0, input.length() - 2);
                }
                return PlaceholderAPI.setPlaceholders(null, input);
            } catch (NoSuchFieldError e) {
                UtilsHandler.getMsg().sendDebugMsg(true, pluginName,
                        "Error has occurred when setting the PlaceHolder " + e.getMessage() +
                                ", if this issue persist contact the developer of PlaceholderAPI.");
            }
        }
        return input;
    }

    @Override
    public List<String> transLayoutPAPI(String pluginName, List<String> input, Player player) {
        if (input == null || input.isEmpty())
            return null;
        List<String> list = new ArrayList<>();
        for (String value : input)
            list.add(transLayoutPAPI(pluginName, value, player));
        return list;
    }

    @Override
    public String transLayoutPAPI(String pluginName, String input, Player player) {
        if (UtilsHandler.getDepend().PlaceHolderAPIEnabled()) {
            try {
                if (StringUtils.countMatches(input, "%") % 2 == 1) {
                    input = input + "$%";
                    input = PlaceholderAPI.setPlaceholders(player, input);
                    return input.substring(0, input.length() - 2);
                }
                return PlaceholderAPI.setPlaceholders(player, input);
            } catch (NoSuchFieldError e) {
                UtilsHandler.getMsg().sendDebugMsg(true, pluginName,
                        "Error has occurred when setting the PlaceHolder " + e.getMessage() +
                                ", if this issue persist contact the developer of PlaceholderAPI.");
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
        return UtilsHandler.getVanillaUtils().getValinaName(player, input, type);
    }

    @Override
    public String getVanillaTrans(String pluginName, String local, String input, String type) {
        return UtilsHandler.getVanillaUtils().getValinaNode(local, input, type);
    }

    @Override
    public String getVanillaTrans(String pluginName, String input, String type) {
        return UtilsHandler.getVanillaUtils().getValinaNode(null, input, type);
    }

    @Override
    public void addLog(String pluginName, File file, String message, boolean time, boolean newFile, boolean zip) {
        UtilsHandler.getLog().addLog(pluginName, file, message, time, newFile, zip);
    }

    @Override
    public String getPlayersString(List<Player> players) {
        StringBuilder stringBuilder = new StringBuilder();
        Player player;
        int size = players.size();
        for (int i = 0; i < size; i++) {
            player = players.get(i);
            stringBuilder.append(player.getName());
            if (i != size - 1)
                stringBuilder.append(player.getName()).append(", ");
        }
        if (stringBuilder.toString().equals(""))
            return getMsgTrans("noTarget");
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
            if (i != size - 1)
                stringBuilder.append(block.getType().name()).append(", ");
        }
        if (stringBuilder.toString().equals(""))
            return getMsgTrans("noTarget");
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
            if (i != size - 1)
                stringBuilder.append(entity.getType().name()).append(", ");
        }
        if (stringBuilder.toString().equals(""))
            return getMsgTrans("noTarget");
        return stringBuilder.toString();
    }

    @Override
    public void sendHookMsg(String pluginPrefix, String type, List<String> list) {
        if (list == null || list.isEmpty())
            return;
        StringBuilder message = new StringBuilder("&fHooked " + type + ": [");
        for (String value : list) {
            if (type.equals("Residence flags")) {
                if (UtilsHandler.getCondition().isRegisteredFlag(value))
                    message.append(value).append(", ");
            } else {
                message.append(value).append(", ");
            }
        }
        if (!message.toString().equals("&fHooked " + type + ": ["))
            sendConsoleMsg(pluginPrefix, message.substring(0, message.length() - 2) + "]");
    }
}