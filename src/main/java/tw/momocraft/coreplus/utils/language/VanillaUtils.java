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
        File file = new File(CorePlus.getInstance().getDataFolder().getPath() + "\\VanillaLanguage");
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
        UtilsHandler.getLang().sendConsoleMsg(ConfigHandler.getPlugin(), "&fLanguage: " + langProp.keySet().toString().replaceAll("\\.json", ""));
    }

    public Map<String, JsonObject> getLangProp() {
        return langProp;
    }

    public String getValinaName(Player player, String input, String type) {
        if (!ConfigHandler.getConfigPath().isLanguage()) {
            return input;
        }
        return getValinaNode(getLocal(player), input, type);
    }

    public String getValinaName(String input, String type) {
        if (!ConfigHandler.getConfigPath().isLanguage()) {
            return input;
        }
        return getValinaNode(ConfigHandler.getConfigPath().getLanguageLocalTag(), input, type);
    }


    public String getValinaNode(String local, String input, String type) {
        if (!ConfigHandler.getConfigPath().isLanguage()) {
            return input;
        }
        if (input == null) {
            return "";
        }
        if (type.equals("entity")) {
            try {
                EntityType entityType = EntityType.valueOf(input);
                return getLocalLang("entity.minecraft." + input.toLowerCase(), local);
            } catch (Exception e) {
                UtilsHandler.getLang().sendDebugTrace(ConfigHandler.isDebugging(), ConfigHandler.getPlugin(), e);
                return input;
            }
        }
        if (type.equals("material")) {
            try {
                Material material = Material.getMaterial(input);
                if (material.isBlock()) {
                    return getLocalLang("block.minecraft." + input.toLowerCase(), local);
                }
                return getLocalLang("item.minecraft." + input.toLowerCase(), local);
            } catch (Exception e) {
                UtilsHandler.getLang().sendDebugTrace(ConfigHandler.isDebugging(), ConfigHandler.getPlugin(), e);
                return input;
            }
        }
        return input;
    }

    public String getLocal(Player player) {
        String local;
        if (ConfigHandler.getConfigPath().isLanguageLocal()) {
            local = ConfigHandler.getConfigPath().getLanguageLocalTag();
        } else if (player != null) {
            local = player.getLocale();
            System.out.println(local);
        } else {
            local = ConfigHandler.getConfigPath().getLanguageLocalTag();
        }
        return local;
    }

    private String getLocalLang(String input, String local) {
        JsonObject jsonObject = getLangProp().get(local);
        try {
            return jsonObject.get(input).toString().replaceAll("\"", "");
        } catch (Exception e) {
            UtilsHandler.getLang().sendDebugTrace(ConfigHandler.isDebugging(), ConfigHandler.getPlugin(), e);
            return input;
        }
    }
}
