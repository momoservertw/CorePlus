package tw.momocraft.coreplus.utils.permission;

import net.luckperms.api.LuckPerms;
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
import tw.momocraft.coreplus.handlers.UtilsHandler;

import java.util.Set;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

public class LuckPermsAPI {
    LuckPerms luckPerms = null;

    public LuckPermsAPI() {
        RegisteredServiceProvider<LuckPerms> provider = Bukkit.getServicesManager().getRegistration(LuckPerms.class);
        if (provider != null) {
            luckPerms = provider.getProvider();
        }
    }

    public User getUser(UUID uniqueId) {
        UserManager userManager = luckPerms.getUserManager();
        CompletableFuture<User> userFuture = userManager.loadUser(uniqueId);
        return userFuture.join();
    }

    public boolean hasPermission(UUID uuid, String permission) {
        User user = getUser(uuid);
        if (user == null)
            return false;
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
        if (user == null)
            return null;
        return user.getNodes().stream()
                .filter(NodeType.INHERITANCE::matches)
                .map(NodeType.INHERITANCE::cast)
                .map(InheritanceNode::getGroupName)
                .collect(Collectors.toSet());
    }

    public String getPrefix(UUID uuid) {
        User user = getUser(uuid);
        if (user == null)
            return "";
        ContextManager cm = luckPerms.getContextManager();
        QueryOptions queryOptions = cm.getQueryOptions(user).orElse(cm.getStaticQueryOptions());
        return user.getCachedData().getMetaData(queryOptions).getPrefix();
    }

    public String getSuffix(UUID uuid) {
        User user = getUser(uuid);
        if (user == null)
            return "";
        ContextManager cm = luckPerms.getContextManager();
        QueryOptions queryOptions = cm.getQueryOptions(user).orElse(cm.getStaticQueryOptions());
        return user.getCachedData().getMetaData(queryOptions).getSuffix();
    }

    public String getPlayerPrimaryGroup(UUID uuid) {
        User user = getUser(uuid);
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

    public void addPermission(String prefix, UUID uuid, String permission) {
        User user = getUser(uuid);
        if (user == null) {
            UtilsHandler.getLang().sendErrorMsg(prefix, "can not found LuckPerms user: " + uuid.toString());
            return;
        }
        user.data().add(PermissionNode.builder(permission).build());
        luckPerms.getUserManager().saveUser(user);
    }

    public void removePermission(String prefix, UUID uuid, String permission) {
        User user = getUser(uuid);
        if (user == null) {
            UtilsHandler.getLang().sendErrorMsg(prefix, "can not found LuckPerms user: " + uuid.toString());
            return;
        }
        user.data().remove(PermissionNode.builder(permission).build());
        luckPerms.getUserManager().saveUser(user);
    }
}
