package tw.momocraft.coreplus.utils.conditions;

import me.RockinChaos.itemjoin.api.ItemJoinAPI;

import org.bukkit.Bukkit;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import tw.momocraft.coreplus.handlers.ConfigHandler;


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
        String menuIJ = ConfigHandler.getConfigPath().getMenuIJ();
        if (!menuIJ.equals("")) {
            if (ij.getNode(itemStack) != null) {
                return ij.getNode(itemStack).equals(menuIJ);
            }
        }
        return false;
    }
}
