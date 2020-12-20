package tw.momocraft.coreplus.utils;

import io.lumine.xikage.mythicmobs.MythicMobs;
import org.bukkit.entity.Entity;

public class MythicMobsAPI {

    public static boolean isMythicMob(Entity entity) {
        return MythicMobs.inst().getAPIHelper().isMythicMob(entity);
    }

    public static boolean isMythicMobName(String name) {
        return MythicMobs.inst().getAPIHelper().getMythicMob(name) == null;
    }
}
