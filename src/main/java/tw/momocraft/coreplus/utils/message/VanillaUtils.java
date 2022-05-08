package tw.momocraft.coreplus.utils.message;

import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import tw.momocraft.coreplus.handlers.ConfigHandler;
import tw.momocraft.coreplus.handlers.UtilsHandler;

public class VanillaUtils {

    public String getValinaName(Player player, String input, String type) {
        if (!ConfigHandler.getConfigPath().isVanillaTrans())
            return input;
        if (input == null)
            return "";
        return getValinaNode(getLocal(player), input, type);
    }

    public String getValinaName(String local, String input, String type) {
        if (!ConfigHandler.getConfigPath().isVanillaTrans())
            return input;
        if (input == null)
            return "";
        return getValinaNode(local, input, type);
    }

    public String getValinaName(String input, String type) {
        if (!ConfigHandler.getConfigPath().isVanillaTrans())
            return input;
        if (input == null)
            return "";
        return getValinaNode(ConfigHandler.getConfigPath().getVanillaTransLocal(), input, type);
    }


    public String getValinaNode(String local, String input, String type) {
        if (local == null || local.equals("")) {
            local = ConfigHandler.getConfigPath().getVanillaTransLocal();
            if (local == null)
                return input;
        }
        if (type.equals("entity")) {
            try {
                EntityType entityType = EntityType.valueOf(input);
                return getLocalLang(local, "entity.minecraft." + input.toLowerCase());
            } catch (Exception ex) {
                return input;
            }
        }
        if (type.equals("material")) {
            try {
                Material material = Material.valueOf(input);
                if (material.isBlock())
                    return getLocalLang(local, "block.minecraft." + input.toLowerCase());
                return getLocalLang(local, "item.minecraft." + input.toLowerCase());
            } catch (Exception ex) {
                return input;
            }
        }
        return input;
    }

    public String getLocal() {
        return ConfigHandler.getConfigPath().getVanillaTransLocal();
    }

    public String getLocal(CommandSender sender) {
        Player player = null;
        if (sender instanceof Player)
            player = (Player) sender;
        return getLocal(player);
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

    private String getLocalLang(String local, String input) {
        String lang = UtilsHandler.getFile().getJson().getValue(ConfigHandler.getPluginName(), "lang_" + local, input);
        if (lang == null)
            return input;
        try {
            return lang.replaceAll("\"", "");
        } catch (Exception ex) {
            return input;
        }
    }
}
