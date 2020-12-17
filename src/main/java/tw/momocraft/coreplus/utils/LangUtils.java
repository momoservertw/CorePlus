package tw.momocraft.coreplus.utils;


import com.meowj.langutils.lang.LanguageHelper;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class LangUtils {

    public static String getLocalLang(String input) {
        try {
            return LanguageHelper.getItemName(new ItemStack(Material.getMaterial(input)), "en_us");
        } catch (Exception ignored) {
        }
        try {
            return LanguageHelper.getEntityName(EntityType.valueOf(input), "en_us");
        } catch (Exception ignored) {
        }
        return input;
    }

    public static String getLocalLang(String input, Player player) {
        try {
            return LanguageHelper.getItemName(new ItemStack(Material.getMaterial(input)), player);
        } catch (Exception ignored) {
        }
        try {
            return LanguageHelper.getEntityName(EntityType.valueOf(input), "en_us");
        } catch (Exception ignored) {
        }
        return input;
    }

    public static String getItemType(String itemType) {
        try {
            return LanguageHelper.getItemName(new ItemStack(Material.getMaterial(itemType)), "en_us");
        } catch (Exception e) {
            return itemType;
        }
    }

    public static String getItemType(String itemType, Player player) {
        try {
            return LanguageHelper.getItemName(new ItemStack(Material.getMaterial(itemType)), player);
        } catch (Exception e) {
            return itemType;
        }
    }

    public static String getEntityType(String entityType) {
        try {
            return LanguageHelper.getEntityName(EntityType.valueOf(entityType), "en_us");
        } catch (Exception e) {
            return entityType;
        }
    }

    public static String getEntityType(String entityType, Player player) {
        try {
            return LanguageHelper.getEntityName(EntityType.valueOf(entityType), player);
        } catch (Exception e) {
            return entityType;
        }
    }
}
