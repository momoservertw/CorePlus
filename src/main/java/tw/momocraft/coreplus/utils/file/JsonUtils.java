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

    public Map<String, String> getJsonProp() {
        return ConfigHandler.getConfigPath().getJsonProp();
    }

    private final Map<String, JsonObject> fileMap = new HashMap<>();
    private final List<String> customList = new ArrayList<>();
    private final List<String> langList = new ArrayList<>();

    public JsonUtils() {
        loadLang();
        //loadGroup("");
        loadCustom();

        sendLoadedMsg();
    }

    public boolean load(String pluginName, String group, String filePath) {
        File file = new File(filePath);
        try {
            Gson gson = new Gson();
            JsonObject json = gson.fromJson(new FileReader(file), JsonObject.class);
            fileMap.put(group, json);
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
            return fileMap.get(group).get(input).toString();
        } catch (Exception ex) {
            if (group.startsWith("lang_"))
                return null;
            UtilsHandler.getMsg().sendErrorMsg(pluginName,
                    "An error occurred while getting the value of \"" + input + "\".");
            UtilsHandler.getMsg().sendErrorMsg(pluginName,
                    "Can not find the Json group of \"" + group + "\".");
            return null;
        }
    }

    private void loadLang() {
        String filePath = CorePlus.getInstance().getDataFolder().getPath() + "\\Local";
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
            langList.add(langName);
        }
    }

    private void loadCustom() {
        Map<String, String> prop = ConfigHandler.getConfigPath().getJsonProp();
        if (prop == null)
            return;
        for (String groupName : prop.keySet()) {
            load(ConfigHandler.getPluginName(), groupName, prop.get(groupName));
            customList.add(groupName);
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

    private void sendLoadedMsg() {
        if (!langList.isEmpty())
            UtilsHandler.getMsg().sendConsoleMsg(ConfigHandler.getPluginPrefix(),
                    "Loaded Language files: " + langList);
        if (!customList.isEmpty())
            UtilsHandler.getMsg().sendConsoleMsg(ConfigHandler.getPluginPrefix(),
                    "Loaded Json files: " + customList);
    }
}
