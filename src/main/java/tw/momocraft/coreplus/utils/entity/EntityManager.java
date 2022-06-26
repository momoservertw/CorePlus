package tw.momocraft.coreplus.utils.entity;

import io.lumine.mythic.bukkit.MythicBukkit;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import tw.momocraft.coreplus.api.EntityInterface;
import tw.momocraft.coreplus.handlers.UtilsHandler;

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
        String mmName = UtilsHandler.getEntity().getMythicMobName(entity);
        if (mmName == null)
            return null;
        return MythicBukkit.inst().getAPIHelper().getMythicMob(mmName).getDisplayName().get();
    }

    @Override
    public boolean isMyPet(Entity entity) {
        return entity.hasMetadata("MyPet");
    }

    @Override
    public boolean isNPC(Entity entity) {
        return entity.hasMetadata("NPC");
    }

    @Override
    public boolean isInvisibleArmorStand(Entity entity) {
        if (!(entity instanceof ArmorStand armorStand))
            return false;
        return armorStand.isVisible();
    }
}
