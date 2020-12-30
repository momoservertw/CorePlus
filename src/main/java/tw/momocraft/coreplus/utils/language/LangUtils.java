package tw.momocraft.coreplus.utils.language;


import com.meowj.langutils.lang.LanguageHelper;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import tw.momocraft.coreplus.handlers.ConfigHandler;

import java.util.Map;

public class LangUtils {

    public static String getMaterialType(Player player, String material) {
        try {
            return LanguageHelper.getItemName(new ItemStack(Material.getMaterial(material)), player);
        } catch (Exception e) {
            return material;
        }
    }

    public static String getMaterialType(String material) {
        try {
            return LanguageHelper.getItemName(new ItemStack(Material.getMaterial(material)), ConfigHandler.getConfigPath().getVanillaTransLocal());
        } catch (Exception e) {
            return material;
        }
    }

    public static String getEntityType(Player player, String entityType) {
        try {
            return LanguageHelper.getEntityName(EntityType.valueOf(entityType), player);
        } catch (Exception e) {
            return entityType;
        }
    }

    public static String getEntityType(String entityType) {
        try {
            return LanguageHelper.getEntityName(EntityType.valueOf(entityType), ConfigHandler.getConfigPath().getVanillaTransLocal());
        } catch (Exception e) {
            return entityType;
        }
    }

    public static String getEnchantment(Player player, Map.Entry<Enchantment, Integer> entry) {
        try {
            return LanguageHelper.getEnchantmentDisplayName(entry, player);
        } catch (Exception e) {
            return null;
        }
    }

    public static String getEnchantment(Map.Entry<Enchantment, Integer> entry) {
        try {
            return LanguageHelper.getEnchantmentDisplayName(entry, ConfigHandler.getConfigPath().getVanillaTransLocal());
        } catch (Exception e) {
            return null;
        }
    }
}