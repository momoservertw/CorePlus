package tw.momocraft.coreplus.listeners;

import com.google.common.collect.Table;
import javafx.util.Pair;
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
        Table<String, Pair<Long, Integer>, String> waitingTable = UtilsHandler.getCommandManager().getOnlineCmdTable();
        if (!waitingTable.rowKeySet().contains(playerName))
            return;
        for (Pair<Long, Integer> waitingPair : waitingTable.row(playerName).keySet()) {
            if (waitingPair.getKey() == -1000 || System.currentTimeMillis() - waitingPair.getValue() < waitingPair.getKey())
                UtilsHandler.getCommandManager().sendCmd(
                        ConfigHandler.getPlugin(), player, player, waitingTable.get(playerName, waitingPair));
            waitingTable.remove(playerName, waitingPair);
        }
    }
}
