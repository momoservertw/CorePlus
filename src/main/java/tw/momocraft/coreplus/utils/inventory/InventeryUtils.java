package tw.momocraft.coreplus.utils.inventory;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class InventeryUtils {

    private ItemStack getSlotItem(Player player, String slot) {
        // Inventory
        if (slot.matches("[0-9]+")) {
            return player.getInventory().getItem(Integer.parseInt(slot));
        }
        switch (slot) {
            // Equipment
            case "head":
                return player.getInventory().getHelmet();
            case "chest":
                return player.getInventory().getChestplate();
            case "legs":
                return player.getInventory().getLeggings();
            case "feet":
                return player.getInventory().getBoots();
            case "hand":
                return player.getInventory().getItemInMainHand();
            case "off_hand":
                return player.getInventory().getItemInOffHand();
            // Crafting
            case "crafting[1]":
            case "crafting[2]":
            case "crafting[3]":
            case "crafting[4]":
                return player.getOpenInventory().getTopInventory().getItem(Integer.parseInt(slot.replace("CRAFTING[", "").replace("]", "")));
        }
        return null;
    }

    private static void cleanslot(Player player, String slot) {
        // Inventory
        if (slot.matches("[0-9]+")) {
            player.getInventory().setItem(Integer.parseInt(slot), null);
        }
        switch (slot) {
            // ALL
            case "all":
                player.getInventory().clear();
                break;
            // Equipment
            case "head":
                player.getInventory().setHelmet(null);
                break;
            case "chest":
                player.getInventory().setChestplate(null);
                break;
            case "legs":
                player.getInventory().setLeggings(null);
                break;
            case "feet":
                player.getInventory().setBoots(null);
                break;
            case "hand":
                player.getInventory().setItemInMainHand(null);
                break;
            case "off_hand":
                player.getInventory().setItemInOffHand(null);
                break;
            // Crafting
            case "crafting[1]":
            case "crafting[2]":
            case "crafting[3]":
            case "crafting[4]":
                player.getOpenInventory().getTopInventory().setItem(Integer.parseInt(slot.replace("CRAFTING[", "").replace("]", "")), null);
        }
    }
}
