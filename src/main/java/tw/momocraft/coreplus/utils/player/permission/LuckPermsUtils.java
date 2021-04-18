package tw.momocraft.coreplus.utils.player.permission;

import net.luckperms.api.LuckPerms;
import net.luckperms.api.LuckPermsProvider;
import net.luckperms.api.cacheddata.CachedPermissionData;
import net.luckperms.api.context.ContextManager;
import net.luckperms.api.model.user.User;
import net.luckperms.api.model.user.UserManager;
import net.luckperms.api.node.NodeType;
import net.luckperms.api.node.types.InheritanceNode;
import net.luckperms.api.node.types.PermissionNode;
import net.luckperms.api.query.QueryOptions;
import org.bukkit.Bukkit;
import org.bukkit.plugin.RegisteredServiceProvider;

import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

public class LuckPermsUtils {
    LuckPerms luckPerms = null;

    public LuckPermsUtils() {
        RegisteredServiceProvider<LuckPerms> provider = Bukkit.getServicesManager().getRegistration(LuckPerms.class);
        if (provider != null) {
            luckPerms = provider.getProvider();
        }
    }

    public UUID getUUID(String playerName) {
        User luckUser = LuckPermsProvider.get().getUserManager().getUser(playerName);
        if (luckUser != null)
            return luckUser.getUniqueId();
        return null;
    }

    public String getUserName(UUID uuid) {
        User luckUser = LuckPermsProvider.get().getUserManager().getUser(uuid);
        if (luckUser != null)
            return luckUser.getUsername();
        return null;
    }

    public User getUser(UUID uuid) {
        UserManager userManager = luckPerms.getUserManager();
        CompletableFuture<User> userFuture = userManager.loadUser(uuid);
        return userFuture.join();
    }

    public boolean hasPermission(UUID uuid, String permission) {
        User user = getUser(uuid);
        ContextManager cm = luckPerms.getContextManager();
        QueryOptions queryOptions = cm.getQueryOptions(user).orElse(cm.getStaticQueryOptions());
        CachedPermissionData permissionData = user.getCachedData().getPermissionData(queryOptions);
        return permissionData.checkPermission(permission).asBoolean();
    }

    public boolean isInheritedGroup(UUID uuid, String group) {
        return getInheritedGroups(uuid).contains(group);
    }

    public Set<String> getInheritedGroups(UUID uuid) {
        User user = getUser(uuid);
        return user.getNodes().stream()
                .filter(NodeType.INHERITANCE::matches)
                .map(NodeType.INHERITANCE::cast)
                .map(InheritanceNode::getGroupName)
                .collect(Collectors.toSet());
    }

    public String getPrefix(UUID uuid) {
        User user = getUser(uuid);
        if (user == null)
            return null;
        ContextManager cm = luckPerms.getContextManager();
        QueryOptions queryOptions = cm.getQueryOptions(user).orElse(cm.getStaticQueryOptions());
        return user.getCachedData().getMetaData(queryOptions).getPrefix();
    }

    public String getSuffix(UUID uuid) {
        User user = getUser(uuid);
        if (user == null)
            return null;
        ContextManager cm = luckPerms.getContextManager();
        QueryOptions queryOptions = cm.getQueryOptions(user).orElse(cm.getStaticQueryOptions());
        return user.getCachedData().getMetaData(queryOptions).getSuffix();
    }

    public String getPlayerPrimaryGroup(UUID uuid) {
        User user = getUser(uuid);
        if (user == null)
            return null;
        return user.getPrimaryGroup();
    }

    public boolean setPlayerPrimaryGroup(UUID uuid, String group) {
        User user = getUser(uuid);
        if (user == null)
            return false;
        if (luckPerms.getGroupManager().getGroup(group) == null)
            return false;
        user.setPrimaryGroup(group);
        return true;
    }

    public void addPermission(UUID uuid, String permission) {
        User user = getUser(uuid);
        if (user == null)
            return;
        user.data().add(PermissionNode.builder(permission).build());
        luckPerms.getUserManager().saveUser(user);
    }

    public void removePermission(UUID uuid, String permission) {
        User user = getUser(uuid);
        if (user == null)
            return;
        user.data().remove(PermissionNode.builder(permission).build());
        luckPerms.getUserManager().saveUser(user);
    }

    public List<String> getAllPerms(UUID uuid) {
        User user = getUser(uuid);
        if (user == null)
            return null;
        ContextManager cm = luckPerms.getContextManager();
        QueryOptions queryOptions = cm.getQueryOptions(user).orElse(cm.getStaticQueryOptions());
        CachedPermissionData permissionData = user.getCachedData().getPermissionData(queryOptions);
        Map<String, Boolean> permissionMap = permissionData.getPermissionMap();
        List<String> permList = new ArrayList<>();
        for (String key : permissionMap.keySet()) {
            if (permissionMap.get(key)) {
                permList.add(key);
            }
        }
        return permList;
    }
}
