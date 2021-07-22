package tw.momocraft.coreplus.listeners;

import com.google.common.collect.Table;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import tw.momocraft.coreplus.handlers.ConfigHandler;
import tw.momocraft.coreplus.handlers.UtilsHandler;


public class CommandOnline implements Listener {

    @EventHandler(priority = EventPriority.HIGH, ignoreCancelled = true)
    public void onPlayerJoinEvent(PlayerJoinEvent e) {
        if (UtilsHandler.getDepend().MpdbEnabled())
            return;
        Player player = e.getPlayer();
        String playerName = player.getName();
        Table<String, Long, String> waitingTable = UtilsHandler.getCommandManager().getOnlineCmdTable();
        if (!waitingTable.rowKeySet().contains(playerName))
            return;
        for (long expireTime : waitingTable.row(playerName).keySet()) {
            if (expireTime > System.currentTimeMillis())
                UtilsHandler.getCommandManager().sendCmd(
                        ConfigHandler.getPlugin(), player, player, waitingTable.get(playerName, expireTime));
            waitingTable.remove(playerName, expireTime);
        }
    }
}
