package tw.momocraft.coreplus.api;

import org.bukkit.block.Block;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import tw.momocraft.coreplus.utils.message.TranslateMap;

import java.util.List;

public interface MessageInterface {

    /**
     * Sending broadcast message to the server.
     *
     * @param prefix     the executing plugin prefix.
     * @param input      the value of message.
     * @param langHolder the translation of placeholders. It could be empty.
     */
    void sendBroadcastMsg(String prefix, String input, String... langHolder);

    /**
     * Sending broadcast message to the server.
     *
     * @param input the value of message.
     */
    void sendBroadcastMsg(String input, String... langHolder);


    /**
     * Sending Discord message to the server.
     * Need: DiscordSRV
     *
     * @param prefix     the executing plugin prefix.
     * @param channel    the discord channel to send message.
     * @param input      the value of message.
     * @param langHolder the translation of placeholders. It could be empty.
     */
    void sendDiscordMsg(String prefix, String channel, String input, String... langHolder);

    /**
     * Sending Discord message to the server.
     * Need: DiscordSRV
     *
     * @param input   the value of message.
     * @param channel the discord channel to send message.
     */
    void sendDiscordMsg(String channel, String input);

    /**
     * Sending Discord message to the server.
     * Need: DiscordSRV
     *
     * @param prefix     the executing plugin prefix.
     * @param channel    the discord channel to send message.
     * @param player     the player who will send message.
     * @param input      the value of message.
     * @param langHolder the translation of placeholders. It could be empty.
     */
    void sendDiscordMsg(String prefix, String channel, String input, Player player, String... langHolder);

    /**
     * Sending Discord message to the server.
     * Need: DiscordSRV
     *
     * @param channel the discord channel to send message.
     * @param player  the player who will send message.
     * @param input   the value of message.
     */
    void sendDiscordMsg(String channel, String input, Player player, String... langHolder);

    /**
     * Sending message to a player or console.
     *
     * @param prefix     the executing plugin prefix.
     * @param sender     the executing sender.
     * @param input      the value of message.
     * @param langHolder the translation of placeholders. It could be empty.
     */
    void sendMsg(String prefix, CommandSender sender, String input, String... langHolder);

    /**
     * Sending message to a player or console.
     *
     * @param sender the executing sender.
     * @param input  the value of message.
     */
    void sendMsg(CommandSender sender, String input, String... langHolder);

    /**
     * Printing the message in console.
     *
     * @param prefix     the executing plugin prefix.
     * @param input      the value of message.
     * @param langHolder the translation of placeholders. It could be empty.
     */
    void sendConsoleMsg(String prefix, String input, String... langHolder);

    /**
     * Printing the message in console.
     *
     * @param input the value of message.
     */
    void sendConsoleMsg(String input, String... langHolder);

    /**
     * Sending message to a player.
     *
     * @param prefix     the executing plugin prefix.
     * @param input      the value of message.
     * @param langHolder the translation of placeholders. It could be empty.
     */
    void sendPlayerMsg(String prefix, Player player, String input, String... langHolder);

    /**
     * Sending message to a player.
     *
     * @param input the value of message.
     */
    void sendPlayerMsg(Player player, String input, String... langHolder);

    /**
     * Sending message as player.
     *
     * @param prefix     the executing plugin prefix.
     * @param player     the executing player.
     * @param input      the value of message.
     * @param langHolder the translation of placeholders. It could be empty.
     */
    void sendChatMsg(String prefix, Player player, String input, String... langHolder);

    /**
     * Sending message as player.
     *
     * @param player the executing player.
     * @param input  the value of message.
     */
    void sendChatMsg(Player player, String input, String... langHolder);

    /**
     * Sending message to a player or console.
     *
     * @param player     the executing player.
     * @param input      the value of message.
     * @param langHolder the translation of placeholders. It could be empty.
     */
    void sendActionBarMsg(Player player, String input, String... langHolder);

