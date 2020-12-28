package tw.momocraft.coreplus.api;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public interface LanguageInterface {

    /**
     * Sending broadcast message to the server.
     *
     * @param prefix  the executing plugin prefix.
     * @param message the value of message.
     */
    void sendBroadcastMsg(String prefix, String message);

    /**
     * Printing the message in console.
     *
     * @param prefix  the executing plugin prefix.
     * @param message the value of message.
     */
    void sendConsoleMsg(String prefix, String message);

    /**
     * Sending debug message in console.
     *
     * @param prefix  the executing plugin prefix.
     * @param message the value of message.
     */
    void sendDebugMsg(String prefix, String message);

    /**
     * Sending error message in console.
     *
     * @param prefix  the executing plugin prefix.
     * @param message the value of message.
     */
    void sendErrorMsg(String prefix, String message);

    /**
     * Sending exception message in console.
     *
     * @param prefix the executing plugin prefix.
     * @param e      the type of exception.
     */
    void sendDebugTrace(String prefix, Exception e);

    /**
     * Sending message to a player.
     *
     * @param prefix  the executing plugin prefix.
     * @param message the value of message.
     */
    void sendPlayerMsg(String prefix, Player player, String message);

    /**
     * Sending message as player.
     *
     * @param prefix  the executing plugin prefix.
     * @param player  the executing player.
     * @param message the value of message.
     */
    void sendChatMsg(String prefix, Player player, String message);

    /**
     * Sending message to a player or console.
     *
     * @param prefix  the executing plugin prefix.
     * @param sender  the executing sender.
     * @param message the value of message.
     */
    void sendMsg(String prefix, CommandSender sender, String message);

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
    void sendFeatureMsg(String prefix, String feature, String target, String check, String action, StackTraceElement ste);

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
    void sendFeatureMsg(String prefix, String feature, String target, String check, String action, String detail, StackTraceElement ste);

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
     * @param placeHolder  the translation of placeholders. It could be empty.
     */
    void sendLangMsg(String prefix, String nodeLocation, CommandSender sender, String... placeHolder);

    /**
     * Logging the message in a file.
     *
     * @param path    the path of the file.
     * @param name    the name of the file like "latest.log" or "test.yml".
     * @param message the value of message.
     * @param time    if adding the time in front of the line. Example: "[2020/11/20 18:30:00]: "
     * @param newFile if creating a new file every day.
     * @param zip     if compressing the old log file.
     */
    void addLog(String path, String name, String message, boolean time, boolean newFile, boolean zip);
}
