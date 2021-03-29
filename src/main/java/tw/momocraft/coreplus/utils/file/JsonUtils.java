package tw.momocraft.coreplus.utils.file;

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

    private final Map<String, JsonObject> jsonMap = new HashMap<>();

    public JsonUtils() {
        loadLang();
        loadCustom();
        //loadGroup("");

        sendLoadedMsg();
    }

    private void sendLoadedMsg() {
        List<String> langList = new ArrayList<>();
        List<String> jsonList = new ArrayList<>();
        for (String fileName : jsonMap.keySet()) {
            if (fileName.startsWith("lang_"))
                langList.add(fileName);
            else
                jsonList.add(fileName);
        }
        UtilsHandler.getMsg().sendConsoleMsg(ConfigHandler.getPluginPrefix(),
                "&fLoaded Language files: " + langList.toString());
        UtilsHandler.getMsg().sendConsoleMsg(ConfigHandler.getPluginPrefix(),
                "&fLoaded Json files: " + jsonList.toString());
    }

    private void loadLang() {
        String filePath = CorePlus.getInstance().getDataFolder().getPath() + "\\Vanilla-Translation";
        File file = new File(filePath);
        String[] fileList = file.list();
        if (fileList == null)
            return;
        String langName;
        for (String fileName : fileList) {
            if (!fileName.endsWith(".json"))
                continue;
            langName = fileName.replace(".json", "");
            load(ConfigHandler.getPluginName(), "lang_" + langName, filePath + "\\" + langName + ".json");
        }
    }

    private void loadCustom() {
        Map<String, String> prop = ConfigHandler.getConfigPath().getJsonProp();
        for (String groupName : prop.keySet()) {
            load(ConfigHandler.getPluginName(), groupName, prop.get(groupName));
        }
    }

    private boolean loadGroup(String group) {
        String filePath;
        switch (group) {
            case "example":
                filePath = CorePlus.getInstance().getDataFolder().getPath();
                break;
            default:
                UtilsHandler.getMsg().sendErrorMsg(ConfigHandler.getPluginName(),
                        "Cannot load the Json file: " + group);
                return false;
        }
        return load(ConfigHandler.getPluginName(), group, filePath);
    }

    public boolean load(String pluginName, String group, String filePath) {
        File file = new File(filePath);
        try {
            Gson gson = new Gson();
            JsonObject json = gson.fromJson(new FileReader(file), JsonObject.class);
            jsonMap.put(group, json);
            return true;
        } catch (Exception ex) {
            UtilsHandler.getMsg().sendErrorMsg(pluginName,
                    "Cannot load the Json file: " + filePath);
            UtilsHandler.getMsg().sendDebugTrace(true, pluginName, ex);
            return false;
        }
    }

    public String getValue(String pluginName, String group, String input) {
        try {
            return jsonMap.get(group).get(input).toString();
        } catch (Exception ex) {
            UtilsHandler.getMsg().sendErrorMsg(pluginName,
                    "An error occurred while getting the value of \"" + input + "\".");
            UtilsHandler.getMsg().sendErrorMsg(pluginName,
                    "Can not find the Json group of \"" + group + "\".");
            return null;
        }
    }
}
