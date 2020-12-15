package tw.momocraft.coreplus.api;

import org.bukkit.Location;
import tw.momocraft.coreplus.utils.locationutils.LocationMap;

import java.util.List;

public interface LocationInterface {

    List<LocationMap> getSpeLocMaps(String file, String path);

    boolean checkLocation(Location loc, List<LocationMap> locMaps);
}
