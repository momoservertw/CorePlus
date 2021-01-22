package tw.momocraft.coreplus.utils.conditions;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import tw.momocraft.coreplus.api.ConditionInterface;
import tw.momocraft.coreplus.handlers.UtilsHandler;

import java.util.List;

public class ConditionUtils implements ConditionInterface {

    private static LocationUtils locationUtils;
    private static BlocksUtils blocksUtils;

    public ConditionUtils() {
        setLocationUtils(new LocationUtils());
        setBlockUtils(new BlocksUtils());
    }

    public static void setLocationUtils(LocationUtils loc) {
        locationUtils = loc;
    }

    public static void setBlockUtils(BlocksUtils blocks) {
        blocksUtils = blocks;
    }

    @Override
    public boolean checkLocation(Location loc, List<String> locList, boolean def) {
        return locationUtils.checkLocation(loc, locList, def);
    }

    @Override
    public boolean checkBlocks(Location loc, List<String> blocksList, boolean def) {
        return blocksUtils.checkBlocks(loc, blocksList, def);
    }

    @Override
    public boolean checkFlag(Player player, Location loc, String flag, boolean def) {
        if (!UtilsHandler.getDepend().ResidenceEnabled()) {
            return def;
        }
        return UtilsHandler.getDepend().getResidenceUtils().checkFlag(player, loc, flag, def);
    }

    @Override
    public boolean checkFlag(Player player, Location loc, String flag, boolean def, boolean check) {
        if (!UtilsHandler.getDepend().ResidenceEnabled()) {
            return def;
        }
        return UtilsHandler.getDepend().getResidenceUtils().checkFlag(player, loc, flag, def, check);
    }

    @Override
    public boolean checkFlag(Location loc, String flag, boolean def) {
        if (!UtilsHandler.getDepend().ResidenceEnabled()) {
            return def;
        }
        return UtilsHandler.getDepend().getResidenceUtils().checkFlag(loc, flag, def);
    }

    @Override
    public boolean checkFlag(Location loc, String flag, boolean def, boolean check) {
        if (!UtilsHandler.getDepend().ResidenceEnabled()) {
            return def;
        }
        return UtilsHandler.getDepend().getResidenceUtils().checkFlag(loc, flag, def, check);
    }

    @Override
    public boolean isInResidence(Location loc) {
        if (!UtilsHandler.getDepend().ResidenceEnabled()) {
            return false;
        }
        return UtilsHandler.getDepend().getResidenceUtils().isInResidence(loc);
    }
}
