package tw.momocraft.coreplus.listeners;

import com.google.common.collect.Table;
import net.craftersland.data.bridge.api.events.SyncCompleteEvent;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import tw.momocraft.coreplus.handlers.ConfigHandler;
import tw.momocraft.coreplus.handlers.UtilsHandler;


public class CommandOnlineMPDB implements Listener {

    @EventHandler(priority = EventPriority.HIGH, ignoreCancelled = true)
    private void onSyncCompleteEvent(SyncCompleteEvent e) {
        Player player = e.getPlayer();
        String playerName = player.getName();
        Table<String, Long, String> waitingTable = UtilsHandler.getCommandManager().getOnlineCmdTable();
        if (!waitingTable.rowKeySet().contains(playerName))
            return;
        for (long expireTime : waitingTable.row(playerName).keySet()) {
            if (expireTime > System.currentTimeMillis())
                UtilsHandler.getCommandManager().sendCmd(
                        ConfigHandler.getPluginName(), player, player, waitingTable.get(playerName, expireTime));
            waitingTable.remove(playerName, expireTime);
        }
    }
}