    /**
     * @param player     the executing player.
     * @param input      the input message.
     * @param langHolder the translation of placeholders. It could be empty.
     */
    void sendTitleMsg(Player player, String input, String... langHolder);

    /**
     * @param player        the executing player.
     * @param inputTitle    the title message.
     * @param inputSubtitle the subtitle message.
     * @param langHolder    the translation of placeholders. It could be empty.
     */
    void sendTitleMsg(Player player, String inputTitle, String inputSubtitle, String... langHolder);

    /**
     * @param player        the executing player.
     * @param inputTitle    the title message.
     * @param inputSubtitle the subtitle message.
     * @param fadeIn        the time in ticks for titles to fade in.
     * @param stay          the  time in ticks for titles to stay.
     * @param fadeOut       the time in ticks for titles to fade out.
     * @param langHolder    the translation of placeholders. It could be empty.
     */
    void sendTitleMsg(Player player, String inputTitle, String inputSubtitle, int fadeIn, int stay, int fadeOut, String... langHolder);

    /**
     * Sending the information of feature in console to debug.
     *
     * @param pluginName the executing plugin prefix.
     * @param feature    the name of feature.
     * @param target     the checking target or type.
     * @param check      the checking things.
     * @param action     the feature's action like succeed, failed, return...
     * @param ste        the class and the line of code of this feature.
     */
    void sendDetailMsg(boolean debugging, String pluginName, String feature, String target, String check, String action, StackTraceElement ste);


    /**
     * Sending the information of feature in console to debug.
     *
     * @param pluginName the executing plugin prefix.
     * @param feature    the name of feature.
     * @param target     the checking target or type.
     * @param check      the checking things.
     * @param action     the feature's action like succeed, failed, return...
     * @param ste        the class and the line of code of this feature.
     */
    void sendDetailMsg(String pluginName, String feature, String target, String check, String action, StackTraceElement ste);

    /**
     * Sending the information of feature in console to debug.
     *
     * @param pluginName the executing plugin prefix.
     * @param feature    the name of feature.
     * @param target     the checking target or type.
     * @param check      the checking things.
     * @param action     the feature's action like succeed, failed, return...
     * @param detail     more information.
     * @param ste        the class and the line of code of this feature.
     */
    void sendDetailMsg(boolean debugging, String pluginName, String feature, String target, String check, String action, String detail, StackTraceElement ste);


    /**
     * Sending the information of feature in console to debug.
     *
     * @param pluginName the executing plugin prefix.
     * @param feature    the name of feature.
     * @param target     the checking target or type.
     * @param check      the checking things.
     * @param action     the feature's action like succeed, failed, return...
     * @param detail     more information.
     * @param ste        the class and the line of code of this feature.
     */
    void sendDetailMsg(String pluginName, String feature, String target, String check, String action, String detail, StackTraceElement ste);

    /**
     * Sending debug message in console.
     *
     * @param debugging  check or not.
     * @param pluginName the executing plugin name.
     * @param input      the value of message.
     */
    void sendDebugMsg(boolean debugging, String pluginName, String input);

    /**
     * Sending debug message in console.
     *
     * @param pluginName the executing plugin name.
     * @param input      the value of message.
     */
    void sendDebugMsg(String pluginName, String input);

    /**
     * Sending error message in console.
     *
     * @param pluginName the executing plugin prefix.
     * @param input      the value of message.
     */
    void sendErrorMsg(String pluginName, String input);

    /**
     * Sending exception message in console.
     *
     * @param debugging  check or not.
     * @param pluginName the executing plugin prefix.
     * @param ex         the type of exception.
     */
    void sendDebugTrace(boolean debugging, String pluginName, Exception ex);

    /**
     * Sending exception message in console.
     *
     * @param pluginName the executing plugin prefix.
     * @param ex         the type of exception.
     */
    void sendDebugTrace(String pluginName, Exception ex);

