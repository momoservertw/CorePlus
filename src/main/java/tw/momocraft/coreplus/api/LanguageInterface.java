package tw.momocraft.coreplus.api;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.block.Block;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.io.File;
import java.util.List;
import java.util.UUID;

public interface LanguageInterface {

    /**
     * Sending broadcast message to the server.
     *
     * @param prefix     the executing plugin prefix.
     * @param input      the value of message.
     * @param langHolder the translation of placeholders. It could be empty.
     */
    void sendBroadcastMsg(String prefix, String input, String... langHolder);

    /**
     * Sending Discord message to the server.
     * Need: DiscordSRV
     *
     * @param prefix     the executing plugin prefix.
     * @param input      the value of message.
     * @param langHolder the translation of placeholders. It could be empty.
     */
    void sendDiscordMsg(String prefix, String channel, String input, String... langHolder);

    /**
     * Sending Discord message to the server.
     * Need: DiscordSRV
     *
     * @param prefix     the executing plugin prefix.
     * @param input      the value of message.
     * @param langHolder the translation of placeholders. It could be empty.
     */
    void sendDiscordMsg(String prefix, String channel, String input, Player player, String... langHolder);

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
     * Printing the message in console.
     *
     * @param prefix     the executing plugin prefix.
     * @param input      the value of message.
     * @param langHolder the translation of placeholders. It could be empty.
     */
    void sendConsoleMsg(String prefix, String input, String... langHolder);

    /**
     * Sending message to a player.
     *
     * @param prefix     the executing plugin prefix.
     * @param input      the value of message.
     * @param langHolder the translation of placeholders. It could be empty.
     */
    void sendPlayerMsg(String prefix, Player player, String input, String... langHolder);

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
     * @param prefix  the executing plugin prefix.
     * @param feature the name of feature.
     * @param target  the checking target or type.
     * @param check   the checking things.
     * @param action  the feature's action like succeed, failed, return...
     * @param ste     the class and the line of code of this feature.
     */
    void sendFeatureMsg(boolean debugging, String prefix, String feature, String target, String check, String action, StackTraceElement ste);

    /**
     * Sending the information of feature in console to debug.
     *
     * @param prefix  the executing plugin prefix.
     * @param feature the name of feature.
     * @param target  the checking target or type.
     * @param check   the checking things.
     * @param action  the feature's action like succeed, failed, return...
     * @param detail  more information.
     * @param ste     the class and the line of code of this feature.
     */
    void sendFeatureMsg(boolean debugging, String prefix, String feature, String target, String check, String action, String detail, StackTraceElement ste);

    /**
     * Sending debug message in console.
     *
     * @param debugging  check or not.
     * @param pluginName the executing plugin name.
     * @param input      the value of message.
     */
    void sendDebugMsg(boolean debugging, String pluginName, String input);

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
     * Creating a array for translation the placeholder of message.
     *
     * @return a array for translation the placeholder of message..
     */
    String[] newString();

    /**
     * Sending a message from configuration.
     *
     * @param prefix       the executing plugin prefix.
     * @param nodeLocation the configuration path of this message.
     * @param sender       the executing sender.
     * @param langHolder   the translation of placeholders. It could be empty.
     */
    void sendLangMsg(String pluginName, String prefix, String nodeLocation, CommandSender sender, String... langHolder);

    /**
     * Translating the placeholders before output.
     *
     * @param prefix     the sending plugin prefix.
     * @param player     the target player.
     * @param input      the input string.
     * @param langHolder the translation of placeholders. It could be empty.
     * @return a new string which translated language placeholders.
     */
    String transLangHolders(String prefix, Player player, String input, String... langHolder);

    /**
     * Translating the placeholders before output.
     *
     * @param prefix     the sending plugin prefix.
     * @param local      the target's local language.
     * @param input      the input string.
     * @param langHolder the translation of placeholders. It could be empty.
     * @return a new string which translated language placeholders.
     */
    String transLangHolders(String prefix, String local, String input, String... langHolder);

    /**
     * @param pluginName the sending plugin name.
     * @param local      the target's local language.
     * @param input      the input string list.
     * @param playerName the target player name.
     * @return a new string with translated player placeholders.
     */
    List<String> transByPlayerName(String pluginName, String local, List<String> input, String playerName);

