package tw.momocraft.coreplus.utils.conditions;
import com.bekvon.bukkit.residence.api.ResidenceApi;
import com.bekvon.bukkit.residence.containers.Flags;
import com.bekvon.bukkit.residence.protection.ClaimedResidence;
import com.bekvon.bukkit.residence.protection.ResidencePermissions;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import tw.momocraft.coreplus.handlers.UtilsHandler;

public class ResidenceUtils {

    public boolean checkFlag(Player player, Location loc, String flag, boolean def) {
        if (!UtilsHandler.getDepend().ResidenceEnabled()) {
            return false;
        }
        if (flag != null && !flag.equals("")) {
            ClaimedResidence res = ResidenceApi.getResidenceManager().getByLoc(loc);
            if (res != null) {
                ResidencePermissions perms = res.getPermissions();
                if (player == null) {
                    return perms.has(Flags.getFlag(flag), def);
                }
                switch (flag) {
                    case "build":
                        if (UtilsHandler.getPlayer().hasPermission(player, "residence.bypass.build")) {
                            return true;
                        }
                        break;
                    case "use":
                        if (UtilsHandler.getPlayer().hasPermission(player, "residence.bypass.use")) {
                            return true;
                        }
                        break;
                    case "fly":
                        if (UtilsHandler.getPlayer().hasPermission(player, "residence.bypass.fly")) {
                            return true;
                        }
                        break;
                    case "nofly":
                        if (UtilsHandler.getPlayer().hasPermission(player, "residence.bypass.nofly")) {
                            return true;
                        }
                        break;
                    case "tp":
                        if (UtilsHandler.getPlayer().hasPermission(player, "residence.bypass.tp")) {
                            return true;
                        }
                        break;
                    case "command":
                        if (UtilsHandler.getPlayer().hasPermission(player, "residence.bypass.command")) {
                            return true;
                        }
                        break;
                    case "itempickup":
                        if (UtilsHandler.getPlayer().hasPermission(player, "residence.bypass.itempickup")) {
                            return true;
                        }
                        break;
                    case "destroy":
                    case "place":
                        if (UtilsHandler.getPlayer().hasPermission(player, "residence.bypass.destroy")) {
                            return true;
                        }
                        if (perms.playerHas(player, Flags.build, false)) {
                            if (perms.playerHas(player, Flags.getFlag(flag), true))
                                return true;
                        }
                        break;
                }
                return perms.playerHas(player, Flags.getFlag(flag), def);
            }
        }
        return true;
    }
}