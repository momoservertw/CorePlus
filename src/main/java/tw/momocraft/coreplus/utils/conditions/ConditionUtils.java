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

    public static LocationUtils getLoc() {
        return locationUtils;
    }

    public static void setBlockUtils(BlocksUtils blocks) {
        blocksUtils = blocks;
    }

    public static BlocksUtils getBlock() {
        return blocksUtils;
    }

    @Override
    public boolean checkLocation(Location loc, List<LocationMap> locMaps, boolean def) {
        return locationUtils.checkLocation(loc, locMaps, def);
    }

    @Override
    public List<LocationMap> getSpeLocMaps(List<String> list) {
        return locationUtils.getSpeLocMaps(list);
    }


    @Override
    public List<BlocksMap> getSpeBlocksMaps(List<String> list) {
        return blocksUtils.getSpeBlocksMaps(list);
    }

    @Override
    public boolean checkBlocks(Location loc, List<BlocksMap> blocksMaps, boolean def) {
        return blocksUtils.checkBlocks(loc, blocksMaps, def);
    }

    @Override
    public boolean checkFlag(Player player, Location loc, String flag, boolean def) {
        return UtilsHandler.getDepend().getResidenceUtils().checkFlag(player, loc, flag, def);
    }

    @Override
    public boolean checkFlag(Player player, Location loc, String flag, boolean def, boolean check) {
        return UtilsHandler.getDepend().getResidenceUtils().checkFlag(player, loc, flag, def, check);
    }
}