    /**
     * @param pluginName the sending plugin name.
     * @param local      the target's local language.
     * @param input      the input string.
     * @param playerName the target player name.
     * @return a new string with translated player placeholders.
     */
    String transByPlayerName(String pluginName, String local, String input, String playerName);

    /**
     * @param pluginName the sending plugin name.
     * @param local      the target's local language.
     * @param input      the input string list.
     * @param uuid       the target player uuid.
     * @return a new string with translated player placeholders.
     */
    List<String> transByPlayerUUID(String pluginName, String local, List<String> input, UUID uuid);

    /**
     * @param pluginName the sending plugin name.
     * @param local      the target's local language.
     * @param input      the input string.
     * @param uuid       the target player uuid.
     * @return a new string with translated player placeholders.
     */
    String transByPlayerUUID(String pluginName, String local, String input, UUID uuid);

    /**
     * Translating the player placeholders.
     * Player -> Entity -> General(PlaceHolderAPI) -> PlaceHolderAPI -> Custom
     *
     * @param pluginName the sending plugin name.
     * @param local      the target's local language.
     * @param input      the input string list.
     * @param player     the target player.
     * @return a new string with translated player placeholders.
     */
    List<String> transByPlayer(String pluginName, String local, List<String> input, Player player, String target);

    /**
     * Translating the player placeholders.
     * Player -> Entity -> General(PlaceHolderAPI) -> PlaceHolderAPI -> Custom
     *
     * @param pluginName the sending plugin name.
     * @param local      the target's local language.
     * @param input      the input string.
     * @param player     the target player.
     * @return a new string with translated player placeholders.
     */
    String transByPlayer(String pluginName, String local, String input, Player player, String target);

    /**
     * Translating the entity placeholders.
     * Entity -> Location ->  General(Placeholder) -> Custom
     *
     * @param pluginName     the sending plugin name.
     * @param local          the target's local language.
     * @param input          the input string list.
     * @param entity         the target entity.
     * @param target         the target type: entity, player, target
     * @param isTransGeneral is translated the General placeholders.
     * @return a new string with translated entity placeholders.
     */
    List<String> transByEntity(String pluginName, String local, List<String> input, Entity entity, String target, boolean isTransGeneral);

    /**
     * Translating the entity placeholders.
     * Entity -> Location ->  General(Placeholder) -> Custom
     *
     * @param pluginName     the sending plugin name.
     * @param local          the target's local language.
     * @param input          the input string.
     * @param entity         the target entity.
     * @param target         the target type: entity, player, target
     * @param isTransGeneral is translated the General placeholders.
     * @return a new string with translated entity placeholders.
     */
    String transByEntity(String pluginName, String local, String input, Entity entity, String target, boolean isTransGeneral);

    /**
     * Translating the offline player placeholders.
     * OfflinePlayer -> General(PlaceHolderAPI) -> Custom
     *
     * @param pluginName    the sending plugin name.
     * @param local         the target's local language.
     * @param input         the input string list.
     * @param offlinePlayer the target player.
     * @param target        the target type: player, target
     * @return a new string with translated offline player placeholders.
     */
    List<String> transByOfflinePlayer(String pluginName, String local, List<String> input, OfflinePlayer offlinePlayer, String target);

    /**
     * Translating the offline player placeholders.
     * OfflinePlayer -> General(PlaceHolderAPI) -> Custom
     *
     * @param pluginName    the sending plugin name.
     * @param local         the target's local language.
     * @param input         the input string list.
     * @param offlinePlayer the target player.
     * @param target        the target type: player, target
     * @return a new string with translated offline player placeholders.
     */
    String transByOfflinePlayer(String pluginName, String local, String input, OfflinePlayer offlinePlayer, String target);

    /**
     * Translating the block placeholders.
     * Block -> Location -> General(PlaceHolderAPI) -> Custom
     *
     * @param pluginName the sending plugin name.
     * @param local      the target's local language.
     * @param input      the input string list.
     * @param block      the target block.
     * @param target     the target type: block, target
     * @return a new string with translated block placeholders.
     */
    List<String> transByBlock(String pluginName, String local, List<String> input, Block block, String target);

    /**
     * Translating the block placeholders.
     * Block -> Location -> General(PlaceHolderAPI) -> Custom
     *
     * @param pluginName the sending plugin name.
     * @param local      the target's local language.
     * @param input      the input string.
     * @param block      the target block.
     * @param target     the target type: block, target
     * @return a new string with translated block placeholders.
     */
    String transByBlock(String pluginName, String local, String input, Block block, String target);

