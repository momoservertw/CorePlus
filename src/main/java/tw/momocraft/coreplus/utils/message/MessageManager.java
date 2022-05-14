package tw.momocraft.coreplus.utils.message;

import me.clip.placeholderapi.PlaceholderAPI;
import net.md_5.bungee.api.chat.TextComponent;
import org.apache.commons.lang.StringUtils;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import tw.momocraft.coreplus.CorePlus;
import tw.momocraft.coreplus.api.MessageInterface;
import tw.momocraft.coreplus.handlers.ConfigHandler;
import tw.momocraft.coreplus.handlers.UtilsHandler;

import java.text.SimpleDateFormat;
import java.util.*;

public class MessageManager implements MessageInterface {

    private final Map<String, String> colorMap = new HashMap<>();

    @Override
    public void sendBroadcastMsg(String prefix, String input) {
        if (input == null)
            return;
        input = setPrefixAndColor(prefix, input);
        Bukkit.broadcastMessage(input);
    }

    @Override
    public void sendBroadcastMsg(String input) {
        if (input == null)
            return;
        input = setColor(input);
        Bukkit.broadcastMessage(input);
    }

    @Override
    public void sendDiscordMsg(String prefix, String channel, String input) {
        if (input == null)
            return;
        input = setPrefixAndColor(prefix, input);
        UtilsHandler.getDepend().getDiscordAPI().sendMsg(channel, input);
    }

    @Override
    public void sendDiscordMsg(String channel, String input) {
        if (input == null)
            return;
        input = setColor(input);
        UtilsHandler.getDepend().getDiscordAPI().sendMsg(channel, input);
    }

    @Override
    public void sendDiscordMsg(String prefix, String channel, String input, Player player) {
        if (input == null)
            return;
        if (player == null)
            return;
        String message = UtilsHandler.getFile().getYaml().getConfig("discord_messages").getString("MinecraftChatToDiscordMessageFormat");
        if (message != null)
            message = message.replace("%message%", input);
        else
            message = input;
        String name = player.getName();
        String displayName = player.getCustomName();
        message = message.replace("%username%", name);
        message = message.replace("%displayname%", displayName != null ? displayName : name);
        message = message.replace("%channelname%", channel);
        input = setPrefixAndColor(prefix, input);
        UtilsHandler.getDepend().getDiscordAPI().sendMsg(input, message);
    }

    @Override
    public void sendDiscordMsg(String channel, String input, Player player) {
        if (input == null)
            return;
        if (player == null)
            return;
        String message = UtilsHandler.getFile().getYaml().getConfig("discord_messages").getString("MinecraftChatToDiscordMessageFormat");
        if (message != null)
            message = message.replace("%message%", input);
        else
            message = input;
        String name = player.getName();
        String displayName = player.getCustomName();
        message = message.replace("%username%", name);
        message = message.replace("%displayname%", displayName != null ? displayName : name);
        message = message.replace("%channelname%", channel);
        input = setColor(input);
        UtilsHandler.getDepend().getDiscordAPI().sendMsg(input, message);
    }

    @Override
    public void sendMsg(String prefix, CommandSender sender, String input) {
        if (input == null)
            return;
        if (sender == null)
            return;
        input = setPrefixAndColor(prefix, input);
        sender.sendMessage(input);
    }

    @Override
    public void sendMsg(CommandSender sender, String input) {
        if (input == null)
            return;
        if (sender == null)
            return;
        input = setColor(input);
        sender.sendMessage(input);
    }

    @Override
    public void sendConsoleMsg(String prefix, String input) {
        if (input == null)
            return;
        input = setPrefixAndColor(prefix, input);
        CorePlus.getInstance().getServer().getConsoleSender().sendMessage(input);
    }

    @Override
    public void sendConsoleMsg(String input) {
        if (input == null)
            return;
        input = setColor(input);
        CorePlus.getInstance().getServer().getConsoleSender().sendMessage(input);
    }

    @Override
    public void sendPlayerMsg(String prefix, Player player, String input) {
        if (input == null)
            return;
        if (player == null)
            return;
        input = setPrefixAndColor(prefix, input);
        player.sendMessage(input);
    }

    @Override
    public void sendPlayerMsg(Player player, String input) {
        if (input == null)
            return;
        if (player == null)
            return;
        input = setColor(input);
        player.sendMessage(input);
    }

    @Override
    public void sendChatMsg(String prefix, Player player, String input) {
        if (input == null)
            return;
        if (player == null)
            return;
        input = setPrefixAndColor(prefix, input);
        player.chat(input);
    }

    @Override
    public void sendChatMsg(Player player, String input) {
        if (input == null)
            return;
        if (player == null)
            return;
        input = setColor(input);
        player.chat(input);
    }


    @Override
    public void sendActionBarMsg(Player player, String input) {
        if (input == null)
            return;
        if (player == null)
            return;
        input = setColor(input);
        player.sendActionBar(TextComponent.fromLegacyText(input));
    }

    @Override
    public void sendTitleMsg(Player player, String input) {
        if (input == null)
            return;
        if (player == null)
            return;
        input = setColor(input);
        String[] args = input.split("\\n");
        sendTitleMsg(player, args[0], args[1], 10, 70, 20);
    }

    @Override
    public void sendTitleMsg(Player player, String inputTitle, String inputSubtitle) {
        if (inputTitle == null && inputSubtitle == null)
            return;
        if (player == null)
            return;
        inputTitle = setColor(inputTitle);
        inputSubtitle = setColor(inputSubtitle);
        sendTitleMsg(player, inputTitle, inputSubtitle, 10, 70, 20);
    }

    @Override
    public void sendTitleMsg(Player player, String inputTitle, String inputSubtitle, int fadeIn, int stay, int fadeOut) {
        if (player == null)
            return;
        inputTitle = setColor(inputTitle);
        inputSubtitle = setColor(inputSubtitle);
        player.sendTitle(inputTitle, inputSubtitle, fadeIn, stay, fadeOut);
    }

    @Override
    public void sendErrorMsg(String pluginName, String input) {
        input = "&4[" + pluginName + "_Error] &e" + input;
        input = setColor(input);
        CorePlus.getInstance().getServer().getConsoleSender().sendMessage(input);
    }

    @Override
    public void sendDebugMsg(boolean isDebug, String pluginName, String input) {
        if (!isDebug)
            return;
        input = "&7[" + pluginName + "_Debug]&r " + input;
        input = setColor(input);
        CorePlus.getInstance().getServer().getConsoleSender().sendMessage(input);
    }

    @Override
    public void sendDebugMsg(String pluginName, String input) {
        input = "&7[" + pluginName + "_Debug]&r " + input;
        input = setColor(input);
        CorePlus.getInstance().getServer().getConsoleSender().sendMessage(input);
    }

    @Override
    public void sendDebugTrace(boolean isDebug, String pluginName, Exception ex) {
        if (!isDebug)
            return;
        sendDebugMsg(pluginName, "showing debug trace.");
        ex.printStackTrace();
    }

    @Override
    public void sendDebugTrace(String pluginName, Exception ex) {
        sendDebugMsg(pluginName, "showing debug trace.");
        ex.printStackTrace();
    }

