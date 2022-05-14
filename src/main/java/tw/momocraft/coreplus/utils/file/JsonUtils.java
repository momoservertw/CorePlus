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

    private final Map<String, JsonObject> fileMap = new HashMap<>();
    private final List<String> dataList = new ArrayList<>();
    private final List<String> langList = new ArrayList<>();

    public Map<String, String> getJsonProp() {
        return ConfigHandler.getConfigPath().getJsonProp();
    }

    public boolean isEnable() {
        return ConfigHandler.getConfigPath().isDataJson();
    }

    public void setup() {
        setupLang();
        setupConfig();

        UtilsHandler.getMsg().sendFileLoadedMsg("Json", dataList);
        UtilsHandler.getMsg().sendFileLoadedMsg("Language", langList);
    }

    private void setupLang() {
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
            load(ConfigHandler.getPluginName(), "lang", "lang_" + langName, filePath + "\\" + langName + ".json");
            langList.add(langName);
        }
    }

    private void setupConfig() {
        Map<String, String> prop = ConfigHandler.getConfigPath().getJsonProp();
        if (prop == null)
            return;
        for (String groupName : prop.keySet())
            load(ConfigHandler.getPluginName(), "config", groupName, prop.get(groupName));
    }

    public boolean load(String pluginName, String type, String groupName, String filePath) {
        File file = new File(filePath);
        try {
            Gson gson = new Gson();
            JsonObject json = gson.fromJson(new FileReader(file), JsonObject.class);
            fileMap.put(groupName, json);
            if (type.equals("lang")) {
                langList.add(groupName);
            } else if (type.equals("config")) {
                dataList.add(groupName);
            }
            return true;
        } catch (Exception ex) {
            UtilsHandler.getMsg().sendErrorMsg(pluginName,
                    "Cannot load the Json file: " + filePath);
            UtilsHandler.getMsg().sendDebugTrace(pluginName, ex);
            return false;
        }
    }

    public String getValue(String pluginName, String groupName, String input) {
        try {
            return fileMap.get(groupName).get(input).toString();
        } catch (Exception ex) {
            if (groupName.startsWith("lang_"))
                return null;
            UtilsHandler.getMsg().sendErrorMsg(pluginName,
                    "Cannot get the value from Json file: " + groupName);
            UtilsHandler.getMsg().sendErrorMsg(pluginName,
                    "Value = " + input);
            UtilsHandler.getMsg().sendDebugTrace(ConfigHandler.getPluginName(), ex);
            return null;
        }
    }
}
