package tw.momocraft.coreplus.utils.condition;

import me.RockinChaos.itemjoin.api.ItemJoinAPI;
import org.bukkit.Bukkit;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import tw.momocraft.coreplus.handlers.ConfigHandler;
import tw.momocraft.coreplus.handlers.UtilsHandler;

import java.util.Set;


public class ItemJoinUtils {

    private ItemJoinAPI ij;

    public ItemJoinUtils() {
        Plugin plugin = Bukkit.getServer().getPluginManager().getPlugin("ItemJoin");
        if (plugin != null) {
            ij = new ItemJoinAPI();
        }
    }

    public boolean isCustom(ItemStack itemStack) {
        if (ij.getNode(itemStack) != null) {
            return ij.getNode(itemStack) != null;
        }
        return false;
    }

    public boolean isMenu(ItemStack itemStack) {
        String menuIJ = ConfigHandler.getConfigPath().getMenuItemJoin();
        if (!menuIJ.equals("")) {
            if (ij.getNode(itemStack) != null) {
                return ij.getNode(itemStack).equals(menuIJ);
            }
        }
        return false;
    }

    public String getItemNode(ItemStack itemStack) {
        return ij.getNode(itemStack);
    }

    public ItemStack getItemStack(Player player, String node) {
        return ij.getItemStack(player, node);
    }

    public Set<String> getItemNodes() {
        ConfigurationSection config = UtilsHandler.getYaml().getConfig("itemjoin_items").getConfigurationSection("items");
        if (config != null) {
            return config.getKeys(false);
        }
        return null;
    }

    public ConfigurationSection getItemConfig() {
        return UtilsHandler.getYaml().getConfig("itemjoin_items").getConfigurationSection("items");
    }
}
