package tw.momocraft.coreplus.api;

import org.bukkit.Location;
import org.bukkit.entity.Player;

public interface ResidenceInterface {

    boolean checkFlag(Player player, Location loc, boolean check, String flag);
}
