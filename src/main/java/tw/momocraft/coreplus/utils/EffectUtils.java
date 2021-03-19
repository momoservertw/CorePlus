package tw.momocraft.coreplus.utils;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import tw.momocraft.coreplus.api.EffectInterface;
import tw.momocraft.coreplus.utils.customcommands.ParticleUtils;
import tw.momocraft.coreplus.utils.customcommands.SoundUtils;

public class EffectUtils implements EffectInterface {

    @Override
    public void spawnParticle(String pluginName, Location loc, String type) {
        ParticleUtils.spawnParticle(pluginName, loc, type);
    }

    @Override
    public void sendSound(String pluginName, Player player, Location loc, String type) {
        SoundUtils.sendSound(pluginName, player, loc, type);
    }
}
