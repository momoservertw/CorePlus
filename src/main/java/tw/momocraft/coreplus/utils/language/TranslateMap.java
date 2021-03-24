package tw.momocraft.coreplus.utils.language;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class TranslateMap {

    private Map<Player, String> playerMap;
    private Map<String, String> playerNameMap;
    private Map<UUID, String> playerUUIDMap;
    private Map<OfflinePlayer, String> offlinePlayerMap;
    private Map<Entity, String> entityMap;
    private Map<EntityType, String> entityTypeMap;

    private Map<Block, String> blockMap;
    private Map<ItemStack, String> itemStackMap;
    private Map<Material, String> materialMap;

    private Map<Location, String> locationMap;

    public void putPlayer(Player target, String name) {
        if (playerMap == null)
            playerMap = new HashMap<>();
        playerMap.put(target, name);
    }

    public void putPlayerName(String target, String name) {
        if (playerNameMap == null)
            playerNameMap = new HashMap<>();
        playerNameMap.put(target, name);
    }

    public void putPlayerUUID(UUID target, String name) {
        if (playerUUIDMap == null)
            playerUUIDMap = new HashMap<>();
        playerUUIDMap.put(target, name);
    }

    public void putOfflinePlayer(OfflinePlayer target, String name) {
        if (offlinePlayerMap == null)
            offlinePlayerMap = new HashMap<>();
        offlinePlayerMap.put(target, name);
    }

    public void putEntity(Entity target, String name) {
        if (entityMap == null)
            entityMap = new HashMap<>();
        entityMap.put(target, name);
    }

    public void putEntityType(EntityType target, String name) {
        if (entityTypeMap == null)
            entityTypeMap = new HashMap<>();
        entityTypeMap.put(target, name);
    }

    public void putBlock(Block target, String name) {
        if (blockMap == null)
            blockMap = new HashMap<>();
        blockMap.put(target, name);
    }

    public void putItemStack(ItemStack target, String name) {
        if (itemStackMap == null)
            itemStackMap = new HashMap<>();
        itemStackMap.put(target, name);
    }

    public void putMaterial(Material target, String name) {
        if (materialMap == null)
            materialMap = new HashMap<>();
        materialMap.put(target, name);
    }

    public void putLocation(Location target, String name) {
        if (locationMap == null)
            locationMap = new HashMap<>();
        locationMap.put(target, name);
    }

    public Map<Player, String> getPlayerMap() {
        return playerMap;
    }

    public Map<String, String> getPlayerNameMap() {
        return playerNameMap;
    }

    public Map<UUID, String> getPlayerUUIDMap() {
        return playerUUIDMap;
    }

    public Map<OfflinePlayer, String> getOfflinePlayerMap() {
        return offlinePlayerMap;
    }

    public Map<Entity, String> getEntityMap() {
        return entityMap;
    }

    public Map<EntityType, String> getEntityTypeMap() {
        return entityTypeMap;
    }

    public Map<Block, String> getBlockMap() {
        return blockMap;
    }

    public Map<ItemStack, String> getItemStackMap() {
        return itemStackMap;
    }

    public Map<Material, String> getMaterialMap() {
        return materialMap;
    }

    public Map<Location, String> getLocationMap() {
        return locationMap;
    }
}
