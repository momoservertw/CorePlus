package tw.momocraft.coreplus.utils.player;

import com.Zrips.CMI.CMI;
import com.Zrips.CMI.Containers.CMIUser;
import org.bukkit.entity.Player;

import java.util.UUID;

public class CMIUtils {

    public UUID getUUID(String playerName) {
        CMIUser cmiUser = getUser(playerName);
        if (cmiUser != null)
            return cmiUser.getUniqueId();
        return null;
    }

    public CMIUser getUser(String playerName) {
        return CMI.getInstance().getPlayerManager().getUser(playerName);
    }

    public CMIUser getUser(UUID uuid) {
        return CMI.getInstance().getPlayerManager().getUser(uuid);
    }

    public String getDisplayName(String playerName) {
        CMIUser cmiUser = getUser(playerName);
        if (cmiUser != null)
            return cmiUser.getDisplayName();
        return null;
    }

    public String getDisplayName(UUID uuid) {
        CMIUser cmiUser = getUser(uuid);
        if (cmiUser != null)
            return cmiUser.getDisplayName();
        return null;
    }

    public boolean isAFK(Player player) {
        return CMI.getInstance().getPlayerManager().getUser(player).isAfk();
    }
}
