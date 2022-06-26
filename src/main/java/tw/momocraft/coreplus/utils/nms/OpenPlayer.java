package tw.momocraft.coreplus.utils.nms;

public class OpenPlayer {
/*
    public OpenPlayer(CraftServer server, ServerPlayer entity) {
        super(server, entity);
    }

    @Override
    public void loadData() {
        // See CraftPlayer#loadData
        CompoundTag loaded = this.server.getHandle().playerIo.load(this.getHandle());
        if (loaded != null) {
            readExtraData(loaded);
        }
    }

    @Override
    public void saveData() {
        ServerPlayer player = this.getHandle();
        // See net.minecraft.world.level.storage.PlayerDataStorage#save(EntityHuman)
        try {
            PlayerDataStorage worldNBTStorage = player.server.getPlayerList().playerIo;

            CompoundTag playerData = player.saveWithoutId(new CompoundTag());
            setExtraData(playerData);

            if (!isOnline()) {
                // Special case: save old vehicle data
                CompoundTag oldData = worldNBTStorage.load(player);

                if (oldData != null && oldData.contains("RootVehicle", 10)) {
                    // See net.minecraft.server.PlayerList#a(NetworkManager, EntityPlayer) and net.minecraft.server.EntityPlayer#b(NBTTagCompound)
                    playerData.put("RootVehicle", oldData.getCompound("RootVehicle"));
                }
            }

            File file = File.createTempFile(player.getStringUUID() + "-", ".dat", worldNBTStorage.getPlayerDir());
            NbtIo.writeCompressed(playerData, file);
            File file1 = new File(worldNBTStorage.getPlayerDir(), player.getStringUUID() + ".dat");
            File file2 = new File(worldNBTStorage.getPlayerDir(), player.getStringUUID() + ".dat_old");
            Util.safeReplaceFile(file1, file, file2);
        } catch (Exception e) {
            LogManager.getLogger().warn("Failed to save player data for {}: {}", player.getName().getString(), e.getMessage());
        }
    }

 */
}