    /**
     * Translating the item placeholders.
     * Item -> Material -> General(PlaceHolderAPI) -> Custom
     *
     * @param pluginName the sending plugin name.
     * @param local      the target's local language.
     * @param input      the input string list.
     * @param itemStack  the target item.
     * @param target     the target type: item, target
     * @return a new string with translated block placeholders.
     */
    List<String> transByItem(String pluginName, String local, List<String> input, ItemStack itemStack, String target);

    /**
     * Translating the item placeholders.
     * Item -> Material -> General(PlaceHolderAPI) -> Custom
     *
     * @param pluginName the sending plugin name.
     * @param local      the target's local language.
     * @param input      the input string.
     * @param itemStack  the target item.
     * @param target     the target type: item, target
     * @return a new string with translated block placeholders.
     */
    String transByItem(String pluginName, String local, String input, ItemStack itemStack, String target);

    /**
     * Translating the material placeholders.
     * Material -> General(PlaceHolderAPI) -> Custom
     *
     * @param pluginName     the sending plugin name.
     * @param local          the target's local language.
     * @param input          the input string list.
     * @param material       the target material.
     * @param target         the target type: block, target
     * @param isTransGeneral is translated the General placeholders.
     * @return a new string with translated block placeholders.
     */
    List<String> transByMaterial(String pluginName, String local, List<String> input, Material material, String target, boolean isTransGeneral);

    /**
     * Translating the material placeholders.
     * Material -> General(PlaceHolderAPI) -> Custom
     *
     * @param pluginName     the sending plugin name.
     * @param local          the target's local language.
     * @param input          the input string.
     * @param material       the target material.
     * @param target         the target type: block, target
     * @param isTransGeneral is translated the General placeholders.
     * @return a new string with translated block placeholders.
     */
    String transByMaterial(String pluginName, String local, String input, Material material, String target, boolean isTransGeneral);

    /**
     * Translating the location placeholders.
     * Location -> General(PlaceHolderAPI) -> Custom
     *
     * @param pluginName     the sending plugin name.
     * @param local          the target's local language.
     * @param input          the input string list.
     * @param loc            the target location.
     * @param target         the target type: player, entity, block, target
     * @param isTransGeneral is translated the General placeholders.
     * @return a new string with translated block placeholders.
     */
    List<String> transByLocation(String pluginName, String local, List<String> input, Location loc, String target, boolean isTransGeneral);

    /**
     * Translating the location placeholders.
     * Location -> General(PlaceHolderAPI) -> Custom
     *
     * @param pluginName     the sending plugin name.
     * @param local          the target's local language.
     * @param input          the input string.
     * @param loc            the target location.
     * @param target         the target type: player, entity, block, target
     * @param isTransGeneral is translated the General placeholders.
     * @return a new string with translated block placeholders.
     */
    String transByLocation(String pluginName, String local, String input, Location loc, String target, boolean isTransGeneral);

    /**
     * Translating the general placeholders.
     * General -> PlaceHolderAPI -> Custom
     *
     * @param pluginName the sending plugin name.
     * @param local      the target's local language.
     * @param input      the input string list.
     * @return a new string with translated placeholders.
     */
    List<String> transByGeneral(String pluginName, String local, List<String> input);

    /**
     * Translating the general placeholders.
     * General -> PlaceHolderAPI -> Custom
     *
     * @param pluginName the sending plugin name.
     * @param local      the target's local language.
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
     * Translating the custom placeholders.
     *
     * @param pluginName the sending plugin name.
     * @param local      the target's local language.
     * @param input      the input string list.
     * @return a new string with translated placeholders.
     */
    List<String> transByCustom(String pluginName, String local, List<String> input);

    /**
     * Translating the custom placeholders.
     *
     * @param pluginName the sending plugin name.
     * @param local      the target's local language.
     * @param input      the input string.
     * @return a new string with translated placeholders.
     */
    String transByCustom(String pluginName, String local, String input);

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
     * Logging the message in a file.
     *
     * @param pluginName the sending plugin name.
     * @param file       the file from CorePlus configuration.
     * @param message    the value of message.
     * @param time       if adding the time in front of the line. Example: "[2020/11/20 18:30:00]: "
     * @param newFile    if creating a new file every day.
     * @param zip        if compressing the old log file.
     */
    void addLog(String pluginName, File file, String message, boolean time, boolean newFile, boolean zip);


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
