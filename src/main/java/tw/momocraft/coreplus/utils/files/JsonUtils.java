package tw.momocraft.coreplus.utils.files;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import org.bukkit.Bukkit;
import tw.momocraft.coreplus.CorePlus;
import tw.momocraft.coreplus.handlers.ConfigHandler;
import tw.momocraft.coreplus.handlers.UtilsHandler;

import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JsonUtils {

    private final List<String> langList = new ArrayList<>();
    private final Map<String, JsonObject> jsonMap = new HashMap<>();

    public JsonUtils() {
        loadLang();
        //loadGroup(ConfigHandler.getPluginName(), "");

        sendLoadedMsg();
    }

    private void sendLoadedMsg() {
        UtilsHandler.getLang().sendConsoleMsg(ConfigHandler.getPluginPrefix(),
                "&fLoaded Language files: " + langList.toString());
        UtilsHandler.getLang().sendConsoleMsg(ConfigHandler.getPluginPrefix(),
                "&fLoaded Json files: " + jsonMap.keySet().toString());
    }

    public boolean load(String pluginName, String group, String filePath) {
        File file = new File(filePath);
        try {
            Gson gson = new Gson();
            JsonObject json = gson.fromJson(new FileReader(file), JsonObject.class);
            jsonMap.put(group, json);
            return true;
        } catch (Exception ex) {
            UtilsHandler.getLang().sendErrorMsg(pluginName, "Cannot load the Json file: " + filePath);
            UtilsHandler.getLang().sendDebugTrace(true, pluginName, ex);
            return false;
        }
    }

    private void loadGroup(String pluginName, String group) {
        String filePath;
        switch (group) {
            case "example":
                filePath = Bukkit.getServer().getWorldContainer().getPath() + "//plugins//ExamplePlugin//example.json";
                break;
            default:
                return;
        }
        load(pluginName, group, filePath);
    }

    private void loadLang() {
        String filePath = CorePlus.getInstance().getDataFolder().getPath() + "\\Vanilla-Translation";
        File file = new File(filePath);
        String[] fileList = file.list();
        if (fileList == null) {
            return;
        }
        String langName;
        for (String fileName : fileList) {
            if (!fileName.endsWith(".json")) {
                continue;
            }
            langName = fileName.replace(".json", "");
            load(ConfigHandler.getPluginName(), "lang_" + langName, filePath + "//" + langName);
            langList.add(fileName);
        }
    }

    public String getValue(String pluginName, String group, String input) {
        try {
            if (jsonMap.containsKey(group)) {
                loadGroup(pluginName, group);
            }
            return jsonMap.get(group).get(input).toString();
        } catch (Exception ex) {
            return null;
        }
    }
}
