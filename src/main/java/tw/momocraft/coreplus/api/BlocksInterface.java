package tw.momocraft.coreplus.api;

import org.bukkit.Location;
import tw.momocraft.coreplus.utils.blocksutils.BlocksMap;

import java.util.List;

public interface BlocksInterface {

    List<BlocksMap> getSpeBlocksMaps(String file, String path);

    boolean checkBlocks(Location loc, List<BlocksMap> blocksMaps, boolean def);
}
