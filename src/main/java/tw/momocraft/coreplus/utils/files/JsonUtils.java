package tw.momocraft.coreplus.utils.YamlUtils;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
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
        loadLangFile();
        loadFile(ConfigHandler.getPluginName(), "server.properties", null);
        UtilsHandler.getLang().sendConsoleMsg(ConfigHandler.getPluginPrefix(),
                "&fLanguage: " + langList.toString());
        UtilsHandler.getLang().sendConsoleMsg(ConfigHandler.getPluginPrefix(),
                "&fJson file: " + jsonMap.keySet().toString());
    }

    private void loadFile(String pluginName, String fileName, String filePath) {
        try {
            Gson gson = new Gson();
            JsonObject json = gson.fromJson(new FileReader(new File(filePath, fileName)), JsonObject.class);
            jsonMap.put(fileName.replace(".json", ""), json);
        } catch (Exception ex) {
            UtilsHandler.getLang().sendDebugTrace(true, pluginName, ex);
        }
    }

    private void loadLangFile() {
        String filePath = CorePlus.getInstance().getDataFolder().getPath() + "\\Vanilla-Translation";
        File file = new File(filePath);
        String[] fileList = file.list();
        if (fileList == null) {
            return;
        }
        for (String fileName : fileList) {
            if (!fileName.endsWith(".json")) {
                continue;
            }
            loadFile(ConfigHandler.getPluginName(), fileName, filePath);
            langList.add(fileName.replace(".json", ""));
        }
    }

    public Map<String, JsonObject> getJsonMap() {
        return jsonMap;
    }

    public String getValue(String pluginName, String fileName, String input) {
        try {
            return getJsonMap().get(fileName).get(input).toString();
        } catch (Exception ex) {
            UtilsHandler.getLang().sendErrorMsg(pluginName,
                    "Cannot get the value of \"" + input + "\" in \"" + fileName + "\".");
            UtilsHandler.getLang().sendDebugTrace(true, pluginName, ex);
            return input;
        }
    }
}
