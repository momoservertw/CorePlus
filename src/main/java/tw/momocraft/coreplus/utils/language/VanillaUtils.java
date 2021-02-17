package tw.momocraft.coreplus.utils.language;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import tw.momocraft.coreplus.CorePlus;
import tw.momocraft.coreplus.handlers.ConfigHandler;
import tw.momocraft.coreplus.handlers.UtilsHandler;

import java.io.File;
import java.io.FileReader;
import java.util.HashMap;
import java.util.Map;

public class VanillaUtils {

    private final Map<String, JsonObject> langProp = new HashMap<>();

    public VanillaUtils() {
        setUp();
    }

    private void setUp() {
        Gson gson = new Gson();
        File file = new File(CorePlus.getInstance().getDataFolder().getPath() + "\\Vanilla-Translation");
        String[] fileList = file.list();
        if (fileList == null) {
            return;
        }
        JsonObject json;
        for (String fileName : fileList) {
            if (!fileName.endsWith(".json")) {
                continue;
            }
            try {
                json = gson.fromJson(new FileReader(new File(file.getPath(), fileName)), JsonObject.class);
                langProp.put(fileName.replace(".json", ""), json);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        if (langProp.keySet().isEmpty()) {
            return;
        }
        sendLanguageHook();
    }

    private void sendLanguageHook() {
        UtilsHandler.getLang().sendConsoleMsg(ConfigHandler.getPluginPrefix(), "&fLanguage: " + langProp.keySet().toString().replaceAll("\\.json", ""));
    }

    public Map<String, JsonObject> getLangProp() {
        return langProp;
    }

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
            } catch (Exception e) {
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
            } catch (Exception e) {
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
        JsonObject jsonObject = getLangProp().get(local);
        try {
            return jsonObject.get(input).toString().replaceAll("\"", "");
        } catch (Exception e) {
            UtilsHandler.getLang().sendDebugTrace(ConfigHandler.isDebugging(), ConfigHandler.getPluginPrefix(), e);
            return input;
        }
    }
}
