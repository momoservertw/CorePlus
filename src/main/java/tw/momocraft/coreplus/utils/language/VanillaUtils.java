package tw.momocraft.coreplus.utils.language;

import com.google.gson.JsonObject;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import tw.momocraft.coreplus.handlers.ConfigHandler;
import tw.momocraft.coreplus.handlers.UtilsHandler;

public class VanillaUtils {


    public String getValinaName(Player player, String input, String type) {
        if (!ConfigHandler.getConfigPath().isVanillaTrans()) {
            return input;
        }
        if (input == null) {
            return "";
        }
        return getValinaNode(getLocal(player), input, type);
    }

    public String getValinaName(String input, String type) {
        if (!ConfigHandler.getConfigPath().isVanillaTrans()) {
            return input;
        }
        if (input == null) {
            return "";
        }
        return getValinaNode(ConfigHandler.getConfigPath().getVanillaTransLocal(), input, type);
    }


    public String getValinaNode(String local, String input, String type) {
        if (local == null || local.equals("")) {
            return input;
        }
        if (type.equals("entity")) {
            try {
                EntityType entityType = EntityType.valueOf(input);
                return getLocalLang("entity.minecraft." + input.toLowerCase(), local);
            } catch (Exception ex) {
                return input;
            }
        }
        if (type.equals("material")) {
            try {
                Material material = Material.valueOf(input);
                if (material.isBlock()) {
                    return getLocalLang("block.minecraft." + input.toLowerCase(), local);
                }
                return getLocalLang("item.minecraft." + input.toLowerCase(), local);
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

    private String getLocalLang(String input, String local) {
        JsonObject jsonObject = UtilsHandler.getJson().getJsonMap().get(local + ".json");
        try {
            return jsonObject.get(input).toString().replaceAll("\"", "");
        } catch (Exception ex) {
            UtilsHandler.getLang().sendDebugTrace(true, ConfigHandler.getPluginName(), ex);
            return input;
        }
    }
}
