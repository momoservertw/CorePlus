package tw.momocraft.coreplus.api;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public interface LanguageInterface {

    void sendBroadcastMsg(String prefix, String message);

    void sendConsoleMsg(String prefix, String message);

    void sendDebugMsg(String prefix, String message);

    void sendErrorMsg(String prefix, String message);

    void sendDebugTrace(String prefix, Exception e);

    void sendPlayerMsg(String prefix, Player player, String message);

    void sendChatMsg(String prefix, Player player, String message);

    void sendMsg(String prefix, CommandSender sender, String message);

    void sendFeatureMsg(String prefix, String feature, String target, String check, String action, String detail, StackTraceElement ste);

    void sendFeatureMsg(String prefix, String feature, String target, String check, String action, StackTraceElement ste);

    String[] newString();

    void sendLangMsg(String prefix, String nodeLocation, CommandSender sender, String... placeHolder);
}
