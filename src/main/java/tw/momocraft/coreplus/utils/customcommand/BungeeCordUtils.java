package tw.momocraft.coreplus.utils.customcommand;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import org.bukkit.entity.Player;
import org.bukkit.plugin.messaging.Messenger;
import org.bukkit.plugin.messaging.PluginMessageListener;
import tw.momocraft.coreplus.CorePlus;
import tw.momocraft.coreplus.handlers.ConfigHandler;
import tw.momocraft.coreplus.handlers.UtilsHandler;

public class BungeeCordUtils implements PluginMessageListener {

    public static void SwitchServers(String pluginName, Player player, String server) {
        if (player== null)
            return;
        Messenger messenger = CorePlus.getInstance().getServer().getMessenger();
        if (!messenger.isOutgoingChannelRegistered(CorePlus.getInstance(), "BungeeCord")) {
            messenger.registerOutgoingPluginChannel(CorePlus.getInstance(), "BungeeCord");
        }
        ByteArrayDataOutput out = ByteStreams.newDataOutput();
        try {
            out.writeUTF("Connect");
            out.writeUTF(server);
        } catch (Exception ex) {
            UtilsHandler.getMsg().sendErrorMsg(pluginName, "An error occurred while executing command: \"switch: " + server + "\"");
            UtilsHandler.getMsg().sendErrorMsg(pluginName, "If this error keeps happening, please contact the plugin author.");
            UtilsHandler.getMsg().sendDebugTrace(ConfigHandler.getPluginName(), ex);
        }
        player.sendPluginMessage(CorePlus.getInstance(), "BungeeCord", out.toByteArray());
    }

    public static void ExecuteCommand(String pluginName, Player player, String cmd) {
        Messenger messenger = CorePlus.getInstance().getServer().getMessenger();
        if (!messenger.isOutgoingChannelRegistered(CorePlus.getInstance(), "BungeeCord")) {
            messenger.registerOutgoingPluginChannel(CorePlus.getInstance(), "BungeeCord");
        }
        ByteArrayDataOutput out = ByteStreams.newDataOutput();
        try {
            out.writeUTF("Subchannel");
            out.writeUTF("Argument");
            out.writeUTF(cmd);
        } catch (Exception ex) {
            UtilsHandler.getMsg().sendErrorMsg(pluginName, "An error occurred while executing command: \"bungee: " + cmd + "\"");
            UtilsHandler.getMsg().sendErrorMsg(pluginName, "If this error keeps happening, please contact the plugin author.");
            UtilsHandler.getMsg().sendDebugTrace(ConfigHandler.getPluginName(), ex);
        }
        player.sendPluginMessage(CorePlus.getInstance(), "BungeeCord", out.toByteArray());
    }

    @Override
    public void onPluginMessageReceived(String channel, Player player, byte[] message) {
        if (!channel.equals("BungeeCord")) {
            return;
        }
        ByteArrayDataInput in = ByteStreams.newDataInput(message);
        String subchannel = in.readUTF();

        /*
        if (subchannel.equals("PlayerCount")) {
            String[] serverList = in.readUTF().split(", ");
        }
         */
        if (!subchannel.contains("PlayerCount")) {
            player.sendMessage(subchannel + " " + in.readByte());
        }
    }
}