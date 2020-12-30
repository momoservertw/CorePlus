package tw.momocraft.coreplus.utils.permission;

import net.luckperms.api.LuckPerms;
import net.luckperms.api.model.user.User;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.RegisteredServiceProvider;
import java.util.UUID;

public class LuckPermsAPI {
    LuckPerms luckPerms = null;

    public LuckPermsAPI() {
        RegisteredServiceProvider<LuckPerms> provider = Bukkit.getServicesManager().getRegistration(LuckPerms.class);
        if (provider != null) {
            luckPerms = provider.getProvider();
        }
    }

    public String getPlayerPrimaryGroup(UUID uuid) {
        User user = luckPerms.getUserManager().getUser(uuid);
        if (user == null)
            return "";
        return user.getPrimaryGroup();
    }

    public boolean setPlayerPrimaryGroup(UUID uuid, String group) {
        User user = luckPerms.getUserManager().getUser(uuid);
        if (user == null)
            return false;
        if (luckPerms.getGroupManager().getGroup(group) == null)
            return false;
        user.setPrimaryGroup(group);
        return true;
    }

    public boolean isPlayerInGroup(Player player, String group) {
        return player.hasPermission("group." + group);
    }

/*
    public String getStack(UUID uuid, String stack) {
        User user = luckPerms.getUserManager().getUser(uuid);
        for (Node node : user.getNodes()) {
            node.get
        }

        luckPerms.getTrackManager().getTrack("donate"). (user);
        luckPerms.getTrackManager().getTrack("donate").demote(user, );
        luckPerms.getTrackManager().getTrack("donate").promote(user);
    }

    public boolean isPlayerInherited(UUID uuid) {
        @NonNull CompletableFuture<User> user = luckPerms.getUserManager().loadUser(uuid);

        Collection<Group> inheritedGroups = user.getInheritedGroups(user.getQueryOptions());
        return inheritedGroups.stream().anyMatch(g -> g.getName().equals("admin"));
    }

    public boolean isAdmin(UUID who) {
        User user = luckPerms.getUserManager().loadUser(who);

        luckPerms.get
        Collection<Group> inheritedGroups = user.getInheritedGroups(user.getQueryOptions());
        return inheritedGroups.stream().anyMatch(g -> g.getName().equals("admin"));
    }

 */
}
