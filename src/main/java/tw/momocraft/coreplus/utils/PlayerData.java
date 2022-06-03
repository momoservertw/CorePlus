package tw.momocraft.coreplus.utils;

import org.bukkit.OfflinePlayer;
import org.bukkit.Particle;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import tw.momocraft.coreplus.CorePlus;
import tw.momocraft.coreplus.handlers.ConfigHandler;
import tw.momocraft.coreplus.handlers.UtilsHandler;
import tw.momocraft.coreplus.utils.player.PlayerDataMap;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class PlayerData {

    private Map<String, PlayerDataMap> playerMap = new HashMap<>();

    private void loadPlayer(Player player) {
        String playerName = player.getName();
        UUID uuid = player.getUniqueId();

        new BukkitRunnable() {
            int i = 1;
            PlayerDataMap playerDataMap;

            @Override
            public void run() {
                if (i > 5) {

                } else {
                    i++;
                    playerDataMap = new PlayerDataMap();
                    playerDataMap.setPlayerName(playerName);
                    playerDataMap.setUuid(uuid);
                    Map<String, String> map = UtilsHandler.getFile().getMySQL().getValueMap(ConfigHandler.getPluginName()
                            , "CorePlus", "playerdata", "uuid", uuid.toString());
                    if (Boolean.getBoolean(map.get("synced"))) {
                        playerDataMap.setPlayerData(map);
                        playerMap.put(playerName, playerDataMap);
                        cancel();
                    }
                }
            }
        }.runTaskTimer(CorePlus.getInstance(), 0, 20);
    }

    private void unloadPlayer(OfflinePlayer offlinePlayer) {
        String playerName = offlinePlayer.getName();
        UUID uuid = offlinePlayer.getUniqueId();

        Map<String, String> map = UtilsHandler.getFile().getMySQL().getValueMap(ConfigHandler.getPluginName()
                , "CorePlus", "PLAYERDATA", "uuid", uuid.toString());
        map.put("synced", "true");
        for (Map.Entry<String, String> entry : map.entrySet())
            UtilsHandler.getFile().getMySQL().setValueWhere(ConfigHandler.getPluginName(),
                    "CorePlus", "PLAYERDATA", "uuid",
                    uuid.toString(), entry.getKey(), entry.getValue());
        playerMap.remove(playerName);
    }

    /*
     * 1. 當玩家加入伺服器
     * if (Synced=true)
     * 載入MySQL的資料到快取中，設置Synced=true
     * else
     *   if (player.isOnlineInBungee)
     *   Waiting
     *   else
     *   # 異常狀況，當作玩家在線時伺服器崩潰。
     *
     * 2. 當玩家登出後，儲存該玩家的資料、設置Synced=true，之後從快取中移除該玩家。
     *
     * 3. 每隔3分鐘自動存檔玩家資料
     */

}
