package tw.momocraft.coreplus.utils.customcommands;

import com.Zrips.CMI.CMI;
import com.google.common.collect.Iterables;
import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.messaging.PluginMessageListener;
import tw.momocraft.coreplus.CorePlus;

import java.util.HashMap;
import java.util.Map;

public class BungeePlayerUtils {

    //implements PluginMessageListener {

    /*
    public BungeePlayerUtils() {
        CorePlus.getInstance().getServer().getMessenger().registerOutgoingPluginChannel(CorePlus.getInstance(), "BungeeCord");
        CorePlus.getInstance().getServer().getMessenger().registerIncomingPluginChannel(CorePlus.getInstance(), "BungeeCord", this);
    }

    private static String[] serverList;
    private static String[] list;

    @Override
    public void onPluginMessageReceived(String channel, Player player, byte[] message) {
        if (!channel.equals("BungeeCord")) {
            return;
        }
        ByteArrayDataInput in = ByteStreams.newDataInput(message);
        String server = in.readUTF();
        list = in.readUTF().split(", ");
        // First time: get the server list.
        if (serverList == null) {
            serverList = list;
        } else {
            // After: get the player list.
            playerMap.put(server, list);
        }
    }

    private static final Map<String, String[]> playerMap = new HashMap<>();

    public static void requirePlayerMap(Player player, String server) {
        ByteArrayDataOutput out = ByteStreams.newDataOutput();
        out.writeUTF("PlayerList");
        out.writeUTF(server);
        player.sendPluginMessage(CorePlus.getInstance(), "BungeeCord", out.toByteArray());
    }

    public static void requireServerList(Player player) {
        ByteArrayDataOutput out = ByteStreams.newDataOutput();
        out.writeUTF("GetServers");
        player.sendPluginMessage(CorePlus.getInstance(), "BungeeCord", out.toByteArray());
    }

    public static Map<String, String[]> getPlayerMap() {
        CMI.getInstance().getPlaceholderAPIManager().
        Player player = Iterables.getFirst(Bukkit.getOnlinePlayers(), null);
        requireServerList(player);
        for (String server : serverList) {
            requirePlayerMap(player, server);
        }
        serverList = null;
        return playerMap;
    }

     */
}
