package tw.momocraft.coreplus.api;

import org.bukkit.Location;
import org.bukkit.entity.Player;

public interface EffectInterface {

    /**
     *
     * @param pluginName the sending plugin name.
     * @param loc the target location.
     * @param type the type of particle. Can be custom or vanilla type.
     */
    void spawnParticle(String pluginName, Location loc, String type);

    /**
     *
     * @param pluginName the sending plugin name.
     * @param player the target player.
     * @param loc the target location.
     * @param type the type of sound. Can be custom or vanilla type.
     */
    void sendSound(String pluginName, Player player, Location loc, String type);

}
