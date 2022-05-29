package tw.momocraft.coreplus.utils.condition;

import com.bekvon.bukkit.residence.Residence;
import com.bekvon.bukkit.residence.api.ResidenceApi;
import com.bekvon.bukkit.residence.containers.Flags;
import com.bekvon.bukkit.residence.protection.ClaimedResidence;
import com.bekvon.bukkit.residence.protection.FlagPermissions;
import com.bekvon.bukkit.residence.protection.ResidencePermissions;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import tw.momocraft.coreplus.handlers.UtilsHandler;

import java.util.Set;

public class ResidenceUtils {

    public void registerFlag(String flag) {
        FlagPermissions.addFlag(flag);
    }

    public boolean isInResidence(Location loc) {
        ClaimedResidence res = ResidenceApi.getResidenceManager().getByLoc(loc);
        return res != null;
    }

    public boolean isRegisteredFlag(String flag) {
        return Residence.getInstance().getPermissionManager().getAllFlags().getFlags().containsKey(flag);
    }

    public String getResidenceName(Location loc) {
        ClaimedResidence res = ResidenceApi.getResidenceManager().getByLoc(loc);
        if (res != null)
            return res.getName();
        return null;
    }

    public Set<String> getRegisteredFlags() {
        return Residence.getInstance().getPermissionManager().getAllFlags().getFlags().keySet();
    }

    public boolean checkFlag(Player player, Location loc, String flag, boolean def) {
        if (!UtilsHandler.getDepend().ResidenceEnabled())
            return def;
        if (flag != null) {
            ClaimedResidence res = ResidenceApi.getResidenceManager().getByLoc(loc);
            if (res != null) {
                ResidencePermissions perms = res.getPermissions();
                if (player == null) {
                    switch (flag) {
                        case "destroy":
                        case "place":
                            if (perms.has(Flags.build, false)) {
                                if (perms.has(flag, true))
                                    return true;
                            }
                            break;
                    }
                    return perms.has(flag, def);
                }
                switch (flag) {
                    case "build":
                        if (UtilsHandler.getPlayer().hasPerm(player, "residence.bypass.build")) {
                            return true;
                        }
                        break;
                    case "use":
                        if (UtilsHandler.getPlayer().hasPerm(player, "residence.bypass.use")) {
                            return true;
                        }
                        break;
                    case "fly":
                        if (UtilsHandler.getPlayer().hasPerm(player, "residence.bypass.fly")) {
                            return true;
                        }
                        break;
                    case "nofly":
                        if (UtilsHandler.getPlayer().hasPerm(player, "residence.bypass.nofly")) {
                            return true;
                        }
                        break;
                    case "tp":
                        if (UtilsHandler.getPlayer().hasPerm(player, "residence.bypass.tp")) {
                            return true;
                        }
                        break;
                    case "command":
                        if (UtilsHandler.getPlayer().hasPerm(player, "residence.bypass.command")) {
                            return true;
                        }
                        break;
                    case "itempickup":
                        if (UtilsHandler.getPlayer().hasPerm(player, "residence.bypass.itempickup")) {
                            return true;
                        }
                        break;
                    case "destroy":
                    case "place":
                        if (UtilsHandler.getPlayer().hasPerm(player, "residence.bypass.destroy")) {
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

    public boolean checkFlag(Location loc, String flag, boolean def) {
        return checkFlag(null, loc, flag, def);
    }
}