    @Override
    public void sendDetailMsg(boolean isDebug, String pluginName, String feature, String target, String check, String action, String detail, StackTraceElement ste) {
        if (!isDebug)
            return;
        StringBuilder sb = new StringBuilder();
        sb.append("&f").append(feature).append("&8 - &f").append(target).append(" &8: &f").append(check);
        if (action != null) {
            switch (action) {
                case "cancel", "remove", "kill", "damage", "fail", "failed", "warning", "deny", "prevent" -> sb.append("&8, &c").append(action);
                case "continue", "bypass", "change" -> sb.append("&8, &e").append(action);
                case "return", "success", "succeed", "accept", "allow" -> sb.append("&8, &a").append(action);
            }
        }
        sb.append("&8, &8").append(detail);
        sendDebugMsg(pluginName, sb.toString());
        sendDebugMsg(pluginName,
                "&8(" + ste.getClassName() + " " + ste.getMethodName() + " " + ste.getLineNumber() + ")");
    }


    @Override
    public void sendDetailMsg(String pluginName, String feature, String target, String check, String action, String detail, StackTraceElement ste) {
        StringBuilder sb = new StringBuilder();
        sb.append("&f").append(feature).append("&8 - &f").append(target).append(" &8: &f").append(check);
        if (action != null) {
            switch (action) {
                case "cancel", "remove", "kill", "damage", "fail", "failed", "warning", "deny", "prevent" -> sb.append("&8, &c").append(action);
                case "continue", "bypass", "change" -> sb.append("&8, &e").append(action);
                case "return", "success", "succeed", "accept", "allow" -> sb.append("&8, &a").append(action);
            }
        }
        sb.append("&8, &8").append(detail);
        sendDebugMsg(pluginName, sb.toString());
        sendDebugMsg(pluginName,
                "&8(" + ste.getClassName() + " " + ste.getMethodName() + " " + ste.getLineNumber() + ")");
    }

    @Override
    public void sendDetailMsg(boolean debugging, String pluginName, String feature, String target, String check, String action, StackTraceElement ste) {
        if (!debugging)
            return;
        StringBuilder sb = new StringBuilder();
        sb.append("&f").append(feature).append("&8 - &f").append(target).append(" &8: &f").append(check);
        if (action != null) {
            switch (action) {
                case "cancel", "remove", "kill", "damage", "fail", "failed", "warning", "deny", "prevent" -> sb.append("&8, &c").append(action);
                case "continue", "bypass", "change" -> sb.append("&8, &e").append(action);
                case "return", "success", "succeed", "accept", "allow" -> sb.append("&8, &a").append(action);
            }
        }
        sendDebugMsg(pluginName, sb.toString());
        sendDebugMsg(pluginName,
                "&8(" + ste.getClassName() + " " + ste.getMethodName() + " " + ste.getLineNumber() + ")");
    }

    @Override
    public void sendDetailMsg(String pluginName, String feature, String target, String check, String action, StackTraceElement ste) {
        StringBuilder sb = new StringBuilder();
        sb.append("&f").append(feature).append("&8 - &f").append(target).append(" &8: &f").append(check);
        if (action != null) {
            switch (action) {
                case "cancel", "remove", "kill", "damage", "fail", "failed", "warning", "deny", "prevent" -> sb.append("&8, &c").append(action);
                case "continue", "bypass", "change" -> sb.append("&8, &e").append(action);
                case "return", "success", "succeed", "accept", "allow" -> sb.append("&8, &a").append(action);
            }
        }
        sendDebugMsg(pluginName, sb.toString());
        sendDebugMsg(pluginName,
                "&8(" + ste.getClassName() + " " + ste.getMethodName() + " " + ste.getLineNumber() + ")");
    }

    @Override
    public void sendLangMsg(String input, CommandSender sender, String... langHolder) {
        if (input == null)
            return;
        if (sender == null)
            return;
        if (input.startsWith("Message.")) {
            String langMessage = ConfigHandler.getConfig("message.yml").getString(input);
            if (langMessage != null) {
                input = langMessage;
            } else {
                sendErrorMsg(ConfigHandler.getPluginName(), "Can not find message \"" + input + "\" in message.yml.");
                return;
            }
        }
        input = transLang(sender, input, langHolder);
        input = setColor(input);
        if (!input.contains(" \n ") && !input.contains("{n}")) {
            sender.sendMessage(input);
            return;
        }
        List<String> list = new ArrayList<>();
        list.addAll(Arrays.asList(input.split(" \\n ")));
        list.addAll(Arrays.asList(input.split("\\{n}")));
        for (String langLine : list)
            sender.sendMessage(langLine);
    }