    /**
     * Creating a array for translation the placeholder of message.
     *
     * @return a array for translation the placeholder of message..
     */
    String[] newString();

    /**
     * Translating by custom placeholders.
     *
     * @param prefix     the executing plugin prefix.
     * @param input      the configuration path of this message.
     * @param sender     the executing sender.
     * @param langHolder the translation of placeholders. It could be empty.
     */
    void sendLangMsg(String prefix, String input, CommandSender sender, String... langHolder);

    /**
     * Translating by custom placeholders.
     *
     * @param input      the configuration path of this message.
     * @param sender     the executing sender.
     * @param langHolder the translation of placeholders. It could be empty.
     */
    void sendLangMsg(String input, CommandSender sender, String... langHolder);

    /**
     * Translating by custom placeholders.
     *
     * @param input      the input value list.
     * @param langHolder the custom placeholders.
     * @return the a new list which translated by the custom placeholders.
     */
    List<String> transLang(List<String> input, String... langHolder);

    /**
     * Translating by custom placeholders.
     *
     * @param sender     the target sender.
     * @param input      the input value list.
     * @param langHolder the custom placeholders.
     * @return the a new list which translated by the custom placeholders.
     */
    List<String> transLang(CommandSender sender, List<String> input, String... langHolder);

    /**
     * Translating by custom placeholders.
     *
     * @param player     the target player.
     * @param input      the input value list.
     * @param langHolder the custom placeholders.
     * @return the a new list which translated by the custom placeholders.
     */
    List<String> transLang(Player player, List<String> input, String... langHolder);

    /**
     * Translating by custom placeholders.
     *
     * @param local      the target's local language.
     * @param input      the input value list.
     * @param langHolder the custom placeholders.
     * @return the a new list which translated by the custom placeholders.
     */
    List<String> transLang(String local, List<String> input, String... langHolder);

    /**
     * Translating by custom placeholders.
     *
     * @param sender     the target sender.
     * @param input      the input string.
     * @param langHolder the translation of placeholders. It could be empty.
     * @return a new string which translated language placeholders.
     */
    String transLang(CommandSender sender, String input, String... langHolder);

    /**
     * Translating by custom placeholders.
     *
     * @param player     the target player.
     * @param input      the input string.
     * @param langHolder the translation of placeholders. It could be empty.
     * @return a new string which translated language placeholders.
     */
    String transLang(Player player, String input, String... langHolder);

    /**
     * Translating by custom placeholders.
     *
     * @param local      the sender's local language.
     * @param input      the input string.
     * @param langHolder the translation of placeholders. It could be empty.
     * @return a new string which translated language placeholders.
     */
    String transLang(String local, String input, String... langHolder);

    /**
     * Translating by custom placeholders.
     *
     * @param input      the input string.
     * @param langHolder the translation of placeholders. It could be empty.
     * @return a new string which translated language placeholders.
     */
    String transLang(String input, String... langHolder);

    /**
     * Translating by target placeholders.
     *
     * @param sender the sender of this.
     * @param target the sender's local language.
     * @param input  the input string.
     * @return a new string which translated language placeholders.
     */
    String transHolder(Player sender, Object target, String input);

    List<String> transHolder(Player sender, Object target, List<String> input);

    List<String> transHolder(Player sender, Object target, Object trigger, List<String> input);

    List<String> transHolder(Player sender, List<Object> targets, List<String> input);

    List<String> transHolder(Player sender, List<Object> targets, List<Object> triggers, List<String> input);

    /**
     * Translating the targets placeholders before output.
     *
     * @param pluginName   the sending plugin name.
     * @param player       the sender.
     * @param translateMap the translation targets.
     * @param input        the input string.
     * @return a new string which translated language placeholders.
     */
    String transTranslateMap(String pluginName, Player player, TranslateMap translateMap, String input);

