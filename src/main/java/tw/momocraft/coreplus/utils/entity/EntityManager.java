package tw.momocraft.coreplus.utils.entity;

import io.lumine.mythic.bukkit.MythicBukkit;
import org.bukkit.entity.Entity;
import tw.momocraft.coreplus.api.CorePlusAPI;
import tw.momocraft.coreplus.api.EntityInterface;

public class EntityManager implements EntityInterface {

    @Override
    public boolean isMythicMob(Entity entity) {
        return MythicBukkit.inst().getAPIHelper().isMythicMob(entity);
    }

    @Override
    public boolean isMythicMobName(String name) {
        return MythicBukkit.inst().getAPIHelper().getMythicMob(name) == null;
    }

    @Override
    public String getMythicMobName(Entity entity) {
        if (!isMythicMob(entity))
            return null;
        return MythicBukkit.inst().getAPIHelper().getMythicMobInstance(entity).getType().getInternalName();
    }

    @Override
    public String getMythicMobDisplayName(Entity entity) {
        String mmName = CorePlusAPI.getEnt().getMythicMobName(entity);
        if (mmName == null)
            return null;
        return MythicBukkit.inst().getAPIHelper().getMythicMob(mmName).getDisplayName().get();
    }
}