    @Override
    public void sendLangMsg(String prefix, String input, CommandSender sender, String... langHolder) {
        if (input == null)
            return;
        if (sender == null)
            return;
        if (input.startsWith("Message.")) {
            String langMessage = ConfigHandler.getConfig("message.yml").getString(input);
            if (langMessage != null) {
                input = langMessage;
            } else {
                sendErrorMsg(ConfigHandler.getPluginName(), "Can not find message \"" + input + "\" in message.yml.");
                return;
            }
        }
        input = transLang(sender, input, langHolder);
        input = setPrefixAndColor(prefix, input);
        if (!input.contains(" \n ") && !input.contains("{n}")) {
            sender.sendMessage(input);
            return;
        }
        List<String> list = new ArrayList<>();
        list.addAll(Arrays.asList(input.split(" \\n ")));
        list.addAll(Arrays.asList(input.split("\\{n}")));
        for (String langLine : list)
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
    public List<String> transLang(List<String> input, String... langHolder) {
        List<String> list = new ArrayList<>();
        String local = UtilsHandler.getVanillaUtils().getLocal();
        for (String value : input)
            list.add(transLang(local, value, langHolder));
        return list;
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
    public String transLang(String input, String... langHolder) {
        return transLang(UtilsHandler.getVanillaUtils().getLocal(), input, langHolder);
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
            return null;
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
                .replace("%targetplayer%", langHolder[1])
                .replace("%plugin%", langHolder[2])
                //.replace("%prefix%", langHolder[3])
                .replace("%value%", langHolder[4])
                .replace("%group%", langHolder[5])
                .replace("%amount%", langHolder[6])
                .replace("%material%", langHolder[7])
                .replace("%entity%", langHolder[8])
                .replace("%price%", getMsgTrans(langHolder[9]))
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
    public List<String> transHolder(String pluginName, Map<String, Object> targetMap, List<String> input) {
        if (input == null)
            return null;
        if (targetMap == null)
            return input;
        TranslateMap translateMap = new TranslateMap();
        String objectType;
        Map<String, Object> objectMap = translateMap.getObjectMap();
        for (Map.Entry<String, Object> entry : targetMap.entrySet()) {
            translateMap.putObjectMap(entry.getKey(), entry.getValue());
            objectType = UtilsHandler.getUtil().getObjectType(entry.getValue());
            if (objectMap.get(objectType) == null)
                translateMap.putObjectMap(objectType, entry.getValue());
        }
        List<String> list = new ArrayList<>();
        for (String value : input)
            list.add(getConvertObjects(pluginName, translateMap, value));
        return list;
    }

    @Override
    public List<String> transHolder(String pluginName, Object trigger, List<Object> targets, List<String> input) {
        if (input == null)
            return null;
        if (trigger == null && targets == null)
            return input;
        TranslateMap translateMap = new TranslateMap();
        String objectType;
        Map<String, Object> objectMap = translateMap.getObjectMap();
        if (trigger != null) {
            translateMap.putObjectMap("trigger", trigger);
            objectType = UtilsHandler.getUtil().getObjectType(trigger);
            if (objectMap.get(objectType) == null)
                translateMap.putObjectMap(objectType, trigger);
        }
        if (targets != null) {
            Object target;
            for (int i = 1; i < targets.size(); i++) {
                target = targets.get(i);
                if (target != null) {
                    translateMap.putObjectMap("target_" + i, target);
                    objectType = UtilsHandler.getUtil().getObjectType(target);
                    if (objectMap.get(objectType) == null)
                        translateMap.putObjectMap(objectType, target);
                }
            }
        }
        List<String> list = new ArrayList<>();
        for (String value : input)
            list.add(getConvertObjects(pluginName, translateMap, value));
        return list;
    }

    @Override
    public String transHolder(String pluginName, Object trigger, List<Object> targets, String input) {
        if (input == null)
            return null;
        if (trigger == null && targets == null)
            return input;
        TranslateMap translateMap = new TranslateMap();
        String objectType;
        Map<String, Object> objectMap = translateMap.getObjectMap();
        if (trigger != null) {
            translateMap.putObjectMap("trigger", trigger);
            objectType = UtilsHandler.getUtil().getObjectType(trigger);
            if (objectMap.get(objectType) == null)
                translateMap.putObjectMap(objectType, trigger);
        }
        if (targets != null) {
            Object target;
            for (int i = 1; i < targets.size(); i++) {
                target = targets.get(i);
                if (target != null) {
                    translateMap.putObjectMap("target_" + i, target);
                    objectType = UtilsHandler.getUtil().getObjectType(target);
                    if (objectMap.get(objectType) == null)
                        translateMap.putObjectMap(UtilsHandler.getUtil().getObjectType(target), target);
                }
            }
        }
        return getConvertObjects(pluginName, translateMap, input);
    }


    @Override
    public List<String> transHolder(String pluginName, Object trigger, Object target, List<String> input) {
        if (input == null)
            return null;
        if (trigger == null && target == null)
            return input;
        TranslateMap translateMap = new TranslateMap();
        String objectType;
        Map<String, Object> objectMap = translateMap.getObjectMap();
        if (trigger != null) {
            translateMap.putObjectMap("trigger", trigger);
            objectType = UtilsHandler.getUtil().getObjectType(trigger);
            if (objectMap.get(objectType) == null)
                translateMap.putObjectMap(objectType, trigger);
        }
        if (target != null) {
            translateMap.putObjectMap("target", target);
            objectType = UtilsHandler.getUtil().getObjectType(target);
            if (objectMap.get(objectType) == null)
                translateMap.putObjectMap(UtilsHandler.getUtil().getObjectType(target), target);
        }
        List<String> list = new ArrayList<>();
        for (String value : input)
            list.add(getConvertObjects(pluginName, translateMap, value));
        return list;
    }

    @Override
    public String transHolder(String pluginName, Object trigger, Object target, String input) {
        if (input == null)
            return null;
        if (trigger == null && target == null)
            return input;
        TranslateMap translateMap = new TranslateMap();
        String objectType;
        Map<String, Object> objectMap = translateMap.getObjectMap();
        if (trigger != null) {
            translateMap.putObjectMap("trigger", trigger);
            objectType = UtilsHandler.getUtil().getObjectType(trigger);
            if (objectMap.get(objectType) == null)
                translateMap.putObjectMap(objectType, trigger);
        }
        if (target != null) {
            translateMap.putObjectMap("target", target);
            objectType = UtilsHandler.getUtil().getObjectType(target);
            if (objectMap.get(objectType) == null)
                translateMap.putObjectMap(UtilsHandler.getUtil().getObjectType(target), target);
        }
        return getConvertObjects(pluginName, translateMap, input);
    }

    @Override
    public List<String> transHolder(String pluginName, Object trigger, List<String> input) {
        if (input == null || input.isEmpty())
            return null;
        if (trigger == null)
            return input;
        TranslateMap translateMap = new TranslateMap();
        String objectType;
        Map<String, Object> objectMap = translateMap.getObjectMap();
        translateMap.putObjectMap("trigger", trigger);
        objectType = UtilsHandler.getUtil().getObjectType(trigger);
        if (objectMap.get(objectType) == null)
            translateMap.putObjectMap(objectType, trigger);
        List<String> list = new ArrayList<>();
        for (String value : input)
            list.add(getConvertObjects(pluginName, translateMap, value));
        return list;
    }

    @Override
    public String transHolder(String pluginName, Object trigger, String input) {
        if (input == null)
            return null;
        if (trigger == null)
            return input;
        TranslateMap translateMap = new TranslateMap();
        String objectType;
        Map<String, Object> objectMap = translateMap.getObjectMap();
        translateMap.putObjectMap("trigger", trigger);
        objectType = UtilsHandler.getUtil().getObjectType(trigger);
        if (objectMap.get(objectType) == null)
            translateMap.putObjectMap(objectType, trigger);
        return getConvertObjects(pluginName, translateMap, input);
    }

    @Override
    public String transHolder(String pluginName, String input) {
        if (input == null)
            return null;
        TranslateMap translateMap = new TranslateMap();
        translateMap.putObjectMap("console", Bukkit.getConsoleSender());
        return getConvertObjects(pluginName, translateMap, input);
    }

    public String getConvertObjects(String pluginName, TranslateMap translateMap, String input) {
        Map<String, Object> map = translateMap.getObjectMap();
        String lang = translateMap.getLang();
        Object target;
        for (String prefix : map.keySet()) {
            target = map.get(prefix);
            input = getConvertPAPI(pluginName, input);
            if (target instanceof Player) {
                Player player = (Player) target;
                input = getConvertPlayer(pluginName, prefix, player, input);
                input = getConvertEntity(pluginName, prefix, player, input);
                input = getConvertEntityType(pluginName, lang, prefix, player.getType(), input);
                input = getConvertLocation(pluginName, prefix, player.getLocation(), input);
                input = getConvertPAPI(pluginName, player, input);
            } else if (target instanceof OfflinePlayer) {
                OfflinePlayer player = (OfflinePlayer) target;
                input = getConvertOfflinePlayer(pluginName, prefix, player, input);
            } else if (target instanceof Entity) {
                Entity entity = (Entity) target;
                input = getConvertEntity(pluginName, prefix, entity, input);
                input = getConvertEntityType(pluginName, lang, prefix, entity.getType(), input);
                input = getConvertLocation(pluginName, prefix, entity.getLocation(), input);
            } else if (target instanceof EntityType) {
                input = getConvertEntityType(pluginName, lang, prefix, (EntityType) target, input);
            } else if (target instanceof Block) {
                Block block = (Block) target;
                input = getConvertBlock(pluginName, prefix, block, input);
                input = getConvertMaterial(pluginName, lang, prefix, block.getType(), input);
                input = getConvertLocation(pluginName, prefix, block.getLocation(), input);
            } else if (target instanceof ItemStack) {
                ItemStack itemStack = (ItemStack) target;
                input = getConvertItemStack(pluginName, prefix, itemStack, input);
                input = getConvertMaterial(pluginName, lang, prefix, itemStack.getType(), input);
            } else if (target instanceof Material) {
                Material material = (Material) target;
                input = getConvertMaterial(pluginName, lang, prefix, material, input);
            } else if (target instanceof Location) {
                Location location = (Location) target;
                input = getConvertLocation(pluginName, prefix, location, input);
            }
            input = getConvertGeneral(pluginName, input);
        }
        return input;
    }

    private String getConvertPlayer(String pluginName, String prefix, Player target, String input) {
        if (target == null) {
            // %TARGET_has_played%
            input = input.replace("%" + prefix + "_has_played%", "false");
            return input;
        }
        // %TARGET_last_login%
        input = input.replace("%" + prefix + "_last_login%", String.valueOf(target.getLastLogin()));
        // %TARGET_display_name%
        input = input.replace("%" + prefix + "_display_name%", target.getDisplayName());
        // %TARGET_money%
        UUID uuid = target.getUniqueId();
        if (UtilsHandler.getDepend().VaultEnabled())
            try {
                if (input.contains("%" + prefix + "_money%"))
                    input = input.replace("%" + prefix + "_money%",
                            String.valueOf(UtilsHandler.getDepend().getVaultAPI().getBalance(uuid)));
            } catch (Exception ex) {
                UtilsHandler.getMsg().sendErrorMsg(pluginName, "Can not convert placeholder: " + input);
                UtilsHandler.getMsg().sendErrorMsg(pluginName, "Format: %TARGET_money%");
                UtilsHandler.getMsg().sendDebugTrace(pluginName, ex);
            }
        // %TARGET_points%
        if (UtilsHandler.getDepend().PlayerPointsEnabled())
            try {
                if (input.contains("%" + prefix + "_points%"))
                    input = input.replace("%" + prefix + "_points%",
                            String.valueOf(UtilsHandler.getDepend().getPlayerPointsAPI().getBalance(uuid)));
            } catch (Exception ex) {
                UtilsHandler.getMsg().sendErrorMsg(pluginName, "Can not convert placeholder: " + input);
                UtilsHandler.getMsg().sendErrorMsg(pluginName, "Format: %TARGET_points%");
                UtilsHandler.getMsg().sendDebugTrace(pluginName, ex);
            }
        // %TARGET_gamemode%
        input = input.replace("%" + prefix + "_gamemode%", target.getGameMode().name());
        // %TARGET_sneaking%
        input = input.replace("%" + prefix + "_sneaking%", String.valueOf(target.isSneaking()));
        // %TARGET_flying%
        input = input.replace("%" + prefix + "_flying%", String.valueOf(target.isFlying()));
        // %TARGET_perm%PERMISSION%
        if (input.contains("%" + prefix + "_perm%")) {
            try {
                String[] arr = input.split("%");
                for (int i = 0; i < arr.length; i++)
                    if (arr[i].equals(prefix + "_perm"))
                        input = input.replace("%" + prefix + "_perm%" + arr[i + 1] + "%",
                                String.valueOf(UtilsHandler.getPlayer().hasPerm(target, arr[i + 1])));
            } catch (Exception ex) {
                UtilsHandler.getMsg().sendErrorMsg(pluginName, "Can not convert placeholder: " + input);
                UtilsHandler.getMsg().sendErrorMsg(pluginName, "Format:%TARGET_perm%PERMISSION%");
                UtilsHandler.getMsg().sendDebugTrace(pluginName, ex);
            }
        }
        return input;
    }

    private String getConvertEntity(String pluginName, String prefix, Entity target, String input) {
        String name = target.getName();
        String displayName = target.getCustomName();
        // %TARGET%
        input = input.replace("%" + prefix + "%", name);
        // %TARGET_name%
        input = input.replace("%" + prefix + "_name%", name);
        // %TARGET_display_name%
        input = input.replace("%" + prefix + "_display_name%", displayName != null ? displayName : name);
        // %TARGET_has_custom_name%
        input = input.replace("%" + prefix + "_has_custom_name%", displayName != null ? "true" : "false");
        // %TARGET_uuid%
        UUID uuid = target.getUniqueId();
        input = input.replace("%" + prefix + "_uuid%", uuid.toString());
        return input;
    }

    private String getConvertEntityType(String pluginName, String local, String prefix, EntityType target, String input) {
        String type = target.name();
        // %TARGET%
        input = input.replace("%" + prefix + "%", type);
        // %TARGET_name%
        input = input.replace("%" + prefix + "_name%", type);

        // %TARGET_type%
        input = input.replace("%" + prefix + "_type%", type);
        // %TARGET_type_local%
        input = input.replace("%" + prefix + "_type_local%", getVanillaTrans(local, type, "entity"));
        return input;
    }

    private String getConvertOfflinePlayer(String pluginName, String prefix, OfflinePlayer target, String input) {
        String name = target.getName();
        if (name != null) {
            // %TARGET%
            input = input.replace("%" + prefix + "%", name);
            // %TARGET_name%
            input = input.replace("%" + prefix + "_name%", name);
        }
        // %TARGET_has_played%
        input = input.replace("%" + prefix + "_has_played%", "false");
        // %TARGET_uuid%
        input = input.replace("%" + prefix + "_uuid%", target.getUniqueId().toString());
        // %TARGET_last_login%
        boolean hasPlayed = target.hasPlayedBefore();
        input = input.replace("%" + prefix + "_last_login%",
                hasPlayed ? String.valueOf(target.getLastLogin()) : getMsgTrans("noData"));
        // %TARGET_has_played%
        input = input.replace("%" + prefix + "_has_played%", String.valueOf(hasPlayed));
        // %TARGET_perm%PERMISSION%
        if (input.contains("%" + prefix + "_perm%")) {
            try {
                String[] arr = input.split("%");
                for (int i = 0; i < arr.length; i++)
                    if (arr[i].equals(prefix + "_perm"))
                        input = input.replace("%" + prefix + "_perm%" + arr[i + 1] + "%",
                                String.valueOf(UtilsHandler.getPlayer().hasPerm(target, arr[i + 1])));
            } catch (Exception ex) {
                UtilsHandler.getMsg().sendErrorMsg(pluginName, "Can not convert placeholder: " + input);
                UtilsHandler.getMsg().sendErrorMsg(pluginName, "Format: %TARGET_perm%PERMISSION%");
                UtilsHandler.getMsg().sendDebugTrace(pluginName, ex);
            }
        }
        return input;
    }

    private String getConvertItemStack(String pluginName, String prefix, ItemStack target, String input) {
        String type = target.getType().name();
        // %TARGET%
        input = input.replace("%" + prefix + "%", type);
        // %TARGET_name%
        input = input.replace("%" + prefix + "_name%", type);

        // %TARGET_type%
        input = input.replace("%" + prefix + "_type%", type);
        // %TARGET_display_name%
        ItemMeta itemMeta = target.getItemMeta();
        String displayName = null;
        if (itemMeta != null)
            displayName = target.getItemMeta().getDisplayName();
        try {
            input = input.replace("%" + prefix + "_display_name%", displayName != null ? displayName : target.getType().name());
        } catch (Exception ex) {
            UtilsHandler.getMsg().sendErrorMsg(pluginName, "Can not convert placeholder: " + input);
            UtilsHandler.getMsg().sendErrorMsg(pluginName, "Format: %TARGET_display_name%");
            UtilsHandler.getMsg().sendDebugTrace(pluginName, ex);
        }
        // %TARGET_has_custom_name%
        input = input.replace("%" + prefix + "_has_custom_name%", displayName != null ? "true" : "false");
        // %TARGET_amount%
        try {
            input = input.replace("%" + prefix + "_amount%", String.valueOf(target.getAmount()));
        } catch (Exception ex) {
            UtilsHandler.getMsg().sendErrorMsg(pluginName, "Can not convert placeholder: " + input);
            UtilsHandler.getMsg().sendErrorMsg(pluginName, "Format: %TARGET_amount%");
            UtilsHandler.getMsg().sendDebugTrace(pluginName, ex);
        }
        return input;
    }

    private String getConvertBlock(String pluginName, String prefix, Block target, String input) {
        String type = target.getType().name();
        // %TARGET%
        input = input.replace("%" + prefix + "%", type);
        // %TARGET_name%
        input = input.replace("%" + prefix + "_name%", type);

        // %TARGET_type%
        input = input.replace("%" + prefix + "_type%", type);
        return input;
    }

    private String getConvertMaterial(String pluginName, String local, String prefix, Material target, String input) {
        String type = target.name();
        // %TARGET%
        input = input.replace("%" + prefix + "%", type);
        // %TARGET_name%
        input = input.replace("%" + prefix + "_name%", type);

        // %TARGET_type%
        input = input.replace("%" + prefix + "_type%", type);
        // %TARGET_type_local%
        try {
            input = input.replace("%" + prefix + "_type_local%", getVanillaTrans(pluginName, local, type, "material"));
        } catch (Exception ex) {
            UtilsHandler.getMsg().sendErrorMsg(pluginName, "Can not convert placeholder: " + input);
            UtilsHandler.getMsg().sendErrorMsg(pluginName, "Format: %TARGET_type_local%");
            UtilsHandler.getMsg().sendDebugTrace(pluginName, ex);
        }
        return input;
    }

    private String getConvertLocation(String pluginName, String prefix, Location target, String input) {
        World world = target.getWorld();
        Block block = target.getBlock();
        // %TARGET_world%
        if (input.contains("%" + prefix + "_world%"))
            input = input.replace("%" + prefix + "_world%", world.getName());
        // %TARGET_loc%
        // %TARGET_loc_x%, %TARGET_loc_y%, %TARGET_loc_z%
        // %TARGET_loc_x_NUMBER%, %TARGET_loc_y_NUMBER%, %TARGET_loc_z_NUMBER%
        if (input.contains("%" + prefix + "_loc")) {
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
            input = input.replace("%" + prefix + "_loc%", loc_x + ", " + loc_y + ", " + loc_z);
            input = input.replace("%" + prefix + "_loc_x%", loc_x);
            input = input.replace("%" + prefix + "_loc_y%", loc_y);
            input = input.replace("%" + prefix + "_loc_z%", loc_z);
        }
        // %TARGET_cave%
        if (input.contains("%" + prefix + "_cave%")) {
            try {
                Location blockLoc;
                back:
                for (int x = -3; x <= 3; x++)
                    for (int z = -3; z <= 3; z++)
                        for (int y = -3; y <= 3; y++) {
                            blockLoc = target.clone().add(x, y, z);
                            if (Material.CAVE_AIR.equals(blockLoc.getBlock().getType())) {
                                input = input.replace("%" + prefix + "_cave%", "true");
                                continue back;
                            }
                        }
            } catch (Exception ex) {
                UtilsHandler.getMsg().sendErrorMsg(pluginName, "Can not convert placeholder: " + input);
                UtilsHandler.getMsg().sendErrorMsg(pluginName, "Format: %TARGET_cave%");
                UtilsHandler.getMsg().sendDebugTrace(pluginName, ex);
            }
        }
        // %TARGET_loc_material%
        if (input.contains("%" + prefix + "_loc_material%"))
            input = input.replace("%" + prefix + "_material%", block.getType().name());
        // %TARGET_world_time%
        if (input.contains("%" + prefix + "_world_time%"))
            input = input.replace("%" + prefix + "_world_time%", String.valueOf(world.getTime()));
        // %TARGET_biome%
        if (input.contains("%" + prefix + "_biome%"))
            input = input.replace("%" + prefix + "_biome%", block.getBiome().name());
        // %TARGET_light%
        if (input.contains("%" + prefix + "_light%"))
            input = input.replace("%" + prefix + "_light%", String.valueOf(block.getLightLevel()));
        // %TARGET_liquid%
        if (input.contains("%" + prefix + "_liquid%"))
            input = input.replace("%" + prefix + "_liquid%", String.valueOf(block.isLiquid()));
        // %TARGET_solid%
        if (input.contains("%" + prefix + "_solid%"))
            input = input.replace("%" + prefix + "_solid%", String.valueOf(block.getType().isSolid()));
        // %TARGET_cave%
        if (input.contains("%" + prefix + "_cave%"))
            input = input.replace("%" + prefix + "_cave%", String.valueOf(UtilsHandler.getCondition().isInCave(target)));
        // %TARGET_outside%
        if (input.contains("%" + prefix + "_outside%"))
            input = input.replace("%" + prefix + "_outside%", String.valueOf(UtilsHandler.getCondition().isInOutside(target)));
        // %TARGET_ground%
        if (input.contains("%" + prefix + "_ground%"))
            input = input.replace("%" + prefix + "_ground%", String.valueOf(UtilsHandler.getCondition().isOnGround(target)));
        // %TARGET_residence%
        if (input.contains("%" + prefix + "_residence%")) {
            String residenceName = UtilsHandler.getCondition().getResidenceName(target);
            input = input.replace("%" + prefix + "_residence%", residenceName != null ? residenceName : "");
        }
        // %TARGET_in_residence%
        if (input.contains("%" + prefix + "_in_residence%"))
            input = input.replace("%" + prefix + "_in_residence%",
                    String.valueOf(UtilsHandler.getCondition().isInResidence(target)));
        // %TARGET_location%LOCATION%
        if (input.contains("%" + prefix + "_location%")) {
            try {
                String[] arr = input.split("%");
                for (int i = 0; i < arr.length; i++) {
                    if (arr[i].equals(prefix + "_location"))
                        input = input.replace("%" + prefix + "_location%" + arr[i + 1] + "%",
                                String.valueOf(UtilsHandler.getCondition().checkLocation(ConfigHandler.getPluginName(), target, arr[i + 1], false)));
                }
            } catch (Exception ex) {
                UtilsHandler.getMsg().sendErrorMsg(pluginName, "Can not convert placeholder: " + input);
                UtilsHandler.getMsg().sendErrorMsg(pluginName, "Format: %TARGET_location%LOCATION%");
                UtilsHandler.getMsg().sendDebugTrace(pluginName, ex);
            }
        }
        // %TARGET_blocks%BLOCKS%
        if (input.contains("%" + prefix + "_blocks%")) {
            try {
                String[] arr = input.split("%");
                for (int i = 0; i < arr.length; i++) {
                    if (arr[i].equals(prefix + "_blocks"))
                        input = input.replace("%" + prefix + "_blocks%" + arr[i + 1] + "%",
                                String.valueOf(UtilsHandler.getCondition().checkBlocks(target, arr[i + 1], false)));
                }
            } catch (Exception ex) {
                UtilsHandler.getMsg().sendErrorMsg(pluginName, "Can not convert placeholder: " + input);
                UtilsHandler.getMsg().sendErrorMsg(pluginName, "Format: %TARGET_location%LOCATION%");
                UtilsHandler.getMsg().sendDebugTrace(pluginName, ex);
            }
        }
        // %TARGET_nearby%type%name/type%group%radius%
        // arr[0]=Target_nearby, arr[1]=Type, arr[2]=Name/Type, arr[3]=Group, arr[4]=Radius
        if (input.contains("%" + prefix + "_nearby%")) {
            try {
                String[] arr = input.split("%");
                String nearbyString;
                for (int i = 0; i < arr.length; i++) {
                    if (arr[i].equals(prefix + "_nearby")) {
                        nearbyString = UtilsHandler.getUtil().getNearbyListString(ConfigHandler.getPluginName(), target,
                                arr[i + 1], arr[i + 2], arr[i + 3], Integer.parseInt(arr[i + 4]));
                        if (nearbyString == null) {
                            input = input.replace("%" + arr[i] + "%" + arr[i + 1] + "%" + arr[i + 2] +
                                    "%" + arr[i + 3] + "%" + arr[i + 4] + "%", UtilsHandler.getMsg().getMsgTrans("noTargets"));
                            continue;
                        }
                        input = input.replace("%" + arr[i] + "%" + arr[i + 1] + "%" + arr[i + 2] +
                                "%" + arr[i + 3] + "%" + arr[i + 4] + "%", nearbyString);
                        i += 4;
                    }
                }
            } catch (Exception ex) {
                UtilsHandler.getMsg().sendErrorMsg(pluginName, "Can not convert placeholder: " + input);
                UtilsHandler.getMsg().sendErrorMsg(pluginName, "Format: %TARGET_nearby%type%name/type%group%radius%");
                UtilsHandler.getMsg().sendDebugTrace(pluginName, ex);
            }
        }
        return input;
    }

    public String getConvertGeneral(String pluginName, String input) {
        // Color codes
        input = ChatColor.translateAlternateColorCodes('&', input);
        // Symbol
        input = input.replace("{s}", " ");
        input = input.replace("{e}", "");
        // %localtime_time% => 2020/08/08 12:30:00
        input = input.replace("%localtime_time%", new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(new Date()));
        // %random_number%500%
        if (input.contains("%random_number%")) {
            try {
                String[] arr = input.split("%");
                for (int i = 0; i < arr.length; i++)
                    if (arr[i].equals("random_number"))
                        input = input.replace("%random_number%" + arr[i + 1] + "%",
                                String.valueOf(new Random().nextInt(Integer.parseInt(arr[i + 1]))));
            } catch (Exception ex) {
                UtilsHandler.getMsg().sendErrorMsg(pluginName, "Can not convert placeholder: " + input);
                UtilsHandler.getMsg().sendErrorMsg(pluginName, "Format: %random_number%");
                UtilsHandler.getMsg().sendDebugTrace(pluginName, ex);
            }
        }
        // %random_list%String1,String2%
        if (input.contains("%random_list%")) {
            try {
                List<String> placeholderList = new ArrayList<>();
                String[] arr = input.split("%");
                for (int i = 0; i < arr.length; i++)
                    if (arr[i].equals("random_list"))
                        placeholderList.add((arr[i + 1]));
                String[] stringArr;
                String randomString;
                for (String placeholderValue : placeholderList) {
                    stringArr = placeholderValue.split(",");
                    randomString = stringArr[new Random().nextInt(stringArr.length)];
                    input = input.replace("%random_list%" + placeholderValue + "%", randomString);
                }
            } catch (Exception ex) {
                UtilsHandler.getMsg().sendErrorMsg(pluginName, "Can not convert placeholder: " + input);
                UtilsHandler.getMsg().sendErrorMsg(pluginName, "Format: %random_number%");
                UtilsHandler.getMsg().sendDebugTrace(pluginName, ex);
            }
        }
        // %random_player%
        if (input.contains("%random_player%")) {
            try {
                List<Player> playerList = new ArrayList(Bukkit.getOnlinePlayers());
                String randomPlayer = playerList.get(new Random().nextInt(playerList.size())).getName();
                input = input.replace("%random_player%", randomPlayer);
            } catch (Exception ex) {
                UtilsHandler.getMsg().sendErrorMsg(pluginName, "Can not convert placeholder: " + input);
                UtilsHandler.getMsg().sendErrorMsg(pluginName, "Format: %random_player%");
                UtilsHandler.getMsg().sendDebugTrace(pluginName, ex);
            }
        }
        // %random_player_except%Player1,Player2%
        if (input.contains("%random_player_except%")) {
            try {
                List<String> placeholderList = new ArrayList<>();
                List<Player> playerList = new ArrayList(Bukkit.getOnlinePlayers());
                String[] arr = input.split("%");
                for (int i = 0; i < arr.length; i++)
                    if (arr[i].equals("random_player_except"))
                        placeholderList.add((arr[i + 1]));
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
                        if (!Arrays.asList(playerArr).contains(randomPlayerName)) {
                            String newList = placeholderList.toString().replaceAll("[\\[\\]\\s]", "");
                            input = input.replace("%random_player_except%" + newList + "%", randomPlayerName);
                            break;
                        }
                    }
                }
            } catch (Exception ex) {
                UtilsHandler.getMsg().sendErrorMsg(pluginName, "Can not convert placeholder: " + input);
                UtilsHandler.getMsg().sendErrorMsg(pluginName, "Format: %random_player_except%Player1,Player2%");
                UtilsHandler.getMsg().sendDebugTrace(pluginName, ex);
            }
        }
        String placeholder;
        StringBuilder newPlaceholder;
        String placeholderValue;
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
                            newPlaceholder = new StringBuilder(arr[i + 1].replace(arr[i + 2], arr[i + 3]));
                            input = input.replace(placeholder, newPlaceholder.toString());
                            break;
                        case "str_endswith":
                            // %str_endswith%Momocraft%aft%
                            placeholder = "%" + arr[i] + "%" + arr[i + 1] + "%" + arr[i + 2] + "%";
                            newPlaceholder = new StringBuilder(String.valueOf(arr[i + 1].endsWith(arr[i + 2])));
                            input = input.replace(placeholder, newPlaceholder.toString());
                            break;
                        case "str_startswith":
                            // %str_startswith%Momocraft%Momo%4%
                            if (arr.length >= i + 3 && arr[i + 3].matches("[0-9]+")) {
                                placeholder = "%" + arr[i] + "%" + arr[i + 1] + "%" + arr[i + 2] + "%" + arr[i + 3] + "%";
                                newPlaceholder = new StringBuilder(String.valueOf(arr[i + 1].startsWith(arr[i + 2], Integer.parseInt(arr[i + 3]))));
                            } else {
                                // %str_startswith%Momocraft%Momo%
                                placeholder = "%" + arr[i] + "%" + arr[i + 1] + "%" + arr[i + 2] + "%";
                                newPlaceholder = new StringBuilder(String.valueOf(arr[i + 1].startsWith(arr[i + 2])));
                            }
                            input = input.replace(placeholder, newPlaceholder.toString());
                            break;
                        case "str_matches":
                            // %str_matches%Momocraft%[a-zA-Z]+%
                            placeholder = "%" + arr[i] + "%" + arr[i + 1] + "%" + arr[i + 2] + "%";
                            newPlaceholder = new StringBuilder(String.valueOf(arr[i + 1].matches(arr[i + 2])));
                            input = input.replace(placeholder, newPlaceholder.toString());
                            break;
                        case "str_tolowercase":
                            placeholder = "%" + arr[i] + "%" + arr[i + 1] + "%";
                            newPlaceholder = new StringBuilder(arr[i + 1].toLowerCase());
                            input = input.replace(placeholder, newPlaceholder.toString());
                            break;
                        case "str_touppercase":
                            placeholder = "%" + arr[i] + "%" + arr[i + 1] + "%";
                            newPlaceholder = new StringBuilder(arr[i + 1].toUpperCase());
                            input = input.replace(placeholder, newPlaceholder.toString());
                            break;
                        case "str_length":
                            placeholder = "%" + arr[i] + "%" + arr[i + 1] + "%";
                            newPlaceholder = new StringBuilder(String.valueOf(arr[i + 1].length()));
                            input = input.replace(placeholder, newPlaceholder.toString());
                            break;
                        case "str_indexof":
                            placeholder = "%" + arr[i] + "%" + arr[i + 1] + "%" + arr[i + 2] + "%";
                            newPlaceholder = new StringBuilder(String.valueOf(arr[i + 1].indexOf(arr[i + 2])));
                            input = input.replace(placeholder, newPlaceholder.toString());
                            break;
                        case "str_lastindexof":
                            placeholder = "%" + arr[i] + "%" + arr[i + 1] + "%" + arr[i + 2] + "%";
                            newPlaceholder = new StringBuilder(String.valueOf(arr[i + 1].lastIndexOf(arr[i + 2])));
                            input = input.replace(placeholder, newPlaceholder.toString());
                            break;
                        case "str_equalsignorecase":
                            placeholder = "%" + arr[i] + "%" + arr[i + 1] + "%" + arr[i + 2] + "%";
                            newPlaceholder = new StringBuilder(String.valueOf(arr[i + 1].equalsIgnoreCase(arr[i + 2])));
                            input = input.replace(placeholder, newPlaceholder.toString());
                            break;
                        case "str_contains":
                            placeholder = "%" + arr[i] + "%" + arr[i + 1] + "%" + arr[i + 2] + "%";
                            newPlaceholder = new StringBuilder(String.valueOf(arr[i + 1].contains(arr[i + 2])));
                            input = input.replace(placeholder, newPlaceholder.toString());
                            break;
                        case "str_charat":
                            // %str_charAt%Momocraft%3%
                            placeholder = "%" + arr[i] + "%" + arr[i + 1] + "%" + arr[i + 2] + "%";
                            newPlaceholder = new StringBuilder(String.valueOf(arr[i + 1].charAt(Integer.parseInt(arr[i + 2]))));
                            input = input.replace(placeholder, newPlaceholder.toString());
                            break;
                        case "str_substring":
                            // %str_substring%Momocraft%4%10%
                            if (arr.length >= i + 3 && arr[i + 3].matches("[0-9]+")) {
                                placeholder = "%" + arr[i] + "%" + arr[i + 1] + "%" + arr[i + 2] + "%" + arr[i + 3] + "%";
                                newPlaceholder = new StringBuilder(arr[i + 1].substring(Integer.parseInt(arr[i + 2]), Integer.parseInt(arr[i + 3])));
                            } else {
                                // %str_substring%Momocraft%4%
                                placeholder = "%" + arr[i] + "%" + arr[i + 1] + "%" + arr[i + 2] + "%";
                                newPlaceholder = new StringBuilder(arr[i + 1].substring(Integer.parseInt(arr[i + 2])));
                            }
                            input = input.replace(placeholder, newPlaceholder.toString());
                            break;
                        case "str_split":
                            stringBuilder = new StringBuilder();
                            // %str_split%1+2+3%+%
                            placeholder = "%" + arr[i] + "%" + arr[i + 1] + "%" + arr[i + 2] + "%";
                            for (String s : arr[i + 1].split(arr[i + 2], -1)) {
                                stringBuilder.append(s);
                            }
                            newPlaceholder = new StringBuilder(stringBuilder.toString());
                            input = input.replace(placeholder, newPlaceholder.toString());
                            break;
                    }
                } catch (Exception ex) {
                    UtilsHandler.getMsg().sendErrorMsg(pluginName, "Can not convert placeholder: " + input);
                    UtilsHandler.getMsg().sendErrorMsg(pluginName, "Format: %str_?%");
                    UtilsHandler.getMsg().sendDebugTrace(pluginName, ex);
                }
            }
        }
        // JavaScript placeholder.
        /*
        ScriptEngine engine = new ScriptEngineManager().getEngineByName("nashorn");
        if (engine == null)
        while (input.contains("<js>")) {
            split = input.split("<js>");
            back:
            for (int i = 0; i < split.length; i++) {
                if (i == 0)
                    continue;
                if (split[i].contains("</js>")) {
                    placeholder = split[i].substring(0, split[i].indexOf("</js>"));
                    newPlaceholder = new StringBuilder();
                    if (placeholder.contains("<var>")) {// <var>
                        placeholderValue = placeholder.substring(0, placeholder.indexOf("<var>"));
                        splitValues = placeholder.substring(placeholder.indexOf("<var>") + 5).split(",");
                        for (String var : splitValues) {
                            try {
                                engine.eval(placeholderValue);
                                newPlaceholder.append(engine.get(var).toString()).append(", ");
                            } catch (Exception ex) {
                                input = input.replace("<js>" + placeholder + "</js>", "%ERROR%");
                                UtilsHandler.getMsg().sendErrorMsg(pluginName, "Can not convert placeholder: " + originInput);
                                UtilsHandler.getMsg().sendErrorMsg(pluginName, "Format: <js>?</js>");
                                UtilsHandler.getMsg().sendDebugTrace(pluginName, ex);
                                continue back;
                            }
                        }
                        newPlaceholder = new StringBuilder(newPlaceholder.substring(0, newPlaceholder.length() - 1));
                        newPlaceholder = new StringBuilder(newPlaceholder.toString().replace("<var>", ""));
                        input = input.replace("<js>" + placeholder + "</js>", newPlaceholder.toString());
                    } else {
                        placeholderValue = placeholder;
                        try {
                            newPlaceholder = new StringBuilder(engine.eval(placeholderValue).toString());
                            input = input.replace("<js>" + placeholder + "</js>", newPlaceholder.toString());
                        } catch (Exception ex) {
                            input = input.replace("<js>" + placeholder + "</js>", "%ERROR%");
                            UtilsHandler.getMsg().sendErrorMsg(pluginName, "Can not convert placeholder: " + originInput);
                            UtilsHandler.getMsg().sendErrorMsg(pluginName, "Format: <js>?</js>");
                            UtilsHandler.getMsg().sendDebugTrace(pluginName, ex);
                        }
                    }
                    continue;
                }
                input = input.replace("<js>" + split[i] + "</js>", "%ERROR%");
                UtilsHandler.getMsg().sendErrorMsg(pluginName, "Can not convert placeholder: " + originInput);
                UtilsHandler.getMsg().sendErrorMsg(pluginName, "Format: <js>?</js>");
            }
        }
         */

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
                        newPlaceholder = new StringBuilder(String.valueOf(UtilsHandler.getCondition().checkCondition(pluginName, placeholder)));
                        input = input.replace("<if>" + placeholder + "</if>", newPlaceholder.toString());
                    } catch (Exception ex) {
                        ex.printStackTrace();
                        input = input.replace("<js>" + placeholder + "</js>", "%ERROR%");
                        UtilsHandler.getMsg().sendErrorMsg(pluginName, "Can not convert placeholder: " + originInput);
                        UtilsHandler.getMsg().sendErrorMsg(pluginName, "Format: <js>?</js>");
                        UtilsHandler.getMsg().sendDebugTrace(pluginName, ex);
                    }
                    continue;
                }
                input = input.replace("<js>" + split[i] + "</js>", "%ERROR%");
                UtilsHandler.getMsg().sendErrorMsg(pluginName, "Can not convert placeholder: " + originInput);
                UtilsHandler.getMsg().sendErrorMsg(pluginName, "Format: <js>?</js>");
            }
        }
        // %TARGET_condition%CONDITION%
        if (input.contains("%condition%")) {
            try {
                String[] arr = input.split("%");
                for (int i = 0; i < arr.length; i++)
                    if (arr[i].equals("%condition%"))
                        input = input.replace("%condition%" + arr[i + 1] + "%",
                                String.valueOf(UtilsHandler.getCondition().checkCondition(pluginName,
                                        ConfigHandler.getConfigPath().getConditionProp().get(arr[i + 1]))));
            } catch (Exception ex) {
                UtilsHandler.getMsg().sendErrorMsg(pluginName, "Can not convert placeholder: " + input);
                UtilsHandler.getMsg().sendErrorMsg(pluginName, "Format: %condition%CONDITION%");
                UtilsHandler.getMsg().sendDebugTrace(pluginName, ex);
            }
        }
        return input;
    }

    private String getConvertPAPI(String pluginName, String input) {
        return getConvertPAPI(pluginName, null, input);
    }

    private String getConvertPAPI(String pluginName, Player target, String input) {
        if (UtilsHandler.getDepend().PlaceHolderAPIEnabled()) {
            try {
                if (StringUtils.countMatches(input, "%") % 2 == 1) {
                    input = input + "$%";
                    input = PlaceholderAPI.setPlaceholders(target, input);
                    return input.substring(0, input.length() - 2);
                } else {
                    return PlaceholderAPI.setPlaceholders(target, input);
                }
            } catch (Exception ex) {
                UtilsHandler.getMsg().sendErrorMsg(pluginName, "Can not convert placeholder: " + input);
                UtilsHandler.getMsg().sendErrorMsg(pluginName, "Format: %PlaceHolderAPI%");
                UtilsHandler.getMsg().sendDebugTrace(pluginName, ex);
            }
        }
        return input;
    }

    @Override
    public String getMsgTrans(String input) {
        return ConfigHandler.getConfig("message.yml").getString("Message.Translation." + input, input);
    }

    @Override
    public String getVanillaTrans(String pluginName, Player player, String input, String type) {
        return UtilsHandler.getVanillaUtils().getValinaName(player, input, type);
    }

    @Override
    public String getVanillaTrans(String pluginName, String local, String input, String type) {
        return UtilsHandler.getVanillaUtils().getValinaName(local, input, type);
    }

    @Override
    public String getVanillaTrans(String pluginName, String input, String type) {
        return UtilsHandler.getVanillaUtils().getValinaName(input, type);
    }

    @Override
    public String getPlayersString(List<Player> input) {
        if (input == null || input.isEmpty())
            return getMsgTrans("noTarget");
        StringBuilder stringBuilder = new StringBuilder();
        for (Player value : input) {
            if (value == null)
                continue;
            stringBuilder.append(value.getName()).append(", ");
        }
        if (stringBuilder.toString().equals(""))
            return getMsgTrans("noTarget");
        return stringBuilder.substring(0, stringBuilder.length() - 2);
    }

    @Override
    public String getBlocksString(List<Block> input) {
        if (input == null || input.isEmpty())
            return getMsgTrans("noTarget");
        StringBuilder stringBuilder = new StringBuilder();
        for (Block value : input) {
            if (value == null)
                continue;
            stringBuilder.append(value.getType()).append(", ");
        }
        if (stringBuilder.toString().equals(""))
            return getMsgTrans("noTarget");
        return stringBuilder.substring(0, stringBuilder.length() - 2);
    }

    @Override
    public String getEntitiesString(List<Entity> input) {
        if (input == null || input.isEmpty())
            return getMsgTrans("noTarget");
        StringBuilder stringBuilder = new StringBuilder();
        for (Entity value : input) {
            if (value == null)
                continue;
            stringBuilder.append(value.getType().name()).append(", ");
        }
        if (stringBuilder.toString().equals(""))
            return getMsgTrans("noTarget");
        return stringBuilder.substring(0, stringBuilder.length() - 2);
    }

    @Override
    public void sendHookMsg(String pluginPrefix, String type, List<String> list) {
        if (list == null || list.isEmpty())
            return;
        StringBuilder message = new StringBuilder("&7Hooked " + type + ": [");
        for (String value : list) {
            if (type.equals("residence_flags")) {
                if (UtilsHandler.getCondition().isRegisteredFlag(value))
                    message.append(value).append(", ");
            } else {
                message.append(value).append(", ");
            }
        }
        if (!message.toString().contains(","))
            sendConsoleMsg(pluginPrefix, message.substring(0, message.length() - 2) + "]");
    }

    private String setPrefixAndColor(String prefix, String input) {
        if (input == null)
            return null;
        if (prefix == null)
            prefix = "";
        if (input.contains("%prefix%"))
            input = input.replaceAll("%prefix%\\s*", prefix);
        else
            input = prefix + input;
        return ChatColor.translateAlternateColorCodes('&', input);
    }

    private String setColor(String input) {
        if (input == null)
            return null;
        return ChatColor.translateAlternateColorCodes('&', input);
    }

    public void sendFileLoadedMsg(String type, List<String> list) {
        if (list.isEmpty())
            return;
        UtilsHandler.getMsg().sendConsoleMsg(ConfigHandler.getPluginPrefix(),
                "Loaded " + type + " files: " + list);
    }

    public void setupColorMap() {
        colorMap.put("a", ConfigHandler.getConfig("message.yml").getString("Message.Translation.color_a"));
        colorMap.put("b", ConfigHandler.getConfig("message.yml").getString("Message.Translation.color_b"));
        colorMap.put("c", ConfigHandler.getConfig("message.yml").getString("Message.Translation.color_c"));
        colorMap.put("d", ConfigHandler.getConfig("message.yml").getString("Message.Translation.color_d"));
        colorMap.put("e", ConfigHandler.getConfig("message.yml").getString("Message.Translation.color_e"));
        colorMap.put("f", ConfigHandler.getConfig("message.yml").getString("Message.Translation.color_f"));
        colorMap.put("0", ConfigHandler.getConfig("message.yml").getString("Message.Translation.color_0"));
        colorMap.put("1", ConfigHandler.getConfig("message.yml").getString("Message.Translation.color_1"));
        colorMap.put("2", ConfigHandler.getConfig("message.yml").getString("Message.Translation.color_2"));
        colorMap.put("3", ConfigHandler.getConfig("message.yml").getString("Message.Translation.color_3"));
        colorMap.put("4", ConfigHandler.getConfig("message.yml").getString("Message.Translation.color_4"));
        colorMap.put("5", ConfigHandler.getConfig("message.yml").getString("Message.Translation.color_5"));
        colorMap.put("6", ConfigHandler.getConfig("message.yml").getString("Message.Translation.color_6"));
        colorMap.put("7", ConfigHandler.getConfig("message.yml").getString("Message.Translation.color_7"));
        colorMap.put("8", ConfigHandler.getConfig("message.yml").getString("Message.Translation.color_8"));
        colorMap.put("9", ConfigHandler.getConfig("message.yml").getString("Message.Translation.color_9"));
    }

    @Override
    public String getColorCode(String color) {
        if (color.length() == 1) {
            String pattern = "[a-fA-F0-9]";
            if (color.matches(pattern))
                return color;
        } else {
            String keyColor;
            for (String key : colorMap.keySet()) {
                keyColor = colorMap.get(key);
                if (keyColor.equals(color))
                    return key;
            }
        }
        return "";
    }

    @Override
    public Map<String, String> getColorMap() {
        return colorMap;
    }
}