    /**
     * Translating the targets placeholders before output.
     *
     * @param pluginName   the sending plugin name.
     * @param player       the sender.
     * @param translateMap the translation targets.
     * @param input        the input string.
     * @return a new string which translated language placeholders.
     */
    List<String> transTranslateMap(String pluginName, Player player, TranslateMap translateMap, List<String> input);

    /**
     * Getting the translate placeholder targets map.
     *
     * @param translateMap the import of translate placeholder targets map.
     * @param target       the input target object.
     * @param prefixName   the input placeholder's prefix.
     * @return the translate placeholder targets map.
     */
    TranslateMap getTranslateMap(TranslateMap translateMap, Object target, String targetType, String prefixName);

    /**
     * Getting the translate placeholder targets map.
     * Automatic adding the prefix to each type.
     *
     * @param translateMap the import of translate placeholder targets map.
     * @param target       the input target object.
     * @return the translate placeholder targets map.
     */
    TranslateMap getTranslateMap(TranslateMap translateMap, Object target, String targetType);

    /**
     * Translating the general placeholders.
     * General -> PlaceHolderAPI -> Custom
     *
     * @param pluginName the sending plugin name.
     * @param local      the sender's local language.
     * @param input      the input string list.
     * @return a new string with translated placeholders.
     */
    List<String> transByGeneral(String pluginName, String local, List<String> input);

    /**
     * Translating the general placeholders.
     * General -> PlaceHolderAPI -> Custom
     *
     * @param pluginName the sending plugin name.
     * @param local      the sender's local language.
     * @param input      the input string.
     * @return a new string with translated placeholders.
     */
    String transByGeneral(String pluginName, String local, String input);

    /**
     * Translating the general placeholders.
     *
     * @param pluginName the sending plugin name.
     * @param input      the input string list.
     * @param player     the target player can be null.
     * @return a new string with translated placeholders.
     */
    List<String> transLayoutPAPI(String pluginName, List<String> input, Player player);


    /**
     * Translating the general placeholders.
     *
     * @param pluginName the sending plugin name.
     * @param input      the input string.
     * @param player     the target player can be null.
     * @return a new string with translated placeholders.
     */
    String transLayoutPAPI(String pluginName, String input, Player player);

    /**
     * Getting the translation of placeholder.
     *
     * @param input the placeholder
     * @return the translated placeholder.
     */
    String getMsgTrans(String input);

    /**
     * Translating vanilla name for player.
     *
     * @param pluginName the sending plugin name.
     * @param player     the target player to identify the language.
     * @param input      the input value.
     * @param type       the type of value. Avail: entity, material
     * @return the player's client language value.
     */
    String getVanillaTrans(String pluginName, Player player, String input, String type);

    /**
     * Translating vanilla name for player.
     *
     * @param pluginName the sending plugin name.
     * @param local      the target local to identify the language.
     * @param input      the input value.
     * @param type       the type of value. Avail: entity, material
     * @return the player's client language value.
     */
    String getVanillaTrans(String pluginName, String local, String input, String type);

    /**
     * Translating vanilla name.
     *
     * @param pluginName the sending plugin name.
     * @param input      the input value.
     * @param type       the type of value. Avail: entity, material
     * @return the player's client language value.
     */
    String getVanillaTrans(String pluginName, String input, String type);

    /**
     * @param players the list of player.
     * @return a string of player list.
     */
    String getPlayersString(List<Player> players);

    /**
     * @param blocks the list of block.
     * @return a string of block list.
     */
    String getBlocksString(List<Block> blocks);

    /**
     * @param entities the list of entity.
     * @return a string of entity list.
     */
    String getEntitiesString(List<Entity> entities);

    /**
     * @param pluginPrefix the origin prefix of plugin.
     * @param type         the type of list.
     * @param list         the values of hooks.
     */
    void sendHookMsg(String pluginPrefix, String type, List<String> list);
}
