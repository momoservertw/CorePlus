package tw.momocraft.coreplus.utils.entities;

import io.lumine.xikage.mythicmobs.MythicMobs;
import org.bukkit.entity.Entity;
import tw.momocraft.coreplus.api.EntityInterface;

public class EntityUtils implements EntityInterface {

    @Override
    public boolean isMythicMob(Entity entity) {
        return MythicMobs.inst().getAPIHelper().isMythicMob(entity);
    }


    @Override
    public boolean isMythicMobName(String name) {
        return MythicMobs.inst().getAPIHelper().getMythicMob(name) == null;
    }
}
