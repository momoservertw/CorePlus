package tw.momocraft.coreplus.utils.player;

public class OfflinePlayerManager {
    /*

    private Field bukkitEntity;

    public OfflinePlayerManager() {
        try {
            bukkitEntity = Entity.class.getDeclaredField("bukkitEntity");
        } catch (NoSuchFieldException e) {
            //"Unable to obtain field to inject custom save process - players' mounts may be deleted when loaded."
            bukkitEntity = null;
        }
    }

    public static ServerPlayer getHandle(final Player player) {
        if (player instanceof CraftPlayer) {
            return ((CraftPlayer) player).getHandle();
        }
        Server server = player.getServer();
        ServerPlayer nmsPlayer = null;

        if (server instanceof CraftServer) {
            nmsPlayer = ((CraftServer) server).getHandle().getPlayer(player.getUniqueId());
        }

        if (nmsPlayer == null) {
            // Could use reflection to examine fields, but it's honestly not worth the bother.
            throw new RuntimeException("Unable to fetch EntityPlayer from provided Player implementation");
        }
        return nmsPlayer;
    }

    public Player loadPlayer(OfflinePlayer offlinePlayer) {
        // Ensure player has data
        if (!offlinePlayer.hasPlayedBefore()) {
            return null;
        }
        // Create a profile and entity to load the player data
        // See net.minecraft.server.PlayerList#attemptLogin
        GameProfile profile = new GameProfile(offlinePlayer.getUniqueId(),
                offlinePlayer.getName() != null ? offlinePlayer.getName() : offlinePlayer.getUniqueId().toString());
        MinecraftServer server = ((CraftServer) Bukkit.getServer()).getServer();
        ServerLevel worldServer = server.getLevel(Level.OVERWORLD);

        if (worldServer == null) {
            return null;
        }

        ServerPlayer entity = new ServerPlayer(server, worldServer, profile);

        try {
            injectPlayer(entity);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        // Get the bukkit entity
        Player target = entity.getBukkitEntity();
        if (target != null) {
            // Load data
            target.loadData();
        }
        // Return the entity
        return target;
    }

    void injectPlayer(ServerPlayer player) throws IllegalAccessException {
        if (bukkitEntity == null) {
            return;
        }

        bukkitEntity.setAccessible(true);

        bukkitEntity.set(player, new OpenPlayer(player.server.server, player));
    }

    public Player inject(Player player) {
        try {
            ServerPlayer nmsPlayer = getHandle(player);
            if (nmsPlayer.getBukkitEntity() instanceof OpenPlayer openPlayer) {
                return openPlayer;
            }
            injectPlayer(nmsPlayer);
            return nmsPlayer.getBukkitEntity();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            return player;
        }
    }
     */
}
