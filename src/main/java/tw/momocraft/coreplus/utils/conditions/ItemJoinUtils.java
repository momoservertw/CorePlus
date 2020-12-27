<<<<<<< e5811d5844a80d4babc72785c65de69bd5a57b60:src/main/java/tw/momocraft/coreplus/utils/ItemJoinUtils.java
package tw.momocraft.coreplus.utils;

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
=======
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
>>>>>>> Rewriting...:src/main/java/tw/momocraft/coreplus/utils/conditions/ItemJoinUtils.java
