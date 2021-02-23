package tw.momocraft.coreplus.api;

import org.bukkit.entity.Entity;

public interface EntityInterface {

    /**
     * Checking the entity is MythicMob or not.
     * It doesn't work in CreatureSpawnEvent.
     *
     * @param entity the checking entity.
     * @return if the entity is a MythicMob.
     */
    boolean isMythicMob(Entity entity);

    /**
     * Checking the MythicMobs has this name of custom mob.
     *
     * @param name the checking name.
     * @return if the MythicMobs has this name of custom mob.
     */
    boolean isMythicMobName(String name);

    /**
     * Getting the Entity's MythicMob name.
     * It will return null if entity is noy MythicMob.
     *
     * @param entity the checking entity.
     * @return the Entity's MythicMob name.
     */
    String getMythicMobName(Entity entity);
}
