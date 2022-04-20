package tw.momocraft.coreplus.utils.effect;

import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.scheduler.BukkitRunnable;
import tw.momocraft.coreplus.CorePlus;
import tw.momocraft.coreplus.handlers.ConfigHandler;
import tw.momocraft.coreplus.handlers.UtilsHandler;

public class ParticleUtils {

    public static void spawnParticle(String pluginName, Location loc, String type) {
        ParticleMap particleMap = ConfigHandler.getConfigPath().getParticleProp().get(type);
        if (particleMap == null) {
            try {
                loc.getWorld().spawnParticle(Particle.valueOf(type), loc, 0);
            } catch (Exception ex) {
                UtilsHandler.getMsg().sendErrorMsg(pluginName, "Can not find the particle type: \"" + type + "\"");
                UtilsHandler.getMsg().sendDebugTrace(ConfigHandler.isDebug(), pluginName, ex);
            }
            return;
        }
        spawnParticle(loc, particleMap);
    }

    public static void spawnParticle(Location loc, ParticleMap particleMap) {
        Particle particle = particleMap.getType();
        int amount = particleMap.getAmount();
        int times = particleMap.getTimes();
        double offsetX = particleMap.getOffsetX();
        double offsetY = particleMap.getOffsetY();
        double offsetZ = particleMap.getOffsetZ();
        double extra = particleMap.getExtra();

        // Material
        Material material = particleMap.getMaterial();
        // Color
        Particle.DustOptions dustOptions = null;
        String colorType = particleMap.getColorType();
        if (colorType != null) {
            Color color;
            if (colorType.equals("rgb")) {
                color = Color.fromRGB(255, 0, 0);
            } else {
                color = UtilsHandler.getUtil().getColor(particleMap.getColorType());
            }
            dustOptions = new Particle.DustOptions(color, 1);
        }
        Particle.DustOptions finalDustOptions = dustOptions;

        new BukkitRunnable() {
            int i = 1;

            @Override
            public void run() {
                if (i > times) {
                    cancel();
                } else {
                    ++i;
                    if (particle.equals(Particle.REDSTONE) || particle.equals(Particle.SPELL_MOB) || particle.equals(Particle.SPELL_MOB_AMBIENT)) {
                        loc.getWorld().spawnParticle(Particle.REDSTONE, loc, amount, offsetX, offsetY, offsetZ, extra, finalDustOptions);
                    } else if (particle.equals(Particle.BLOCK_CRACK)) {
                        loc.getWorld().spawnParticle(Particle.BLOCK_CRACK, loc, amount, offsetX, offsetY, offsetZ, extra, material);
                    } else {
                        loc.getWorld().spawnParticle(particle, loc, amount, offsetX, offsetY, offsetZ, extra);
                    }
                }
            }
        }.runTaskTimer(CorePlus.getInstance(), 0, particleMap.getInterval());
    }
}
