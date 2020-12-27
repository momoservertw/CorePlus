package tw.momocraft.coreplus.api;

import org.bukkit.entity.Entity;

public interface EntityInterface {

    boolean isMythicMob(Entity entity);

    boolean isMythicMobName(String name);
}
