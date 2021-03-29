package tw.momocraft.coreplus.utils.entity;

import io.lumine.xikage.mythicmobs.MythicMobs;
import org.bukkit.entity.Entity;
import tw.momocraft.coreplus.api.EntityInterface;

public class EntityManager implements EntityInterface {

    @Override
    public boolean isMythicMob(Entity entity) {
        return MythicMobs.inst().getAPIHelper().isMythicMob(entity);
    }

    @Override
    public boolean isMythicMobName(String name) {
        return MythicMobs.inst().getAPIHelper().getMythicMob(name) == null;
    }

    @Override
    public String getMythicMobName(Entity entity) {
        if (!isMythicMob(entity))
            return null;
        return MythicMobs.inst().getAPIHelper().getMythicMobInstance(entity).getType().getInternalName();
    }
}
