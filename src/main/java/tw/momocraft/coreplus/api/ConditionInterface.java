package tw.momocraft.coreplus.api;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import tw.momocraft.coreplus.utils.conditions.BlocksMap;
import tw.momocraft.coreplus.utils.conditions.LocationMap;

import java.util.List;

public interface ConditionInterface {

    boolean checkLocation(Location loc, List<LocationMap> locMaps, boolean def);

    List<LocationMap> getSpeLocMaps(String file, String path);

    List<BlocksMap> getSpeBlocksMaps(String file, String path);

    boolean checkBlocks(Location loc, List<BlocksMap> blocksMaps, boolean def);

    boolean checkFlag(Player player, Location loc, String flag, boolean def);
}
