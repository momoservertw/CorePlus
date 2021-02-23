package tw.momocraft.coreplus.utils.language;

import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import tw.momocraft.coreplus.handlers.ConfigHandler;
import tw.momocraft.coreplus.handlers.UtilsHandler;

public class VanillaUtils {


    public String getValinaName(String pluginName, Player player, String input, String type) {
        if (!ConfigHandler.getConfigPath().isVanillaTrans()) {
            return input;
        }
        if (input == null) {
            return "";
        }
        return getValinaNode(pluginName, getLocal(player), input, type);
    }

    public String getValinaName(String pluginName, String input, String type) {
        if (!ConfigHandler.getConfigPath().isVanillaTrans()) {
            return input;
        }
        if (input == null) {
            return "";
        }
        return getValinaNode(pluginName, ConfigHandler.getConfigPath().getVanillaTransLocal(), input, type);
    }


    public String getValinaNode(String pluginName, String local, String input, String type) {
        if (local == null || local.equals("")) {
            local = ConfigHandler.getConfigPath().getVanillaTransLocal();
            if (local == null)
                return input;
        }
        if (type.equals("entity")) {
            try {
                EntityType entityType = EntityType.valueOf(input);
                return getLocalLang(pluginName, "entity.minecraft." + input.toLowerCase(), local);
            } catch (Exception ex) {
                return input;
            }
        }
        if (type.equals("material")) {
            try {
                Material material = Material.valueOf(input);
                if (material.isBlock()) {
                    return getLocalLang(pluginName, "block.minecraft." + input.toLowerCase(), local);
                }
                return getLocalLang(pluginName, "item.minecraft." + input.toLowerCase(), local);
            } catch (Exception ex) {
                return input;
            }
        }
        return input;
    }

    public String getLocal(Player player) {
        if (ConfigHandler.getConfigPath().isVanillaTransForce()) {
            return ConfigHandler.getConfigPath().getVanillaTransLocal();
        } else if (player != null) {
            return player.getLocale();
        } else {
            return ConfigHandler.getConfigPath().getVanillaTransLocal();
        }
    }

    private String getLocalLang(String pluginName, String local, String input) {
        String lang = UtilsHandler.getJson().getValue(pluginName, local, "lang_" + input);
        try {
            return lang.replaceAll("\"", "");
        } catch (Exception ex) {
            UtilsHandler.getLang().sendDebugTrace(true, ConfigHandler.getPluginName(), ex);
            return input;
        }
    }
}
